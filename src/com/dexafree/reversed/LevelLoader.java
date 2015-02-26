package com.dexafree.reversed;

import com.dexafree.reversed.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LevelLoader {
    
    private final String FILE = "levels.json";

    public ArrayList<Level> loadLevels(){
        
        ArrayList<Level> levelList = new ArrayList<Level>();
        
        String fileContent = getFileContent();
        
        Gson gson = new GsonBuilder().create();
        JsonArray levels = gson.fromJson(fileContent, JsonArray.class);
        for(int i=0;i<levels.size();i++){
            
            JsonObject level = levels.get(i).getAsJsonObject();
            
            String title = getTitle(level);
            String endingSentence = getEndingSentence(level);
            float[] structure = getStructure(level);
            ArrayList<Platform> platforms = getPlatforms(level);
            ArrayList<Mirror> mirrors = getMirrors(level);
            ArrayList<Platform> staticPlatforms = getStaticPlatforms(level);
            ArrayList<GameObject> objects = getObjects(level);
            Exit exit = getExit(level);
            Point start = getStart(level);
            
            Level l = new Level(title, endingSentence, structure, platforms, mirrors, staticPlatforms, objects, start, exit, i);
            levelList.add(l);
        }
        return levelList;
    }
    
    private String getFileContent(){
        try {

            final Path path;
            
            if(Game.FOR_COMPILATION) {

                URI uri = this.getClass().getClassLoader().getResource(FILE).toURI();

                final Map<String, String> env = new HashMap<String, String>();
                final String[] array = uri.toString().split("!");
                final FileSystem fs = FileSystems.newFileSystem(URI.create(array[0]), env);
                path = fs.getPath(array[1]);
            } else {
                path = Paths.get(FILE);
            }
            
            byte[] encoded = Files.readAllBytes(path);

            
            return new String(encoded, StandardCharsets.UTF_8);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    private String getStringGeneric(JsonObject level, String name){

        return level.get(name).getAsString();
        
    }
    
    private String getTitle(JsonObject level){
        return getStringGeneric(level, "title");
    }
    
    private String getEndingSentence(JsonObject level){
        return getStringGeneric(level, "end_sentence");
        
    }
    
    private float[] getStructure(JsonObject level){
        JsonArray pointArray = level.get("structure").getAsJsonArray();
        float[] points = new float[pointArray.size()];
        for(int i = 0; i < pointArray.size(); i++){
            points[i] = pointArray.get(i).getAsInt();
        }
        return points;
    }
    
    private ArrayList<Mirror> getMirrors(JsonObject level){
        ArrayList<Mirror> mirrorsList = new ArrayList<Mirror>();
        JsonArray mirrorsArray = level.get("mirrors").getAsJsonArray();
        for(int i=0;i<mirrorsArray.size();i++){

            JsonObject mirror = mirrorsArray.get(i).getAsJsonObject();

            String color = mirror.get("color").getAsString();
            int x = mirror.get("x").getAsInt();
            int y = mirror.get("y").getAsInt();


            mirrorsList.add(new Mirror(x, y, color));
        }

        return mirrorsList;
    }

    
    private ArrayList<Platform> getPlatforms(JsonObject level){
        return getPlatformsGeneric(level, "platforms");
    }
    
    private ArrayList<Platform> getStaticPlatforms(JsonObject level){
        return getPlatformsGeneric(level, "static");
    }

    private ArrayList<Platform> getPlatformsGeneric(JsonObject level, String elem){
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
    
    private ArrayList<GameObject> getObjects(JsonObject level){
        ArrayList<GameObject> objects = new ArrayList<GameObject>();
        JsonArray objectsArray = level.get("objects").getAsJsonArray();
        for(int i = 0; i < objectsArray.size();i++){
            JsonObject object = objectsArray.get(i).getAsJsonObject();
            int x = object.get("x").getAsInt();
            int y = object.get("y").getAsInt();
            String type = object.get("type").getAsString();
            String color = object.get("color").getAsString();
            objects.add(new GameObject(x, y, type, color));
        }
        
        return objects;
        
    }
    
    private Exit getExit(JsonObject level){
        JsonObject exit = level.get("exit").getAsJsonObject();
        int x = exit.get("x").getAsInt();
        int y = exit.get("y").getAsInt();
        return new Exit(x, y);
    }
    
    private Point getStart(JsonObject level){
        JsonObject exit = level.get("start").getAsJsonObject();
        int x = exit.get("x").getAsInt();
        int y = exit.get("y").getAsInt();
        return new Point(x, y);
    }
    
}
