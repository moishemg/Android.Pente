package com.mochi.pente.entity;

import java.util.ArrayList;

public class Partida {
	final int MAX_FILAS = 19;
	final int MAX_COLUMNAS = 19;
	final int VACIO = 0;
	final int FICHA_ROJO = 1;
	final int FICHA_AMARILLO = 2;
	final int FICHA_AZUL = 3;
	final int FICHA_VERDE = 4;
	final int PAREJAS_ROBADAS = 5;
	
	private boolean fin;
	private boolean finGanador;
	private int robadosJug1;
	private int robadosJug2;
	private ArrayList<Jugada> linea;
	
	private Jugador jug1;
	private Jugador jug2;
	private Jugador turno;
	
	private int[][] tablero;
	
	public Partida(Jugador jug1,Jugador jug2){
		this.jug1 = jug1;
		this.jug2 = jug2;
		this.iniciar();
	}
	
	private void iniciar(){
		this.tablero = new int[this.MAX_FILAS][this.MAX_COLUMNAS];
		for (int iFila=0;iFila<this.MAX_FILAS;iFila++) {
			for (int iCol=0;iCol<this.MAX_COLUMNAS;iCol++) {
				this.tablero[iFila][iCol] = this.VACIO;
			}
		}
		this.setFinGanador(false);
		this.robadosJug1 = 0;
		this.robadosJug2 = 0;
		if (((Math.random() * 100) % 2)==0) {
			this.turno = this.jug1;
		} else {
			this.turno = this.jug2;
		}
	}
	
	public void siguienteTurno(){
		if (this.turno.equals(this.jug1)) {
			this.turno = this.jug2;
		} else {
			this.turno = this.jug2;
		}
	}
	
	private void setFinGanador(boolean valor) {
		this.finGanador = valor;
		this.fin = valor;
	}
	
	private boolean posicionDentroTablero(Jugada pos) {
		return (pos.fila>=0 && pos.fila<=this.MAX_FILAS && pos.columna>=0 && pos.columna<=this.MAX_COLUMNAS);
	}
	
	private boolean posicionFichaJugador(Jugada pos) {
		return (this.tablero[pos.fila][pos.columna]==this.turno.getFicha());
	}
	
	private void establecerFichaJugadorTablero(Jugada pos) {
		this.tablero[pos.fila][pos.columna]=this.turno.getFicha();
	}
	
	public void comprobar5Linea(Jugada jug) {
		
	}
	
	private void comprobarLinea(Jugada jug,int mov_fila,int mov_columna){
	
	}
	
	public void comprobarRobar2(Jugada jug){
		int[][] movimientos = {{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}};
		boolean robar = false;
		int iMovimiento = 0;
		while (!robar && iMovimiento<movimientos.length) {
			robar = this.comprobarRobar(jug,movimientos[iMovimiento][0],movimientos[iMovimiento][1]);
			iMovimiento++;
		}
		
		if (robar) this.sumarParejaRobada();
	}
	
	private boolean comprobarRobar(Jugada jug,int mov_fila,int mov_columna) {
		boolean robar = false;
		this.linea = new ArrayList<Jugada>();
		
		if (!robar) this.linea = new ArrayList<Jugada>();
		return robar;
	}
	
	private void sumarParejaRobada(){
		if (this.turno.equals(this.jug1)) {
			this.robadosJug1++;
			this.setFinGanador(this.robadosJug1==this.PAREJAS_ROBADAS);
		} else {
			this.robadosJug2++;
			this.setFinGanador(this.robadosJug2==this.PAREJAS_ROBADAS);
		}
	}
}
