/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual.Plano;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;

/**
 *
 * @author marcius
 */
public class AmbienteSala extends Ambiente{
    private int delta;
    private int alturaAnterior;
    
    public AmbienteSala(Tela2D janela) {
        super(janela);
    }

    @Override
    public void atualizarCoordenadas() {
        
        this.alturaAnterior = janela.getHeight();
        this.delta = this.alturaAnterior / controle.getNJogadores();
        if(this.delta > 100){
            this.delta = 100;
        }
    }

    @Override
    public void desenha(Graphics2D g2d) {
        this.atualizarCoordenadas();
        
        int i =0;
        Iterator<Color> ic = controle.getSala().getJogadoresCor().iterator();
        for (String s : controle.getSala().getJogadoresTime()) {
            Color c = ic.next();
            g2d.drawString(s, delta+20, i*delta+40);

            g2d.setColor(c);
            g2d.fillRect(10, i*delta+20, delta, delta);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(10, i*delta+20, delta, delta);
            i++;
        }
        
    }
}