/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Visual.Plano.MapaImagens;

/**
 *
 * @author marcius
 */
public class Campo implements Cloneable{
    
    /**
     * face: face do campo
     * linha: linha contando o centro como 0 indo para as pontas 8
     * coluna: contando no sentido anti horario 0 a 8 
     */
    private int face, linha, coluna;
    
    /**
     * id: Conteudo do campo [FJPVJPV]
 F: fundo da tela 0 - preto | 1 - branco | 2 - Amarelo | 3 - Verde | 4 - Azul | 5 - Vermelho
 J: ordem do existeJogador 1 ~ 7
 P: tipo da peca 1 - Rei | 2 - Dama | 3 - Principe | 4 - Bispo | 5 - Cavaleiro | 6 - Testudo | 7 - Soldado | 8 - testudo sozinho
 V: vida 0 ~ 4, dependendo da peça
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
    
    public void setFundoPreto(){
        int corAnterior = this.id/1000000;
        this.id += 1000000*(MapaImagens.PRETO-corAnterior);
    }
    
    public void setFundoBranco(){
        int corAnterior = this.id /1000000;
        this.id += 1000000*(MapaImagens.BRANCO-corAnterior);
    }
    
    public void setFundoAmarelo(){
        int corAnterior = this.id /1000000;
        this.id += 1000000*(MapaImagens.AMARELO-corAnterior);
    }
    
    public void setFundoVerde(){
        int corAnterior = this.id /1000000;
        this.id += 1000000*(MapaImagens.VERDE-corAnterior);
    }
    
    public void setFundoAzul(){
        int corAnterior = this.id /1000000;
        this.id += 1000000*(MapaImagens.AZUL-corAnterior);
    }
    
    public void setFundoVermelho(){
        int corAnterior = this.id /1000000;
        this.id += 1000000*(MapaImagens.VERMELHO-corAnterior);
    }
    
    public void setFundo(){
        this.id += (1000000*((linha + (face%2)) %2 )-this.id )+this.id % 1000000;
    }
    
    public int getCorFundo(){
        return (this.id/1000000);
    }
    
    /**
     * 
     * @param ordem : ordem do existeJogador
     */
    public void addJogador1(int ordem){
        int jogadorAnterior = (this.id % 1000000)/100000;
        this.id += 100000*(ordem-jogadorAnterior);
    }
    
    /**
     * 
     * @param ordem : ordem do existeJogador
     */
    public void addJogador2(int ordem){
        int jogadorAnterior = (this.id % 1000)/100;
        this.id += 100*(ordem-jogadorAnterior);
    }
    
    /**
     * 
     * @param tipo : tipo da peca
     * @param vida : vida da peca
     */
    public void addPeca1(int tipo, int vida){
        int tipoAnterior = (this.id % 100000)/10000;
        this.id += 10000*(tipo-tipoAnterior);
        
        int vidaAnterior = (this.id % 10000)/1000;
        this.id += 1000*(vida-vidaAnterior);
    }
    
    /**
     * 
     * @param tipo : tipo da peca.
     * @param vida : vida da peca.
     */
    public void addPeca2(int tipo, int vida){
        
        int tipoAnterior = (this.id % 100)/10;
        this.id += 10*(tipo-tipoAnterior);
        
        int vidaAnterior = (this.id % 10);
        this.id += (vida-vidaAnterior);
    }
    
    /**
     * 
     * @return retorna qual o numero do existeJogador 1.
     */
    public int jogador1(){
        return ((this.id%1000000)/100000);
    }
    
    /**
     * 
     * @return retorna qual o numero do existeJogador 2.
     */
    public int jogador2(){
        return ((this.id%1000)/100);
    }
    
    /**
     * 
     * 
     * @param jogador jogador que verifica se exite
     * @return retorna qual a posicao do existeJogador.
     */
    public int existeJogador(int jogador){
        if(jogador == jogador1()){
            return 1;
        }
        if(jogador == jogador2()){
            return 2;
        }
        return 0;
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
     * 
     * @return retorna tipo da peca 1.
     */
    public int vidaPeca1(){
        return ((this.id%10000)/1000);
    }
    
    /**
     * 
     * @return retorna tipo da peca 2.
     */
    public int vidaPeca2(){
        return (this.id%10);
    }
    
    /**
     * Decramenta o valor da vida da peca se o parametro for menor que zero,
     * ou incrementa caso ao contrario da peca 1.
     * @param parametro
     * @return 1 se a peca ainda estiver viva
     */
    public int peca1Vida(int parametro){
        if(parametro < 0){
            if((((this.id % 10000) / 1000)+parametro)>0){
                this.id += 1000*parametro; //Retirando vida
                return 0;
            }    
            int peca2 = this.id%1000; //Salvando informacoes da peca 2 [jogador2 peca2 vida2]
            this.id -= (this.id%1000000); //Zerando [jogador1 peca1 vida1]
            this.id += peca2*1000; //Inserindo as informacoes da peca 2 no lugar da peca 1
            return 2;
        }

        this.id += 1000*parametro; //Somando vida
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
            if((this.id % 10 + parametro)>0){
                this.id += parametro; //Retirando vida
                return 0;
            }    
            this.id -= (this.id%1000); //Zerando [jogador2 peca2 vida2]
            return 2;
        }

        this.id += parametro; //somando vida
        return 1;
    }
    
    /**
     * Mata a peca 2
     */
    public void matarPeca2(){
        this.id -= (this.id%1000); //Zerando [jogador2 peca2 vida2]
    }
    
