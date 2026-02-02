package ar.edu.unlam.dominio;

public class AutomovilVehiculo extends Vehiculo {

	private final Integer CANTIDAD_MAXIMA_PASAJEROS = 3;

	public AutomovilVehiculo(Chofer chofer) {
		super(chofer);
	}

	public Boolean agregarPasajero(Pasajero pasajeroAgregado) {
		if (super.getPasajeros().size() >= CANTIDAD_MAXIMA_PASAJEROS) {
			return false;
		}

		return super.getPasajeros().add(pasajeroAgregado);
	}
}