package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class GameScreen implements Screen {
    final MainGame game;
    private final Texture spaceImage;
    private final Rectangle spaceShip;

    private int spawned = 10;

    OrthographicCamera camera;

    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;

    final int SPEED = 800;

    private final ArrayList<Laser> lasers;

    private final ArrayList<Enemy> enemies;

    public GameScreen(final MainGame game) {
        this.game = game;
        spaceImage = new Texture(Gdx.files.internal("ship.png"));


        spaceShip = new Rectangle();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);

        //asset non si centra correttamente, che fare?
        spaceShip.x = (float) WIDTH/2 - 10;
        spaceShip.y = 20;



        //perché non è centrato??
        spaceShip.width = 20;
        spaceShip.height = 20;

        lasers = new ArrayList<>();
        enemies = new ArrayList<>();
        for(int i = 0; i < spawned; i++){
            enemies.add(new Enemy());
        }
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

        Enemy enemy = new Enemy();
        enemy.update(game.batch);

        //bullet logic
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            Laser l = new Laser(spaceShip.x, spaceShip.y);
            lasers.add(l);
        }


        //molto buggato, però sembra funzionare
        for(Laser l: lasers){
            l.update(game.batch);
            if(l.getLaser().overlaps(enemy.getEnemy())){
                l.setActive(false);
                l.setLaserImage(null);
            }
        }


        //collision detection



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
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }
}
