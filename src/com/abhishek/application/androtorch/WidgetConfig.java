package com.abhishek.application.androtorch;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.widget.Button;
import android.widget.RemoteViews;

public class WidgetConfig extends BroadcastReceiver {
    private static boolean isLightOn = false;
    private static Camera camera;
    
    @Override
    public void onReceive(Context context, Intent intent) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            
            if(isLightOn) {
                    views.setImageViewResource(R.id.FlashOn, R.drawable.bulb_icon_off);
            } else {
                
                    views.setImageViewResource(R.id.FlashOn, R.drawable.bulb_icon_on);
            }

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(new ComponentName(context,     TorchWidget.class),
                                                                             views);

            if (isLightOn) {
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
                            isLightOn = false;
                    }

            } else {
                    
                    camera = Camera.open();
                    if(camera == null) {
                        
                    } else {
                            Parameters param = camera.getParameters();
                            param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                            try {
                                    camera.setParameters(param);
                                    camera.startPreview();
                                    isLightOn = true;
                            } catch (Exception e) {
                                camera.release();
                            }
                    }
            }       
    }    
}
