package com.dexafree.reversed.components;

import com.dexafree.reversed.Main;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class IntroScreen {
    
    public interface IntroFinished {
        
        void onIntroFinished();
        
    }
    
    private final static String[] WELCOME_STRINGS = new String[]{
            "Welcome to ᗡƎƧЯƎVƎЯ",
            "These are the only rules:",
            "1. You can only go to the right",
            "2. You can jump",
            "3. You can use mirrors by pressing SPACE",
            "4. Some mirrors require a key to be usable",
            "To restart a level, press R"
            
    };
    private final static int STRING_TIME = 2000;
    private IntroFinished callback;
    private static int STATE;
    private int acumTime;
    
    public IntroScreen(IntroFinished callback){
        this.callback = callback;
        
    }
    
    public void init(){
        acumTime = 0;
        STATE = 0;
    }
    
    public void update(GameContainer gc, int delta){
        
        acumTime += delta;
        
        if(acumTime > STRING_TIME){
            acumTime = 0;
            STATE++;
        }
        if(STATE == 8){
            callback.onIntroFinished();
        }
    }
    
    public void render(GameContainer gc, Graphics g){
        
        int initPosition = 40;
        
        g.setColor(Color.black);
        g.drawRect(0, 0, Main.WIDTH, Main.HEIGHT);
        
        g.setColor(Color.white);
        for(int i=0;i<WELCOME_STRINGS.length && i<=STATE ;i++){
            g.drawString(WELCOME_STRINGS[i], 300, initPosition+(i*30));
        }

        
    }
    
}
