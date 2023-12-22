package droidlymobilegames.ca.tilemanager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.xml.transform.sax.TemplatesHandler;

public class TileManager extends TileInfo{

    public TileInfo[] layer1 = new TileInfo[100];//Number is how many images/IDs we have

    public TileManager(Gameview gameview){
        this.gameView = gameview;
        worldTileNumLayer1 = new int[50][50];//ROOM/WORLD size
        setupTileInfo();//Setup all of our tile details before trying to draw
    }

    public void draw(Canvas canvas){
        int tileCol = 0;
        int tileRow = 0;
        while (tileCol < 50 && tileRow < 50){
            tileId1 = worldTileNumLayer1[tileCol][tileRow];
            int tilePosX = tileCol * 100;//Sets the tile at the position X in the world times the scaled tilesize 160 in example
            int tilePosY = tileRow * 100;//Sets position Y times scaled tilesize
            int tileScreenX = tilePosX - gameView.posX + gameView.screenX;
            int tileScreenY = tilePosY - gameView.posY + gameView.screenY;

            if(tileScreenX > -100
                    && tileScreenY > -100
                    && tileScreenX < gameView.getDisplayWidth() + (100 *2)
                    && tileScreenY < (gameView.getDisplayHeight() + 100)) {
                if (layer1[tileId1] != null) {
                    if (layer1[tileId1].tileImg != null) {
                        canvas.drawBitmap(layer1[tileId1].tileImg, tileScreenX, tileScreenY, null);
                    }
                }
            }
            tileCol ++;
            if (tileCol == 50){//Check if tileCol reaches the end in this case 100 tiles then resets back to 0 then increases rows
                tileCol = 0;
                tileRow++;
            }
        }

    }

    public void loadWorldMap(final String _mapname){//Used to load map from the raw folder in res
        try {
            inputStream = gameView.getContext().getResources().openRawResource(
                    gameView.getContext().getResources().getIdentifier(
                            _mapname,"raw", gameView.getContext().getPackageName()));
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int column = 0;
            int row = 0;
            while (column < 05 && row < 50){
                String line = bufferedReader.readLine();

                while (column < 50){
                    //Splits the line to read the data from the text
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[column]);
                    worldTileNumLayer1[column][row]= num;
                    column ++;
                }
                if (column == 50){
                    column = 0;
                    row ++;
                }
            }
            bufferedReader.close();
        }catch (Exception e){
        }
    }

    public void setupTileInfo(){
        setupTilesheet();
        layer1[0] = new TileInfo();
        layer1[0].tileImg = allTileImgs[1];//allTileImgs[0] is an empty image
        layer1[2] = new TileInfo();
        layer1[2].tileImg = allTileImgs[2];
    }
    public void setupTilesheet(){
        Bitmap tilesheet;
        int col1 = 0;
        int row1 = 0;
        int numoftiles = 0;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        tilesheet = BitmapFactory.decodeResource(gameView.getResources(),
                R.drawable.groundtilesheet,bitmapOptions);
        int maxcol1 = tilesheet.getWidth()/16;
        int maxrow1 = tilesheet.getHeight()/16;
        while (row1 < maxrow1){
            allTileImgs[numoftiles] = Bitmap.createScaledBitmap(Bitmap.createBitmap
                            (tilesheet,col1 * 16,row1 * 16,16,16),100,
                    100,false);
            col1 ++;
            if (col1 == maxcol1){
                col1 = 0;
                row1 ++;
            }
            numoftiles ++;
        }
    }
}
