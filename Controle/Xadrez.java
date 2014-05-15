/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Controle.Ator.Ator;
import Modelo.Campo;
import Modelo.Peca.Bispo;
import Modelo.Peca.Cavaleiro;
import Modelo.Peca.Dama;
import Modelo.Peca.Peca;
import Modelo.Peca.Principe;
import Modelo.Peca.Rei;
import Modelo.Peca.Soldado;
import Modelo.Peca.Testudo;
import Modelo.Tabuleiro;

/**
 *
 * @author marcius
 */
public class Xadrez {
    private static Tabuleiro tabuleiro;
    private static Campo campoSelecionado;
    private static boolean temCampoSelecionado;
    
    /**
     * Tratadores das peças presente nos campos
     */
    private static Peca[] tratadores;
    private static final int REI = 0;
    private static final int DAMA = 1;
    private static final int PRINCIPE = 2;
    private static final int BISPO = 3;
    private static final int CAVALEIRO = 4;
    private static final int TESTUDOS = 5;
    private static final int SOLDADO = 6;
    private static final int TESTUDO = 7;
    
    public Xadrez(int iJogadores) {
        tabuleiro = new Tabuleiro(iJogadores);
        
        tratadores = new Peca[7];
        tratadores[REI] = new Rei();
        tratadores[DAMA] = new Dama();
        tratadores[PRINCIPE] = new Principe();
        tratadores[BISPO] = new Bispo();
        tratadores[CAVALEIRO] = new Cavaleiro();
        tratadores[TESTUDOS] = new Testudo();
        tratadores[SOLDADO] = new Soldado();
    }

    public boolean selecionarCampo(int face, int linha, int coluna, int usuario) {
        campoSelecionado = tabuleiro.campoSelecionado(face, linha, coluna);
        temCampoSelecionado = false;
        
        if(usuario == campoSelecionado.jogador1() && campoSelecionado.peca1() > 0){
            temCampoSelecionado = true;
            tratadores[campoSelecionado.peca1()-1].selecionarCampo(usuario, campoSelecionado);
        }else if (usuario == campoSelecionado.jogador2() && campoSelecionado.peca2() > 0){
            temCampoSelecionado = true;
            tratadores[campoSelecionado.peca2()-1].selecionarCampo(usuario, campoSelecionado);
        }
        
        return temCampoSelecionado;
    }

    public boolean temCampoSelecionado() {
        return temCampoSelecionado;
    }

    public boolean validaCampo(int face, int linha, int coluna) {
        return false;
    }
    
    public Campo[] getCampos() {
        return tabuleiro.getCampos();
    }
    
    public static Campo getCampoSelecianado(){
        return campoSelecionado;
    }
}
