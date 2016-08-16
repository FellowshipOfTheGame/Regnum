/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle.Rede;

import Controle.Controle;
import static Controle.Rede.Servidor.sala;
import Visual.Plano.Tela2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcius
 */
public class Escutador extends Thread {
    private Pacote mensagem;
    private Socket conexao;
    private ObjectInputStream entrada;
    private ObjectOutputStream saida;
    private Servidor servidor;

    /**
     * Todos os Escutador utilizao da mesma conexao: @param sConexao
     */
    public Escutador(Socket sConexao, Servidor servidor) {
        this.conexao = sConexao;
        this.servidor = servidor;
    }

    /**
     * Ira primeiro encontrar um time para conectar
     * Depois ficara esperando o time a dar a resposta e depois levara a 
     * resposata dele para todos
     */
    @Override
    public void run() {
        try {       
            saida = new ObjectOutputStream(conexao.getOutputStream());
            saida.flush();
            saida.reset();
            
            entrada = new ObjectInputStream(conexao.getInputStream());
            
            while (true) {
                saida.writeObject(new Pacote());
                saida.flush();
                try {
                    mensagem = (Pacote)entrada.readObject();

                    if (mensagem == null) {
                        return;
                    }

                    int existeTime = sala.timeExistente(mensagem);
                    if(existeTime==0){
                        break;
                    }else if(existeTime==1){
                        mensagem.setProtocolo(Pacote.CLIENTE_FALHOU_NOME);
                        mensagem.setOrdem(sala.getnJogadores()-1);
                        saida.writeObject(mensagem);
                        saida.flush();
                    }else if(existeTime==2){
                        mensagem.setProtocolo(Pacote.CLIENTE_FALHOU_COR);
                        mensagem.setOrdem(sala.getnJogadores()-1);
                        saida.writeObject(mensagem);
                        saida.flush();
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            /*Noficando ao cliente que sua conecção foi estabelecida e ordem obtida*/
            mensagem.setProtocolo(Pacote.CLIENTE);
            mensagem.setOrdem(sala.getnJogadores()-1);
            saida.writeObject(mensagem);
            saida.flush();
            
            servidor.saidas.add(saida);
            
            servidor.noficaJogadoresAdicionados();

            while (true) {
                Pacote movimento;
                try {                
                    movimento = (Pacote)entrada.readObject();
                    if (movimento == null) {
                        return;
                    }
                    for (ObjectOutputStream outro : servidor.saidas) {
                        outro.writeObject(movimento);
                        outro.flush();
                    }
                } catch (IOException ex) {
                    Controle c = Controle.instanciaControle();
                    Tela2D.aviso("Jogador "+c.getUsuario().getTime()+" desconectou!");
                    //TODO fazer tratamento de saida de jogador
                    return;
                } catch (ClassNotFoundException ex) {
                    System.out.println("o que aconteceu?");
                    Logger.getLogger(Escutador.class.getName()).log(Level.SEVERE, null, ex);
                }        
            }   
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (mensagem != null) {
                sala.removeTime(mensagem);
            }
            if (saida != null) {
                servidor.saidas.remove(saida);
            }
            try {
                conexao.close();
            } catch (IOException e) {
            }
        }
    }
    
}
