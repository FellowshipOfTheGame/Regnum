/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle.Rede;

import Modelo.Ator.Ator;
import Controle.Controle;
import Modelo.Movimento;
import Visual.Plano.Tela2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcius
 */
public class Cliente extends Thread{
    private final String ip;
    private final int porta;
    
    private final Ator usuario;
    private final Sala sala;
    
    private ObjectInputStream entrada;
    private ObjectOutputStream saida;
    
    private Pacote mensagem;

    public Cliente(Sala sala) {
        Controle c = Controle.instanciaControle();
        this.usuario = c.getUsuario();
        this.sala = sala;
        this.ip = sala.getIp();
        this.porta = sala.getPorta();
    }
    
    @Override
    public void run(){
        //TODO gravar e assistir
        //FileChooser.addExtension(".Regnum", "Arquivo com os movimento salvos .Regnum");
        
        try {
            Socket conexao = new Socket(ip, porta);
            
            saida = new ObjectOutputStream(conexao.getOutputStream());
            saida.flush();
            saida.reset();
            
            entrada = new ObjectInputStream(conexao.getInputStream());
            
            while (true) {
                try {               
                    mensagem = (Pacote) entrada.readObject();
                    
                    switch(mensagem.getProtocolo()){
                        case Pacote.PROCURANDO:{
                            saida.writeObject(iniciarContato(mensagem));
                            break;
                        }case Pacote.CLIENTE_FALHOU_COR:{
                            Tela2D.aviso("Cor invalida!");
                            break;
                        }case Pacote.CLIENTE_FALHOU_NOME:{
                            Tela2D.aviso("Nome invalido!");
                            break;
                        }case Pacote.CLIENTE:{
                            Tela2D.desligaAviso();
                            aprovadoContato(mensagem);
                            break;
                        }case Pacote.MOVIMENTO:{
                            this.sala.realizarMovimento(mensagem.getOrdem(), mensagem.getMovimento());
                            break;
                        }case Pacote.INICIA_JOGO:{
                            sala.iniciaPartida();
                            break;
                        }case Pacote.OUTRO_JOGADOR:{
                            if(!sala.isServidor()){
                                sala.timeExistente(mensagem);
                            }
                            break;
                        }case Pacote.MENSAGEM_CHAT:{
                            sala.respostaChat(mensagem.getTime(), mensagem.getMensagemChat(), mensagem.getCor());
                            break;
                        }case Pacote.DERROTADO:{
                            this.sala.jogadorDerrotado(mensagem.getOrdem());
                            break;
                        }
                    }
                } catch (ClassNotFoundException ex) {
                    return;
                }
            }
            
        } catch (UnknownHostException ex) {
        } catch (IOException ex) {
            Tela2D.aviso("Servidor Desconectado!");
        }
    }
    
    private Pacote iniciarContato(Pacote m){
        m.setTime(usuario.getTime());
        m.setCor(usuario.getCor());
        return m;
    }
    
    private void aprovadoContato(Pacote m){
        usuario.setOrdem(m.getOrdem());
        sala.setClienteConectou(true);
    }

    void notificaPartidaIniciada() {
        try {
            mensagem.setProtocolo(Pacote.INICIA_JOGO);
            saida.writeObject(mensagem);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void mensagemChat(String text) {
        try {
            mensagem.setTime(usuario.getTime());
            mensagem.setCor(usuario.getCor());
            mensagem.setProtocolo(Pacote.MENSAGEM_CHAT);
            mensagem.setMensagemChat(text);
            saida.writeObject(mensagem);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void notificaMovimentoRealizado(Movimento movimentoTemporario) {
        try {
            mensagem.setTime(usuario.getTime());
            mensagem.setCor(usuario.getCor());
            mensagem.setOrdem(usuario.getOrdem());
            mensagem.setProtocolo(Pacote.MOVIMENTO);
            mensagem.setMovimento(movimentoTemporario);
            saida.writeObject(mensagem);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void notificaJogadorDerrotado(int jogador) {
        try {
            mensagem.setOrdem(jogador);
            mensagem.setProtocolo(Pacote.DERROTADO);
            saida.writeObject(mensagem);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void conectar() {
        mensagem.setProtocolo(Pacote.PROCURANDO);
    }
}
