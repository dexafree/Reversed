package com.dexafree.reversed.components;

import com.dexafree.reversed.AssetManager;
import com.dexafree.reversed.Game;
import com.dexafree.reversed.Main;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class IntroScreen {
    
    public interface IIntroScreen {
        
        void onIntroFinished();
        
    }
    
    private final static String[] WELCOME_STRINGS = new String[]{
            "Welcome to REVERSED",
            "These are the only rules:",
            "1. You can only go to the right (Right Arrow)",
            "2. You can jump (Up Arrow)",
            "3. You can use mirrors by pressing SPACE",
            "4. Some mirrors require a key to be usable",
            "To restart a level, press R",
            "\n\nI hope you enjoy the Game",
            "\n\nLudum Dare February 2015",
            "\n\n© Dexafree"
            
    };
    private final static int STRING_TIME = 2000;
    private IIntroScreen callback;
    private static int STATE;
    private int acumTime;
    private int logoX;
    private Image logo;
    
    public IntroScreen(AssetManager manager, IIntroScreen callback){
        this.callback = callback;
        this.logo = manager.getLogoImage();
    }
    
    public void init(){
        acumTime = 0;
        STATE = 0;

        logoX = (Main.WIDTH/2 - logo.getWidth()/2);
    }
    
    
    
    public void update(GameContainer gc, int delta){
        
        acumTime += delta;
        
        if(acumTime > STRING_TIME){
            acumTime = 0;
            STATE++;
        }
        if(STATE == WELCOME_STRINGS.length){
            callback.onIntroFinished();
        }
    }
    
    public void render(GameContainer gc, Graphics g){

        logo.draw(logoX, 100);
        
        int initPosition = 300;
        
        g.setColor(Color.black);
        g.drawRect(0, 0, Main.WIDTH, Main.HEIGHT);
        
        g.setColor(Color.white);
        for(int i=0;i<WELCOME_STRINGS.length && i<=STATE ;i++){
            
            if(i == 0)
                g.drawString(WELCOME_STRINGS[i], 400, initPosition+(i*30));
            else
                g.drawString(WELCOME_STRINGS[i], 300, initPosition+(i*30));
        }

        
    }
    
}
