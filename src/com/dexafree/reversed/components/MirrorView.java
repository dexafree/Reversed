package com.dexafree.reversed.components;

import com.dexafree.reversed.AssetManager;
import com.dexafree.reversed.Utils;
import com.dexafree.reversed.model.GameObject;
import com.dexafree.reversed.model.Mirror;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.ArrayList;

public class MirrorView extends BaseView{
    
    private boolean canUse = false;
    private Mirror mirror;
    private Color color = Color.white;
    private Image mImage;
    
    public MirrorView(Mirror mirror, AssetManager manager){
        this.mirror = mirror;
        color = Utils.getColor(mirror.getColor());
        mShape = getPlatform(mirror.getX(), mirror.getY(), 1, 2);
        mImage = manager.getMirrorImage();
    }
    
    public boolean canUse(){
        return canUse || color.equals(color.white);
    }
    
    
    public void render(Graphics g){
        mImage.draw(mShape.getX(), mShape.getY(), color);
    }
    
    
    public void unlock(GameObject object){
        if(!canUse && object.getColor().equalsIgnoreCase(mirror.getColor())){
            canUse = true;
            color = Color.white;
        }
        
    }
}
