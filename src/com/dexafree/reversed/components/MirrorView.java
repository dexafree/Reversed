package com.dexafree.reversed.components;

import com.dexafree.reversed.model.Mirror;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class MirrorView extends BaseView{
    
    private boolean used;
    private Mirror mirror;
    
    public MirrorView(Mirror mirror){
        this.mirror = mirror;
        mShape = getPlatform(mirror.getX(), mirror.getY(), 1, 2);
    }
    
    public boolean isUsed(){
        return false;
    }
    
    public void use() {
        used = true;
    }
    
    
    public void render(Graphics g){
        g.setColor(Color.white);
        g.fill(mShape);
    }
}
