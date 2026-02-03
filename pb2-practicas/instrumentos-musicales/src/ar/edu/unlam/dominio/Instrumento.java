package ar.edu.unlam.dominio;

import java.util.Objects;

import ar.edu.unlam.dominio.enums.EnumMarca;
import ar.edu.unlam.dominio.exception.ExceptionNumeroSerie;

public abstract class Instrumento implements Comparable<Instrumento> {
	
	private EnumMarca enumMarca;
	private String modelo;
	private Integer numeroSerie;
	private Double precioBase;
	
	public Instrumento(EnumMarca enumMarca, String modelo, Integer numeroSerie, Double precioBase) throws ExceptionNumeroSerie {
		this.enumMarca = enumMarca;
		this.modelo = modelo;
		
		if (numeroSerie >= 8) {
			this.numeroSerie = numeroSerie;
		} else {
			this.numeroSerie = 0;
			throw new ExceptionNumeroSerie("Error, número de serie no cumple con los 8 dígitos.");
		}
		
		this.precioBase = precioBase;
	}
	
	public Double getPrecioBase() {
		return precioBase;
	}

	public Integer getNumeroSerie() {
		return numeroSerie;
	}

	public abstract Double calcularPrecioFinal();
	
	@Override
	public int hashCode() {
		return Objects.hash(this.numeroSerie);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		Instrumento other = (Instrumento) obj;
		return Objects.equals(this.numeroSerie, other.numeroSerie);
	}
	
	@Override
	public int compareTo(Instrumento instrumentoDos) {
		return this.numeroSerie.compareTo(instrumentoDos.numeroSerie);
	}
}