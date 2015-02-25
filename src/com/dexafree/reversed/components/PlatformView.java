package com.dexafree.reversed.components;

import com.dexafree.reversed.AssetManager;
import com.dexafree.reversed.model.Platform;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class PlatformView extends BaseView {
    
    private Platform platform;
    
    public PlatformView(Platform platform){
        this.platform = platform;
        int x = platform.getX();
        int y = platform.getY();
        int width = platform.getWidth();
        int height = platform.getHeight();
        mShape = getPlatform(x, y, width, height);
    }
    
    public void render(Graphics g){
        g.setColor(Color.green);
        g.fill(mShape);
    }
    
}
