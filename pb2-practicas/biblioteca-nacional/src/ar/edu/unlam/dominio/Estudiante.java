package ar.edu.unlam.dominio;

public class Estudiante {

	private Integer documento;
	private String nombre;
	private String apellido;
	private Integer libroPrestado;
	private Boolean libroDisponible;

	public Estudiante(Integer documento, String nombre, String apellido) {
		this.documento = documento;
		this.nombre = nombre;
		this.apellido = apellido;
		this.libroPrestado = 0;
		this.libroDisponible = true;
	}

	public Boolean adquirirLibro() {
		this.libroPrestado++;

		return this.libroDisponible = false;
	}

	public Boolean devolverLibro() {
		this.libroPrestado--;

		return this.libroDisponible = true;
	}

	public Integer getPrestarLibro() {
		return libroPrestado;
	}

	public Boolean getLibroDisponible() {
		return libroDisponible;
	}
}