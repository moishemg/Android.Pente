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
	private ArrayList<Jugada> linea;
	
	private Jugador jug1;
	private Jugador jug2;
	private Jugador turno;
	
	private int[][] tablero;
	
	public Partida(Jugador jug1,Jugador jug2){
		this.jug1 = jug1;
		this.jug2 = jug2;
	}
	
	public FichaPosicion iniciar(){
		this.tablero = new int[Partida.MAX_FILAS][Partida.MAX_COLUMNAS];
		for (int iFila=0;iFila<Partida.MAX_FILAS;iFila++) {
			for (int iCol=0;iCol<Partida.MAX_COLUMNAS;iCol++) {
				this.tablero[iFila][iCol] = Partida.VACIO;
			}
		}
		this.setFinGanador(false);
		this.jug1.iniciarParejasRobadas();
		this.jug2.iniciarParejasRobadas();
		if (((Math.ceil(Math.random() * 100)) % 2)==0) {
			this.turno = this.jug1;
		} else {
			this.turno = this.jug2;
		}
		
		// el primer movimiento es en el centro del tablero
		FichaPosicion ficha = this.primerMovimiento();
		
		this.siguienteTurno();
		
		return ficha;
	}
	
	public Jugador siguienteTurno(){
		if (this.turno.equals(this.jug1)) {
			this.turno = this.jug2;
		} else {
			this.turno = this.jug1;
		}
		return this.turno;
	}
	
	public void rendirse() {
		this.fin = true;
	}
	
	public Jugador getTurno(){
		return this.turno;
	}
	
	public void comprobarJugada(Jugada jug){
		this.establecerJugadorTablero(jug);
		
		// comprobar si ha conseguido 5 en l�nea
		this.comprobarLineaDeFichas(this.FICHAS_IGUALES_LINEA,jug,true,true);

		// sino comprobar si ha robado pareja de fichas
		if (!this.fin) this.comprobarRobar2(jug,true);
	}
	
	private FichaPosicion primerMovimiento(){
		// el primer movimiento es en el centro del tablero
		int iFila = (int) Math.ceil(Partida.MAX_FILAS/2);
		int iCol = (int) Math.ceil(Partida.MAX_COLUMNAS/2);
		this.establecerJugadorTablero(new Jugada(iFila,iCol));
		
		return new FichaPosicion(iFila,iCol,this.getTurno().getFicha());
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
	
	private void establecerJugadorTablero(FichaPosicion ficha) {
		this.tablero[ficha.fila][ficha.columna]=ficha.color;
	}	
	
	public void comprobarLineaDeFichasPropia(int numFichasLinea,Jugada jug) {
		this.comprobarLineaDeFichas(numFichasLinea,jug,false,true);
	}
	
	public void comprobarLineaDeFichasContrario(int numFichasLinea,Jugada jug) {
		this.comprobarLineaDeFichas(numFichasLinea,jug,false,false);
	}	
	
	private void comprobarLineaDeFichas(int numFichasLinea,Jugada jug,boolean comprobarFinPartida, boolean propia) {
		try {
			int[][] movimientos = {{-1,-1},{-1,0},{-1,1},{0,-1}};
			int iMovimiento = 0;
			boolean fin = false;
			while (!fin && iMovimiento<movimientos.length) {
				if (propia)
					fin = (this.comprobarLineaPropia(jug,movimientos[iMovimiento][0],movimientos[iMovimiento][1])==numFichasLinea);
				else
					fin = (this.comprobarLineaContrario(jug,movimientos[iMovimiento][0],movimientos[iMovimiento][1])==numFichasLinea);
				iMovimiento++;
			}
			if (comprobarFinPartida) this.setFinGanador(fin);
		} catch (Exception e) {}
	}
	
	private int comprobarLineaPropia(Jugada jug,int mov_fila,int mov_columna){
		int numFichasIguales = 0;
		try {
			Jugada jugPos = new Jugada(jug.fila-mov_fila,jug.columna-mov_columna);
			while (this.posicionDentroTablero(jugPos) && this.posicionFichaJugador(jugPos)) {
				jugPos.fila-=mov_fila;
				jugPos.columna-=mov_columna;
			}
			jugPos.fila+=mov_fila;
			jugPos.columna+=mov_columna;
			
			this.linea = new ArrayList<Jugada>();
			while (this.posicionDentroTablero(jugPos) && this.posicionFichaJugador(jugPos)) {
				this.linea.add(new Jugada(jugPos.fila,jugPos.columna));
				numFichasIguales++;
				jugPos.fila+=mov_fila;
				jugPos.columna+=mov_columna;
			}
		} catch (Exception e) {}
		
		return numFichasIguales;
	}
	
	private int comprobarLineaContrario(Jugada jug,int mov_fila,int mov_columna){
		int numFichasIguales = 0;
		try {
			Jugada jugPos = new Jugada(jug.fila-mov_fila,jug.columna-mov_columna);
			while (this.posicionDentroTablero(jugPos) && this.posicionFichaContrario(jugPos)) {
				jugPos.fila-=mov_fila;
				jugPos.columna-=mov_columna;
			}
			jugPos.fila+=mov_fila;
			jugPos.columna+=mov_columna;
			
			this.linea = new ArrayList<Jugada>();
			while (this.posicionDentroTablero(jugPos) && this.posicionFichaContrario(jugPos)) {
				this.linea.add(new Jugada(jugPos.fila,jugPos.columna));
				numFichasIguales++;
				jugPos.fila+=mov_fila;
				jugPos.columna+=mov_columna;
			}
		} catch (Exception e) {}
		return numFichasIguales;
	}	
	
	public boolean comprobarRobar2(Jugada jug){
		return this.comprobarRobar2(jug, false);
	}
	
	public boolean comprobarRobar2(Jugada jug,boolean sumar){
		boolean robar = false;
		int iMovimiento = 0;
		while (!robar && iMovimiento<Partida.movimientosVecinos.length) {
			robar = this.comprobarRobar(jug,Partida.movimientosVecinos[iMovimiento][0],Partida.movimientosVecinos[iMovimiento][1]);
			iMovimiento++;
		}
		
		if (robar && sumar) this.sumarParejaRobada();
		
		return robar;
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
			this.jug1.sumarParejaRobada();
			this.setFinGanador(this.jug1.getParejasRobadas()==this.PAREJAS_ROBADAS);
		} else {
			this.jug2.sumarParejaRobada();
			this.setFinGanador(this.jug2.getParejasRobadas()==this.PAREJAS_ROBADAS);
		}
	}
	
	public int getNumParejasRobadas(Jugador jug){
		int num = 0;
		if (this.turno.equals(jug)) num = this.jug1.getParejasRobadas();
		else num = this.jug2.getParejasRobadas();
		return num;
	}

	public void marcarPosicion(Jugada pos) {
		this.establecerJugadorTablero(pos);
	}
	
	public void liberarPosicion(Jugada pos) {
		this.tablero[pos.fila][pos.columna] = Partida.VACIO;
	}
	
	public ArrayList<Jugada> getLineaFichas() {
		return this.linea;
	}
	
	public ArrayList<Jugada> getLineaGanadora() {
		ArrayList<Jugada> lst = this.getLineaFichas();
		return (lst!=null && lst.size()==this.FICHAS_IGUALES_LINEA?lst:null);
	}
	
	public ArrayList<Jugada> getParejaRobada(){
		ArrayList<Jugada> pareja = new ArrayList<Jugada>();
		if (this.linea.size()==4) {
			pareja.add(new Jugada(this.linea.get(1).fila,this.linea.get(1).columna));
			pareja.add(new Jugada(this.linea.get(2).fila,this.linea.get(2).columna));
		}
		return pareja;
	}
	
	public boolean posicionLibreTablero(Jugada pos) {
		return this.posicionDentroTablero(pos) && this.tablero[pos.fila][pos.columna]==Partida.VACIO;
	}
	
	public void quitarParejaRobada(){
		ArrayList<Jugada> lst = this.getParejaRobada();
		for (Jugada jug : lst) {
			this.liberarPosicion(jug);
		}
	}
	
	public String toString(){
		String cadena = "Jugador 1: " + this.jug1.toString()+"\n"+"Jugador 2: "+this.jug2.toString()+"\n";
		for (int iFila=0;iFila<Partida.MAX_FILAS;iFila++) {
			cadena += "---";
		}
		for (int iFila=0;iFila<Partida.MAX_FILAS;iFila++) {
			cadena += "\n";
			for (int iCol=0;iCol<Partida.MAX_COLUMNAS;iCol++) {
				cadena += " "+this.tablero[iFila][iCol]+" ";
				//cadena += " "+iFila+","+iCol+" ";
			}
		}
		cadena += "\n";
		for (int iFila=0;iFila<Partida.MAX_FILAS;iFila++) {
			cadena += "---";
		}
		cadena += "\n";
		
		return cadena;
	}
	
	public void establecerTablero(ArrayList<FichaPosicion> fichas) {
		if (fichas!=null) {
			for (FichaPosicion ficha : fichas) {
				this.establecerJugadorTablero(ficha);
			}
		}
	}
	
	public boolean esTurnoJugador1(){
		return this.getTurno().equals(this.jug1);
	}
	
	public Jugador getJugador1(){
		return this.jug1;
	}
	
	public Jugador getJugador2(){
		return this.jug2;
	}
}
