package jetovnigame;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CarregadorDeImagens {

    public static Image carregarImagem(String nomeArquivo) {
        try {
            return ImageIO.read(new File("resources/" + nomeArquivo));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Image carregarImagemRedimensionada(String nomeArquivo, int largura, int altura) {
        try {
            Image imagemOriginal = ImageIO.read(new File("resources/" + nomeArquivo));
            if (imagemOriginal != null) {
                return imagemOriginal.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}