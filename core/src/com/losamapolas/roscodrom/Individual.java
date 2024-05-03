package com.losamapolas.roscodrom;

import static javax.swing.UIManager.put;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.FileHandler;

import javax.swing.JFormattedTextField;

public class Individual implements Screen {
    private static Label correct;

    // Lista para almacenar las palabras encontradas
    static List<String> palabrasEncontradas = new ArrayList<>();
    float tiempoRestante = 60;
    final Roscodrom game;
    static Skin skin;
    static Stage stage;
    Label outputLabel;
    Label score;
    Label timer;
    String filePath = "DISC2/DISC2-LP.txt";
    // Palabra a buscar
    String targetWord ;
    int punt=0;
    int vocal;

    Music aciertoSound;
    Music  errorSound;

    OrthographicCamera camera;
    private static final Map<Character, Integer> valoresLetras = new HashMap<Character, Integer>() {{
        put('a', 1); put('b', 3); put('c', 3); put('d', 2); put('e', 1);
        put('f', 4); put('g', 2); put('h', 4); put('i', 1); put('j', 8);
        put('l', 1); put('m', 3); put('n', 1); put('o', 1);
        put('p', 3); put('q', 8); put('r', 1); put('s', 1); put('t', 1);
        put('u', 1); put('v', 4);  put('x', 8); put('y', 4);
        put('z', 10);
    }};

    char[] con = {'B', 'C', 'D', 'F', 'G', 'H', 'J', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V',  'X', 'Y', 'Z'};
    char[] voc = {'A', 'E', 'I', 'O', 'U'};
    int n = 10;

    Random random = new Random();
    char[] nuevaArray = new char[n];
    Set<Character> selectedLetters = new HashSet<>();
    Set<Character> letrasPresionadas = new HashSet<>();

    public Individual(final Roscodrom game, int n ) {
        this.game = game;
        this.n = n;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 550, 880);

    }

