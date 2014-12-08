/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual.Plano;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 *
 * @author marcius
 */
public class AmbienteOutroJogagador extends AmbienteJogo{
    /**
     * Deslocamento do centro em relacao a tela
     */
    private int deltaX;
    /**
     * Controle de largura e altura anterior, para redimensionamento
     */
    private int larguraAnterior, alturaAnterior;
    /**
     * Controle do start do jogo
     */
    private boolean iniciado;
    
    public AmbienteOutroJogagador(Tela2D janela) {
        super(janela);
    }
    
    @Override
    public void desenha(Graphics2D g2d) {
        super.desenha(g2d);
        
        if (!this.iniciado) {
            this.iniciaTela();
        }
        
        g2d.translate(deltaX, 0);
        
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, this.larguraAnterior, this.alturaAnterior);
        
        Image imagesJogador = this.mapaImagens.getImageLider(controle.getJogadorAtual(), janela);
        int w = (this.larguraAnterior < this.alturaAnterior)? this.larguraAnterior : this.alturaAnterior;
        g2d.drawImage(imagesJogador, 0, 0, w, w, janela);
        
        g2d.translate(-deltaX, 0);
        
        
    }
    
    @Override
    public void atualizarCoordenadas() {
        super.atualizarCoordenadas();
        this.deltaX = (2*janela.getWidth()) / 3;
        this.larguraAnterior = janela.getWidth()/3;
        this.alturaAnterior = janela.getHeight();
        //janela.repaint();
    }
    
    private void iniciaTela() {
        this.iniciado = true;
        
        this.mapaImagens.iniciaJogo();
        
        //janela.repaint();
    }
}
