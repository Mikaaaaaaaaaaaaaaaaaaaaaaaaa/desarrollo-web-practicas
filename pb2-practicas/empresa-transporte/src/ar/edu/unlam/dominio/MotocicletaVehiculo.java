package ar.edu.unlam.dominio;

import ar.edu.unlam.dominio.exception.AcompanianteCantidadExcedida;

public class MotocicletaVehiculo extends Vehiculo {

	private final Integer CANTIDAD_MAXIMA_ACOMPANIANTES = 1;

	public MotocicletaVehiculo(Chofer chofer) {
		super(chofer);
	}

	public Boolean asignarChofer(Chofer choferAsignado) {

		if (super.getPasajeros() != null && super.getPasajeros().isEmpty() == false) {
			return false;
		}

		return super.asignarChofer(choferAsignado);
	}

	public Boolean agregarAcompaniante(Acompaniante acompañanteAgregado) throws AcompanianteCantidadExcedida {
		if (super.getAcompaniantes().size() >= CANTIDAD_MAXIMA_ACOMPANIANTES) {
			throw new AcompanianteCantidadExcedida("Error, no se ha podido agregar el acompañante.");
		}

		return super.getAcompaniantes().add(acompañanteAgregado);
	}

	public Boolean cambiarChofer(Chofer choferCambiado) {
		if (super.getChoferes().size() > 1) {
			return false;
		}

		return super.cambiarChofer(choferCambiado);
	}
}