package jetovnigame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Aeronave {

    protected int x, y;
    protected int largura, altura;
    protected int saude;
    protected int velocidade = 2;
    protected int vx = 0;
    protected int vy = 0;
    protected Image imagem; // Alterado para 'protected' para que o Jet possa acessá-lo

    // === NOVAS VARIÁVEIS PARA INVENCIBILIDADE ===
    protected boolean invencivel = false;
    protected long tempoInicioInvencibilidade;
    protected final long duracaoInvencibilidade = 5000; // 5 segundos
    // ===========================================

    public Aeronave(int x, int y, String nomeImagem) {
        this.x = x;
        this.y = y;
        this.saude = 1;
        carregarImagem(nomeImagem);
    }
    
    private void carregarImagem(String nomeImagem) {
        try {
            this.imagem = CarregadorDeImagens.carregarImagemRedimensionada(nomeImagem, 50, 50);
            this.largura = imagem.getWidth(null);
            this.altura = imagem.getHeight(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void mover() {
        this.x += vx;
        this.y += vy;
    }
    
    public void moverEsquerda() { vx = -velocidade; }
    public void moverDireita() { vx = velocidade; }
    public void moverCima() { vy = -velocidade; }
    public void moverBaixo() { vy = velocidade; }
    
    public void pararMovimentoHorizontal() { vx = 0; }
    public void pararMovimentoVertical() { vy = 0; }
    
    public Bullet atirar() {
        return new Bullet(x + largura / 2, y, "tiro.png", false);
    }
    
    public void perderSaude() {
        saude--;
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
    public int getLargura() { return largura; }
    public int getAltura() { return altura; }
    public int getSaude() { return saude; }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, largura, altura);
    }
    
    public void desenhar(Graphics2D g) {
        if (imagem != null) {
            g.drawImage(imagem, x, y, null);
        }
    }

    // === NOVOS MÉTODOS PARA INVENCIBILIDADE ===
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isInvencivel() {
        return invencivel;
    }

    public void setInvencivel(boolean invencivel) {
        this.invencivel = invencivel;
        if (invencivel) {
            this.tempoInicioInvencibilidade = System.currentTimeMillis();
        }
    }

    public long getTempoInicioInvencibilidade() {
        return tempoInicioInvencibilidade;
    }

    public long getDuracaoInvencibilidade() {
        return duracaoInvencibilidade;
    }
    // ===========================================
}