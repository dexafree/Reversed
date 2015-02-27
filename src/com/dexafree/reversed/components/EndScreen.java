package com.dexafree.reversed.components;

import com.dexafree.reversed.AssetManager;
import com.dexafree.reversed.Main;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class EndScreen {
    
    public interface EndScreenFinish {
        void onEndScreenFinished();
    }
    
    private final static String[] SENTENCES = new String[]{
            "You finished the game!",
            "I know that there are only a few levels, but I did what I could",
            "Maybe in the future I will add some more.",
            "\n\nI hope you enjoyed the game :)",
            "\n\nCredits:\n- Depth: Character design\n- JRoig: Graphic design\n- Raindrinker: Challenging me to do this",
            "\n\n\n\n\n© 2015 Dexafree"
            
    };
    
    private EndScreenFinish callback;
    private final static int SENTENCE_TIME = 2000;
    private int STATE;
    private int acumTime;
    private Image logoImage;
    private int logoX;
    
    public EndScreen(AssetManager manager, EndScreenFinish callback){
        this.logoImage = manager.getLogoImage();
        this.callback = callback;
        logoX = (Main.WIDTH/2 - logoImage.getWidth()/2);
    }
    
    public void init(){
        acumTime = 0;
        STATE = 0;
    }
    
    public void update(GameContainer gc, int delta){
        
        acumTime += delta;
        
        if(acumTime > SENTENCE_TIME){
            STATE++;
            acumTime = 0;
        }
        
        if(acumTime > SENTENCE_TIME && STATE == SENTENCES.length){
            callback.onEndScreenFinished();
        }
        
        
    }
    
    public void render(GameContainer gc, Graphics g){

        logoImage.draw(logoX, 100);


        int startingX = 50;


        g.setColor(Color.white);
        for(int i=0;i<STATE;i++){
            g.drawString(SENTENCES[i], startingX, 400 + (i*30));
        }
        
    }
    
}
