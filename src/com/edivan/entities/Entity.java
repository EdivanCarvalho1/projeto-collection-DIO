package com.edivan.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.edivan.game.Game;
import com.edivan.world.Camera;

public class Entity {
	protected int width, height;
	protected double x, y;
	protected BufferedImage sprite;
	public static BufferedImage LIFEPACK_EN = Game.sheet.getSprite(6*16, 0, 16, 16);
	public static BufferedImage WEAPON_EN = Game.sheet.getSprite(7*16, 0, 16, 16);
	public static BufferedImage BULLET_EN = Game.sheet.getSprite(6*16, 16, 16, 16);
	public static BufferedImage ENEMY_EN = Game.sheet.getSprite(7*16, 16, 16, 16);
	public static BufferedImage GUN_RIGHT = Game.sheet.getSprite(128, 0, 16, 16);
	public static BufferedImage GUN_LEFT = Game.sheet.getSprite(128+16, 0, 16, 16);
	public static BufferedImage ENEMY_FEEDBACK = Game.sheet.getSprite(16, 16, 16, 16);
	private int maskx;
	private static int masky;
	private int mwidth;
	private int mheight;
	public Entity(double x, double y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
		
	}
	public void setMask(int maskx, int masky, int maskwidth, int mheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = maskwidth;
		this.mheight = mheight;
		
	}
	public double getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public static boolean isColliding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle((int)(e1.getX() + e1.maskx), (int)(e1.getY() + masky), e1.mwidth, e1.mheight);
		Rectangle e2Mask = new Rectangle((int)(e2.getX() + e1.maskx), (int)(e2.getY() + masky), e2.mwidth, e2.mheight);
		
		return e1Mask.intersects(e2Mask);
	}
	public void render(Graphics g) {
		g.drawImage(sprite, (int)this.getX() - Camera.x, (int)this.getY() - Camera.y, null);
		//g.setColor(Color.red);
		//g.fillRect( (int)this.getX() + maskx - Camera.x, (int)this.getY() + masky - Camera.y, mwidth, mheight);
	}
	public void tick() {
		
	}
}
