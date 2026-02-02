package ar.edu.unlam.dominio;

public abstract class Vm {

	private Double capacidadAlmacenamiento;

	public Vm(Double capacidadAlmacenamiento) {
		this.capacidadAlmacenamiento = capacidadAlmacenamiento;
	}

	public Double getCapacidadAlmacenamiento() {
		return capacidadAlmacenamiento;
	}

	public void setCapacidadAlmacenamiento(Double capacidadAlmacenamiento) {
		this.capacidadAlmacenamiento = capacidadAlmacenamiento;
	}

	public abstract Double calcularCostoExtra();
}