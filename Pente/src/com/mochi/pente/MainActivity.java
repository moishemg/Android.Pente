package com.mochi.pente;

import com.mochi.pente.entity.FichaPosicion;
import com.mochi.pente.entity.Jugada;
import com.mochi.pente.entity.Jugador;
import com.mochi.pente.entity.JugadorCPU;
import com.mochi.pente.entity.Partida;
import com.mochi.pente.events.OnTableroEventListener;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		private TextView txt;
		private Tablero tablero;
		private Partida partida;
		
		public PlaceholderFragment() { }
		
		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
		    super.onViewCreated(view, savedInstanceState);
		    this.tablero = (Tablero)this.getView().findViewById(R.id.tablero);
		    this.txt = (TextView)this.getView().findViewById(R.id.txtText);
		    this.tablero.setTableroEventListener(new OnTableroEventListener() {
				@Override
				public void onEvent(FichaPosicion ficha) {
					txt.setText(partida.getTurno().getNombre()+": "+ficha.fila+"-"+ficha.columna);
					ficha.color=partida.getTurno().getFicha();
					tablero.drawFicha(ficha);
					enJuego(new Jugada(ficha.fila,ficha.columna));
				}
		    });
		    
		    
		    this.partida = new Partida(new Jugador(1,"Jugador 1",Partida.FICHA_ROJO),new Jugador(2,"Jugador 2",Partida.FICHA_VERDE));
		    this.partida.iniciar();
		    this.txt.setText("Turno : " + partida.getTurno().getNombre());
		    
		    //this.test();
		}
		
		private void enJuego(Jugada jug){
			if (jug.valida()) {
				this.partida.comprobarJugada(jug);
				if (this.partida.getParejaRobada().size()>0) {
					this.tablero.eliminarFichas(this.partida.getParejaRobada());
				} 
				if (!partida.esFinPartida()) {
					this.partida.siguienteTurno();
					this.txt.setText("Turno : " + partida.getTurno().getNombre());
				}
			} else {
				this.partida.rendirse();
			}
			
			if (this.partida.esFinPartida()) this.finDePartida();
		}
		
		private void finDePartida(){
			if (this.partida.esFinPartidaGanadora()) {
				if (this.partida.getLineaGanadora()!=null) {
					this.tablero.marcarFichas(this.partida.getLineaGanadora());
				}
				this.txt.setText("Ganador : " + this.partida.getTurno().getNombre());
			} else {
				this.txt.setText("Fin de partida");
			}
			
			this.tablero.setTableroEventListener(null);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,	false);
			
			return rootView;
		}
		
		private void test(){
			Jugador jug1 = new JugadorCPU(1,"Jugador 1",Partida.FICHA_ROJO);
			Jugador jug2 = new JugadorCPU(2,"Jugador 2",Partida.FICHA_AMARILLO);
			
			Partida partida = new Partida(jug1,jug2);
			TextView txt = (TextView)this.getView().findViewById(R.id.txtText);
			
			Jugador jugador = partida.iniciar();
			while (!partida.esFinPartida()) {
				Jugada jug = ((JugadorCPU)jugador).siguienteMovimiento(partida);
				if (jug.valida()) {
					partida.comprobarJugada(jug);
					
					jugador = partida.siguienteTurno();
				} else {
					partida.rendirse();
				}
				
			}
			
			txt.setText("Fin de Partida\n"+partida.toString());
			if (partida.esFinPartidaGanadora()) {
				txt.setText(txt.getText()+"\nEl ganador es: "+partida.getTurno());
			}
			
			/*Jugador jugador = partida.getTurno();
			
			partida.comprobarJugada(new Jugada(1,1));
			partida.comprobarJugada(new Jugada(1,3));
			partida.comprobarJugada(new Jugada(1,4));
			
			Jugada jug = ((JugadorCPU)jug2).siguienteMovimiento(partida);
			partida.comprobarJugada(jug);
			
			jugador = partida.siguienteTurno();
			partida.comprobarJugada(new Jugada(2,1));
			partida.comprobarJugada(new Jugada(2,2));
			partida.comprobarJugada(new Jugada(2,3));
			
			String tab = partida.toString();*/
			
			
			
		}
	}

}
