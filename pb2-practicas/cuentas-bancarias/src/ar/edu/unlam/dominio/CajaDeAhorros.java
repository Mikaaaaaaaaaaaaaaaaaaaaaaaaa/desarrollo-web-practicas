package ar.edu.unlam.dominio;

public class CajaDeAhorros extends CuentaBancaria {

	private Integer cantidadExtracciones;

	public CajaDeAhorros(Cliente cliente, String cbuUnico, Double saldo) {
		super(cliente, cbuUnico, saldo);
		this.cantidadExtracciones = 0;
	}

	@Override
	public Boolean retirarDinero(Double retirarDinero) throws ExceptionMontoNegativo, ExceptionFondosInsuficientes {
		if (retirarDinero < 0) {
			throw new ExceptionMontoNegativo("Error, monto negativo.");
		}

		Boolean resultado = super.retirarDinero(retirarDinero);

		if (resultado == true) {
			this.cantidadExtracciones++;

			if (this.cantidadExtracciones > 5) {

				if (super.getSaldo() < 100.0) {
					throw new ExceptionFondosInsuficientes("Error, fondos insuficientes.");
				}

				super.setSaldo(super.getSaldo() - 100.0);
			}
		}

		return resultado;
	}
}