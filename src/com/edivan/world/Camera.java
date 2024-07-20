package com.edivan.world;

public abstract class Camera {
	public static int x = 0, y = 0;
	public static int clamp(int xAtual, int xMin,int xMax){
		if(xAtual < xMin) {
			xAtual = xMin;
		}if(xAtual > xMax) {
			xAtual = xMax;
		}
		return xAtual;
	}
}
