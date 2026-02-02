package ar.edu.unlam.dominio;

public abstract class Libro {

	private Integer codigo;
	private String nombre;
	private String autor;

	public Libro(Integer codigo, String nombre, String autor) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.autor = autor;
	}
}