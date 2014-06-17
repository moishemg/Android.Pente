package com.mochi.pente.ia;

public class Conseguir4LineaSinBorde extends Objetivo {
	public Conseguir4LineaSinBorde(){
		super.puntuacion = super.MAX_PUNTUACION - (super.STEP*2);
	}
}
