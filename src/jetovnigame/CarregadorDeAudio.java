package jetovnigame;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

public class CarregadorDeAudio {

    private static Clip clipMusicaFundo;
    private static String musicaAtual = "";

    // Método para tocar um efeito sonoro
    public static void tocarEfeitoSonoro(String nomeArquivo) {
        try {
            Clip clip = AudioSystem.getClip();
            InputStream audioStream = CarregadorDeAudio.class.getResourceAsStream("/resources/" + nomeArquivo);
            
            if (audioStream == null) {
                System.err.println("Arquivo de som não encontrado: " + nomeArquivo);
                return;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(audioStream));
            clip.open(audioInputStream);

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
            
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void tocarMusicaDeFundo(String nomeArquivo) {
        if (nomeArquivo.equals(musicaAtual)) {
            return;
        }

        try {
            pararMusicaDeFundo();

            InputStream audioStream = CarregadorDeAudio.class.getResourceAsStream("/resources/" + nomeArquivo);
            if (audioStream == null) {
                throw new FileNotFoundException("Arquivo não encontrado: " + nomeArquivo);
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(audioStream));
            clipMusicaFundo = AudioSystem.getClip();
            clipMusicaFundo.open(audioInputStream);
            clipMusicaFundo.loop(Clip.LOOP_CONTINUOUSLY);
            musicaAtual = nomeArquivo;
        } catch (Exception e) {
            System.err.println("Erro ao carregar ou tocar a música de fundo: " + nomeArquivo);
            e.printStackTrace();
        }
    }

    public static void pararMusicaDeFundo() {
        if (clipMusicaFundo != null && clipMusicaFundo.isRunning()) {
            clipMusicaFundo.stop();
            clipMusicaFundo.close();
        }
    }
}