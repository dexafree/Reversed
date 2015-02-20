package com.dexafree.reversed.model;

import com.dexafree.reversed.components.*;

import java.util.ArrayList;

public class Level {

    private String title;
    private float[] levelStructure;
    private ArrayList<Platform> platforms;
    private ArrayList<Mirror> mirrors;
    private ArrayList<Platform> staticShapes;
    private Point start;
    private Exit exit;

    public Level(String title, float[] levelStructure, ArrayList<Platform> platforms, ArrayList<Mirror> mirrors, ArrayList<Platform> staticShapes, Point start, Exit exit) {
        this.title = title;
        this.levelStructure = levelStructure;
        this.platforms = platforms;
        this.mirrors = mirrors;
        this.staticShapes = staticShapes;
        this.start = start;
        this.exit = exit;
    }

    public String getTitle() {
        return title;
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

    public Point getStart() {
        return start;
    }

    public Exit getExit() {
        return exit;
    }
}
