package entity;

import java.awt.*;

public class Block {

    public double x, y;
    public double x_speed;
    public int w,h;
    public long mass; // in g
    public Color color;

    public Block(double x, double y, double x_speed, double y_speed, int w, int h, long mass, Color color) {

        this.x = x;
        this.y = y;

        this.x_speed = x_speed;

        this.w = w;
        this.h = h;

        this.mass = mass;

        this.color = color;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);

        g2.fillRect((int) x, (int) y, w, h);

    }
}
