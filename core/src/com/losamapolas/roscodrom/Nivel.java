package com.losamapolas.roscodrom;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

public class Nivel extends ApplicationAdapter implements Screen {

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

    public Nivel(final Roscodrom game) {
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
        TextButton main = new TextButton("<----", skin);

        main.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });


        nickname.setMessageText("Ingrese texto aquí");
        nickname.setPosition(175, 600);
        nickname.setSize(200, 50);
        nickname.setColor(1,1,1,1);







        TextButton enviar = new TextButton("Començar", skin);


        enviar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                try {

                    int numero = Integer.parseInt(nickname.getText());
                    if (numero>=6 && numero<=10 && !nickname.getText().isEmpty()){
                        game.setScreen(new Individual(game,Integer.parseInt(nickname.getText())));
                    }

                } catch (NumberFormatException e) {
                    // Maneja la excepción si la cadena no es un número válido
                    System.out.println("La cadena no es un número válido");
                } catch (NullPointerException e) {
                    // Maneja la excepción si la cadena es nula
                    System.out.println("La cadena es nula");
                }

            }
        });


        main.setPosition(30,810);
        enviar.setPosition(380,50);




        stage.addActor(main);
        stage.addActor(enviar);
        stage.addActor(nickname);



        Gdx.input.setInputProcessor(stage);
    }
    private static byte[] readImageBytes(String imagePath) throws IOException {
        File file = new File(imagePath);
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buf)) != -1) {
            bos.write(buf, 0, bytesRead);
        }
        fis.close();
        return bos.toByteArray();
    }

    // Método para codificar los bytes de la imagen en Base64
    @SuppressWarnings("NewApi")
    private static String encodeImageToBase64(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }


    @Override
    public void render(float delta) {

        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        game.font.getData().setScale(1.5f);
        game.font.setColor(1, 1, 1, 1);
        game.font.draw(game.batch, "Nivell: ", 80, 630);

        game.font.getData().setScale(1.5f);
        game.font.setColor(1, 1, 1, 1);
        game.font.draw(game.batch, "Numero de lletres per jugar (6-10)", 80, 580);

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

