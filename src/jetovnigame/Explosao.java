package jetovnigame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Explosao {

    private int x, y;
    private List<Image> frames;
    private int frameAtual;
    private int largura, altura;
    private boolean animacaoConcluida;

    // Construtor alterado para receber as imagens já carregadas
    public Explosao(int x, int y, int largura, int altura, List<Image> framesCarregados) {
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
        this.frameAtual = 0;
        this.animacaoConcluida = false;
        
        // Atribui a lista de frames que já foi carregada
        this.frames = framesCarregados;
    }
    
    public void desenhar(Graphics2D g2d) {
        if (!animacaoConcluida && frames.get(frameAtual) != null) {
            g2d.drawImage(frames.get(frameAtual), x, y, null);
        }
    }
    
    public void atualizar() {
        if (!animacaoConcluida) {
            frameAtual++;
            if (frameAtual >= frames.size()) {
                animacaoConcluida = true;
                System.out.println("Animação da explosão concluída.");
            }
        }
    }

    public boolean isAnimacaoConcluida() {
        return animacaoConcluida;
    }
}