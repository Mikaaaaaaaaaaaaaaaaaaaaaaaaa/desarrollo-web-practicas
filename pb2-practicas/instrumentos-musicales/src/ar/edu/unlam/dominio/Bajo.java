package ar.edu.unlam.dominio;

import ar.edu.unlam.dominio.enums.EnumMarca;
import ar.edu.unlam.dominio.exception.ExceptionNumeroSerie;

public class Bajo extends Instrumento {

	public Bajo(EnumMarca enumMarca, String modelo, Integer numeroSerie, Double precioBase) throws ExceptionNumeroSerie {
		super(enumMarca, modelo, numeroSerie, precioBase);
	}

	@Override
	public Double calcularPrecioFinal() {
		return super.getPrecioBase() + 20000 + (super.getPrecioBase() * 0.10);
	}
}