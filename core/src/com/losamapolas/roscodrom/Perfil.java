package com.losamapolas.roscodrom;
import org.json.JSONException;
import org.json.JSONObject;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

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

    String nick;
    String em;
    String tl;
    String avatar;

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




        for (int i = 1; i <= 4; i++) {
            final int imageIndex = i;
            Texture buttonImage = new Texture(Gdx.files.internal(i+".png"));
            TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
            buttonStyle.up = new TextureRegionDrawable(new TextureRegion(buttonImage));
            buttonStyle.font = font;
            TextButton a1 = new TextButton("", buttonStyle);
            a1.setSize(100, 100);
            a1.setPosition(0 +(i*100),200);

            a1.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {


                    String imagePath =imageIndex+ ".png";

                    byte[] imageBytes = Gdx.files.internal(imagePath).readBytes();

                    String base64Image = encodeImageToBase64(imageBytes);
                    avatar=base64Image;
                    //System.out.println(base64Image);

                }
            });

            stage.addActor(a1);
        }



        TextButton enviar = new TextButton("Enviar", skin);


        enviar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                nick=nickname!= null ? nickname.getText() : "";;
                em = email!= null ? email.getText() : "";
                tl= tel!= null ? tel.getText() : "";
                avatar = avatar != null ? avatar : "";



                if(!nick.isEmpty()  || !em.isEmpty() || !tl.isEmpty()|| !avatar.isEmpty()){
                    JSONObject json = new JSONObject();
                    try {
                        json.put("nick", nick);
                        json.put("em", em);
                        json.put("tl", tl);
                        json.put("avatar", avatar);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                    // Convertir el objeto JSON a String
                    String jsonString = json.toString();

                    // Haz lo que necesites con la cadena JSON
                    System.out.println("JSON creado: " + jsonString);
                    try (FileWriter file = new FileWriter("assets/perfil.json")) {
                        file.write(jsonString);
                        System.out.println("JSON guardado en perfil.json");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                else {
                    System.out.println("Faltan Datos");
                }

                game.setScreen(new MainMenuScreen(game));

            }
        });


        main.setPosition(30,810);
        enviar.setPosition(400,50);




        stage.addActor(main);
        stage.addActor(enviar);
        stage.addActor(nickname);
        stage.addActor(email);
        stage.addActor(tel);


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

        // Lee los bytes de un archivo

    }

