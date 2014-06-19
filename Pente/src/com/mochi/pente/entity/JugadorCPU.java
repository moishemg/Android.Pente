package com.mochi.pente.entity;

import java.util.ArrayList;

import com.mochi.pente.entity.Partida;
import com.mochi.pente.ia.Evaluador;

public class JugadorCPU extends Jugador {
	public JugadorCPU(long id, String nombre, int ficha) {
		super(id, nombre, ficha);
	}

	public Jugada siguienteMovimiento(Partida partida) {
		// generamos un movimiento en automático
		ArrayList<Jugada> posibles = this.encontrarPosiblesHuecos(partida);
		int peso = 0;
		Evaluador eval = new Evaluador();
		Jugada jugada = new Jugada(-1,-1);
		if (posibles.size()>0) jugada = posibles.get(0);
		for (Jugada pos : posibles) {
			int newPeso = eval.evaluar(partida, pos);
			if (newPeso>peso) {
				peso = newPeso;
				jugada = pos;
			}
		}
		
		return jugada;
	}
	
	private ArrayList<Jugada> encontrarPosiblesHuecos(Partida partida){
		ArrayList<Jugada> huecos = new ArrayList<Jugada>();
		for (int iFila=0;iFila<Partida.MAX_FILAS;iFila++) {
			for (int iCol=0;iCol<Partida.MAX_COLUMNAS;iCol++) {
				Jugada posibleHueco = new Jugada(iFila,iCol);
				if (!partida.posicionLibreTablero(posibleHueco)) {
					// comprobar sus ochos vecinos
					int i = 0;
					while (i<Partida.movimientosVecinos.length) {
						Jugada newPos = new Jugada(iFila+Partida.movimientosVecinos[i][0],iCol+Partida.movimientosVecinos[i][1]);
						if (partida.posicionLibreTablero(new Jugada(newPos.fila,newPos.columna)) && !huecos.contains(newPos)) {
							huecos.add(newPos);
						}
						i++;
					}
				} // for
			} // for
		}
		return huecos;
	}
	
	
}