    /**
     * Mata a peca 1
     */
    public void matarPeca1(){
        int peca2 = this.id%1000; //Salvando informacoes da peca 2 [jogador2 peca2 vida2]
        this.id -= (this.id%1000000); //Zerando [jogador1 peca1 vida1]
        this.id += peca2*1000; //Inserindo as informacoes da peca 2 no lugar da peca 1
    }
    
    /**
     * Mover peca 1 para 
     * @param destino
     */
    public void moverPeca1Destino(Campo destino){
        if(destino == null){
            return;
        }
        if(destino.campoVazio()){
            destino.addJogador1(this.jogador1());
            destino.addPeca1(this.peca1(), this.vidaPeca1());
        }else if(!destino.campoCheio()){
            destino.receberPeca(this.jogador1(), this.peca1(), this.vidaPeca1());
        }
        
        int peca2 = this.id%1000; //Salvando informacoes da peca 2 [jogador2 peca2 vida2]
        this.id -= (this.id%1000000); //Zerando [jogador1 peca1 vida1]
        this.id += peca2*1000; //Inserindo as informacoes da peca 2 no lugar da peca 1
    }
    
    /**
     * Mover peca 2 para 
     * @param destino
     */
    public void moverPeca2Destino(Campo destino){
        if(destino == null)
            return;
        if(destino.campoVazio()){
            destino.addJogador1(this.jogador2());
            destino.addPeca1(this.peca2(), this.vidaPeca2());
        }else if(!destino.campoCheio()){
            destino.receberPeca(this.jogador2(), this.peca2(), this.vidaPeca2());
        }
        
        this.id -= (this.id%1000); //Zerando [jogador2 peca2 vida2]
    }
    
    /** Recebe um:
     * @param jogador
     * @param peca
     * @param vida
     */
    public void receberPeca(int jogador, int peca, int vida){
        if(!this.campoCheio()){
            int jogadorPecaVida1 = (this.id%1000000); //salvando [jogador1 peca1 vida1]
            jogadorPecaVida1 /= 1000; //movendo para [jogador2 peca2 vida2]
            this.id -= (this.id%1000000); //zerando [jogador1 peca1 vida1 jogador2 peca2 vida2]
            this.id += (jogador*100000 + peca*10000 + vida*1000 + jogadorPecaVida1);
        }
    }
    
    /**
     * 
     * @param usuario
     */
    public void moverUsuarioPecaFrente(int usuario){
        if(usuario == jogador2()){
            int jogadorPecaVida1 = (this.id%1000000); //salvando [jogador1 peca1 vida1]
            int jogadorPecaVida2 = (this.id%1000); //salvando [jogador2 peca2 vida2]
            
            jogadorPecaVida1 /= 1000; //movendo para [jogador2 peca2 vida2]
            jogadorPecaVida2 *= 1000; //movendo para [jogador1 peca1 vida1]
            
            this.id -= (this.id%1000000); //zerando [jogador1 peca1 vida1 jogador2 peca2 vida2]
            
            this.id += jogadorPecaVida2 + jogadorPecaVida1;
        }
    }
    
    /**
     * Troca as pecas de lugar no campo
     */
    public void moverPecaFrente(){
        int jogadorPecaVida1 = (this.id%1000000); //salvando [jogador1 peca1 vida1]
        int jogadorPecaVida2 = (this.id%1000); //salvando [jogador2 peca2 vida2]

        jogadorPecaVida1 /= 1000; //movendo para [jogador2 peca2 vida2]
        jogadorPecaVida2 *= 1000; //movendo para [jogador1 peca1 vida1]

        this.id -= (this.id%1000000); //zerando [jogador1 peca1 vida1 jogador2 peca2 vida2]

        this.id += jogadorPecaVida2 + jogadorPecaVida1;
    }    
    
    /*FIM----------------IMPLEMENTACAO METODOS CONTEUDO DO CAMPO--------------*/
    
    /*INICIO-------------IMPLEMENTACAO METODOS VALIDACAO DE CAMPO-------------*/
    public boolean campoVazio(){  
        return (peca1() == 0 && peca2() == 0);
    }
    
    public boolean campoCheio(){  
        if(peca1() == 0)
            return false;
        
        if(peca2() == 0)
            return false;
        
        return true;
    }
    
    public boolean campoAmigo(int ordemPeca, Campo destino){
        if(destino == null){
            return false;
        }
        if(destino.jogador1() == 0){
            return false;
        }
        
        if(ordemPeca != Movimento.PEGARP1){
            return (this.jogador2() == destino.jogador1());
        }
        return (this.jogador1() == destino.jogador1());
    }
    
    public boolean pecasAmigas(){
        if(this.jogador1() == 0)
            return false;
        if(this.jogador2() == 0)
            return false;
        return (this.jogador1() == this.jogador2());
    }
    
    public boolean campoInimigo(int ordemPeca, Campo destino){
        if(destino == null){
            return false;
        }
        if(destino.jogador1() == 0){
            return false;
        }
        return (!campoAmigo(ordemPeca, destino));
    }
    
    public boolean pecasInimigas(){
        if(this.jogador1() == 0)
            return false;
        if(this.jogador2() == 0)
            return false;
        return (this.jogador1() != this.jogador2());
    }
    /*FIM----------------IMPLEMENTACAO METODOS VALIDACAO DE CAMPO-------------*/
    
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

    @Override
    public Object clone(){
        try {
            return super.clone();
        }catch (CloneNotSupportedException e) {
            throw new InternalError(e.toString());
        }
    }
    
}
