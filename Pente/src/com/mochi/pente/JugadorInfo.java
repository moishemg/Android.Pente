package com.mochi.pente;

import com.mochi.pente.entity.Jugador;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class JugadorInfo extends RelativeLayout {
	private Jugador jugador;
	private RelativeLayout view;
	private LayoutInflater mInflater;
	public JugadorInfo(Context context) {
		super(context);
		mInflater = LayoutInflater.from(context);
		this.view = (RelativeLayout)mInflater.inflate(R.layout.jugador_info, this, true);
	}

    public JugadorInfo(Context context, AttributeSet attrs) {
    	super(context, attrs);
    	mInflater = LayoutInflater.from(context);
    	this.view = (RelativeLayout)mInflater.inflate(R.layout.jugador_info, this, true); 
    }
   
   public void setJugador(Jugador jugador) {
	   this.jugador = jugador;
   }
   

   
   public void refresh(boolean turno){
	   ((TextView)this.view.findViewById(R.id.lbNombre)).setText(this.jugador.getNombre());
	   ((TextView)this.view.findViewById(R.id.lbParejasRobadas)).setText(this.jugador.getParejasRobadas()+"");
	   
	   TextView txt = (TextView)this.view.findViewById(R.id.txtTurno);
	   txt.setVisibility(View.INVISIBLE);
	   int border = R.drawable.border_jugador_info;
	   if (turno) {
		   border = R.drawable.border_jugador_turno;
		   txt.setVisibility(View.VISIBLE);
		   txt.setText(this.getContext().getString(R.string.tu_turno));
	   }
	   
	   this.setBorder(border);
   }
   
   public void ganador(){
	   this.setBorder(R.drawable.border_jugador_ganador);
	   TextView txt = (TextView)this.view.findViewById(R.id.txtTurno);
	   txt.setVisibility(View.VISIBLE);
	   txt.setText(this.getContext().getString(R.string.ganador));
   }
   
   public void perdedor(){
	   this.setBorder(R.drawable.border_jugador_perdedor);
	   TextView txt = (TextView)this.view.findViewById(R.id.txtTurno);
	   txt.setVisibility(View.INVISIBLE);
   }
   
   @SuppressLint("NewApi")
   private void setBorder(int border) {
	   if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
		   this.view.setBackgroundDrawable(this.getContext().getResources().getDrawable(border));
	   } else {
		   this.view.setBackground(this.getContext().getResources().getDrawable(border));
	   }
   }
}
