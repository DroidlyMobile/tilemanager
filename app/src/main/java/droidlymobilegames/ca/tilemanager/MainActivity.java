package droidlymobilegames.ca.tilemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Gameview gameview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameview = new Gameview(this);
        setContentView(gameview);
    }

    @Override
    protected void onPause() {
        gameview.gameloop2.stopLoop();
        super.onPause();
    }
}