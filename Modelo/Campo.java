/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author marcius
 */
public class Campo {
    
    /**
     * face: face do campo
     * linha: linha contando o centro como 0 indo para as pontas 8
     * coluna: contando no sentido anti horario 0 a 8 
     */
    private int face, linha, coluna;
    
    /**
     * id: Conteudo do campo [IFJPVJPV]
     * I: significa que a iteracao ja ocorreu
     * F: fundo da tela 0 - preto | 1 - branco | 2 - Amarelo | 3 - Verde | 4 - Azul | 5 - Vermelho
     * J: ordem do jogador 1 ~ 7
     * P: tipo da peca 1 - Rei | 2 - Dama | 3 - Principe | 4 - Bispo | 5 - Cavaleiro | 6 - Testudo | 7 - Soldado | 8 - testudo sozinho
     * V: vida 0 ~ 4, dependendo da peça
     */
    private int id;
    
    /**
     * Campos adijacentes pela face
     */
    private Campo faceAcimaEsquerda, faceAcimaDireita, faceAbaixoEsquerda, faceAbaixoDireita;
    
    /**
     * Campos adijacentes pela quina
     */
    private Campo quinaAcima, quinaDireita, quinaAbaixo, quinaEsquerda;
    
    /**
     * 
     * @param iFace: face do campo
     * @param iLinha: linha contando o centro como 0 indo para as pontas 8
     * @param iColuna: contando no sentido anti horario 0 a 8 
     */
    public Campo(int iFace, int iLinha, int iColuna){
        this.face = iFace;
        this.linha = iLinha;
        this.coluna = iColuna;
        this.iniciaID();
    }
    
    private void iniciaID(){
        this.id = ((linha + (face%2)) %2 )*1000000;
    }
    
    /*INICIO--------------IMPLEMENTACAO METODOS CONTEUDO DO CAMPO-------------*/
    
    /**
     * 
     * @param ordem : ordem do jogador
     */
    public void addJogador1(int ordem){
        this.id += 100000*ordem;
    }
    
    /**
     * 
     * @param ordem : ordem do jogador
     */
    public void addJogador2(int ordem){
        this.id += 100*ordem;
    }
    
    /**
     * 
     * @param tipo : tipo da peca
     * @param vida : vida da peca
     */
    public void addPeca1(int tipo, int vida){
        this.id += 10000*tipo;
        this.id += 1000*vida;
    }
    
    /**
     * 
     * @param tipo : tipo da peca.
     * @param vida : vida da peca.
     */
    public void addPeca2(int tipo, int vida){
        this.id += 10*tipo;
        this.id += vida;
    }
    
    /**
     * 
     * @return retorna qual o numero do jogador 1.
     */
    public int jogador1(){
        return ((this.id%1000000)/100000);
    }
    
    /**
     * 
     * @return retorna qual o numero do jogador 2.
     */
    public int jogador2(){
        return ((this.id%1000)/100);
    }
    
    /**
     * 
     * @return retorna tipo da peca 1.
     */
    public int peca1(){
        return ((this.id%100000)/10000);
    }
    
    /**
     * 
     * @return retorna tipo da peca 2.
     */
    public int peca2(){
        return ((this.id%100)/10);
    }
    
    /**
     * Decramenta o valor da vida da peca se o parametro for menor que zero,
     * ou incrementa caso ao contrario da peca 1.
     * @param parametro
     * @return 1 se a peca ainda estiver viva
     */
    public int peca1Vida(int parametro){
        if(parametro < 0){
            if((this.id % 10000) / 1000>1){
                this.id -= 1000; //Retirando vida
                return 1;
            }    
            int peca2 = this.id%1000; //Salvando informacoes da peca 2 [jogador2 peca2 vida2]
            this.id -= (this.id%1000000); //Zerando [jogador1 peca1 vida1]
            this.id += peca2; //Inserindo as informacoes da peca 2
            return -1;
        }

        this.id += 1000; //Somando vida
        return 1;
    }
    
    /**
     * Decramenta o valor da vida da peca se o parametro for menor que zero,
     * ou incrementa caso ao contrario da peca 2.
     * @param parametro
     * @return 1 se a peca ainda estiver viva
     */
    public int peca2Vida(int parametro){
        if(parametro < 0){
            if(this.id % 10>1){
                this.id -= 1; //Retirando vida
                return 1;
            }    
            this.id -= (this.id%1000); //Zerando [jogador2 peca2 vida2]
            return -1;
        }

        this.id += 1; //somando vida
        return 1;
    }
    
    /**
     * Marca que a iteracao entre as duas pecas do campo ja ocorreu.
     */
    public void iteragirPecas(){
        if(!iteracao()){
            this.id += 10000000;
        }
    }
    
    /**
     * Desmarca que a iteracao entre as duas pecas.
     */
    public void desmarcaIteracao(){
        if(iteracao()){
            this.id -= 10000000;
        }
    }
    
    /**
     * Responde a iteracao entre duas pecas do campo ja ocorreu.
     */
    public boolean iteracao(){
        return (this.id / 10000000 == 1);
    }
    
    //TODO retirar peca do campo ainda nao foi tratado
    /*FIM----------------IMPLEMENTACAO METODOS CONTEUDO DO CAMPO--------------*/
    
    /*INICIO--------------IMPLEMENTACAO METODOS CAMPOS ADJACENTE--------------*/
    
    public Campo getFaceAcimaEsquerda() {
        return faceAcimaEsquerda;
    }

    public void setFaceAcimaEsquerda(Campo faceAcimaEsquerda) {
        this.faceAcimaEsquerda = faceAcimaEsquerda;
    }

    public Campo getFaceAcimaDireita() {
        return faceAcimaDireita;
    }

    public void setFaceAcimaDireita(Campo faceAcimaDireita) {
        this.faceAcimaDireita = faceAcimaDireita;
    }

    public Campo getFaceAbaixoEsquerda() {
        return faceAbaixoEsquerda;
    }

    public void setFaceAbaixoEsquerda(Campo faceAbaixoEsquerda) {
        this.faceAbaixoEsquerda = faceAbaixoEsquerda;
    }

    public Campo getFaceAbaixoDireita() {
        return faceAbaixoDireita;
    }

    public void setFaceAbaixoDireita(Campo faceAbaixoDireita) {
        this.faceAbaixoDireita = faceAbaixoDireita;
    }

    public Campo getQuinaAcima() {
        return quinaAcima;
    }

    public void setQuinaAcima(Campo quinaAcima) {
        this.quinaAcima = quinaAcima;
    }

    public Campo getQuinaDireita() {
        return quinaDireita;
    }

    public void setQuinaDireita(Campo quinaDireita) {
        this.quinaDireita = quinaDireita;
    }

    public Campo getQuinaAbaixo() {
        return quinaAbaixo;
    }

    public void setQuinaAbaixo(Campo quinaAbaixo) {
        this.quinaAbaixo = quinaAbaixo;
    }

    public Campo getQuinaEsquerda() {
        return quinaEsquerda;
    }

    public void setQuinaEsquerda(Campo quinaEsquerda) {
        this.quinaEsquerda = quinaEsquerda;
    }
    
    public int getId() {
        return id;
    }

    /*FIM----------------IMPLEMENTACAO METODOS CAMPOS ADJACENTE---------------*/
    public int getFace() {
        return face;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    
    
}
