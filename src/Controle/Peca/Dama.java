/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle.Peca;

import Controle.Xadrez;
import Modelo.Campo;
import Modelo.Movimento;
import Visual.Plano.MapaImagens;
import java.util.ArrayList;

/**
 *
 * @author marcius
 */
public class Dama extends Peca{

    public Dama() {
        this.vidaTotal = 3;
        this.fatorVida = 1;
    }
    
    @Override
    public boolean pintaCampo(int pecaPega, Campo campoSelecionado, boolean selecionarCampo) {
    /**Cavalo apenas anda pelas faces com movimento de 
     * 2 face na mesma direcao depois virando
     * ou 1 face depois vira e segue 2 face em outra direcao
     */
        Campo[] movimentos = new Campo[8];
        movimentos[0] = campoSelecionado;
        movimentos[1] = campoSelecionado;
        movimentos[2] = campoSelecionado;
        movimentos[3] = campoSelecionado;
        movimentos[4] = campoSelecionado;
        movimentos[5] = campoSelecionado;
        movimentos[6] = campoSelecionado;
        movimentos[7] = campoSelecionado;
        ArrayList<Campo> passei = new ArrayList<Campo>();
        
        boolean movimento = false;
        boolean continua = false;
        for(int i=0; i<8; i++){
            passei.clear();
            int contaPasso=0;
            do{
                continua = false;
                switch(i){
                    case 0: try{ movimentos[0] = movimentos[0].getFaceAbaixoDireita(); }catch(Exception e){}
                        break;
                    case 1: try{ movimentos[1] = movimentos[1].getFaceAbaixoEsquerda(); }catch(Exception e){}
                        break;
                    case 2: try{ movimentos[2] = movimentos[2].getFaceAcimaDireita(); }catch(Exception e){}
                        break;
                    case 3: try{ movimentos[3] = movimentos[3].getFaceAcimaEsquerda(); }catch(Exception e){}
                        break;
                    case 4: try{ movimentos[4] = movimentos[4].getQuinaAbaixo(); }catch(Exception e){}
                        break;
                    case 5: try{ movimentos[5] = movimentos[5].getQuinaAcima(); }catch(Exception e){}
                        break;
                    case 6: try{ movimentos[6] = movimentos[6].getQuinaDireita(); }catch(Exception e){}
                        break;
                    case 7: try{ movimentos[7] = movimentos[7].getQuinaEsquerda(); }catch(Exception e){}
                        break;
                }
                if(movimentos[i] != null && !passei.contains(movimentos[i])){
                    //System.out.println("movimento: "+i);
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
                            movimento =  true;
                        }else{
                            if(campoSelecionado.peca2() != Xadrez.SOLDADO && campoSelecionado.peca2() != Xadrez.REI){
                                movimento = (pintaAmigo(pecaPega, campoSelecionado, movimentos[i], selecionarCampo) || movimento);
                            }
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
    
    private boolean pintaAmigo(int pecaPega, Campo campoSelecionado, Campo amigo, boolean selecionarCampo){
        if(!amigo.campoCheio()){
            if(campoSelecionado.campoAmigo(pecaPega, amigo)){//Verifca se as pecas nas faces acima são um amigo
                int pecaTipo = amigo.peca1();
                int[] parametros = new int[1];
                parametros[0] = (pecaPega == Movimento.PEGARP1)? campoSelecionado.vidaPeca1() : campoSelecionado.vidaPeca2();
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
        if(pecaTipo == Xadrez.SOLDADO){
            return true;
        }
                
        return pecaTipo == Xadrez.BISPO && parametros[0] < vidaTotal;
    }

    @Override
    public boolean amigo(int pecaPega, Campo campo) {
        /*int pecaTipo = -1;
        int[] parametros = new int[1];
        if(pecaPega == Movimento.PEGARP1){
            pecaTipo = campo.peca2();
            parametros[0] = campo.vidaPeca1();
        }else{
            pecaTipo = campo.peca1();
            parametros[0] = campo.vidaPeca2();
        }
        return (amigo(pecaTipo, parametros));*/
        return false;
    }

    @Override
    public boolean realizaMovimento(int pecaPega, Campo clicado, Campo destino) {
        boolean retorno = super.realizaMovimento(pecaPega, clicado, destino);
        if(destino.pecasAmigas()){
            if(destino.peca2() == Xadrez.SOLDADO){
                return false;
            }
        }
        
        return retorno;
    }
}
