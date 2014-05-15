/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle.Rede;

import Controle.Controle;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author marcius
 */
public class Sala {
    private String ip;
    private int porta;
    private boolean servidor;
    
    private int nJogadores;
    private ArrayList<String> jogadoresTime;
    private ArrayList<Color> jogadoresCor;
    
    private Controle controle;
    
    private Cliente cliente;
    private boolean clienteConectou;

    public Sala(String ip, int porta, boolean servidor) {
        this.ip = ip;
        this.porta = porta;
        this.servidor = servidor;
        
        this.nJogadores = 0;
        this.jogadoresTime = new ArrayList<String>();
        this.jogadoresCor = new ArrayList<Color>();
        
        this.controle = Controle.instanciaControle();
        clienteConectou = false;
    }
    
    public synchronized boolean timeExistente(Pacote p){
        String jogador = p.getTime();
        Color cor =  p.getCor();
        if (!jogadoresTime.contains(jogador) && !jogadoresCor.contains(cor)) {
            jogadoresTime.add(jogador);
            jogadoresCor.add(cor);
            nJogadores++;
            
            return false;
        }
        return true;
    }
    
    public synchronized boolean removeTime(Pacote p){
        return true;
    }

    boolean comecou() {
        if(controle.getEstado() == Controle.JOGANDO){
            return true;
        }
        
        return false;
    }

    public void iniciaServidor() {
        new Servidor(this).start();
    }
    
    public void iniciaCliente() {
        if(this.cliente == null){
            this.cliente = new Cliente(this);
            this.cliente.start();
            
            clienteConectou = false;
        }
    }
    
    public boolean clienteConectou(){
        if(this.cliente != null){
            return clienteConectou;
        }
        
        return false;
    }
    
    public void mensagemChat(String text) {
        this.cliente.mensagemChat(text);
    }
    
    void respostaChat(String time, String mensagemChat, Color c) {
        this.controle.respostaChat(time, mensagemChat, c);
    }
    
    public void iniciaPartida() {
        this.controle.iniciaPartida();
    }

    public String getIp() {
        return ip;
    }

    public int getPorta() {
        return porta;
    }

    public boolean isServidor() {
        return servidor;
    }

    public int getnJogadores() {
        if(nJogadores == 0){
            return 1;
        }
        return nJogadores;
    }

    public void setClienteConectou(boolean clienteConectou) {
        this.clienteConectou = clienteConectou;
    }

    public ArrayList<String> getJogadoresTime() {
        return jogadoresTime;
    }

    public ArrayList<Color> getJogadoresCor() {
        return jogadoresCor;
    }

    public void notificaPartidaIniciada() {
        this.cliente.notificaPartidaIniciada();
    }
}
