/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Controle.Peca.Bispo;
import Controle.Peca.Cavaleiro;
import Controle.Peca.Dama;
import Controle.Peca.Peca;
import Controle.Peca.Principe;
import Controle.Peca.Rei;
import Controle.Peca.Soldado;
import Controle.Peca.Testudo;
import Controle.Peca.Testudos;
import Modelo.Campo;
import Modelo.Movimento;
import Modelo.Tabuleiro;
import Visual.Plano.Ambientes.AmbienteOpcao;
import Visual.Plano.Utilidades.MapaImagens;
import Visual.Plano.Tela2D;

/**
 *
 * @author marcius
 */
public class Xadrez {

    private static Tabuleiro tabuleiro;
    private static Campo[] campoSelecionado;
    private static int[] pecaPega;
    private static boolean[] temCampoSelecionado;
    private static boolean[] movimentoObrigatorio;

    /**
     * Tratadores das peças presente nos campos
     */
    private static final Peca[] tratadores = new Peca[8];
    public static final int REI = 1;
    public static final int PRINCIPE = 2;
    public static final int DAMA = 3;
    public static final int BISPO = 4;
    public static final int CAVALEIRO = 5;
    public static final int TESTUDOS = 6;
    public static final int SOLDADO = 7;
    public static final int TESTUDO = 8;

    public static boolean existePeca(int jogador, int tipoPeca) {
        int nJogadores = Controle.instanciaControle().getNJogadores();
        Campo[] camposTemp = tabuleiro.getCampos();
        for (int i = 0; i < nJogadores * 90; i++) {
            if (camposTemp[i].existeJogador(jogador) != 0 && (camposTemp[i].peca1() == tipoPeca || camposTemp[i].peca2() == tipoPeca || (camposTemp[i].peca1() == CAVALEIRO && camposTemp[i].vidaPeca1() - 1 == tipoPeca) || (camposTemp[i].peca2() == CAVALEIRO && camposTemp[i].vidaPeca2() - 1 == tipoPeca))) {
                return true;
            }
        }

        return false;
    }

    public Xadrez(int iJogadores) {
        tratadores[REI - 1] = new Rei();
        tratadores[DAMA - 1] = new Dama();
        tratadores[PRINCIPE - 1] = new Principe();
        tratadores[BISPO - 1] = new Bispo();
        tratadores[CAVALEIRO - 1] = new Cavaleiro();
        tratadores[TESTUDOS - 1] = new Testudos();
        tratadores[SOLDADO - 1] = new Soldado();
        tratadores[TESTUDO - 1] = new Testudo();

        tabuleiro = new Tabuleiro(iJogadores);
        
        campoSelecionado = new Campo[iJogadores];
        pecaPega = new int[iJogadores];
        temCampoSelecionado = new boolean[iJogadores];
        movimentoObrigatorio = new boolean[iJogadores];
    }

