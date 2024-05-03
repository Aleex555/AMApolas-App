package com.losamapolas.roscodrom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import org.json.JSONException;
import org.json.JSONObject;

public class GameOver implements Screen {
    final Roscodrom game;
    Skin skin;
    Stage stage;

    BitmapFont font;
    int p;
    SpriteBatch batch;
    Label nikname;
    Label score;


    OrthographicCamera camera;
    public GameOver(final Roscodrom game,int p) {
        this.game = game;
        this.p=p;

        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        //this.nik=nik;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 550, 880);
    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("star-soldier-ui.json"));
        stage = new Stage(new FitViewport(550, 880, camera));
        TextButton main = new TextButton("<----", skin);
        batch = new SpriteBatch();
        main.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));

            }
        });
        nikname = new Label("", skin);
        nikname.setPosition(100, 600);
        score = new Label("", skin);
        score.setPosition(300, 600);


        FileHandle file = Gdx.files.local("perfil.json");


        if (file.exists()) {
            String jsonString = file.readString();
            try {
                // Convertir el contenido del archivo JSON en un objeto JSONObject
                JSONObject perfilJson = new JSONObject(jsonString);
                // Obtener los valores del JSON y colocarlos en los TextField
                if (perfilJson.has("nickname")) {
                    nikname.setText(perfilJson.getString("nickname"));
                    score.setText(String.valueOf(p));

                }

            } catch (JSONException e) {
                // Manejar la excepción si ocurre un error al analizar el JSON
                e.printStackTrace();
            }


        }

        main.setPosition(30,810);
        stage.addActor(nikname);
        stage.addActor(score);
        stage.addActor(main);
        Gdx.input.setInputProcessor(stage);


    }

    @Override
    public void render(float delta) {



        batch.setProjectionMatrix(camera.combined);


        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.font.getData().setScale(3);
        game.font.draw(game.batch, "GAME OVER", 150, 700);



        game.batch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        camera.update();











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
