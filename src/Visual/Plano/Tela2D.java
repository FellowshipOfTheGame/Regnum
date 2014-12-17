/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual.Plano;

import Visual.Plano.Ambientes.AmbienteOutroJogagador;
import Visual.Plano.Ambientes.AmbienteSala;
import Visual.Plano.Ambientes.AmbienteVencedor;
import Visual.Plano.Ambientes.AmbienteTime;
import Visual.Plano.Ambientes.AmbienteJogo;
import Visual.Plano.Ambientes.Ambiente;
import Visual.Plano.Ambientes.AmbienteOpcao;
import Visual.Plano.Utilidades.MapaImagens;
import Controle.Controle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;

/**
 *
 * @author marcius
 */
public class Tela2D extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    private Controle controle;
    private static String msg;

    private static Map<Integer, Ambiente> ambientes;

    public Tela2D() {
        this.controle = Controle.instanciaControle();

        ambientes = new HashMap<Integer, Ambiente>();

        super.addMouseListener(this);
        super.addMouseWheelListener(this);
        super.addMouseMotionListener(this);
        super.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                for (Ambiente a : ambientes.values()) {
                    a.atualizarCoordenadas();
                }

            }

        });
    }

    public void iniciarTela() {
        Integer opcao = (Integer) controle.getEstado();

        ambientes.get(opcao).setInciarTela();
    }

    @Override
    protected void paintComponent(Graphics gG) {
        draw(gG);
        super.paintComponent(gG);
    }

    private void draw(Graphics gG) {
        Graphics2D g2d = (Graphics2D) gG;

        Integer opcao = (Integer) controle.getEstado();
        Ambiente a = ambientes.get(opcao);
        if (a != null) {
            a.desenha(g2d);
        }

        if (msg != null) {
            g2d.drawImage(MapaImagens.getBalao(), 0, 0, this);
            /*try {
                Font f = Font.createFont(Font.TRUETYPE_FONT, new FileFont(new File("font/VeniceClassic.ttf")) {
                    @Override
                    protected void close() {
                        
                    }

                    @Override
                    ByteBuffer readBlock(int i, int i1) {
                        
                    }

                    @Override
                    protected FontScaler getScaler() {
                        
                    }

                    @Override
                    CharToGlyphMapper getMapper() {
                        
                    }
                });
                f.deriveFont(Font.BOLD, 24f);
                g2d.setFont(f);
                //Até aqui tudo funciona perfeitamente
            } catch (FontFormatException ex) {
            } catch (IOException ex) {
            }*/
            g2d.drawString(msg, 50, 50);
        }
    }

    public static void aviso(String m) {
        msg = m;
    }

    public static void desligaAviso() {
        msg = null;
    }

    public void mouseClicked(MouseEvent e) {
        Integer opcao = (Integer) controle.getEstado();
        ambientes.get(opcao).mouseClicked(e);
    }

    public void mousePressed(MouseEvent e) {
        Integer opcao = (Integer) controle.getEstado();

        ambientes.get(opcao).mousePressed(e);
    }

    public void mouseReleased(MouseEvent e) {
        Integer opcao = (Integer) controle.getEstado();
        ambientes.get(opcao).mouseReleased(e);
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        Integer opcao = (Integer) controle.getEstado();
        ambientes.get(opcao).mouseDragged(e);
    }

    public void mouseMoved(MouseEvent e) {
        Integer opcao = (Integer) controle.getEstado();
        ambientes.get(opcao).mouseMoved(e);
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        Integer opcao = (Integer) controle.getEstado();
        ambientes.get(opcao).mouseWheelMoved(e);
    }

    public void setInicio() {
        ambientes.put((Integer) Controle.INICIANDO, new Ambiente(this));
    }

    public void setJogo() {
        ambientes.put((Integer) Controle.JOGANDO, new AmbienteJogo(this));
    }

    public void setVencedor() {
        ambientes.put((Integer) Controle.VENCEDOR, new AmbienteVencedor(this));
    }

    public void setOpcao() {
        ambientes.put((Integer) Controle.OPCAO_CAMPO, new AmbienteOpcao(this));
    }

    public void setSala() {
        ambientes.put((Integer) Controle.SALA, new AmbienteSala(this));
    }

    public void setTime() {
        ambientes.put((Integer) Controle.CONFIGURANDO, new AmbienteTime(this));
    }

    public void setOutroJogador() {
        ambientes.put((Integer) Controle.OUTRO_JOGANDO, new AmbienteOutroJogagador(this));
    }
}
