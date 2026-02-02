package ar.edu.unlam.dominio;

import ar.edu.unlam.dominio.enums.MaterialesCopas;

public class CopaEstandar extends Copa {

	private Integer stock;

	public CopaEstandar(Integer identificador, Double precio, MaterialesCopas materialesCopas, Integer stock) {
		super(identificador, precio * 1.20, materialesCopas);
		this.stock = stock;
	}

	public Boolean descontarCantidadStockDisponible(Integer cantidad) {
		this.stock -= cantidad;
		return true;
	}

	public Integer getStock() {
		return stock;
	}
}