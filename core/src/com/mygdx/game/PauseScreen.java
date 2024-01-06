package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;
import jdk.tools.jmod.Main;


public class PauseScreen implements Screen {
    private final MainGame game;
    private static final int RECT_WIDTH = 1000;
    private static final int RECT_HEIGTH = 500;
    private Rectangle rectangle;

    public PauseScreen(final MainGame game) {
        this.game = game;
        rectangle = new Rectangle();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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

    }
}
