package com.jetovni;

import java.awt.Image;
import java.awt.Graphics2D;

public class Resgate {

    private int x, y;
    private Image imagem;
    
    public Resgate(int x, int y, Image imagem) {
        this.x = x;
        this.y = y;
        this.imagem = imagem;
    }
    
    public void mover() {
        // Lógica de movimento (caindo na tela)
    }
    
    public void desenhar(Graphics2D g2d) {
        g2d.drawImage(imagem, x, y, null);
    }
}