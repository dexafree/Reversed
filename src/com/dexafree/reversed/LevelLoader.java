package com.dexafree.reversed;

import com.dexafree.reversed.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LevelLoader {
    
    private final static String FILE = "levels.json";
    
    public static ArrayList<Level> loadLevels(){
        
        ArrayList<Level> levelList = new ArrayList<Level>();
        
        String fileContent = getFileContent();
        
        Gson gson = new GsonBuilder().create();
        JsonArray levels = gson.fromJson(fileContent, JsonArray.class);
        for(int i=0;i<levels.size();i++){
            
            JsonObject level = levels.get(i).getAsJsonObject();
            
            String title = getTitle(level);
            float[] structure = getStructure(level);
            ArrayList<Platform> platforms = getPlatforms(level);
            ArrayList<Mirror> mirrors = getMirrors(level);
            ArrayList<Platform> staticPlatforms = getStaticPlatforms(level);
            Exit exit = getExit(level);
            Point start = getStart(level);
            
            Level l = new Level(title, structure, platforms, mirrors, staticPlatforms, start, exit);
            levelList.add(l);
        }
        return levelList;
    }
    
    private static String getFileContent(){
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(FILE));
            return new String(encoded, StandardCharsets.UTF_8);
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    
    private static String getTitle(JsonObject level){
        return level.get("title").getAsString();
    }
    
    private static float[] getStructure(JsonObject level){
        JsonArray pointArray = level.get("structure").getAsJsonArray();
        float[] points = new float[pointArray.size()];
        for(int i = 0; i < pointArray.size(); i++){
            points[i] = pointArray.get(i).getAsInt();
        }
        return points;
    }
    
    private static ArrayList<Mirror> getMirrors(JsonObject level){
        ArrayList<Mirror> mirrorsList = new ArrayList<Mirror>();
        JsonArray mirrorsArray = level.get("mirrors").getAsJsonArray();
        for(int i=0;i<mirrorsArray.size();i++){
            
            JsonArray mirror = mirrorsArray.get(i).getAsJsonArray();
            
            int[] points = new int[]{
                mirror.get(0).getAsInt(),
                mirror.get(1).getAsInt()
            };
            
            mirrorsList.add(new Mirror(points[0], points[1]));
        }
        
        return mirrorsList;
    }
    
    private static ArrayList<Platform> getPlatforms(JsonObject level){
        return getPlatformsGeneric(level, "platforms");
    }
    
    private static ArrayList<Platform> getStaticPlatforms(JsonObject level){
        return getPlatformsGeneric(level, "static");
    }

    private static ArrayList<Platform> getPlatformsGeneric(JsonObject level, String elem){
        ArrayList<Platform> platforms = new ArrayList<Platform>();
        JsonArray platformsArray = level.get(elem).getAsJsonArray();
        for(int i=0;i<platformsArray.size();i++){
            JsonArray platform = platformsArray.get(i).getAsJsonArray();

            int x = platform.get(0).getAsInt();
            int y = platform.get(1).getAsInt();
            int width = platform.get(2).getAsInt();
            int height = platform.get(3).getAsInt();

            platforms.add(new Platform(x, y, width, height));
        }

        return platforms;
    }
    
    private static Exit getExit(JsonObject level){
        JsonObject exit = level.get("exit").getAsJsonObject();
        int x = exit.get("x").getAsInt();
        int y = exit.get("y").getAsInt();
        return new Exit(x, y);
    }
    
    private static Point getStart(JsonObject level){
        JsonObject exit = level.get("start").getAsJsonObject();
        int x = exit.get("x").getAsInt();
        int y = exit.get("y").getAsInt();
        return new Point(x, y);
    }
    
}
