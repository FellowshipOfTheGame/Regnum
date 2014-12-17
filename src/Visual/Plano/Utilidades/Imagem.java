/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual.Plano.Utilidades;

import Controle.Rede.Servidor;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.MediaTracker;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcius
 */
public class Imagem {

    private int w;
    private int h;
    private BufferedImage buffered;
    private Image imgOriginal;
    private int corOriginalRGB;
    private boolean original;
    private int corNovaRGB;
    private int corTransparenciaRGB;
    private boolean transparencia;
    private Component compJanela;

    public Imagem() {
        this.original = false;
        this.transparencia = false;
        this.buffered = null;
    }

    /**
     * @param cor1 cor original da imagem a ser subistituida
     * @param cor2 nova cor na imagem
     * @param cor3 cor a ser posta tranparente
     * @param img imagem a ser usada como base
     * @param comp Component ambiente que a imagem esta sendo usada
     * @return retorna uma imagem com a cor nova
     */
    public Image imagemConverteCor(Color cor1, Color cor2, Color cor3, Image imgOriginal, Component comp) {
        carregaImagem(imgOriginal, comp);
        carregaCorOriginal(cor1);
        carregaCorNova(cor2);
        carregaCorTransparencia(cor3);

        criaBuffered(comp);

        //Pegando matriz de pixel e trocando a cor
        int[] imageBytes = buffered.getRGB(0, 0, w, h, null, 0, w);

        //System.out.println("imagem de cor tamanho: " + imageBytes.length + " h*w: " + h * w);

        subtituir(imageBytes);

        buffered.setRGB(0, 0, w, h, imageBytes, 0, w);

        return buffered.getScaledInstance(w, h, Image.SCALE_DEFAULT);
    }

    /**
     * @param cor1 cor original da imagem a ser subistituida
     * @param cor2 nova cor na imagem
     * @param cor3 cor a ser posta tranparente
     * @return retorna uma imagem com a cor nova
     */
    public Image imagemConverteCor(Color cor1, Color cor2, Color cor3) {
        if (buffered == null) {
            return null;
        }

        carregaCorOriginal(cor1);
        carregaCorNova(cor2);
        carregaCorTransparencia(cor3);

        criaBuffered(this.compJanela);

        //Pegando matriz de pixel e trocando a cor
        int[] imageBytes = buffered.getRGB(0, 0, w, h, null, 0, w);

        subtituir(imageBytes);

        buffered.setRGB(0, 0, w, h, imageBytes, 0, w);

        return buffered.getScaledInstance(w, h, Image.SCALE_DEFAULT);
    }

    /**
     * @param cor1 cor original da imagem a ser subistituida
     * @param cor2 nova cor na imagem
     * @return retorna uma imagem com a cor nova
     */
    public Image imagemConverteCor(Color cor1, Color cor2) {
        if (buffered == null || !transparencia) {
            return null;
        }

        carregaCorOriginal(cor1);
        carregaCorNova(cor2);

        criaBuffered(this.compJanela);

        //Pegando matriz de pixel e trocando a cor
        int[] imageBytes = buffered.getRGB(0, 0, w, h, null, 0, w);

        subtituir(imageBytes);

        buffered.setRGB(0, 0, w, h, imageBytes, 0, w);

        return buffered.getScaledInstance(w, h, Image.SCALE_DEFAULT);
    }

    /**
     * @param cor2 nova cor na imagem, apartir da cor original
     * @return retorna uma imagem com a cor nova
     */
    public Image imagemConverteCor(Color cor2) {
        if (buffered == null || !transparencia || !original) {
            return null;
        }

        carregaCorNova(cor2);

        criaBuffered(this.compJanela);

        //Pegando matriz de pixel e trocando a cor
        int[] imageBytes = buffered.getRGB(0, 0, w, h, null, 0, w);

        subtituir(imageBytes);

        buffered.setRGB(0, 0, w, h, imageBytes, 0, w);

        return buffered.getScaledInstance(w, h, Image.SCALE_DEFAULT);
    }

    private void subtituir(int[] imageBytes) {
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                try {
                    if (imageBytes[i * w + j] == corOriginalRGB) {
                        imageBytes[i * w + j] = corNovaRGB;
                    } else if (imageBytes[i * w + j] == corTransparenciaRGB) {
                        imageBytes[i * w + j] = 0;
                    }
                } catch (Exception ex) {
                    System.out.println("imagem de cor tamanho: " + imageBytes.length + " i*h + j: " + (i * h + j)+" h: "+h+" w: "+w);
                    //System.out.println("imagem de cor tamanho: " + imageBytes.length + " i*h + j: " + (i * h + j));
                    //Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
            }
        }
    }

    private void criaBuffered(Component comp) {
        //Fazendo casth de Image para BufferImage
        buffered = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = buffered.createGraphics();
        bGr.drawImage(imgOriginal, 0, 0, comp);
        bGr.dispose();
    }

    public void carregaImagem(Image img, Component comp) {
        this.imgOriginal = img;
        this.compJanela = comp;

        MediaTracker m = new MediaTracker(comp);
        m.addImage(imgOriginal, 1);
        try {
            m.waitForAll();
        } catch (InterruptedException e) {
        } // Esperando imagem carregar
        if (img == null) {
            System.exit(0);
        }

        w = imgOriginal.getWidth(comp);
        h = imgOriginal.getHeight(comp);
        w = (w == -1) ? 128 : w;
        h = (h == -1) ? 128 : h;

        this.imgOriginal = img;
    }

    public void carregaCorOriginal(Color cor1) {
        this.corOriginalRGB = cor1.getRGB();
        this.original = true;
    }

    public void carregaCorNova(Color cor2) {
        this.corNovaRGB = cor2.getRGB();
    }

    public void carregaCorTransparencia(Color cor3) {
        this.corTransparenciaRGB = cor3.getRGB();
        this.transparencia = true;
    }
    
    public Image somaImagens(Image img1, Image img2, Component comp){
        MediaTracker m = new MediaTracker(comp);
        m.addImage(img1, 1);
        m.addImage(img2, 2);
        try {
            m.waitForAll();
        } catch (InterruptedException e) {
        } // Esperando imagem carregar
        if (img1 == null) {
            System.out.println("null img1 - nao existe imagem do atacante");
            System.exit(0);
        }
        if (img2 == null) {
            System.out.println("null img2 - nao existe imagem do defensor");
            System.exit(0);
        }

        int wImg1 = img1.getWidth(comp);
        int hImg1 = img1.getHeight(comp);
        
        BufferedImage quadro = new BufferedImage(wImg1, hImg1, BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = quadro.createGraphics();
        bGr.drawImage(img1, 0, 0, comp);
        bGr.drawImage(img2, 0, 0, comp);
        bGr.dispose();
        
        return quadro.getScaledInstance(wImg1, hImg1, Image.SCALE_DEFAULT);
    }
}
