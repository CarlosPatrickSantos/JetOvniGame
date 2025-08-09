package jetovnigame;

public class OvniAtirador extends Obstaculo { // CORREÇÃO: Estende Obstaculo

    private final long DELAY_TIRO = 2000;
    private long ultimoTempoTiro = 0;

    public OvniAtirador(int x, int y, int velocidade) {
        // CORREÇÃO: Chama o construtor da classe pai (Obstaculo)
        super(x, y, velocidade, "ovni_atirador.png");
    }

    public boolean podeAtirar() {
        if (System.currentTimeMillis() - ultimoTempoTiro > DELAY_TIRO) {
            ultimoTempoTiro = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public Bullet atirar() {
        // Adiciona 'true' no construtor para indicar que É um tiro de inimigo
    return new Bullet(x + largura / 2, y + altura, "tiro_inimigo.png", true);
    }
}