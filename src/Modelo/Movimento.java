/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;

/**
 *
 * @author marcius
 */
public final class Movimento implements Cloneable, Serializable{
    /** Campo que o usuario decidiu interagir */
    private final int[][] campo;
    
    private final int tipoMovimento;
    public static final int PEGARP1=0;
    public static final int PEGARP2=1;
    public static final int INTERAGIR1=2;
    public static final int INTERAGIR2=3;
    public static final int ATACAR1=4;
    public static final int ATACAR2=5;
    public static final int ASSOPRO=20;
    
    public Movimento(Campo campoSelecionado, Campo campoDestino, int tipoMovimento) {
        
        this.campo = new int[2][3];
        this.campo[0][0] = campoSelecionado.getFace();
        this.campo[0][1] = campoSelecionado.getLinha();
        this.campo[0][2] = campoSelecionado.getColuna();
        this.campo[1][0] = campoDestino.getFace();
        this.campo[1][1] = campoDestino.getLinha();
        this.campo[1][2] = campoDestino.getColuna();
        
        this.tipoMovimento = tipoMovimento;
        
    }
    
    public Movimento(Campo campoSelecionado, int tipoMovimento) {
        
        this.campo = new int[1][3];
        this.campo[0][0] = campoSelecionado.getFace();
        this.campo[0][1] = campoSelecionado.getLinha();
        this.campo[0][2] = campoSelecionado.getColuna();
        
        this.tipoMovimento = tipoMovimento;
    }
    
    /**
     * Um movimento eh composto de um click inicial
     * * se houver uma peca os campos são pintados
     * * se houver duas pecas:
     * * * se forem inimigas eh perguntado se deseja atacar ou movimentar-se
     * * * se forem duas pecas amigas aparece eh perguntado qual delas deseja movimentar ou iteracao entre elas
     * 
     * Segundo click se ouver ira levar uma peca a outro campo.
     * @return 
     */
    public int[] getCampoSelecionado() {
        return campo[0];
    }

    /**
     * Um movimento eh composto de um click inicial
     * * se houver uma peca os campos são pintados
     * * se houver duas pecas:
     * * * se forem inimigas eh perguntado se deseja atacar ou movimentar-se
     * * * se forem duas pecas amigas aparece eh perguntado qual delas deseja movimentar ou iteracao entre elas
     * 
     * Segundo click se ouver ira levar uma peca a outro campo.
     * @return 
     */
    public int[] getCampoDestino() {
        try{
            return campo[1];
        }catch(Exception e){
            return null;
        }
    } 
    

    public int getTipoMovimento() {
        return tipoMovimento;
    }
    

    @Override
    public Object clone(){
        try {
            return super.clone();
        }catch (CloneNotSupportedException e) {
            throw new InternalError(e.toString());
        }
    }
    
}
