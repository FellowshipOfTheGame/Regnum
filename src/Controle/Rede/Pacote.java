/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle.Rede;

import Modelo.Ator.Ator;
import Modelo.Movimento;
import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author marcius
 */
public class Pacote implements Serializable{
    String time;
    Color cor;
    int ordem;
    
    Movimento movimento;
    
    int protocolo;
    
    String mensagemChat;
    
    public static final int PROCURANDO = 0;
    public static final int CLIENTE = 1;
    public static final int MOVIMENTO = 2;
    public static final int INICIA_JOGO = 3;
    //public static final int FINAL = 4;
    public static final int OUTRO_JOGADOR = 5;
    public static final int MENSAGEM_CHAT = 6;
    public static final int DERROTADO = 7;
    
    
    public Pacote(){
        protocolo = PROCURANDO;
    }
    
    public void SetAtor(Ator a){
        this.setTime(a.getTime());
        this.setCor(a.getCor());
        this.setOrdem(a.getOrdem());
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    public Movimento getMovimento() {
        return movimento;
    }

    public void setMovimento(Movimento movimento) {
        this.movimento = movimento;
    }

    public int getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(int protocolo) {
        this.protocolo = protocolo;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public String getMensagemChat() {
        return mensagemChat;
    }

    public void setMensagemChat(String mensagemChat) {
        this.mensagemChat = mensagemChat;
    }
}
