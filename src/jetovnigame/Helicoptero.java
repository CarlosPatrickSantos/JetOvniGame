package com.jetovni;

import jetovnigame.Aeronave;

public class Helicoptero extends Aeronave {
    public Helicoptero(int x, int y) {
        super(x, y, "player_helicopter.png");
        this.saude = 8;
        this.velocidade = 5;
    }
}