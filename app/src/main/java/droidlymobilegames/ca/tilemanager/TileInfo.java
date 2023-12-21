package droidlymobilegames.ca.tilemanager;

import android.graphics.Bitmap;

import java.io.BufferedReader;
import java.io.InputStream;

public class TileInfo {
    public InputStream inputStream;
    public BufferedReader bufferedReader;
    public Gameview gameView;

    public int worldTileNumLayer1[][];//XY 2D Tiles
    public int tileId1,tileId2 = 1;
    public Bitmap tileImg = null;
    public Bitmap[] allTileImgs = new Bitmap[100];
}
