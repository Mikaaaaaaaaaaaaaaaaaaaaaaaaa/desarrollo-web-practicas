package ar.edu.unlam.dominio;

public class Factura implements Comparable<Factura> {

	private Integer codigoUnico;
	private String fecha;
	private Double subTotal;
	private Cliente cliente;

	public Factura(Integer codigoUnico, String fecha, Double subTotal, Cliente cliente) {
		this.codigoUnico = codigoUnico;
		this.fecha = fecha;
		this.subTotal = subTotal;
		this.cliente = cliente;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public Integer getCodigoUnico() {
		return codigoUnico;
	}

	public Integer getNroCte() {
		return this.codigoUnico;
	}

	public Double getImpuestos() {
		Double impuestoCalculado = 0.0;

		if (this.cliente instanceof ConsumidorFinal) {
			impuestoCalculado = this.subTotal * 0.21;
		} else if (this.cliente instanceof Monotributista) {
			impuestoCalculado = this.subTotal * 0.105;
		} else if (this.cliente instanceof ResponsableInscripto) {
			impuestoCalculado = this.subTotal * 0.0;
		}

		return impuestoCalculado;
	}

	@Override
	public int compareTo(Factura facturaDos) {
		return this.codigoUnico.compareTo(facturaDos.getCodigoUnico());
	}
}