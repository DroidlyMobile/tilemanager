package droidlymobilegames.ca.tilemanager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Gameview extends SurfaceView implements SurfaceHolder.Callback {

    private Gameloop gameThread;
    public Gameloop2 gameloop2;
    public Paint textpaint;
    int posX,posY = 100;
    int screenX,screenY = 1000;
    boolean screenTouched = false;
    public TileManager tileManager;

    public Gameview(Context context) {
        super(context);
        /*getHolder().addCallback(this);
        gameThread = new Gameloop(getHolder(), this);*/
        getHolder().addCallback(this);
        gameloop2 = new Gameloop2( this,getHolder());
        setFocusable(true);
        textpaint = new Paint();
        textpaint.setColor(Color.WHITE);
        textpaint.setTextSize(25);
        tileManager = new TileManager(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        /*gameThread = new Gameloop(getHolder(), this);
        gameThread.setRunning(true);
        gameThread.start();*/
        if (gameloop2.getState().equals(Thread.State.TERMINATED)) {
            getHolder().addCallback(this);
            gameloop2 = new Gameloop2(this, getHolder());
        }
        gameloop2.startLoop();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Handle surface changes if needed
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        gameThread.setRunning(false);
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                // Handle the exception
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        // Draw on the canvas here
        tileManager.draw(canvas);
        canvas.drawText("TEST TEST", posX,1000,textpaint);
    }

    // Update game state (called in the game loop)
    public void update() {
        // Update game logic here
        if (screenTouched) {
            posX += 10;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerID = event.getPointerId(event.getActionIndex());
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                screenX = ((int)event.getX() + 25) / 100;
                screenY = (int)event.getY() / 100;
                posX = (screenX + ((posX -   screenX) / 100)) - (posX / 100);
                posY = screenY + (posY/ 100);
                if (posX > 0) {
                    tileManager.worldTileNumLayer1[(int)posX][(int)posY] = 2;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
       /* return super.onTouchEvent(event);*/
        return true;
    }



    public int getDisplayWidth(){
        return getContext().getResources().getDisplayMetrics().widthPixels;
    }
    public int getDisplayHeight(){
        return getContext().getResources().getDisplayMetrics().heightPixels;
    }

}