/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle.Rede;

import Controle.Controle;
import Modelo.Movimento;
import Visual.Plano.Tela2D;
import Visual.Plano.Utilidades.MapaImagens;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author marcius
 */
public class Sala {
    private final String ip;
    private final int porta;
    private final boolean servidor;
    
    private int nJogadores;
    private final ArrayList<String> jogadoresTime;
    private final ArrayList<Color> jogadoresCor;
    private final ArrayList<Boolean> jogadoresVivos;
    
    private final Controle controle;
    
    private Cliente cliente;
    private boolean clienteConectou;

    public Sala(String ip, int porta, boolean servidor) {
        this.ip = ip;
        this.porta = porta;
        this.servidor = servidor;
        
        this.nJogadores = 0;
        this.jogadoresTime = new ArrayList<String>();
        this.jogadoresCor = new ArrayList<Color>();
        this.jogadoresVivos = new ArrayList<Boolean>();
        
        this.controle = Controle.instanciaControle();
        clienteConectou = false;
    }
    
    public synchronized int timeExistente(Pacote p){
        String jogador = p.getTime();
        Color cor =  p.getCor();
        if(MapaImagens.corPermitida(cor)){
            if (!jogadoresTime.contains(jogador) && !jogadoresCor.contains(cor)) {
                jogadoresTime.add(jogador);
                jogadoresCor.add(cor);
                jogadoresVivos.add(true);
                nJogadores++;

                //TODO gravar e assistir
                
                return 0;
            }else{
                return 1;
            }
        }
        return 2;
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
        }else{
            this.cliente.conectar();
        }
    }
    
    public boolean clienteConectou(){
        if(this.cliente != null){
            return clienteConectou;
        }
        
        return false;
    }
    
    public void clienteDesconectou(int i) {
        this.jogadorPerdeu(i);
    }
    
    public void mensagemChat(String text) {
        this.cliente.mensagemChat(text);
    }
    
    void respostaChat(String time, String mensagemChat, Color c) {
        this.controle.respostaChat(time, mensagemChat, c);
    }
    
    public void iniciaPartida() {
        this.controle.iniciaPartida();
        //TODO gravar e assistir
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

    public ArrayList<Boolean> getJogadoresVivos() {
        return jogadoresVivos;
    }

    public void notificaPartidaIniciada() {
        this.cliente.notificaPartidaIniciada();
    }

    public void realizarMovimento(Movimento movimentoTemporario) {
        this.cliente.notificaMovimentoRealizado(movimentoTemporario);
    }

    public void realizarMovimento(int ordem, Movimento movimento) {
        this.controle.realizarmovimento(ordem, movimento);
    }

    public void jogadorPerdeu(int jogador) {
        this.cliente.notificaJogadorDerrotado(jogador);
    }

    public void jogadorDerrotado(int ordem) {
        Tela2D.aviso(this.jogadoresTime.get(ordem)+" Perdeu!");
        this.jogadoresVivos.remove(ordem);
        this.jogadoresVivos.add(ordem, false);
        int i = 0;
        int vencedor = -1;
        ordem=0;
        for(boolean vivo : this.jogadoresVivos){
            i += (vivo)? 1:0;
            vencedor = (vivo)? ordem : vencedor;
            ordem++;
        }
        if(!(i>1)){
            this.controle.jogadorVencedor(vencedor);
        }
    }
}
