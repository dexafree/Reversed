package com.dexafree.reversed.components;

import com.dexafree.reversed.Main;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class PlotScreen {
    
    public interface IPlotScreen {
        void onPlotFinished();
    }
    
    private IPlotScreen callback;
    
    private final static int CHARACTER_TIME = 100;
    private int elapsedTime;
    private int currentChar;
    private String plotString;
    private String currentString;
    
    public PlotScreen(String plotString, IPlotScreen callback){
        this.callback = callback;
        this.plotString = plotString;
        currentString = "";
    }
    
    public void init(){
        currentChar = 0;
        elapsedTime = 0;
    }
    
    public void update(GameContainer gc, int delta){
        
        elapsedTime += delta;

        if(elapsedTime >= CHARACTER_TIME && currentChar < plotString.length()){
            currentChar++;
            currentString = plotString.substring(0, currentChar);
            elapsedTime = 0;
        }

        if(currentChar == plotString.length() && elapsedTime >= 1000){
            callback.onPlotFinished();
        }
        
    }
    
    public void render(GameContainer gc, Graphics g){

        g.setColor(Color.black);
        g.drawRect(0, 0, Main.WIDTH, Main.HEIGHT);
        
        g.setColor(Color.white);
        g.drawString(currentString, 300, 300);
        
    }
    
}
