package com.losamapolas.roscodrom;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.WebSockets;
import java.net.URI;
import java.net.URISyntaxException;

public class InicioP implements Screen {

    private final Roscodrom game;
    private Skin skin;
    private Stage stage;
    private WebSocket socket;
    private Label messageLabel;

    public InicioP(final Roscodrom game) {
        this.game = game;
        try {
            URI serverUri = new URI("https://roscodrom6.ieti.site");
            socket = WebSockets.newSocket(String.valueOf(serverUri));
            socket.setSendGracefully(false);
            socket.addListener(new MyWebSocketListener());
            socket.connect();
            socket.send("Enviar datos al servidor");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("star-soldier-ui.json"));
        stage = new Stage(new FitViewport(550, 880));

        // Crear y agregar la etiqueta para mostrar el mensaje
        messageLabel = new Label("", skin);
        messageLabel.setAlignment(Align.center);
        messageLabel.setWidth(550); // Ancho de la etiqueta igual al ancho de la pantalla
        messageLabel.setPosition(0, 400); // Posición centrada verticalmente
        stage.addActor(messageLabel);

        // Botón para regresar al menú principal
        TextButton main = new TextButton("<----", skin);
        main.setPosition(30, 810);
        main.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(main);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        if (socket != null) {
            socket.close();
        }
    }

    private class MyWebSocketListener implements WebSocketListener {
        @Override
        public boolean onOpen(WebSocket webSocket) {
            // Manejar la conexión abierta
            return true;
        }

        @Override
        public boolean onClose(WebSocket webSocket, int closeCode, String reason) {
            // Manejar la conexión cerrada
            return true;
        }

        @Override
        public boolean onMessage(WebSocket webSocket, String packet) {
            // Manejar mensajes recibidos
            return true;
        }

        @Override
        public boolean onMessage(WebSocket webSocket, byte[] packet) {
            return false;
        }

        @Override
        public boolean onError(WebSocket webSocket, Throwable error) {
            // Manejar errores
            return true;
        }
    }
}
