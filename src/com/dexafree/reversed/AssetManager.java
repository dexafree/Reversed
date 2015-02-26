package com.dexafree.reversed;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class AssetManager {
    
    private Image exitImage;
    private Image keyImage;
    private Image gameExitImage;
    private Image mirrorImage;
    private Image playerImage;
    private Image tileImage;
    private Image staticTileImage;
    
    public AssetManager() throws SlickException{
        exitImage = new Image("assets/exit.png");
        keyImage = new Image("assets/key.png");
        gameExitImage = new Image("assets/game_exit.png");
        mirrorImage = new Image("assets/mirror.png");
        playerImage = new Image("assets/player.png");
        tileImage = new Image("assets/tile.png");
        staticTileImage = new Image("assets/static_tile.png");
    }
    
    public Image getExitImage(){
        return exitImage;
    }

    public Image getKeyImage() {
        return keyImage;
    }

    public Image getGameExitImage() {
        return gameExitImage;
    }

    public Image getMirrorImage() {
        return mirrorImage;
    }

    public Image getPlayerImage() {
        return playerImage;
    }

    public Image getTileImage() {
        return tileImage;
    }

    public Image getStaticTileImage() {
        return staticTileImage;
    }
}
