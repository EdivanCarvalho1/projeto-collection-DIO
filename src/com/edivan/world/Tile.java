package com.edivan.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.edivan.game.Game;

public class Tile {
	static BufferedImage TILE_FLOOR = Game.sheet.getSprite(0, 0, 16, 16);
	static BufferedImage TILE_WALL = Game.sheet.getSprite(16, 0, 16, 16);
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	private BufferedImage sprite;
	private int x,y;
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y,  null);
	}
}
