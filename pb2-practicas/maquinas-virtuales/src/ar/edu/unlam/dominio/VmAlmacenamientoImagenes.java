package ar.edu.unlam.dominio;

import ar.edu.unlam.dominio.exception.CantidadEscrituraSuperadaException;

public class VmAlmacenamientoImagenes extends Vm {

	private final Integer COSTO_INICIAL = 500;
	private Double cantidadLimiteLecturas;
	private Double cantidadLimiteEscrituras;
	private Integer limiteHorasMensualesUso;

	public VmAlmacenamientoImagenes(Double cantidadLimiteLecturas, Double cantidadLimiteEscrituras, Integer limiteHorasMensualesUso) {
		super(5.0);
		this.cantidadLimiteLecturas = cantidadLimiteLecturas;
		this.cantidadLimiteEscrituras = cantidadLimiteEscrituras;
		this.limiteHorasMensualesUso = limiteHorasMensualesUso;
	}

	public Boolean lecturasEscriturasUso(Integer usarCantidadEscrituras) throws CantidadEscrituraSuperadaException {
		if (usarCantidadEscrituras < this.cantidadLimiteEscrituras) {
			return true;
		}

		throw new CantidadEscrituraSuperadaException("Error, se ha superado la cantidad de escrituras.");
	}

	@Override
	public Double calcularCostoExtra() {
		return this.COSTO_INICIAL + (this.cantidadLimiteLecturas * 1) + (this.cantidadLimiteEscrituras * 2);
	}
}