package com.dexafree.reversed.components;

import com.dexafree.reversed.AssetManager;
import com.dexafree.reversed.model.Platform;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class PlatformView extends BaseView {
    
    private Platform platform;
    private Image mImage;
    
    public PlatformView(Platform platform, AssetManager manager){
        this.platform = platform;
        int x = platform.getX();
        int y = platform.getY();
        int width = platform.getWidth();
        int height = platform.getHeight();
        mShape = getPlatform(x, y, width, height);
        mImage = manager.getTileImage();
    }
    
    public void render(Graphics g){
        g.fillRect(mShape.getX(), mShape.getY(), mShape.getWidth(), mShape.getHeight(), mImage, 0, 0);
    }
    
}
