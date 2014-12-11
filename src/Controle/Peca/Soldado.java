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
public class Soldado extends Peca {

    public Soldado() {
        this.vidaTotal = 1;
        this.fatorVida = 1;
    }

    @Override
    public boolean pintaCampo(int pecaPega, Campo campoSelecionado, boolean selecionarCampo) {
        boolean selecionado = false;

        if (campoSelecionado.getQuinaAcima() != null) {
            if (campoSelecionado.getQuinaAcima().campoVazio()) {//Campo vazio
                if (selecionarCampo) {
                    campoSelecionado.setFundoAmarelo();
                    campoSelecionado.getQuinaAcima().setFundoVerde();
                } else {
                    campoSelecionado.setFundo();
                    campoSelecionado.getQuinaAcima().setFundo();
                }
                selecionado = true;
            } else if (!campoSelecionado.getQuinaAcima().campoCheio()) {
                selecionado = (pintaAmigo(pecaPega, campoSelecionado, campoSelecionado.getQuinaAcima(), selecionarCampo));
            }
        }

        selecionado = pintaInimigo(pecaPega, campoSelecionado, campoSelecionado.getFaceAcimaDireita(), selecionarCampo) || selecionado;
        selecionado = pintaInimigo(pecaPega, campoSelecionado, campoSelecionado.getFaceAcimaEsquerda(), selecionarCampo) || selecionado;

        return selecionado;
    }

    private boolean pintaInimigo(int pecaPega, Campo campoSelecionado, Campo campoInimigo, boolean selecionarCampo) {
        if (campoInimigo != null && !campoInimigo.campoCheio()) {//Verifca se a peca acima é um inimigo
            if (campoSelecionado.campoInimigo(pecaPega, campoInimigo)) {
                if (selecionarCampo) {
                    campoSelecionado.setFundoAmarelo();
                    campoInimigo.setFundoVermelho();
                } else {
                    campoSelecionado.setFundo();
                    campoInimigo.setFundo();
                }
                return true;
            }
        } 

        return false;
    }

    private boolean pintaAmigo(int pecaPega, Campo campoSelecionado, Campo amigo, boolean selecionarCampo) {
        if (campoSelecionado.campoAmigo(pecaPega, amigo)) {//Verifca se as pecas nas faces acima são um amigo
            if (!amigo.campoCheio()) {
                int pecaTipo = amigo.peca1();
                int[] parametro = new int[3];
                parametro[0] = amigo.getColuna();
                parametro[1] = amigo.getLinha();
                parametro[2] = amigo.vidaPeca1();
                if (amigo(pecaTipo, parametro)) {
                    if (selecionarCampo) {
                        campoSelecionado.setFundoAmarelo();
                        campoSelecionado.getQuinaAcima().setFundoAzul();
                    } else {
                        campoSelecionado.setFundo();
                        campoSelecionado.getQuinaAcima().setFundo();
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean amigo(int pecaTipo, int[] parametros) {
        return (pecaTipo == Xadrez.CAVALEIRO && parametros[2]==Xadrez.getTratadores()[Xadrez.CAVALEIRO-1].vidaTotal) || (pecaTipo == Xadrez.BISPO && parametros[0] == 0 && parametros[1] == 0);
    }

    @Override
    public boolean amigo(int pecaPega, Campo campo) {
        /*int pecaTipo;
        int[] parametro = new int[2];
        if (pecaPega == Movimento.PEGARP1) {
            pecaTipo = campo.peca2();
        } else {
            pecaTipo = campo.peca1();
        }
        parametro[0] = campo.getColuna();
        parametro[1] = campo.getLinha();
        return (amigo(pecaTipo, parametro));
        if (pecaPega == Movimento.PEGARP1) {
            return campo.peca2() == Xadrez.CAVALEIRO;
        } else {
            return campo.peca1() == Xadrez.CAVALEIRO;
        }*/
        return false;
    }

    @Override
    public boolean realizaMovimento(int pecaPega, Campo clicado, Campo destino) {
        boolean retorno = super.realizaMovimento(pecaPega, clicado, destino);
        if (destino.pecasAmigas()) {
            if (destino.peca2() == Xadrez.BISPO && destino.getLinha() == 0 && destino.getColuna() == 0) {
                destino.moverPecaFrente();
                return false;
            }else if(destino.peca2() == Xadrez.CAVALEIRO){
                destino.moverPecaFrente();
                return false;
            }
        }

        return retorno;
    }

}
