package ar.edu.unlam.dominio;

import java.time.LocalDate;
import java.util.Objects;
import java.util.TreeSet;

import ar.edu.unlam.dominio.enums.TipoSala;
import ar.edu.unlam.dominio.exception.ClienteExistenteException;

public abstract class Evento {

	private String codigoAlfanumerico;
	private LocalDate fecha;
	private String nombre;
	private TipoSala tipoSala;
	private Cliente participante;
	private TreeSet<Cliente> clientes;

	public Evento(String codigoAlfanumerico, LocalDate fecha, String nombre, TipoSala tipoSala, Cliente cliente) {
		this.codigoAlfanumerico = codigoAlfanumerico;
		this.fecha = fecha;
		this.nombre = nombre;
		this.tipoSala = tipoSala;
		this.participante = cliente;
		this.clientes = new TreeSet<>();
	}

	public abstract Boolean agregarCliente(Cliente cliente) throws ClienteExistenteException;

	public String getCodigoAlfanumerico() {
		return this.codigoAlfanumerico;
	}

	public TreeSet<Cliente> getClientes() {
		return clientes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigoAlfanumerico);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evento other = (Evento) obj;
		return Objects.equals(codigoAlfanumerico, other.codigoAlfanumerico);
	}
}