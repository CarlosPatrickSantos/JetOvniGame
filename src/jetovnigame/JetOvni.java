
package jetovnigame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;

public class JetOvni extends JFrame {

    public static final int LARGURA = 800;
    public static final int ALTURA = 600;

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private StartScreen startScreen;
    private GamePanel gamePanel;
    private GameOverScreen gameOverScreen;

    public JetOvni() {
        setTitle("Jet Ovni Game");
        setSize(LARGURA, ALTURA);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Inicializa o gerenciador de layouts CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Cria e adiciona as telas
        startScreen = new StartScreen(this);
        mainPanel.add(startScreen, "Start");
        
        // A tela de GamePanel e GameOverScreen serão criadas depois
        // quando o jogador escolher a nave.
        
        add(mainPanel);
        setVisible(true);
    }
    
    // Método para iniciar o jogo com uma nave específica
    public void startGame(String tipoNave) {
        // Remove painel de jogo antigo se existir
        if (gamePanel != null) {
            mainPanel.remove(gamePanel);
        }
        gamePanel = new GamePanel(this, tipoNave);
        mainPanel.add(gamePanel, "Game");
        cardLayout.show(mainPanel, "Game");
        gamePanel.requestFocusInWindow();
    }
    
    // Método para mostrar a tela de game over
// O método agora recebe o tipo de nave também
public void showGameOver(int score, String tipoNave) {
    if (gameOverScreen != null) {
        mainPanel.remove(gameOverScreen);
    }
    
    // A nova tela de Game Over é criada com o tipo da nave
    gameOverScreen = new GameOverScreen(this, score, tipoNave);
    mainPanel.add(gameOverScreen, "GameOver");
    cardLayout.show(mainPanel, "GameOver");
}
    // Método para voltar à tela de início
    public void showStartScreen() {
        cardLayout.show(mainPanel, "Start");
    }
    // Método para reiniciar o jogo, retornando à tela de seleção de nave
public void restartGame() {
    // Para a música de fundo que está tocando
    CarregadorDeAudio.pararMusicaDeFundo();
    
    // Volta para a tela inicial
    showStartScreen();
}

    public static void main(String[] args) {
        new JetOvni();
    }
}








/*
package jetovnigame;

import jetovnigame.GamePanel;
import javax.swing.JFrame;
import java.awt.Dimension;

public class JetOvni extends JFrame {

    public static final int LARGURA = 800;
    public static final int ALTURA = 600;

    public JetOvni() {
        setTitle("Jet Ovni");
        setSize(new Dimension(LARGURA, ALTURA));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
           
        // As linhas abaixo foram adicionadas para criar e exibir o GamePanel
        GamePanel gamePanel = new GamePanel();
        add(gamePanel);
        pack(); // Ajusta o tamanho da janela para o GamePanel
        
        setVisible(true);
    
        
       
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new JetOvni();
        });
    }
}
*/