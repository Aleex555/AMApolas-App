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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
    String api;

    public Perfilfet(final Roscodrom game) {
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
        TextButton main = new TextButton("Enrrere", skin);

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




        String[] nombresImagenes = {"Imagen 1", "Imagen 2", "Imagen 3", "Imagen 4"};
        SelectBox<String> selectBox = new SelectBox<>(skin);
        selectBox.setItems(nombresImagenes);
        selectBox.setPosition(50, 200);
        selectBox.setSize(200, 50);
        selectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Obtener el índice de la imagen seleccionada
                int indiceSeleccionado = selectBox.getSelectedIndex();
                // Cargar la imagen correspondiente
                Texture imagen = new Texture(Gdx.files.internal((indiceSeleccionado + 1) + ".png"));
                // Crear un nuevo Actor Image para mostrar la imagen
                Image imageActor = new Image(imagen);
                // Posicionar el Actor Image en la escena
                imageActor.setPosition(300, 200); // Ajusta las coordenadas según sea necesario
                imageActor.setSize(100,100);
                // Asegúrate de eliminar el Actor Image anterior si ya existe
                Actor actorToRemove = stage.getRoot().findActor("imageActor");
                if (actorToRemove != null) {
                    actorToRemove.remove();
                    imageActor.setName("imageActor");
                }
                byte[] imageBytes = Gdx.files.internal((indiceSeleccionado + 1) + ".png").readBytes();
//
//                    String base64Image = encodeImageToBase64(imageBytes);
                avatar=(indiceSeleccionado + 1) + ".png";
                // Agregar el Actor Image al Stage
                stage.addActor(imageActor);
            }
        });
        stage.addActor(selectBox);



        FileHandle file = Gdx.files.local("perfil.json");



            String jsonString = file.readString();


        try {
            // Convertir el contenido del archivo JSON en un objeto JSONObject
            JSONObject perfilJson = new JSONObject(jsonString);


            // Obtener los valores del JSON y colocarlos en los TextField
            if (perfilJson.has("nickname")) {
                System.out.println("JSON creado: " + perfilJson.getString("nickname"));
                nickname.setText(perfilJson.getString("nickname"));
            }
            if (perfilJson.has("email")) {
                email.setText(perfilJson.getString("email"));
            }
            if (perfilJson.has("phone_number")) {
                tel.setText(perfilJson.getString("phone_number"));
            }
            if (perfilJson.has("api_key")) {
                api=perfilJson.getString("api_key");
            }
            if (perfilJson.has("avatar")) {
                Texture imagen = new Texture(Gdx.files.internal((perfilJson.getString("avatar"))));
                Image imageActor = new Image(imagen);
                // Posicionar el Actor Image en la escena
                imageActor.setPosition(300, 200); // Ajusta las coordenadas según sea necesario
                imageActor.setSize(100,100);
                avatar=perfilJson.getString("avatar");
                stage.addActor(imageActor);

            }

            // Si tienes un campo para el avatar, también puedes cargar la imagen correspondiente aquí

        } catch (JSONException e) {
            // Manejar la excepción si ocurre un error al analizar el JSON
            e.printStackTrace();
        }



        TextButton enviar = new TextButton("Cambiar", skin);


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
                        String[] historial = new String[0];
                        json.put("nickname", nick);
                        json.put("email", em);
                        json.put("phone_number", tl);
                        json.put("avatar", avatar);
                        json.put("historial_partides",historial );
                        json.put("api_key",api);





                            String apiUrl = "https://roscodrom6.ieti.site/api/user/update";
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {



                                    Gdx.app.postRunnable(new Runnable() {
                                        @Override
                                        public void run() {
                                            NetworkUtils networkUtils = new NetworkUtils();
                                            try {
                                                networkUtils.post(apiUrl, json.toString());
                                            } catch (Exception e) {
                                                throw new RuntimeException(e);
                                            }

                                        }
                                    });

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();



                        } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    String jsonString = json.toString();

                    FileHandle file = Gdx.files.local("perfil.json");

                    file.writeString(jsonString, false);
                    System.out.println("JSON creado: " + jsonString);
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



    }

