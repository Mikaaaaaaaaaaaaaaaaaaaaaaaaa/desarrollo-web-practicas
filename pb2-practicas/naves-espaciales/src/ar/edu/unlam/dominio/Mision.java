package ar.edu.unlam.dominio;

import java.util.Objects;

public class Mision {

	private Integer id;
	private static Integer contadorId = 0;
	private Nave nave;
	private Integer horas;

	public Mision(Nave nave, Integer horas) {
		this.nave = nave;
		this.horas = horas;
		this.contadorId++;
		this.id = contadorId;
	}

	public Nave getNave() {
		return nave;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mision other = (Mision) obj;
		return Objects.equals(this.id, other.id);
	}
}