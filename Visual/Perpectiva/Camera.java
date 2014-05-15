/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual.Perpectiva;

/**
 *
 * @author marcius
 */
public class Camera {
    private static float[] p0;
    private static float[] pref;
    private static float[] viewup;
    
    private final int x=0;
    private final int y=1;
    private final int z=2;
    
    public Camera(){
        p0 = new float[3];
        pref = new float[3];
        viewup = new float[3];
        
        p0[x]=p0[y]=p0[z]=0;
        pref[x]=0; pref[y]=0; pref[z]=-1;
        viewup[x]=viewup[z]=0; viewup[y]=1;
    }
    
    public static float[] getP0() {
        return p0;
    }

    public static float[] getPref() {
        return pref;
    }

    public static float[] getViewup() {
        return viewup;
    }
}
