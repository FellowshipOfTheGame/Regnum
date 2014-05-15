/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual.Plano;

import Controle.Controle;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 *
 * @author marcius
 */
public class Ambiente {

    public Tela2D janela;
    protected Controle controle;
    protected Imagem trocador;
    protected MapaImagens mapaImagens;
    

    public Ambiente(Tela2D janela) {
        this.janela = janela;
        this.controle = Controle.instanciaControle();

        this.mapaImagens = new MapaImagens(janela);
        
        trocador = new Imagem();
    }

    public void atualizarCoordenadas() {
    }
    
    public void setInciarTela(){
        
    }

    public void desenha(Graphics2D g2d) {
        //TODO conseguir uma imagem para abertura
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
    }
}
