/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual.Plano.Utilidades;

import Controle.Controle;
import Controle.Xadrez;
import Modelo.Campo;
import Modelo.Movimento;
import Visual.Plano.Tela2D;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author marcius
 */
public class MapaImagens {

    HashMap<String, Image> pecasHash;
    HashMap<String, Image> defensorHash;
    HashMap<String, Image> atacanteHash;
    HashMap<String, Image> iteracoesHash;
    /* Enquanto a cor não eh iniciada 
     * timeCofingurando == true; 
     * Usar colorTime
     * timeCofingurando == false; 
     * Usar colorTimes
     */
    private final Image fundo;
    private final Image letra;
    private final Image frame;
    private final Image botao;
    private final Image bordinha;
    private final Image coracao;
    private final Image coracao_preto;
    
    private boolean timeCofingurando;
    private Color colorTime;
    private ArrayList<Color> colorTimes;

    /* Faz tratamento de cores nas imagens
     */
    private Imagem trocador;

    /**
     * Imagens dos campos Preto e Branco: cores padrões de fundo, Amarelo: Campo
     * selecionado, Verde: Caminho possiveis, Azul: Campos com possivel
     * interação, Vermelho: Campos possiveis de atacar
     */
    protected final Image[] imgFundoimg;
    protected static Color[] coresUsadas;
    public static final int PRETO = 0;
    public static final int BRANCO = 1;
    public static final int AMARELO = 2;
    public static final int VERDE = 3;
    public static final int AZUL = 4;
    public static final int VERMELHO = 5;

    public MapaImagens(Component comp) {
        this.fundo = Toolkit.getDefaultToolkit().getImage("img/fundo_0.png");
        this.letra = Toolkit.getDefaultToolkit().getImage("img/letra.png");
        this.frame = Toolkit.getDefaultToolkit().getImage("img/moldura_32.png");
        this.botao = Toolkit.getDefaultToolkit().getImage("img/botao_32.png");
        this.bordinha = Toolkit.getDefaultToolkit().getImage("img/bordinha_12.png");
        this.coracao = Toolkit.getDefaultToolkit().getImage("img/coracao.png");
        this.coracao_preto = Toolkit.getDefaultToolkit().getImage("img/coracao_preto.png");
        
        trocador = new Imagem();
        
        this.imgFundoimg = new Image[6];
        this.imgFundoimg[PRETO] = Toolkit.getDefaultToolkit().getImage("img/preto.png");
        this.imgFundoimg[PRETO] = trocador.imagemConverteCor(Color.BLACK, Color.GRAY, Color.PINK, this.imgFundoimg[PRETO], comp);
        this.imgFundoimg[AMARELO] = trocador.imagemConverteCor(Color.YELLOW);
        this.imgFundoimg[AZUL] = trocador.imagemConverteCor(Color.blue);
        this.imgFundoimg[VERMELHO] = trocador.imagemConverteCor(Color.red);
        this.imgFundoimg[BRANCO] = trocador.imagemConverteCor(Color.WHITE);
        this.imgFundoimg[VERDE] = trocador.imagemConverteCor(Color.green);

        this.pecasHash = new HashMap<String, Image>();
        load("img/", pecasHash);
        this.atacanteHash = new HashMap<String, Image>();
        load("img/Ataque", atacanteHash);
        this.defensorHash = new HashMap<String, Image>();
        load("img/Defesa", defensorHash);
        this.iteracoesHash = new HashMap<String, Image>();
        load("img/Iteracao", iteracoesHash);

        coresUsadas = new Color[6];
        coresUsadas[PRETO] = Color.BLACK;
        coresUsadas[BRANCO] = Color.WHITE;
        coresUsadas[AMARELO] = Color.YELLOW;
        coresUsadas[VERDE] = Color.green;
        coresUsadas[AZUL] = Color.blue;
        coresUsadas[VERMELHO] = Color.red;
    }

