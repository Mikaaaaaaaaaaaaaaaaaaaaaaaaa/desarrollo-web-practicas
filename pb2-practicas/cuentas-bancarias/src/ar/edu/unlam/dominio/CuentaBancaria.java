package ar.edu.unlam.dominio;

import java.util.Objects;

public abstract class CuentaBancaria implements Comparable<CuentaBancaria> {

	private Cliente cliente;
	private String cbuUnico;
	private Double saldo;

	public CuentaBancaria(Cliente cliente, String cbuUnico, Double saldo) {
		this.cliente = cliente;
		this.cbuUnico = cbuUnico;
		this.saldo = saldo;
	}

	public Double getSaldo() {
		return this.saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public String getCbuUnico() {
		return cbuUnico;
	}

	public Boolean agregarDinero(Integer agregarDinero) {
		if (agregarDinero > 0) {
			this.saldo += agregarDinero;
			return true;
		}

		return false;
	}

	public Boolean retirarDinero(Double retirarDinero) throws ExceptionMontoNegativo, ExceptionFondosInsuficientes {
		if (retirarDinero < 0) {
			throw new ExceptionMontoNegativo("Error, monto negativo.");
		}

		if (this.saldo < retirarDinero) {
			throw new ExceptionFondosInsuficientes("Error, fondos insuficientes.");
		}

		if (this.saldo > retirarDinero) {
			setSaldo(this.saldo - retirarDinero);
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.cbuUnico);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		CuentaBancaria other = (CuentaBancaria) obj;
		return Objects.equals(this.cbuUnico, other.cbuUnico);
	}

	@Override
	public int compareTo(CuentaBancaria cuentaBancariaDos) {
		Integer comparacionSaldo = this.saldo.compareTo(cuentaBancariaDos.saldo);

		if (comparacionSaldo != 0) {
			return comparacionSaldo;
		}

		return this.cbuUnico.compareTo(cuentaBancariaDos.cbuUnico);
	}
}