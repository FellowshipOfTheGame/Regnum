/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual.Plano;

import Controle.Controle;
import Modelo.Campo;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 *
 * @author marcius
 */
public class AmbienteJogo extends Ambiente {

    private int nFaces = 0;
    /**
     * Angulo de cada face, sendo igual a 2pi/nFaces
     */
    private double angle;
    /**
     * Deslocamento do centro em relacao a tela
     */
    protected int deltaX, deltaY;
    protected double s = 1;
    /**
     * Guardam as dimensões do losango
     */
    private int diametroMenor, diametroMaior, lado;

    /**
     * Controle de largura e altura anterior, para redimensionamento
     */
    private int larguraAnterior, alturaAnterior;
    /**
     * Vetor de mapeamento das cores de cada campo
     */
    private int[] mapaCampo;
    /**
     * Controle do start do jogo
     */
    private boolean iniciado;
    /**
     * Controle de arrasto da tela
     */
    private int Xa, Ya;
    /**
     * Controle de rotação da tela
     */
    private double rotacao;
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
     * Detecta qual campo foi clicado, dado a posicao do mouse, e dispara um
     * evento em AtorUsuario
     *
     * @param x Posicao X do mouse em relacao ao centro
     * @param y Posicao Y do mouse em relacao ao centro
     */
    public void clickemCampo(double x, double y) {
        if (this.controle.getEstado() != Controle.JOGANDO) {
            return;
        }

        //conversao em coordenadas polares em relacao ao centro do tabuleiro
        double hipo = Math.hypot(x, y);
        double theta = Math.atan2(y, x); //angulo em radianos

        theta = -theta - rotacao + 2 * Math.PI + (Math.PI + angle) / 2;// ajuste do intervalo de theta para considerar a primeira face
        theta %= 2 * Math.PI;
        //System.out.println(" t: " + (int)(theta*180/Math.PI)+" angle: "+(int) (angle*180/Math.PI) );


        int face = (int) (theta / angle);
        theta %= angle;


        //calculando a quantidade de quadrados nas laterais - distancia até o campo desejado
        int v = (int) (hipo / ((Math.sin(angle - theta) / Math.tan(theta)) + Math.cos(angle - theta)));
        int u = (int) ((hipo - v * Math.cos(angle - theta)) / Math.cos(theta));


        //dividindo pelo lado estimado dos losangos
        u /= lado;
        v /= lado;
        int linha = u + v;
        int coluna = v;

        if (linha > 8 || coluna > 8) {
            return;
        }

        this.controle.campoSelecionado(face, linha, coluna);
    }

    /*FIM-------IMPLEMENTACAO METODOS DE CONTROLE DE EVENTOS NA TELA----------*/
    /*INICIO-------------IMPLEMENTACAO METODOS DE DESENHO---------------------*/
    @Override
    public void desenha(Graphics2D g2d) {
        if (!this.iniciado) {
            this.iniciaJogo();
        }


        Image imagePeca, imageFundo;

        g2d.translate(deltaX, deltaY);
        g2d.scale(s, s);

        g2d.rotate(-rotacao);
        for (int i = 0; i < nFaces; i++) {
            g2d.rotate(-angle);
            for (int j = 1; j < 10; j++) {
                for (int k = 0; k < j; k++) {
                    imageFundo = this.mapaImagens.getImageFundo(this.mapaCampo[i * 45 + (j * (j - 1)) / 2 + k], janela);
                    g2d.drawImage(imageFundo, -diametroMenor / 2 * j + diametroMenor * k, (diametroMaior * (j - 1)) / 2, -diametroMenor / 2 * j + diametroMenor * (k + 1), (diametroMaior * (j + 1)) / 2, 0, 0, 128, 128, janela);
                    imagePeca = this.mapaImagens.getImagePeca(this.mapaCampo[i * 45 + (j * (j - 1)) / 2 + k], janela);
                    g2d.translate(-diametroMenor / 2 * j + diametroMenor * k, (diametroMaior * (j - 1)) / 2);
                    g2d.rotate((angle+rotacao/2)*(i+1));
                    g2d.drawImage(imagePeca, 0, 0, diametroMenor, diametroMaior, 0, 0, 128, 128, janela);                    
                    g2d.rotate(-(angle+rotacao/2)*(i+1));
                    g2d.translate(diametroMenor / 2 * j - diametroMenor * k, -(diametroMaior * (j - 1)) / 2);
                }
            }

        }
    }

    /*FIM-------------IMPLEMENTACAO METODOS DE DESENHO---------------------*/
    /*INICIO-------------IMPLEMENTACAO METODOS CONTROLE VARIAVEIS-------------*/
    /**
     * Configura as variaveis internas para um dado numero de jogadores e
     * imprime a tela novamente.
     *
     */
    private void iniciaJogo() {

        int nJogadores = controle.getNJogadores();

        nFaces = 2 * nJogadores;
        angle = 2 * Math.PI / nFaces;
        rotacao = 0;

        diametroMenor = 128 - (nFaces / 2 - 2) * 6; // o valor padrão do lasango menor é 100, mas para melhor proporção ele é refatorado
        diametroMaior = (int) (diametroMenor / Math.tan(angle / 2));
        lado = (int) (diametroMaior / (2 * Math.cos(angle / 2)));

        mapaCampo = new int[nFaces * 45];
        Campo[] camposTemp = controle.getXadrez().getCampos();
        for (int i = 0; i < nFaces * 45; i++) {
            mapaCampo[i] = camposTemp[i].getId();
        }

        this.iniciado = true;
        
        this.mapaImagens.iniciaJogo();

        janela.repaint();
    }

    @Override
    public void atualizarCoordenadas() {
        int w = (janela.getWidth() * 2) / 3;
        this.deltaX += (w - this.larguraAnterior) / 2;
        this.deltaY += (janela.getHeight() - this.alturaAnterior) / 2;
        this.larguraAnterior = janela.getWidth();
        this.alturaAnterior = janela.getHeight();
        janela.repaint();
    }

    private void setScale(double ds) {
        s *= ds;
    }

    /**
     * 1 para rotacionar um campo para frente -1 para rotacionar uma campo para
     * tras
     */
    private void setRotacao(int dr) {
        this.rotacao = (this.rotacao + dr * (angle / 3)) % (2 * Math.PI);
    }

    /*FIM-------------IMPLEMENTACAO METODOS CONTROLE VARIAVEIS-------------*/
    /*INICIO----------------------IMPLEMENTACAO METODOS MOUSE-----------------*/
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            double x = (e.getX() - deltaX) / s;
            double y = (e.getY() - deltaY) / s;
            this.clickemCampo(x, y);
            janela.repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (this.mouseEstado == ARRASTANDO_TELA) {
            this.deltaX += e.getX() - this.Xa;
            this.deltaY += e.getY() - this.Ya;
            this.Xa = e.getX();
            this.Ya = e.getY();
            janela.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //janela.repaint();
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
        janela.repaint();
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

        this.janela.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.mouseEstado = INATIVO;
        janela.repaint();
    }
    /*FIM----------------------IMPLEMENTACAO METODOS MOUSE-----------------*/
}
