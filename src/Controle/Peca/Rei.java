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
public class Rei extends Peca{

    public Rei() {
        this.vidaTotal = 1;
        this.fatorVida = 1;
    }
    
    @Override
    public boolean pintaCampo(int pecaPega, Campo campoSelecionado, boolean selecionarCampo) {
    /**Cavalo apenas anda pelas faces com movimento de 
     * 2 face na mesma direcao depois virando
     * ou 1 face depois vira e segue 2 face em outra direcao
     */
        Campo[] movimentos = new Campo[8];
        try{ movimentos[0] = campoSelecionado.getFaceAcimaDireita(); }catch(Exception e){}
        try{ movimentos[1] = campoSelecionado.getFaceAcimaEsquerda(); }catch(Exception e){}
        try{ movimentos[2] = campoSelecionado.getFaceAbaixoDireita(); }catch(Exception e){}
        try{ movimentos[3] = campoSelecionado.getFaceAbaixoEsquerda(); }catch(Exception e){}
        try{ movimentos[4] = campoSelecionado.getQuinaAbaixo(); }catch(Exception e){}
        try{ movimentos[5] = campoSelecionado.getQuinaAcima(); }catch(Exception e){}
        try{ movimentos[6] = campoSelecionado.getQuinaDireita(); }catch(Exception e){}
        try{ movimentos[7] = campoSelecionado.getQuinaEsquerda(); }catch(Exception e){}

        boolean movimento = false;
        for(int i=0; i<8; i++){
            if(movimentos[i] != null ){
                //System.out.println("movimento: "+i);
                if(movimentos[i].campoVazio()){//Campo vazio
                    if(selecionarCampo){
                        movimentos[i].setFundoVerde();
                    }else{
                        movimentos[i].setFundo();
                    }
                    movimento = true;
                }else if(!movimentos[i].campoCheio()){//Verifca se a peca acima é um inimigo
                    if(campoSelecionado.campoInimigo(pecaPega, movimentos[i]) && ((movimentos[i].vidaPeca1()/Xadrez.getTratadores()[movimentos[i].peca1()-1].fatorVida)==1)){
                        if(selecionarCampo){
                            movimentos[i].setFundoVermelho();
                        }else{
                            movimentos[i].setFundo();
                        }
                        movimento = true;
                    }else{
                        movimento = (pintaAmigo(pecaPega, campoSelecionado, movimentos[i], selecionarCampo) || movimento);
                    }
                }
            }
        }
        if(movimento){
            if(selecionarCampo){
                campoSelecionado.setFundoAmarelo();
            }else{
                campoSelecionado.setFundo();
            }
        }
            
        return movimento;
    }
    
    private boolean pintaAmigo(int pecaPega, Campo campoSelecionado, Campo amigo, boolean selecionarCampo){
        if(!amigo.campoCheio()){
            if(campoSelecionado.campoAmigo(pecaPega, amigo)){//Verifca se as pecas nas faces acima são um amigo
                int pecaTipo = amigo.peca1();
                int[] parametros = null;
                if(amigo(pecaTipo, parametros)){
                    if(selecionarCampo){
                        amigo.setFundoAzul();
                    }else{
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
        return pecaTipo == Xadrez.CAVALEIRO || pecaTipo == Xadrez.DAMA || pecaTipo == Xadrez.TESTUDO;
    }

    @Override
    public boolean amigo(int pecaPega, Campo campo) {return false;}
        /*
        int pecaTipo = -1;
        int[] parametros = null;
        if(pecaPega == Movimento.PEGARP1){
            pecaTipo = campo.peca1();
        }else{
            pecaTipo = campo.peca2();
        }
        return (amigo(pecaTipo, parametros));
    }*/

    @Override
    public boolean realizaMovimento(int pecaPega, Campo clicado, Campo destino) {
        boolean retorno = super.realizaMovimento(pecaPega, clicado, destino);
        if(destino.pecasAmigas()){
            if(destino.peca2() == Xadrez.CAVALEIRO){
                destino.moverPecaFrente();
                destino.peca1Vida(destino.peca2()); //isso eh um pog para colocar a peca junto com o cavalo
                destino.matarPeca2();
                return true;
            }else if(destino.peca2() == Xadrez.DAMA && !Xadrez.existePeca(destino.jogador1(), Xadrez.PRINCIPE)){
                return false;
            }else if(destino.peca2() == Xadrez.TESTUDO){
                return false;
            }
        }
        
        return retorno;
    }
}
