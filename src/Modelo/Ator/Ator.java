package Modelo.Ator;

import Controle.Controle;
import java.awt.Color;
import java.awt.Event;

/**
 *
 * @author marcius
 */
public class Ator {
    protected Controle controle;
    protected String time;
    protected Color cor;
    protected int ordem;

    public Ator(Controle controle, String time, Color cor) {
        this.controle = controle;
        this.time = time;
        this.cor = cor;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }
    
    public String getTime() {
        return time;
    }

    public Color getCor() {
        return cor;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public int getOrdem() {
        return ordem;
    }
    
}
