package com.mochi.pente.entity;

import java.util.ArrayList;
import com.mochi.pente.entity.Partida;
import com.mochi.pente.ia.Evaluador;

public class JugadorCPU extends Jugador {
	public Jugada siguienteMovimiento(int[][] tablero) {
		// generamos un movimiento en automático
		ArrayList<Jugada> posibles = this.encontrarPosiblesHuecos(tablero);
		int peso = 0;
		Evaluador eval = new Evaluador();
		Jugada jugada = new Jugada(-1,-1);
		for (Jugada pos : posibles) {
			int newPeso = eval.evaluar(tablero, pos);
			if (newPeso>peso) {
				newPeso = peso;
				jugada = pos;
			}
		}
		
		return jugada;
	}
	
	private ArrayList<Jugada> encontrarPosiblesHuecos(int[][] tablero){
		ArrayList<Jugada> huecos = new ArrayList<Jugada>();
		for (int iFila=0;iFila<Partida.MAX_FILAS;iFila++) {
			for (int iCol=0;iCol<Partida.MAX_COLUMNAS;iCol++) {
				Jugada posibleHueco = new Jugada(iFila,iCol);
				if (Partida.posicionDentroTablero(posibleHueco) && tablero[iFila][iCol]==Partida.VACIO) {
					// comprobar sus ochos vecinos
					boolean dentro = false;
					int i = 0;
					while (!dentro && i<Partida.movimientosVecinos.length) {
						Jugada newPos = new Jugada(iFila+Partida.movimientosVecinos[i][0],iCol+Partida.movimientosVecinos[i][1]);
						if (this.comprobarPosicion(tablero,newPos.fila,newPos.columna) && !huecos.contains(newPos)) {
							huecos.add(newPos);
						}
						i++;
					}
				} // for
			} // for
		}
		return huecos;
	}
	
	private boolean comprobarPosicion(int[][] tablero,int mov_fila,int mov_col) {
		return (Partida.posicionDentroTablero(new Jugada(mov_fila,mov_col)) && tablero[mov_fila][mov_col]==Partida.VACIO);
	}
	
	
}
