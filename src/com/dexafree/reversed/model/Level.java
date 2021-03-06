package com.dexafree.reversed.model;

import java.util.ArrayList;

public class Level {

    private String title;
    private String endingSentence;
    private String plotSentence;
    private float[] levelStructure;
    private ArrayList<Platform> platforms;
    private ArrayList<Mirror> mirrors;
    private ArrayList<Platform> staticShapes;
    private ArrayList<GameObject> objects;
    private Point start;
    private Exit exit;
    private int numLevel;

    public Level(String title, String endingSentence, String plotSentence, float[] levelStructure, ArrayList<Platform> platforms,
                 ArrayList<Mirror> mirrors, ArrayList<Platform> staticShapes,
                 ArrayList<GameObject> objects, Point start, Exit exit, int numLevel) {
        this.title = title;
        this.endingSentence = endingSentence;
        this.plotSentence = plotSentence;
        this.levelStructure = levelStructure;
        this.platforms = platforms;
        this.mirrors = mirrors;
        this.staticShapes = staticShapes;
        this.objects = objects;
        this.start = start;
        this.exit = exit;
        this.numLevel = numLevel;
    }

    public String getTitle() {
        return title;
    }

    public String getEndingSentence() {
        return endingSentence;
    }

    public String getPlotSentence() {
        return plotSentence;
    }

    public float[] getLevelStructure() {
        return levelStructure;
    }

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }

    public ArrayList<Mirror> getMirrors() {
        return mirrors;
    }

    public ArrayList<Platform> getStaticShapes() {
        return staticShapes;
    }
    
    public ArrayList<GameObject> getObjects(){
        return objects;
    }

    public Point getStart() {
        return start;
    }

    public Exit getExit() {
        return exit;
    }

    public int getNumLevel() {
        return numLevel;
    }
}
