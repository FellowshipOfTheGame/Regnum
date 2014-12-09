package Controle;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.*;

/**
 *
 * @author andre
 */
public class FileChooser {

    private static final JFileChooser fc = new JFileChooser();;
    private static boolean directoryOnly = false;
    private static File defaultLocation = null;

    private FileChooser() {
    }

    public static void addExtension(String ext, String descricao) {
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(new FileNameExtensionFilter(descricao, ext));
    }

    public static void addExtension(FileFilter f) {
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(f);
    }

    public static void directoryOnly() {
        directoryOnly = true;
    }

    public static File getDefaultLocation() {
        return defaultLocation;
    }

    public static void setDefaultLocation(File location) {
        defaultLocation = location;
    }

    public static File getSaveDialog() {
        if (defaultLocation != null) {
            fc.setCurrentDirectory(defaultLocation);
        }
        fc.setFileSelectionMode(directoryOnly ? JFileChooser.DIRECTORIES_ONLY : JFileChooser.FILES_ONLY);
        File fp;
        int returnVal;
        do {
            returnVal = fc.showSaveDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                fp = fc.getSelectedFile();
                if (!fc.accept(fp)) {
                    //Coloca extensao padrao
                    fp = new File(fp.getAbsolutePath() + ((FileNameExtensionFilter) fc.getFileFilter()).getExtensions()[0]);
                }
            } else {
                break;
            }

            if (fp.exists()) {
                returnVal = JOptionPane.showConfirmDialog(null, "O arquivo já existe, deseja sobreescrever?");
                if (returnVal == JOptionPane.YES_OPTION) {
                    return fp;
                } else if (returnVal != JOptionPane.NO_OPTION) {
                    return null;
                }
            }
            defaultLocation = fp;
            return fp;
        } while (true);
        return null;
    }

    public static File getOpenDialog() {
        if (defaultLocation != null) {
            fc.setCurrentDirectory(defaultLocation);
        }
        fc.setFileSelectionMode(directoryOnly ? JFileChooser.DIRECTORIES_ONLY : JFileChooser.FILES_ONLY);
        File fp;
        int returnVal;
        do {
            returnVal = fc.showOpenDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                fp = fc.getSelectedFile();
            } else {
                break;
            }
            defaultLocation = fp;
            return fp;
        } while (true);
        return null;
    }
}
