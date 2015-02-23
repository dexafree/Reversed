package com.dexafree.reversed.model;

import com.dexafree.reversed.components.*;

import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

import java.awt.Font;
import java.util.ArrayList;

public class LevelView {
    
    public interface OnLevelFinished {
        
        void run() throws SlickException;
        
    }
    
    private final static int FINISH_WAIT_TIME = 2000;

    private Level level;
    private Polygon levelStructure;
    private ArrayList<PlatformView> platforms;
    private ArrayList<MirrorView> mirrors;
    private ArrayList<Shape> staticShapes;
    private ArrayList<ObjectView> objects;
    private ExitView exit;
    private TrueTypeFont levelFont;
    private OnLevelFinished callback;
    private boolean isFinished;
    private int finishedCount;
    private static org.newdawn.slick.Font originalFont;

    public LevelView(Level level, OnLevelFinished callback){
        this.level = level;
        this.callback = callback;
    }


    public void init(GameContainer gc) throws SlickException{
        isFinished = false;
        finishedCount = 0;
        
        generateStructure();
        generatePlatforms();
        generateMirrors();
        generateStaticShapes();
        generateObjects();
        generateExit();
        levelFont = generateFont();
    }

    public void render(GameContainer gc, Graphics g) throws SlickException{
        
        if(originalFont == null){
            originalFont = g.getFont();
        }
        
        renderLevel(g);
        renderPlatforms(g);
        renderMirrors(g);
        renderStaticShapes(g);
        renderObjects(g);

        exit.render(g);
        
        renderEnd(g);
    }

    public void update(GameContainer gc, int delta) throws SlickException {
        
        if(isFinished) {
            finishedCount += delta;

            if (finishedCount > FINISH_WAIT_TIME) {
                callback.run();
            }
        }

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

    private void generateExit(){
        exit = new ExitView(level.getExit());
    }
    
    private void generateObjects(){
        
        objects = new ArrayList<ObjectView>();
        for(GameObject object : level.getObjects()){
            
            ObjectView view = new ObjectView(object);
            view.init();
            
            objects.add(view);
        }
        
    }
    
    private TrueTypeFont generateFont(){

        Font font = new Font("Times New Roman", Font.BOLD, 30);
        return new TrueTypeFont(font, false);
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
            if(m.collidesOrContains(s) && m.canUse()) {
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
        invertObjects(gameWidth);
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
    
    private void invertObjects(float gameWidth){
        for(ObjectView o : objects){
            o.invert(gameWidth);
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
    
    public boolean areAllObjectsPicked(){
        for(ObjectView o : objects){
            if(!o.isPicked()){
                return false;
            }
        }
        return true;
        
    }
    
    public ObjectView isOnObject(Shape s){
        
        for(ObjectView view : objects){
            if(view.collides(s)){
                return view;
            }
        }
        
        return null;
        
    }

    private void renderLevel(Graphics g){
        g.setBackground(Color.green);
        g.setColor(Color.black);
        g.fill(levelStructure);
        
        g.getFont();
        
        
        // Level title
        g.setColor(Color.white);

        levelFont.drawString(12, 16, level.getTitle());
        
        //g.setFont(originalFont);
        
        
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

    
    private void renderObjects(Graphics g){
        for(ObjectView v : objects){
            v.render(g);
        }
        
    }
    
    private void renderEnd(Graphics g){
        if(isFinished){

            g.setColor(new Color(0, 0, 0, 0.5f));
            g.fillRect(0, 0, 1000, 768);
            
            g.setColor(Color.white);
            originalFont.drawString(400, 300, level.getEndingSentence());
        }
        
    }
    
    public void updateMirrors(GameObject object){
        for(MirrorView m : mirrors){
            m.unlock(object);
        }
        
    }
    
    public Level getLevel(){
        return level;
    }
    
    public void finish(){
        isFinished = true;
        
    }

}
