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
    private Rectangle spaceShip;

    OrthographicCamera camera;

    private int WIDTH = 800;
    private int HEIGHT = 480;
    public GameScreen(final MainGame game) {
        this.game = game;
        spaceImage = new Texture(Gdx.files.internal("ship.png"));
        spaceShip = new Rectangle();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);

        //asset non si centra correttamente, che fare?
        spaceShip.x = 800/2 - 64/2;
        spaceShip.y = 20;

        spaceShip.width = 64;
        spaceShip.height = 64;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0.2f,1);

        camera.update();
        System.out.println(spaceShip.x);
        System.out.println(spaceShip.y);
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(spaceImage, spaceShip.x, spaceShip.y);
        game.batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                spaceShip.x -= 600 * Gdx.graphics.getDeltaTime();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                spaceShip.x += 600 * Gdx.graphics.getDeltaTime();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.UP)){
                spaceShip.y += 600 * Gdx.graphics.getDeltaTime();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                spaceShip.y -= 600 * Gdx.graphics.getDeltaTime();
            }
        }else{
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                spaceShip.x -= 200 * Gdx.graphics.getDeltaTime();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                spaceShip.x += 200 * Gdx.graphics.getDeltaTime();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.UP)){
                spaceShip.y += 200 * Gdx.graphics.getDeltaTime();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                spaceShip.y -= 200 * Gdx.graphics.getDeltaTime();
            }
        }


        if(spaceShip.x < 0) spaceShip.x = 0;
        if(spaceShip.x > WIDTH - spaceShip.width) spaceShip.x = 0;

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
}
