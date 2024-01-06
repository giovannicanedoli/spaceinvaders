package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Enemy {
    private Texture enemyTexture;
    private Rectangle enemy;

    private int WIDTH = GameScreen.getWIDTH();
    private int HEIGHT = GameScreen.getHEIGHT();
    private float posx;
    private float posy;

    public Enemy(){

        enemyTexture = new Texture(Gdx.files.internal("pixel_ship_red_small.png"));
        enemy = new Rectangle();
        enemy.x = (int)(Math.random() * (WIDTH - 20 + 1)) + 10;             //bug here ---where to spawn correctly?
        enemy.y = (int)(Math.random() * (HEIGHT - 20 - 500 + 1)) + 500;     //bug here
        enemy.width = 20;
        enemy.height = 20;
        posx = enemy.x;
        posy = enemy.y;

    }

    public void update(SpriteBatch batch){
        batch.draw(enemyTexture, posx, posy);
    }

    public Rectangle getEnemy() {
        return enemy;
    }
}
