package jetovnigame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CarregadorDeAudio {

    private static Map<String, Clip> clips = new HashMap<>();
    private static Clip clipMusicaFundo; // NOVO: Variável para guardar a música de fundo

    public static void carregarClips() {
        try {
            clips.put("tiro.wav", AudioSystem.getClip());
            clips.put("explosao.wav", AudioSystem.getClip());
            clips.put("musica_fundo.wav", AudioSystem.getClip());
            
            File tiroFile = new File("resources/tiro.wav");
            File explosaoFile = new File("resources/explosao.wav");
            File musicaFundoFile = new File("resources/musica_fundo.wav");
            
            clips.get("tiro.wav").open(AudioSystem.getAudioInputStream(tiroFile));
            clips.get("explosao.wav").open(AudioSystem.getAudioInputStream(explosaoFile));
            clips.get("musica_fundo.wav").open(AudioSystem.getAudioInputStream(musicaFundoFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void tocarSom(String nomeArquivo) {
        Clip clip = clips.get(nomeArquivo);
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public static void tocarMusicaDeFundo(String nomeArquivo) {
        try {
            // CORREÇÃO: Para a música anterior antes de começar a nova
            if (clipMusicaFundo != null && clipMusicaFundo.isRunning()) {
                clipMusicaFundo.stop();
            }
            
            File file = new File("resources/" + nomeArquivo);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            clipMusicaFundo = AudioSystem.getClip();
            clipMusicaFundo.open(audioInputStream);
            clipMusicaFundo.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pararMusicaDeFundo() {
        if (clipMusicaFundo != null && clipMusicaFundo.isRunning()) {
            clipMusicaFundo.stop();
        }
    }
}