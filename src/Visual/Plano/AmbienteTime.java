/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual.Plano;

import Modelo.Ator.Ator;
import Modelo.Campo;
import Modelo.Tabuleiro;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 *
 * @author marcius
 */
public class AmbienteTime extends Ambiente {

    private int diametroMenor, diametroMaior;
    private int larguraAnterior;
    /**
     * Vetor de mapeamento das cores de cada campo
     */
    private int[] mapaCampo;
    /**
     * Controle do start do jogo
     */
    private boolean iniciado;

    public AmbienteTime(Tela2D janela) {
        super(janela);
    }

    @Override
    public void atualizarCoordenadas() {
        this.larguraAnterior = janela.getWidth();

        diametroMaior = (this.larguraAnterior-80) / 9;
        diametroMenor = diametroMaior; // o valor padrão do lasango menor é 100, mas para melhor proporção ele é refatorado

        //janela.repaint();
    }

    @Override
    public void desenha(Graphics2D g2d) {
        super.desenha(g2d);

        if (janela == null) {
            return;
        }

        Image imagePeca, imageFundo;

        Ator a = controle.getUsuario();
        if (a == null) {
            return;
        } else if (!this.iniciado) {
            this.iniciaTela(a);
        }
        g2d.setColor(a.getCor());
        g2d.fillRoundRect(40, 40, 102, 102, 20, 20);
        g2d.drawString(a.getTime(), 146, 90);
        Image imagesJogador = this.mapaImagens.getImageLider(a.getCor(), janela);
        g2d.drawImage(imagesJogador, 41, 40, 100, 100, janela);
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(40, 40, 102, 102, 20, 20);
        //g2d.drawRect(40, 40, 102, 102);

        g2d.translate(this.larguraAnterior/2, 40);

        for (int j = 1; j < 10; j++) {
            for (int k = 0; k < j; k++) {
                imageFundo = this.mapaImagens.getImageFundo(this.mapaCampo[(j * (j - 1)) / 2 + k], janela);
                g2d.drawImage(imageFundo, -diametroMenor / 2 * j + diametroMenor * k, (diametroMaior * (j - 1)) / 2, -diametroMenor / 2 * j + diametroMenor * (k + 1), (diametroMaior * (j + 1)) / 2, 0, 0, 128, 128, janela);
                imagePeca = this.mapaImagens.getImagePeca(this.mapaCampo[(j * (j - 1)) / 2 + k], janela);
                g2d.translate(-diametroMenor / 2 * j + diametroMenor * k, (diametroMaior * (j - 1)) / 2);
                g2d.drawImage(imagePeca, diametroMenor / 4, diametroMaior / 4, (diametroMenor * 3) / 4, (diametroMaior * 3) / 4, 0, 0, 128, 128, janela);
                g2d.translate(diametroMenor / 2 * j - diametroMenor * k, -(diametroMaior * (j - 1)) / 2);
            }
        }

    }

    /**
     * Configura as variaveis internas para um dado numero de jogadores e
     * imprime a tela novamente.
     *
     */
    private void iniciaTela(Ator a) {
        if (janela == null) {
            return;
        }

        mapaImagens.setCorTimeConfigurando(a.getCor());

        mapaCampo = new int[45];
        Campo[] camposTemp = Tabuleiro.campos();
        for (int i = 0; i < 45; i++) {
            mapaCampo[i] = camposTemp[i].getId();
        }

        this.iniciado = true;

        //janela.repaint();
    }

    @Override
    public void setInciarTela() {
        this.iniciado = false;
    }
}
