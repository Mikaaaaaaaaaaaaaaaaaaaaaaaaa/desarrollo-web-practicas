package ar.edu.unlam.dominio;

import ar.edu.unlam.dominio.enums.MaterialesCopas;
import ar.edu.unlam.dominio.enums.TipoAtril;

public class CopaPersonalizada extends Copa {

	private TipoAtril tipoAtril;

	public CopaPersonalizada(Integer identificador, Double precio, MaterialesCopas materialesCopas, TipoAtril tipoAtril) {
		super(identificador, precio * 1.15, materialesCopas);
		this.tipoAtril = tipoAtril;

		Double precioModificado = 0.0;
		if (tipoAtril.equals(TipoAtril.CAOBA)) {
			precioModificado = super.getPrecio() + (super.getPrecio() * 0.05);
			super.setPrecio(precioModificado);
			this.tipoAtril = tipoAtril;
			return;
		} else if (tipoAtril.equals(TipoAtril.CEDRO)) {
			precioModificado = super.getPrecio() + (super.getPrecio() * 0.10);
			super.setPrecio(precioModificado);
			this.tipoAtril = tipoAtril;
			return;
		} else if (tipoAtril.equals(TipoAtril.ROBLE_OSCURO)) {
			precioModificado = super.getPrecio() + (super.getPrecio() * 0.15);
			super.setPrecio(precioModificado);
			this.tipoAtril = tipoAtril;
			return;
		}
	}
}