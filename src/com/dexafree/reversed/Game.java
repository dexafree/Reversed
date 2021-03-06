package com.dexafree.reversed;

import com.dexafree.reversed.components.EndScreen;
import com.dexafree.reversed.components.IntroScreen;
import com.dexafree.reversed.components.PlotScreen;
import com.dexafree.reversed.model.Level;
import com.dexafree.reversed.model.LevelView;
import com.dexafree.reversed.model.Point;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;

public class Game extends BasicGame {
    
    public final static int LINES_SIZE = 50;
    public static int WIDTH_SQUARES;
    public static int HEIGHT_SQUARES;
    private final static float SECONDS_TILL_FLIP = 0.5f;
    private final static float FLIP_TIME = SECONDS_TILL_FLIP * 1000;
    private final static int STARTING_LEVEL = 0;
    private final static long DISAPPOINT_TIME = 20000;
    private final static boolean DEBUG_MODE = false;
    private final static boolean EDIT_MODE = false;
    private static boolean DRAW_LINES = false || EDIT_MODE;
    private static boolean SHOW_OPOSITE = false;
    public final static boolean FOR_COMPILATION = false;
    
    public static int NUM_LEVELS = -1;
    
    
    private Circle lastClick;
    private Rectangle shadow;
    
    private final ArrayList<Level> levels;
    private int currentLevel;
    
    private LevelView currentLevelView;
    private Player player;
    private IntroScreen introScreen;
    private PlotScreen plotScreen;
    private EndScreen endScreen;
    private AssetManager assetManager;
    
    private PlotScreen.IPlotScreen introFinished;
    private EndScreen.EndScreenFinish endScreenCallback;
    
    private int timeSinceLastFlip = 0;
    private long timeSinceLevelStarted;
    private boolean canFlip = true;
    
    private boolean isWin = false;
    private boolean isIntroFinished = false || DEBUG_MODE;
    private boolean isPlotFinished = false || DEBUG_MODE;
    private boolean isEndFinished = false;
    private boolean isGameFinished = false;
    

    public Game() throws SlickException {
        super("ᗡƎƧЯƎVƎЯ");
        levels = new LevelLoader().loadLevels();
        NUM_LEVELS = levels.size();
    }


    public void init(GameContainer gc) throws SlickException {

        currentLevel = 0;
        timeSinceLevelStarted = 0;
        
        WIDTH_SQUARES = gc.getWidth() / LINES_SIZE;
        HEIGHT_SQUARES = gc.getHeight() / LINES_SIZE;
        
        assetManager = new AssetManager();

        if(!EDIT_MODE) {
            introScreen = new IntroScreen(assetManager, new IntroScreen.IIntroScreen() {
                @Override
                public void onIntroFinished() {
                    isIntroFinished = true;
                }
            });
            introScreen.init();
            introFinished = new PlotScreen.IPlotScreen() {
                @Override
                public void onPlotFinished() {
                    isPlotFinished = true;
                }
            };
            endScreenCallback = new EndScreen.EndScreenFinish() {
                @Override
                public void onEndScreenFinished() {
                    isEndFinished = true;
                }
            };
            plotScreen = new PlotScreen(levels.get(currentLevel).getPlotSentence(), introFinished);
            endScreen = new EndScreen(assetManager, endScreenCallback);
            
            setLevel(gc, STARTING_LEVEL);
            setPlayer(gc);
        } else {
            setEditMode(gc);
        }

    }
    
    private void setEditMode(GameContainer gc) throws SlickException{
        currentLevelView = new LevelEditMode(null);
        currentLevelView.init(gc);
    }
    
    private void setLevel(final GameContainer gc, int levelNum) throws SlickException{
        this.currentLevel = levelNum;
        Level level = levels.get(levelNum);
        currentLevelView = new LevelView(level, assetManager, new LevelView.OnLevelFinished() {
            @Override
            public void run() throws SlickException{
                isWin = false;
                isPlotFinished = false;
                nextLevel(gc);
            }
        });
        timeSinceLevelStarted = 0;
        currentLevelView.init(gc);
        player = new Player(currentLevelView, assetManager);
        player.init();
    }
    
