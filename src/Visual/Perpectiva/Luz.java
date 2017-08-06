/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual.Perpectiva;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;

/**
 *
 * @author marcius
 */
public class Luz {
    public static float[] ambient = new float[]{0.3f, 0.3f, 0.3f, 1.0f};
    public static float[] diffuse = new float[]{0.75f, 0.75f, 0.75f, 1.0f};
    public static float[] specular = new float[]{0.9f, 0.9f, 0.9f, 1.0f};
    public static float[] position = new float[]{0, 5, 0, 1.0f};

    public static void setAmbient(float ambient, int parametro) {
        Luz.ambient[parametro] = ambient/100;
    }

    public static void setDiffuse(float diffuse, int parametro) {
        Luz.diffuse[parametro] = diffuse/100;
    }

    public static void setSpecular(float specular, int parametro) {
        Luz.specular[parametro] = specular;
    }

    public static void lighting(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        // Define os parametros da luz de numero 0
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_AMBIENT, ambient, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_SPECULAR, specular, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_POSITION, position, 0);
    }
}
