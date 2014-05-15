/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual.Plano;

import Controle.Controle;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileFilter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author marcius
 */
public class MapaImagens {

    HashMap<String, Image> pecasHash;
    HashMap<String, Image> inimigosHash;
    HashMap<String, Image> iteracoesHash;
    /* Enquanto a cor não eh iniciada 
     * timeCofingurando == true; 
     * Usar colorTime
     * timeCofingurando == false; 
     * Usar colorTimes
     */
    private boolean timeCofingurando; 
    private Color colorTime;
    private ArrayList<Color> colorTimes;
    
    /* Faz tratamento de cores nas imagens
     */
    private Imagem trocador;
    
    /**
     * Imagens dos campos Preto e Branco: cores padrões de fundo, Amarelo: Campo
     * selecionado, Verde: Caminho possiveis, Azul: Campos com possivel interação,
     * Vermelho: Campos possiveis de atacar
     */
    protected final Image[] imgFundoimg;
    protected final int PRETO=0;
    protected final int BRANCO=1;
    private final int AMARELO=2;
    private final int VERDE=3;
    private final int AZUL=4;
    private final int VERMELHO=5;

    public MapaImagens(Component comp) {        
        trocador = new Imagem();
                
        this.imgFundoimg = new Image[6];
        this.imgFundoimg[PRETO] = Toolkit.getDefaultToolkit().getImage("src/img/preto.png");
        this.imgFundoimg[AMARELO] = trocador.imagemConverteCor(Color.BLACK, Color.YELLOW, Color.PINK, this.imgFundoimg[PRETO], comp);
        this.imgFundoimg[AZUL] = trocador.imagemConverteCor(Color.blue);
        this.imgFundoimg[VERMELHO] = trocador.imagemConverteCor(Color.red);
        this.imgFundoimg[BRANCO] = trocador.imagemConverteCor(Color.WHITE);
        this.imgFundoimg[VERDE] = trocador.imagemConverteCor(Color.green);

        
        this.pecasHash = new HashMap<String, Image>();
        load("src/img/", pecasHash);
        this.inimigosHash = new HashMap<String, Image>();
        load("src/img/Ataque", inimigosHash);
        this.iteracoesHash = new HashMap<String, Image>();
        load("src/img/Iteracao", iteracoesHash);
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

    //[IFJPVJPV]
    public Image getImagePeca(int id, Component comp) {
        int i = (id % 100000000) / 10000000;
        int j1 = (id % 1000000) / 100000;
        int p1 = (id % 100000) / 10000;
        int j2 = ((id % 1000) / 100);
        int p2 = (id % 100) / 10;
        Image retorno = null;

        if(timeCofingurando){
            if (j1 != 0) {
                retorno = this.pecasHash.get("i" + p1 + ".png");
                retorno = trocador.imagemConverteCor(Color.blue, colorTime, Color.PINK, retorno, comp);
                return retorno;
            }
        }
        
        if (i != 0 && j1 == j2) {
            retorno = this.iteracoesHash.get("i" + p1 + "_" + p2 + ".png");
            if (retorno == null) {
                return this.iteracoesHash.get("i" + p2 + "_" + p1 + ".png");
            }
        }

        if (i != 0 && j1 != j2) {
            //TODO fazer tratamento das cores diferentes
            retorno = this.inimigosHash.get("i" + p1 + "_" + p2 + ".png");
            if (retorno == null) {
                return this.inimigosHash.get("i" + p2 + "_" + p1 + ".png");
            }
        }

        if (j1 != 0) {
            retorno = this.pecasHash.get("i" + p1 + ".png");
            retorno = trocador.imagemConverteCor(Color.blue, colorTimes.get(j1-1), Color.PINK, retorno, comp);
            
        }

        if (j2 != 0) {
            retorno = this.pecasHash.get("i" + p2 + ".png");
            retorno = trocador.imagemConverteCor(Color.blue, colorTimes.get(j2-1), Color.PINK, retorno, comp);

        }

        return retorno;
    }

    Image getImageFundo(int id, Tela2D janela) {
        int f = (id % 10000000) / 1000000;
        
        return this.imgFundoimg[f];
    }

    Image getFundo(int cor) {
        return this.imgFundoimg[cor];
    }

    void iniciaJogo() {
        timeCofingurando = false;
        Controle tmp = Controle.instanciaControle();
        colorTimes = tmp.getColorTimes();
    }

    void setCorTimeConfigurando(Color cor) {
        timeCofingurando = true;
        colorTime = cor;
    }
}
