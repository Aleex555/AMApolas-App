package com.losamapolas.roscodrom;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenuScreen extends ApplicationAdapter implements Screen {
    final Roscodrom game;
    OrthographicCamera camera;
    Stage stage;
    Skin skin;

    public MainMenuScreen(final Roscodrom game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 550, 880); // Cambiado a 480x800 para modo vertical
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(550, 880, camera));
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("star-soldier-ui.json")); // Carga el skin para los botones

        // Crear botones
        TextButton individual = new TextButton("Individual", skin);
        TextButton multijugador = new TextButton("Multijugador", skin);
        TextButton colisseu = new TextButton("Colisseu", skin);
        TextButton opcions = new TextButton("Opcions", skin);
        TextButton perfil = new TextButton("Perfil", skin);

        // Configurar listener para cada botón
        individual.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Individual(game));
            }
        });
        multijugador.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Gdx.files.internal("assets/perfil.json").exists()){
                    game.setScreen(new Multijugador(game));
                }
                else {
                    game.setScreen(new Perfil(game));

                }

            }
        });
        colisseu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Colisseu(game));

            }
        });
        opcions.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Opcions(game));
            }
        });
        perfil.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Gdx.files.internal("perfil.json").exists()){
                    game.setScreen(new Perfilfet(game));
                    System.out.println("hola1 ");
                }else {
                    game.setScreen(new Perfil(game));
                    System.out.println("hola2 ");
                }


            }
        });


        stage.addActor(individual);
        stage.addActor(multijugador);
        stage.addActor(colisseu);
        stage.addActor(opcions);
        stage.addActor(perfil);
        // Establecer posiciones verticales para los botones
        float buttonSpacing = 40;
        float buttonWidth = 300;
        float buttonHeight = 100;
        float x = 150;
        individual.setSize(buttonWidth, buttonHeight);
        multijugador.setSize(buttonWidth, buttonHeight);
        colisseu.setSize(buttonWidth, buttonHeight);
        opcions.setSize(buttonWidth, buttonHeight);
        perfil.setSize(buttonWidth, buttonHeight);
        individual.setPosition(x, 750);
        multijugador.setPosition(x, individual.getY() - multijugador.getHeight() - buttonSpacing);
        colisseu.setPosition(x, multijugador.getY() - colisseu.getHeight() - buttonSpacing);
        opcions.setPosition(x, colisseu.getY() - opcions.getHeight() - buttonSpacing);
        perfil.setPosition(x, opcions.getY() - perfil.getHeight() - buttonSpacing);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    // Implementación de los métodos no utilizados
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}