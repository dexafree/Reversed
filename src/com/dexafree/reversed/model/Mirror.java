package com.dexafree.reversed.model;

public class Mirror {
    
    private boolean isUsed = false;
    private int x;
    private int y;

    public Mirror(int x, int y) {
        this.x = x;
        this.y = y;
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
    
    public void use(){
        isUsed = true;
        
    }
}
