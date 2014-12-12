/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual.Plano;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
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
        
        this.alturaAnterior = janela.getHeight()-80;
        this.delta = this.alturaAnterior / controle.getNJogadores();
        if(this.delta > 100){
            this.delta = 100;
        }
    }

    @Override
    public void desenha(Graphics2D g2d) {
        super.desenha(g2d);
        
        this.atualizarCoordenadas();
        
        int i =0;
        Iterator<Color> ic = controle.getSala().getJogadoresCor().iterator();
        for (String s : controle.getSala().getJogadoresTime()) {
            Color c = ic.next();
            g2d.setColor(c);
            g2d.drawString(s, delta+46, i*delta+40+delta/2);

            g2d.fillRoundRect(41, i*(delta+1)+40, delta, delta, 20, 20);
            Image imagesJogador = this.mapaImagens.getImageLider(c, janela);
            g2d.drawImage(imagesJogador, 41, i*(delta+1)+40, delta, delta, janela);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(40, i*delta+40, delta+2, delta+2, 20, 20);
            
            i++;
        }
        
    }
}