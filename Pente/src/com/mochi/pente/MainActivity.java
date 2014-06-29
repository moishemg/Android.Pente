package com.mochi.pente;

import java.util.ArrayList;

import com.mochi.pente.entity.FichaPosicion;
import com.mochi.pente.entity.Jugada;
import com.mochi.pente.entity.Jugador;
import com.mochi.pente.entity.JugadorCPU;
import com.mochi.pente.entity.Partida;
import com.mochi.pente.events.OnTableroEventListener;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
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
	
	public static void mensaje(Context context,String msj,boolean parar,boolean error, int drawable){
		if (!parar) {
			Toast.makeText(context, msj, Toast.LENGTH_SHORT).show();
		} else {
			Builder alert = new AlertDialog.Builder(context);
			if (error) 	alert.setTitle("ERROR");
			else alert.setTitle("Información");
			
			if (drawable>0) alert.setIcon(drawable);
			
			alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {  
			      public void onClick(final DialogInterface dialog, final int which) {  
			        return;  
			    } });
			alert.setMessage(msj);
			alert.create().show();
		} // if
	} // mensaje

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		private Activity activity;
		public PlaceholderFragment() { }
		
		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
		    super.onViewCreated(view, savedInstanceState);
		    
		    this.activity = this.getActivity();
		    
		    ((Button)this.getActivity().findViewById(R.id.btnHumanVsHuman1)).setOnClickListener(this.btnButton());
		    ((Button)this.getActivity().findViewById(R.id.btnHumanVsHuman2)).setOnClickListener(this.btnButton());
		    ((Button)this.getActivity().findViewById(R.id.btnHumanVsCPU)).setOnClickListener(this.btnButton());
		}
		
		private OnClickListener btnButton(){
			OnClickListener handler = new OnClickListener() {
				@Override
				public void onClick(View arg) {
					Intent intent = new Intent(activity,Juego.class);
					
					intent.putExtra("jug1", 1);
					
					if (arg.getTag().equals("humanVsHuman2___")) {
						intent.putExtra("jugRemoto", 1);
					} else if (arg.getTag().equals("humanVsCPU")) {
						intent.putExtra("jugCPU", 1);	
					} else {
						intent.putExtra("jug2",1);
					}
					
					activity.startActivity(intent);
				}
			
			};
			return handler;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,	false);
			
			return rootView;
		}
	}

}
