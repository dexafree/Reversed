package com.dexafree.reversed.model;

public class GameObject {
    
    private String type;
    private String color;
    private int x;
    private int y;

    public GameObject(int x, int y, String type, String color) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
