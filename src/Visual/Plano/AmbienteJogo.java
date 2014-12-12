/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual.Plano;

import Controle.Controle;
import Controle.Xadrez;
import Modelo.Campo;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 *
 * @author marcius
 */
public class AmbienteJogo extends Ambiente {

    /**
     * Controle de rotação da tela
     */
    protected static double rotacaoTabuleiro;
    /**
     * Deslocamento do centro em relacao a tela
     */
    protected int deltaXTabuleiro, deltaYTabuleiro;
    /**
     * Zum da tela
     */
    protected static double scaleTabuleiro;

    /**
     * Numero de faces do tabuleiro
     */
    private int nFaces;
    /**
     * Guardam as dimensões do losango
     */
    private int diametroMenor, diametroMaior, ladoLozango, larguraImagem;
    /**
     * Controle de largura e altura anterior, para redimensionamento
     */
    private int larguraAnterior, alturaAnterior;

    /**
     * Controle do start do jogo
     */
    private boolean iniciado;

    /**
     * Controle de arrasto da tela
     */
    private int Xa, Ya;
    /**
     * Angulo de cada face, sendo igual a 2pi/nFaces
     */
    private double angle;

    /**
     * Vetor de mapeamento das cores de cada campo
     */
    private int[] mapaCampo;
    /**
     * Vetor que guarda informações da ultimo campo clicado campoFLC[0] guarda a
     * face do campo campoFLC[1] guarda a linha do campo campoFLC[2] guarda a
     * coluna do campo
     */
    private int[] campoFLC;
    private final int FACE = 0;
    private final int LINHA = 1;
    private final int COLUNA = 2;

    /**
     * Controle do estado do mouse
     */
    private int mouseEstado;
    private final int INATIVO = 0;
    private final int ARRASTANDO_TELA = 1;
    private final int GIRANDO_TELA = 2;

    public AmbienteJogo(Tela2D janela) {
        super(janela);

        this.iniciado = false;

        this.mouseEstado = INATIVO;
    }

    /*INICIO-----IMPLEMENTACAO METODOS DE CONTROLE DE EVENTOS NA TELA---------*/
    /**
     * Detecta qual campo foi clicado, dado a posicao do mouse, e retorna a
     * face, linha e coluna.
     *
     * @param x Posicao X do mouse em relacao ao centro
     * @param y Posicao Y do mouse em relacao ao centro
     * @return int[3] com os valores da face, linha e coluna nas posicoes 0, 1 e
     * 2.
     */
    public int[] clickemCampo(double x, double y) {
        int[] flc = new int[3];
        if (this.controle.getEstado() != Controle.JOGANDO) {
            return null;
        }

        //conversao em coordenadas polares em relacao ao centro do tabuleiro
        double hipo = Math.hypot(x, y);
        double theta = Math.atan2(y, x); //angulo em radianos

        theta = -theta - rotacaoTabuleiro + 2 * Math.PI + (Math.PI + angle) / 2;// ajuste do intervalo de theta para considerar a primeira face
        theta %= 2 * Math.PI;
        //System.out.println(" t: " + (int)(theta*180/Math.PI)+" angle: "+(int) (angle*180/Math.PI) );

        int face = (int) (theta / angle);
        theta %= angle;

        //calculando a quantidade de quadrados nas laterais - distancia até o campo desejado
        int v = (int) (hipo / ((Math.sin(angle - theta) / Math.tan(theta)) + Math.cos(angle - theta)));
        int u = (int) ((hipo - v * Math.cos(angle - theta)) / Math.cos(theta));

        //dividindo pelo lado estimado dos losangos
        u /= ladoLozango;
        v /= ladoLozango;
        int linha = u + v;
        int coluna = v;

        if (linha > 8 || coluna > 8) {
            return null;
        }

        flc[0] = face;
        flc[1] = linha;
        flc[2] = coluna;
        return flc;
    }

