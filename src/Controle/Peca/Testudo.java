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
public class Testudo extends Peca{
    public Testudo() {
        this.fatorVida = 1;
        this.vidaTotal = 4;
    }
    
    @Override
    public boolean pintaCampo(int pecaPega, Campo campoSelecionado, boolean selecionarCampo){        
        int movimento=4;
        if(campoSelecionado.getFaceAbaixoDireita() != null && (campoSelecionado.getFaceAbaixoDireita().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAbaixoDireita().peca2() == Xadrez.TESTUDO)){
            movimento = 0;
        }else if(campoSelecionado.getColuna()!=0 && (campoSelecionado.getFaceAcimaEsquerda().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAcimaEsquerda().peca2() == Xadrez.TESTUDO)){
            movimento = 0;
        }else{
            if(campoSelecionado.getFaceAbaixoEsquerda() != null && (campoSelecionado.getFaceAbaixoEsquerda().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAbaixoEsquerda().peca1() == Xadrez.TESTUDO)){
                movimento = 1;
            }else if(campoSelecionado.getColuna()!=campoSelecionado.getLinha() && (campoSelecionado.getFaceAcimaDireita().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAcimaDireita().peca2() == Xadrez.TESTUDO)){
                movimento = 1;
            }
        }
        
        boolean stop=false;
        do{
            switch(movimento){
                case 0: 
                    try{ 
                        if(campoSelecionado.getFaceAbaixoDireita()!=null && (campoSelecionado.getFaceAbaixoDireita().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAbaixoDireita().peca2() == Xadrez.TESTUDO)){
                            campoSelecionado = campoSelecionado.getFaceAbaixoDireita();
                        } else if(campoSelecionado.getColuna()==campoSelecionado.getLinha() && (campoSelecionado.getFaceAcimaDireita().peca1() == Xadrez.TESTUDO ||campoSelecionado.getFaceAcimaDireita().peca2() == Xadrez.TESTUDO)){
                            campoSelecionado = campoSelecionado.getFaceAcimaDireita();
                        } else{
                            stop=true;
                        }
                    }catch(Exception e){}
                    break;
                case 1: 
                    try{ 
                        if(campoSelecionado.getFaceAbaixoEsquerda()!=null && (campoSelecionado.getFaceAbaixoEsquerda().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAbaixoEsquerda().peca2() == Xadrez.TESTUDO)){
                            campoSelecionado = campoSelecionado.getFaceAbaixoEsquerda();
                        } else if(campoSelecionado.getColuna()==0 && (campoSelecionado.getFaceAcimaEsquerda().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAcimaEsquerda().peca2() == Xadrez.TESTUDO)){
                            campoSelecionado = campoSelecionado.getFaceAcimaEsquerda();
                        } else {
                            stop=true;
                        }
                    }catch(Exception e){}
                    break;
            }
        }while(!stop);
        
        boolean move = false;
        stop = false;
        do{
            if(selecionarCampo){
                if(campoSelecionado.getFaceAbaixoDireita() != null) if(campoSelecionado.getFaceAbaixoDireita().campoVazio()){ campoSelecionado.getFaceAbaixoDireita().setFundoVerde(); move=true;}
                if(campoSelecionado.getFaceAbaixoEsquerda()!= null) if(campoSelecionado.getFaceAbaixoEsquerda().campoVazio()){ campoSelecionado.getFaceAbaixoEsquerda().setFundoVerde(); move=true;}
                if(campoSelecionado.getFaceAcimaDireita() != null) if(campoSelecionado.getFaceAcimaDireita().campoVazio()){ campoSelecionado.getFaceAcimaDireita().setFundoVerde(); move=true;}
                if(campoSelecionado.getFaceAcimaEsquerda() != null) if(campoSelecionado.getFaceAcimaEsquerda().campoVazio()){ campoSelecionado.getFaceAcimaEsquerda().setFundoVerde(); move=true;}
                if(campoSelecionado.getQuinaAbaixo() != null) if(campoSelecionado.getQuinaAbaixo().campoVazio()){ campoSelecionado.getQuinaAbaixo().setFundoVerde(); move=true;}
                if(campoSelecionado.getQuinaEsquerda() != null) if(campoSelecionado.getQuinaEsquerda().campoVazio()){ campoSelecionado.getQuinaEsquerda().setFundoVerde(); move=true;}
                if(campoSelecionado.getQuinaDireita() != null) if(campoSelecionado.getQuinaDireita().campoVazio()){ campoSelecionado.getQuinaDireita().setFundoVerde(); move=true;}
                if(campoSelecionado.getQuinaAcima() != null) if(campoSelecionado.getQuinaAcima().campoVazio()){ campoSelecionado.getQuinaAcima().setFundoVerde(); move=true;}
            }else{
                if(campoSelecionado.getFaceAbaixoDireita() != null) if(campoSelecionado.getFaceAbaixoDireita().campoVazio()){ campoSelecionado.getFaceAbaixoDireita().setFundo(); move=true;}
                if(campoSelecionado.getFaceAbaixoEsquerda() != null) if(campoSelecionado.getFaceAbaixoEsquerda().campoVazio()){ campoSelecionado.getFaceAbaixoEsquerda().setFundo(); move=true;}
                if(campoSelecionado.getFaceAcimaDireita() != null) if(campoSelecionado.getFaceAcimaDireita().campoVazio()){ campoSelecionado.getFaceAcimaDireita().setFundo(); move=true;}
                if(campoSelecionado.getFaceAcimaEsquerda() != null) if(campoSelecionado.getFaceAcimaEsquerda().campoVazio()){ campoSelecionado.getFaceAcimaEsquerda().setFundo(); move=true;}
                if(campoSelecionado.getQuinaAbaixo() != null) if(campoSelecionado.getQuinaAbaixo().campoVazio()){ campoSelecionado.getQuinaAbaixo().setFundo(); move=true;}
                if(campoSelecionado.getQuinaEsquerda() != null) if(campoSelecionado.getQuinaEsquerda().campoVazio()){ campoSelecionado.getQuinaEsquerda().setFundo(); move=true;}
                if(campoSelecionado.getQuinaDireita() != null) if(campoSelecionado.getQuinaDireita().campoVazio()){ campoSelecionado.getQuinaDireita().setFundo(); move=true;}
                if(campoSelecionado.getQuinaAcima() != null) if(campoSelecionado.getQuinaAcima().campoVazio()){ campoSelecionado.getQuinaAcima().setFundo(); move=true;}
            }
            switch(movimento){
                case 0: 
                    try{ 
                        if(campoSelecionado.getFaceAcimaEsquerda().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAcimaEsquerda().peca2() == Xadrez.TESTUDO){
                            campoSelecionado = campoSelecionado.getFaceAcimaEsquerda();
                        } else{
                            stop=true;
                        }
                    }catch(Exception e){}
                    break;
                case 1: 
                    try{ 
                        if(campoSelecionado.getFaceAcimaDireita().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAcimaDireita().peca2() == Xadrez.TESTUDO){
                            campoSelecionado = campoSelecionado.getFaceAcimaDireita();
                        } else {
                            stop=true;
                        }
                    }catch(Exception e){}
                    break;
            }
        }while(!stop);
        
        return move;
    }

