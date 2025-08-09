package jetovnigame;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JPanel;

public class Fundo {
    
    // Variável 'y' agora é privada
    private Image imagem;
    private int y;
    private int altura;
    private int velocidade;

    public Fundo(int y, int velocidade, String nomeImagem, JPanel panel) {
        this.y = y;
        this.velocidade = velocidade;
        
        this.imagem = CarregadorDeImagens.carregarImagem(nomeImagem);
        this.altura = imagem.getHeight(panel);
    }
    
    public void mover() {
        y += velocidade;
    }
    
    public void desenhar(Graphics2D g) {
        g.drawImage(imagem, 0, y, null);
    }
    
    // NOVO: Método para poder definir o valor de 'y' de fora da classe
    public void setY(int novoY) {
        this.y = novoY;
    }
    
    public int getY() {
        return y;
    }
    
    public int getAltura() {
        return altura;
    }
}