/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle.Ator;

import Controle.Controle;
import java.awt.Color;

/**
 *
 * @author marcius
 */
public class AtorRede extends Ator{

    public AtorRede(Controle controle, String time, Color cor) {
        super(controle, time, cor);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }
}
