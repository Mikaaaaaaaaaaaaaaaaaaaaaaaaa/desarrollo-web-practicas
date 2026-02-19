package ar.edu.unlam.dominio;

public class ConsumidorFinal extends Cliente {

	private String apellido;
	private String nombre;

	public ConsumidorFinal(String codigoUnico, String apellido, String nombre) {
		super(codigoUnico);
		this.apellido = apellido;
		this.nombre = nombre;
	}
}