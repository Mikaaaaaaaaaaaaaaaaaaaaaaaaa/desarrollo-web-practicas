package ar.edu.unlam.dominio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Vehiculo {

	private Double kilometrosRecorridos;
	private Set<Chofer> choferes;
	private List<Pasajero> pasajeros;
	private List<Acompaniante> acompaniantes;

	public Vehiculo(Chofer chofer) {
		this.kilometrosRecorridos = 0.0;
		this.choferes = new HashSet<>();
		this.pasajeros = new ArrayList<>();
		this.acompaniantes = new ArrayList<>();
	}

	public Set<Chofer> getChoferes() {
		return this.choferes;
	}

	public List<Pasajero> getPasajeros() {
		return this.pasajeros;
	}

	public List<Acompaniante> getAcompaniantes() {
		return this.acompaniantes;
	}

	public Boolean asignarChofer(Chofer choferAsignado) {
		if (this.choferes.contains(choferAsignado) == true) {
			return false;
		}

		return this.choferes.add(choferAsignado);
	}

	public Boolean cambiarChofer(Chofer choferCambiado) {
		if (this.choferes.contains(choferCambiado) == true) {
			return false;
		}

		this.choferes.clear();

		return this.choferes.add(choferCambiado);
	}

	public Double getKilometrosRecorridos() {
		return this.kilometrosRecorridos;
	}

	public void iniciarViaje(Double distanciaRecorrer) {
		this.kilometrosRecorridos += distanciaRecorrer;
	}
}