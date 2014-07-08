package com.mochi.pente;

import java.util.ArrayList;

import com.mochi.pente.entity.FichaPosicion;
import com.mochi.pente.entity.Jugada;
import com.mochi.pente.entity.Jugador;
import com.mochi.pente.entity.JugadorCPU;
import com.mochi.pente.entity.JugadorRemoto;
import com.mochi.pente.entity.Partida;
import com.mochi.pente.events.OnTableroEventListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class JuegoActivity extends Activity {
	private Tablero tablero;
	private Partida partida;
	private JugadorInfo jugInfo1;
	private JugadorInfo jugInfo2;
	private Button btnJugar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pantala_juego);
		
		try {
			this.btnJugar = (Button)this.findViewById(R.id.btnJugar);
			this.btnJugar.setOnClickListener(this.btnJugarOnClickListener());
			this.iniciarJuego();
        } catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private OnClickListener btnJugarOnClickListener(){
		OnClickListener handler = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				boolean handler = new Handler().post(new Runnable() {
					@Override
					public void run() {
						iniciarJuego();
					}
				});
			}
		};
		return handler;
	}
	
	private void iniciarJuego(){
		Jugador jug1 = new Jugador(1,"Jugador Rojo",Partida.FICHA_ROJO);
		
		Jugador jug2;
		if (getIntent().getExtras().get("jugCPU")!=null) 
			jug2 =  new JugadorCPU(2,"CPU",Partida.FICHA_AZUL);
		else if (getIntent().getExtras().get("jugRemoto")!=null)
			jug2 =  new JugadorRemoto(2,"Jugador Amarillo",Partida.FICHA_AMARILLO);
		else jug2 =  new Jugador(2,"Jugador Verde",Partida.FICHA_VERDE);
    	
		btnJugar.setVisibility(View.INVISIBLE);
	
		this.tablero = (Tablero)this.findViewById(R.id.tablero);
		this.tablero.init();
		
		this.jugInfo1 = (JugadorInfo)this.findViewById(R.id.jugInfo1);
		this.jugInfo1.setJugador(jug1);
		this.jugInfo2 = (JugadorInfo)this.findViewById(R.id.jugInfo2);
		this.jugInfo2.setJugador(jug2);
		
	    this.tablero.setTableroEventListener(new OnTableroEventListener() {
			@Override
			public void onEvent(FichaPosicion ficha) {
				ficha.color=partida.getTurno().getFicha();
				dibujarFichaEnPantalla(ficha);
				//tablero.drawFicha(ficha);
				enJuego(new Jugada(ficha.fila,ficha.columna));
			}
	    });
	    
	    this.partida = new Partida(jug1,jug2);
	    
	    FichaPosicion ficha = this.partida.iniciar();
	    //this.tablero.drawFicha(ficha);
	    this.dibujarFichaEnPantalla(ficha);
	    
	    // zona test
	    ArrayList<FichaPosicion> fichasTest = new ArrayList<FichaPosicion>(); 
	    fichasTest.add(new FichaPosicion(9,8,jug2.getFicha()));
	    fichasTest.add(new FichaPosicion(8,8,jug2.getFicha()));
	    fichasTest.add(new FichaPosicion(7,8,jug2.getFicha()));
	    
	    //this.partida.establecerTablero(fichasTest);
	    //this.tablero.establecerFichas(fichasTest);
	    //
	    
	    this.mostrarTurno();
	    
	    if (this.partida.getTurno() instanceof JugadorCPU) this.turnoCPU();
	}
	
	private void mostrarTurno(){
		boolean handler = new Handler().post(new Runnable() {
			@Override
			public void run() {
			    jugInfo1.refresh(partida.esTurnoJugador1());
			    jugInfo2.refresh(!partida.esTurnoJugador1());
			}
		});
	}
	
	private void dibujarFichaEnPantalla(final FichaPosicion drawFicha) {
		boolean handler = new Handler().post(new Runnable() {
			public void run(){
				tablero.drawFicha(drawFicha);
			}
		});
	}
	
	private void turnoCPU(){
		ProgressDialog pd = ProgressDialog.show(this,"Pente","Pensando siguiente jugada...");
		pd.show();
		try {
			Thread.sleep(1000);
		} catch (Exception d) {}
		boolean handler = new Handler().post(new Runnable() {
			public void run(){
				Jugada jug = ((JugadorCPU)partida.getTurno()).siguienteMovimiento(partida);
				FichaPosicion ficha = new FichaPosicion(jug.fila,jug.columna,partida.getTurno().getFicha());
				dibujarFichaEnPantalla(ficha);
				//this.tablero.drawFicha(ficha);
				enJuego(jug);
			}
		});
		pd.dismiss();
	}
	
	private void enJuego(Jugada jug){
		if (jug.valida()) {
			this.partida.comprobarJugada(jug);
			if (this.partida.getParejaRobada().size()>0) {
				this.tablero.eliminarFichas(this.partida.getParejaRobada());
				this.partida.quitarParejaRobada();
			} 
			if (!partida.esFinPartida()) {
				this.partida.siguienteTurno();
				this.mostrarTurno();
			}
				
		} else {
			this.partida.rendirse();
		}
		
		if (this.partida.esFinPartida()) this.finDePartida();
		else if (this.partida.getTurno() instanceof JugadorCPU) this.turnoCPU();
	}
	
	private void finDePartida(){
		if (this.partida.esFinPartidaGanadora()) {
			if (this.partida.getLineaGanadora()!=null) this.tablero.marcarFichas(this.partida.getLineaGanadora());
			if (this.partida.esTurnoJugador1()) {
				this.jugInfo1.ganador();
				this.jugInfo2.perdedor();
			} else {
				this.jugInfo1.perdedor();
				this.jugInfo2.ganador();
			}
			this.mostrarMensaje("Ganador : " + this.partida.getTurno().getNombre());
		} else {
			this.mostrarMensaje("Fin de partida");
		}
		
		this.tablero.setTableroEventListener(null);
		this.btnJugar.setVisibility(View.VISIBLE);
	}
	
	private void mostrarMensaje(String mensaje) {
		//MainActivity.mensaje(this, mensaje, true, false, R.drawable.ic_launcher);
	}
	
}
