package com.dexafree.reversed;

import com.dexafree.reversed.model.Level;
import com.dexafree.reversed.model.LevelView;
import com.dexafree.reversed.model.Point;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;

import java.util.ArrayList;

public class LevelEditMode extends LevelView {

    private ArrayList<Point> pointList;
    private Polygon structure;
    private final static int MIN_ACTION_TIME = 500;
    private int elapsedTime;
    
    public LevelEditMode(Level level){
        super(level);
    }
    
    @Override
    public void init(GameContainer gc) throws SlickException {
        pointList = new ArrayList<Point>();
        structure = new Polygon(new float[]{0, 0});
        elapsedTime = 0;
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException{
        g.setColor(Color.green);
        g.draw(structure);
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        Input input = gc.getInput();
        
        elapsedTime += delta;
        
        if(elapsedTime > MIN_ACTION_TIME) {
            
            boolean hasMadeAction = false;
            
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {

                hasMadeAction = true;
                
                System.out.println("ENTERED");


                int x = input.getMouseX();
                int y = input.getMouseY();

                pointList.add(new Point(x, y));
                structure.addPoint(x, y);
            }

            if (input.isKeyDown(Input.KEY_R)) {
                hasMadeAction = true;
                deleteLastPoint();
            }

            if (input.isKeyDown(Input.KEY_L)) {
                
                hasMadeAction = true;
                logPoints();
            }
            
            if(hasMadeAction)
                elapsedTime = 0;
        }

    }
    
    private void deleteLastPoint(){
        
        if(pointList.size() >= 4 ) {
            pointList.remove(pointList.size() - 1);

            float[] points = new float[pointList.size() * 2];

            int i = 0;
            for(Point p : pointList){
                points[i++] = p.x;
                points[i++] = p.y;
            }

            structure = new Polygon(points);
        }
    }
    
    private void logPoints(){
        for(Point p : pointList){
            System.out.println(p.x+","+p.y+",");
        }
    }
    
}
