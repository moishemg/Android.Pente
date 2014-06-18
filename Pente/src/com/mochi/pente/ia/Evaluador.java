package com.mochi.pente.ia;

import com.mochi.pente.entity.Jugada;


public class Evaluador {
	public final int MAX_PESO = 1000;
	public final int STEP = 10;
	private Objetivo[] objetivos;
	
	public Evaluador() {
		this.inicializarEstrategia();
	}
	
	public int evaluar(int[][] tablero,Jugada pos) {
		int peso = 0;
		for (int i=0;i<this.objetivos.length;i++) {
			int newPeso = this.objetivos[i].evaluar(tablero, pos);
			if (newPeso>peso) peso = newPeso;
		}
		return peso;
	}
	
	private void inicializarEstrategia(){
		this.objetivos = new Objetivo[15];
		
		int i = 0;
		this.objetivos[i] = new ConseguirLinea(this.MAX_PESO - (this.STEP * i),5,false);
		
		i++;
		this.objetivos[i] = new EvitarLinea(this.MAX_PESO - (this.STEP*i),5,false);
		
		i++;
		this.objetivos[i] = new ConseguirLinea(this.MAX_PESO - (this.STEP*i),4,false);
		
		i++;
		this.objetivos[i] = new EvitarLinea(this.MAX_PESO - (this.STEP*i),4,false);
		
		i++;
		this.objetivos[i] = new ConseguirLinea(this.MAX_PESO - (this.STEP*i),4,true);
		
		i++;
		this.objetivos[i] = new EvitarLinea(this.MAX_PESO - (this.STEP*i),4,false);
		
		i++;
		this.objetivos[i] = new Robar2Piezas(this.MAX_PESO - (this.STEP*i));

		i++;
		this.objetivos[i] = new ConseguirLinea(this.MAX_PESO - (this.STEP*i),3,false);
		
		i++;
		this.objetivos[i] = new EvitarLinea(this.MAX_PESO - (this.STEP*i),3,false);
		
		i++;
		this.objetivos[i] = new ConseguirLinea(this.MAX_PESO - (this.STEP*i),3,true);
		
		i++;
		this.objetivos[i] = new EvitarLinea(this.MAX_PESO - (this.STEP*i),3,false);
		
		i++;
		this.objetivos[i] = new ConseguirLinea(this.MAX_PESO - (this.STEP*i),2,false);
		
		i++;
		this.objetivos[i] = new EvitarLinea(this.MAX_PESO - (this.STEP*i),2,false);
		
		i++;
		this.objetivos[i] = new ConseguirLinea(this.MAX_PESO - (this.STEP*i),2,true);
		
		i++;
		this.objetivos[i] = new EvitarLinea(this.MAX_PESO - (this.STEP*i),2,false);		
	}

}
