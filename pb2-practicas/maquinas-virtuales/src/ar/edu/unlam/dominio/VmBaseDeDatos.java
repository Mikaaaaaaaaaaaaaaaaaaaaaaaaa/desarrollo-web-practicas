package ar.edu.unlam.dominio;

import ar.edu.unlam.dominio.exception.CantidadHorasUsoExcedidaException;

public class VmBaseDeDatos extends Vm {

	private final Integer COSTO_INICIAL = 1000;
	private Integer limiteHorasMensualesUso;

	public VmBaseDeDatos(Integer limiteHorasMensualesUso) {
		super(20.0);
		this.limiteHorasMensualesUso = limiteHorasMensualesUso;
	}

	public Boolean horasUso(Integer horasUtilizar) throws CantidadHorasUsoExcedidaException {
		if (horasUtilizar < this.limiteHorasMensualesUso) {
			return true;
		}

		throw new CantidadHorasUsoExcedidaException("Error, se ha superado la cantidad de horas.");
	}

	@Override
	public Double calcularCostoExtra() {
		return this.COSTO_INICIAL + (super.getCapacidadAlmacenamiento() * 10) + (this.limiteHorasMensualesUso * 5);
	}
}