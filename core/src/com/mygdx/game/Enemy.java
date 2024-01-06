package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class Enemy {
    private Texture enemyTexture;
    private Rectangle enemy;

    private int WIDTH = GameScreen.getWIDTH();
    private int HEIGHT = GameScreen.getHEIGHT();
    private float posx;
    private float posy;

    private static final String BLUELASER = "pixel_laser_blue.png";

    private ArrayList<Laser> lasers;

    public Enemy(){

        enemyTexture = new Texture(Gdx.files.internal("pixel_ship_red_small.png"));
        enemy = new Rectangle();
        enemy.x = (int)(Math.random() * (WIDTH - 40 + 1)) + 40;             //bug here ---where to spawn correctly?
        enemy.y = (int)(Math.random() * (HEIGHT - 40 - 500 + 1)) + 500;     //bug here
        enemy.width = 20;
        enemy.height = 20;
        posx = enemy.x;
        posy = enemy.y;
        lasers = new ArrayList<>();

    }

    public void update(SpriteBatch batch){
        batch.draw(enemyTexture, posx, posy);
    }

    public Rectangle getEnemy() {
        return enemy;
    }

    public void setEnemyTexture(Texture enemyTexture) {
        this.enemyTexture = enemyTexture;
    }

    public boolean decideToShot() {

        //int num = (int)(Math.random()*(100) + 1);
        //if(num % 2 == 0) return true;
        return true;
    }

    public Laser shoot() {
        Laser l = new Laser(this.posx - 15, this.posy - 50, BLUELASER);
        lasers.add(l);
        return l;
    }
}
