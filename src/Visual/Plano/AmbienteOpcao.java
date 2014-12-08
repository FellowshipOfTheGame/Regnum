/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual.Plano;

import Controle.Xadrez;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author marcius
 */
public class AmbienteOpcao extends AmbienteJogo{
    /**
     * Deslocamento do centro em relacao a tela
     */
    private int deltaX;
    /**
     * Controle de largura e altura anterior, para redimensionamento
     */
    private int larguraTela, alturaTela;
    /**
     * Controle do start do jogo
     */
    private static boolean resetar;
    
    private ArrayList<Image> imagesOpcao;
    private int larguraBotao;
    
    public AmbienteOpcao(Tela2D janela) {
        super(janela);
        
        resetar = false;
    }
    
    @Override
    public void desenha(Graphics2D g2d) {
        super.desenha(g2d);
        
        if (!resetar) {
            this.resetarTela();
        }
        
        g2d.translate(deltaX, 0);
        
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, this.larguraTela, this.alturaTela);
        
        int y = 10;
        for (Image imagePeca : imagesOpcao) {
            if(imagePeca != null){
                g2d.drawImage(imagePeca, this.larguraTela/2 - this.larguraBotao/2, y, this.larguraBotao, this.larguraBotao, janela);
                y += this.larguraBotao + 10;
            }
        }
        
        g2d.translate(-deltaX, 0);
        
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        
        if (e.getButton() == MouseEvent.BUTTON1) {
            double x = (e.getX() - deltaX);
            double y = e.getY();
            
            int h = 10;
            if((this.larguraTela/2 - this.larguraBotao/2) < x && (this.larguraTela/2 + this.larguraBotao/2) > x){
                for (int i=0; i<imagesOpcao.size(); i++) {
                    if(h < y && (h + this.larguraBotao) > y){
                        this.controle.botaoSelecionado(i);
                    }
                    
                    h += this.larguraBotao + 10;
                }
            }
            
            //janela.repaint();
        }
    }

    @Override
    public void atualizarCoordenadas() {
        super.atualizarCoordenadas();
        
        this.deltaX = (janela.getWidth() * 2) / 3;

        this.larguraTela = janela.getWidth()/3;
        this.alturaTela = janela.getHeight();
        //janela.repaint();
    }
    
    private void resetarTela() {
        resetar = true;
        
        this.mapaImagens.iniciaJogo();
        
        this.imagesOpcao = this.mapaImagens.getImageOpcao(Xadrez.getCampoSelecianado(), Xadrez.getPecaPega(), janela);
        this.larguraBotao = (this.alturaTela-(imagesOpcao.size()+1)*10) / imagesOpcao.size();
        
        //janela.repaint();
    }

    public static void setReset(boolean iniciado) {
        AmbienteOpcao.resetar = iniciado;
    }
}

