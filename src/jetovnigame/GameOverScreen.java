package jetovnigame;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverScreen extends JPanel {

    private JetOvni game;
    private Image backgroundImage;
    private String tipoNave;

    public GameOverScreen(JetOvni game, int score, String tipoNave) {
        this.game = game;
        this.tipoNave = tipoNave;
        
        backgroundImage = CarregadorDeImagens.carregarImagem("fundo_gameover.png");
        
        // NOVO: Usa BoxLayout para organizar os componentes na vertical
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // Adiciona um espaço no topo para centralizar o conteúdo verticalmente
        add(Box.createVerticalStrut(100));

        JLabel titulo = new JLabel("Game Over!");
        titulo.setFont(new Font("Arial", Font.BOLD, 40));
        titulo.setForeground(Color.RED);
        titulo.setOpaque(false);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza o título
        add(titulo);
        
        add(Box.createVerticalStrut(20)); // Espaço entre o título e a pontuação

        JLabel scoreLabel = new JLabel("Pontuação Final: " + score);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setOpaque(false);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza a pontuação
        add(scoreLabel);

        add(Box.createVerticalStrut(50)); // Espaço entre a pontuação e os botões

        JButton menuButton = new JButton("Menu Principal");
        menuButton.setFont(new Font("Arial", Font.BOLD, 20));
        menuButton.setForeground(Color.WHITE);
        menuButton.setContentAreaFilled(false);
        menuButton.setBorderPainted(false);
        menuButton.setFocusPainted(false);
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza o botão
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.showStartScreen();
            }
        });
        add(menuButton);
        
        add(Box.createVerticalStrut(20)); // Espaço entre os botões

        JButton restartButton = new JButton("Reiniciar");
        restartButton.setFont(new Font("Arial", Font.BOLD, 20));
        restartButton.setForeground(Color.WHITE);
        restartButton.setContentAreaFilled(false);
        restartButton.setBorderPainted(false);
        restartButton.setFocusPainted(false);
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza o botão
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.startGame(GameOverScreen.this.tipoNave);
            }
        });
        add(restartButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}




/*
package jetovnigame;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverScreen extends JPanel {

    private JetOvni game;
    private Image backgroundImage;
    private String tipoNave; // NOVO: Variável para armazenar o tipo da nave

    public GameOverScreen(JetOvni game, int score, String tipoNave) {
        this.game = game;
        this.tipoNave = tipoNave; // Armazena o tipo da nave recebido
        
        backgroundImage = CarregadorDeImagens.carregarImagem("fundo_gameover.png");
        
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 100));
        
        JLabel titulo = new JLabel("Game Over!");
        titulo.setFont(new Font("Arial", Font.BOLD, 40));
        titulo.setForeground(Color.RED);
        titulo.setOpaque(false);
        add(titulo);

        JLabel scoreLabel = new JLabel("Pontuação Final: " + score);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setOpaque(false);
        add(scoreLabel);

         
       JButton menuButton = new JButton("Menu Principal");
        // Ajustes de aparência para o botão Menu Principal
        menuButton.setFont(new Font("Arial", Font.BOLD, 20));
        menuButton.setForeground(Color.WHITE);
        menuButton.setContentAreaFilled(false);
        menuButton.setBorderPainted(false);
        menuButton.setFocusPainted(false);
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.showStartScreen();
            }
        });
        add(menuButton);
    }
    
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

/*
package jetovnigame;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverScreen extends JPanel {

    private JetOvni game;
    private Image backgroundImage; // NOVO: Variável para a imagem de fundo

    public GameOverScreen(JetOvni game, int score) {
        this.game = game;
        
        // NOVO: Carrega a imagem de fundo
        // SUBSTITUA 'fundo_gameover.png' PELO NOME DO SEU ARQUIVO
        backgroundImage = CarregadorDeImagens.carregarImagem("fundo_gameover.png");
        
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 100));
        
        JLabel titulo = new JLabel("Game Over!");
        titulo.setFont(new Font("Arial", Font.BOLD, 40));
        titulo.setForeground(Color.RED); // Cor vermelha para o título
        titulo.setOpaque(false); // Torna o label transparente
        add(titulo);

        JLabel scoreLabel = new JLabel("Pontuação Final: " + score);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        scoreLabel.setForeground(Color.WHITE); // Cor branca para a pontuação
        scoreLabel.setOpaque(false); // Torna o label transparente
        add(scoreLabel);

        JButton restartButton = new JButton("Reiniciar");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.restartGame();
            }
        });
        add(restartButton);

        JButton menuButton = new JButton("Menu Principal");
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.showStartScreen();
            }
        });
        add(menuButton);
    }

    // NOVO: Sobrescreve o método paintComponent para desenhar o fundo
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Desenha a imagem de fundo preenchendo o painel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}









/*
package jetovnigame;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverScreen extends JPanel {

    private JetOvni game;

    public GameOverScreen(JetOvni game, int score) {
        this.game = game;
        setLayout(new FlowLayout());
        
        JLabel titulo = new JLabel("GAME OVER");
        titulo.setFont(new Font("Arial", Font.BOLD, 50));
        add(titulo);
        
        JLabel scoreLabel = new JLabel("Sua pontuação: " + score);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        add(scoreLabel);

        JButton reiniciarButton = new JButton("Reiniciar Jogo");
        reiniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.showStartScreen(); // Chama o método para voltar para a tela inicial
            }
        });
        add(reiniciarButton);
    }
}
*/