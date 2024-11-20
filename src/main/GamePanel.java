package main;

import entity.BlockManager;

import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {
    Thread gameThread;

    BlockManager bm = new BlockManager(this);

    int SCREEN_W = 768;
    int SCREEN_H = 576;

    public int collisions = 0;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_W, SCREEN_H));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000_000_000_000L;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += currentTime - lastTime / drawInterval;

            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();

                delta--;
            }
        }
    }

    public void update() {
        bm.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        bm.draw(g2);
        g2.setFont(new Font("Arial", Font.PLAIN, 40));
        g2.drawString(String.valueOf(collisions), 550, 40);
        g2.dispose();
    }
}
