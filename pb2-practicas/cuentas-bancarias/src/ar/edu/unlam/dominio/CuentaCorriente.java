package ar.edu.unlam.dominio;

public class CuentaCorriente extends CuentaBancaria {

	private Double descubierto;

	public CuentaCorriente(Cliente cliente, String cbuUnico, Double saldo, Double descubierto) {
		super(cliente, cbuUnico, saldo);
		this.descubierto = descubierto;
	}

	@Override
	public Boolean retirarDinero(Double retirarDinero) throws ExceptionMontoNegativo {
		if (retirarDinero < 0) {
			throw new ExceptionMontoNegativo("Error, monto negativo.");
		}

		Double descubierto = super.getSaldo() + this.descubierto;
		if (retirarDinero <= super.getSaldo()) {
			super.setSaldo(super.getSaldo() - retirarDinero);
			return true;

		} else if (retirarDinero <= descubierto) {
			Double descubiertoUsado = retirarDinero - super.getSaldo();
			Double comision = descubiertoUsado * 0.05;

			super.setSaldo(super.getSaldo() - retirarDinero - comision);
			return true;
		}

		return false;
	}
}