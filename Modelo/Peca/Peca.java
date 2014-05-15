/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Peca;

import Modelo.Campo;

/**
 *
 * @author marcius
 */
public abstract class Peca {

    public abstract Campo selecionarCampo(int usuario, int peca);

    public void selecionarCampo(int usuario, Campo campoSelecionado) {
        System.out.println("peca f: " + campoSelecionado.getFace() + " l: " + campoSelecionado.getLinha() + " c: " + campoSelecionado.getColuna());
        //TODO fazer a thread que passa fazendo a selecao de acordo com o movimento da peca
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
