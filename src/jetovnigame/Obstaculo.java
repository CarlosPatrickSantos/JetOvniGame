package jetovnigame;

public class Obstaculo extends Aeronave {
    
    // Construtor
    public Obstaculo(int x, int y, int velocidade, String nomeArquivo) {
        super(x, y, nomeArquivo); // Chama o construtor da classe pai (Aeronave)
        this.velocidade = velocidade;
    }
    
    // Método de movimento específico do obstáculo
    @Override
    public void mover() {
        y += velocidade;
    }
    
    // Os métodos getLargura() e getAltura() já são herdados da classe Aeronave
    // e não precisam ser declarados aqui novamente.
}