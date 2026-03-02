package ar.edu.unlam.dominio;

public class ClientePremium extends Cliente {

	private String nombre;
	private String apellido;
	private Integer puntosFidelidad;

	public ClientePremium(String documento, String nombre, String apellido, Integer puntosFidelidad) {
		super(documento);
		this.nombre = nombre;
		this.apellido = apellido;
		this.puntosFidelidad = puntosFidelidad;
	}
}