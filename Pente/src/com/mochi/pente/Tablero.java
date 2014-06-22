package com.mochi.pente;

import java.util.ArrayList;

import com.mochi.pente.entity.FichaPosicion;
import com.mochi.pente.entity.Jugada;
import com.mochi.pente.events.OnTableroEventListener;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Tablero extends View implements OnTableroEventListener {
	OnTableroEventListener onEventListener;
	
	private int filas;
	private int columnas;
	private int pasoHor;
	private int pasoVer;
	
	private ArrayList<FichaPosicion> fichas;
	
	public Tablero(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.Tablero, 0, 0);

	    try {
	       this.filas = a.getInteger(R.styleable.Tablero_filas, 19);
	       this.columnas = a.getInteger(R.styleable.Tablero_columnas, 19);
	    } finally {
	       a.recycle();
	    }
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		int width = this.getWidth();
		int height = this.getHeight();
		
		this.pasoHor = (width/this.columnas);
		this.pasoVer = (height/this.filas);
		if (this.pasoHor>this.pasoVer) this.pasoHor = this.pasoVer;
		else if (this.pasoVer>this.pasoHor) this.pasoVer = this.pasoHor;
		
		int posX = this.pasoHor;
		int posY = this.pasoVer;
		
		Paint pincel = new Paint();
		pincel.setARGB(255, 0, 0, 0);
		for (int iFilas=0;iFilas<this.filas;iFilas++) {
			canvas.drawLine(posX, 0, posX, width, pincel);
			posX+=this.pasoHor;
		}
		
		for (int iCol=0;iCol<this.columnas;iCol++) {
			canvas.drawLine(0, posY, height, posY , pincel);
			posY+=this.pasoVer;
		}
		
		// dibujar las fichas
		this.drawFichas(canvas);
	}
	
	private void drawFichas(Canvas canvas) {
		if (this.fichas!=null && this.fichas.size()>0) {
			Paint pincel = new Paint();
			pincel.setStyle(Style.FILL_AND_STROKE);
			for (FichaPosicion ficha : this.fichas) {
				switch (ficha.color) {
					case 1: pincel.setARGB(255, 255, 0, 0); break;
					case 2: pincel.setARGB(255, 255, 255, 0); break;
					case 3: pincel.setARGB(255, 0, 255, 0); break;
					case 4: pincel.setARGB(255, 0, 0, 255); break;
					case 10: pincel.setARGB(255, 255, 0, 153); break; // especial ganador
					default: pincel.setARGB(0, 255, 255, 255); break;
				}
				
				canvas.drawCircle(((ficha.fila+1)*this.pasoHor), ((ficha.columna+1)*this.pasoVer), this.pasoVer/2, pincel);
			}
			
		}
	}
	
	public void marcarFichas(ArrayList<Jugada> posiciones) {
		if (this.fichas!=null) {			
			for (Jugada jug : posiciones) {
				boolean encontrado = false;
				int i =0;
				while (!encontrado && i<this.fichas.size()) {
					if (this.fichas.get(i).fila==jug.fila && this.fichas.get(i).columna==jug.columna) {
						encontrado = true;
						this.fichas.get(i).color = 10;
					}
					
					i++;
				}
			}
			
			this.invalidate();
			this.requestLayout();
		}
	}
	
	public void eliminarFichas(ArrayList<Jugada> posiciones) {
		if (this.fichas!=null) {
			//this.fichas.remove(posiciones.get(0));
			//this.fichas.remove(posiciones.get(1));
			
			for (Jugada jug : posiciones) {
				boolean eliminado = false;
				int i =0;
				while (!eliminado && i<this.fichas.size()) {
					if (this.fichas.get(i).fila==jug.fila && this.fichas.get(i).columna==jug.columna) {
						eliminado = true;
						this.fichas.remove(i);
					}
					
					i++;
				}
			}
			
			this.invalidate();
			this.requestLayout();
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// sacamos las coordenadas
		int posX = (int)event.getX();
		int posY = (int)event.getY();
		
		// posicionamos según la cuadricula
		int x = (posX / this.pasoHor) + (posX % this.pasoHor>this.pasoHor/2?1:0);
		int y = (posY / this.pasoVer) + (posY % this.pasoVer>this.pasoVer/2?1:0);
		
		// tope por debajo
		if (x>0) x-=1;
		if (y>0) y-=1;
		
		// tope por arriba
		if (x>this.filas) x=this.filas-1;
		if (y>this.columnas) y=this.columnas-1;
		
		if (this.onEventListener!=null) onEventListener.onEvent(new FichaPosicion(x,y,0));
	    return super.onTouchEvent(event);
	}
	
	public void setTableroEventListener(OnTableroEventListener onTableroEventListener) {
		this.onEventListener=onTableroEventListener;
	}

	public void drawFicha(FichaPosicion ficha) {
		if (this.fichas==null) this.fichas = new ArrayList<FichaPosicion>();
		
		if (!this.fichas.contains(ficha)) this.fichas.add(ficha);
		
		this.invalidate();
		this.requestLayout();
	}

	@Override
	public void onEvent(FichaPosicion ficha) { }
}
