package ar.edu.unlam.dominio;

import java.time.LocalDate;

import ar.edu.unlam.dominio.enums.TipoSala;
import ar.edu.unlam.dominio.exception.ClienteExistenteException;

public class EventoTaller extends Evento {

	private Integer cupoMaximoParticipante;
	private Integer duracion;
	private final Integer VALOR_POR_PERSONA_QUE_PARTICIPA = 25000;

	public EventoTaller(String codigo, LocalDate fecha, String nombre, TipoSala tipoSala, Cliente cliente, Integer cupoMaximoParticipante, Integer duracion) {
		super(codigo, fecha, nombre, tipoSala, cliente);
		this.cupoMaximoParticipante = cupoMaximoParticipante;
		this.duracion = duracion;
	}

	public Integer calcularPrecioTotalTaller() {
		Integer precioCalculado = 0;

		if (super.getClientes().size() >= this.cupoMaximoParticipante) {
			return precioCalculado;
		}

		return precioCalculado = super.getClientes().size() * this.VALOR_POR_PERSONA_QUE_PARTICIPA;
	}

	@Override
	public Boolean agregarCliente(Cliente cliente) throws ClienteExistenteException {
		if (super.getClientes().size() >= this.cupoMaximoParticipante) {
			return false;
		}

		return super.getClientes().add(cliente);
	}
}