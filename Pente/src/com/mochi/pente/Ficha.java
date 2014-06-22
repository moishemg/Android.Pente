package com.mochi.pente;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class Ficha extends View {
	private int tipo;
	public Ficha(Context context) {
		super(context);
		this.tipo = 0;
	}
	public Ficha(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.Ficha, 0, 0);

	    try {
	       this.tipo = a.getInteger(R.styleable.Ficha_tipo, 0);
	    } finally {
	       a.recycle();
	    }
	}
	
	public int getTipo(){
		return this.tipo;
	}
	
	public void setTipo(int newTipo) {
		this.tipo = newTipo;
		this.invalidate();
		this.requestLayout();
	}
	
	protected void onDraw(Canvas canvas) {
		   super.onDraw(canvas);
		   int width = this.getWidth();
		   int height = this.getHeight();
		   Paint pincel1 = new Paint();
		   switch (this.tipo) {
		   		case 1 : pincel1.setARGB(255, 255, 0, 0); break;
		   		case 2 : pincel1.setARGB(255, 255, 255, 0); break;
		   		case 3 : pincel1.setARGB(255, 0, 255, 0); break;
		   		case 4 : pincel1.setARGB(255, 0, 0, 255); break;
		   		default : pincel1.setARGB(255, 0, 0, 0); break;
		   }
           
		   
           pincel1.setStyle(Style.FILL_AND_STROKE);
		   
           if (this.tipo>0 && this.tipo<5)
              canvas.drawCircle(width/2,height/2 , width/2, pincel1);
	}

}