    /*FIM-------IMPLEMENTACAO METODOS DE CONTROLE DE EVENTOS NA TELA----------*/
    /*INICIO-------------IMPLEMENTACAO METODOS DE DESENHO---------------------*/
    @Override
    public void desenha(Graphics2D g2d) {
        super.desenha(g2d);

        if (!this.iniciado) {
            this.iniciaJogo();
        }

        Campo[] camposTemp = controle.getXadrez().getCampos();
        for (int i = 0; i < nFaces * 45; i++) {
            mapaCampo[i] = camposTemp[i].getId();
        }

        Image imagePeca, imageFundo;

        g2d.translate(deltaXTabuleiro, deltaYTabuleiro);
        g2d.scale(scaleTabuleiro, scaleTabuleiro);

        g2d.rotate(-rotacaoTabuleiro);
        for (int i = 0; i < nFaces; i++) {
            for (int j = 1; j < 10; j++) {
                for (int k = 0; k < j; k++) {
                    imageFundo = this.mapaImagens.getImageFundo(this.mapaCampo[i * 45 + (j * (j - 1)) / 2 + k], janela);
                    g2d.drawImage(imageFundo, -diametroMenor / 2 * j + diametroMenor * k, (diametroMaior * (j - 1)) / 2, -diametroMenor / 2 * j + diametroMenor * (k + 1), (diametroMaior * (j + 1)) / 2, 0, 0, imageFundo.getWidth(janela), imageFundo.getHeight(janela), janela);
                    imagePeca = this.mapaImagens.getImagePeca(this.mapaCampo[i * 45 + (j * (j - 1)) / 2 + k], janela);
                    if (imagePeca != null) {
                        g2d.translate(-diametroMenor / 2 * j + diametroMenor * k + diametroMenor / 2, (diametroMaior * (j - 1)) / 2 + diametroMaior / 2);
                        g2d.rotate((angle) * (i) + rotacaoTabuleiro);
                        g2d.drawImage(imagePeca, -larguraImagem / 2, -larguraImagem / 2, larguraImagem / 2, larguraImagem / 2, 0, 0, imagePeca.getWidth(janela), imagePeca.getHeight(janela), janela);
                        g2d.rotate(-(angle) * (i) - rotacaoTabuleiro);
                        g2d.translate(diametroMenor / 2 * j - diametroMenor * k - diametroMenor / 2, -(diametroMaior * (j - 1)) / 2 - diametroMaior / 2);

                    }
                }
            }
            g2d.rotate(-angle);
        }
        g2d.rotate(angle * (nFaces));

        g2d.rotate(rotacaoTabuleiro);

        g2d.scale(1 / scaleTabuleiro, 1 / scaleTabuleiro);
        g2d.translate(-deltaXTabuleiro, -deltaYTabuleiro);

        Image imageBordinha = this.mapaImagens.getBordinha();
        g2d.drawImage(imageBordinha, 0, 0, janela.getWidth() - 12, 12, 0, 0, janela.getWidth() - 12, 12, janela);
        g2d.drawImage(imageBordinha, this.janela.getWidth() - 12, 0, this.janela.getWidth(), janela.getHeight() - 12, imageBordinha.getWidth(janela) - 12, 0, imageBordinha.getWidth(janela), janela.getHeight() - 12, janela);
        g2d.drawImage(imageBordinha, 12, this.janela.getHeight() - 12, this.janela.getWidth(), this.janela.getHeight(), imageBordinha.getWidth(janela) - janela.getWidth() + 12, imageBordinha.getHeight(janela) - 12, imageBordinha.getWidth(janela), imageBordinha.getHeight(janela), janela);
        g2d.drawImage(imageBordinha, 0, 12, 12, this.janela.getHeight(), 0, imageBordinha.getHeight(janela) - janela.getHeight() + 12, 12, imageBordinha.getHeight(janela), janela);

        if (this.campoFLC != null) {
            g2d.translate((this.larguraAnterior * 2) / 3, 0);

            this.desenhaQuadro(g2d, larguraAnterior / 3, alturaAnterior);

            int id = this.mapaCampo[this.campoFLC[FACE] * 45 + (this.campoFLC[LINHA] * (this.campoFLC[LINHA] + 1)) / 2 + this.campoFLC[COLUNA]];
            int j1 = (id % 1000000) / 100000;
            int p1 = (id % 100000) / 10000;
            int v1 = (id % 10000) / 1000;
            int j2 = (id % 1000) / 100;

            Image imageCoracao = this.mapaImagens.getCoracao();

            int size = (j2 != 0) ? 2 : 1;
            int h = (this.alturaAnterior - (40 + imageCoracao.getWidth(janela)) * (size + 1)) / size;
            h = (h < this.larguraAnterior / 3 - 80) ? h : this.larguraAnterior / 3 - 80;

            Image peca = this.mapaImagens.getImageCampo(p1, j1, v1, janela);
            g2d.drawImage(peca, this.larguraAnterior / 6 - h / 2, 40 + h / 5, h, h, janela);
            if (p1 == Xadrez.CAVALEIRO && v1 > 1) {
                g2d.drawImage(imageCoracao, larguraAnterior / 6 - h / 5, 40, h / 5, h / 5, janela);
                g2d.drawImage(imageCoracao, larguraAnterior / 6 + imageCoracao.getWidth(janela), 40, h / 5, h / 5, janela);
            } else {
                for (int i = 0; i < v1; i++) {
                    g2d.drawImage(imageCoracao, larguraAnterior / 6 + (2 * i - v1) * (h / 5) / 2, 40, h / 5, h / 5, janela);
                }
            }
            if (size == 2) {
                int p2 = (id % 100) / 10;
                int v2 = (id % 10);

                peca = this.mapaImagens.getImageCampo(p2, j2, v2, janela);
                g2d.drawImage(peca, this.larguraAnterior / 6 - h / 2, h + 40 + 2 * (h / 5) + 32, h, h, janela);
                if (p2 == Xadrez.CAVALEIRO && v2 > 1) {
                    g2d.drawImage(imageCoracao, larguraAnterior / 6 - h / 5, h + 40 + (h / 5) + 32, h / 5, h / 5, janela);
                    g2d.drawImage(imageCoracao, larguraAnterior / 6 + h / 5, h + 40 + (h / 5) + 32, h / 5, h / 5, janela);
                } else {
                    for (int i = 0; i < v2; i++) {
                        g2d.drawImage(imageCoracao, larguraAnterior / 6 + (2 * i - v2) * (h / 5) / 2, h + 40 + (h / 5) + 32, h / 5, h / 5, janela);
                    }
                }
            }

            g2d.translate(-(this.larguraAnterior * 2) / 3, 0);
        }
    }

