package com.dexafree.reversed.components;

import com.dexafree.reversed.AssetManager;
import com.dexafree.reversed.Game;
import com.dexafree.reversed.model.Exit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class ExitView extends BaseView {
    
    private Exit exit;
    private Image exitImage;

    public ExitView(Exit exit, AssetManager manager){
        this.exit = exit;
        mShape = getPlatform(exit.getX(), exit.getY(), 1, 2);
        exitImage = manager.getExitImage();
    }
    
    public void render(Graphics g){
        /*g.setColor(Color.cyan);
        g.fill(mShape);*/
        float x = mShape.getX();
        float y = mShape.getY();
        
        exitImage.draw(x, y);
    }

}
