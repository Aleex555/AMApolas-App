package com.losamapolas.roscodrom;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

public class Perfilfet extends ApplicationAdapter implements Screen {

    final Roscodrom game;
    SpriteBatch batch;
    BitmapFont font;
    Stage stage;
    TextField nickname;
    TextField email;
    TextField tel;
    Skin skin;
    OrthographicCamera camera;

    String nick;
    String em;
    String tl;
    String avatar;

    public Perfilfet(final Roscodrom game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 550, 880);
    }

    @Override
    public void create() {
        FileHandle fileHandle = Gdx.files.internal("perfil.json");

        try {

            String jsonString = fileHandle.readString("UTF-8");


            // Analizar el JSON
            JSONObject jsonObject = new JSONObject(jsonString);

            // Obtener los datos del JSON
            nick = jsonObject.getString("nickname");
            em = jsonObject.getString("email");
            tl = jsonObject.getString("phone_number");
            avatar = jsonObject.getString("avatar");
            System.out.println(nick);
            System.out.println(em);
            System.out.println(tl);
            System.out.println(avatar);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(550, 880, camera));
        batch = new SpriteBatch();
        font = new BitmapFont();
        skin = new Skin(Gdx.files.internal("star-soldier-ui.json"));

        nickname = new TextField("", skin);
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

        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.font.getData().setScale(3);
        game.font.draw(game.batch, "Perfil", 200, 800);
        game.font.getData().setScale(1.5f);
        game.font.setColor(1, 1, 1, 1);
        game.font.draw(game.batch, "Nickname:", 80, 680);
        game.font.draw(game.batch, "Email:", 80, 580);
        game.font.draw(game.batch, "Telefon:", 80, 480);
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
        batch.dispose();
        font.dispose();
        skin.dispose();
        stage.dispose();
    }



    }

