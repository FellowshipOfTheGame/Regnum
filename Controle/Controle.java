package Controle;

import Controle.Ator.*;
import Controle.Rede.Sala;
import Visual.Janela;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Classe responsavel pelo gerenciamento geral do jogo, como rede, telas,
 * controlador de jogo e atores
 *
 * @author andre
 */
public class Controle {

    private Janela tela;
    private Sala sala;
    private Ator usuario;
    private int jogadorAtual;
    private static Controle CONTROLE;
    private int estado;
    public final static int INICIANDO = 0;
    public final static int CONFIGURANDO = 1;
    public final static int SALA = 2;
    public final static int JOGANDO = 3;
    public final static int OPCAO_CAMPO = 4;
    public final static int OUTRO_JOGANDO = 5;
    public final static int ASSISTINDO = 6;
    private boolean assistindo;
    private boolean gavar;
    private Xadrez xadrez;

    private Controle() {
    }

    /*INICIO----------------------IMPLEMENTACAO METODOS ESCENCIAIS------------*/
    /**
     * Controle para haver uma uncia instancia de controle no programa design
     * patern: Singleton
     */
    public static Controle instanciaControle() {
        if (CONTROLE == null) {
            CONTROLE = new Controle();
        }
        return CONTROLE;
    }

    /**
     * Acao a ser tomada logo que o jogo e aberto
     */
    public void init() {
        this.tela = new Janela();
        this.tela.setVisible(true);
    }

    public void iniciaPartida() {
        jogadorAtual = 0;

        this.xadrez = new Xadrez(this.getNJogadores());

        if (this.usuario.getOrdem() == this.jogadorAtual) {
            this.estado = JOGANDO;
        } else {
            this.estado = OUTRO_JOGANDO;
        }
        this.tela.iniciaPartida();
    }

    /*FIM-------------------------IMPLEMENTACAO METODOS ESCENCIAIS------------*/
    /*INICIO ---------------------IMPLEMENTACAO CONTROLE DE REDE--------------*/
    public boolean conectou() {
        return this.sala.clienteConectou();
    }

    public boolean isProvedor() {
        if (sala != null) {
            return this.sala.isServidor();
        }

        return false;
    }

    public void conectaCliente() {
        this.sala.iniciaCliente();
    }

    public void setSala(String ip, boolean servidor) {
        this.sala = new Sala(ip, 9001, servidor);

        if (servidor) {
            //System.out.println("botao de servidor certo");
            this.sala.iniciaServidor();
        }
    }

    public void noficaPartidaIniciada() {
        this.sala.notificaPartidaIniciada();
    }

    public void mensagemChat(String text) {
        this.sala.mensagemChat(text);
    }

    public void respostaChat(String time, String mensagemChat, Color c) {
        this.tela.respostaChat(time, mensagemChat, c);
    }

    public ArrayList<Color> getColorTimes() {
        return this.sala.getJogadoresCor();
    }

    /*FIM----------------------------IMPLEMENTACAO CONTROLE DE REDE-----------*/
    /*INICIO -------------------IMPLEMENTACAO VALIDACAO DAS ACOE----------------
     -----------------------------------E CRIACAO DO MOVIMENTO-----------------*/
    public void campoSelecionado(int face, int linha, int coluna) {
        System.out.println("f: " + face + " l: " + linha + " c: " + coluna);
        if (this.xadrez.temCampoSelecionado()) {
            if (this.xadrez.validaCampo(face, linha, coluna)) {
                this.usuario.campoValidado(face, linha, coluna);
                //TODO trocar de usuario
            } else {
                //TODO fazer tratamento de err0
            }
        } else if (this.xadrez.selecionarCampo(face, linha, coluna, usuario.getOrdem())) {
            this.usuario.campoSelecionado(face, linha, coluna);
        } else {
            //TODO fazer tratamento de erro
        }
    }

    /*FIM-----------------------IMPLEMENTACAO VALIDACAO DAS ACOE----------------
     ------------------------------------E CRIACAO DO MOVIMENTO----------------*/

    /*INICIO----------------------IMPLEMENTACAO GET E SET---------------------*/
    public void setUsuario(String time, Color cor) {
        if (this.usuario == null) {
            this.usuario = new Ator(CONTROLE, time, cor);
        } else {
            this.usuario.setTime(time);
            this.usuario.setCor(cor);
        }
        
    }

    public Ator getUsuario() {
        return usuario;
    }

    public int getNJogadores() {
        if (sala != null) {
            return this.sala.getnJogadores();
        }
        return 1;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getEstado() {
        return estado;
    }

    public boolean isAssistindo() {
        return assistindo;
    }

    public void setAssistindo(boolean assistindo) {
        this.assistindo = assistindo;
    }

    public boolean isGavar() {
        return gavar;
    }

    public void setGavar(boolean gavar) {
        this.gavar = gavar;
    }

    public Sala getSala() {
        return sala;
    }

    public Xadrez getXadrez() {
        return xadrez;
    }
    /*FIM----------------------IMPLEMENTACAO GET E SET------------------------*/
}
