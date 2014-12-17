/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual.Plano.Ambientes;

import Visual.Plano.Tela2D;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 *
 * @author marcius
 */
public class AmbienteVencedor extends AmbienteJogo{
    /**
     * Deslocamento do centro em relacao a tela
     */
    private int deltaX, deltaY;
    /**
     * Controle do start do jogo
     */
    private static boolean resetar;
    
    public AmbienteVencedor(Tela2D janela) {
        super(janela);
        
        resetar = false;
    }
    
    @Override
    public void desenha(Graphics2D g2d) {
        super.desenha(g2d);
        
        if (!resetar) {
            this.resetarTela();
        }
        
        g2d.translate(deltaX, deltaY);
        
        Image imagesVencedor = this.mapaImagens.getImageLider(controle.getJogadorAtual(), janela);
        g2d.drawImage(imagesVencedor, -imagesVencedor.getWidth(janela)/2, -imagesVencedor.getHeight(janela)/2, janela);
        
        g2d.translate(-deltaX, -deltaY);
        
    }
    
    
    @Override
    public void atualizarCoordenadas() {
        super.atualizarCoordenadas();
        
        this.deltaX = (janela.getWidth() * 1) / 3;
        this.deltaY = (janela.getHeight() * 1) / 3;
        //janela.repaint();
    }
    
    private void resetarTela() {
        resetar = true;
        
        this.mapaImagens.iniciaJogo();
        
        //janela.repaint();
    }

    public static void setReset(boolean iniciado) {
        AmbienteVencedor.resetar = iniciado;
    }
}

