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
public class Testudos extends Peca{

    public Testudos() {
        this.fatorVida = 2;
        this.vidaTotal = 4;
    }
    
    public boolean pintaCampo(int pecaPega, Campo campoSelecionado, boolean selecionarCampo) {
        Campo[] movimentos = new Campo[4];
        movimentos[0] = campoSelecionado;
        movimentos[1] = campoSelecionado;
        movimentos[2] = campoSelecionado;
        movimentos[3] = campoSelecionado;
        ArrayList<Campo> passei = new ArrayList<Campo>();
        
        boolean movimento = false;
        boolean continua;
        for(int i=0; i<4; i++){
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

    @Override
    public boolean pintaCampo(int pecaPega, Campo campoSelecionado, boolean selecionarCampo, int parametro) {
    /**Cavalo apenas anda pelas faces com movimento de 
     * 2 face na mesma direcao depois virando
     * ou 1 face depois vira e segue 2 face em outra direcao
     */
        Campo[] movimentos = new Campo[4];
        movimentos[0] = campoSelecionado;
        movimentos[1] = campoSelecionado;
        movimentos[2] = campoSelecionado;
        movimentos[3] = campoSelecionado;
        
        boolean[] abrir = new boolean[4];
        for(int j=0; j<4; j++){
            abrir[j] = true;
        }
        
        boolean movimento=false;
            
        for(int i=0; i<parametro-1; i++){
            if(abrir[0]) try{ movimentos[0] = movimentos[0].getFaceAbaixoDireita(); }catch(Exception e){}
            if(abrir[1]) try{ movimentos[1] = movimentos[1].getFaceAbaixoEsquerda(); }catch(Exception e){}
            if(abrir[2]) try{ movimentos[2] = movimentos[2].getFaceAcimaDireita(); }catch(Exception e){}            
            if(abrir[3]) try{ movimentos[3] = movimentos[3].getFaceAcimaEsquerda(); }catch(Exception e){}
            for(int j=0; j<4; j++){
                if(abrir[j] && movimentos[j] != null){
                    abrir[j] = movimentos[j].campoVazio();   
                }else{
                    abrir[j] = false;
                }
            }
        }
        for(int j=0; j<4; j++){
            if(abrir[j]){
                movimento = true;
                if(selecionarCampo){
                    campoSelecionado.setFundoAmarelo();
                    movimentos[j].setFundoVerde();
                }else{
                    campoSelecionado.setFundo();
                    movimentos[j].setFundo();
                }
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
        return (pecaTipo == Xadrez.BISPO && parametros[0] < vidaTotal);
    }

    @Override
    public boolean amigo(int pecaPega, Campo campo) {
        /*int pecaTipo;
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

}
