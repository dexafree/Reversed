package com.dexafree.reversed;

import com.dexafree.reversed.components.ObjectView;
import com.dexafree.reversed.model.GameObject;
import com.dexafree.reversed.model.LevelView;
import com.dexafree.reversed.model.Mirror;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import java.util.ArrayList;

public class Player {

    private final static float GRAVITY = 0.5f;
    private final static float JUMP_STRENGTH = -15;
    private final static float SPEED = 8;

    private final static int interations = 5;
    
    private boolean inverted = false;
    private boolean firstTime = true;


    private Shape player;
    private LevelView level;
    private ArrayList<GameObject> inventory;
    
    private Image playerImage;


    private float vX = 0;
    private float vY = 0;

    public Player(LevelView level, AssetManager manager) {
        
        this.playerImage = manager.getPlayerImage();
        this.level = level;
    }

    public void init() throws SlickException {
        int startingX = level.getLevel().getStart().x;
        int startingY = level.getLevel().getStart().y;
        
        inventory = new ArrayList<GameObject>();
        //player = new Rectangle(startingX, startingY ,40,40);
        
        player = new Rectangle(startingX, startingY ,playerImage.getWidth(), playerImage.getHeight()-1);
        
        float[] points = player.getPoints();
        
        points[0] = (points[0] + 10);
        points[2] = (points[2] - 8);
        points[4] = (points[4] - 8);
        points[6] = (points[6] + 10);

        player = new Polygon(points);
    }


    public void render(GameContainer gc, Graphics g) throws SlickException {
        /*g.setColor(Color.red );
        g.fill(player);*/
        playerImage.draw(player.getX()-8, player.getY());
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
        
        checkObjects();

        
    }
    
    private void checkObjects(){
        
        ObjectView object = level.isOnObject(player);
        
        if(object != null){
            inventory.add(object.getObject());
            object.pick();
            level.updateMirrors(object.getObject());
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