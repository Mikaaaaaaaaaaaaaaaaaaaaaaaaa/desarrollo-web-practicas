package ar.edu.unlam.dominio;

import java.time.LocalDate;

import ar.edu.unlam.dominio.enums.TipoSala;
import ar.edu.unlam.dominio.exception.ClienteExistenteException;

public class EventoConferencia extends Evento {

	private String temaTratar;

	public EventoConferencia(String codigo, LocalDate fecha, String nombre, TipoSala tipoSala, Cliente cliente, String temaTratar) {
		super(codigo, fecha, nombre, tipoSala, cliente);
		this.temaTratar = temaTratar;
	}

	@Override
	public Boolean agregarCliente(Cliente cliente) throws ClienteExistenteException {
		if (super.getClientes().add(cliente) == false) {
			throw new ClienteExistenteException("Error, cliente duplicado.");
		}

		return true;
	}
}