/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle.Peca;

import Controle.Xadrez;
import Modelo.Campo;
import Modelo.Movimento;
import java.util.ArrayList;

/**
 *
 * @author marcius
 */
public class Bispo extends Peca{

    public Bispo() {
        this.vidaTotal = 2;
        this.fatorVida = 1;
    }
    
    
    @Override
    public boolean pintaCampo(int pecaPega, Campo campoSelecionado, boolean selecionarCampo) {
    /**Cavalo apenas anda pelas faces com movimento de 
     * 2 face na mesma direcao depois virando
     * ou 1 face depois vira e segue 2 face em outra direcao
     */
        Campo[] movimentos = new Campo[4];
        movimentos[0] = campoSelecionado;
        movimentos[1] = campoSelecionado;
        movimentos[2] = campoSelecionado;
        movimentos[3] = campoSelecionado;
        ArrayList<Campo> passei = new ArrayList<Campo>();
        
        boolean movimento = false;
        boolean continua = false;
        for(int i=0; i<4; i++){
            passei.clear();
            int contaPasso = 0;
            do{
                continua = false;
                switch(i){
                    case 0: try{ movimentos[0] = movimentos[0].getQuinaAbaixo(); }catch(Exception e){}
                        break;
                    case 1: try{ movimentos[1] = movimentos[1].getQuinaAcima(); }catch(Exception e){}
                        break;
                    case 2: try{ movimentos[2] = movimentos[2].getQuinaDireita(); }catch(Exception e){}
                        break;
                    case 3: try{ movimentos[3] = movimentos[3].getQuinaEsquerda(); }catch(Exception e){}
                        break;
                }
                if(movimentos[i] != null && !passei.contains(movimentos[i])){
                    if(movimentos[i].campoVazio()){//Campo vazio
                        if(selecionarCampo){
                            movimentos[i].setFundoVerde();
                        }else{
                            movimentos[i].setFundo();
                        }
                        passei.add(movimentos[i]);
                        movimento = true;
                        continua = true;
                        contaPasso++;
                    }else if(!movimentos[i].campoCheio()){//Verifca se a peca acima é um inimigo
                        if(campoSelecionado.campoInimigo(pecaPega, movimentos[i])){
                            if(selecionarCampo){
                                movimentos[i].setFundoVermelho();
                            }else{
                                movimentos[i].setFundo();
                            }
                            movimento = true;
                        }else{
                            movimento = (pintaAmigo(pecaPega, campoSelecionado, movimentos[i], selecionarCampo) || movimento);
                        }
                        passei.add(movimentos[i]);
                        contaPasso++;
                    }   
                }
            }while(continua && contaPasso < 9);
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
    
    private boolean pintaAmigo(int pecaPega, Campo campoSelecionado, Campo amigo, boolean selecionarCampo) {
        if(!amigo.campoCheio()){
            if(campoSelecionado.campoAmigo(pecaPega, amigo)){//Verifca se as pecas nas faces acima são um amigo
                int pecaTipo = amigo.peca1();
                int[] parametros = new int[4];
                parametros[0] = amigo.getColuna();
                parametros[1] = amigo.getLinha();
                parametros[2] = amigo.vidaPeca1();
                parametros[3] = (pecaPega == Movimento.PEGARP1)? campoSelecionado.vidaPeca1() : campoSelecionado.vidaPeca2();
                
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
    
    public boolean amigo(int pecaTipo, int[] parametros) {
        if(pecaTipo == Xadrez.SOLDADO && parametros[0] == 0 && parametros[1] == 0){
            return true;
        }

        if(parametros[2] < Xadrez.getTratadores()[pecaTipo-1].vidaTotal){
            return pecaTipo != Xadrez.TESTUDO;
        }
        
        return pecaTipo == Xadrez.BISPO && parametros[3] < vidaTotal;
    }
    
    public boolean amigo(int pecaPega, Campo campo){
        int pecaTipo; 
        int[] parametros = new int[4];
        if(pecaPega == Movimento.PEGARP1){
            pecaTipo = campo.peca2();
            parametros[2] = campo.vidaPeca2();
            parametros[3] = campo.vidaPeca1();
        }else{
            return false;
        }
        parametros[0] = campo.getColuna();
        parametros[1] = campo.getLinha();
        return amigo(pecaTipo, parametros);
    }

    @Override
    public boolean realizaMovimento(int pecaPega, Campo clicado, Campo destino) {
        boolean retorno = super.realizaMovimento(pecaPega, clicado, destino);
        if(destino.pecasAmigas()){
            if(destino.peca2() == Xadrez.SOLDADO && destino.getLinha() == 0 && destino.getColuna() == 0){
                return false;
            }
        }
        
        return retorno;
    }
    
    @Override
    public boolean realizaIteracao(int pecaPega, Campo clicado){
        if(pecaPega == Movimento.INTERAGIR1 && clicado.vidaPeca2() < Xadrez.getTratadores()[clicado.peca2()-1].vidaTotal){
            int cura = (Xadrez.getTratadores()[clicado.peca2()-1].vidaTotal - clicado.vidaPeca2() < Xadrez.getTratadores()[clicado.peca2()-1].fatorVida)? Xadrez.getTratadores()[clicado.peca2()-1].vidaTotal - clicado.vidaPeca2(): Xadrez.getTratadores()[clicado.peca2()-1].fatorVida;
            clicado.peca2Vida(1*cura);
            return (false);
        }else if(pecaPega == Movimento.INTERAGIR1 && clicado.vidaPeca1() < Xadrez.getTratadores()[clicado.peca1()-1].vidaTotal){
            int cura = (Xadrez.getTratadores()[clicado.peca1()-1].vidaTotal - clicado.vidaPeca1() < Xadrez.getTratadores()[clicado.peca1()-1].fatorVida)? Xadrez.getTratadores()[clicado.peca1()-1].vidaTotal - clicado.vidaPeca1(): Xadrez.getTratadores()[clicado.peca1()-1].fatorVida;
            clicado.peca1Vida(1*cura);
            clicado.moverPecaFrente();
            return (false);
        }
        
        return true;
    }
}
