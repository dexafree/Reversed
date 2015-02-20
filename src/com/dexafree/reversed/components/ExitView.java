package com.dexafree.reversed.components;

import com.dexafree.reversed.model.Exit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class ExitView extends BaseView {
    
    private Exit exit;

    public ExitView(Exit exit){
        this.exit = exit;
        mShape = getPlatform(exit.getX(), exit.getY(), 1, 2);
    }
    
    public void render(Graphics g){
        g.setColor(Color.cyan);
        g.fill(mShape);
    }

}
