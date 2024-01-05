package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.LongMap;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final MainGame game;
    private Texture spaceImage;
    private Texture enemyImage;
    private Texture laserImage;

    private Rectangle spaceShip;
    private Rectangle enemy;
    private Rectangle laser;

    OrthographicCamera camera;

    private final int WIDTH = 1920;
    private final int HEIGHT = 1080;

    private final int SPEED = 800;

    //private int nenemy = 0;
    boolean active = false;
    float laserx;
    float lasery;
    public GameScreen(final MainGame game) {
        this.game = game;
        spaceImage = new Texture(Gdx.files.internal("ship.png"));
        laserImage = new Texture(Gdx.files.internal("pixel_laser_red.png"));
        enemyImage = new Texture(Gdx.files.internal("pixel_ship_red_small.png"));

        spaceShip = new Rectangle();
        enemy = new Rectangle();
        laser = new Rectangle();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);

        //asset non si centra correttamente, che fare?
        spaceShip.x = WIDTH/2 - 10;
        spaceShip.y = 20;

        enemy.x = (int)(Math.random() * (WIDTH - 20 + 1)) + 10;             //bug here ---where to spawn correctly?
        enemy.y = (int)(Math.random() * (HEIGHT - 20 - 500 + 1)) + 500;     //bug here

        laser.x = spaceShip.x;
        laser.y = spaceShip.y + 100;

        //perché non è centrato??
        spaceShip.width = 20;
        spaceShip.height = 20;

        enemy.width = 20;
        enemy.height = 20;

        laser.width = 10;
        laser.height = 20;
        laserx = spaceShip.getX();
        lasery = spaceShip.getY();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        ScreenUtils.clear(0,0,0.2f,1);

        camera.update();
        //System.out.println(spaceShip.x + " " + spaceShip.y);

        game.batch.setProjectionMatrix(camera.combined);


        game.batch.draw(spaceImage, spaceShip.x, spaceShip.y);

        game.batch.draw(enemyImage, enemy.x, enemy.y);

        game.batch.draw(enemyImage, enemy.x, enemy.y);


        //bullet logic
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            active = true;
            laserx = spaceShip.getX();
            lasery = spaceShip.getY();
        }

        if(active){
            game.batch.draw(laserImage, laserx, lasery);
            lasery += SPEED * Gdx.graphics.getDeltaTime();; //temp value, how to update y in a more "fancy" way?
        }


        //collision detection
        if(laser.getY() > HEIGHT){
            active = false;
        }

        if(laser.overlaps(enemy)){
            System.out.println("collision problem here");
            active = false;
        }


        //movement
        if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)){
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)){
                spaceShip.x -= 2*SPEED * Gdx.graphics.getDeltaTime();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)){
                spaceShip.x += 2*SPEED * Gdx.graphics.getDeltaTime();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)){
                spaceShip.y += 2*SPEED * Gdx.graphics.getDeltaTime();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)){
                spaceShip.y -= 2*SPEED * Gdx.graphics.getDeltaTime();
            }
        }else{
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)){
                spaceShip.x -= SPEED * Gdx.graphics.getDeltaTime();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)){
                spaceShip.x += SPEED * Gdx.graphics.getDeltaTime();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)){
                spaceShip.y += SPEED * Gdx.graphics.getDeltaTime();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)){
                spaceShip.y -= SPEED * Gdx.graphics.getDeltaTime();
            }
        }

        //bounds settings
        if(spaceShip.x < 0) spaceShip.x = 0;
        if(spaceShip.y < 0) spaceShip.y = 0;
        if(spaceShip.x >= WIDTH + spaceShip.width) spaceShip.x = WIDTH-100;         //glitch here -----bounds are a bit glitchy
        if(spaceShip.y >= HEIGHT - spaceShip.height) spaceShip.y = HEIGHT-100;      //glitch here


        game.batch.end();   //is it worth to keep having the batch open for the whole rendering process? Probably no
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        spaceImage.dispose();
        enemyImage.dispose();
        laserImage.dispose();
    }
}
