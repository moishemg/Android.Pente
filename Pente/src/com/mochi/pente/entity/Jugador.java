package com.mochi.pente.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Jugador implements Parcelable {
	private long id;
	private String nombre;
	private int ficha;
	private int parejasRobadas;
	
	public Jugador(long id,String nombre,int ficha){
		this.id = id;
		this.ficha = ficha;
		this.nombre = nombre;
		this.parejasRobadas = 0;
	}
	
	public Jugador(Parcel in){
		this.readToParcel(in);
	}
	
	public long getId(){
		return this.id;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public int getFicha(){
		return this.ficha;
	}
	
	public int getParejasRobadas(){
		return this.parejasRobadas;
	}
	
	public void iniciarParejasRobadas() {
		this.parejasRobadas = 0;
	}
	
	public void sumarParejaRobada() {
		this.parejasRobadas++;
	}
	
	public boolean equals(Object o) {
		return (o instanceof Jugador) && ((Jugador)o).getNombre().equals(this.getNombre());
	}
	
	public Jugada siguienteMovimiento() {
		// se debe coger la jugada de la pantalla
		return new Jugada();
	}
	
	public String toString() {
		return this.getNombre();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int arg1) {
		out.writeLong(this.getId());
		out.writeString(this.getNombre());
		out.writeInt(this.getFicha());
		out.writeInt(this.getParejasRobadas());
	}
	
	public void readToParcel(Parcel in){
		this.id = in.readLong();
		this.nombre = in.readString();
		this.ficha = in.readInt();
		this.parejasRobadas = in.readInt();
	}
	
	public static final Jugador.Creator<Jugador> CREATOR = 
			new Jugador.Creator<Jugador>() {
		      public Jugador createFromParcel(Parcel in) {
		              return new Jugador(in);
		      }

		      public Jugador[] newArray(int size) {
		              return new Jugador[size];
		      }
		  };
	
}
