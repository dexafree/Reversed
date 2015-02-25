package com.dexafree.reversed;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class AssetManager {
    
    private Image exitImage;
    private Image keyImage;
    
    public AssetManager() throws SlickException{
        exitImage = new Image("assets/exit.png");
        keyImage = new Image("assets/key.png");
    }
    
    public Image getExitImage(){
        return exitImage;
    }

    public Image getKeyImage() {
        return keyImage;
    }
}
