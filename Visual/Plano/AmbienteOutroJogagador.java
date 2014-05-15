/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual.Plano;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 *
 * @author marcius
 */
public class AmbienteOutroJogagador extends AmbienteJogo{

    public AmbienteOutroJogagador(Tela2D janela) {
        super(janela);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            double x = (e.getX() - deltaX) / s;
            double y = (e.getY() - deltaY) / s;
         
            janela.repaint();
        }
    }
    
    @Override
    public void desenha(Graphics2D g2d) {
        super.desenha(g2d);
        //TODO TRATAR DESENHO DO JOGADOR QUE ESTA JOGANDO
    }
}
