package ar.edu.unlam.dominio;

import ar.edu.unlam.dominio.enums.MaterialesCopas;

public abstract class Copa {

	private Integer identificador;
	private Double precio;
	private MaterialesCopas materialesCopas;

	public Copa(Integer identificador, Double precio, MaterialesCopas materialesCopas) {
		this.identificador = identificador;
		this.precio = precio;
		this.materialesCopas = materialesCopas;
	}

	public Integer getIdentificador() {
		return identificador;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
}