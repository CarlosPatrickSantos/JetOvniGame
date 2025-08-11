
package jetovnigame;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    private final JetOvni game; // REFERÊNCIA À CLASSE PRINCIPAL
    private final String tipoNave;  // NOVO: Armazena o tipo de nave
    private final int LARGURA = JetOvni.LARGURA;
    private final int ALTURA = JetOvni.ALTURA;
    
    // NOVO: Defina aqui o limite inferior da tela (ajuste este valor se precisar)
    private final int LIMITE_INFERIOR = 550; 

    private Timer gameLoop;
    private Random random;

    private final String[] nomesDeObstaculos = {
        "meteoro.png", "ovni.png", "asteroide1.png", "asteroide2.png", "asteroide3.png", "asteroide4.png"
    };

    private final String[] nomesDePowerUps = {
        "powerup_vida.png", "powerup_escudo.png"
    };

    private Aeronave jogador;
    private List<Bullet> tiros;
    private List<Bullet> tirosInimigos;
    private List<Obstaculo> obstaculos;
    private List<Explosao> explosoes;

    private List<Image> framesExplosao;

    private List<PowerUp> powerUps;

    private Fundo fundo1;
    private Fundo fundo2;
    private final String NOME_IMAGEM_FUNDO = "fundo_espaco.png";

    private long ultimoTempoExplosao = 0;
    private final long DELAY_SOM_EXPLOSAO = 50;

    // A flag isGameOver não é mais necessária, pois a classe principal gerencia as telas
    // private boolean isGameOver = false;
    private int score = 0;
    private int dificuldade = 1;

    // CONSTRUTOR CORRIGIDO: Agora recebe a referência do jogo e o tipo de nave
    public GamePanel(JetOvni game, String tipoNave) {
        this.game = game;
        this.tipoNave = tipoNave;
        setPreferredSize(new Dimension(LARGURA, ALTURA));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new TecladoListener());

        random = new Random();
        
        inicializarObjetos();

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    private void inicializarObjetos() {
        // CORRIGIDO: Usa o tipoNave para carregar a imagem correta
        String nomeImagemNave = "jet.png";
        if (tipoNave.equals("nave2")) {
            nomeImagemNave = "player_helicopter.png";
        } else if (tipoNave.equals("nave3")) {
            nomeImagemNave = "ovni.png";
        } else if (tipoNave.equals("oliver")) { // NOVO: Adicione este bloco para a nave "oliver"
            nomeImagemNave = "oliver.png";
    }
        
        jogador = new Jet(LARGURA / 2, ALTURA - 100, nomeImagemNave);
        
        tiros = new ArrayList<>();
        tirosInimigos = new ArrayList<>();
        obstaculos = new ArrayList<>();
        explosoes = new ArrayList<>();
        powerUps = new ArrayList<>();

        score = 0;
        dificuldade = 1;

     
        CarregadorDeAudio.tocarMusicaDeFundo("musica_fundo.wav");

        fundo1 = new Fundo(0, 1, NOME_IMAGEM_FUNDO, this);
        fundo2 = new Fundo(-ALTURA, 1, NOME_IMAGEM_FUNDO, this);

        framesExplosao = new ArrayList<>();
        Obstaculo obstaculoTemp = new Obstaculo(0, 0, 0, "ovni.png");
        int larguraExplosao = obstaculoTemp.getLargura();
        int alturaExplosao = obstaculoTemp.getAltura();

        for (int i = 1; i <= 4; i++) {
            Image frame = CarregadorDeImagens.carregarImagem("explosao_" + i + ".png");
            if (frame != null) {
                frame = frame.getScaledInstance(larguraExplosao, alturaExplosao, Image.SCALE_SMOOTH);
                framesExplosao.add(frame);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        fundo1.desenhar(g2d);
        fundo2.desenhar(g2d);

        if (jogador != null) {
            jogador.desenhar(g2d);

            if (jogador instanceof Jet && ((Jet) jogador).temEscudo()) {
                Image escudo = CarregadorDeImagens.carregarImagem("powerup_escudo.png");
                if (escudo != null) {
                    g2d.drawImage(escudo, jogador.x, jogador.y, jogador.largura, jogador.altura, this);
                }
            }
        }

        for (Bullet tiro : tiros) {
            tiro.desenhar(g2d);
        }

        for (Bullet tiro : tirosInimigos) {
            tiro.desenhar(g2d);
        }

        for (Obstaculo obstaculo : obstaculos) {
            obstaculo.desenhar(g2d);
        }

        for (Explosao explosao : explosoes) {
            explosao.desenhar(g2d);
        }

        for (PowerUp powerUp : powerUps) {
            powerUp.desenhar(g2d);
        }

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("Pontuação: " + score, 10, 25);
        g2d.drawString("Vidas: " + jogador.getSaude(), 10, 50);
        g2d.drawString("Nível: " + dificuldade, 10, 75);
        
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Condição de Game Over agora está no final do updateGame()
        updateGame();
        repaint();
    }

    // === MÉTODO updateGame() ===
private void updateGame() {
    jogador.mover();
    fundo1.mover();
    fundo2.mover();

    if (fundo1.getY() >= ALTURA) {
        fundo1.setY(-fundo1.getAltura());
    }
    if (fundo2.getY() >= ALTURA) {
        fundo2.setY(-fundo2.getAltura());
    }

    // === LÓGICA DO ESCUDO E INVENCIBILIDADE ===
    if (jogador instanceof Jet) {
        Jet jetPlayer = (Jet) jogador;
        if (jetPlayer.temEscudo() && (System.currentTimeMillis() - jetPlayer.getTempoAtivacaoEscudo() > jetPlayer.getDuracaoEscudo())) {
            jetPlayer.desativarEscudo();
        }
    }
    if (jogador.isInvencivel() && (System.currentTimeMillis() - jogador.getTempoInicioInvencibilidade() > jogador.getDuracaoInvencibilidade())) {
        jogador.setInvencivel(false);
    }
    // ========================================

    for (Explosao explosao : explosoes) {
        explosao.atualizar();
    }
    explosoes.removeIf(Explosao::isAnimacaoConcluida);

    for (Bullet tiro : tiros) {
        tiro.mover();
    }
    tiros.removeIf(tiro -> tiro.getY() < 0);

    for (Bullet tiro : tirosInimigos) {
        tiro.mover();
    }
    tirosInimigos.removeIf(tiro -> tiro.getY() > ALTURA);

    for (Obstaculo obstaculo : obstaculos) {
        if (obstaculo instanceof OvniAtirador) {
            OvniAtirador ovni = (OvniAtirador) obstaculo;
            if (ovni.podeAtirar()) {
                tirosInimigos.add(ovni.atirar());
            }
        }
    }

    for (Obstaculo obstaculo : obstaculos) {
        obstaculo.mover();
    }
    obstaculos.removeIf(obstaculo -> obstaculo.getY() > ALTURA);

    for (PowerUp powerUp : powerUps) {
        powerUp.mover();
    }
    powerUps.removeIf(powerUp -> powerUp.getY() > ALTURA);

    if (random.nextInt(200) < 1) {
        int xPos = random.nextInt(LARGURA - 40);
        int velocidade = 1;
        String nomeImagem = nomesDePowerUps[random.nextInt(nomesDePowerUps.length)];
        powerUps.add(new PowerUp(xPos, 0, velocidade, nomeImagem));
    }

    if (random.nextInt(100) < 5 + dificuldade) {
        int xPos = random.nextInt(LARGURA - 40);
        int velocidade = random.nextInt(dificuldade) + 2;

        if (random.nextBoolean() && dificuldade > 1) {
            obstaculos.add(new OvniAtirador(xPos, 0, velocidade));
        } else {
            String nomeImagem = nomesDeObstaculos[random.nextInt(nomesDeObstaculos.length)];
            obstaculos.add(new Obstaculo(xPos, 0, velocidade, nomeImagem));
        }
    }

    List<Bullet> tirosParaRemover = new ArrayList<>();
    List<Obstaculo> obstaculosParaRemover = new ArrayList<>();
    for (Bullet tiro : tiros) {
        for (Obstaculo obstaculo : obstaculos) {
            if (tiro.getBounds().intersects(obstaculo.getBounds())) {
                tirosParaRemover.add(tiro);
                obstaculosParaRemover.add(obstaculo);
                explosoes.add(new Explosao(obstaculo.getX(), obstaculo.getY(), obstaculo.getLargura(), obstaculo.getAltura(), framesExplosao));
                tocarSomExplosao();
                score++;
            }
        }
    }
    tiros.removeAll(tirosParaRemover);
    obstaculos.removeAll(obstaculosParaRemover);

    List<Bullet> tirosInimigosRemover = new ArrayList<>();
    for (Bullet tiroInimigo : tirosInimigos) {
        if (jogador.getBounds().intersects(tiroInimigo.getBounds())) {
            // === LÓGICA DE COLISÃO COM TIRO INIMIGO ===
            if (!jogador.isInvencivel()) {
                if (jogador instanceof Jet && !((Jet) jogador).temEscudo()) {
                    jogador.perderSaude();
                    explosoes.add(new Explosao(jogador.getX(), jogador.getY(), jogador.getLargura(), jogador.getAltura(), framesExplosao));
                    tocarSomExplosao();
                    if (jogador.getSaude() > 0) {
                        reiniciarPosicaoJogador();
                    }
                }
            }
            // ==========================================
            tirosInimigosRemover.add(tiroInimigo);
            explosoes.add(new Explosao(tiroInimigo.getX(), tiroInimigo.getY(), tiroInimigo.getLargura(), tiroInimigo.getAltura(), framesExplosao));
        }
    }
    tirosInimigos.removeAll(tirosInimigosRemover);

    List<Obstaculo> obstaculosAtingidos = new ArrayList<>();
    for (Obstaculo obstaculo : obstaculos) {
        if (jogador.getBounds().intersects(obstaculo.getBounds())) {
            // === LÓGICA DE COLISÃO COM OBSTÁCULO ===
            if (!jogador.isInvencivel()) {
                if (jogador instanceof Jet && ((Jet) jogador).temEscudo()) {
                    obstaculosAtingidos.add(obstaculo);
                    explosoes.add(new Explosao(obstaculo.getX(), obstaculo.getY(), obstaculo.getLargura(), obstaculo.getAltura(), framesExplosao));
                    tocarSomExplosao();
                } else {
                    jogador.perderSaude();
                    obstaculosAtingidos.add(obstaculo);
                    explosoes.add(new Explosao(jogador.getX(), jogador.getY(), jogador.getLargura(), jogador.getAltura(), framesExplosao));
                    tocarSomExplosao();
                    if (jogador.getSaude() > 0) {
                        reiniciarPosicaoJogador();
                    }
                }
            } else { // Se o jogador está invencível, ele explode o obstáculo e não perde vida
                obstaculosAtingidos.add(obstaculo);
                explosoes.add(new Explosao(obstaculo.getX(), obstaculo.getY(), obstaculo.getLargura(), obstaculo.getAltura(), framesExplosao));
                tocarSomExplosao();
            }
        }
    }
    obstaculos.removeAll(obstaculosAtingidos);

    List<PowerUp> powerUpsColetados = new ArrayList<>();
    for (PowerUp powerUp : powerUps) {
        if (jogador.getBounds().intersects(powerUp.getBounds())) {
            powerUpsColetados.add(powerUp);
            if (powerUp.getNomeArquivo().equals("powerup_vida.png")) {
                ((Jet) jogador).aumentarSaude();
            } else if (powerUp.getNomeArquivo().equals("powerup_escudo.png")) {
                ((Jet) jogador).ativarEscudo();
            }
        }
    }
    powerUps.removeAll(powerUpsColetados);

    if (score > 0 && score / 10 >= dificuldade) {
        dificuldade++;
    }

    if (jogador.getSaude() <= 0) {
        gameLoop.stop();
        game.showGameOver(score, this.tipoNave);
    }

    // Limites de tela
    if (jogador.getX() < 0) {
        jogador.x = 0;
    }
    if (jogador.getX() > LARGURA - jogador.largura) {
        jogador.x = LARGURA - jogador.largura;
    }
    if (jogador.getY() < 0) {
        jogador.y = 0;
    }
    if (jogador.getY() > LIMITE_INFERIOR - jogador.altura) {
        jogador.y = LIMITE_INFERIOR - jogador.altura;
    }
}
// ===============================================

// === MÉTODOS ADICIONAIS NECESSÁRIOS ===
private void reiniciarPosicaoJogador() {
    jogador.setX(LARGURA / 2 - jogador.getLargura() / 2);
    jogador.setY(ALTURA - 100);
    jogador.setInvencivel(true);
}

private void tocarSomExplosao() {
    long tempoAtual = System.currentTimeMillis();
    if (tempoAtual - ultimoTempoExplosao > DELAY_SOM_EXPLOSAO) {
        if (random.nextInt(16) == 0) {
            CarregadorDeAudio.tocarEfeitoSonoro("wilhelm.wav");
        } else {
            CarregadorDeAudio.tocarEfeitoSonoro("explosao.wav");
        }
        ultimoTempoExplosao = tempoAtual;
    }
}

   

    private class TecladoListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT) {
                jogador.moverEsquerda();
            }
            if (key == KeyEvent.VK_RIGHT) {
                jogador.moverDireita();
            }
            if (key == KeyEvent.VK_UP) {
                jogador.moverCima();
            }
            if (key == KeyEvent.VK_DOWN) {
                jogador.moverBaixo();
            }

            if (key == KeyEvent.VK_SPACE) {
                Bullet novoTiro = jogador.atirar();
                if (novoTiro != null) {
                    tiros.add(novoTiro);
                    CarregadorDeAudio.tocarEfeitoSonoro("tiro.wav");
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
                jogador.pararMovimentoHorizontal();
            }
            if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
                jogador.pararMovimentoVertical();
            }
        }
    }
}



