package jetovnigame;

import javax.swing.ImageIcon;
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

public class StartScreen extends JPanel {

    private JetOvni game;
    private Image backgroundImage;

    public StartScreen(JetOvni game) {
        this.game = game;
        
        backgroundImage = CarregadorDeImagens.carregarImagem("fundo_inicio.png");
        
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 100));
        
        JLabel titulo = new JLabel("Escolha sua nave:");
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE);
        titulo.setOpaque(false);
        add(titulo);

        // --- Botão do Jet com ícone ---
        JButton jetButton = new JButton("Jet");
        Image jetImage = CarregadorDeImagens.carregarImagem("jet.png");
        if (jetImage != null) {
            Image jetIconImage = jetImage.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            jetButton.setIcon(new ImageIcon(jetIconImage));
        }
        jetButton.setContentAreaFilled(false);
        jetButton.setBorderPainted(false);
        jetButton.setFocusPainted(false);
        jetButton.setForeground(Color.WHITE);
        jetButton.setHorizontalTextPosition(JButton.CENTER);
        jetButton.setVerticalTextPosition(JButton.BOTTOM);
        jetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.startGame("jet");
            }
        });
        add(jetButton);

        // --- Botão da Nave 2 com ícone ---
        JButton nave2Button = new JButton("Helicoptero");
        Image player_helicopterImage = CarregadorDeImagens.carregarImagem("player_helicopter.png");
        if (player_helicopterImage != null) {
            Image player_helicopterIconImage = player_helicopterImage.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            nave2Button.setIcon(new ImageIcon(player_helicopterIconImage));
        }
        nave2Button.setContentAreaFilled(false);
        nave2Button.setBorderPainted(false);
        nave2Button.setFocusPainted(false);
        nave2Button.setForeground(Color.WHITE);
        nave2Button.setHorizontalTextPosition(JButton.CENTER);
        nave2Button.setVerticalTextPosition(JButton.BOTTOM);
        nave2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.startGame("nave2");
            }
        });
        add(nave2Button);
        
        // --- Botão da Nave 3 com ícone ---
        JButton nave3Button = new JButton("Ovni");
        Image ovniImage = CarregadorDeImagens.carregarImagem("ovni.png");
        if (ovniImage != null) {
            Image ovniIconImage = ovniImage.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            nave3Button.setIcon(new ImageIcon(ovniIconImage));
        }
        nave3Button.setContentAreaFilled(false);
        nave3Button.setBorderPainted(false);
        nave3Button.setFocusPainted(false);
        nave3Button.setForeground(Color.WHITE);
        nave3Button.setHorizontalTextPosition(JButton.CENTER);
        nave3Button.setVerticalTextPosition(JButton.BOTTOM);
        nave3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.startGame("nave3");
            }
        });
        add(nave3Button);

        // --- NOVO: Botão do Oliver com ícone ---
        JButton oliverButton = new JButton("Oliver");
        Image oliverImage = CarregadorDeImagens.carregarImagem("oliver.png");
        if (oliverImage != null) {
            Image oliverIconImage = oliverImage.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            oliverButton.setIcon(new ImageIcon(oliverIconImage));
        }
        oliverButton.setContentAreaFilled(false);
        oliverButton.setBorderPainted(false);
        oliverButton.setFocusPainted(false);
        oliverButton.setForeground(Color.WHITE);
        oliverButton.setHorizontalTextPosition(JButton.CENTER);
        oliverButton.setVerticalTextPosition(JButton.BOTTOM);
        oliverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.startGame("oliver"); // Passa o nome "oliver"
            }
        });
        add(oliverButton);
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

import javax.swing.ImageIcon;
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

public class StartScreen extends JPanel {

    private JetOvni game;
    private Image backgroundImage;

    public StartScreen(JetOvni game) {
        this.game = game;
        
        backgroundImage = CarregadorDeImagens.carregarImagem("fundo_inicio.png");
        
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 100));
        
        JLabel titulo = new JLabel("Escolha sua nave:");
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE);
        titulo.setOpaque(false);
        add(titulo);

       // --- Botão do Jet com ícone ---
JButton jetButton = new JButton("Jet");

