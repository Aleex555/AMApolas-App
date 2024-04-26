package com.losamapolas.roscodrom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.JFormattedTextField;

public class Individual implements Screen {
    final Roscodrom game;
    Skin skin;
    Stage stage;
    Label outputLabel;

    OrthographicCamera camera;

    char[] con = {'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'};
    char[] voc = {'A', 'E', 'I', 'O', 'U'};
    int n = 7;
    Random random = new Random();
    char[] nuevaArray = new char[n];
    Set<Character> selectedLetters = new HashSet<>();


    Set<Character> letrasPresionadas = new HashSet<>();

    public Individual(final Roscodrom game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 550, 880);
    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("star-soldier-ui.json"));
        stage = new Stage(new FitViewport(550, 880, camera));

        TextButton main = new TextButton("Back", skin);
        main.setPosition(30, 810);
        main.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(main);
        TextButton envia = new TextButton("SEND", skin);
        envia.setPosition(200, 130);
        envia.setSize(150, 150);
        envia.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(envia);
        for (int i = 0; i < 2; i++) {
            char selectedVowel;
            do {
                selectedVowel = voc[random.nextInt(voc.length)];
            } while (selectedLetters.contains(selectedVowel));
            nuevaArray[i] = selectedVowel;
            selectedLetters.add(selectedVowel);
        }

        // Agregar el resto de las consonantes aleatorias
        for (int i = 2; i < n; i++) {
            char selectedConsonant;
            do {
                selectedConsonant = con[random.nextInt(con.length)];
            } while (selectedLetters.contains(selectedConsonant));
            nuevaArray[i] = selectedConsonant;
            selectedLetters.add(selectedConsonant);
        }


        // Crear etiqueta para mostrar las letras presionadas
        outputLabel = new Label("", skin);
        outputLabel.setPosition(30, 600);

        stage.addActor(outputLabel);

        // Agregar botones del abecedario
        float centerX = 275;
        float centerY = 200;
        float radius = 150;
        float angleIncrement = 360f / n;
        float currentAngle = 0f;

        for (int i = 0; i < n; i++) {
            final char letra = nuevaArray[i];
            float x = (float) (centerX + radius * Math.cos(Math.toRadians(currentAngle)));
            float y = (float) (centerY + radius * Math.sin(Math.toRadians(currentAngle)));

            TextButton button = new TextButton(String.valueOf(letra), skin,"default");
            button.setPosition(x, y);
            button.setSize(2, 2);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!letrasPresionadas.contains(letra)) {
                        button.setStyle(skin.get("pressed", TextButton.TextButtonStyle.class));
                        outputLabel.setText(outputLabel.getText().toString() + letra);
                        letrasPresionadas.add(letra);
                    } else {
                        String textoActual = outputLabel.getText().toString();
                        textoActual = textoActual.replace(String.valueOf(letra), "");
                        button.setStyle(skin.get("default", TextButton.TextButtonStyle.class));

                        outputLabel.setText(textoActual);
                        letrasPresionadas.remove(letra);
                    }
                }
            });
            stage.addActor(button);

            currentAngle += angleIncrement;
        }

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.font.getData().setScale(2);
        game.font.setColor(1, 1, 1, 1);
        game.batch.begin();
        game.font.draw(game.batch, "Individual", 150, 700);
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
