package com.abhishek.application.androtorch;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class ColorActivity extends Activity implements OnTouchListener {
    
    private float h, v;
    private int c1, c2;
    private View view;
    private int m_interval1 = 1000;
    private int m_interval2 = 1000;
    private int t1 = 1000;
    private int t2 = 1000, police;
    private Handler m_handler1, tornadoHandler;
    private Handler m_handler2;
    private SeekBar seekBar1, seekBar2;
    private ColorCanvas colorCanvas;
    private boolean bulb;
    private Button toggleButton;
    private Button bulbButton;
    private boolean flashOn = false;
    Runnable m_statusChecker1, m_statusChecker2;
    static Camera camera;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        colorCanvas = new ColorCanvas(this);
        if(Settings.id == Settings.COLOR) {
//            colorCanvas = (ColorCanvas) findViewById(R.id.view1);
            setContentView(R.layout.color_activity);
            colorCanvas = (ColorCanvas) findViewById(R.id.view1);
            colorCanvas.setColor(Color.HSVToColor(new float[] {(float)Math.random()*360, .8f, (float)Math.random()}));
            colorCanvas.invalidate();
            colorCanvas.setOnTouchListener(this);
        }
        else if(Settings.id == Settings.POLICE)
        {
            setContentView(R.layout.color_activity);
            view = (View) findViewById(R.id.view1);
            m_statusChecker1 = m_statusChecker3;
            m_statusChecker2 = m_statusChecker4;
            m_handler1 = new Handler();
            m_handler2 = new Handler();
            m_interval1 = 200;
            m_interval2 = 200;
            startRepeatingTask1(Color.RED);
            c2 = Color.BLACK;
            m_handler2.postDelayed(m_statusChecker2, (long)m_interval1 / 2);
        }
        else if(Settings.id == Settings.SCREEN_FLASH)
        {
            setContentView(R.layout.color_activity);
            view = (View) findViewById(R.id.view1);
            m_statusChecker1 = m_statusChecker3;
            m_statusChecker2 = m_statusChecker4;
            addSeekBars();
            m_handler1 = new Handler();
            m_handler2 = new Handler();
            startRepeatingTask1(Color.WHITE);
            c2 = Color.BLACK;
            m_handler2.postDelayed(m_statusChecker2, (long)m_interval1 / 2);
            
        }
        else if(Settings.id == Settings.BULB) {
          setContentView(R.layout.color_activity);
//          bulbButton = (Button)findViewById(R.id.bulbOn);
//          bulbButton.setVisibility(Button.VISIBLE);
//          bulbButton.setOnClickListener(new OnClickListener() {
//              @Override
//              public void onClick(View v) {
//                  
//              }
//          });
          colorCanvas = (ColorCanvas) findViewById(R.id.view1);
          colorCanvas.setOnTouchListener(this);
          colorCanvas.setBackgroundResource(R.drawable.bulb_off);
          Display display = getWindowManager().getDefaultDisplay(); 
          int width = getWindowManager().getDefaultDisplay().getWidth();
          int height = getWindowManager().getDefaultDisplay().getHeight();
          colorCanvas.setScreenHeight(height);
          colorCanvas.setScreenWidth(width);
      }
        else if(Settings.id == Settings.CAMERA_LIGHT) {
            setContentView(R.layout.color_activity);
            toggleButton = (Button) findViewById(R.id.FlashOn);
            toggleButton.setVisibility(Button.VISIBLE);
            toggleButton.setOnClickListener(new OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    updateStatus3();
                }
            });
            
        }
        else if(Settings.id == Settings.SCREEN_LIGHT) {
            try {
                Class<?> c = Class.forName("com.abhishek.application.androtorch.MainActivity");
                Intent i = new Intent(ColorActivity.this, c);
                startActivity(i);
            } catch (ClassNotFoundException e) {}
            
        }
        else if(Settings.id == Settings.CAMERA_FLASH) {
            setContentView(R.layout.color_activity);
            view = (View) findViewById(R.id.view1);
            m_statusChecker1 = m_statusChecker4;
            m_statusChecker3 = m_statusChecker6;
            addSeekBars();
            m_handler1 = new Handler();
            startFlashlight();
            m_handler2 = new Handler();
            m_handler2.postDelayed(m_statusChecker2, (long)m_interval1 / 2);
        }
        else if(Settings.id == Settings.TORNADO) {
            setContentView(R.layout.color_activity);
            colorCanvas = (ColorCanvas) findViewById(R.id.view1);
//            colorCanvas.invalidate();
            colorCanvas.setId(Settings.TORNADO);
            tornadoHandler = new Handler();
            tornadoHandler.postDelayed(mTornado, 100);
        }
        else if(Settings.id == Settings.DISCO) {
            GLSurfaceView view = new GLSurfaceView(this);
            view.setRenderer(new com.abhishek.application.androtorch.disco.DiscoBallRenderer());
            setContentView(view);
        }
        else if(Settings.id == Settings.TREE) {
            setContentView(R.layout.color_activity);
            colorCanvas = (ColorCanvas) findViewById(R.id.view1);
//            colorCanvas.invalidate();
            colorCanvas.setId(Settings.TREE);
            colorCanvas.setOnTouchListener(this);
        }
    }
    
    void startFlashlight() {
        m_statusChecker1.run();
    }
    
    void setFlashOn() {
        
        camera = Camera.open();
        if(camera == null) {
            Toast.makeText(this, "Camera not Supported", Toast.LENGTH_SHORT).show();
        } else {
                Parameters param = camera.getParameters();
                param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                try {
                        camera.setParameters(param);
                        camera.startPreview();
                        flashOn = true;
                } catch (Exception e) {
                    Toast.makeText(this, "Camera not Supported", Toast.LENGTH_SHORT).show();
                }
                }
    }
    
    void setFlashOff() {
        
        if (camera != null) {
            Parameters param = camera.getParameters();
            param.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            try {
                camera.setParameters(param);
                camera.startPreview();
        } catch (Exception e) {
        }
            camera.stopPreview();
            camera.release();
            camera = null;
            flashOn = false;
        }
    }
    
    void setFlash() {
        if(isFlashAvailable()) {
        if(!flashOn)
        {
            setFlashOn();
        }
        else {
            setFlashOff();
        }
        }
    }
    
    boolean isFlashAvailable() {
        return this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }
    
    private double dist(float x1, float y1, float x2, float y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
    
//    @Override
//    public void onBackPressed() {
//        
//        Intent i = new Intent("com.abhishek.application.androtorch.Settings");
//        startActivity(i);
////        super.onBackPressed();
//    }
    
    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        
    }

    void addSeekBars() {
        seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        seekBar1.setMax(1800);
        seekBar1.setProgress(700);
        seekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser) {
                stopRepeatingTask1();
                stopRepeatingTask2();
                
                t1 = 200 + progress;
                m_interval1 = t1 + t2;
                startRepeatingTask1(c1);
                m_interval2 = t1 + t2;
                m_handler2.postDelayed(m_statusChecker2, t1);
                }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        seekBar1.setVisibility(0);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekBar2.setMax(1800);
        seekBar2.setProgress(700);
        seekBar2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser) {
                stopRepeatingTask1();
                stopRepeatingTask2();
                t2 = 200 + progress;
                m_interval1 = t1 + t2;
                
                startRepeatingTask1(c1);
                m_interval2 = t1 + t2;
                
                m_handler2.postDelayed(m_statusChecker2, t1);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        seekBar2.setVisibility(0);
    }
    
    Runnable mTornado = new Runnable()
    {
         @Override 
         public void run() {
              updateStatusT();
//              m_handler1.postDelayed(mTornado, 100);
         }
    };
    
    Runnable m_statusChecker3 = new Runnable()
    {
         @Override 
         public void run() {
              updateStatus1(c1);
              m_handler1.postDelayed(m_statusChecker3, m_interval1);
         }
    };
    
    Runnable m_statusChecker4 = new Runnable()
    {
         @Override 
         public void run() {
              updateStatus2(c2);
              m_handler2.postDelayed(m_statusChecker4, m_interval2);
         }
    };
    Runnable m_statusChecker5 = new Runnable()
    {
         @Override 
         public void run() {
              updateStatus3();
              m_handler1.postDelayed(m_statusChecker5, m_interval1);
         }
    };
    
    Runnable m_statusChecker6 = new Runnable()
    {
         @Override 
         public void run() {
              updateStatus3();
              m_handler2.postDelayed(m_statusChecker6, m_interval2);
         }
    };
    
    void updateStatusT() {
        colorCanvas.invalidate();
    }
    
    void updateStatus3() {
        setFlash();
        if(flashOn)
            toggleButton.setBackgroundResource(R.drawable.power);
        else
            toggleButton.setBackgroundResource(R.drawable.power_off);
    }
    
    private void getColor() {
        police();
        if(police % 6 < 3) {
            c1 = Color.RED;
        }
        else {
            c1 = Color.BLUE;
        }
    }
    
    private void police() {
        if(police == 5) 
            police = 0;
        else
            police++;
    }
     
    void updateStatus2(int c2) {
        view.setBackgroundColor(c2);
    }
    
    void updateStatus1(int color) {
        if(color == Color.RED || color == Color.BLUE) {
            getColor();
        }
        view.setBackgroundColor(c1);
    }

    void startRepeatingTask1(int c1)
    {
        this.c1 = c1;
        m_statusChecker1.run();
    }
    
    void startRepeatingTask2(int c2)
    {
        this.c2 = c2;
        m_statusChecker2.run();
    }

    void stopRepeatingTask2()
    {
        m_handler2.removeCallbacks(m_statusChecker2);
    }
    
    void stopRepeatingTask1()
    {
        m_handler1.removeCallbacks(m_statusChecker1);
    }
    
    
    protected void onPause() {
        super.onPause();
        WindowManager.LayoutParams settings = getWindow().getAttributes();
        settings.screenBrightness = -1f;
        getWindow().setAttributes(settings);
        if (camera != null) {
            Parameters param = camera.getParameters();
            param.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            try {
                camera.setParameters(param);
                camera.startPreview();
            } catch (Exception e) {
            }
            camera.stopPreview();
            camera.release();
            camera = null;
            flashOn = false;
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        WindowManager.LayoutParams settings = getWindow().getAttributes();
        settings.screenBrightness = 1f;
        getWindow().setAttributes(settings);
        
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if(Settings.id == 1) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_MOVE :
            float h =  (float)(dist(0, event.getY(), event.getX(), event.getY()) / 780 * 360);
            float b = (float)(dist(event.getX(), view.getHeight(), event.getX(), event.getY()) / 420);
            if(h > 360) h = 360;
            if(b > 1) b = 1;
            if(b < 0.1) b = 0.1f;
            view.setId(1);
//            view.setBackgroundColor(Color.HSVToColor(new float[] {h, .8f, b}));
            colorCanvas.setColor(Color.HSVToColor(new float[] {h, .8f, b}));
            colorCanvas.invalidate();
            break;
            default:
                break;
            }
        }
        else if(Settings.id == 4) {
            if(h > 360) h = 360;
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_MOVE :
                    h =  (float)(dist(0, event.getY()
                            , event.getX(), event.getY()) / 780 * 360);
                    v = (float)(dist(event.getX(), view.getHeight(), event.getX(), event.getY()) / 420);
                    
                    if(v > 1) v = 1;
                    if(v < 0.1) v = 0.1f;
                    colorCanvas.setColor(Color.HSVToColor(new float[] {h, .8f, v}));
                    break;
                case MotionEvent.ACTION_UP :
                    bulb = !bulb;
                    if(bulb) {
                        v += 0.5f;
                        if(v > 1f) v = 1f; 
                        colorCanvas.setColor(Color.HSVToColor(new float[] {h, .8f, v}));
                    }
                    else {
                        v -= 0.5f;
                        if(v < 0f) v = 0.1f; 
                        colorCanvas.setColor(Color.HSVToColor(new float[] {h, .8f, v}));
                    }
                    colorCanvas.drawImage(bulb);
                    break;
                default:
                    break;
            }
            
            colorCanvas.invalidate();
        }
        else if(Settings.id == 10) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_MOVE :
            float h =  (float)(dist(0, event.getY(), event.getX(), event.getY()) / 780 * 360);
            float b = (float)(dist(event.getX(), view.getHeight(), event.getX(), event.getY()) / 420);
            if(h > 360) h = 360;
            if(b > 1) b = 1;
            if(b < 0.1) b = 0.1f;
//            view.setBackgroundColor(Color.HSVToColor(new float[] {h, .8f, b}));
            colorCanvas.setColor(Color.HSVToColor(new float[] {h, .8f, b}));
            break;
            default:
                break;
            }
            colorCanvas.setMouseX(event.getX());
            colorCanvas.setMouseY(event.getY());
            colorCanvas.invalidate();
        }
        return true;
    }
    
}
