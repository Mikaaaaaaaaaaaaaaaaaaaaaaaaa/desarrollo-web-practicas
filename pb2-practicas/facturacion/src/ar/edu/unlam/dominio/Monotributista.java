package ar.edu.unlam.dominio;

public class Monotributista extends Cliente {

	private String apellido;
	private String nombre;
	private String nombreFantasia;

	public Monotributista(String codigoUnico, String apellido, String nombre, String nombreFantasia) {
		super(codigoUnico);
		this.apellido = apellido;
		this.nombre = nombre;
		this.nombreFantasia = nombreFantasia;
	}

	public String getCuit() {
		return super.getCodigoUnico();
	}

	public String getApellido() {
		return this.apellido;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getNombreFantasia() {
		return this.nombreFantasia;
	}
}