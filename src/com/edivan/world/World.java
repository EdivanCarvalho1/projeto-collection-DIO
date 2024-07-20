package com.edivan.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.edivan.entities.Bullet;
import com.edivan.entities.Enemy;
import com.edivan.entities.Entity;
import com.edivan.entities.Medkit;
import com.edivan.entities.Weapon;
import com.edivan.game.Game;

public class World {
	public static Tile[] tiles;
	public static int HEIGHT, WIDTH;
	public static final int TILE_SIZE = 16;
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for (int xx = 0; xx < map.getWidth(); xx++) {
				for (int yy = 0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
					if (pixelAtual == 0xFF000000) {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
					} else if (pixelAtual == 0xFFFFFFFF) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL);
					} else if (pixelAtual == 0xFF2837FF) {
						Game.player.setX(xx * 16);
						Game.player.setY(yy * 16);
					} else if (pixelAtual == 0xFFFF0000) {
						Enemy en = new Enemy(xx * 16, yy *16,16, 16, Entity.ENEMY_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
						
						

					} else if (pixelAtual == 0xFFFF7F7F) {
						Medkit med = new Medkit(xx*16, yy*16, 16, 16, Entity.LIFEPACK_EN);
						Game.entities.add(med);
						med.setMask(8,8,8,8);
					} else if (pixelAtual == 0xFFFFD800) {
						Game.entities.add(new Bullet(xx*16, yy*16, 16, 16, Entity.BULLET_EN));
					} else if (pixelAtual == 0xFFFF8B2D) {
						Game.entities.add(new Weapon(xx*16, yy*16, 16, 16, Entity.WEAPON_EN));
					}

				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static boolean isFree(int xnext, int ynext) {
	    int xTile = xnext / TILE_SIZE;
	    int yTile = ynext / TILE_SIZE;

	    if (xTile < 0 || xTile >= WIDTH || yTile < 0 || yTile >= HEIGHT) {
	        return false;
	    }

	    Tile topLeft = tiles[xTile + yTile * WIDTH];
	    Tile topRight = tiles[(xTile + 1) + yTile * WIDTH];
	    Tile bottomLeft = tiles[xTile + (yTile + 1) * WIDTH];
	    Tile bottomRight = tiles[(xTile + 1) + (yTile + 1) * WIDTH];

	    return !(topLeft instanceof WallTile) &&
	           !(topRight instanceof WallTile) &&
	           !(bottomLeft instanceof WallTile) &&
	           !(bottomRight instanceof WallTile);
	}
	public void render(Graphics g) {
	    int xstart = Math.max(0, Camera.x / 16);
	    int ystart = Math.max(0, Camera.y / 16);
	    int xfinal = Math.min(WIDTH - 1, xstart + (Game.WIDTH / 16)); // Ensure xfinal is within bounds
	    int yfinal = Math.min(HEIGHT - 1, ystart + (Game.HEIGHT / 16)); // Ensure yfinal is within bounds

	    for (int x = xstart; x <= xfinal; x++) {
	        for (int y = ystart; y <= yfinal; y++) {
	            Tile tile = tiles[x + (y * WIDTH)];
	            tile.render(g);
	        }
	    }
	}
}