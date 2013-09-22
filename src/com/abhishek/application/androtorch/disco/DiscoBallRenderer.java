package com.abhishek.application.androtorch.disco;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;
import android.opengl.GLSurfaceView; //2
import java.lang.Math;
import java.nio.*;

public class DiscoBallRenderer implements GLSurfaceView.Renderer{
    
    float angle = 0.01f;
    float scale = 1;
    
    public DiscoBallRenderer()
    {
        mPlanet=new DiscoBall(30,50,1.0f, 0.7f);
    }
    
    public void onDrawFrame(GL10 gl)
    {
        
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glClearColor(0.0f,0.0f,0.0f,1.0f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        
        gl.glTranslatef(0.0f, 0.0f, -4.0f);
//      gl.glRotatef(mAngle, 1, 0, 0);
        if(scale < 0.6) { scale = 0.6f; 
        angle = -angle;
        }
        if(scale > 1.5) { scale = 1.5f; 
        angle = -angle;
        }
        gl.glScalef(scale, scale, scale);
        gl.glRotatef(mAngle, 0, 1, 0);
        mPlanet.draw(gl);
        mTransY+=.075f;
        mAngle+=1.9;
        scale += angle;
    }
    
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        gl.glViewport(0, 0, width, height);
        float aspectRatio;
        float zNear = 0.1f;
        float zFar = 1000;
        float fieldOfView = 30.0f/57.3f; //1
        float size;
        gl.glEnable(GL10.GL_NORMALIZE);
        aspectRatio=(float)width/(float)height; //2
        gl.glMatrixMode(GL10.GL_PROJECTION); //3
        size = zNear * (float)(Math.tan((double)(fieldOfView/2.0f))); //4
        gl.glFrustumf(-size, size, -(size) /(aspectRatio) , //5
        (size) /(aspectRatio) , zNear, zFar);
        gl.glMatrixMode(GL10.GL_MODELVIEW); //6
    }
    
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) //15
    {
        gl.glDisable(GL10.GL_DITHER); //16
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, //17
        GL10.GL_FASTEST);
        
        gl.glEnable(GL10.GL_CULL_FACE); //19
        gl.glCullFace(GL10.GL_BACK);
        gl.glShadeModel(GL10.GL_FLAT); //20
        gl.glEnable(GL10.GL_DEPTH_TEST); //21
        initGeometry(gl);
        initLighting(gl);
    }
    
    private void initLighting(GL10 gl)
    {
    float[] posMain={5.0f ,4.0f,6.0f,1.0f};
    float[] posFill1={-15.0f,15.0f,0.0f,1.0f};
    float[] posFill2={-10.0f,-4.0f,1.0f,1.0f};
    float[] white={1.0f, 1.0f,1.0f,1.0f};
    float[] lightGrey={.98f, .98f, .98f,1.0f};
    float[] darkGrey={.5f, .5f, .5f,1.0f};
    float[] red={1.0f,0.0f,0.0f,1.0f};
    float[] dimred={.5f, 0.0f,0.0f,1.0f};
    float[] green={0.0f, 1.0f,0.0f,0.0f};
    float[] dimgreen={0.0f,.5f,0.0f,0.0f};
    float[] blue ={0.0f,0.0f,1.0f,1.0f};
    float[] dimblue={0.0f,0.0f,.2f,1.0f};
    float[] cyan ={0.0f,1.0f,1.0f,1.0f};
    float[] yellow={.8f , .8f, .0f,1.0f};
    float[] magenta={1.0f,0.0f,1.0f,1.0f};
    float[] dimmagenta={ .75f,0.0f,.25f,1.0f};
    float[] dimcyan={0.0f,.5f,.5f,1.0f};
    float[] golden= {.98f, 0.96f, 0.35f, 1.0f};
    float[] myColor = {0.98f, .98f, .23f, 1.0f};
    float[] lightGreen= {.97f, .84f, .686f, 1.0f};
    //Lights go here.
    gl.glLightfv(SS_SUNLIGHT, GL10.GL_POSITION, makeFloatBuffer(posMain));
    gl.glLightfv(SS_SUNLIGHT, GL10.GL_DIFFUSE, makeFloatBuffer(red));
    gl.glLightfv(SS_SUNLIGHT, GL10.GL_SPECULAR, makeFloatBuffer(blue));
    gl.glLightfv(SS_FILLLIGHT1, GL10.GL_POSITION, makeFloatBuffer(posFill1));
    gl.glLightfv(SS_FILLLIGHT1, GL10.GL_DIFFUSE, makeFloatBuffer(green));
    gl.glLightfv(SS_FILLLIGHT1, GL10.GL_SPECULAR, makeFloatBuffer(red));
    gl.glLightfv(SS_FILLLIGHT2, GL10.GL_POSITION, makeFloatBuffer(posFill2));
    gl.glLightfv(SS_FILLLIGHT2, GL10.GL_SPECULAR, makeFloatBuffer(blue));
    gl.glLightfv(SS_FILLLIGHT2, GL10.GL_DIFFUSE, makeFloatBuffer((blue)));
    gl.glLightf(SS_SUNLIGHT, GL10.GL_QUADRATIC_ATTENUATION, .005f);
    //Materials go here.
    gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, makeFloatBuffer(white));
    gl.glMaterialfv(GL10 .GL_FRONT_AND_BACK, GL10.GL_SPECULAR,
    makeFloatBuffer(white));
    gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 25);
//  gl.glShadeModel(GL10 .GL_EMISSION);
    gl.glShadeModel(GL10.GL_AMBIENT);
    gl.glLightModelf(GL10.GL_LIGHT_MODEL_TWO_SIDE, 1.0f);
    gl.glEnable( GL10.GL_LIGHTING);
    gl.glEnable( SS_SUNLIGHT);
    gl.glEnable( SS_FILLLIGHT1);
    gl.glEnable( SS_FILLLIGHT2);
    gl.glLoadIdentity();
    }
    
    protected static FloatBuffer makeFloatBuffer(float[] arr)
    {
        ByteBuffer bb = ByteBuffer.allocateDirect(arr.length*4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(arr);
        fb.position(0);
        return fb;
    }
    
    void initGeometry(GL10 gl)
    {
        mPlanet=new DiscoBall(30, 50, 1f, 1.0f);
    }
    
private boolean mTranslucentBackground;
private DiscoBall mPlanet;
private float mTransY;
private float mAngle;
public final static int SS_SUNLIGHT = GL10.GL_LIGHT0;
public final static int SS_FILLLIGHT1 = GL10.GL_LIGHT1;
public final static int SS_FILLLIGHT2 = GL10.GL_LIGHT2;
}
