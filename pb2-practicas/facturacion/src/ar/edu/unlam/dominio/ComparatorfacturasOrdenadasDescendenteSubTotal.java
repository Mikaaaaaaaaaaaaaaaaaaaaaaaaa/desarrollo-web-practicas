package ar.edu.unlam.dominio;

import java.util.Comparator;

public class ComparatorfacturasOrdenadasDescendenteSubTotal implements Comparator<Factura> {

	@Override
	public int compare(Factura facturaUno, Factura facturaDos) {
		return Double.compare(facturaDos.getSubTotal(), facturaUno.getSubTotal());
	}
}