package com.dexafree.reversed;

import com.dexafree.reversed.model.LevelView;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Player {

    private final static float GRAVITY = 0.5f;
    private final static float JUMP_STRENGTH = -15;
    private final static float SPEED = 10;

    private final static int interations = 5;
    
    private boolean inverted = false;
    private boolean firstTime = true;


    private Shape player;
    private LevelView level;


    private float vX = 0;
    private float vY = 0;

    public Player(LevelView level) {
        this.level = level;
    }

    public void init() throws SlickException {
        int startingX = level.getLevel().getStart().x;
        int startingY = level.getLevel().getStart().y;
        
        player = new Rectangle(startingX, startingY ,40,40);
    }


    public void render(GameContainer gc, Graphics g) throws SlickException {
        g.setColor( Color.red );
        g.fill(player);
    }


    public void update(GameContainer gc, int delta) throws SlickException {
        
        // Y acceleration
        vY += GRAVITY;
        
        if(gc.getInput().isKeyDown(Input.KEY_UP)) {
            player.setY(player.getY()+0.5f );
            
            if(level.collidesWith(player)) {
                //System.out.print("jump");
                vY = JUMP_STRENGTH;
            }

            player.setY( player.getY()-0.5f );
        }
        
        
        // Y Movement-Collisions
        float vYtemp = vY/interations;
        
        if(firstTime){
            firstTime = false;
            vYtemp = vY/(interations*20);
        }
        
        for(int i = 0; i < interations ; i++) {
            player.setY( player.getY() + vYtemp );
            if(level.collidesWith(player)) {
                player.setY( player.getY() - vYtemp );
                vY = 0;
            }
        }

        // X acceleration
        if(gc.getInput().isKeyDown(Input.KEY_LEFT)) {
            //vX = -SPEED;
        } else if (gc.getInput().isKeyDown(Input.KEY_RIGHT)) {
            vX = SPEED;
        } else {
            vX = 0;
        }

        // X Movement-Collisions
        float vXtemp = vX/interations;
        for(int i = 0; i < interations ; i++) {
            player.setX( player.getX() + vXtemp );
            
            if( level.collidesWith(player)) {
                player.setX( player.getX() - vXtemp );
                vX = 0;
            }
        }

        
    }

    public void invert(GameContainer gc){

        float gameWidth = gc.getWidth();


        float[] points = player.getPoints();
        for(int j=0;j<points.length;j+=2){
            points[j] = (gameWidth - points[j]);
        }


        player = new Polygon(points);


    }
    
    public boolean isOnMirror(){
        return level.isOnMirror(player);
    }
    
    public float getY(){
        return player.getY();
    }
    
    public float getX(){
        return player.getX();
    }
    
    public boolean isOnExit(){
        return level.isOnExit(player);
    }
    
    
}