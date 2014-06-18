package com.mochi.pente.entity;

import java.util.ArrayList;

public class Partida {
	public static int MAX_FILAS = 19;
	public static int MAX_COLUMNAS = 19;
	public static int VACIO = 0;
	public static int FICHA_ROJO = 1;
	public static int FICHA_AMARILLO = 2;
	public static int FICHA_AZUL = 3;
	public static int FICHA_VERDE = 4;
	public final int PAREJAS_ROBADAS = 5;
	public final int FICHAS_IGUALES_LINEA = 5;
	
	public static int[][] movimientosVecinos = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,1},{1,0},{1,-1}};
	
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
		this.tablero = new int[Partida.MAX_FILAS][Partida.MAX_COLUMNAS];
		for (int iFila=0;iFila<Partida.MAX_FILAS;iFila++) {
			for (int iCol=0;iCol<Partida.MAX_COLUMNAS;iCol++) {
				this.tablero[iFila][iCol] = Partida.VACIO;
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
	
	public boolean esFinPartida(){
		return this.fin;
	}
	
	public boolean esFinPartidaGanadora(){
		return this.finGanador;
	}
	
	public static boolean posicionDentroTablero(Jugada pos) {
		return (pos.fila>=0 && pos.fila<=Partida.MAX_FILAS && pos.columna>=0 && pos.columna<=Partida.MAX_COLUMNAS);
	}
	
	private boolean posicionFichaJugador(Jugada pos) {
		return (this.tablero[pos.fila][pos.columna]==this.turno.getFicha());
	}
	
	private void establecerJugadorTablero(Jugada pos) {
		this.tablero[pos.fila][pos.columna]=this.turno.getFicha();
	}
	
	public void comprobarJugada(Jugada jug){
		this.establecerJugadorTablero(jug);
		this.comprobar5Linea(jug);
		if (!this.fin) this.comprobarRobar2(jug);
	}
	
	private void comprobar5Linea(Jugada jug) {
		int[][] movimientos = {{-1,-1},{-1,0},{-1,1},{0,-1}};
		int iMovimiento = 0;
		while (!this.fin && iMovimiento<movimientos.length) {
			this.comprobarLinea(jug,movimientos[iMovimiento][0],movimientos[iMovimiento][1]);
			iMovimiento++;
		}
	}
	
	private void comprobarLinea(Jugada jug,int mov_fila,int mov_columna){
		Jugada jugPos = new Jugada(jug.fila-mov_fila,jug.columna-mov_columna);
		while (Partida.posicionDentroTablero(jugPos) && this.posicionFichaJugador(jugPos)) {
			jugPos.fila-=mov_fila;
			jugPos.columna-=mov_columna;
		}
		jugPos.fila+=mov_fila;
		jugPos.columna+=mov_columna;
		
		int numFichasIguales = 0;
		this.linea = new ArrayList<Jugada>();
		while (Partida.posicionDentroTablero(jugPos) && this.posicionFichaJugador(jugPos)) {
			this.linea.add(new Jugada(jugPos.fila,jugPos.columna));
			numFichasIguales++;
			jugPos.fila+=mov_fila;
			jugPos.columna+=mov_columna;
		}
		
		this.setFinGanador(numFichasIguales==this.FICHAS_IGUALES_LINEA);
	}
	
	private void comprobarRobar2(Jugada jug){
		boolean robar = false;
		int iMovimiento = 0;
		while (!robar && iMovimiento<Partida.movimientosVecinos.length) {
			robar = this.comprobarRobar(jug,Partida.movimientosVecinos[iMovimiento][0],Partida.movimientosVecinos[iMovimiento][1]);
			iMovimiento++;
		}
		
		if (robar) this.sumarParejaRobada();
	}
	
	private boolean comprobarRobar(Jugada jug,int mov_fila,int mov_columna) {
		boolean robar = false;
		this.linea = new ArrayList<Jugada>();
		this.linea.add(new Jugada(jug.fila,jug.columna));
		Jugada jugPos = new Jugada(jug.fila+mov_fila,jug.columna+mov_columna);
		if (Partida.posicionDentroTablero(jugPos) && !this.posicionFichaJugador(jugPos)) {
			this.linea.add(jugPos);
			jugPos = new Jugada(jugPos.fila+mov_fila,jugPos.columna+mov_columna);
			if (Partida.posicionDentroTablero(jugPos) && !this.posicionFichaJugador(jugPos)) {
				this.linea.add(jugPos);
				jugPos = new Jugada(jugPos.fila+mov_fila,jugPos.columna+mov_columna);
				if (Partida.posicionDentroTablero(jugPos) && this.posicionFichaJugador(jugPos)) {
					this.linea.add(jugPos);
					robar = true;
				} // if propia ficha
			} // if ficha contrario
		} // if ficha contrario
		
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
