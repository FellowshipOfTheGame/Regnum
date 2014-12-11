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
import Visual.Plano.AmbienteOpcao;
import Visual.Plano.MapaImagens;

/**
 *
 * @author marcius
 */
public class Xadrez {

    private static Tabuleiro tabuleiro;
    private static Campo campoSelecionado;
    private static int pecaPega;
    private static boolean temCampoSelecionado;
    private static boolean movimentoObrigatorio;

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
    }

    public boolean selecionarCampo(int face, int linha, int coluna, int usuario) {
        if (!movimentoObrigatorio) {
            campoSelecionado = null;
            pecaPega = -1;

            campoSelecionado = tabuleiro.campoSelecionado(face, linha, coluna);
            if (campoSelecionado.existeJogador(usuario) != 0) {
                if (!campoSelecionado.campoCheio()) {
                    if (campoSelecionado.peca1() == TESTUDOS) {
                        Controle.instanciaControle().setEstado(Controle.OPCAO_CAMPO);
                        AmbienteOpcao.setReset(false);
                        temCampoSelecionado = true;
                        pecaPega = Movimento.PEGARP1;
                    } else if (campoSelecionado.peca1() == TESTUDO) {
                        Campo origem = (Campo) Xadrez.getCampoSelecianado().clone();
                        pecaPega = Movimento.PEGARP1;
                        Controle.instanciaControle().enviaMovimento(origem, null, pecaPega);
                    } else if (campoSelecionado.peca1() == CAVALEIRO && campoSelecionado.vidaPeca1() > tratadores[CAVALEIRO - 1].getVidaTotal()) {
                        Controle.instanciaControle().setEstado(Controle.OPCAO_CAMPO);
                        AmbienteOpcao.setReset(false);
                        temCampoSelecionado = true;
                        pecaPega = Movimento.PEGARP1;
                    } else {
                        pecaPega = Movimento.PEGARP1;
                        temCampoSelecionado = tratadores[campoSelecionado.peca1() - 1].pintaCampo(pecaPega, campoSelecionado, true);
                    }
                } else {
                    Controle.instanciaControle().setEstado(Controle.OPCAO_CAMPO);
                    AmbienteOpcao.setReset(false);
                    if (campoSelecionado.pecasInimigas()) {
                        if (usuario == campoSelecionado.jogador1()) {
                            pecaPega = Movimento.ATACAR1;
                            temCampoSelecionado = true;
                            campoSelecionado.setFundoVermelho();
                        } else if (usuario == campoSelecionado.jogador2()) {
                            if (campoSelecionado.peca2() == TESTUDO) {
                                Controle.instanciaControle().setEstado(Controle.JOGANDO);
                                Campo origem = (Campo) Xadrez.getCampoSelecianado().clone();
                                pecaPega = Movimento.PEGARP2;
                                Controle.instanciaControle().enviaMovimento(origem, null, pecaPega);
                            } else {
                                campoSelecionado.moverPecaFrente();
                                pecaPega = Movimento.ATACAR2;
                                temCampoSelecionado = true;
                                campoSelecionado.setFundoVermelho();
                            }
                        }
                    } else {
                        /*if (campoSelecionado.peca1() == CAVALEIRO && campoSelecionado.vidaPeca1() == tratadores[CAVALEIRO - 1].getVidaTotal()) {
                         temCampoSelecionado = true;
                         pecaPega = Movimento.PEGARP1;
                         } else {*/
                        pecaPega = Movimento.INTERAGIR1;
                        campoSelecionado.setFundoAzul();
                        temCampoSelecionado = true;
                        //}
                    }
                }
            }
        } else {
            if (campoSelecionado.peca2() == TESTUDO) { //Movimento do Rei passando pela formacao do TESTUDO
                pecaPega = Movimento.PEGARP2;
                temCampoSelecionado = tratadores[campoSelecionado.peca2() - 1].pintaCampo(pecaPega, campoSelecionado, true);
                pecaPega = Movimento.PEGARP1;
            } else if (campoSelecionado.peca1() == DAMA && campoSelecionado.peca2() == SOLDADO) {
                pecaPega = Movimento.PEGARP1;
                temCampoSelecionado = tratadores[campoSelecionado.peca1() - 1].pintaCampo(pecaPega, campoSelecionado, true);
            } else {
                Controle.instanciaControle().setEstado(Controle.OPCAO_CAMPO);
                AmbienteOpcao.setReset(false);
                if (campoSelecionado.pecasAmigas()) {
                    temCampoSelecionado = true;
                    pecaPega = Movimento.INTERAGIR1;
                    campoSelecionado.setFundoAzul();
                } 
                
            }
        }

        return temCampoSelecionado;
    }

    public void realizarBotao(int botaoClicado) {
        if (movimentoObrigatorio && campoSelecionado.peca2() == SOLDADO && campoSelecionado.peca1() == BISPO) {//Tratatando poromocao do soldado
            int peca = (!existePeca(campoSelecionado.jogador1(), DAMA)) ? DAMA : BISPO;
            pecaPega = botaoClicado + peca;//Transforma o soldado para a peca que foi escolhida para promocao
            temCampoSelecionado = tratadores[campoSelecionado.peca1() - 1].pintaCampo(Movimento.PEGARP1, campoSelecionado, true);
        } else {
            if (botaoClicado == 0) {
                if (pecaPega == Movimento.INTERAGIR1) {
                    if (temCampoSelecionado = tratadores[campoSelecionado.peca1() - 1].pintaCampo(Movimento.PEGARP1, campoSelecionado, true)) {
                        pecaPega = Movimento.PEGARP1;
                    } else {
                        //Um problema quando pintaCampo retorna false, ou seja,
                        //no caso de dois SOLDADOS no mesmo campo, entao esta setado 
                        //movimentoObrigatorio=true, e ai nao eh possivel realizar o
                        //movimento. Como tratar isso?
                        //como o movimento não foi possivel a peca eh perdida
                        pecaPega = Movimento.ASSOPRO;
                        temCampoSelecionado = false;
                        movimentoObrigatorio = false;
                        campoSelecionado.setFundo();
                        Campo origem = (Campo) Xadrez.getCampoSelecianado().clone();
                        Controle.instanciaControle().enviaMovimento(origem, null, pecaPega);
                    }
                } else if (pecaPega == Movimento.ATACAR1) {
                    temCampoSelecionado = tratadores[campoSelecionado.peca1() - 1].pintaCampo(Movimento.PEGARP1, campoSelecionado, true);
                    pecaPega = Movimento.PEGARP1;
                } else if (pecaPega == Movimento.ATACAR2) {
                    campoSelecionado.moverPecaFrente();
                    temCampoSelecionado = tratadores[campoSelecionado.peca2() - 1].pintaCampo(Movimento.PEGARP2, campoSelecionado, true);
                    pecaPega = Movimento.PEGARP2;
                } else if (pecaPega == Movimento.PEGARP1) {
                    temCampoSelecionado = tratadores[campoSelecionado.peca1() - 1].pintaCampo(Movimento.PEGARP1, campoSelecionado, true);
                    pecaPega = Movimento.PEGARP1;
                }
            } else if (botaoClicado == 1) {
                if (pecaPega == Movimento.INTERAGIR1) {
                    temCampoSelecionado = tratadores[campoSelecionado.peca2() - 1].pintaCampo(Movimento.PEGARP2, campoSelecionado, true);
                    pecaPega = Movimento.PEGARP2;
                } else if (pecaPega == Movimento.PEGARP1) {
                    if (campoSelecionado.peca1() == TESTUDOS) { //Abrindo formacao TESTUDO
                        pecaPega = Movimento.INTERAGIR1;
                        temCampoSelecionado = tratadores[campoSelecionado.peca1() - 1].pintaCampo(Movimento.PEGARP1, campoSelecionado, true, campoSelecionado.vidaPeca1());
                    } else if (campoSelecionado.peca1() == CAVALEIRO) { //Montando ou desmontando do Cavaleiro
                        temCampoSelecionado = false;
                        movimentoObrigatorio = false;
                        Campo origem = (Campo) Xadrez.getCampoSelecianado().clone();
                        pecaPega = Movimento.INTERAGIR1;
                        Controle.instanciaControle().enviaMovimento(origem, null, pecaPega);
                    }
                } else {//Atacando uma peca que esta no mesmo campo
                    campoSelecionado.setFundo();
                    /**
                     * Caso sua peca seja a segunda do campo, ela teve de ser
                     * movida para frente para na opcao aparcer o ataque dela
                     * agora deve trazer para traz novamente
                     */
                    if (pecaPega == Movimento.ATACAR2) {
                        campoSelecionado.moverPecaFrente();
                    }
                    Campo origem = (Campo) Xadrez.getCampoSelecianado().clone();
                    Controle.instanciaControle().enviaMovimento(origem, null, pecaPega);
                    temCampoSelecionado = false;
                }
            } else if (botaoClicado == 2) {
                //if (tratadores[campoSelecionado.peca1() - 1].amigo(Movimento.PEGARP1, campoSelecionado)) {
                if (pecaPega == Movimento.INTERAGIR1) {
                    campoSelecionado.setFundo();
                    temCampoSelecionado = false;
                    movimentoObrigatorio = false;
                    Campo origem = (Campo) Xadrez.getCampoSelecianado().clone();
                    Controle.instanciaControle().enviaMovimento(origem, null, pecaPega);
                }
                /*} else if (tratadores[campoSelecionado.peca2() - 1].amigo(Movimento.PEGARP2, campoSelecionado)) {
                 if (pecaPega == Movimento.INTERAGIR1) {
                 pecaPega = Movimento.INTERAGIR2;
                 Campo origem = (Campo) Xadrez.getCampoSelecianado().clone();
                 Controle.instanciaControle().enviaMovimento(origem, null, pecaPega);
                 }
                 }*/

            } else if (botaoClicado == 3) {
                //if (tratadores[campoSelecionado.peca2() - 1].amigo(Movimento.PEGARP2, campoSelecionado)) {
                if (pecaPega == Movimento.INTERAGIR1) {
                    pecaPega = Movimento.INTERAGIR2;
                    Campo origem = (Campo) Xadrez.getCampoSelecianado().clone();
                    Controle.instanciaControle().enviaMovimento(origem, null, pecaPega);
                }
                //}
                campoSelecionado.setFundo();
                temCampoSelecionado = false;
            }
        }
    }

    public boolean validaCampo(int face, int linha, int coluna) {
        if (campoSelecionado != tabuleiro.campoSelecionado(face, linha, coluna)) {
            int corFundo = tabuleiro.campoSelecionado(face, linha, coluna).getCorFundo();
            if (corFundo != MapaImagens.PRETO && corFundo != MapaImagens.BRANCO) {
                if (movimentoObrigatorio && campoSelecionado.peca2() == TESTUDO && campoSelecionado.peca1() == REI) { //Rei saindo da torre
                    pecaPega = Movimento.PEGARP2;//isso nao esta sendo usado
                    tratadores[campoSelecionado.peca2() - 1].pintaCampo(pecaPega, campoSelecionado, false);
                    pecaPega = Movimento.PEGARP1;
                } else if (movimentoObrigatorio && campoSelecionado.peca2() == SOLDADO && campoSelecionado.peca1() == BISPO) {//Tratatando poromocao do soldado
                    tratadores[campoSelecionado.peca1() - 1].pintaCampo(Movimento.PEGARP1, campoSelecionado, false);
                } else if (movimentoObrigatorio && campoSelecionado.peca2() == SOLDADO && campoSelecionado.peca1() == SOLDADO) {//Tratatando soldado nascido
                    tratadores[campoSelecionado.peca1() - 1].pintaCampo(Movimento.PEGARP1, campoSelecionado, false);
                } else if (pecaPega == Movimento.INTERAGIR1 && campoSelecionado.peca1() == TESTUDOS) {
                    tratadores[campoSelecionado.peca1() - 1].pintaCampo(Movimento.PEGARP1, campoSelecionado, false, campoSelecionado.vidaPeca1());
                } else if (pecaPega % 2 == 0) {
                    tratadores[campoSelecionado.peca1() - 1].pintaCampo(Movimento.PEGARP1, campoSelecionado, false);
                } else if (pecaPega % 2 == 1) {
                    tratadores[campoSelecionado.peca2() - 1].pintaCampo(Movimento.PEGARP2, campoSelecionado, false);
                }

                temCampoSelecionado = false;
                movimentoObrigatorio = false;
            }
        }

        return !temCampoSelecionado;
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
                        campoSelecionado = cDestino;
                        movimentoObrigatorio = true;
                    } /*else {
                        movimentoObrigatorio = false;
                    }*/
                }
                if (p1 == BISPO) {//Promocao de SOLDADO
                    cClicado.addPeca1(movimentoRealizado.getTipoMovimento(), tratadores[movimentoRealizado.getTipoMovimento() - 1].getVidaTotal());//Promovendo soldado
                } else if (p1 == DAMA) {//Nascendo mais um SOLDADO
//                        if (movimentoObrigatorio) {
//                            pecaPega = Movimento.ASSOPRO;
//                            Campo origem = (Campo) cClicado.clone();
//                            Controle.instanciaControle().enviaMovimento(origem, null, pecaPega);
////                        } else {
                    if (Controle.instanciaControle().getUsuario().getOrdem() == ordem) {
                        campoSelecionado = cClicado;
                        movimentoObrigatorio = true;
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
                        campoSelecionado = cDestino;
                        movimentoObrigatorio = true;
                    }/* else {
                        movimentoObrigatorio = false;
                    }*/
                }
                cClicado.addJogador2(ordem + 1);
                cClicado.addPeca2(PRINCIPE, tratadores[PRINCIPE - 1].getVidaTotal());
                if (Controle.instanciaControle().getUsuario().getOrdem() == ordem) {
                    campoSelecionado = cClicado;
                    movimentoObrigatorio = true;
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
                campoSelecionado = cDestino;
                movimentoObrigatorio = true;
//            } else {
//                movimentoObrigatorio = false;
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
                    campoSelecionado = cClicado;
                    movimentoObrigatorio = true;
                }/* else {
                    movimentoObrigatorio = false;
                }*/
            }
        }

    }

    public boolean temCampoSelecionado() {
        return temCampoSelecionado;
    }

    public Campo[] getCampos() {
        return tabuleiro.getCampos();
    }

    public static Campo getCampoSelecianado() {
        return campoSelecionado;
    }

    public static Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public static int getPecaPega() {
        return pecaPega;
    }

    public static Peca[] getTratadores() {
        return tratadores;
    }

}
