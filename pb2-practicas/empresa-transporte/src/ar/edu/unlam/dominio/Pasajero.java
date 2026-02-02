package ar.edu.unlam.dominio;

public class Pasajero implements Comparable<Pasajero> {

	private String nombre;
	private Integer edad;
	private Integer documento;

	public Pasajero(String nombre, Integer edad, Integer documento) {
		this.nombre = nombre;
		this.edad = edad;
		this.documento = documento;
	}

	@Override
	public int compareTo(Pasajero pasajeroDos) {
		return this.edad.compareTo(pasajeroDos.edad);
	}
}