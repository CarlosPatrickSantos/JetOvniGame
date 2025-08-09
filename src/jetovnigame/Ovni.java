package com.jetovni;

import jetovnigame.Aeronave;

public class Ovni extends Aeronave {
    public Ovni(int x, int y) {
        super(x, y, "ovni.png");
        this.saude = 3;
        this.velocidade = 12;
    }
}