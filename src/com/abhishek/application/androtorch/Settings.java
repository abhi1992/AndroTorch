package com.abhishek.application.androtorch;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Settings extends Activity {

    static final short COLOR = 1;
    static final short  POLICE = 2;
    static final short SCREEN_FLASH = 3;
    static final short BULB = 4;
    static final short SCREEN_LIGHT= 5;
    static final short  CAMERA_LIGHT = 6;
    static final short CAMERA_FLASH = 7;
    static final short TORNADO = 8;
    static final short DISCO = 9;
    static final short TREE = 10;
    
    public static int id;
    private Button color;
    private Button police;
    private Button screenFlash;
    private Button bulb;
    private Button cameraFlash;
    private Button screenLight;
    private Button cameraLight;
    private Button tornado;
    private Button disco;
    private Button tree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        color = (Button) findViewById(R.id.colorButton);
        color.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                id = 1;
                Intent i = new Intent("com.abhishek.application.androtorch.ColorActivity");
                startActivity(i);
            }
        });
        police = (Button) findViewById(R.id.policeButton);
        police.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                id = 2;
                Intent i = new Intent("com.abhishek.application.androtorch.ColorActivity");
                startActivity(i);
            }
        });
        screenFlash = (Button) findViewById(R.id.Flash);
        screenFlash.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                id = 3;
                Intent i = new Intent("com.abhishek.application.androtorch.ColorActivity");
                startActivity(i);
            }
        });
        bulb = (Button) findViewById(R.id.bulbOn);
        bulb.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                id = 4;
                Intent i = new Intent("com.abhishek.application.androtorch.ColorActivity");
                startActivity(i);
            }
        });
        screenLight = (Button) findViewById(R.id.screenLight);
        screenLight.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                id = 5;
                Intent i = new Intent("com.abhishek.application.androtorch.ColorActivity");
                startActivity(i);
            }
        });
        cameraLight = (Button) findViewById(R.id.cameraLight);
        cameraLight.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if(isFlashAvailable())
                {      id = 6;
                    Intent i = new Intent("com.abhishek.application.androtorch.ColorActivity");
                    startActivity(i);
                }
            }
        });
        cameraFlash = (Button) findViewById(R.id.cameraFlash);
        cameraFlash.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if(isFlashAvailable())
                {      id = 7;
                    Intent i = new Intent("com.abhishek.application.androtorch.ColorActivity");
                    startActivity(i);
                }
            }
        });
        
        tornado = (Button) findViewById(R.id.tornado);
        tornado.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                    id = 8;
                    Intent i = new Intent("com.abhishek.application.androtorch.ColorActivity");
                    startActivity(i);
               
            }
        });
        disco = (Button) findViewById(R.id.disco);
        disco.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                      id = 9;
                    Intent i = new Intent("com.abhishek.application.androtorch.ColorActivity");
                    startActivity(i);
            }
        });
        tree = (Button) findViewById(R.id.tree);
        tree.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                    id = 10;
                    Intent i = new Intent("com.abhishek.application.androtorch.ColorActivity");
                    startActivity(i);
            }
        });
    }
    
    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        
    }
    
//    @Override
//    public void onBackPressed() {
////        super.onDestroy();
//        System.exit(0);
//        Settings.this.finish();
//        
//    }
    
    boolean isFlashAvailable() {
        return this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }
    
    protected void onPause() {
        super.onPause();
        WindowManager.LayoutParams settings = getWindow().getAttributes();
        settings.screenBrightness = -1f;
        getWindow().setAttributes(settings);
    }
    
    @Override
    protected void onRestart() {
        super.onRestart();
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
}