    private void resetButtons() {
        for (Actor actor : stage.getActors()) {
            if (actor instanceof TextButton) {
                TextButton button = (TextButton) actor;
                button.setStyle(skin.get("default", TextButton.TextButtonStyle.class));
            }
        }}
    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("star-soldier-ui.json"));
        stage = new Stage(new FitViewport(550, 880, camera));
        correct = new Label("", skin);
        aciertoSound = Gdx.audio.newMusic(Gdx.files.internal("correct.mp3"));
        errorSound = Gdx.audio.newMusic(Gdx.files.internal("error.mp3"));

        timer = new Label("", skin);
        timer.setPosition(50, 700);
        stage.addActor(timer);

        correct = new Label("", skin);
        correct.setWrap(true); // Habilitar el ajuste de texto automático
        correct.setWidth(400); // Establecer el ancho máximo de la etiqueta
        correct.setAlignment(Align.topLeft); // Alinear el texto hacia arriba a la izquierda

        ScrollPane scrollPane = new ScrollPane(correct, skin);
        scrollPane.setPosition(250, 700); // Ajustar la posición según tu diseño
        scrollPane.setSize(100, 20); // Establecer las dimensiones del ScrollPane
        scrollPane.setScrollingDisabled(false, true); // Habilitar el desplazamiento vertical


        score = new Label("", skin);
        score.setPosition(350, 750);
        stage.addActor(score);

        stage.addActor(scrollPane);
        if (n >= 6 && n <= 8) {
            vocal = 2;
        } else {
            vocal = 3;
        }

        TextButton main = new TextButton("<----", skin);
        main.setPosition(30, 810);
        main.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(main);
        TextButton envia = new TextButton("Envia", skin);
        envia.setPosition(205, 130);
        envia.setSize(150, 150);
        envia.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                    targetWord= String.valueOf(outputLabel.getText());
                // Abrir el archivo y crear un BufferedReader para leerlo línea por línea

                FileHandle reader = Gdx.files.internal(filePath);
                String line;
                // Leer todas las líneas del archivo y almacenarlas en un arreglo de Strings
                String text = reader.readString();

                String[] words = text.split("\r?\n");

                Arrays.sort(words);
                int index = binarySearch(words, targetWord);

                // Comprobar si la palabra fue encontrada
                if (index != -1) {

                    // Verificar si la palabra ya está presente en la etiqueta correct
                    if (!correct.getText().toString().contains(targetWord)) {
                        // Sumar puntos
                        punt = punt + calcularPuntuacion(targetWord);
                        System.out.println(punt);

                        score.setText(String.valueOf(punt));
                        // Actualizar la etiqueta correct con la palabra encontrada
                        updateLabelWithFoundWords(targetWord);
                        aciertoSound.play();
                    } else {
                        errorSound.play();
                        System.out.println("La palabra '" + targetWord + "' ya fue encontrada anteriormente.");
                    }
                } else {
                    errorSound.play();
                    System.out.println("La palabra '" + targetWord + "' no fue encontrada.");
                }
                outputLabel.setText("");
                resetButtons();
                letrasPresionadas.clear();

                calcularPuntuacion(String.valueOf(outputLabel.getText()));

            }
        });
        stage.addActor(envia);
        for (int i = 0; i < vocal; i++) {
            char selectedVowel;
            do {
                selectedVowel = voc[random.nextInt(voc.length)];
            } while (selectedLetters.contains(selectedVowel));
            nuevaArray[i] = selectedVowel;
            selectedLetters.add(selectedVowel);
        }

        // Agregar el resto de las consonantes aleatorias
        for (int i = vocal; i < n; i++) {
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
        float centerX = 230;
        float centerY = 150;
        float radius = 150;
        float angleIncrement = 360f / n;
        float currentAngle = 0f;

        for (int i = 0; i < n; i++) {
            final char letra = nuevaArray[i];
            float x = (float) (centerX + radius * Math.cos(Math.toRadians(currentAngle)));
            float y = (float) (centerY + radius * Math.sin(Math.toRadians(currentAngle)));

            TextButton button = new TextButton(String.valueOf(letra), skin,"default");
            button.setPosition(x, y);
            button.setSize(100, 90);
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
        game.font.getData().setScale(1.5f);
        game.font.setColor(1, 1, 1, 1);
        game.batch.begin();
        game.font.draw(game.batch, "Punts:", 270, 760);

        tiempoRestante -= delta;

        timer.setText("TEMPS:  "+String.valueOf(Math.round(tiempoRestante)));


        //game.font.draw(game.batch, "Tiempo restante: " + (int) tiempoRestante + " segundos", 50, 700);

        if (tiempoRestante <= 0) {
            game.setScreen(new GameOver(game,punt));
            tiempoRestante = 0;
        }

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
        stage.dispose();
        skin.dispose();
        aciertoSound.dispose();
        errorSound.dispose();

    }
    private int calcularPuntuacion(String palabra) {
        int puntuacion = 0;
        palabra = palabra.toLowerCase(); // Convertir la palabra a minúsculas
        for (int i = 0; i < palabra.length(); i++) {
            char letra = palabra.charAt(i);
            if (valoresLetras.containsKey(letra)) {
                puntuacion += valoresLetras.get(letra);
            }
        }
        return puntuacion;
    }
    private static int binarySearch(String[] arr, String target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = target.compareTo(arr[mid]);

            // Si la palabra está en el medio del arreglo
            if (comparison == 0) {
                palabrasEncontradas.add(target);

                return mid;
            }
            // Si la palabra está en la mitad izquierda del arreglo
            else if (comparison < 0) {
                right = mid - 1;
            }
            // Si la palabra está en la mitad derecha del arreglo
            else {
                left = mid + 1;
            }
        }

        return -1;
    }
    private static void updateLabelWithFoundWords(String word) {
        // Verificar si la palabra ya está presente en la etiqueta correct
        if (!correct.getText().toString().contains(word)) {
            StringBuilder sb = new StringBuilder(correct.getText().toString()); // Obtener el texto actual de la etiqueta

            // Agregar la palabra encontrada al texto de la etiqueta
            sb.append(word).append("\n");

            // Actualizar el texto de la etiqueta correct
            correct.setText(sb.toString());
            correct.setPosition(250, 600); // Ajustar la posición según tu diseño

            // Agregar la etiqueta actualizada al stage si no está presente
            if (!stage.getActors().contains(correct, true)) {
                stage.addActor(correct);
            }
        }
    }
}
