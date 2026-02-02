package ar.edu.unlam.dominio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ar.edu.unlam.dominio.enums.MaterialesCopas;
import ar.edu.unlam.dominio.enums.TipoAtril;
import ar.edu.unlam.dominio.exception.ClienteDuplicadoException;

public class TestCopaMundo {
	
	@Test
	public void dadoQueExisteUnaFabricaDeCopasDelMundoSePuedeAgregarUnaCopaDelMundoEstandar() {
		FabricaGestora fabricaGestoraCreada = new FabricaGestora();

		Copa copaEstandarCreada = new CopaEstandar(123, 50.0, MaterialesCopas.PLASTICO, 10);

		assertTrue(fabricaGestoraCreada.agregarCopaMundo(copaEstandarCreada));
	}
	
	@Test
	public void dadoQueExisteUnaFabricaDeCopasDelMundoSePuedeAgregarUnaCopaDelMundoPersonalizada() {
		FabricaGestora fabricaGestoraCreada = new FabricaGestora();

		Copa copaPersonalizadaCreada = new CopaPersonalizada(123, 50.0, MaterialesCopas.PLASTICO, TipoAtril.CAOBA);

		assertTrue(fabricaGestoraCreada.agregarCopaMundo(copaPersonalizadaCreada));
	}
	
	@Test
	public void dadoQueExisteUnaFabricaDeCopasDelMundoAlAgregarUnClienteExistenteSeLanzaUnaClienteDuplicadoException() throws ClienteDuplicadoException {
		FabricaGestora fabricaGestoraCreada = new FabricaGestora();

		Cliente clienteCreadoUno = new Cliente(123, "NombreClienteUno", "ApellidoClienteUno");

		fabricaGestoraCreada.agregarCliente(clienteCreadoUno);

		try {
			Cliente clienteCreadoDos = new Cliente(123, "NombreClienteDos", "ApellidoClienteDos");
			fabricaGestoraCreada.agregarCliente(clienteCreadoDos);

		} catch (ClienteDuplicadoException clienteDuplicadoException) {
			assertEquals("Error, cliente duplicado.", clienteDuplicadoException.getMessage());
		}
	}
	
	@Test
	public void dadoQueExisteUnaFabricaQuePoseeCopasDelMundoSePuedenObtenerLasCopasDelMundoEstandar() {
		FabricaGestora fabricaGestoraCreada = new FabricaGestora();

		Copa copaEstandarCreadaUno = new CopaEstandar(123, 50.0, MaterialesCopas.PLASTICO, 10);
		Copa copaEstandarCreadaDos = new CopaEstandar(123, 50.0, MaterialesCopas.RESINA, 10);

		fabricaGestoraCreada.agregarCopaMundo(copaEstandarCreadaUno);
		fabricaGestoraCreada.agregarCopaMundo(copaEstandarCreadaDos);

		assertEquals(2, fabricaGestoraCreada.obtenerCopaMundoEstandar().size());
	}
	
	@Test
	public void dadoQueExisteUnaFabricaDeCopasDelMundoConCopasDelMundoPuedoObtenerUnaCopaDelMundoPorSuId() {
		FabricaGestora fabricaGestoraCreada = new FabricaGestora();

		Copa copaMundoCreadaUno = new CopaEstandar(123, 50.0, MaterialesCopas.PLASTICO, 10);
		Copa copaMundoCreadaDos = new CopaEstandar(1234, 50.0, MaterialesCopas.RESINA, 10);
		Copa copaMundoCreadaTres = new CopaEstandar(12345, 50.0, MaterialesCopas.YESO, 10);

		fabricaGestoraCreada.agregarCopaMundo(copaMundoCreadaUno);
		fabricaGestoraCreada.agregarCopaMundo(copaMundoCreadaDos);
		fabricaGestoraCreada.agregarCopaMundo(copaMundoCreadaTres);

		assertEquals(copaMundoCreadaUno.getIdentificador(), fabricaGestoraCreada.obtenerCopaMundoIdentificador(123).getIdentificador());
	}
	
