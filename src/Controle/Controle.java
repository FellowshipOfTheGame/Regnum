package Controle;

import Controle.Rede.Sala;
import Modelo.Ator.Ator;
import Modelo.Campo;
import Modelo.Movimento;
import Visual.Janela;
import java.awt.Color;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe responsavel pelo gerenciamento geral do jogo, como rede, telas,
 * controlador de jogo e atores
 *
 * @author andre
 */
public class Controle {

    private static Controle CONTROLE;

    private Xadrez xadrez;

    private Janela tela;
    private boolean assistindo;
    private boolean gavar;

    private Sala sala;

    private Ator usuario;
    private int jogadorAtual;

    private int estado;
    public final static int INICIANDO = 0;
    public final static int CONFIGURANDO = 1;
    public final static int SALA = 2;
    public final static int JOGANDO = 3;
    public final static int OPCAO_CAMPO = 4;
    public final static int OUTRO_JOGANDO = 5;
    public final static int ASSISTINDO = 6;
    public final static int VENCEDOR = 7;

    private Movimento movimentoTemporario;

    private Controle() {
    }

    /*INICIO----------------------IMPLEMENTACAO METODOS ESCENCIAIS------------*/
    /**
     * Controle para haver uma uncia instancia de controle no programa design
     * patern: Singleton
     *
     * @return
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

    public void jogadorVencedor(int ordem) {
        this.estado = VENCEDOR;
        jogadorAtual = ordem;
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
        if (this.usuario.getOrdem() == this.jogadorAtual) {
            if (this.xadrez.temCampoSelecionado()) {
                if (this.xadrez.validaCampo(face, linha, coluna)) {
                    Campo origem = (Campo) Xadrez.getCampoSelecianado().clone();
                    Campo destino = (Campo) Xadrez.getTabuleiro().campoSelecionado(face, linha, coluna).clone();
                    this.enviaMovimento(origem, destino, Xadrez.getPecaPega());
                } else {
                    //TODO fazer tratamento de erro
                }
            } else if (!this.xadrez.selecionarCampo(face, linha, coluna, usuario.getOrdem() + 1)) {
                //TODO fazer tratamento de erro
            }
        }
    }

    public void enviaMovimento(Campo origem, Campo destino, int pecaPega) {
        if (destino != null) {
            this.movimentoTemporario = new Movimento(origem, destino, pecaPega);
        } else {
            this.movimentoTemporario = new Movimento(origem, pecaPega);
        }
        //TODO quando for implementar a parte 3D tera de pegar os movimentos aqui e colocar em uma lista
        this.sala.realizarMovimento(this.movimentoTemporario);
    }

    public void realizarmovimento(int ordem, Movimento movimentoRealizado) {
        if (movimentoRealizado.getTipoMovimento() == Movimento.ASSOPRO) {
            this.xadrez.realizarAcao(ordem, movimentoRealizado.getCampoSelecionado(), movimentoRealizado.getTipoMovimento());
        } else {
            //TODO gravar e assistir
            /*if (this.gavar) {
                SaveLoad.save(movimentoRealizado);
            }*/
            if (!this.xadrez.tratarExecaoMovimento(ordem, movimentoRealizado)) {
                if (movimentoRealizado.getTipoMovimento() <= Movimento.PEGARP2) {
                    this.xadrez.realizarMovimento(ordem, movimentoRealizado.getCampoSelecionado(), movimentoRealizado.getCampoDestino(), movimentoRealizado.getTipoMovimento());
                } else {
                    this.xadrez.realizarAcao(ordem, movimentoRealizado.getCampoSelecionado(), movimentoRealizado.getTipoMovimento());
                }
            }
            int vivos = this.getNJogadores();
            for (int i = 0; i < this.getNJogadores(); i++) {
                if (this.sala.getJogadoresVivos().get(i)) {
                    if (!Xadrez.existePeca(i + 1, Xadrez.REI) && !(Xadrez.existePeca(i + 1, Xadrez.PRINCIPE) && Xadrez.existePeca(i + 1, Xadrez.BISPO))) {
                        this.sala.jogadorPerdeu(i);
                        vivos--;
                    }
                } else {
                    vivos--;
                }
            }
            if (vivos != 1) {
                do {
                    this.jogadorAtual = (this.jogadorAtual + 1) % this.sala.getnJogadores();
                } while (!this.sala.getJogadoresVivos().get(this.jogadorAtual));
                if (this.usuario.getOrdem() == this.jogadorAtual) {
                    this.estado = JOGANDO;
                } else {
                    this.estado = OUTRO_JOGANDO;
                }
            } else {
                this.estado = VENCEDOR;
                //this.sala.jogadorVenceu(jogadorAtual);
            }
        }
    }

    public void botaoSelecionado(int botaoClicado) {
        this.xadrez.realizarBotao(botaoClicado);
        this.setEstado(Controle.JOGANDO);
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

    public int getJogadorAtual() {
        return jogadorAtual;
    }

    /*FIM----------------------IMPLEMENTACAO GET E SET------------------------*/
}
