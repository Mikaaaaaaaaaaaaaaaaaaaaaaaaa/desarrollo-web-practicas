package ar.edu.unlam.dominio;

import java.util.ArrayList;
import java.util.TreeSet;

import ar.edu.unlam.dominio.enums.TipoClasificacion;
import ar.edu.unlam.dominio.exception.ClienteDuplicadoException;

public class GestorCompania {

	private TreeSet<Cliente> clientes;
	private TreeSet<Canal> canales;
	private ArrayList<Suscripcion> suscripciones;

	public GestorCompania() {
		this.clientes = new TreeSet<>();
		this.canales = new TreeSet<>();
		this.suscripciones = new ArrayList<>();
	}

	public Boolean agregarCliente(Cliente cliente) throws ClienteDuplicadoException {
		if (this.clientes.contains(cliente)) {
			throw new ClienteDuplicadoException("Error, cliente duplicado.");
		}

		return this.clientes.add(cliente);
	}

	public TreeSet<Cliente> getClientes() {
		return clientes;
	}

	public void agregarSuscripcion(Suscripcion suscripcion) {
		this.suscripciones.add(suscripcion);
	}

	public void agregarCanal(Canal canal) {
		this.canales.add(canal);
	}

	public ArrayList<Suscripcion> suscripcionesPlanesPremium() {
		ArrayList<Suscripcion> suscripcionesPlanesPremium = new ArrayList<>();

		for (Suscripcion suscripcion : this.suscripciones) {
			if (suscripcion.getCanal().getTipoClasificacion().equals(TipoClasificacion.PREMIUM)) {
				suscripcionesPlanesPremium.add(suscripcion);
			}
		}

		return suscripcionesPlanesPremium;
	}

	public ArrayList<Suscripcion> suscripcionesPlanesBasico() {
		ArrayList<Suscripcion> suscripcionesPlanesBasico = new ArrayList<>();

		for (Suscripcion suscripcion : this.suscripciones) {
			if (suscripcion.getCanal().getTipoClasificacion().equals(TipoClasificacion.BASICO)) {
				suscripcionesPlanesBasico.add(suscripcion);
			}
		}

		return suscripcionesPlanesBasico;
	}

	public Double obtenerTotalPrecioPorTipoClasificacionPlan(TipoClasificacion tipoClasificacion) {
		Double total = 0.0;

		for (Suscripcion suscripcion : this.suscripciones) {
			if (suscripcion.getCanal().getTipoClasificacion().equals(tipoClasificacion)) {
				total += suscripcion.getCanal().calcularPrecioPlan();
			}
		}

		return total;
	}
}