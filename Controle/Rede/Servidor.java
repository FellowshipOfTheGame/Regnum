/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle.Rede;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcius
 */
public class Servidor extends Thread{
    public int porta;
    public static Sala sala;
    
    public ArrayList<ObjectOutputStream> saidas;

    public Servidor(Sala sThis) {
        sala = sThis;
        porta = sala.getPorta();
        saidas = new ArrayList<ObjectOutputStream>();
    }
    
    
    @Override
    public void run(){
        try {
            ServerSocket conexao = new ServerSocket(porta, 7);
            try {
                for(int i=0; i<7 && !sala.comecou(); i++) {
                    new Escutador(conexao.accept(), this).start();
                }
            } finally {
                conexao.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void noficaJogadoresAdicionados() {
        Pacote mensagem = new Pacote();
        mensagem.setProtocolo(Pacote.OUTRO_JOGADOR);
        
        Iterator<Color> ic = sala.getJogadoresCor().iterator();
        for (Iterator<String> it = sala.getJogadoresTime().iterator(); it.hasNext();) {
            
            String s = it.next();
            Color c = ic.next();
            
            mensagem.setCor(c);
            mensagem.setTime(s);
            
            /*Notificando a todos, todos os clientes*/
            for (ObjectOutputStream outro : saidas) {
                try {
                    outro.writeObject(mensagem);
                    outro.flush();
                    outro.reset();
                } catch (IOException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
}
