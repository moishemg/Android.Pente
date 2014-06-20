package com.mochi.pente;

import com.mochi.pente.entity.Jugada;
import com.mochi.pente.entity.Jugador;
import com.mochi.pente.entity.JugadorCPU;
import com.mochi.pente.entity.Partida;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

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
		
		public PlaceholderFragment() { }
		
		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
		    super.onViewCreated(view, savedInstanceState);
		    this.test();
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
