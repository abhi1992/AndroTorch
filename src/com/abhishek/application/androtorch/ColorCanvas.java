package com.abhishek.application.androtorch;

import com.abhishek.application.androtorch.R.id;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class ColorCanvas extends View {
    
    private int color, id, sW, sH, imgW, imgH;
    private float time = 10;
    private float mouseX, mouseY, theta;
    private Paint paint;
    private Bitmap bulbOnImage, bulbOffImage;
    private boolean drawImage;
    
    public ColorCanvas(Context context) {
        super(context);
        init();
    }
        
    private void init() {
        paint = new Paint();
        bulbOnImage = BitmapFactory.decodeResource(getResources(), R.drawable.bulb_on);
        bulbOffImage = BitmapFactory.decodeResource(getResources(), R.drawable.bulb_off);
    }
    
    public ColorCanvas(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    
    public ColorCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setMouseX(float x) {
        mouseX = x;
    }
    
    public void setMouseY(float y) {
        mouseY = y;
    }
    
    public void setColor(int color) {
        this.color = color;
        paint = new Paint();
    }
    
    public void drawImage(boolean b) {
        drawImage = b;
        id = 4;
    }
    
    int getScreenWidth() {
        return sW;
    }
    
    int getScreenHeight() {
        return sH;
    }
    
    void setScreenWidth(int w) {
        sW = w;
    }
    
    void setScreenHeight(int h) {
        sH = h;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Style.FILL);
        
        paint.setColor(color);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        paint = new Paint();
        if(drawImage && id == 4) {
            bulbOnImage = resizeImage(bulbOnImage, getScreenWidth(), getScreenHeight());
            canvas.drawBitmap(bulbOnImage, (getScreenWidth() - bulbOnImage.getWidth()) / 2, 
                    (getScreenHeight() - bulbOnImage.getHeight()) / 2, paint);
            
        }
        else if(id == 4){
            bulbOffImage = resizeImage(bulbOffImage, getScreenWidth(), getScreenHeight());
            canvas.drawBitmap(bulbOffImage, (getScreenWidth() - bulbOffImage.getWidth()) / 2, 
                    (getScreenHeight() - bulbOffImage.getHeight()) / 2, paint);
        }
        else if(id == 8) {
            paint.setColor(Color.WHITE);
            canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
            paint.setColor(Color.BLACK);
            doSomeCrazyStuff(canvas);
        }
        else if(id == 10) {
            paint.setStrokeWidth(2);
            paint.setColor(color);
            canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
            paint.setColor(Color.LTGRAY);
            drawTree(canvas);
        }
    }
    
    private void drawTree(Canvas canvas) {
        float a = (mouseX / (float) getWidth()) * 90f;
        theta = (float) Math.toRadians(50*a);
        
        canvas.translate(getWidth()/2,getHeight());
        canvas.drawLine(0,0,0,-120, paint);
        canvas.translate(0,-120);
        branch(canvas, 120);
    }
    
    void branch(Canvas canvas, float h) {
        h *= 0.66;
        if (h > 2) {
          canvas.save();
          canvas.rotate(theta);   // Rotate by theta
          canvas.drawLine(0, 0, 0, -h, paint);  // Draw the branch
          canvas.translate(0, -h); // Move to the end of the branch
          branch(canvas, h);       // Ok, now call myself to draw two new branches!!
          canvas.restore();     // Whenever we get back here, we "pop" in order to restore the previous matrix state
          
          // Repeat the same thing, only branch off to the "left" this time!
          canvas.save();
          canvas.rotate(-theta);
          canvas.drawLine(0, 0, 0, -h, paint);
          canvas.translate(0, -h);
          branch(canvas, h);
          canvas.restore();
        }
    }
    
    private void doSomeCrazyStuff(Canvas canvas) {
        
        time += 0.15f;
//        canvas.save();
        paint.setStrokeWidth(5);
        canvas.translate(getWidth()/2, getHeight()/2);
        for(int i = 0; i < 250; i++) {
            
            canvas.rotate(time);
//            canvas.drawLine(i, i, i*10, i*10, paint);
            canvas.drawCircle(i, i, i*6/35, paint);
        }
//        canvas.restore();
        invalidate();
    }
    
    public Bitmap resizeImage(Bitmap image,int maxWidth, int maxHeight)
    {
        Bitmap resizedImage = null;
        try {
            int imageHeight = image.getHeight();


            if (imageHeight > maxHeight)
                imageHeight = maxHeight;
            int imageWidth = (imageHeight * image.getWidth())
                    / image.getHeight();

            if (imageWidth > maxWidth) {
                imageWidth = maxWidth;
                imageHeight = (imageWidth * image.getHeight())
                        / image.getWidth();
            }

            if (imageHeight > maxHeight)
                imageHeight = maxHeight;
            if (imageWidth > maxWidth)
                imageWidth = maxWidth;


            resizedImage = Bitmap.createScaledBitmap(image, imageWidth,
                    imageHeight, true);
        } catch (OutOfMemoryError e) {

            e.printStackTrace();
        }catch(NullPointerException e)
        {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return resizedImage;
    }
}
