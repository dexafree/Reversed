package com.dexafree.reversed.model;

import com.dexafree.reversed.components.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

import java.util.ArrayList;

public class LevelView {

    private Level level;
    private Polygon levelStructure;
    private ArrayList<PlatformView> platforms;
    private ArrayList<MirrorView> mirrors;
    private ArrayList<Shape> staticShapes;
    private ExitView exit;

    public LevelView(Level level){
        this.level = level;
    }


    public void init(GameContainer gc) throws SlickException{
        generateStructure();
        generatePlatforms();
        generateMirrors();
        generateStaticShapes();
        generateExit();
    }

    public void render(GameContainer gc, Graphics g) throws SlickException{
        renderLevel(g);
        renderPlatforms(g);
        renderMirrors(g);
        renderStaticShapes(g);

        exit.render(g);
    }

    public void update(GameContainer gc, int delta) throws SlickException {


    }

    private void generateStructure(){
        float[] points = level.getLevelStructure();
        Polygon p = new Polygon(points);
        p.setClosed(false);
        levelStructure = p;
    }

    private void generatePlatforms(){
        platforms = new ArrayList<PlatformView>();

        ArrayList<Platform> platformsModel = level.getPlatforms();
        for(Platform p : platformsModel){

            platforms.add(new PlatformView(p));
        }
    }

    private void generateMirrors(){

        mirrors = new ArrayList<MirrorView>();

        ArrayList<Mirror> mirrorsModel = level.getMirrors();
        for(Mirror m : mirrorsModel){
            mirrors.add(new MirrorView(m));
        }
    }

    private void generateStaticShapes(){
        staticShapes = new ArrayList<Shape>();

        ArrayList<Platform> staticShapesModel = level.getStaticShapes();
        for(Platform p : staticShapesModel){
            int x = p.getX();
            int y = p.getY();
            int width = p.getWidth();
            int height = p.getHeight();

            staticShapes.add(BaseView.getPlatform(x, y, width, height));
        }
    }

    protected void generateExit(){
        exit = new ExitView(level.getExit());
    }


    public boolean collidesWith(Shape s){
        // Roof control
        if(s.getY() < 0)
            return true;

        // Level limits control
        if(levelStructure.intersects(s))
            return true;

        for(PlatformView shape : platforms) {
            if (shape.collidesOrContains(s)) {
                return true;
            }
        }

        for(Shape shape : staticShapes) {
            if (shape.contains(s) || shape.intersects(s)) {
                return true;
            }
        }

        return false;
    }


    public boolean isOnMirror(Shape s){
        for(MirrorView m : mirrors){
            if(m.collidesOrContains(s) && !m.isUsed()) {
                m.use();
                return true;
            }
        }

        return false;

    }

    public void invert(GameContainer gc){

        float gameWidth = gc.getWidth();

        invertLevel(gameWidth);
        invertPlatforms(gameWidth);
        invertMirrors(gameWidth);
        invertExit(gameWidth);

    }

    protected void invertLevel(float gameWidth){

        float points[] = levelStructure.getPoints();
        points = rotatePoints(points, gameWidth);
        levelStructure = new Polygon(points);
    }

    protected void invertPlatforms(float gameWidth){
        for(PlatformView p : platforms){
            p.invert(gameWidth);
        }
    }

    protected void invertMirrors(float gameWidth){
        for(MirrorView m : mirrors){
            m.invert(gameWidth);
        }

    }


    protected void invertExit(float gameWidth){
        exit.invert(gameWidth);
    }

    protected float[] rotatePoints(float[] points, float gameWidth){

        float[] pPoints = points;

        for(int i=0;i<points.length;i+=2){
            pPoints[i] = (gameWidth - pPoints[i]);
        }

        return pPoints;
    }

    public boolean isOnExit(Shape s){
        return exit.collidesOrContains(s);
    }

    private void renderLevel(Graphics g){
        g.setBackground(Color.green);
        g.setColor(Color.black);
        g.fill(levelStructure);
    }

    private void renderPlatforms(Graphics g){
        for(PlatformView p : platforms){
            p.render(g);
        }
    }

    private void renderMirrors(Graphics g){
        for(MirrorView s : mirrors){
            s.render(g);
        }
    }

    private void renderStaticShapes(Graphics g){
        g.setColor(Color.orange);
        for(Shape s : staticShapes){
            g.fill(s);
        }
    }
    
    public Level getLevel(){
        return level;
    }

}
