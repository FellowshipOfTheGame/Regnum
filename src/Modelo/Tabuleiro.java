/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Controle.Xadrez;

/**
 *
 * @author marcius
 */
public class Tabuleiro {

    private Campo[] campos;

    /**
     *
     * @param iJogadores : numero de jogadores presente no jogo
     */
    public Tabuleiro(int iJogadores) {
        campos = new Campo[iJogadores * 90];
        for (int f = 0; f < 2 * iJogadores; f++) {
            for (int l = 1; l < 10; l++) {
                for (int c = 0; c < l; c++) {
                    campos[f * 45 + (l * (l - 1)) / 2 + c] = new Campo(f, l - 1, c);
                }
            }
        }

        /*inserindo Soldados*/
        for (int f = 0; f < 2 * iJogadores; f += 2) {
            for (int l = 7; l < 9; l++) {
                for (int c = 0; c < l; c++) {
                    /*linha de peoes*/
                    campos[f * 45 + (l * (l - 1)) / 2 + c].addJogador1(f / 2 + 1);
                    /* P: tipo da peca 7 - Soldado*/
                    campos[f * 45 + (l * (l - 1)) / 2 + c].addPeca1(Xadrez.SOLDADO, Xadrez.getTratadores()[Xadrez.SOLDADO-1].getVidaTotal());
                }
            }
            campos[f * 45 + 36 + 0].addJogador1(f / 2 + 1);
            campos[f * 45 + 36 + 0].addPeca1(Xadrez.SOLDADO, Xadrez.getTratadores()[Xadrez.SOLDADO-1].getVidaTotal());
            campos[f * 45 + 36 + 8].addJogador1(f / 2 + 1);
            campos[f * 45 + 36 + 8].addPeca1(Xadrez.SOLDADO, Xadrez.getTratadores()[Xadrez.SOLDADO-1].getVidaTotal());
        }

        for (int f = 0; f < 2 * iJogadores; f += 2) {
            
            campos[f * 45 + 36 + 1].addJogador1(f / 2 + 1);
            campos[f * 45 + 36 + 1].addPeca1(Xadrez.TESTUDOS, Xadrez.getTratadores()[Xadrez.TESTUDOS-1].getVidaTotal());
            campos[f * 45 + 36 + 7].addJogador1(f / 2 + 1);
            campos[f * 45 + 36 + 7].addPeca1(Xadrez.TESTUDOS, Xadrez.getTratadores()[Xadrez.TESTUDOS-1].getVidaTotal());

            
            campos[f * 45 + 28 + 2].addJogador1(f / 2 + 1);
            campos[f * 45 + 28 + 2].addPeca1(Xadrez.CAVALEIRO, Xadrez.getTratadores()[Xadrez.CAVALEIRO-1].getVidaTotal());
            campos[f * 45 + 36 + 6].addJogador1(f / 2 + 1);
            campos[f * 45 + 36 + 6].addPeca1(Xadrez.CAVALEIRO, Xadrez.getTratadores()[Xadrez.CAVALEIRO-1].getVidaTotal());

            
            campos[f * 45 + 36 + 2].addJogador1(f / 2 + 1);
            campos[f * 45 + 36 + 2].addPeca1(Xadrez.BISPO, Xadrez.getTratadores()[Xadrez.BISPO-1].getVidaTotal());
            campos[f * 45 + 28 + 5].addJogador1(f / 2 + 1);
            campos[f * 45 + 28 + 5].addPeca1(Xadrez.BISPO, Xadrez.getTratadores()[Xadrez.BISPO-1].getVidaTotal());

            
            campos[f * 45 + 36 + 3].addJogador1(f / 2 + 1);
            campos[f * 45 + 36 + 3].addPeca1(Xadrez.DAMA, Xadrez.getTratadores()[Xadrez.DAMA-1].getVidaTotal());

            
            campos[f * 45 + 36 + 5].addJogador1(f / 2 + 1);
            campos[f * 45 + 36 + 5].addPeca1(Xadrez.REI, Xadrez.getTratadores()[Xadrez.REI-1].getVidaTotal());
        }

        for (int f = 0; f < 2 * iJogadores; f++) {
            for (int l = 1; l < 10; l++) {
                for (int c = 0; c < l; c++) {
                    if (l != 9) {//excluindo ultima linha
                        campos[f * 45 + (l * (l - 1)) / 2 + c].setFaceAbaixoDireita(campos[f * 45 + ((l + 1) * l) / 2 + c + 1]);
                        campos[f * 45 + (l * (l - 1)) / 2 + c].setFaceAbaixoEsquerda(campos[f * 45 + ((l + 1) * l) / 2 + c]);
                    }
                    if (c != l - 1) {//excluindo ultima coluna antes de mudar de face
                        campos[f * 45 + (l * (l - 1)) / 2 + c].setFaceAcimaDireita(campos[f * 45 + ((l - 1) * (l - 2)) / 2 + c]);
                    } else {
                        campos[f * 45 + (l * (l - 1)) / 2 + c].setFaceAcimaDireita(campos[((f + 1) % (2 * iJogadores)) * 45 + (l * (l - 1)) / 2 + 0]);
                    }
                    if (c != 0) {//excluindo primeira coluna assim que mudou de face
                        campos[f * 45 + (l * (l - 1)) / 2 + c].setFaceAcimaEsquerda(campos[f * 45 + ((l - 1) * (l - 2)) / 2 + c - 1]);
                    } else {
                        campos[f * 45 + (l * (l - 1)) / 2 + c].setFaceAcimaEsquerda(campos[((f + 2 * iJogadores - 1) % (2 * iJogadores)) * 45 + (l * (l - 1)) / 2 + l - 1]);
                    }

                    if (c != l - 1) {//excluindo ultima coluna antes de mudar de face
                        campos[f * 45 + (l * (l - 1)) / 2 + c].setQuinaDireita(campos[f * 45 + (l * (l - 1)) / 2 + c + 1]);
                    } else if (l != 9) {
                        campos[f * 45 + (l * (l - 1)) / 2 + c].setQuinaDireita(campos[((f + 1) % (2 * iJogadores)) * 45 + ((l + 1) * l) / 2 + 0]);
                    }
                    if (c != 0) {//excluindo primeira coluna assim que mudou de face
                        campos[f * 45 + (l * (l - 1)) / 2 + c].setQuinaEsquerda(campos[f * 45 + (l * (l - 1)) / 2 + c - 1]);
                    } else if (l != 9) {
                        campos[f * 45 + (l * (l - 1)) / 2 + c].setQuinaEsquerda(campos[((f + 2 * iJogadores - 1) % (2 * iJogadores)) * 45 + ((l + 1) * l) / 2 + l]);
                    }
                    if (l != 9 && l != 8) {
                        campos[f * 45 + (l * (l - 1)) / 2 + c].setQuinaAbaixo(campos[f * 45 + ((l + 2) * (l + 1)) / 2 + c + 1]);
                    } else {
                        campos[f * 45 + (l * (l - 1)) / 2 + c].setQuinaAbaixo(null);
                    }
                    if (l == 1) {//Centro do tabuleiro
                        campos[f * 45 + 0 + 0].setQuinaAcima(null);
                    } else {
                        if (c == 0) {//excluindo primeira coluna assim que mudou de face
                            campos[f * 45 + (l * (l - 1)) / 2 + c].setQuinaAcima(campos[((f + 2 * iJogadores - 1) % (2 * iJogadores)) * 45 + ((l - 1) * (l - 2)) / 2 + l - 2]);
                        } else if (c == l - 1) {//excluindo primeira coluna assim que mudou de face
                            campos[f * 45 + (l * (l - 1)) / 2 + c].setQuinaAcima(campos[((f + 1) % (2 * iJogadores)) * 45 + ((l - 1) * (l - 2)) / 2 + 0]);
                        } else {
                            campos[f * 45 + (l * (l - 1)) / 2 + c].setQuinaAcima(campos[f * 45 + ((l - 2) * (l - 3)) / 2 + c - 1]);
                        }
                    }
                }
            }
        }
    }