    private void restartLevel(GameContainer gc) throws SlickException {
        currentLevelView.init(gc);
        timeSinceLevelStarted = 0;
        
    }
    
    private void setPlayer(GameContainer gc) throws SlickException{
        player = new Player(currentLevelView, assetManager);
        player.init();
    }



    public void render(GameContainer gc, Graphics g) throws SlickException {

        if(!isGameFinished) {

            if (isIntroFinished) {

                if (isPlotFinished) {

                    currentLevelView.render(gc, g);
                    if (!EDIT_MODE) {
                        player.render(gc, g);
                    }

                    if (!isWin) {

                        //drawDebugLines(gc, g, LINES_SIZE);
                        //showMouseInfo(gc, g);

                        if (DEBUG_MODE) {

                            if (shadow != null && SHOW_OPOSITE) {
                                g.setColor(Color.cyan);
                                g.fill(shadow);
                            }

                            //showPosition(g);
                            if (!EDIT_MODE)
                                showPlayerInfo(g);

                            if (lastClick != null) {
                                g.setColor(Color.red);
                                g.fill(lastClick);
                            }
                        }

                        if (timeSinceLevelStarted > DISAPPOINT_TIME) {
                            //currentLevelView.drawDisappoint(g);
                        }

                    } else {
                        showWin(gc, g);
                    }
                } else {
                    plotScreen.render(gc, g);
                }
            } else {
                introScreen.render(gc, g);
            }
        } else {
            endScreen.render(gc, g);
        }

    }
    
    private void nextLevel(GameContainer gc) throws SlickException{
        if(currentLevel < levels.size()-1) {
            isPlotFinished = false;
            plotScreen = new PlotScreen(levels.get(currentLevel+1).getPlotSentence(), introFinished);
            plotScreen.init();
            setLevel(gc, currentLevel + 1);
            setPlayer(gc);
        } else {
            isGameFinished = true;
        }
        
    }


    public void update(GameContainer gc, int delta) throws SlickException {

        if(!isEndFinished) {
            if (!isGameFinished) {

                if (isIntroFinished) {

                    if (isPlotFinished) {

                        timeSinceLevelStarted += delta;
                        currentLevelView.update(gc, delta);

                        if (!EDIT_MODE) {
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

                            if (!isWin) {
                                isWin = player.isOnExit() && gc.getInput().isKeyDown(Input.KEY_SPACE);
                            } else {
                                currentLevelView.finish();
                            }

                            if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                                int x = gc.getInput().getMouseX();
                                int y = gc.getInput().getMouseY();

                                lastClick = new Circle(x, y, 10);
                            }

                            if (DEBUG_MODE) {
                                makeOposite(gc);
                            }

                        }
                    } else {
                        plotScreen.update(gc, delta);
                    }
                } else {
                    introScreen.update(gc, delta);
                }

            } else {
                endScreen.update(gc, delta);
            }
        } else {
            gc.exit();
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
    
    
    private void showPlayerInfo(Graphics g){
        if(player.isOnMirror()) {
            g.drawString("FLIP", 50, 40);
        }
        
    }
    
    private void showMouseInfo(GameContainer gc, Graphics g){
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
        g.setColor(Color.white);
        if(currentLevelView.areAllObjectsPicked()) {
            //g.drawString("WIN", 50, 40);
        } else {
            g.drawString("YOU HAVE NOT PICKED ALL OBJECTS!", 50, 40);
        }
        
    }
    
    private void makeOposite(GameContainer gc){
        
        Point mousePosition = getMousePosition(gc);
        
        int invertedX = (gc.getWidth()-mousePosition.x);
        
        shadow = new Rectangle(invertedX, (mousePosition.y-(LINES_SIZE/2)), LINES_SIZE, LINES_SIZE);
        
    }
    
    private Point getMousePosition(GameContainer gc){
        
        Input input = gc.getInput();
        
        return new Point(input.getMouseX(), input.getMouseY());
    }

}