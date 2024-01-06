package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Laser {
    private Texture laserImage;
    private final Rectangle laser;

    private boolean active = false;

    private static final int LASER_SPEED = 600;

    public Laser(float posx,float posy){
        laser = new Rectangle();
        laserImage = new Texture(Gdx.files.internal("pixel_laser_red.png"));
        laser.x = posx;
        laser.y = posy;
        laser.width = 10;
        laser.height = 10;
        active = true;
    }

    public Laser(float posx, float posy, final String colour){
        laser = new Rectangle();
        laserImage = new Texture(Gdx.files.internal(colour));
        laser.x = posx;
        laser.y = posy;
        laser.width = 10;
        laser.height = 10;
        active = true;
    }

    public void update(SpriteBatch batch){
        if(active){
            laser.y += LASER_SPEED * Gdx.graphics.getDeltaTime();
            batch.draw(laserImage, laser.x, laser.y);
        }
    }

    public void enemyupdate(SpriteBatch batch){
        if(active){
            laser.y -= LASER_SPEED * Gdx.graphics.getDeltaTime();
            batch.draw(laserImage, laser.x, laser.y);
        }
    }

    public Rectangle getLaser() {
        return laser;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setLaserImage(Texture l){
        laserImage = l;
    }


    public void dispose(){
        laserImage.dispose();
    }

    public Texture getLaserImage() {
        return laserImage;
    }
}
