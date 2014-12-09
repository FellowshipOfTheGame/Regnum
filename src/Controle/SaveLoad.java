package Controle;

import Modelo.Movimento;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SaveLoad implements Serializable {

    public static Movimento objMensagem;
    private static File path;

    private SaveLoad() {
    }

    public static void save(Movimento o) {
        objMensagem = o;
        SaveLoad.path = FileChooser.getDefaultLocation();
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(SaveLoad.path));
            oos.writeObject(objMensagem.getTipoMovimento());
            oos.writeObject(objMensagem.getCampoSelecionado()[0]);
            oos.writeObject(objMensagem.getCampoSelecionado()[1]);
            oos.writeObject(objMensagem.getCampoSelecionado()[2]);
            if (objMensagem.getCampoDestino() != null) {
                oos.writeObject(2);
                oos.writeObject(objMensagem.getCampoDestino()[0]);
                oos.writeObject(objMensagem.getCampoDestino()[1]);
                oos.writeObject(objMensagem.getCampoDestino()[2]);
            } else {
                oos.writeObject(1);
            }

        } catch (IOException ex) {
        }
    }

    public static Movimento load() {
        SaveLoad.path = FileChooser.getDefaultLocation();
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream(path));
            Integer tipoMovimento = (Integer) ois.readObject();
            int[] c = new int[3];
            c[0] = (Integer) ois.readObject();
            c[1] = (Integer) ois.readObject();
            c[2] = (Integer) ois.readObject();
            int movimento = (Integer) ois.readObject();
            if (movimento == 2) {
                int[] c2 = new int[3];
                c2[0] = (Integer) ois.readObject();
                c2[1] = (Integer) ois.readObject();
                c2[2] = (Integer) ois.readObject();
                objMensagem = new Movimento(Xadrez.getTabuleiro().campoSelecionado(c[0], c[1], c[2]), Xadrez.getTabuleiro().campoSelecionado(c2[0], c2[1], c2[2]), tipoMovimento);
            } else {
                objMensagem = new Movimento(Xadrez.getTabuleiro().campoSelecionado(c[0], c[1], c[2]), tipoMovimento);
            }
            return objMensagem;
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
        }
        return null;

    }

    public static File getPath() {
        return path;
    }

}
