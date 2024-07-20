package com.edivan.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.edivan.game.Game;
import com.edivan.graficos.Spritesheet;
import com.edivan.world.Camera;
import com.edivan.world.World;
public class Player extends Entity {
	public boolean right, up, left, down;
	public double speed = 1.4;
	public int right_dir = 0,left_dir=1;
	public int dir = right_dir;
	private int frames = 0, maxFrames= 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	public double life = 100;
	static int cura = 10;
	public int ammo = 0;
	private BufferedImage playerDamage;
	public boolean isDamaged = false;
	private int damageFrames = 0;
	private boolean hasGun = false;
	public boolean shoot = false;
	public boolean mouseShoot = false;
	public int mx, my;
	
	public Player(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		playerDamage = Game.sheet.getSprite(0, 16, 16, 16);
		for(int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.sheet.getSprite(32 + (i*16), 0, 16, 16);
		}
		for(int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.sheet.getSprite(32 + (i*16), 16, 16, 16);
		}
	}
	public void tick() {
		moved = false;
		if(right && World.isFree((int)this.getX()+(int)speed, (int)this.getY())) {
			moved = true;
			dir = right_dir;
			x+= speed;
		}
		else if(left && World.isFree((int)this.getX()-(int)speed, (int)this.getY())) {
			moved = true;
			dir = left_dir;
			x-= speed;
		}
		if(up && World.isFree((int)this.getX(), (int)(this.getY() - (int)speed))) {
			moved = true;
			y-= speed;
		}else if(down && World.isFree((int)this.getX(), (int)(this.getY() + (int)speed))) {
			moved = true;
			y+= speed;
		}
		if(moved && World.isFree((int)this.getX(), (int)this.getY() + (int)speed)) {
			frames++;
			if(frames >= maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) 
					index = 0;
			}
		}
		if(shoot) {
			shoot = false;
			if(hasGun && ammo > 0) {
				ammo--;
				
				int dx = 0;
				int px = 0, py = 8;
				if(dir == right_dir) {
					px = 4;
					dx = 1;
				}
				else {
					px = -4;
					dx = -1;
				}
				BulletShoot bullet = new BulletShoot(this.getX() + px, this.getY() + py, 3, 3, null, dx, 0);
				Game.bullets.add(bullet);
			}
			
			
		}
		if(mouseShoot) {
			mouseShoot = false;
			if(hasGun && ammo > 0) {
				ammo--;
				double angle = 0;
				int px = 0;
				int py = 8;
				if(dir == right_dir) {
					px = 18;
					angle = Math.atan2(my - (this.getY() + py - Camera.y), mx -(this.getX() + px - Camera.x));
				}
				else {
					px = -8;
					angle = Math.atan2(my - (this.getY() + py - Camera.y), mx -(this.getX() + px - Camera.x));
				}
				double dy = Math.sin(angle);
				double dx = Math.cos(angle);
				
				BulletShoot bullet = new BulletShoot(this.getX() + px, this.getY() + py, 3, 3, null, dx, dy);
				Game.bullets.add(bullet);
				
			}
			
		}
		Camera.x = Camera.clamp((int)(this.getX() - Game.WIDTH/2), 0, World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp((int)(this.getY() - Game.HEIGHT/2), 0, World.HEIGHT*16 - Game.HEIGHT);
		this.checkCollisionMedKit();
		this.checkCollisionAmmo();
		this.checkCollisionWeapon();
		if(isDamaged) {
			this.damageFrames++;
			if(this.damageFrames == 8) {
				this.damageFrames =0;
				isDamaged = false;
			}
		}
		if(life <= 0) {
			Game.gameState = "GAME_OVER";
		}
	}
	public void checkCollisionWeapon() {
		for(int i =0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Weapon) {
				if(Entity.isColliding(this, atual)){
					hasGun = true;
					Game.entities.remove(atual);
					}
				}
			}
		}
	public void checkCollisionAmmo() {
		for(int i =0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Bullet) {
				if(Entity.isColliding(this, atual)){
					ammo+=50;
					Game.entities.remove(atual);
					}
				}
			}
		}
	public void checkCollisionMedKit() {
		for(int i =0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Medkit) {
				if(Entity.isColliding(this, atual)){
					if(life >= 100) {
						return;
					}
					else {
						life+=cura;
						Game.entities.remove(i);
						if(life+cura > 100) {
							life = 100;
						}
					}
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(!isDamaged) {
			if(dir == right_dir) {
				g.drawImage(rightPlayer[index], (int)this.getX() - (int)Camera.x, (int)this.getY() -(int) Camera.y, null);
				if(hasGun) {
					g.drawImage(GUN_RIGHT, (int)this.getX() - Camera.x + 6, (int)this.getY() - Camera.y, null);
				}
			}else if(dir == left_dir) {
				g.drawImage(leftPlayer[index], (int)this.getX() - (int)Camera.x, (int)this.getY() -(int) Camera.y, null);
				if(hasGun) {
					g.drawImage(GUN_LEFT, (int)this.getX() - Camera.x - 6, (int)this.getY() - Camera.y, null);
				}
			}
		}
		else {
			g.drawImage(playerDamage, (int)this.getX() - (int)Camera.x, (int)this.getY() -(int) Camera.y, null);
		}
	}
}
