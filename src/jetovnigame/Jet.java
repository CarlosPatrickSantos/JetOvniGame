package jetovnigame;

public class Jet extends Aeronave {
    
     private boolean temEscudo = false;
    private long tempoAtivacaoEscudo = 0;
    private final long DURACAO_ESCUDO = 20000; // Duração do escudo em milissegundos (5 segundos)
    private final int MAX_SAUDE = 9;


    public Jet(int x, int y, String nomeImagem) {
        super(x, y, nomeImagem);
        this.saude = 3;
        this.velocidade = 5; // Adicione esta linha para mudar a velocidade apenas do jet
    }

    // Método para ativar o escudo
    public void ativarEscudo() {
        this.temEscudo = true;
        this.tempoAtivacaoEscudo = System.currentTimeMillis();
    }
     // Método para saber se o escudo está ativo
    public boolean temEscudo() {
        return temEscudo && (System.currentTimeMillis() - tempoAtivacaoEscudo < DURACAO_ESCUDO);
    }
    
     // Método para desativar o escudo
    public void desativarEscudo() {
        this.temEscudo = false;
    }
    
     // Método GETTER para obter o tempo de ativação
    public long getTempoAtivacaoEscudo() {
        return tempoAtivacaoEscudo;
    }
    
    // MÉTODO GETTER para obter a duração do escudo
    public long getDuracaoEscudo() {
        return DURACAO_ESCUDO;
    }
    
     // Método para aumentar a vida (coletar power-up de vida)
    public void aumentarSaude() {
        // Aumenta a saúde, mas não permite que ultrapasse um limite, por exemplo, 5
        if (saude < MAX_SAUDE) {
            saude++;
        }
    }
    
    @Override
    public Bullet atirar() {
       // Adiciona 'false' no construtor para indicar que NÃO é um tiro de inimigo
    return new Bullet(x + largura / 2, y, "tiro.png", false); 
    }
}