    public boolean selecionarCampo(int face, int linha, int coluna, int usuario) {
        if (!movimentoObrigatorio[Controle.instanciaControle().getJogadorAtual()]) {
            campoSelecionado[Controle.instanciaControle().getJogadorAtual()] = null;
            pecaPega[Controle.instanciaControle().getJogadorAtual()] = -1;

            campoSelecionado[Controle.instanciaControle().getJogadorAtual()] = tabuleiro.campoSelecionado(face, linha, coluna);
            if (campoSelecionado[Controle.instanciaControle().getJogadorAtual()].existeJogador(usuario) != 0) {
                if (!campoSelecionado[Controle.instanciaControle().getJogadorAtual()].campoCheio()) {
                    if (campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() == TESTUDOS) {
                        Controle.instanciaControle().setEstado(Controle.OPCAO_CAMPO);
                        AmbienteOpcao.setReset(false);
                        temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()] = true;
                        pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.PEGARP1;
                    } else if (campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() == TESTUDO) {
                        Campo origem = (Campo) Xadrez.getCampoSelecianado().clone();
                        pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.PEGARP1;
                        Controle.instanciaControle().enviaMovimento(origem, null, pecaPega[Controle.instanciaControle().getJogadorAtual()]);
                        return true;
                    } else if (campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() == CAVALEIRO && campoSelecionado[Controle.instanciaControle().getJogadorAtual()].vidaPeca1() > tratadores[CAVALEIRO - 1].getVidaTotal()) {
                        Controle.instanciaControle().setEstado(Controle.OPCAO_CAMPO);
                        AmbienteOpcao.setReset(false);
                        temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()] = true;
                        pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.PEGARP1;
                    } else {
                        pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.PEGARP1;
                        temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()] = tratadores[campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() - 1].pintaCampo(pecaPega[Controle.instanciaControle().getJogadorAtual()], campoSelecionado[Controle.instanciaControle().getJogadorAtual()], true);
                    }
                } else {
                    Controle.instanciaControle().setEstado(Controle.OPCAO_CAMPO);
                    AmbienteOpcao.setReset(false);
                    if (campoSelecionado[Controle.instanciaControle().getJogadorAtual()].pecasInimigas()) {
                        if (usuario == campoSelecionado[Controle.instanciaControle().getJogadorAtual()].jogador1()) {
                            pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.ATACAR1;
                            temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()] = true;
                            campoSelecionado[Controle.instanciaControle().getJogadorAtual()].setFundoVermelho();
                        } else if (usuario == campoSelecionado[Controle.instanciaControle().getJogadorAtual()].jogador2()) {
                            if (campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca2() == TESTUDO) {
                                Controle.instanciaControle().setEstado(Controle.JOGANDO);
                                Campo origem = (Campo) Xadrez.getCampoSelecianado().clone();
                                pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.PEGARP2;
                                Controle.instanciaControle().enviaMovimento(origem, null, pecaPega[Controle.instanciaControle().getJogadorAtual()]);
                                return true;
                            } else {
                                campoSelecionado[Controle.instanciaControle().getJogadorAtual()].moverPecaFrente();
                                pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.ATACAR2;
                                temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()] = true;
                                campoSelecionado[Controle.instanciaControle().getJogadorAtual()].setFundoVermelho();
                            }
                        }
                    } else {
                        /*if (campoSelecionado.peca1() == CAVALEIRO && campoSelecionado.vidaPeca1() == tratadores[CAVALEIRO - 1].getVidaTotal()) {
                         temCampoSelecionado = true;
                         pecaPega = Movimento.PEGARP1;
                         } else {*/
                        pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.INTERAGIR1;
                        campoSelecionado[Controle.instanciaControle().getJogadorAtual()].setFundoAzul();
                        temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()] = true;
                        //}
                    }
                }
            }
        } else {
            Tela2D.aviso("Movimento Obrigatorio!");
            if (campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca2() == TESTUDO) { //Movimento do Rei passando pela formacao do TESTUDO
                pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.PEGARP2;
                temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()] = tratadores[campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca2() - 1].pintaCampo(pecaPega[Controle.instanciaControle().getJogadorAtual()], campoSelecionado[Controle.instanciaControle().getJogadorAtual()], true);
                pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.PEGARP1;
            } else if (campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() == DAMA && campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca2() == SOLDADO) {
                pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.PEGARP1;
                temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()] = tratadores[campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() - 1].pintaCampo(pecaPega[Controle.instanciaControle().getJogadorAtual()], campoSelecionado[Controle.instanciaControle().getJogadorAtual()], true);
            } else {
                Controle.instanciaControle().setEstado(Controle.OPCAO_CAMPO);
                AmbienteOpcao.setReset(false);
                if (campoSelecionado[Controle.instanciaControle().getJogadorAtual()].pecasAmigas()) {
                    temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()] = true;
                    pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.INTERAGIR1;
                    campoSelecionado[Controle.instanciaControle().getJogadorAtual()].setFundoAzul();
                }

            }
        }

        return temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()];
    }

    public void soltarBotao() {
        Tela2D.desligaAviso();

        int corFundo = tabuleiro.campoSelecionado(campoSelecionado[Controle.instanciaControle().getJogadorAtual()].getFace(), campoSelecionado[Controle.instanciaControle().getJogadorAtual()].getLinha(), campoSelecionado[Controle.instanciaControle().getJogadorAtual()].getColuna()).getCorFundo();
        if (corFundo != MapaImagens.PRETO && corFundo != MapaImagens.BRANCO) {
            if (movimentoObrigatorio[Controle.instanciaControle().getJogadorAtual()] && campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca2() == TESTUDO && campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() == REI) { //Rei saindo da torre
                pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.PEGARP2;//isso nao esta sendo usado
                tratadores[campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca2() - 1].pintaCampo(pecaPega[Controle.instanciaControle().getJogadorAtual()], campoSelecionado[Controle.instanciaControle().getJogadorAtual()], false);
                pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.PEGARP1;
            } else if (movimentoObrigatorio[Controle.instanciaControle().getJogadorAtual()] && campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca2() == SOLDADO && campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() == BISPO) {//Tratatando poromocao do soldado
                tratadores[campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() - 1].pintaCampo(Movimento.PEGARP1, campoSelecionado[Controle.instanciaControle().getJogadorAtual()], false);
            } else if (movimentoObrigatorio[Controle.instanciaControle().getJogadorAtual()] && campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca2() == SOLDADO && campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() == SOLDADO) {//Tratatando soldado nascido
                tratadores[campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() - 1].pintaCampo(Movimento.PEGARP1, campoSelecionado[Controle.instanciaControle().getJogadorAtual()], false);
            } else if (pecaPega[Controle.instanciaControle().getJogadorAtual()] == Movimento.INTERAGIR1 && campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() == TESTUDOS) {
                tratadores[campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() - 1].pintaCampo(Movimento.PEGARP1, campoSelecionado[Controle.instanciaControle().getJogadorAtual()], false, campoSelecionado[Controle.instanciaControle().getJogadorAtual()].vidaPeca1());
            } else if (pecaPega[Controle.instanciaControle().getJogadorAtual()] % 2 == 0) {
                tratadores[campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() - 1].pintaCampo(Movimento.PEGARP1, campoSelecionado[Controle.instanciaControle().getJogadorAtual()], false);
            } else if (pecaPega[Controle.instanciaControle().getJogadorAtual()] % 2 == 1) {
                tratadores[campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca2() - 1].pintaCampo(Movimento.PEGARP2, campoSelecionado[Controle.instanciaControle().getJogadorAtual()], false);
            }
        }
        temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()] = false;
        Controle.instanciaControle().setEstado(Controle.JOGANDO);
    }

    public void realizarBotao(int botaoClicado) {
        Tela2D.desligaAviso();
        if (movimentoObrigatorio[Controle.instanciaControle().getJogadorAtual()] && campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca2() == SOLDADO && campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() == BISPO) {//Tratatando poromocao do soldado
            int peca = (!existePeca(campoSelecionado[Controle.instanciaControle().getJogadorAtual()].jogador1(), DAMA)) ? DAMA : BISPO;
            pecaPega[Controle.instanciaControle().getJogadorAtual()] = botaoClicado + peca;//Transforma o soldado para a peca que foi escolhida para promocao
            temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()] = tratadores[campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() - 1].pintaCampo(Movimento.PEGARP1, campoSelecionado[Controle.instanciaControle().getJogadorAtual()], true);
        } else {
            if (botaoClicado == 0) {
                if (pecaPega[Controle.instanciaControle().getJogadorAtual()] == Movimento.INTERAGIR1) {
                    if (temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()] = tratadores[campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() - 1].pintaCampo(Movimento.PEGARP1, campoSelecionado[Controle.instanciaControle().getJogadorAtual()], true)) {
                        pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.PEGARP1;
                    } else {
                        //Um problema quando pintaCampo retorna false, ou seja,
                        //no caso de dois SOLDADOS no mesmo campo, entao esta setado 
                        //movimentoObrigatorio=true, e ai nao eh possivel realizar o
                        //movimento. Como tratar isso?
                        //como o movimento não foi possivel a peca eh perdida
                        pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.ASSOPRO;
                        temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()] = false;
                        movimentoObrigatorio[Controle.instanciaControle().getJogadorAtual()] = false;
                        campoSelecionado[Controle.instanciaControle().getJogadorAtual()].setFundo();
                        Campo origem = (Campo) Xadrez.getCampoSelecianado().clone();
                        Controle.instanciaControle().enviaMovimento(origem, null, pecaPega[Controle.instanciaControle().getJogadorAtual()]);
                    }
                } else if (pecaPega[Controle.instanciaControle().getJogadorAtual()] == Movimento.ATACAR1) {
                    temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()] = tratadores[campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() - 1].pintaCampo(Movimento.PEGARP1, campoSelecionado[Controle.instanciaControle().getJogadorAtual()], true);
                    pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.PEGARP1;
                } else if (pecaPega[Controle.instanciaControle().getJogadorAtual()] == Movimento.ATACAR2) {
                    campoSelecionado[Controle.instanciaControle().getJogadorAtual()].moverPecaFrente();
                    temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()] = tratadores[campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca2() - 1].pintaCampo(Movimento.PEGARP2, campoSelecionado[Controle.instanciaControle().getJogadorAtual()], true);
                    pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.PEGARP2;
                } else if (pecaPega[Controle.instanciaControle().getJogadorAtual()] == Movimento.PEGARP1) {
                    temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()] = tratadores[campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() - 1].pintaCampo(Movimento.PEGARP1, campoSelecionado[Controle.instanciaControle().getJogadorAtual()], true);
                    pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.PEGARP1;
                }
            } else if (botaoClicado == 1) {
                if (pecaPega[Controle.instanciaControle().getJogadorAtual()] == Movimento.INTERAGIR1) {
                    temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()] = tratadores[campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca2() - 1].pintaCampo(Movimento.PEGARP2, campoSelecionado[Controle.instanciaControle().getJogadorAtual()], true);
                    pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.PEGARP2;
                } else if (pecaPega[Controle.instanciaControle().getJogadorAtual()] == Movimento.PEGARP1) {
                    if (campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() == TESTUDOS) { //Abrindo formacao TESTUDO
                        pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.INTERAGIR1;
                        temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()] = tratadores[campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() - 1].pintaCampo(Movimento.PEGARP1, campoSelecionado[Controle.instanciaControle().getJogadorAtual()], true, campoSelecionado[Controle.instanciaControle().getJogadorAtual()].vidaPeca1());
                    } else if (campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() == CAVALEIRO) { //Montando ou desmontando do Cavaleiro
                        temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()] = false;
                        movimentoObrigatorio[Controle.instanciaControle().getJogadorAtual()] = false;
                        Campo origem = (Campo) Xadrez.getCampoSelecianado().clone();
                        pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.INTERAGIR1;
                        Controle.instanciaControle().enviaMovimento(origem, null, pecaPega[Controle.instanciaControle().getJogadorAtual()]);
                    }
                } else {//Atacando uma peca que esta no mesmo campo
                    campoSelecionado[Controle.instanciaControle().getJogadorAtual()].setFundo();
                    /**
                     * Caso sua peca seja a segunda do campo, ela teve de ser
                     * movida para frente para na opcao aparcer o ataque dela
                     * agora deve trazer para traz novamente
                     */
                    if (pecaPega[Controle.instanciaControle().getJogadorAtual()] == Movimento.ATACAR2) {
                        campoSelecionado[Controle.instanciaControle().getJogadorAtual()].moverPecaFrente();
                    }
                    Campo origem = (Campo) Xadrez.getCampoSelecianado().clone();
                    Controle.instanciaControle().enviaMovimento(origem, null, pecaPega[Controle.instanciaControle().getJogadorAtual()]);
                    temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()] = false;
                }
            } else if (botaoClicado == 2) {
                //if (tratadores[campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() - 1].amigo(Movimento.PEGARP1, campoSelecionado[Controle.instanciaControle().getJogadorAtual()])) {
                if (pecaPega[Controle.instanciaControle().getJogadorAtual()] == Movimento.INTERAGIR1) {
                    campoSelecionado[Controle.instanciaControle().getJogadorAtual()].setFundo();
                    temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()] = false;
                    movimentoObrigatorio[Controle.instanciaControle().getJogadorAtual()] = false;
                    Campo origem = (Campo) Xadrez.getCampoSelecianado().clone();
                    Controle.instanciaControle().enviaMovimento(origem, null, pecaPega[Controle.instanciaControle().getJogadorAtual()]);
                }
                /*} else if (tratadores[campoSelecionado.peca2() - 1].amigo(Movimento.PEGARP2, campoSelecionado)) {
                 if (pecaPega[Controle.instanciaControle().getJogadorAtual()] == Movimento.INTERAGIR1) {
                 pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.INTERAGIR2;
                 Campo origem = (Campo) Xadrez.getCampoSelecianado().clone();
                 Controle.instanciaControle().enviaMovimento(origem, null, pecaPega[Controle.instanciaControle().getJogadorAtual()]);
                 }
                 }*/

            } else if (botaoClicado == 3) {
                //if (tratadores[campoSelecionado.peca2() - 1].amigo(Movimento.PEGARP2, campoSelecionado)) {
                if (pecaPega[Controle.instanciaControle().getJogadorAtual()] == Movimento.INTERAGIR1) {
                    pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.INTERAGIR2;
                    Campo origem = (Campo) Xadrez.getCampoSelecianado().clone();
                    Controle.instanciaControle().enviaMovimento(origem, null, pecaPega[Controle.instanciaControle().getJogadorAtual()]);
                }
                //}
                campoSelecionado[Controle.instanciaControle().getJogadorAtual()].setFundo();
                temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()] = false;
            }
        }
    }

    public boolean validaCampo(int face, int linha, int coluna) {
        Tela2D.desligaAviso();
        if (campoSelecionado[Controle.instanciaControle().getJogadorAtual()] != tabuleiro.campoSelecionado(face, linha, coluna)) {
            int corFundo = tabuleiro.campoSelecionado(face, linha, coluna).getCorFundo();
            if (corFundo != MapaImagens.PRETO && corFundo != MapaImagens.BRANCO) {
                if (movimentoObrigatorio[Controle.instanciaControle().getJogadorAtual()] && campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca2() == TESTUDO && campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() == REI) { //Rei saindo da torre
                    pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.PEGARP2;//isso nao esta sendo usado
                    tratadores[campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca2() - 1].pintaCampo(pecaPega[Controle.instanciaControle().getJogadorAtual()], campoSelecionado[Controle.instanciaControle().getJogadorAtual()], false);
                    pecaPega[Controle.instanciaControle().getJogadorAtual()] = Movimento.PEGARP1;
                } else if (movimentoObrigatorio[Controle.instanciaControle().getJogadorAtual()] && campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca2() == SOLDADO && campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() == BISPO) {//Tratatando poromocao do soldado
                    tratadores[campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() - 1].pintaCampo(Movimento.PEGARP1, campoSelecionado[Controle.instanciaControle().getJogadorAtual()], false);
                } else if (movimentoObrigatorio[Controle.instanciaControle().getJogadorAtual()] && campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca2() == SOLDADO && campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() == SOLDADO) {//Tratatando soldado nascido
                    tratadores[campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() - 1].pintaCampo(Movimento.PEGARP1, campoSelecionado[Controle.instanciaControle().getJogadorAtual()], false);
                } else if (pecaPega[Controle.instanciaControle().getJogadorAtual()] == Movimento.INTERAGIR1 && campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() == TESTUDOS) {
                    tratadores[campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() - 1].pintaCampo(Movimento.PEGARP1, campoSelecionado[Controle.instanciaControle().getJogadorAtual()], false, campoSelecionado[Controle.instanciaControle().getJogadorAtual()].vidaPeca1());
                } else if (pecaPega[Controle.instanciaControle().getJogadorAtual()] % 2 == 0) {
                    tratadores[campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca1() - 1].pintaCampo(Movimento.PEGARP1, campoSelecionado[Controle.instanciaControle().getJogadorAtual()], false);
                } else if (pecaPega[Controle.instanciaControle().getJogadorAtual()] % 2 == 1) {
                    tratadores[campoSelecionado[Controle.instanciaControle().getJogadorAtual()].peca2() - 1].pintaCampo(Movimento.PEGARP2, campoSelecionado[Controle.instanciaControle().getJogadorAtual()], false);
                }

                temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()] = false;
                movimentoObrigatorio[Controle.instanciaControle().getJogadorAtual()] = false;
            }
        }

        return !temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()];
    }

    public boolean tratarExecaoMovimento(int ordem, Movimento movimentoRealizado) {
        int[] iClicado = movimentoRealizado.getCampoSelecionado();
        Campo cClicado = tabuleiro.campoSelecionado(iClicado[0], iClicado[1], iClicado[2]);

        if (cClicado.pecasAmigas()) {
            if (cClicado.peca2() == SOLDADO && (cClicado.peca1() == BISPO || cClicado.peca1() == DAMA)) {
                int[] iDestino = movimentoRealizado.getCampoDestino();
                Campo cDestino = tabuleiro.campoSelecionado(iDestino[0], iDestino[1], iDestino[2]);
                int p1 = cClicado.peca1();

                if (!tratadores[p1 - 1].realizaMovimento(Movimento.PEGARP1, cClicado, cDestino)) {
                    if (Controle.instanciaControle().getUsuario().getOrdem() == ordem) {
                        campoSelecionado[Controle.instanciaControle().getJogadorAtual()] = cDestino;
                        movimentoObrigatorio[Controle.instanciaControle().getJogadorAtual()] = true;
                    } /*else {
                     movimentoObrigatorio = false;
                     }*/

                }
                if (p1 == BISPO) {//Promocao de SOLDADO
                    int peca = movimentoRealizado.getTipoMovimento();
                    cClicado.addPeca1(peca, tratadores[peca - 1].getVidaTotal());//Promovendo soldado
                } else if (p1 == DAMA) {//Nascendo mais um SOLDADO
//                        if (movimentoObrigatorio) {
//                            pecaPega = Movimento.ASSOPRO;
//                            Campo origem = (Campo) cClicado.clone();
//                            Controle.instanciaControle().enviaMovimento(origem, null, pecaPega);
////                        } else {
                    if (Controle.instanciaControle().getUsuario().getOrdem() == ordem) {
                        campoSelecionado[Controle.instanciaControle().getJogadorAtual()] = cClicado;
                        movimentoObrigatorio[Controle.instanciaControle().getJogadorAtual()] = true;
                    }
                    cClicado.addJogador2(ordem + 1);
                    cClicado.addPeca2(SOLDADO, tratadores[SOLDADO - 1].getVidaTotal());
//                        }
                }
                return true;
            } else if (cClicado.peca1() == REI && cClicado.peca2() == DAMA) {
                int[] iDestino = movimentoRealizado.getCampoDestino();
                Campo cDestino = tabuleiro.campoSelecionado(iDestino[0], iDestino[1], iDestino[2]);

                int p1 = (movimentoRealizado.getTipoMovimento() == Movimento.PEGARP1) ? cClicado.peca1() : cClicado.peca2();

                if (!tratadores[p1 - 1].realizaMovimento(movimentoRealizado.getTipoMovimento(), cClicado, cDestino)) {
                    if (Controle.instanciaControle().getUsuario().getOrdem() == ordem) {
                        campoSelecionado[Controle.instanciaControle().getJogadorAtual()] = cDestino;
                        movimentoObrigatorio[Controle.instanciaControle().getJogadorAtual()] = true;
                    }/* else {
                     movimentoObrigatorio = false;
                     }*/

                }
                cClicado.addJogador2(ordem + 1);
                cClicado.addPeca2(PRINCIPE, tratadores[PRINCIPE - 1].getVidaTotal());
                if (Controle.instanciaControle().getUsuario().getOrdem() == ordem) {
                    campoSelecionado[Controle.instanciaControle().getJogadorAtual()] = cClicado;
                    movimentoObrigatorio[Controle.instanciaControle().getJogadorAtual()] = true;
                }
                return true;
            }
        } else if (movimentoRealizado.getTipoMovimento() == Movimento.INTERAGIR1 && cClicado.peca1() == TESTUDOS && cClicado.peca2() == 0) { // Abrindo TESTUDOS
            int[] iDestino = movimentoRealizado.getCampoDestino();
            Campo cDestino = tabuleiro.campoSelecionado(iDestino[0], iDestino[1], iDestino[2]);

            Testudo.abrirTestudos(cClicado, cDestino);
//            cClicado.addJogador1(ordem + 1);
//            cClicado.addPeca1(Xadrez.TESTUDO, tratadores[TESTUDO-1].getVidaTotal());
//            cDestino.addJogador1(ordem + 1);
//            cDestino.addPeca1(Xadrez.TESTUDO, tratadores[TESTUDO-1].getVidaTotal());
//            
            return true;
        } else if (cClicado.peca1() == TESTUDO || cClicado.peca2() == TESTUDO) {//Fecha Testudo
            int vida = Testudo.apagarTestudos(cClicado);
            if (cClicado.campoVazio()) {
                cClicado.addPeca1(Xadrez.TESTUDOS, vida);
                cClicado.addJogador1(ordem + 1);
            } else {
                cClicado.addPeca2(Xadrez.TESTUDOS, vida);
                cClicado.addJogador2(ordem + 1);
                cClicado.moverPecaFrente();
                tratadores[cClicado.peca1() - 1].realizaAcao(cClicado);//Testudo fecha atacando
            }
            return true;
        }

        return false;
    }

    public void realizarMovimento(int ordem, int[] iClicado, int[] iDestino, int pecaClicada) {
        Campo cClicado = tabuleiro.campoSelecionado(iClicado[0], iClicado[1], iClicado[2]);
        Campo cDestino = tabuleiro.campoSelecionado(iDestino[0], iDestino[1], iDestino[2]);
        boolean obrigatorio = false;
        if (pecaClicada == Movimento.PEGARP1) {
            obrigatorio = !tratadores[cClicado.peca1() - 1].realizaMovimento(pecaClicada, cClicado, cDestino);
        } else if (pecaClicada == Movimento.PEGARP2) {
            obrigatorio = !tratadores[cClicado.peca2() - 1].realizaMovimento(pecaClicada, cClicado, cDestino);
        }
        if (obrigatorio) {
            if (Controle.instanciaControle().getUsuario().getOrdem() == ordem) {
                campoSelecionado[Controle.instanciaControle().getJogadorAtual()] = cDestino;
                movimentoObrigatorio[Controle.instanciaControle().getJogadorAtual()] = true;
            } else {
                movimentoObrigatorio[Controle.instanciaControle().getJogadorAtual()] = false;
            }
        }
    }

    public void realizarAcao(int ordem, int[] iClicado, int pecaClicada) {
        Campo cClicado = tabuleiro.campoSelecionado(iClicado[0], iClicado[1], iClicado[2]);

        boolean obrigatorio = false;
        if (pecaClicada == Movimento.ASSOPRO) {
            cClicado.addJogador2(0);
            cClicado.addPeca2(0, 0);
        } else if (pecaClicada == Movimento.ATACAR1) {
            tratadores[cClicado.peca1() - 1].realizaAcao(cClicado);
        } else if (pecaClicada == Movimento.ATACAR2) {
            cClicado.moverPecaFrente();
            tratadores[cClicado.peca1() - 1].realizaAcao(cClicado);
        } else {
            if (pecaClicada == Movimento.INTERAGIR1) {
                obrigatorio = !tratadores[cClicado.peca1() - 1].realizaIteracao(pecaClicada, cClicado);
            } else if (pecaClicada == Movimento.INTERAGIR2) {
                //cClicado.moverPecaFrente();
                obrigatorio = !tratadores[cClicado.peca2() - 1].realizaIteracao(pecaClicada, cClicado);
            }
            if (obrigatorio) {
                if (Controle.instanciaControle().getUsuario().getOrdem() == ordem) {
                    campoSelecionado[Controle.instanciaControle().getJogadorAtual()] = cClicado;
                    movimentoObrigatorio[Controle.instanciaControle().getJogadorAtual()] = true;
                }/* else {
                 movimentoObrigatorio = false;
                 }*/

            }
        }

    }

    public boolean temCampoSelecionado() {
        return temCampoSelecionado[Controle.instanciaControle().getJogadorAtual()];
    }

    public Campo[] getCampos() {
        return tabuleiro.getCampos();
    }

    public static Campo getCampoSelecianado() {
        return campoSelecionado[Controle.instanciaControle().getJogadorAtual()];
    }

    public static Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public static int getPecaPega() {
        return pecaPega[Controle.instanciaControle().getJogadorAtual()];
    }

    public static Peca[] getTratadores() {
        return tratadores;
    }
}
