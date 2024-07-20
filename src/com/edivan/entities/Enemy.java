package com.edivan.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.edivan.game.Game;
import com.edivan.world.Camera;
import com.edivan.world.World;

public class Enemy extends Entity {
	private double speed = 0.6;
	private int frames = 0;
	int maxFrames = 20;
	int index = 0;
	int maxIndex = 1;
	private int lifeEnemy = 10;
	private boolean enemyDamaged = false;
	private int damageFrames = 10, damageCurrent = 0;
	public static int pontosPlayer = 0;
	public Enemy(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		sprites = new BufferedImage[2];
		sprites[0] = Game.sheet.getSprite(112, 16, 16, 16);
		sprites[1] = Game.sheet.getSprite(112 + 16, 16, 16, 16);
	}

	public void tick() {
		if (this.isCollidingWithPlayer() == false) {
			if ((int) x < Game.player.getX() && World.isFree((int) (x + speed), (int) this.getY())
					&& !isColliding((int) (x + speed), (int) this.getY())) {
				x += speed;
			} else if ((int) x > Game.player.getX() && World.isFree((int) (x - speed), (int) this.getY())
					&& !isColliding((int) (x - speed), (int) this.getY())) {
				x -= speed;
			}
			if ((int) y < Game.player.getY() && World.isFree((int) (this.getX()), (int) (y + speed))
					&& !isColliding((int) (this.getX()), (int) (y + speed))) {
				y += speed;
			} else if ((int) y > Game.player.getY() && World.isFree((int) (this.getX()), (int) (y - speed))
					&& !isColliding((int) (this.getX()), (int) (y - speed))) {
				y -= speed;
			}
		} else {
			if (Game.rand.nextInt(100) < 10) {
				Game.player.life-= 5;
				Game.player.isDamaged = true;
				System.out.println(Game.player.life);
			}

			if (Game.player.life == 0) {
				//System.exit(1);
			}
		}

		frames++;
		if (frames >= maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex)
				index = 0;
		}
		collidingBullet();
		if(lifeEnemy<=0) {
			destroySelf();
			return;
		}
		if(enemyDamaged) {
			this.damageCurrent++;
			if(this.damageCurrent == this.damageFrames) {
				this.damageCurrent = 0;
				this.enemyDamaged = false;
			}
		}
	}
	public void destroySelf() {
		Game.enemies.remove(this);
		Game.entities.remove(this);
		pontosPlayer+=10;
	}
	
	public void collidingBullet() {
		for(int i = 0; i < Game.bullets.size();i++) {
			Entity e = Game.bullets.get(i);
			if(e instanceof BulletShoot) {
				if(Entity.isColliding(this, e)) {
					enemyDamaged = true;
					lifeEnemy-=3;
					Game.bullets.remove(i);
					return;
				}
			}
		}	

	}
	public boolean isCollidingWithPlayer() {
		Rectangle currentEnemy = new Rectangle((int) this.getX(), (int) this.getY(), World.TILE_SIZE, World.TILE_SIZE);
		Rectangle player = new Rectangle((int) Game.player.getX(), (int) Game.player.getY(), 16, 16);
		return currentEnemy.intersects(player);
	}

	public boolean isColliding(int xnext, int ynext) {
		Rectangle currentEnemy = new Rectangle(xnext, ynext, World.TILE_SIZE, World.TILE_SIZE);

		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if (e == this)
				continue;
			Rectangle targetEnemy = new Rectangle((int) e.getX(), (int) e.getY(), World.TILE_SIZE, World.TILE_SIZE);
			if (currentEnemy.intersects(targetEnemy)) {
				return true;
			}
		}
		return false;
	}

	private BufferedImage[] sprites;

	public void render(Graphics g) {
		if(!enemyDamaged) {
			g.drawImage(sprites[index], (int) this.getX() - Camera.x, (int) this.getY() - Camera.y, null);
		}else {
			g.drawImage(ENEMY_FEEDBACK, (int) this.getX() - Camera.x, (int) this.getY() - Camera.y, null);
		}
		
	}
}
