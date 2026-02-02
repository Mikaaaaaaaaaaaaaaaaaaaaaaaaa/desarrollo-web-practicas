package ar.edu.unlam.dominio;

public class AutobusVehiculo extends Vehiculo {

	private final Integer CANTIDAD_MAXIMA_ASIENTOS = 20;

	public AutobusVehiculo(Chofer chofer) {
		super(chofer);
	}

	public Boolean asignarChofer(Chofer choferAsignado) {
		if (super.getPasajeros() != null && super.getPasajeros().isEmpty() == false) {
			return false;
		}

		return super.asignarChofer(choferAsignado);
	}

	public Boolean agregarPasajero(Pasajero pasajeroAgregado) {
		if (super.getPasajeros().size() >= CANTIDAD_MAXIMA_ASIENTOS) {
			return false;
		}

		return super.getPasajeros().add(pasajeroAgregado);
	}

	public Boolean cambiarChofer(Chofer choferCambiado) {
		if (super.getPasajeros().size() >= 1) {
			return false;
		}

		super.getChoferes().clear();

		return super.cambiarChofer(choferCambiado);
	}
}