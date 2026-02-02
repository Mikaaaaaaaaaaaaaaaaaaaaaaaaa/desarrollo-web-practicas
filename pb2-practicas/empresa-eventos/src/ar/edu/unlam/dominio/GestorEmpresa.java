package ar.edu.unlam.dominio;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import ar.edu.unlam.dominio.exception.ClienteExistenteException;
import ar.edu.unlam.dominio.exception.EventoDuplicadoException;
import ar.edu.unlam.dominio.exception.EventoNoEncontradoException;

public class GestorEmpresa {

	private TreeSet<Cliente> clientes;
	private TreeSet<Evento> eventos;

	public GestorEmpresa() {
		this.clientes = new TreeSet<>(new ClientePorApellidoComparator());
		this.eventos = new TreeSet<>(new EventoPorCodigoComparator());
	}

	public Boolean agregarCliente(Cliente cliente) throws ClienteExistenteException {
		if (this.clientes.add(cliente) == false) {
			throw new ClienteExistenteException("Error, cliente duplicado.");
		}

		return true;
	}

	public Boolean agregarEvento(Evento evento) throws EventoDuplicadoException {
		if (this.eventos.add(evento) == false) {
			throw new EventoDuplicadoException("Error, evento duplicado.");
		}

		return true;
	}

	public Boolean agregarClienteEvento(String codigo, Cliente cliente) throws ClienteExistenteException, EventoNoEncontradoException {
		Evento eventoEncontrado = buscarEventoPorCodigoAlfanumerico(codigo);
		if (eventoEncontrado == null) {
			throw new EventoNoEncontradoException("Error, evento no encontrado.");
		}

		if (eventoEncontrado.getClientes().contains(cliente)) {
			throw new ClienteExistenteException("Error, cliente duplicado.");
		}

		return eventoEncontrado.agregarCliente(cliente);
	}

	public Evento buscarEventoPorCodigoAlfanumerico(String codigoAlfanumerico) {
		for (Evento evento : this.eventos) {
			if (evento.getCodigoAlfanumerico().equals(codigoAlfanumerico)) {
				return evento;
			}
		}

		return null;
	}

	public Cliente buscarClientePorCliente(Cliente cliente) {
		for (Cliente clienteEncontrado : this.clientes) {
			if (clienteEncontrado.getDocumento().equals(cliente.getDocumento())) {
				return clienteEncontrado;
			}
		}

		return null;
	}

	public ArrayList<EventoConferencia> obtenerListaConferenciasExistentes() {
		ArrayList<EventoConferencia> listaConferenciasExistentes = new ArrayList<>();
		for (Evento evento : this.eventos) {
			if (evento instanceof EventoConferencia) {
				EventoConferencia conferencia = (EventoConferencia) evento;
				listaConferenciasExistentes.add(conferencia);
			}
		}

		return listaConferenciasExistentes;
	}

	public Set<Cliente> getClientes() {
		return clientes;
	}
}