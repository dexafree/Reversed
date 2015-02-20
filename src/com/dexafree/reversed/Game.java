package com.dexafree.reversed;

import com.dexafree.reversed.model.Level;
import com.dexafree.reversed.model.LevelView;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;

import java.util.ArrayList;

public class Game extends BasicGame {
    
    public final static int LINES_SIZE = 50;
    public static int WIDTH_SQUARES;
    public static int HEIGHT_SQUARES;
    private final static int SECONDS_TILL_FLIP = 1;
    private final static int FLIP_TIME = SECONDS_TILL_FLIP * 1000;
    private final static int STARTING_LEVEL = 0;
    private final static boolean DEBUG_MODE = true;
    private static boolean DRAW_LINES = false;
    
    private Circle lastClick;
    
    private final ArrayList<Level> levels;

    private int currentLevel;
    private LevelView currentLevelView; 
    private Player player;
    
    private int timeSinceLastFlip = 0;
    private boolean canFlip = true;
    
    private boolean isWin = false;
    

    public Game() throws SlickException {
        super("ᗡƎƧЯƎVƎЯ");
        levels = LevelLoader.loadLevels();
        currentLevel = 0;
    }


    public void init(GameContainer gc) throws SlickException {
        
        WIDTH_SQUARES = gc.getWidth() / LINES_SIZE;
        HEIGHT_SQUARES = gc.getHeight() / LINES_SIZE;

        setLevel(gc, STARTING_LEVEL);
        setPlayer(gc);

    }
    
    private void setLevel(GameContainer gc, int levelNum) throws SlickException{
        this.currentLevel = levelNum;
        Level level = levels.get(levelNum);
        currentLevelView = new LevelView(level);
        currentLevelView.init(gc);
        player = new Player(currentLevelView);
        player.init();
    }
    
    private void restartLevel(GameContainer gc) throws SlickException {
        currentLevelView.init(gc);
        
    }
    
    private void setPlayer(GameContainer gc) throws SlickException{
        player = new Player(currentLevelView);
        player.init();
    }



    public void render(GameContainer gc, Graphics g) throws SlickException {

        currentLevelView.render(gc, g);
        player.render(gc, g);
        
        if(!isWin) {
            
            if(DEBUG_MODE) {

                drawDebugLines(gc, g, LINES_SIZE);

                //showPosition(g);
                showInfo(gc, g);
                
                if(lastClick != null) {
                    g.setColor(Color.red);
                    g.fill(lastClick);
                }
            }
        } else {
            showWin(gc, g);
            if(currentLevel < levels.size()-1) {
                setLevel(gc, currentLevel + 1);
                setPlayer(gc);
            }
            isWin = false;
        }

    }


    public void update(GameContainer gc, int delta) throws SlickException {

        currentLevelView.update(gc, delta);
        player.update(gc, delta);

        timeSinceLastFlip += delta;

        if (timeSinceLastFlip > FLIP_TIME && !canFlip) {
            canFlip = true;
        }

        if (gc.getInput().isKeyDown(Input.KEY_SPACE) && canFlip && player.isOnMirror()) {
            currentLevelView.invert(gc);
            player.invert(gc);
            canFlip = false;
            timeSinceLastFlip = 0;
        }

        if (gc.getInput().isKeyDown(Input.KEY_R)) {
            restartLevel(gc);
            setPlayer(gc);
        }

        if(!isWin){
            isWin = player.isOnExit() && gc.getInput().isKeyDown(Input.KEY_SPACE);
        }
        
        if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
            int x = gc.getInput().getMouseX();
            int y = gc.getInput().getMouseY();
            
            lastClick = new Circle(x, y, 10); 
        }
        
        if(gc.getInput().isKeyDown(Input.KEY_L) && timeSinceLastFlip > FLIP_TIME){
            DRAW_LINES = !DRAW_LINES;
            timeSinceLastFlip = 0;
        }

    }

    // Draw a grid on the screen for easy positioning
    public void drawDebugLines(GameContainer gc, Graphics g, int size) {
        
        if(DRAW_LINES) {
            int resolution = gc.getWidth();
            g.setColor(Color.lightGray);
            for (int i = 0; i < resolution; i += size) {
                g.drawLine(i, 0, i, resolution);
                g.drawLine(0, i, resolution, i);
            }
        }
    }
    
    private void showPosition(Graphics g){
        g.drawString("X: "+player.getX(), 50, 50);
        g.drawString("Y: "+player.getY(), 50, 70);
    }
    
    private void showInfo(GameContainer gc, Graphics g){
        if(player.isOnMirror()) {
            g.drawString("FLIP", 50, 40);
        }
        
        g.setColor(Color.red);
        
        int x = gc.getInput().getMouseX();
        int y = gc.getInput().getMouseY();
        
        g.drawString(x+"", 50, 80);
        g.drawString(y+"", 50, 90);
        
        int squareX = x / LINES_SIZE;
        int squareY = y / LINES_SIZE;
        
        g.drawString(squareX+"", 100, 80);
        g.drawString(squareY+"", 100, 90);
    }
    
    private void showWin(GameContainer gc, Graphics g){
        g.drawString("WIN", 50, 40);
        
    }

}