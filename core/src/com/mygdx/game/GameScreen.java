package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen implements Screen {
    final MainGame game;
    private final Texture spaceImage;
    private final Rectangle spaceShip;

    private final int spawned = 4;

    OrthographicCamera camera;

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    final int SPEED = 500;

    private final ArrayList<Laser> lasers;

    private final ArrayList<Enemy> enemies;

    private final ArrayList<Laser> enemylasers;

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
        enemylasers = new ArrayList<>();
        for(int i = 0; i < spawned; i++){
            enemies.add(new Enemy());
        }
    }


    @Override
    public void render(float delta) {
        game.batch.begin();
        ScreenUtils.clear(0,0,0.2f,1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.draw(spaceImage, spaceShip.x, spaceShip.y);

        if(enemies.size() < spawned){
            enemies.add(new Enemy());
        }

        for(Enemy en: enemies){
            en.update(game.batch);
            if(en.decideToShot()){  //Se ho un numero paro sparo, altrimenti no.
                Laser toreturn_laser = en.shoot();         //Se ho un numero paro creo un laser interno alla classe nemico (le collisioni vengono gestite internamente alla classe)
                enemylasers.add(toreturn_laser);
                game.batch.draw(toreturn_laser.getLaserImage(), toreturn_laser.getLaser().getX(), toreturn_laser.getLaser().getY());
            }
        }

        //bullet logic
        for(Laser l: enemylasers){
            l.enemyupdate(game.batch);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            Laser l = new Laser(spaceShip.x, spaceShip.y);
            lasers.add(l);
        }

        Iterator<Laser> enlaseriterator = enemylasers.iterator();
        while(enlaseriterator.hasNext()){
            Laser l = enlaseriterator.next();
            l.update(game.batch);
            if(l.getLaser().overlaps(spaceShip)){
                System.exit(0);
            }
        }

        //collision detection
        Iterator<Laser> laserIterator = lasers.iterator();
        while(laserIterator.hasNext()){
            Laser l = laserIterator.next();
            l.update(game.batch);
            Iterator<Enemy> enemyIterator = enemies.iterator();
            while(enemyIterator.hasNext()) {
                Enemy en = enemyIterator.next();
                if(l.getLaser().overlaps(en.getEnemy())) {

                    l.setActive(false);
                    l.setLaserImage(null);
                    en.setEnemyTexture(null);

                    enemyIterator.remove();
                    laserIterator.remove(); //memory management ?
                }
            }
        }



        //GRANDE PROBLEMA DI EFFICIENZA!
        // vengono rimossi dalla lista solo i colpi che collidono con i mostri non quelli che raggiungono i limiti
        movement();

        /*if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            game.setScreen(new PauseScreen(game));
        }*/

        //bounds settings
        if(spaceShip.x < 0) spaceShip.x = 0;
        if(spaceShip.y < 0) spaceShip.y = 0;
        if(spaceShip.x >= WIDTH + spaceShip.width) spaceShip.x = WIDTH-100;         //glitch here -----bounds are a bit glitchy
        if(spaceShip.y >= HEIGHT - spaceShip.height) spaceShip.y = HEIGHT-100;      //glitch here

        game.batch.end();   //is it worth to keep having the batch open for the whole rendering process? Probably no
    }


    public void movement(){
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
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
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

    @Override
    public void show() {

    }


}
