package droidlymobilegames.ca.tilemanager;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class Gameloop extends Thread {

    private SurfaceHolder surfaceHolder;
    private boolean isRunning;
    private final int TARGET_UPS = 120;  // Target updates per second
    private long targetUpdateTimeNanos = 1000000000 / TARGET_UPS;  // Nanoseconds per update
    private Gameview gameview;

    public Gameloop(SurfaceHolder holder, Gameview gameview) {
        this.gameview = gameview;
        surfaceHolder = holder;
        isRunning = false;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    @Override
    public void run() {
        long startTime;
        long elapsedTime;
        long sleepTime;

        while (isRunning) {
            startTime = System.nanoTime();

            // Perform game logic update
            update();

            // Draw on the canvas
            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    draw(canvas);
                }
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

            // Calculate sleep time to achieve target updates per second
            elapsedTime = System.nanoTime() - startTime;
            sleepTime = Math.max(0, (targetUpdateTimeNanos - elapsedTime) / 1000000);

            try {
                // Sleep to control the update rate
                sleep(sleepTime);
            } catch (InterruptedException e) {
                // Handle interruption if needed
            }
        }
    }

    private void update() {
        // Update game logic here
        gameview.update();
    }

    private void draw(Canvas canvas) {
        // Draw on the canvas here
        gameview.draw(canvas);
    }
}