/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual.Perpectiva;

import Visual.Janela;
import com.sun.opengl.util.GLUT;
//import com.sun.opengl.util.GLUT;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author marcius
 */
public class Tela3D extends KeyAdapter implements GLEventListener {
    private final int x=0;
    private final int y=1;
    private final int z=2;
    private Camera camera;
    private Janela myWindows;
    
    public Tela3D(Janela aThis) {
        this.myWindows = aThis;
        camera = new Camera();
    }
    
    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        gl.glEnable(GL.GL_LIGHTING);
        gl.glEnable(GL.GL_LIGHT0);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glShadeModel(GL.GL_SMOOTH);

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
        
        Luz.lighting(drawable);
    }
    
    public void lighting(GLAutoDrawable drawable){
        Luz.lighting(drawable);
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();
        GLUT glut = new GLUT();
        
        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(60, 1, 0.001, 100);

        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();

        glu.gluLookAt(Camera.getP0()[x], Camera.getP0()[y], Camera.getP0()[z], Camera.getPref()[x], Camera.getPref()[y], Camera.getPref()[z], Camera.getViewup()[x], Camera.getViewup()[y], Camera.getViewup()[z]);
               
        gl.glPushMatrix();
            //gl.glMatrixMode(GL.GL_MODELVIEW);
            gl.glColor3f (1.0f, 0.0f, 0.0f);
            //gl.glScalef(0.05f, 0.05f, 0.05f);
            glut.glutSolidCube(0.00199f);
        gl.glPopMatrix();

        // Flush all drawing operations to the graphics card
        //gl.glFlush();
        
        
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!
        
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        //glu.gluOrtho2D(0, 0, h, h);
        glu.gluPerspective(60.0f, h, 0.001, 100.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}
    
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_A){
            Camera.getP0()[x]+=0.5;
            Camera.getPref()[x]+=0.5;
        }
    }
}
