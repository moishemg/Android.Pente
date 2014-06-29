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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class Tablero extends View implements OnTableroEventListener {
	OnTableroEventListener onEventListener;
	
	private int filas;
	private int columnas;
	private int pasoHor;
	private int pasoVer;
	
	private ArrayList<FichaPosicion> fichas;
	private ArrayList<Jugada> marcas;
	
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
	
	public void init() {
		this.marcas = null;
		this.fichas = null;
		this.invalidate();
		this.requestLayout();
	}
	

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//int widthMode = MeasureSpec.getMode(widthMeasureSpec);
	    int widthSize = MeasureSpec.getSize(widthMeasureSpec);
	    //int heightMode = MeasureSpec.getMode(heightMeasureSpec);
	    int heightSize = MeasureSpec.getSize(heightMeasureSpec);
	    
	    int width = (widthSize<heightSize?widthSize:heightSize);
	    int height = width;
	    this.setMeasuredDimension(width, height);
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
	
	private int ult=0;
	private void drawFichas(Canvas canvas) {
		if (this.fichas!=null && this.fichas.size()>0) {
			Paint pincel = new Paint();
			pincel.setStyle(Style.FILL_AND_STROKE);
			
			Paint border = new Paint();
			border.setStyle(Style.STROKE);
			border.setStrokeWidth(3);
			
			Paint centro = new Paint();
			centro.setStyle(Style.FILL);
			centro.setStrokeWidth(3);
			
			for (FichaPosicion ficha : this.fichas) {
				switch (ficha.color) {
					case 1: pincel.setARGB(200, 255, 0, 0); 
							centro.setARGB(255, 255, 0, 0);
							border.setARGB(128, 255, 0, 0);
							break;
					case 2: pincel.setARGB(200, 255, 255, 0);
							centro.setARGB(255, 255, 255, 0); 
							border.setARGB(128, 255, 255, 0);
							break;
					case 3: pincel.setARGB(200, 0, 0, 255); 
							centro.setARGB(255, 0, 0, 255); 
							border.setARGB(200, 0, 0, 255); 
							break;
					case 4: pincel.setARGB(200, 0, 255, 0); 
							centro.setARGB(255, 0, 255, 0);
							border.setARGB(128, 0, 255, 0); 
							break;
					case 10: pincel.setARGB(200, 255, 0, 153); 
							 centro.setARGB(255, 255, 0, 153); 
							 border.setARGB(128, 255, 0, 153); 
							 break; // especial ganador
					default: pincel.setARGB(0, 255, 255, 255); 
					         centro.setARGB(0, 255, 255, 255); 
							 border.setARGB(0, 255, 255, 255); 
							 break;
				}
				
				canvas.drawCircle(((ficha.columna+1)*this.pasoVer), ((ficha.fila+1)*this.pasoHor),  this.pasoVer/2, pincel);
				//canvas.drawCircle(((ficha.fila+1)*this.pasoHor), ((ficha.columna+1)*this.pasoVer), this.pasoVer/2, border);
				canvas.drawCircle(((ficha.columna+1)*this.pasoVer), ((ficha.fila+1)*this.pasoHor),  this.pasoVer/4, centro);
			}
			
			if (this.marcas!=null && this.marcas.size()>0) {
				try { 
					pincel.setARGB(255, 255, 0, 153);
					pincel.setStyle(Style.STROKE);
					pincel.setStrokeWidth(6);
					/*
					for (Jugada pos : this.marcas) {
						canvas.drawCircle(((pos.fila+1)*this.pasoHor), ((pos.columna+1)*this.pasoVer), (this.pasoVer/2)+1, pincel);
					}
					*/
					this.ult = (this.ult>=this.marcas.size()?this.marcas.size()-1:this.ult);
					
					int posX = (this.marcas.get(0).columna+1)*this.pasoVer;
					int posY = (this.marcas.get(0).fila+1)*this.pasoHor;
					int posXFin = (this.marcas.get(this.ult).columna+1)*this.pasoVer;
					int posYFin = (this.marcas.get(this.ult).fila+1)*this.pasoHor;

					canvas.drawLine(posX, posY, posXFin,posYFin,  pincel);
					
					pincel.setARGB(255, 255, 0, 204);
					pincel.setStrokeWidth(2);
					canvas.drawLine(posX, posY, posXFin,posYFin, pincel);
					this.ult+=1;
					
					if (this.ult<this.marcas.size()) this.postInvalidateDelayed(20);
				} catch (Exception e) { 
					Log.e("Pente", e.toString());
				}
			}
			
		}
	}
	
	public void establecerFichas(ArrayList<FichaPosicion> fichas) {
		if (this.fichas==null) this.fichas = new ArrayList<FichaPosicion>();
		for (FichaPosicion ficha : fichas) {
			this.fichas.add(ficha);
		}
		this.invalidate();
		this.requestLayout();
	}
	
	public void marcarFichas(ArrayList<Jugada> posiciones) {
		this.ult = 0;
		this.marcas = posiciones;

		this.invalidate();
		this.requestLayout();
	}
	
	public void eliminarFichas(ArrayList<Jugada> posiciones) {
		if (this.fichas!=null) {
			try {
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
			} catch (Exception e) {}
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// sacamos las coordenadas
		int posCol = (int)event.getX();
		int posFila = (int)event.getY();
		
		// posicionamos según la cuadricula
		int col = (posCol / this.pasoHor) + (posCol % this.pasoHor>this.pasoHor/2?1:0);
		int fila = (posFila / this.pasoVer) + (posFila % this.pasoVer>this.pasoVer/2?1:0);
		
		// tope por debajo
		if (col>0) col-=1;
		if (fila>0) fila-=1;
		
		// tope por arriba
		if (col>this.columnas) col=-1;
		if (fila>this.filas) fila=-1;
		
		if (this.onEventListener!=null && col>=0 && fila>=0) {
			FichaPosicion ficha = new FichaPosicion(fila,col,0);
			if (this.fichas!=null && !this.fichas.contains(ficha) || this.fichas==null) onEventListener.onEvent(ficha);
		}
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
