package jetovnigame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bullet {

    private int x, y;
    private int velocidade = 5;
    private Image imagem;
    private int largura, altura;
    private boolean isEnemyBullet;

    public Bullet(int x, int y, String nomeImagem, boolean isEnemyBullet) {
    this.x = x;
    this.y = y;
    this.isEnemyBullet = isEnemyBullet;
    try {
        // Redimensiona o tiro para 5x10 pixels (ajuste se preferir outro tamanho)
        this.imagem = CarregadorDeImagens.carregarImagemRedimensionada(nomeImagem, 5, 10);
        if (this.imagem != null) {
            this.largura = this.imagem.getWidth(null);
            this.altura = this.imagem.getHeight(null);
        }
    } catch (Exception e) {
        e.printStackTrace();
        this.imagem = null;
    }
}

    public void mover() {
        if (isEnemyBullet) {
            y += velocidade; // Tiro do inimigo desce
        } else {
            y -= velocidade; // Tiro do jogador sobe
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    
    public int getLargura() { return largura; }
    public int getAltura() { return altura; }

    public Rectangle getBounds() {
        return new Rectangle(x, y, largura, altura);
    }

    public void desenhar(Graphics2D g) {
        if (imagem != null) {
            g.drawImage(imagem, x, y, null);
        }
    }
}