// 1. Carrega a imagem original do Jet
Image jetImage = CarregadorDeImagens.carregarImagem("jet.png");

if (jetImage != null) {
    // 2. Redimensiona a imagem para o tamanho de um ícone (64x64 pixels)
    Image jetIconImage = jetImage.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
    
    // 3. Cria o ImageIcon com a imagem redimensionada
    jetButton.setIcon(new ImageIcon(jetIconImage));
}
        jetButton.setContentAreaFilled(false); // Torna o fundo do botão transparente
        jetButton.setBorderPainted(false);     // Remove a borda
        jetButton.setFocusPainted(false);      // Remove o contorno de foco
        jetButton.setForeground(Color.WHITE);  // Cor do texto
        jetButton.setHorizontalTextPosition(JButton.CENTER); // Posiciona o texto no centro do ícone
        jetButton.setVerticalTextPosition(JButton.BOTTOM);   // Posiciona o texto abaixo do ícone
        jetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.startGame("jet");
            }
        });
        add(jetButton);

        // --- Botão da Nave 2 com ícone ---
JButton nave2Button = new JButton("Helicoptero");

// 1. Carrega a imagem original do Helicóptero
Image player_helicopterImage = CarregadorDeImagens.carregarImagem("player_helicopter.png");

if (player_helicopterImage != null) {
    // 2. Redimensiona a imagem para o tamanho de um ícone
    Image player_helicopterIconImage = player_helicopterImage.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
    
    // 3. Cria o ImageIcon com a imagem redimensionada
    nave2Button.setIcon(new ImageIcon(player_helicopterIconImage));
}

        nave2Button.setContentAreaFilled(false);
        nave2Button.setBorderPainted(false);
        nave2Button.setFocusPainted(false);
        nave2Button.setForeground(Color.WHITE);
        nave2Button.setHorizontalTextPosition(JButton.CENTER);
        nave2Button.setVerticalTextPosition(JButton.BOTTOM);
        nave2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.startGame("nave2");
            }
        });
        add(nave2Button);
        
      // --- Botão da Nave 3 com ícone ---
JButton nave3Button = new JButton("Ovni");

// 1. Carrega a imagem original do Ovni
Image ovniImage = CarregadorDeImagens.carregarImagem("ovni.png"); 

if (ovniImage != null) {
    // 2. Redimensiona a imagem para o tamanho de um ícone (ex: 64x64 pixels)
    // Se quiser outro tamanho, mude os valores '64, 64'
    Image ovniIconImage = ovniImage.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
    
    // 3. Cria o ImageIcon com a imagem redimensionada
    nave3Button.setIcon(new ImageIcon(ovniIconImage));
}
        nave3Button.setContentAreaFilled(false);
        nave3Button.setBorderPainted(false);
        nave3Button.setFocusPainted(false);
        nave3Button.setForeground(Color.WHITE);
        nave3Button.setHorizontalTextPosition(JButton.CENTER);
        nave3Button.setVerticalTextPosition(JButton.BOTTOM);
        nave3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.startGame("nave3");
            }
        });
        add(nave3Button);
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

public class StartScreen extends JPanel {

    private JetOvni game;
    private Image backgroundImage; // Variável para a imagem de fundo

    public StartScreen(JetOvni game) {
        this.game = game;
        
        // Carrega a imagem de fundo
        backgroundImage = CarregadorDeImagens.carregarImagem("fundo_inicio.png");
        
        // Ajusta o layout e o alinhamento
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 100));
        
        // Cria os componentes (botões e texto)
        JLabel titulo = new JLabel("Escolha sua nave:");
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE); // Cor do texto para ser visível
        titulo.setOpaque(false); // Torna o label transparente
        add(titulo);

        JButton jetButton = new JButton("Jet");
        jetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.startGame("jet");
            }
        });
        add(jetButton);

        JButton nave2Button = new JButton("Helicopter");
        nave2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.startGame("nave2");
            }
        });
        add(nave2Button);
        
        JButton nave3Button = new JButton("UFO");
        nave3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.startGame("nave3");
            }
        });
        add(nave3Button);
    }
    
    // Sobrescreve o método paintComponent para desenhar o fundo
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Desenha a imagem de fundo preenchendo o painel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
*/