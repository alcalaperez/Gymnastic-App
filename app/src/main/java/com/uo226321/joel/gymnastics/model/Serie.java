package com.uo226321.joel.gymnastics.model;

import java.io.Serializable;

public class Serie implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int diaId;
	private String ejercicioId;
	private Ejercicio ejercicio;
	private int series;
	private int repeticiones;
	private int descanos;
	
	
	public Serie() {

	}
	
	public Serie(int id, int diaId, String ejercicioId, int series, int repeticiones, int descanos) {
		this.id = id;
		this.diaId = diaId;
		this.ejercicioId = ejercicioId;
		this.series = series;
		this.repeticiones = repeticiones;
		this.descanos = descanos;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDiaId() {
		return diaId;
	}
	public void setDiaId(int diaId) {
		this.diaId = diaId;
	}
	public String getEjercicioId() {
		return ejercicioId;
	}
	public void setEjercicioId(String ejercicioId) {
		this.ejercicioId = ejercicioId;
	}
	public int getSeries() {
		return series;
	}
	public void setSeries(int series) {
		this.series = series;
	}
	public int getRepeticiones() {
		return repeticiones;
	}
	public void setRepeticiones(int repeticiones) {
		this.repeticiones = repeticiones;
	}
	public int getDescanos() {
		return descanos;
	}
	public void setDescanos(int descanos) {
		this.descanos = descanos;
	}
	public Ejercicio getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(Ejercicio ejercicio) {
		this.ejercicio = ejercicio;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + descanos;
		result = prime * result + diaId;
		result = prime * result
				+ ((ejercicioId == null) ? 0 : ejercicioId.hashCode());
		result = prime * result + id;
		result = prime * result + repeticiones;
		result = prime * result + series;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Serie other = (Serie) obj;
		if (descanos != other.descanos)
			return false;
		if (diaId != other.diaId)
			return false;
		if (ejercicioId == null) {
			if (other.ejercicioId != null)
				return false;
		} else if (!ejercicioId.equals(other.ejercicioId))
			return false;
		if (id != other.id)
			return false;
		if (repeticiones != other.repeticiones)
			return false;
		if (series != other.series)
			return false;
		return true;
	}
	
	

}