    protected void desenhaQuadro(Graphics2D g2d, int largura, int altura) {
        Image imageFrame = this.mapaImagens.getFrame();
        int laguraBordaFrame = 32;

        g2d.drawImage(imageFrame, laguraBordaFrame, laguraBordaFrame, largura - laguraBordaFrame, altura - laguraBordaFrame, laguraBordaFrame, imageFrame.getHeight(janela) - alturaAnterior + laguraBordaFrame, larguraAnterior / 3 - laguraBordaFrame, imageFrame.getHeight(janela) - laguraBordaFrame, janela);
        g2d.drawImage(imageFrame, 0, 0, largura - laguraBordaFrame, laguraBordaFrame, 0, 0, largura - laguraBordaFrame, laguraBordaFrame, janela);
        g2d.drawImage(imageFrame, largura - laguraBordaFrame, 0, largura, alturaAnterior - laguraBordaFrame, imageFrame.getWidth(janela) - laguraBordaFrame, 0, imageFrame.getWidth(janela), alturaAnterior - laguraBordaFrame, janela);
        g2d.drawImage(imageFrame, laguraBordaFrame, altura - laguraBordaFrame, largura, altura, imageFrame.getWidth(janela) - larguraAnterior / 3 + laguraBordaFrame, imageFrame.getHeight(janela) - laguraBordaFrame, imageFrame.getWidth(janela), imageFrame.getHeight(janela), janela);
        g2d.drawImage(imageFrame, 0, laguraBordaFrame, laguraBordaFrame, altura, 0, imageFrame.getHeight(janela) - alturaAnterior + laguraBordaFrame, laguraBordaFrame, imageFrame.getHeight(janela), janela);
    }