    private void load(String path, HashMap<String, Image> map) {
        File folder = new File(path);
        Image img;
        File[] files = folder.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.getAbsolutePath().endsWith("png");
            }
        });
        for (File f : files) {
            img = Toolkit.getDefaultToolkit().getImage(f.getAbsolutePath());
            map.put(f.getName(), img);
        }
    }

    //[FJPVJPV]
    public Image getImagePeca(int id, Component comp) {
        int j1 = (id % 1000000) / 100000;
        int p1 = (id % 100000) / 10000;
        int v1 = (id % 10000) / 1000;
        int j2 = ((id % 1000) / 100);
        int p2 = (id % 100) / 10;
        int v2 = (id % 10);
        Image retorno = null;

        if (timeCofingurando) {
            if (j1 != 0) {
                retorno = this.pecasHash.get("p" + p1 + "" + v1 + ".png");
                retorno = trocador.imagemConverteCor(Color.blue, colorTime, Color.PINK, retorno, comp);
            }
        } else if (j1 != 0 && j1 == j2) {
            retorno = this.iteracoesHash.get("i" + p1 + "" + v1 + "" + p2 + "" + v2 + ".png");
            if (retorno == null) {
                retorno = this.iteracoesHash.get("i" + p2 + "" + v2 + "" + p1 + "" + v1 + ".png");
            }
            if (retorno == null) {
                System.err.println("iterecao: p1: "+p1+" v1: "+v1+" p2: "+p2+" v2: "+v2);
            }
            retorno = trocador.imagemConverteCor(Color.blue, colorTimes.get(j1 - 1), Color.PINK, retorno, comp);
        } else if (j1 != 0 && j2 != 0 && j1 != j2) {
            Image atacante = this.atacanteHash.get("a" + p1 + "" + v1 + ".png");
            if (atacante == null) {
                System.err.println("atacente: a: "+p1+" v: "+v1);
            }
            atacante = trocador.imagemConverteCor(Color.blue, colorTimes.get(j1 - 1), Color.PINK, atacante, comp);
            Image defensor = this.defensorHash.get("d" + p2 + "" + v2 + ".png");
            if (defensor == null) {
                System.err.println("defensor: d: "+p2+" v: "+v2);
            }
            defensor = trocador.imagemConverteCor(Color.blue, colorTimes.get(j2 - 1), Color.PINK, defensor, comp);
            retorno = trocador.somaImagens(atacante, defensor, comp);
        } else if (j1 != 0) {
            retorno = this.pecasHash.get("p" + p1 + "" + v1 + ".png");
            if (retorno == null) {
                System.err.println("peca: p: "+p1+" v: "+v1);
            }
            retorno = trocador.imagemConverteCor(Color.blue, colorTimes.get(j1 - 1), Color.PINK, retorno, comp);
        }

        return retorno;
    }

    public Image getImageFundo(int id, Tela2D janela) {
        int f = (id % 10000000) / 1000000;

        return this.imgFundoimg[f];
    }

    public Image getFundo(int cor) {
        return this.imgFundoimg[cor];
    }

    public void iniciaJogo() {
        timeCofingurando = false;
        Controle tmp = Controle.instanciaControle();
        colorTimes = tmp.getColorTimes();
    }

    public void setCorTimeConfigurando(Color cor) {
        timeCofingurando = true;
        colorTime = cor;
    }

    public static boolean corPermitida(Color cor) {
        for (int i = 0; i < coresUsadas.length; i++) {
            if (coresUsadas[i].equals(cor)) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Image> getImageOpcao(Campo campo, int pecaPega, Tela2D comp) {

        int id = campo.getId();

        int i = (id % 10000000) / 1000000;
        int j1 = (id % 1000000) / 100000;
        int p1 = (id % 100000) / 10000;
        int v1 = (id % 10000) / 1000;
        int j2 = (id % 1000) / 100;
        int p2 = (id % 100) / 10;
        int v2 = (id % 10);
        Image retorno = null;
        ArrayList<Image> listaOpcao = null;

        if (pecaPega == Movimento.PEGARP1) {
            if (p1 == Xadrez.TESTUDOS) {
                listaOpcao = new ArrayList<Image>();
                retorno = this.pecasHash.get("p" + Xadrez.TESTUDOS + "" + v1 + ".png");
                retorno = trocador.imagemConverteCor(Color.blue, colorTimes.get(j1 - 1), Color.PINK, retorno, comp);
                listaOpcao.add(retorno);
                retorno = this.pecasHash.get("p" + Xadrez.TESTUDO + "" + Xadrez.getTratadores()[Xadrez.TESTUDO - 1].getVidaTotal() + ".png");
                retorno = trocador.imagemConverteCor(Color.blue, colorTimes.get(j1 - 1), Color.PINK, retorno, comp);
                listaOpcao.add(retorno);
            } else if (p1 == Xadrez.CAVALEIRO) {
                listaOpcao = new ArrayList<Image>();
                retorno = this.pecasHash.get("p" + Xadrez.CAVALEIRO + "" + v1 + ".png");
                retorno = trocador.imagemConverteCor(Color.blue, colorTimes.get(j1 - 1), Color.PINK, retorno, comp);
                listaOpcao.add(retorno);
                retorno = this.iteracoesHash.get("i" + Xadrez.CAVALEIRO + "" + Xadrez.getTratadores()[Xadrez.CAVALEIRO - 1].getVidaTotal() + "" + (v1 - 1) + "" + Xadrez.getTratadores()[(v1 - 1) - 1].getVidaTotal() + ".png");
                retorno = trocador.imagemConverteCor(Color.blue, colorTimes.get(j1 - 1), Color.PINK, retorno, comp);
                listaOpcao.add(retorno);
            }
        } else if (pecaPega == Movimento.INTERAGIR1) {
            listaOpcao = new ArrayList<Image>();
            if (p2 == Xadrez.SOLDADO && p1 == Xadrez.BISPO) {
                if (!Xadrez.existePeca(j1, Xadrez.DAMA)) {
                    retorno = this.pecasHash.get("p" + Xadrez.DAMA + "" + Xadrez.getTratadores()[Xadrez.DAMA - 1].getVidaTotal() + ".png");
                    retorno = trocador.imagemConverteCor(Color.blue, colorTimes.get(j1 - 1), Color.PINK, retorno, comp);
                    listaOpcao.add(retorno);
                }
                for (int u = Xadrez.BISPO; u <= Xadrez.TESTUDOS; u++) {
                    retorno = this.pecasHash.get("p" + u + "" + Xadrez.getTratadores()[u - 1].getVidaTotal() + ".png");
                    retorno = trocador.imagemConverteCor(Color.blue, colorTimes.get(j1 - 1), Color.PINK, retorno, comp);
                    listaOpcao.add(retorno);
                }
            } else {
                retorno = this.pecasHash.get("p" + p1 + "" + v1 + ".png");
                retorno = trocador.imagemConverteCor(Color.blue, colorTimes.get(j1 - 1), Color.PINK, retorno, comp);
                listaOpcao.add(retorno);

                retorno = this.pecasHash.get("p" + p2 + "" + v2 + ".png");
                retorno = trocador.imagemConverteCor(Color.blue, colorTimes.get(j2 - 1), Color.PINK, retorno, comp);
                listaOpcao.add(retorno);

                if (p1 == Xadrez.CAVALEIRO && v1 == Xadrez.getTratadores()[Xadrez.CAVALEIRO - 1].getVidaTotal()) {
                    retorno = this.pecasHash.get("p" + p1 + "" + (p2+1) + ".png");
                    retorno = trocador.imagemConverteCor(Color.blue, colorTimes.get(j1 - 1), Color.PINK, retorno, comp);
                    listaOpcao.add(retorno);
                } else {
                    if (Xadrez.getTratadores()[p1 - 1].amigo(Movimento.PEGARP1, campo)) {
                        retorno = this.iteracoesHash.get("i" + p1 + "" + v1 + "" + p2 + "" + v2 + ".png");
                        if (retorno != null) {
                            retorno = trocador.imagemConverteCor(Color.blue, colorTimes.get(j1 - 1), Color.PINK, retorno, comp);
                        }
                        listaOpcao.add(retorno);
                    }
                    if (Xadrez.getTratadores()[p2 - 1].amigo(Movimento.PEGARP2, campo)) {
                        retorno = this.iteracoesHash.get("i" + p2 + "" + v2 + "" + p1 + "" + v2 + ".png");
                        if (retorno != null) {
                            retorno = trocador.imagemConverteCor(Color.blue, colorTimes.get(j2 - 1), Color.PINK, retorno, comp);
                        }
                        listaOpcao.add(retorno);
                    }
                }
            }
        } else if (pecaPega == Movimento.ATACAR1 || pecaPega == Movimento.ATACAR2) {
            listaOpcao = new ArrayList<Image>();
            retorno = this.pecasHash.get("p" + p1 + "" + v1 + ".png");
            retorno = trocador.imagemConverteCor(Color.blue, colorTimes.get(j1 - 1), Color.PINK, retorno, comp);
            listaOpcao.add(retorno);
            Image atacante = this.atacanteHash.get("a" + p1 + "" + v1 + ".png");
            atacante = trocador.imagemConverteCor(Color.blue, colorTimes.get(j1 - 1), Color.PINK, atacante, comp);
            Image defensor = this.defensorHash.get("d" + p2 + "" + v2 + ".png");
            defensor = trocador.imagemConverteCor(Color.blue, colorTimes.get(j2 - 1), Color.PINK, defensor, comp);
            retorno = trocador.somaImagens(atacante, defensor, comp);
            listaOpcao.add(retorno);
        }

        return listaOpcao;
    }

    public Image getImageLider(int jogadorAtual, Tela2D janela) {
        Image retorno = this.pecasHash.get("p" + Xadrez.REI + "" + 1 + ".png");
        retorno = trocador.imagemConverteCor(Color.blue, colorTimes.get(jogadorAtual), Color.PINK, retorno, janela);
        return retorno;
    }
    
    public Image getImageLider(Color jogadorAtual, Tela2D janela) {
        Image retorno = this.pecasHash.get("p" + Xadrez.REI + "" + 1 + ".png");
        retorno = trocador.imagemConverteCor(Color.blue, jogadorAtual, Color.PINK, retorno, janela);
        return retorno;
    }

    public Image getImageCampo(int p, int j, int v, Tela2D janela) {
        Image retorno = this.pecasHash.get("p" + p + "" + v + ".png");
        retorno = trocador.imagemConverteCor(Color.blue, colorTimes.get(j - 1), Color.PINK, retorno, janela);
        return retorno;
    }

    public Image getFundo() {
        return fundo;
    }

    public Image getLetra() {
        return letra;
    }

    public Image getFrame() {
        return frame;
    }

    public ArrayList<Image> getBotao(Campo campo, int pecaPega, Tela2D comp) {
        int id = campo.getId();

        int i = (id % 10000000) / 1000000;
        int j1 = (id % 1000000) / 100000;
        int p1 = (id % 100000) / 10000;
        int v1 = (id % 10000) / 1000;
        int j2 = (id % 1000) / 100;
        int p2 = (id % 100) / 10;
        int v2 = (id % 10);
        
        Image retorno = null;
        ArrayList<Image> listaOpcao = null;

        if (pecaPega == Movimento.PEGARP1) {
            if (p1 == Xadrez.TESTUDOS) {
                listaOpcao = new ArrayList<Image>();
                retorno = trocador.imagemConverteCor(Color.blue, Color.green, Color.PINK, botao, comp);
                listaOpcao.add(retorno);                
                retorno = trocador.imagemConverteCor(Color.blue);
                listaOpcao.add(retorno);
            } else if (p1 == Xadrez.CAVALEIRO) {
                listaOpcao = new ArrayList<Image>();
                retorno = trocador.imagemConverteCor(Color.blue, Color.green, Color.PINK,  botao, comp);
                listaOpcao.add(retorno);                
                retorno = trocador.imagemConverteCor(Color.blue);
                listaOpcao.add(retorno);
            }
        } else if (pecaPega == Movimento.INTERAGIR1) {
            listaOpcao = new ArrayList<Image>();
            if (p2 == Xadrez.SOLDADO && p1 == Xadrez.BISPO) {
                if (!Xadrez.existePeca(j1, Xadrez.DAMA)) {
                    listaOpcao.add(botao);
                }
                for (int u = Xadrez.BISPO; u <= Xadrez.TESTUDOS; u++) {                    
                    listaOpcao.add(botao);
                }
            } else {
                retorno = trocador.imagemConverteCor(Color.blue, Color.green, Color.PINK,  botao, comp);
                listaOpcao.add(retorno);                

                listaOpcao.add(retorno);

                if (p1 == Xadrez.CAVALEIRO && v1 == Xadrez.getTratadores()[Xadrez.CAVALEIRO - 1].getVidaTotal()) {
                    retorno = trocador.imagemConverteCor(Color.blue);
                    listaOpcao.add(retorno);
                } else {
                    if (Xadrez.getTratadores()[p1 - 1].amigo(Movimento.PEGARP1, campo)) {
                        retorno = trocador.imagemConverteCor(Color.blue);
                        listaOpcao.add(retorno);
                    }
                    if (Xadrez.getTratadores()[p2 - 1].amigo(Movimento.PEGARP2, campo)) {
                        retorno = trocador.imagemConverteCor(Color.blue);
                        listaOpcao.add(retorno);
                    }
                }
            }
        } else if (pecaPega == Movimento.ATACAR1 || pecaPega == Movimento.ATACAR2) {
            listaOpcao = new ArrayList<Image>();
            retorno = trocador.imagemConverteCor(Color.blue, Color.green, Color.PINK,  botao, comp);
            listaOpcao.add(retorno);                
            retorno = trocador.imagemConverteCor(Color.red);
            listaOpcao.add(retorno);
        }

        return listaOpcao;
    }

    public Image getBordinha() {
        return bordinha;
    }
    
    

    public Image getCoracao() {
        return coracao;
    }

    public Image getCoracao_preto() {
        return coracao_preto;
    }

    public static Image getBalao() {
        return (Toolkit.getDefaultToolkit().getImage("img/balao.png"));
    }
}