    @Override
    public boolean amigo(int pecaTipo, int[] parametros) {
        return false;
    }

    @Override
    public boolean amigo(int pecaPega, Campo campo) {
        return false;
    }
 
    public static void abrirTestudos(Campo campoSelecionado, Campo campoDestino){
        Campo[] movimentos = new Campo[4];
        movimentos[0] = campoSelecionado;
        movimentos[1] = campoSelecionado;
        movimentos[2] = campoSelecionado;
        movimentos[3] = campoSelecionado;
        
        boolean[] abrir = new boolean[4];
        for(int j=0; j<4; j++){
            abrir[j] = true;
        }
        
        int vida = campoSelecionado.vidaPeca1();
            
        for(int i=0; i<vida-1; i++){
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
                if(movimentos[j] == campoDestino){
                    int jogador = campoSelecionado.jogador1();
                    campoSelecionado.addPeca1(Xadrez.TESTUDO, Xadrez.getTratadores()[Xadrez.TESTUDO-1].getVidaTotal());
                    for(int i=0; i<vida-1; i++){
                        switch(j){
                            case 0: try{ campoSelecionado = campoSelecionado.getFaceAbaixoDireita(); }catch(Exception e){}
                                break;
                            case 1: try{ campoSelecionado = campoSelecionado.getFaceAbaixoEsquerda(); }catch(Exception e){}
                                break;
                            case 2: try{ campoSelecionado = campoSelecionado.getFaceAcimaDireita(); }catch(Exception e){}
                                break;
                            case 3: try{ campoSelecionado = campoSelecionado.getFaceAcimaEsquerda(); }catch(Exception e){}
                                break;
                        }
                        campoSelecionado.addJogador1(jogador);
                        campoSelecionado.addPeca1(Xadrez.TESTUDO, vida);
                    }
                    return;
                }
            }
        }
    }
    
    public static int apagarTestudos(Campo campoSelecionado) {
        int movimento=4;
        if(campoSelecionado.getFaceAbaixoDireita() != null && (campoSelecionado.getFaceAbaixoDireita().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAbaixoDireita().peca2() == Xadrez.TESTUDO)){
            movimento = 0;
        }else if(campoSelecionado.getColuna()!=0 && (campoSelecionado.getFaceAcimaEsquerda().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAcimaEsquerda().peca2() == Xadrez.TESTUDO)){
            movimento = 0;
        }else{
            if(campoSelecionado.getFaceAbaixoEsquerda() != null && (campoSelecionado.getFaceAbaixoEsquerda().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAbaixoEsquerda().peca1() == Xadrez.TESTUDO)){
                movimento = 1;
            }else if(campoSelecionado.getColuna()!=campoSelecionado.getLinha() && (campoSelecionado.getFaceAcimaDireita().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAcimaDireita().peca2() == Xadrez.TESTUDO)){
                movimento = 1;
            }
        }
        
        boolean stop=false;
        do{
            switch(movimento){
                case 0: 
                    try{ 
                        if(campoSelecionado.getFaceAbaixoDireita()!=null && (campoSelecionado.getFaceAbaixoDireita().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAbaixoDireita().peca2() == Xadrez.TESTUDO)){
                            campoSelecionado = campoSelecionado.getFaceAbaixoDireita();
                        } else if(campoSelecionado.getColuna()==campoSelecionado.getLinha() && (campoSelecionado.getFaceAcimaDireita().peca1() == Xadrez.TESTUDO ||campoSelecionado.getFaceAcimaDireita().peca2() == Xadrez.TESTUDO)){
                            campoSelecionado = campoSelecionado.getFaceAcimaDireita();
                        } else{
                            stop=true;
                        }
                    }catch(Exception e){}
                    break;
                case 1: 
                    try{ 
                        if(campoSelecionado.getFaceAbaixoEsquerda()!=null && (campoSelecionado.getFaceAbaixoEsquerda().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAbaixoEsquerda().peca2() == Xadrez.TESTUDO)){
                            campoSelecionado = campoSelecionado.getFaceAbaixoEsquerda();
                        } else if(campoSelecionado.getColuna()==0 && (campoSelecionado.getFaceAcimaEsquerda().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAcimaEsquerda().peca2() == Xadrez.TESTUDO)){
                            campoSelecionado = campoSelecionado.getFaceAcimaEsquerda();
                        } else {
                            stop=true;
                        }
                    }catch(Exception e){}
                    break;
            }
        }while(!stop);
        
        int vida = 0;
        stop = false;
        do{
            vida++;
            if(campoSelecionado.peca1() == Xadrez.TESTUDO){
                campoSelecionado.addJogador1(0);
                campoSelecionado.addPeca1(0, 0);
            }else{
                campoSelecionado.addJogador2(0);
                campoSelecionado.addPeca2(0, 0);
            }
            switch(movimento){
                case 0: 
                    try{ 
                        if(campoSelecionado.getFaceAcimaEsquerda().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAcimaEsquerda().peca2() == Xadrez.TESTUDO){
                            campoSelecionado = campoSelecionado.getFaceAcimaEsquerda();
                        } else{
                            stop=true;
                        }
                    }catch(Exception e){}
                    break;
                case 1: 
                    try{ 
                        if(campoSelecionado.getFaceAcimaDireita().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAcimaDireita().peca2() == Xadrez.TESTUDO){
                            campoSelecionado = campoSelecionado.getFaceAcimaDireita();
                        } else {
                            stop=true;
                        }
                    }catch(Exception e){}
                    break;
            }
        }while(!stop);
        
        return vida;
    }
    
    static void diminuirVida(Campo campoSelecionado) {
        int movimento=4;
        if(campoSelecionado.getFaceAbaixoDireita() != null && (campoSelecionado.getFaceAbaixoDireita().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAbaixoDireita().peca2() == Xadrez.TESTUDO)){
            movimento = 0;
        }else if(campoSelecionado.getColuna()!=0 && (campoSelecionado.getFaceAcimaEsquerda().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAcimaEsquerda().peca2() == Xadrez.TESTUDO)){
            movimento = 0;
        }else{
            if(campoSelecionado.getFaceAbaixoEsquerda() != null && (campoSelecionado.getFaceAbaixoEsquerda().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAbaixoEsquerda().peca1() == Xadrez.TESTUDO)){
                movimento = 1;
            }else if(campoSelecionado.getColuna()!=campoSelecionado.getLinha() && (campoSelecionado.getFaceAcimaDireita().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAcimaDireita().peca2() == Xadrez.TESTUDO)){
                movimento = 1;
            }
        }
        
        boolean stop=false;
        do{
            switch(movimento){
                case 0: 
                    try{ 
                        if(campoSelecionado.getFaceAbaixoDireita()!=null && (campoSelecionado.getFaceAbaixoDireita().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAbaixoDireita().peca2() == Xadrez.TESTUDO)){
                            campoSelecionado = campoSelecionado.getFaceAbaixoDireita();
                        } else if(campoSelecionado.getColuna()==campoSelecionado.getLinha() && (campoSelecionado.getFaceAcimaDireita().peca1() == Xadrez.TESTUDO ||campoSelecionado.getFaceAcimaDireita().peca2() == Xadrez.TESTUDO)){
                            campoSelecionado = campoSelecionado.getFaceAcimaDireita();
                        } else{
                            stop=true;
                        }
                    }catch(Exception e){}
                    break;
                case 1: 
                    try{ 
                        if(campoSelecionado.getFaceAbaixoEsquerda()!=null && (campoSelecionado.getFaceAbaixoEsquerda().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAbaixoEsquerda().peca2() == Xadrez.TESTUDO)){
                            campoSelecionado = campoSelecionado.getFaceAbaixoEsquerda();
                        } else if(campoSelecionado.getColuna()==0 && (campoSelecionado.getFaceAcimaEsquerda().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAcimaEsquerda().peca2() == Xadrez.TESTUDO)){
                            campoSelecionado = campoSelecionado.getFaceAcimaEsquerda();
                        } else {
                            stop=true;
                        }
                    }catch(Exception e){}
                    break;
            }
        }while(!stop);
        
        stop = false;
        do{
            if(campoSelecionado.peca1() == Xadrez.TESTUDO){
                campoSelecionado.peca1Vida(-1);
            }else{
                campoSelecionado.peca2Vida(-1);
            }
            switch(movimento){
                case 0: 
                    try{ 
                        if(campoSelecionado.getFaceAcimaEsquerda().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAcimaEsquerda().peca2() == Xadrez.TESTUDO){
                            campoSelecionado = campoSelecionado.getFaceAcimaEsquerda();
                        } else{
                            stop=true;
                        }
                    }catch(Exception e){}
                    break;
                case 1: 
                    try{ 
                        if(campoSelecionado.getFaceAcimaDireita().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAcimaDireita().peca2() == Xadrez.TESTUDO){
                            campoSelecionado = campoSelecionado.getFaceAcimaDireita();
                        } else {
                            stop=true;
                        }
                    }catch(Exception e){}
                    break;
            }
        }while(!stop);
        
        stop=false;
        do{
            if(campoSelecionado.getFaceAcimaEsquerda().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAcimaEsquerda().peca2() == Xadrez.TESTUDO){
                campoSelecionado = campoSelecionado.getFaceAcimaEsquerda();
            } else if(campoSelecionado.getFaceAcimaDireita().peca1() == Xadrez.TESTUDO || campoSelecionado.getFaceAcimaDireita().peca2() == Xadrez.TESTUDO){
                campoSelecionado = campoSelecionado.getFaceAcimaDireita();
            } else{
                stop=true;
            }
        }while(!stop);
        if(campoSelecionado.peca1() == Xadrez.TESTUDO){
            campoSelecionado.addJogador1(0);
            campoSelecionado.addPeca1(0, 0);
        }else{
            campoSelecionado.addJogador2(0);
            campoSelecionado.addPeca2(0, 0);
        }
    }
}

