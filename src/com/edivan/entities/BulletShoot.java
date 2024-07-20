package com.edivan.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.edivan.game.Game;
import com.edivan.world.Camera;

public class BulletShoot extends Entity {
	private double dx, dy;
	private double spd = 4;
	private int lifeArma = 40, curLife = 0;
	public void tick() {
		x+=dx*spd;
		y+=dy*spd;
	}
	public BulletShoot(double x, double y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
		if(curLife == lifeArma) {
			Game.bullets.remove(this);
			return;
		}
	}
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval((int)this.getX() - Camera.x, (int) this.getY() - Camera.y , 3, 3);
	}
}
