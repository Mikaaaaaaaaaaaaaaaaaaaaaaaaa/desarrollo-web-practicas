package ar.edu.unlam.dominio;

import java.util.Comparator;

public class ComparatorCuentasBancariasOrdenadasCbu implements Comparator<CuentaBancaria> {

	@Override
	public int compare(CuentaBancaria cuentaBancariaUno, CuentaBancaria cuentaBancariaDos) {
		return cuentaBancariaUno.getCbuUnico().compareTo(cuentaBancariaDos.getCbuUnico());
	}
}