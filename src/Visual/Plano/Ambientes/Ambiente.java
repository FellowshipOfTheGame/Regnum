/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual.Plano.Ambientes;

import Visual.Plano.Utilidades.Imagem;
import Visual.Plano.Utilidades.MapaImagens;
import Controle.Controle;
import Visual.Plano.Tela2D;
import java.awt.Graphics2D;
import java.awt.Image;
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
        Image imageFundo = this.mapaImagens.getFundo();
        Image imageLetra = this.mapaImagens.getLetra();
        Image imageBordinha = this.mapaImagens.getBordinha();
        g2d.drawImage(imageFundo, 0, 0, janela.getWidth(), janela.getHeight(), janela);
        g2d.drawImage(imageLetra, janela.getWidth()/2-imageLetra.getWidth(janela)/2, janela.getHeight()/2-imageLetra.getHeight(janela)/2, janela);
        
        g2d.drawImage(imageBordinha, 0, 0, janela.getWidth()-12, 12, 0, 0, janela.getWidth()-12, 12, janela);
        g2d.drawImage(imageBordinha, this.janela.getWidth()-12, 0, this.janela.getWidth(), janela.getHeight()-12, imageBordinha.getWidth(janela)-12, 0, imageBordinha.getWidth(janela), janela.getHeight()-12, janela);
        g2d.drawImage(imageBordinha, 12, this.janela.getHeight()-12, this.janela.getWidth(), this.janela.getHeight(), imageBordinha.getWidth(janela)-janela.getWidth()+12, imageBordinha.getHeight(janela)-12, imageBordinha.getWidth(janela), imageBordinha.getHeight(janela), janela);        
        g2d.drawImage(imageBordinha, 0, 12, 12, this.janela.getHeight(), 0, imageBordinha.getHeight(janela)-janela.getHeight()+12, 12, imageBordinha.getHeight(janela), janela);
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