    /*FIM-------------IMPLEMENTACAO METODOS DE DESENHO---------------------*/
    /*INICIO-------------IMPLEMENTACAO METODOS CONTROLE VARIAVEIS-------------*/
    /**
     * Configura as variaveis internas para um dado numero de jogadores e
     * imprime a tela novamente.
     *
     */
    private void iniciaJogo() {
        deltaXTabuleiro = (larguraAnterior * 2) / 6;

        int nJogadores = controle.getNJogadores();

        nFaces = 2 * nJogadores;
        angle = 2 * Math.PI / nFaces;
        rotacaoTabuleiro = 2 * angle * controle.getUsuario().getOrdem();

        diametroMenor = 128 - (nFaces / 2 - 2) * 6; // o valor padrão do lasango menor é 100, mas para melhor proporção ele é refatorado
        diametroMaior = (int) (diametroMenor / Math.tan(angle / 2));
        ladoLozango = (int) (diametroMaior / (2 * Math.cos(angle / 2)));
        double tgAlpha = Math.tan(Math.PI / 2 - angle / 2);
        larguraImagem = (int) (diametroMenor * tgAlpha / (tgAlpha + 1));

        double tamanhoTela = (larguraAnterior < alturaAnterior) ? larguraAnterior : alturaAnterior;
        double tamanhoTabuleiro = (21 * ladoLozango * Math.cos(angle / 2));//18*ladoLozango*sen(90-angle/2)
        //System.out.println("tTe: " + tamanhoTela + " tTa: " + tamanhoTabuleiro);
        scaleTabuleiro = tamanhoTela / tamanhoTabuleiro;

        this.iniciado = true;

        mapaCampo = new int[nFaces * 45];

        this.mapaImagens.iniciaJogo();

        //janela.repaint();
    }

    @Override
    public void atualizarCoordenadas() {
        int w = (janela.getWidth() * 2) / 3;
        deltaXTabuleiro += (w - (this.larguraAnterior * 2) / 3) / 2;
        deltaYTabuleiro += (janela.getHeight() - this.alturaAnterior) / 2;
        this.larguraAnterior = janela.getWidth();
        this.alturaAnterior = janela.getHeight();
        janela.repaint();
    }

    private void setScale(double ds) {
        scaleTabuleiro *= ds;
    }

    /**
     * 1 para rotacionar um campo para frente -1 para rotacionar uma campo para
     * tras
     */
    private void setRotacao(int dr) {
        rotacaoTabuleiro = (rotacaoTabuleiro + dr * (angle / 3)) % (2 * Math.PI);
    }

    /*FIM-------------IMPLEMENTACAO METODOS CONTROLE VARIAVEIS-------------*/
    /*INICIO----------------------IMPLEMENTACAO METODOS MOUSE-----------------*/
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            double x = (e.getX() - deltaXTabuleiro) / scaleTabuleiro;
            double y = (e.getY() - deltaYTabuleiro) / scaleTabuleiro;
            this.campoFLC = this.clickemCampo(x, y);
            if (this.campoFLC != null) {
                this.controle.campoSelecionado(this.campoFLC[FACE], this.campoFLC[LINHA], this.campoFLC[COLUNA]);
            }
            this.campoFLC = null;
            //janela.repaint();
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (this.mouseEstado == ARRASTANDO_TELA) {
            deltaXTabuleiro += e.getX() - this.Xa;
            deltaYTabuleiro += e.getY() - this.Ya;
            this.Xa = e.getX();
            this.Ya = e.getY();
            //janela.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        double x = (e.getX() - deltaXTabuleiro) / scaleTabuleiro;
        double y = (e.getY() - deltaYTabuleiro) / scaleTabuleiro;
        this.campoFLC = this.clickemCampo(x, y);
        if (this.campoFLC != null) {
            int id = this.mapaCampo[this.campoFLC[FACE] * 45 + (this.campoFLC[LINHA] * (this.campoFLC[LINHA] + 1)) / 2 + this.campoFLC[COLUNA]];
            int j1 = (id % 1000000) / 100000;
            if (j1 != 0) {
                // janela.repaint();
                return;
            }
            this.campoFLC = null;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1: {
                this.Xa = e.getX();
                this.Ya = e.getY();
                this.mouseEstado = ARRASTANDO_TELA;

                break;
            }
            case MouseEvent.BUTTON2: {
                this.mouseEstado = GIRANDO_TELA;

                break;
            }
        }
        //janela.repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        switch (this.mouseEstado) {
            case INATIVO: {
                if (e.getWheelRotation() > 0) {
                    this.setScale(0.91f);
                } else {
                    this.setScale(1.1f);
                }

                break;
            }
            case GIRANDO_TELA: {
                if (e.getWheelRotation() > 0) {
                    this.setRotacao(-1);
                } else {
                    this.setRotacao(1);
                }

                break;
            }
        }

        //janela.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.mouseEstado = INATIVO;
        //janela.repaint();
    }
    /*FIM----------------------IMPLEMENTACAO METODOS MOUSE-----------------*/
}
