package ar.edu.unlam.dominio;

import java.util.Objects;

public class Chofer {

	private String nombre;
	private Integer edad;
	private Integer documento;

	public Chofer(String nombre, Integer edad, Integer documento) {
		this.nombre = nombre;
		this.edad = edad;
		this.documento = documento;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.documento);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		Chofer other = (Chofer) obj;
		return Objects.equals(this.documento, other.documento);
	}
}