/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual.Plano.Ambientes;

import Visual.Plano.Tela2D;
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
            this.iniciaAnterior();
        }
        
        g2d.translate(deltaX, 0);
        
        super.desenhaQuadro(g2d, larguraAnterior, alturaAnterior);
        
        Image imagesJogador = this.mapaImagens.getImageLider(controle.getJogadorAtual(), janela);
        int w = (this.larguraAnterior < this.alturaAnterior)? this.larguraAnterior : this.alturaAnterior;
        w-=80;
        g2d.drawImage(imagesJogador, this.larguraAnterior/2-w/2, this.alturaAnterior/2-w/2, w, w, janela);
        
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
    
    private void iniciaAnterior() {
        this.iniciado = true;
        
        this.mapaImagens.iniciaJogo();
        
        //janela.repaint();
    }
}
