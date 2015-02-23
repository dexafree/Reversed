package com.dexafree.reversed.model;

import java.util.ArrayList;

public class Mirror {
    
    private boolean isUsed = false;
    private int x;
    private int y;
    private String color = "white";

    public Mirror(int x, int y, String color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public String getColor(){
        return color;
    }
    
    public boolean canUse(ArrayList<GameObject> inventory){
        
        if(color.equals("white")){
            return true;
        }
        
        for(GameObject o : inventory){
            if(o.getColor().equalsIgnoreCase(color)){
                return true;
            }
        }
        return false;
    }
}
