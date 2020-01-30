package com.uo226321.joel.gymnastics.model;

import java.io.Serializable;


public class Ejercicio implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String descripcion;
	private String consejo;
	private String foto1;
	private String foto2;
	private String musculo;
	
	
	public Ejercicio() {
		
	}
	
	public Ejercicio(String nombre, String descripcion, String consejo, String foto1, String foto2, String musculo) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.consejo = consejo;
		this.foto1 = foto1;
		this.foto2 = foto2;
		this.musculo = musculo;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getConsejo() {
		return consejo;
	}
	public void setConsejo(String consejo) {
		this.consejo = consejo;
	}
	public String getFoto1() {
		return foto1;
	}
	public void setFoto1(String foto1) {
		this.foto1 = foto1;
	}
	public String getFoto2() {
		return foto2;
	}
	public void setFoto2(String foto2) {
		this.foto2 = foto2;
	}
	public String getMusculo() {
		return musculo;
	}
	public void setMusculo(String musculo) {
		this.musculo = musculo;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((consejo == null) ? 0 : consejo.hashCode());
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((foto1 == null) ? 0 : foto1.hashCode());
		result = prime * result + ((foto2 == null) ? 0 : foto2.hashCode());
		result = prime * result + ((musculo == null) ? 0 : musculo.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Ejercicio other = (Ejercicio) obj;
		if (consejo == null) {
			if (other.consejo != null)
				return false;
		} else if (!consejo.equals(other.consejo))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (foto1 == null) {
			if (other.foto1 != null)
				return false;
		} else if (!foto1.equals(other.foto1))
			return false;
		if (foto2 == null) {
			if (other.foto2 != null)
				return false;
		} else if (!foto2.equals(other.foto2))
			return false;
		if (musculo == null) {
			if (other.musculo != null)
				return false;
		} else if (!musculo.equals(other.musculo))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
	
	

}
