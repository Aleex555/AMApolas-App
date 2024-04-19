package com.losamapolas.roscodrom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class Multijugador implements Screen {
    final Roscodrom game;

    OrthographicCamera camera;
    public Multijugador(final Roscodrom game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 550, 880);
    }

    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.font.getData().setScale(2);
        game.font.setColor(1, 1, 1, 1);
        game.batch.begin();
        game.font.draw(game.batch, "Multijugador", 150, 700);

        game.batch.end();



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
