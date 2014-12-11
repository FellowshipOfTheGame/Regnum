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
public class Cavaleiro extends Peca {

    public Cavaleiro() {
        this.vidaTotal = 1;
        this.fatorVida = 1;
    }

    @Override
    public boolean pintaCampo(int pecaPega, Campo campoSelecionado, boolean selecionarCampo) {
        /**
         * Cavalo apenas anda pelas faces com movimento de 2 face na mesma
         * direcao depois virando ou 1 face depois vira e segue 2 face em outra
         * direcao
         */
        Campo[] movimentos = new Campo[8];
        try {
            movimentos[0] = campoSelecionado.getFaceAcimaDireita().getFaceAcimaDireita().getFaceAcimaEsquerda();
        } catch (Exception e) {
        }
        try {
            movimentos[1] = campoSelecionado.getFaceAcimaEsquerda().getFaceAcimaEsquerda().getFaceAcimaDireita();
        } catch (Exception e) {
        }
        try {
            movimentos[2] = campoSelecionado.getFaceAcimaDireita().getFaceAcimaDireita().getFaceAbaixoDireita();
        } catch (Exception e) {
        }
        try {
            movimentos[3] = campoSelecionado.getFaceAcimaEsquerda().getFaceAcimaEsquerda().getFaceAbaixoEsquerda();
        } catch (Exception e) {
        }
        try {
            movimentos[4] = campoSelecionado.getFaceAbaixoDireita().getFaceAbaixoDireita().getFaceAbaixoEsquerda();
        } catch (Exception e) {
        }
        try {
            movimentos[5] = campoSelecionado.getFaceAbaixoEsquerda().getFaceAbaixoEsquerda().getFaceAbaixoDireita();
        } catch (Exception e) {
        }
        try {
            movimentos[6] = campoSelecionado.getFaceAbaixoDireita().getFaceAbaixoDireita().getFaceAcimaDireita();
        } catch (Exception e) {
        }
        try {
            movimentos[7] = campoSelecionado.getFaceAbaixoEsquerda().getFaceAbaixoEsquerda().getFaceAcimaEsquerda();
        } catch (Exception e) {
        }

        boolean movimento = false;
        for (int i = 0; i < 8; i++) {
            if (movimentos[i] != null) {
                if (movimentos[i].campoVazio()) {//Campo vazio
                    if (selecionarCampo) {
                        movimentos[i].setFundoVerde();
                    } else {
                        movimentos[i].setFundo();
                    }
                    //System.out.println("f: "+movimentos[i].getFace()+" l: "+movimentos[i].getLinha()+" c: "+movimentos[i].getColuna());
                    movimento = true;
                } else if (!movimentos[i].campoCheio()) {//Verifca se a peca acima é um inimigo
                    if (campoSelecionado.campoInimigo(pecaPega, movimentos[i])) {
                        if (selecionarCampo) {
                            movimentos[i].setFundoVermelho();
                        } else {
                            movimentos[i].setFundo();
                        }
                        movimento = true;
                    } else {
                        movimento = (pintaAmigo(pecaPega, campoSelecionado, movimentos[i], selecionarCampo) || movimento);
                    }
                } 
            }
        }
        if (movimento) {
            if (selecionarCampo) {
                campoSelecionado.setFundoAmarelo();
            } else {
                campoSelecionado.setFundo();
            }
        }

        return movimento;
    }

    private boolean pintaAmigo(int pecaPega, Campo campoSelecionado, Campo amigo, boolean selecionarCampo) {
        if (!amigo.campoCheio()) {
            if (campoSelecionado.campoAmigo(pecaPega, amigo)) {//Verifca se as pecas nas faces acima são um amigo
                int pecaTipo = amigo.peca1();
                int[] parametros = new int[1];
                parametros[0] = (pecaPega == Movimento.PEGARP1)? campoSelecionado.vidaPeca1() : campoSelecionado.vidaPeca2();
                if (amigo(pecaTipo, parametros)) {
                    if (selecionarCampo) {
                        amigo.setFundoAzul();
                    } else {
                        amigo.setFundo();
                    }
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean amigo(int pecaTipo, int[] parametros) {
        return (parametros[0]==vidaTotal && (pecaTipo == Xadrez.REI || pecaTipo == Xadrez.PRINCIPE || pecaTipo == Xadrez.SOLDADO));
    }

    public boolean amigo(int pecaPega, Campo campo) {
        int pecaTipo;
        int[] parametros = new int[1];
        
        if (pecaPega == Movimento.PEGARP1) {
            pecaTipo = campo.peca2();
            parametros[0] = campo.vidaPeca1();
        } else {
            pecaTipo = campo.peca1();
            parametros[0] = campo.vidaPeca2();
        }
        return (amigo(pecaTipo, parametros));
    }

    @Override
    public boolean realizaMovimento(int pecaPega, Campo clicado, Campo destino) {
        boolean retorno = super.realizaMovimento(pecaPega, clicado, destino);
        if (destino.pecasAmigas()) {
            if (destino.peca2() == Xadrez.REI || destino.peca2() == Xadrez.PRINCIPE) {
                destino.peca1Vida(destino.peca2()); //isso eh um pog para colocar a peca junto com o cavalo
                destino.matarPeca2();
            }else if(destino.peca2() == Xadrez.SOLDADO){
                return false;
            }
        }

        return retorno;
    }

    @Override
    public boolean realizaIteracao(int pecaPega, Campo clicado) {
        /*int pecaTipo = -1;
        if (pecaPega == Movimento.INTERAGIR1) {
            pecaTipo = clicado.peca2();
        }else if (pecaPega == Movimento.INTERAGIR2) {
            pecaTipo = clicado.peca1();
            clicado.moverPecaFrente();
        }*/
        
        if ((clicado.peca2() == Xadrez.SOLDADO || clicado.peca2() == Xadrez.PRINCIPE || clicado.peca2() == Xadrez.REI) && clicado.vidaPeca1()==vidaTotal) {
            clicado.peca1Vida(clicado.peca2()); //isso eh um pog para colocar a peca junto com o cavalo
            clicado.matarPeca2();
            return true;
        }else if(clicado.vidaPeca1() > vidaTotal){
            int pecaMontadaNoCavalo = clicado.getId();
            //a peca que estava nas costas do cavalo era sua vida
            clicado.addJogador2((pecaMontadaNoCavalo %1000000)/100000);
            //as pecas que montam no cavalo sempre tem 1 de vida
            clicado.addPeca2(((pecaMontadaNoCavalo %10000)/1000) - 1, 1);
            clicado.peca1Vida(-(((pecaMontadaNoCavalo %10000)/1000) - 1));
            return false;
        }

        return false;
    }
}
