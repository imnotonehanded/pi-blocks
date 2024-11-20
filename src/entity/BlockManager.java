package entity;

import main.GamePanel;

import javax.sound.sampled.*;

import java.awt.*;

public class BlockManager {
    GamePanel gp;

    private final Block[] blocks;
    private long lastSoundTime = 0;

    public BlockManager(GamePanel gp) {
        this.gp = gp;
        this.blocks = new Block[3];

        blocks[0] = new Block(
                0,
                0,
                0,
                0,
                40,
                600,
                0,
                Color.darkGray
        );

        blocks[1] = new Block(
                200,
                476,
                0,
                0,
                100,
                100,
                1,
                Color.lightGray
        );

        blocks[2] = new Block(
                500,
                376,
                -0.000003,
                0,
                200,
                200,
                100_000_000_000_000L,
                Color.WHITE
        );
    }

    public void draw(Graphics2D g2) {
        for (Block i: blocks) {
            i.draw(g2);

            if (i.mass == 0) {
                continue;
            }

            g2.setFont(new Font("Arial", Font.PLAIN, 25));
            g2.drawString(i.mass + "kg", (int) i.x , 300);
        }
    }

    public void update() {
        //for (int i = 0; i < 10000; i++) {
            // 0 is wall
            // 1 is small
            // 2 is big
            Block block = blocks[1];
            Block other = blocks[2];
            Block wall = blocks[0];

            boolean blockCollidedWithWall = block.x < wall.x + wall.w && block.x + block.w > wall.x;
            boolean blockCollidedWithOther = block.x < other.x + other.w && block.x + block.w > other.x;


            if (blockCollidedWithWall) {
                // Collided with wall
                sound();
                gp.collisions++;
                block.x_speed = -block.x_speed;

                block.x = wall.x + wall.w; // Adjust position to prevent sticking
            } else if (blockCollidedWithOther) {
                // Collided with big block
                sound();
                gp.collisions++;
                double newBlockSpeed = ((block.mass - other.mass) * block.x_speed + 2 * other.mass * other.x_speed) /
                        (block.mass + other.mass);
                double newOtherSpeed = ((other.mass - block.mass) * other.x_speed + 2 * block.mass * block.x_speed) /
                        (block.mass + other.mass);
                block.x_speed = newBlockSpeed;
                other.x_speed = newOtherSpeed;


                block.x = other.x - block.w; // Adjust position to prevent sticking
                other.x = block.x + block.w;
            }

            block.x += (block.x_speed);
            other.x += (other.x_speed);
        //}
    }
    public void sound() {

        long currentTime = System.nanoTime();
        long soundInterval = 100_000_000;
        if (currentTime - lastSoundTime >= soundInterval) {
            lastSoundTime = currentTime;
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sounds/hit.wav"));
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