    public Campo campoSelecionado(int face, int linha, int coluna) {
        return campos[face * 45 + ((linha + 1) * linha) / 2 + coluna];
    }

    public Campo[] getCampos() {
        return campos;
    }

    public static Campo[] campos() {
        Campo[] tmpcampos = new Campo[45];

        for (int l = 1; l < 10; l++) {
            for (int c = 0; c < l; c++) {
                tmpcampos[(l * (l - 1)) / 2 + c] = new Campo(0, l - 1, c);
            }
        }


        /*inserindo Soldados*/
        
            for (int l = 7; l < 9; l++) {
                for (int c = 0; c < l; c++) {
                    /*linha de solados*/
                    tmpcampos[(l * (l - 1)) / 2 + c].addJogador1(1);
                    /* P: tipo da peca 7 - Soldado*/
                    tmpcampos[(l * (l - 1)) / 2 + c].addPeca1(Xadrez.SOLDADO, 1);
                }
            }
        tmpcampos[36 + 0].addJogador1(1);
        tmpcampos[36 + 0].addPeca1(Xadrez.SOLDADO, 1);
        tmpcampos[36 + 8].addJogador1(1);
        tmpcampos[36 + 8].addPeca1(Xadrez.SOLDADO, 1);

        /* P: tipo da peca 6 - Testudos */
        tmpcampos[36 + 1].addJogador1(1);
        tmpcampos[36 + 1].addPeca1(Xadrez.TESTUDOS, 1);
        tmpcampos[36 + 7].addJogador1(1);
        tmpcampos[36 + 7].addPeca1(Xadrez.TESTUDOS, 1);

        /* P: tipo da peca 5 - Cavaleiro */
        tmpcampos[28 + 2].addJogador1(1);
        tmpcampos[28 + 2].addPeca1(Xadrez.CAVALEIRO, 1);
        tmpcampos[36 + 6].addJogador1(1);
        tmpcampos[36 + 6].addPeca1(Xadrez.CAVALEIRO, 1);

        /* P: tipo da peca 4 - Bispo */
        tmpcampos[36 + 2].addJogador1(1);
        tmpcampos[36 + 2].addPeca1(Xadrez.BISPO, 1);
        tmpcampos[28 + 5].addJogador1(1);
        tmpcampos[28 + 5].addPeca1(Xadrez.BISPO, 1);

        /* P: tipo da peca 3 - Dama */
        tmpcampos[36 + 3].addJogador1(1);
        tmpcampos[36 + 3].addPeca1(Xadrez.DAMA, 1);
        
        /* P: tipo da peca 2 - Principe */
        tmpcampos[36 + 4].addJogador1(1);
        tmpcampos[36 + 4].addPeca1(Xadrez.PRINCIPE, 1);

        /* P: tipo da peca 1 - Rei */
        tmpcampos[36 + 5].addJogador1(1);
        tmpcampos[36 + 5].addPeca1(Xadrez.REI, 1);

        return tmpcampos;
    }
}
