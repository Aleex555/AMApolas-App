package com.losamapolas.roscodrom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Colisseu implements Screen {
    final Roscodrom game;
    Skin skin;
    Stage stage;

    OrthographicCamera camera;
    public Colisseu(final Roscodrom game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 550, 880);
    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("star-soldier-ui.json"));
        stage = new Stage(new FitViewport(550, 880, camera));
        TextButton main = new TextButton("Back", skin);

        main.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));

            }
        });
        main.setPosition(30,810);
        stage.addActor(main);
        Gdx.input.setInputProcessor(stage);


    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.font.getData().setScale(2);
        game.font.setColor(1, 1, 1, 1);
        game.batch.begin();
        game.font.draw(game.batch, "Colisseu", 150, 700);


        game.batch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();




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
