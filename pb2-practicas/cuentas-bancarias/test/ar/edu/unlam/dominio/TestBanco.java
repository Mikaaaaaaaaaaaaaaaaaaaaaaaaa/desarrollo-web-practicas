package ar.edu.unlam.dominio;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestBanco {
	Banco bancoCreado;

	Cliente clienteUno;
	Cliente clienteDos;
	Cliente clienteTres;
	Cliente clienteCuatro;
	Cliente clienteCinco;

	CuentaBancaria cuentaSueldoUno;
	CuentaBancaria cuentaSueldoDos;
	CuentaBancaria cajaAhorroUno;
	CuentaBancaria cajaAhorroDos;
	CuentaBancaria cuentaCorrienteUno;
	CuentaBancaria cuentaCorrienteDos;
	CuentaBancaria cuentaCorrienteTres;

	@Before
	public void setUp() throws ExceptionCbuDuplicado {
		this.bancoCreado = new Banco();

		this.clienteUno = new Cliente(1, "Juan Perez");
		this.clienteDos = new Cliente(2, "Valentina Ríos");
		this.clienteTres = new Cliente(3, "Lucas Fernández");
		this.clienteCuatro = new Cliente(4, "Martina Sosa");
		this.clienteCinco = new Cliente(5, "Julián Acosta");

		this.cuentaSueldoUno = new CuentaSueldo(this.clienteUno, "CBU01", 2000.0);
		this.bancoCreado.agregarCuenta(this.cuentaSueldoUno);

		this.cuentaSueldoDos = new CuentaSueldo(this.clienteDos, "CBU02", 11000.0);
		this.bancoCreado.agregarCuenta(this.cuentaSueldoDos);

		this.cajaAhorroUno = new CajaDeAhorros(this.clienteTres, "CBU03", 7000.0);
		this.bancoCreado.agregarCuenta(this.cajaAhorroUno);

		this.cajaAhorroDos = new CajaDeAhorros(this.clienteCuatro, "CBU04", 10000.0);
		this.bancoCreado.agregarCuenta(this.cajaAhorroDos);

		this.cuentaCorrienteUno = new CuentaCorriente(this.clienteUno, "CBU05", 10000.0, 30000.0);
		this.bancoCreado.agregarCuenta(this.cuentaCorrienteUno);

		this.cuentaCorrienteDos = new CuentaCorriente(this.clienteDos, "CBU06", 1000.0, 30000.0);
		this.bancoCreado.agregarCuenta(this.cuentaCorrienteDos);

		this.cuentaCorrienteTres = new CuentaCorriente(this.clienteCinco, "CBU07", 20000.0, 30000.0);
		this.bancoCreado.agregarCuenta(this.cuentaCorrienteTres);
	}

	@Test
	public void queSePuedaExtraer1000PesosDeUnaCuentaSueldoConSaldoIgualA2000Pesos() throws ExceptionMontoNegativo, ExceptionFondosInsuficientes, ExceptionCuentaInexistente {
		this.bancoCreado.retirar("CBU01", 1000.0);

		assertEquals(1000.0, this.bancoCreado.encontrarCbuCuentaBancaria("CBU01").getSaldo(), 0.01);
	}

	@Test(expected = ExceptionFondosInsuficientes.class)
	public void queNoSePuedaExtraer2500PesosDeUnaCuentaSueldoConSaldoIgualA2000Pesos() throws ExceptionMontoNegativo, ExceptionFondosInsuficientes, ExceptionCuentaInexistente {

		this.bancoCreado.retirar("CBU01", 2500.0);
	}

	@Test
	public void queSeCobreElRecargoAPartirDeLaQuintaExtraccionEnCajasDeAhorro() throws ExceptionMontoNegativo, ExceptionFondosInsuficientes, ExceptionCuentaInexistente {
		this.bancoCreado.retirar("CBU03", 1000.0);
		this.bancoCreado.retirar("CBU03", 1000.0);
		this.bancoCreado.retirar("CBU03", 1000.0);
		this.bancoCreado.retirar("CBU03", 1000.0);
		this.bancoCreado.retirar("CBU03", 1000.0);
		this.bancoCreado.retirar("CBU03", 1000.0);

		assertEquals(900, this.cajaAhorroUno.getSaldo(), 0.01);
	}

	@Test
	public void queAlAgregarUnaCuentaConCbuDuplicadoLanceExcepcion() {
		try {
			CuentaBancaria cuentaCorrienteDuplicada = new CuentaCorriente(this.clienteUno, "CBU04", 10000.0, 30000.0);
			this.bancoCreado.agregarCuenta(cuentaCorrienteDuplicada);

		} catch (ExceptionCbuDuplicado exceptionCbuDuplicado) {
			assertEquals("Error, cbu duplicado.", exceptionCbuDuplicado.getMessage());
		}
	}

	@Test
	public void queSeCobreRecargoAlRealizarUnaExtraccionMayorAlSaldoEnUnaCuentaCorriente() throws ExceptionMontoNegativo, ExceptionFondosInsuficientes, ExceptionCuentaInexistente {
		this.bancoCreado.retirar("CBU05", 11000.0);

		assertEquals(-1050.0, this.cuentaCorrienteUno.getSaldo(), 0.01);
	}

	@Test
	public void queAlBuscarUnaCuentaPorCBUErroneoLanceExcepcion() throws ExceptionCuentaInexistente {
		try {
			this.bancoCreado.encontrarCbuCuentaBancaria("CBU08");

		} catch (ExceptionCuentaInexistente exceptionCuentaInexistente) {
			assertEquals("Error, cuenta inexistente.", exceptionCuentaInexistente.getMessage());
		}
	}

	@Test
	public void queAlBuscarRetirarODepositarMontoNegativoLanceExcepcion() throws ExceptionMontoNegativo, ExceptionCuentaInexistente {
		try {
			this.bancoCreado.retirar("CBU01", 10000.0);

		} catch (ExceptionFondosInsuficientes exceptionFondosInsuficientese) {
			assertEquals("Error, fondos insuficientes.", exceptionFondosInsuficientese.getMessage());
		}
	}

	@Test
	public void queSeObtengaUnListadoDeTodasLasCuentasOrdenadasPorSaldo() {

		assertEquals(this.cuentaCorrienteDos.getSaldo(), this.bancoCreado.cuentasBancariasOrdenadasSaldo().first().getSaldo(), 0.01);
		assertEquals(this.cuentaCorrienteTres.getSaldo(), this.bancoCreado.cuentasBancariasOrdenadasSaldo().last().getSaldo(), 0.01);
	}

	@Test
	public void queSeObtengaUnListadoDeCuentasCorrientesOrdenadasPorSaldo() {

		assertEquals(1000.0, this.bancoCreado.cuentasCorrientesOrdenadasSaldo().first().getSaldo(), 0.01);
		assertEquals(20000.0, this.bancoCreado.cuentasCorrientesOrdenadasSaldo().last().getSaldo(), 0.01);
	}

	@Test
	public void queSeObtengaUnListadoDeTodasLasCuentasOrdenadasPorCbu() {

		assertEquals("CBU01", this.bancoCreado.cuentasBancariasOrdenadasCbu().first().getCbuUnico());
		assertEquals("CBU07", this.bancoCreado.cuentasBancariasOrdenadasCbu().last().getCbuUnico());
	}
}