/*

package jetovnigame;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// CORREÇÃO: Import necessário para a classe CarregadorDeImagens
import java.awt.Image;

public class GamePanel extends JPanel implements ActionListener {

    private final int LARGURA = JetOvni.LARGURA;
    private final int ALTURA = JetOvni.ALTURA;

    private Timer gameLoop;
    private Random random;

    private final String[] nomesDeObstaculos = {
        "meteoro.png", "ovni.png", "asteroide1.png", "asteroide2.png", "asteroide3.png", "asteroide4.png"
    };

    private final String[] nomesDePowerUps = {
        "powerup_vida.png", "powerup_escudo.png"
    };

    private Aeronave jogador;
    private List<Bullet> tiros;
    private List<Obstaculo> obstaculos;
    private List<Explosao> explosoes;

    private List<Image> framesExplosao;

    private List<PowerUp> powerUps;
    private List<Bullet> tirosInimigos;

    private Fundo fundo1;
    private Fundo fundo2;
    private final String NOME_IMAGEM_FUNDO = "fundo_espaco.png";

    private long ultimoTempoExplosao = 0;
    private final long DELAY_SOM_EXPLOSAO = 50;

    private boolean isGameOver = false;
    private int score = 0;
    private int dificuldade = 1;

    public GamePanel() {
        setPreferredSize(new Dimension(LARGURA, ALTURA));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new TecladoListener());

        random = new Random();

        inicializarObjetos();

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    private void inicializarObjetos() {
        jogador = new Jet(LARGURA / 2, ALTURA - 100);
        tiros = new ArrayList<>();
        tirosInimigos = new ArrayList<>();
        obstaculos = new ArrayList<>();
        explosoes = new ArrayList<>();
        powerUps = new ArrayList<>();

        isGameOver = false;
        score = 0;
        dificuldade = 1;

        CarregadorDeAudio.carregarClips();
        CarregadorDeAudio.tocarMusicaDeFundo("musica_fundo.wav");

        fundo1 = new Fundo(0, 1, NOME_IMAGEM_FUNDO, this);
        fundo2 = new Fundo(-ALTURA, 1, NOME_IMAGEM_FUNDO, this);

        framesExplosao = new ArrayList<>();
        Obstaculo obstaculoTemp = new Obstaculo(0, 0, 0, "ovni.png");
        int larguraExplosao = obstaculoTemp.getLargura();
        int alturaExplosao = obstaculoTemp.getAltura();

        for (int i = 1; i <= 4; i++) {
            Image frame = CarregadorDeImagens.carregarImagem("explosao_" + i + ".png");
            if (frame != null) {
                frame = frame.getScaledInstance(larguraExplosao, alturaExplosao, Image.SCALE_SMOOTH);
                framesExplosao.add(frame);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // NOVO: Desenha o fundo ANTES de tudo
        fundo1.desenhar(g2d);
        fundo2.desenhar(g2d);

        if (jogador != null) {
            jogador.desenhar(g2d);

            if (jogador instanceof Jet && ((Jet) jogador).temEscudo()) {
                Image escudo = CarregadorDeImagens.carregarImagem("powerup_escudo.png");
                if (escudo != null) {
                    g2d.drawImage(escudo, jogador.x, jogador.y, jogador.largura, jogador.altura, this);
                }
            }
        }

        for (Bullet tiro : tiros) {
            tiro.desenhar(g2d);
        }

        for (Bullet tiro : tirosInimigos) {
            tiro.desenhar(g2d);
        }

        for (Obstaculo obstaculo : obstaculos) {
            obstaculo.desenhar(g2d);
        }

        for (Explosao explosao : explosoes) {
            explosao.desenhar(g2d);
        }

        for (PowerUp powerUp : powerUps) {
            powerUp.desenhar(g2d);
        }

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("Pontuação: " + score, 10, 25);
        g2d.drawString("Vidas: " + jogador.getSaude(), 10, 50);
        g2d.drawString("Nível: " + dificuldade, 10, 75);

        if (isGameOver) {
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 50));
            String msg = "GAME OVER";
            int larguraTexto = g2d.getFontMetrics().stringWidth(msg);
            g2d.drawString(msg, (LARGURA - larguraTexto) / 2, ALTURA / 2);

            g2d.setFont(new Font("Arial", Font.PLAIN, 20));
            String restartMsg = "Pressione 'R' para reiniciar";
            int larguraRestart = g2d.getFontMetrics().stringWidth(restartMsg);
            g2d.drawString(restartMsg, (LARGURA - larguraRestart) / 2, ALTURA / 2 + 60);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isGameOver) {
            updateGame();
            repaint();
        }
    }

    private void updateGame() {
        jogador.mover();

        // NOVO: Move o fundo e reinicia quando sai da tela para criar um loop
        fundo1.mover();
        fundo2.mover();

        if (fundo1.getY() >= ALTURA) {
            // CORREÇÃO: Usa o método setY() e getAltura() para a posição
            fundo1.setY(-fundo1.getAltura());
        }
        if (fundo2.getY() >= ALTURA) {
            fundo2.setY(-fundo2.getAltura());
        }

        if (jogador instanceof Jet) {
            Jet jetPlayer = (Jet) jogador;
            if (jetPlayer.temEscudo() && (System.currentTimeMillis() - jetPlayer.getTempoAtivacaoEscudo() > jetPlayer.getDuracaoEscudo())) {
                jetPlayer.desativarEscudo();
            }
        }

        for (Explosao explosao : explosoes) {
            explosao.atualizar();
        }
        explosoes.removeIf(Explosao::isAnimacaoConcluida);

        // Move e remove os tiros do jogador
        for (Bullet tiro : tiros) {
            tiro.mover();
        }
        tiros.removeIf(tiro -> tiro.getY() < 0);

        // Move e remove os tiros dos inimigos
        for (Bullet tiro : tirosInimigos) {
            tiro.mover();
        }
        tirosInimigos.removeIf(tiro -> tiro.getY() > ALTURA);

        // Faz os ovnis atiradores atirarem
        for (Obstaculo obstaculo : obstaculos) {
            if (obstaculo instanceof OvniAtirador) {
                OvniAtirador ovni = (OvniAtirador) obstaculo;
                if (ovni.podeAtirar()) {
                    tirosInimigos.add(ovni.atirar());
                }
            }
        }
        
        // Move e remove os obstáculos
        for (Obstaculo obstaculo : obstaculos) {
            obstaculo.mover();
        }
        obstaculos.removeIf(obstaculo -> obstaculo.getY() > ALTURA);

        // Move e remove os power-ups
        for (PowerUp powerUp : powerUps) {
            powerUp.mover();
        }
        powerUps.removeIf(powerUp -> powerUp.getY() > ALTURA);

        // Lógica de spawn de power-ups
        if (random.nextInt(200) < 1) {
            int xPos = random.nextInt(LARGURA - 40);
            int velocidade = 1;
            String nomeImagem = nomesDePowerUps[random.nextInt(nomesDePowerUps.length)];
            powerUps.add(new PowerUp(xPos, 0, velocidade, nomeImagem));
        }

        // CORREÇÃO: Lógica de spawn de obstáculos e OvniAtirador
        if (random.nextInt(100) < 5 + dificuldade) {
            int xPos = random.nextInt(LARGURA - 40);
            int velocidade = random.nextInt(dificuldade) + 2;

            if (random.nextBoolean() && dificuldade > 1) {
                obstaculos.add(new OvniAtirador(xPos, 0, velocidade));
            } else {
                String nomeImagem = nomesDeObstaculos[random.nextInt(nomesDeObstaculos.length)];
                obstaculos.add(new Obstaculo(xPos, 0, velocidade, nomeImagem));
            }
        }
        
        // --- LÓGICA DE COLISÃO AGRUPADA E CORRIGIDA ---

        // Colisão 1: Tiros do jogador vs. Obstáculos
        List<Bullet> tirosParaRemover = new ArrayList<>();
        List<Obstaculo> obstaculosParaRemover = new ArrayList<>();
        for (Bullet tiro : tiros) {
            for (Obstaculo obstaculo : obstaculos) {
                if (tiro.getBounds().intersects(obstaculo.getBounds())) {
                    tirosParaRemover.add(tiro);
                    obstaculosParaRemover.add(obstaculo);
                    explosoes.add(new Explosao(obstaculo.getX(), obstaculo.getY(), obstaculo.getLargura(), obstaculo.getAltura(), framesExplosao));
                    long tempoAtual = System.currentTimeMillis();
                    if (tempoAtual - ultimoTempoExplosao > DELAY_SOM_EXPLOSAO) {
                        CarregadorDeAudio.tocarSom("explosao.wav");
                        ultimoTempoExplosao = tempoAtual;
                    }
                    score++;
                }
            }
        }
        tiros.removeAll(tirosParaRemover);
        obstaculos.removeAll(obstaculosParaRemover);

        // Colisão 2: Jogador vs. Tiros dos Inimigos
        List<Bullet> tirosInimigosRemover = new ArrayList<>();
        for (Bullet tiroInimigo : tirosInimigos) {
            if (jogador.getBounds().intersects(tiroInimigo.getBounds())) {
                if (jogador instanceof Jet && !((Jet) jogador).temEscudo()) {
                    jogador.perderSaude();
                }
                tirosInimigosRemover.add(tiroInimigo);
                explosoes.add(new Explosao(tiroInimigo.getX(), tiroInimigo.getY(), tiroInimigo.getLargura(), tiroInimigo.getAltura(), framesExplosao));
                CarregadorDeAudio.tocarSom("explosao.wav");
            }
        }
        tirosInimigos.removeAll(tirosInimigosRemover);

        // Colisão 3: Jogador vs. Obstáculos
        List<Obstaculo> obstaculosAtingidos = new ArrayList<>();
        for (Obstaculo obstaculo : obstaculos) {
            if (jogador.getBounds().intersects(obstaculo.getBounds())) {
                if (jogador instanceof Jet && ((Jet) jogador).temEscudo()) {
                    // Se o jogador tem escudo, o obstáculo é destruído sem causar dano
                    obstaculosAtingidos.add(obstaculo);
                    explosoes.add(new Explosao(obstaculo.getX(), obstaculo.getY(), obstaculo.getLargura(), obstaculo.getAltura(), framesExplosao));
                    CarregadorDeAudio.tocarSom("explosao.wav");
                } else {
                    jogador.perderSaude();
                    obstaculosAtingidos.add(obstaculo);
                }
            }
        }
        obstaculos.removeAll(obstaculosAtingidos);

        // Colisão 4: Jogador vs. Power-ups
        List<PowerUp> powerUpsColetados = new ArrayList<>();
        for (PowerUp powerUp : powerUps) {
            if (jogador.getBounds().intersects(powerUp.getBounds())) {
                powerUpsColetados.add(powerUp);
                if (powerUp.getNomeArquivo().equals("powerup_vida.png")) {
                    ((Jet) jogador).aumentarSaude();
                } else if (powerUp.getNomeArquivo().equals("powerup_escudo.png")) {
                    ((Jet) jogador).ativarEscudo();
                }
            }
        }
        powerUps.removeAll(powerUpsColetados);

        // Lógica para aumentar a dificuldade
        if (score > 0 && score / 10 >= dificuldade) {
            dificuldade++;
        }

        // Lógica de game over
        if (jogador.getSaude() <= 0) {
            isGameOver = true;
            gameLoop.stop();
        }

        // Limites da tela
        if (jogador.getX() < 0) jogador.x = 0;
        if (jogador.getX() > LARGURA - jogador.largura) jogador.x = LARGURA - jogador.largura;
        if (jogador.getY() < 0) jogador.y = 0;
        if (jogador.getY() > ALTURA - jogador.altura) jogador.y = ALTURA - jogador.altura;
    }

    private class TecladoListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (isGameOver && key == KeyEvent.VK_R) {
                inicializarObjetos();
                gameLoop.start();
                return;
            }

            if (!isGameOver) {
                if (key == KeyEvent.VK_LEFT) {
                    jogador.moverEsquerda();
                }
                if (key == KeyEvent.VK_RIGHT) {
                    jogador.moverDireita();
                }
                if (key == KeyEvent.VK_UP) {
                    jogador.moverCima();
                }
                if (key == KeyEvent.VK_DOWN) {
                    jogador.moverBaixo();
                }

                if (key == KeyEvent.VK_SPACE) {
                    Bullet novoTiro = jogador.atirar();
                    if (novoTiro != null) {
                        tiros.add(novoTiro);
                        CarregadorDeAudio.tocarSom("tiro.wav");
                    }
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (!isGameOver) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
                    jogador.pararMovimentoHorizontal();
                }
                if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
                    jogador.pararMovimentoVertical();
                }
            }
        }
    }
}
*/