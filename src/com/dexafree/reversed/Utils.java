package com.dexafree.reversed;

import org.newdawn.slick.Color;

public class Utils {

    public static Color getColor(String objectColor){

        if(objectColor.equalsIgnoreCase("black")){
            return Color.black;
        }
        else if(objectColor.equalsIgnoreCase("blue")){
            return Color.blue;
        }
        else if(objectColor.equalsIgnoreCase("cyan")){
            return Color.cyan;
        }
        else if(objectColor.equalsIgnoreCase("darkGray")){
            return Color.darkGray;
        }
        else if(objectColor.equalsIgnoreCase("gray")){
            return Color.gray;
        }
        else if(objectColor.equalsIgnoreCase("green")){
            return Color.green;
        }
        else if(objectColor.equalsIgnoreCase("lightGray")){
            return Color.lightGray;
        }
        else if(objectColor.equalsIgnoreCase("magenta")){
            return Color.magenta;
        }
        else if(objectColor.equalsIgnoreCase("orange")){
            return Color.orange;
        }
        else if(objectColor.equalsIgnoreCase("pink")){
            return Color.pink;
        }
        else if(objectColor.equalsIgnoreCase("red")){
            return Color.red;
        }
        else if(objectColor.equalsIgnoreCase("white")){
            return Color.white;
        }
        else if(objectColor.equalsIgnoreCase("yellow")){
            return Color.yellow;
        }else {
            return Color.green;
        }
    }
    
}