	@Test
	public void dadoQueExisteUnaFabricaDeCopasDelMundoConCopasDelMundoAlAgregarCincoCopasDelMundoAUnaVentaDeCopasDelMundoEstandarParaUnClienteSeDescuentanCincoUnidadesDelStockDeCopasDelMundoEstandar() {
		FabricaGestora fabricaGestoraCreada = new FabricaGestora();

		CopaEstandar copaMundoCreadaUno = new CopaEstandar(123, 50.0, MaterialesCopas.PLASTICO, 10);
		CopaEstandar copaMundoCreadaDos = new CopaEstandar(1234, 50.0, MaterialesCopas.RESINA, 10);
		CopaEstandar copaMundoCreadaTres = new CopaEstandar(12345, 50.0, MaterialesCopas.YESO, 10);
		CopaEstandar copaMundoCreadaCuatro = new CopaEstandar(123456, 50.0, MaterialesCopas.RESINA, 10);
		CopaEstandar copaMundoCreadaCinco = new CopaEstandar(1234567, 50.0, MaterialesCopas.PLASTICO, 10);

		Cliente clienteCreado = new Cliente(1, "NombreCliente", "ApellidoCliente");

		Venta ventaCreadaUno = new Venta(clienteCreado);
		Venta ventaCreadaDos = new Venta(clienteCreado);
		Venta ventaCreadaTres = new Venta(clienteCreado);
		Venta ventaCreadaCuatro = new Venta(clienteCreado);
		Venta ventaCreadaCinco = new Venta(clienteCreado);

		ventaCreadaUno.agregarCopaMundoVenta(copaMundoCreadaUno);
		ventaCreadaDos.agregarCopaMundoVenta(copaMundoCreadaDos);
		ventaCreadaTres.agregarCopaMundoVenta(copaMundoCreadaTres);
		ventaCreadaCuatro.agregarCopaMundoVenta(copaMundoCreadaCuatro);
		ventaCreadaCinco.agregarCopaMundoVenta(copaMundoCreadaCinco);

		fabricaGestoraCreada.venderCopaMundoEstandar(clienteCreado, copaMundoCreadaUno, 5);

		assertEquals(5, copaMundoCreadaUno.getStock(), 0.01);
	}
	
	@Test
	public void dadoQueExisteUnaFabricaDeCopasDelMundoConCopasDelMundoAlAgregarUnaVentaDeCopasDelMundoPersonalizadaParaUnClienteSeRemueveLaCopaDelMundoPersonalizadaDeLaFabrica() {
		FabricaGestora fabricaGestoraCreada = new FabricaGestora();

		CopaPersonalizada copaMundoCreadaUno = new CopaPersonalizada(123, 10.0, MaterialesCopas.PLASTICO, TipoAtril.CAOBA);
		CopaPersonalizada copaMundoCreadaDos = new CopaPersonalizada(1234, 20.0, MaterialesCopas.RESINA, TipoAtril.CEDRO);
		CopaPersonalizada copaMundoCreadaTres = new CopaPersonalizada(12345, 30.0, MaterialesCopas.YESO, TipoAtril.ROBLE_OSCURO);

		Cliente clienteCreado = new Cliente(1, "NombreCliente", "ApellidoCliente");

		Venta ventaCreada = new Venta(clienteCreado);

		ventaCreada.agregarCopaMundoVenta(copaMundoCreadaUno);
		ventaCreada.agregarCopaMundoVenta(copaMundoCreadaDos);
		ventaCreada.agregarCopaMundoVenta(copaMundoCreadaTres);

		fabricaGestoraCreada.agregarCopaMundo(copaMundoCreadaUno);
		fabricaGestoraCreada.agregarCopaMundo(copaMundoCreadaDos);
		fabricaGestoraCreada.agregarCopaMundo(copaMundoCreadaTres);

		fabricaGestoraCreada.venderCopaMundoPersonalizada(clienteCreado, copaMundoCreadaUno);

		assertFalse(fabricaGestoraCreada.getCopas().contains(copaMundoCreadaUno));
		assertEquals(copaMundoCreadaDos, fabricaGestoraCreada.getCopas().get(0));
		assertEquals(copaMundoCreadaTres, fabricaGestoraCreada.getCopas().get(1));
	}
	
	@Test
	public void dadoQueExisteUnaFabricaDeCopasDelMundoConCopasDelMundoPersonalizadasSePuedeObtenerElPrecioDeUnaCopaDelMundoPersonalizada() {
		CopaPersonalizada copaMundoCreadaUno = new CopaPersonalizada(123, 10.0, MaterialesCopas.PLASTICO, TipoAtril.CAOBA);
		CopaPersonalizada copaMundoCreadaDos = new CopaPersonalizada(1234, 20.0, MaterialesCopas.RESINA, TipoAtril.CEDRO);
		CopaPersonalizada copaMundoCreadaTres = new CopaPersonalizada(12345, 30.0, MaterialesCopas.YESO, TipoAtril.ROBLE_OSCURO);
		
		assertEquals(12.075, copaMundoCreadaUno.getPrecio(), 0.01);
		assertEquals(25.3, copaMundoCreadaDos.getPrecio(), 0.01);
		assertEquals(39.675, copaMundoCreadaTres.getPrecio(), 0.01);
	}
}