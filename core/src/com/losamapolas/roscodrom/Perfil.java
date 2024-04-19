package com.losamapolas.roscodrom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.ScreenUtils;

public class Perfil implements Screen {
    final Roscodrom game;

    OrthographicCamera camera;
    public Perfil(final Roscodrom game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 550, 880);    }

    @Override
    public void show() {

        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style.font = new BitmapFont();
        style.fontColor = Color.CHARTREUSE;
        game.font.draw(game.batch, "Perfil", 150, 700);
        TextField field = new TextField("", style);
        field.setText("Test");
        field.setWidth(150);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.font.getData().setScale(2);
        game.font.setColor(1, 1, 1, 1);
        game.batch.begin();




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
