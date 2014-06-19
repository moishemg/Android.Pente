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
		if (((Math.ceil(Math.random() * 100)) % 2)==0) {
			this.turno = this.jug1;
		} else {
			this.turno = this.jug2;
		}
	}
	
	public Jugador siguienteTurno(){
		if (this.turno.equals(this.jug1)) {
			this.turno = this.jug2;
		} else {
			this.turno = this.jug1;
		}
		return this.turno;
	}
	
	public Jugador getTurno(){
		return this.turno;
	}
	
	public void comprobarJugada(Jugada jug){
		this.establecerJugadorTablero(jug);
		this.comprobarLineaDeFichas(this.FICHAS_IGUALES_LINEA,jug,true,true);
		if (!this.fin) this.comprobarRobar2(jug);
	}
	
	public ArrayList<Jugada> lineaFichas(){
		return this.linea;
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
	
	private boolean posicionDentroTablero(Jugada pos) {
		return (pos.fila>=0 && pos.fila<=Partida.MAX_FILAS && pos.columna>=0 && pos.columna<=Partida.MAX_COLUMNAS);
	}
	
	private boolean posicionFichaJugador(Jugada pos) {
		return (this.tablero[pos.fila][pos.columna]==this.turno.getFicha());
	}
	
	private boolean posicionFichaContrario(Jugada pos) {
		return (this.tablero[pos.fila][pos.columna]!=this.turno.getFicha() && this.tablero[pos.fila][pos.columna]!=Partida.VACIO);
	}
	
	private void establecerJugadorTablero(Jugada pos) {
		this.tablero[pos.fila][pos.columna]=this.turno.getFicha();
	}
	
	public void comprobarLineaDeFichasPropia(int numFichasLinea,Jugada jug) {
		this.comprobarLineaDeFichas(numFichasLinea,jug,false,true);
	}
	
	public void comprobarLineaDeFichasContrario(int numFichasLinea,Jugada jug) {
		this.comprobarLineaDeFichas(numFichasLinea,jug,false,false);
	}	
	
	public void comprobarLineaDeFichas(int numFichasLinea,Jugada jug,boolean comprobarFinPartida, boolean propia) {
		int[][] movimientos = {{-1,-1},{-1,0},{-1,1},{0,-1}};
		int iMovimiento = 0;
		boolean fin = false;
		while (!this.fin && iMovimiento<movimientos.length) {
			if (propia)
				fin = (this.comprobarLineaPropia(jug,movimientos[iMovimiento][0],movimientos[iMovimiento][1])==numFichasLinea);
			else
				fin = (this.comprobarLineaContrario(jug,movimientos[iMovimiento][0],movimientos[iMovimiento][1])==numFichasLinea);
			iMovimiento++;
		}
		if (comprobarFinPartida) this.setFinGanador(fin);
	}
	
	private int comprobarLineaPropia(Jugada jug,int mov_fila,int mov_columna){
		Jugada jugPos = new Jugada(jug.fila-mov_fila,jug.columna-mov_columna);
		while (this.posicionDentroTablero(jugPos) && this.posicionFichaJugador(jugPos)) {
			jugPos.fila-=mov_fila;
			jugPos.columna-=mov_columna;
		}
		jugPos.fila+=mov_fila;
		jugPos.columna+=mov_columna;
		
		int numFichasIguales = 0;
		this.linea = new ArrayList<Jugada>();
		while (this.posicionDentroTablero(jugPos) && this.posicionFichaJugador(jugPos)) {
			this.linea.add(new Jugada(jugPos.fila,jugPos.columna));
			numFichasIguales++;
			jugPos.fila+=mov_fila;
			jugPos.columna+=mov_columna;
		}
		
		return numFichasIguales;
	}
	
	private int comprobarLineaContrario(Jugada jug,int mov_fila,int mov_columna){
		Jugada jugPos = new Jugada(jug.fila-mov_fila,jug.columna-mov_columna);
		while (this.posicionDentroTablero(jugPos) && this.posicionFichaContrario(jugPos)) {
			jugPos.fila-=mov_fila;
			jugPos.columna-=mov_columna;
		}
		jugPos.fila+=mov_fila;
		jugPos.columna+=mov_columna;
		
		int numFichasIguales = 0;
		this.linea = new ArrayList<Jugada>();
		while (this.posicionDentroTablero(jugPos) && this.posicionFichaContrario(jugPos)) {
			this.linea.add(new Jugada(jugPos.fila,jugPos.columna));
			numFichasIguales++;
			jugPos.fila+=mov_fila;
			jugPos.columna+=mov_columna;
		}
		
		return numFichasIguales;
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
		if (this.posicionDentroTablero(jugPos) && this.posicionFichaContrario(jugPos)) {
			this.linea.add(jugPos);
			jugPos = new Jugada(jugPos.fila+mov_fila,jugPos.columna+mov_columna);
			if (this.posicionDentroTablero(jugPos) && this.posicionFichaContrario(jugPos)) {
				this.linea.add(jugPos);
				jugPos = new Jugada(jugPos.fila+mov_fila,jugPos.columna+mov_columna);
				if (this.posicionDentroTablero(jugPos) && this.posicionFichaJugador(jugPos)) {
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
	
	public boolean posicionLibreTablero(Jugada pos) {
		return this.posicionDentroTablero(pos) && this.tablero[pos.fila][pos.columna]==Partida.VACIO;
	}
}
