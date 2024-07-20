package com.edivan.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.edivan.entities.Enemy;
import com.edivan.entities.Player;
import com.edivan.game.Game;

public class UI {
	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(20, 8, 50, 8);
		g.setColor(Color.red);
		g.fillRect(20, 8,(int)((Game.player.life/100) * 50), 8);
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 8));
		g.drawString((int)Game.player.life + "/" + "100",30, 8);
		g.setFont(new Font("Arial", Font.BOLD, 8));
		g.drawString("Pontuação: " + Enemy.pontosPlayer, 150, 10);
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 8));
		g.drawString("Munição: " + Game.player.ammo , 250, 10);
	}
}
