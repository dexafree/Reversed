package com.dexafree.reversed;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {

    public final static int WIDTH = 1000;
    public final static int HEIGHT = 768;

    public static void main(String[] args) {
        //System.setProperty("org.lwjgl.librarypath", new File( new File( System.getProperty("user.dir") , "native") , LWJGLUtil.getPlatformName() ).getAbsolutePath() );
        //System.setProperty("org.lwjgl.librarypath", new File("~/Home/SDKs/lwjgl/natives/macosx").getAbsolutePath());


        try {
            AppGameContainer app = new AppGameContainer(new Game());
            app.setDisplayMode(WIDTH, HEIGHT, false);
            app.setShowFPS(true);
            app.setVSync(true);
            //app.setTargetFrameRate(60);
            app.start();

        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

}
