package ar.edu.unlam.dominio;

import java.util.Objects;

public class Cliente implements Comparable<Cliente> {

	private Integer documento;
	private String apellido;
	private String nombre;

	public Cliente(Integer documento, String apellido, String nombre) {
		this.documento = documento;
		this.apellido = apellido;
		this.nombre = nombre;
	}

	public Integer getDocumento() {
		return this.documento;
	}

	public String getApellido() {
		return this.apellido;
	}

	@Override
	public int hashCode() {
		return Objects.hash(documento);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass()) // ¡ACORDATE DE ESTO!.
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(documento, other.documento);
	}

	@Override
	public int compareTo(Cliente o) {
		return this.documento.compareTo(o.getDocumento());
	}
}