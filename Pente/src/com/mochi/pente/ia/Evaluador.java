package com.mochi.pente.ia;

import java.util.ArrayList;

import com.mochi.pente.entity.Jugada;
import com.mochi.pente.entity.Partida;


public class Evaluador {
	public final int MAX_PESO = 1000;
	public final int STEP = 10;
	private ArrayList<Objetivo> objetivos;
	
	public Evaluador() {
		this.inicializarEstrategia();
	}
	
	public int evaluar(Partida partida,Jugada pos) {
		int peso = 0;
		for (Objetivo obj : this.objetivos) {
			int newPeso = obj.evaluar(partida, pos);
			if (newPeso>peso) peso = newPeso;
		}
		return peso;
	}
	
	private void inicializarEstrategia(){
		this.objetivos = new ArrayList<Objetivo>();
		
		int i = 0;
		this.objetivos.add(new ConseguirLinea(this.MAX_PESO - (this.STEP * i),5,false));
		
		i++;
		this.objetivos.add(new EvitarLinea(this.MAX_PESO - (this.STEP*i),5,false));
		
		i++;
		this.objetivos.add(new ConseguirLinea(this.MAX_PESO - (this.STEP*i),4,false));
		
		i++;
		this.objetivos.add(new EvitarLinea(this.MAX_PESO - (this.STEP*i),4,false));
		
		i++;
		//this.objetivos[i] = new ConseguirLinea(this.MAX_PESO - (this.STEP*i),4,true);
		
		i++;
		//this.objetivos[i] = new EvitarLinea(this.MAX_PESO - (this.STEP*i),4,true);
		
		i++;
		this.objetivos.add( new Robar2Piezas(this.MAX_PESO - (this.STEP*i)));

		i++;
		this.objetivos.add(new ConseguirLinea(this.MAX_PESO - (this.STEP*i),3,false));
		
		i++;
		this.objetivos.add(new EvitarLinea(this.MAX_PESO - (this.STEP*i),3,false));
		
		i++;
		//this.objetivos[i] = new ConseguirLinea(this.MAX_PESO - (this.STEP*i),3,true);
		
		i++;
		//this.objetivos[i] = new EvitarLinea(this.MAX_PESO - (this.STEP*i),3,true);
		
		i++;
		this.objetivos.add(new ConseguirLinea(this.MAX_PESO - (this.STEP*i),2,false));
		
		i++;
		this.objetivos.add(new EvitarLinea(this.MAX_PESO - (this.STEP*i),2,false));
		
		i++;
		//this.objetivos[i] = new ConseguirLinea(this.MAX_PESO - (this.STEP*i),2,true);
		
		i++;
		//this.objetivos[i] = new EvitarLinea(this.MAX_PESO - (this.STEP*i),2,true);		
		
		this.objetivos.add(new ConseguirLinea(this.MAX_PESO - (this.STEP*i),1,false));
		
		i++;
		this.objetivos.add(new EvitarLinea(this.MAX_PESO - (this.STEP*i),1,false));
	}

}
