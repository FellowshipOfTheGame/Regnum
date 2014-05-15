/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.Peca.Bispo;
import Modelo.Peca.Cavaleiro;
import Modelo.Peca.Dama;
import Modelo.Peca.Peca;
import Modelo.Peca.Principe;
import Modelo.Peca.Rei;
import Modelo.Peca.Soldado;
import Modelo.Peca.Testudo;

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
            for (int c = 0; c < 8; c++) {
                campos[f * 45 + 28 + c].addJogador1(f / 2 + 1);
                /* P: tipo da peca 1 - Rei | 2 - Dama | 3 - Principe | 4 - Bispo | 5 - Cavaleiro | 6 - Testudo | 7 - Soldado*/
                campos[f * 45 + 28 + c].addPeca1(7, 1);
            }
        }

        for (int f = 0; f < 2 * iJogadores; f += 2) {
            /* P: tipo da peca 6 - Testudo */
            campos[f * 45 + 36 + 0].addJogador1(f / 2 + 1);
            campos[f * 45 + 36 + 0].addPeca1(6, 4);
            campos[f * 45 + 36 + 8].addJogador1(f / 2 + 1);
            campos[f * 45 + 36 + 8].addPeca1(6, 4);

            /* P: tipo da peca 5 - Cavaleiro */
            campos[f * 45 + 36 + 1].addJogador1(f / 2 + 1);
            campos[f * 45 + 36 + 1].addPeca1(5, 2);
            campos[f * 45 + 36 + 7].addJogador1(f / 2 + 1);
            campos[f * 45 + 36 + 7].addPeca1(5, 2);

            /* P: tipo da peca 4 - Bispo */
            campos[f * 45 + 36 + 2].addJogador1(f / 2 + 1);
            campos[f * 45 + 36 + 2].addPeca1(4, 2);
            campos[f * 45 + 36 + 6].addJogador1(f / 2 + 1);
            campos[f * 45 + 36 + 6].addPeca1(4, 2);

            /* P: tipo da peca 2 - Dama */
            campos[f * 45 + 36 + 3].addJogador1(f / 2 + 1);
            campos[f * 45 + 36 + 3].addPeca1(2, 3);

            /* P: tipo da peca 1 - Rei */
            campos[f * 45 + 36 + 5].addJogador1(f / 2 + 1);
            campos[f * 45 + 36 + 5].addPeca1(1, 1);
        }

        for (int f = 0; f < 2 * iJogadores; f++) {
            for (int l = 1; l < 10; l++) {
                for (int c = 0; c < l; c++) {
                    if (l != 9) {//excluindo ultima linha
                        campos[f * 45 + (l * (l - 1)) / 2 + c].setFaceAbaixoDireita(campos[f * 45 + ((l + 1) * l) / 2 + c + 1]);
                        campos[f * 45 + (l * (l - 1)) / 2 + c].setFaceAbaixoEsquerda(campos[f * 45 + ((l + 1) * l) / 2 + c - 1]);
                    }
                    if (c != l - 1) {//excluindo ultima coluna antes de mudar de face
                        campos[f * 45 + (l * (l - 1)) / 2 + c].setFaceAcimaDireita(campos[f * 45 + ((l - 1) * (l - 2)) / 2 + c + 1]);
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
                        campos[f * 45 + (l * (l - 1)) / 2 + c].setQuinaEsquerda(campos[((f + 2 * iJogadores - 1) % (2 * iJogadores)) * 45 + ((l + 1) * l) / 2 + l - 1]);
                    }
                    if (l == 9) {
                        campos[f * 45 + (l * (l - 1)) / 2 + c].setQuinaAbaixo(null);
                    } else {
                        campos[f * 45 + (l * (l - 1)) / 2 + c].setQuinaAbaixo(campos[f * 45 + ((l + 1) * l) / 2 + c + 1]);
                        campos[f * 45 + ((l + 1) * l) / 2 + c + 1].setQuinaAcima(campos[f * 45 + (l * (l - 1)) / 2 + c]);
                    }
                    if ((l == 1) && (c == 0)) {
                        campos[f * 45 + 0 + 0].setQuinaAcima(null);
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
        
        for (int c = 0; c < 8; c++) {
            tmpcampos[ 28 + c].addJogador1(1);
            /* P: tipo da peca 1 - Rei | 2 - Dama | 3 - Principe | 4 - Bispo | 5 - Cavaleiro | 6 - Testudo | 7 - Soldado*/
            tmpcampos[28 + c].addPeca1(7, 1);
        }
        
        /* P: tipo da peca 6 - Testudo */
        tmpcampos[36 + 0].addJogador1(1);
        tmpcampos[36 + 0].addPeca1(6, 4);
        tmpcampos[36 + 8].addJogador1(1);
        tmpcampos[36 + 8].addPeca1(6, 4);

        /* P: tipo da peca 5 - Cavaleiro */
        tmpcampos[36 + 1].addJogador1(1);
        tmpcampos[36 + 1].addPeca1(5, 2);
        tmpcampos[36 + 7].addJogador1(1);
        tmpcampos[36 + 7].addPeca1(5, 2);

        /* P: tipo da peca 4 - Bispo */
        tmpcampos[36 + 2].addJogador1(1);
        tmpcampos[36 + 2].addPeca1(4, 2);
        tmpcampos[36 + 6].addJogador1(1);
        tmpcampos[36 + 6].addPeca1(4, 2);

        /* P: tipo da peca 2 - Dama */
        tmpcampos[36 + 3].addJogador1(1);
        tmpcampos[36 + 3].addPeca1(2, 3);

        /* P: tipo da peca 1 - Rei */
        tmpcampos[36 + 5].addJogador1(1);
        tmpcampos[36 + 5].addPeca1(1, 1);
        
        return tmpcampos;
    }
}
