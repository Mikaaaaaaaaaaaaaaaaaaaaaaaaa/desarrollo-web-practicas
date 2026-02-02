package ar.edu.unlam.dominio;

import java.util.Objects;

public class Cliente implements Comparable<Cliente> {

	private Integer documento;
	private String nombre;
	private String apellido;
	private Integer edad;

	public Cliente(Integer documento, String nombre, String apellido, Integer edad) {
		this.documento = documento;
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
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
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(documento, other.documento);
	}

	public Integer getDocumento() {
		return documento;
	}

	@Override
	public int compareTo(Cliente cliente) {
		return this.documento.compareTo(cliente.getDocumento());
	}
}