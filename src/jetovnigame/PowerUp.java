package jetovnigame;

public class PowerUp extends Aeronave {
    
    private String nomeArquivo;

    public PowerUp(int x, int y, int velocidade, String nomeArquivo) {
        // Chama o construtor da classe-pai (Aeronave)
        super(x, y, nomeArquivo);
        this.nomeArquivo = nomeArquivo;
        
        // Define a velocidade do PowerUp.
        // vx (velocidade horizontal) é 0, pois o power-up não se move para os lados.
        this.vx = 0;
        // vy (velocidade vertical) é a velocidade passada para o construtor.
        this.vy = velocidade;
    }
    
    public String getNomeArquivo() {
        return this.nomeArquivo;
    }

    @Override
    public void perderSaude() {
        // Power-ups não perdem saúde, então este método não faz nada.
    }
}