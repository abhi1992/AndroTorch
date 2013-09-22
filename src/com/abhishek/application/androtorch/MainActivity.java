package com.abhishek.application.androtorch;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity {
    private Button toggleOnOff;
    private Button settings;
    private View view;
    private boolean on;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = (View) findViewById(R.id.view1);
        toggleOnOff = (Button) findViewById(R.id.FlashOn);
        settings = (Button) findViewById(R.id.button2);
        settings.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                
                Intent i = new Intent("com.abhishek.application.androtorch.Settings");
                startActivity(i);
//                MainActivity.this.finish();
            }
        });
        toggleOnOff.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                toggleBrightness(v);
            }
        });
    }
    
    void toggleBrightness(View v) {
        on = !on;
        if(on) {
                adjustBrightness(1f);
                toggleOnOff.setBackgroundResource(R.drawable.light);
                int color = Color.argb(255, 255, 255, 255);
                view.setBackgroundColor(color);
                settings.setBackgroundResource(R.drawable.menu_glow);
        }
        else {
                adjustBrightness(-1f);
                toggleOnOff.setBackgroundResource(R.drawable.power);
                view.setBackgroundResource(R.drawable.a);
                settings.setBackgroundResource(R.drawable.menu);
//            toggleOnOff.setText("Off");
//            toggleOnOff.setBackgroundResource(R.drawable.power);
//            toggleOnOff.setBackgroundColor(0xffeeeeee);
//            v.setBackgroundResource(R.drawable.a);
            
        }
    }
    
//    void setFlash(boolean b) {
//        if(b)
//        {
//            Camera cam = Camera.open();
//            Parameters p = cam.getParameters();
//            p.setFlashMode(Parameters.FLASH_MODE_TORCH);
//            cam.setParameters(p);
//            cam.startPreview();
//        }
//        else {
//            Camera cam = Camera.open();
//            Parameters p = cam.getParameters();
//            p.setFlashMode(Parameters.FLASH_MODE_TORCH);
//            cam.setParameters(p);
//            cam.stopPreview();
//            cam.release();
//        }
//    }
    
    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        
    }
    
//    @Override
//    public void onBackPressed() {
//        
//        Intent i = new Intent("com.abhishek.application.androtorch.Settings");
//        startActivity(i);
////        super.onBackPressed();
//    }
    
    public void setBrightness(float brightness){
        try {
        int brightnessMode = android.provider.Settings.System.getInt(getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE);
            if (brightnessMode == android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
            android.provider.Settings.System.putInt(getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE, android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        }
     
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = brightness;
        getWindow().setAttributes(layoutParams);
        } catch (Exception e){
            // do something useful
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        adjustBrightness(-1);
    }
    
    private void adjustBrightness(float f) {
        WindowManager.LayoutParams settings = getWindow().getAttributes();
        settings.screenBrightness = f;
        getWindow().setAttributes(settings);
    }
    
    @Override
    protected void onRestart() {
        super.onRestart();
//        adjustBrightness(1);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        adjustBrightness(1);
    }
    
//    @Override
//    public void onBackPressed() {
//        System.exit(0);
////        super.onBackPressed();
//    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }
}