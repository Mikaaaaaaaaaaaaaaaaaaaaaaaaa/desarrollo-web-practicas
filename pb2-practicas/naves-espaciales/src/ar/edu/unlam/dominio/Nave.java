package ar.edu.unlam.dominio;

public abstract class Nave implements Comparable<Nave> {

	private String id;
	private String nombre;
	private Double capacidadMaximaCombustible;
	private Double consumoBase;

	public Nave(String id, String nombre, Double capacidadMaximaCombustible) {
		this.id = id;
		this.nombre = nombre;
		this.capacidadMaximaCombustible = capacidadMaximaCombustible;
		this.consumoBase = 5.0;
	}

	abstract Double calcularConsumo(Integer horas);

	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public Double getCapacidadMaximaCombustible() {
		return capacidadMaximaCombustible;
	}

	public double getConsumoBase() {
		return consumoBase;
	}

	@Override
	public int compareTo(Nave naveDos) {
		return this.id.compareTo(naveDos.getId());
	}
}