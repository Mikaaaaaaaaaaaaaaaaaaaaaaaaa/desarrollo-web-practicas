package ar.edu.unlam.dominio;

import java.util.TreeSet;

public class Banco {

	private TreeSet<CuentaBancaria> cuentasBancarias;

	public Banco() {
		this.cuentasBancarias = new TreeSet<>();
	}

	public void agregarCuenta(CuentaBancaria cuentaSueldo) throws ExceptionCbuDuplicado {
		if (this.cuentasBancarias.contains(cuentaSueldo)) {
			throw new ExceptionCbuDuplicado("Error, cbu duplicado.");
		}

		this.cuentasBancarias.add(cuentaSueldo);
	}

	public void retirar(String cbu, Double retirarCantidad) throws ExceptionMontoNegativo, ExceptionFondosInsuficientes, ExceptionCuentaInexistente {
		CuentaBancaria cuentaBancaria = encontrarCbuCuentaBancaria(cbu);
		cuentaBancaria.retirarDinero(retirarCantidad);
	}

	public CuentaBancaria encontrarCbuCuentaBancaria(String buscarCbu) throws ExceptionCuentaInexistente {
		for (CuentaBancaria cuentaBancaria : this.cuentasBancarias) {
			if (cuentaBancaria.getCbuUnico().equals(buscarCbu)) {
				return cuentaBancaria;
			}
		}

		throw new ExceptionCuentaInexistente("Error, cuenta inexistente.");
	}

	public TreeSet<CuentaBancaria> cuentasBancariasOrdenadasSaldo() {
		TreeSet<CuentaBancaria> cuentasBancariasOrdenadasSaldo = new TreeSet<>();

		for (CuentaBancaria cuentaBancaria : this.cuentasBancarias) {
			if (cuentaBancaria instanceof CuentaSueldo || cuentaBancaria instanceof CajaDeAhorros || cuentaBancaria instanceof CuentaCorriente) {
				cuentasBancariasOrdenadasSaldo.add(cuentaBancaria);
			}
		}

		return cuentasBancariasOrdenadasSaldo;
	}

	public TreeSet<CuentaBancaria> cuentasCorrientesOrdenadasSaldo() {
		TreeSet<CuentaBancaria> cuentasBancariasOrdenadasSaldo = new TreeSet<>();

		for (CuentaBancaria cuentaBancaria : this.cuentasBancarias) {
			if (cuentaBancaria instanceof CuentaCorriente) {
				cuentasBancariasOrdenadasSaldo.add(cuentaBancaria);
			}
		}

		return cuentasBancariasOrdenadasSaldo;
	}

	public TreeSet<CuentaBancaria> cuentasBancariasOrdenadasCbu() {
		TreeSet<CuentaBancaria> cuentasBancariasOrdenadasCbu = new TreeSet<>(new ComparatorCuentasBancariasOrdenadasCbu());

		for (CuentaBancaria cuentaBancaria : this.cuentasBancarias) {
			if (cuentaBancaria instanceof CuentaSueldo || cuentaBancaria instanceof CajaDeAhorros || cuentaBancaria instanceof CuentaCorriente) {
				cuentasBancariasOrdenadasCbu.add(cuentaBancaria);
			}
		}

		return cuentasBancariasOrdenadasCbu;
	}
}