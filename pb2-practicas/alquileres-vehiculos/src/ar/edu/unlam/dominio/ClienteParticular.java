package ar.edu.unlam.dominio;

public class ClienteParticular extends Cliente {

	private String documento;
	private String nombre;
	private String apellido;

	public ClienteParticular(String documento, String nombre, String apellido) {
		super(documento);
		this.nombre = nombre;
		this.apellido = apellido;
	}
}