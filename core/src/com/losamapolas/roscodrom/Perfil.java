package com.losamapolas.roscodrom;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Perfil extends ApplicationAdapter implements Screen {
    final Roscodrom game;
    SpriteBatch batch;
    BitmapFont font;
    Stage stage;
    TextField nickname;
    TextField email;
    TextField tel;
    Skin skin;
    OrthographicCamera camera;

    public Perfil(final Roscodrom game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 550, 880);
    }

    @Override
    public void create() {



    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(550, 880, camera));
        batch = new SpriteBatch();
        font = new BitmapFont();
        skin = new Skin(Gdx.files.internal("star-soldier-ui.json"));

        nickname = new TextField("", skin);
        TextButton main = new TextButton("Back", skin);
        TextButton enviar = new TextButton("Enviar", skin);
        main.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        enviar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        nickname.setMessageText("Ingrese texto aquí");
        nickname.setPosition(175, 600);
        nickname.setSize(200, 50);
        nickname.setColor(1,1,1,1);

        email = new TextField("", skin);
        email.setMessageText("Ingrese texto aquí");
        email.setPosition(175, 500);
        email.setSize(200, 50);
        email.setColor(1,1,1,1);

        tel = new TextField("", skin);

        tel.setMessageText("Ingrese texto aquí");
        tel.setPosition(175, 400);
        tel.setSize(200, 50);
        tel.setColor(1,1,1,1);

        //nickname.getText();
        //email.getText();
        //tel.getText();

        main.setPosition(30,810);
        enviar.setPosition(400,50);
        stage.addActor(main);
        stage.addActor(enviar);
        stage.addActor(nickname);
        stage.addActor(email);
        stage.addActor(tel);


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
