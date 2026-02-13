package ar.edu.unlam.dominio;

public class CuentaSueldo extends CuentaBancaria {

	public CuentaSueldo(Cliente cliente, String cbuUnico, Double saldo) {
		super(cliente, cbuUnico, saldo);
	}

	@Override
	public Boolean retirarDinero(Double retirarDinero) throws ExceptionMontoNegativo, ExceptionFondosInsuficientes {
		return super.retirarDinero(retirarDinero);
	}
}