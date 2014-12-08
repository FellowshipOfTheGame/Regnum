/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle.Peca;

import Controle.Xadrez;
import Modelo.Campo;
import Modelo.Movimento;

/**
 *
 * @author marcius
 */
public abstract class Peca {

    /**
     * fator que pondera a atribuicao de vida ou diminuicao da vida
     */
    protected int fatorVida;
    protected int vidaTotal;

    public Peca() {
    }

    public abstract boolean pintaCampo(int pecaPega, Campo campoSelecionado, boolean selecionarCampo);

    public boolean pintaCampo(int pecaPega, Campo campoSelecionado, boolean selecionarCampo, int parametro) {
        return true;
    }

    /**
     * @param pecaPega, a peca que esta sendo tratada
     * @param campo, campo que esta sendo selecioando
     * @return true se existe interacao entre as pecas do mesmo campo
     */
    public abstract boolean amigo(int pecaPega, Campo campo);

    public abstract boolean amigo(int pecaTipo, int[] parametros);

    /**
     *
     * @param pecaPega peca que foi pega no campo;
     * @param clicado campo de origem do movimento;
     * @param destino campo de destino do movimento;
     * @return retorna verdadeiro quando o movimento foi terminado, e falso
     * quando eh necessario um complemento apos o movimento, ou seja, quando o
     * proximo movimento do jogador sera obrigatorio;
     */
    public boolean realizaMovimento(int pecaPega, Campo clicado, Campo destino) {
        if (pecaPega == Movimento.PEGARP1) {
            clicado.moverPeca1Destino(destino);
        } else {
            clicado.moverPeca2Destino(destino);
        }
        if (destino.pecasInimigas()) {
            int dano = Xadrez.getTratadores()[destino.peca2() - 1].fatorVida;
            dano = (destino.peca1() == Xadrez.CAVALEIRO && destino.vidaPeca1() > 1) ? dano * 2 : dano;//faz com que alguem montado no cavalo de dano em dobro
            if (destino.peca2() == Xadrez.TESTUDO) {
                for (int i = 0; i < dano; i++) {
                    Testudo.diminuirVida(destino);
                }
            } else if (destino.peca2() == Xadrez.CAVALEIRO && destino.vidaPeca2() > 1) {
                int pecaMontadaNoCavalo = destino.getId() % 1000;
                destino.matarPeca2();
                destino.addJogador2(pecaMontadaNoCavalo / 100);
                //as pecas que montam no cavalo sempre tem 1 de vida
                //a peça que estava nas costas do cavalo era sua vida
                destino.addPeca2((pecaMontadaNoCavalo % 10) - 1, 1);
            } else {
                destino.peca2Vida(-1 * dano);
            }
        } else if (destino.pecasAmigas() && destino.peca2() == Xadrez.BISPO && destino.vidaPeca1() < vidaTotal) {
            destino.moverPecaFrente();
            return false;
        }

        return true;
    }

    public boolean realizaIteracao(int pecaPega, Campo clicado) {
        return true;
    }

    public void realizaAcao(Campo clicado) {
        if (clicado.pecasInimigas()) {
            clicado.peca2Vida(-1 * Xadrez.getTratadores()[clicado.peca2() - 1].fatorVida);
        }
    }

    public int getVidaTotal() {
        return vidaTotal;
    }

    public int getFatorVida() {
        return fatorVida;
    }

}
