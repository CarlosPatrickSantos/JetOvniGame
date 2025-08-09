package com.jetovni;

import java.awt.Image;
import java.awt.Graphics2D;

public class HealthPack {

    private int x, y;
    private int valorCura;
    private Image imagem;

    public HealthPack(int x, int y, int valorCura, Image imagem) {
        this.x = x;
        this.y = y;
        this.valorCura = valorCura;
        this.imagem = imagem;
    }

    public void mover() {
        // Lógica de movimento (caindo na tela, por exemplo)
    }

    public void desenhar(Graphics2D g2d) {
        g2d.drawImage(imagem, x, y, null);
    }
}