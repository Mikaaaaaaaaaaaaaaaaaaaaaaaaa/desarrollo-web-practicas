package ar.edu.unlam.dominio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import ar.edu.unlam.dominio.enums.TipoCanal;
import ar.edu.unlam.dominio.enums.TipoClasificacion;
import ar.edu.unlam.dominio.exception.ClienteDuplicadoException;

public class TestCompaniaTelevision {
	
	@Test
	public void dadoQueExisteUnaCompaniaSePuedeAgregarUnCliente() throws ClienteDuplicadoException {
		GestorCompania gestorCompania = new GestorCompania();

		Cliente clienteCreado = new Cliente(123, "NombreCliente", "ApellidoCliente", 15);

		assertTrue(gestorCompania.agregarCliente(clienteCreado));
	}
	
	@Test
	public void dadoQueExisteUnaCompaniaAlAgregarUnClienteExistenteSeLanzaUnaClienteExistenteException() throws ClienteDuplicadoException {
		GestorCompania gestorCompania = new GestorCompania();

		Cliente clienteCreadoUno = new Cliente(123, "NombreCliente", "ApellidoCliente", 15);
		gestorCompania.agregarCliente(clienteCreadoUno);

		try {

			Cliente clienteCreadoDos = new Cliente(123, "NombreCliente", "ApellidoCliente", 15);
			gestorCompania.agregarCliente(clienteCreadoDos);

		} catch (ClienteDuplicadoException clienteDuplicadoException) {
			assertEquals("Error, cliente duplicado.", clienteDuplicadoException.getMessage());
		}
	}
	
	@Test
	public void dadoQueExisteUnaCompaniaConUnPlanBasicoYUnPlanPremiumCuandoSeObtieneElPrecioDeUnPlanPremiumDevuelve6000() {
		GestorCompania gestorCompania = new GestorCompania();
		
		Canal canalCreadoUno = new Canal(5, "NombreCanal", TipoCanal.DEPORTES, TipoClasificacion.BASICO);
		Canal canalCreadoDos = new Canal(10, "NombreCanal", TipoCanal.DEPORTES, TipoClasificacion.PREMIUM);
		
		assertEquals(6000, canalCreadoDos.calcularPrecioPlan(), 0.01);
	}
	
	@Test
	public void dadoQueExisteUnaCompaniaConClientesSePuedenListarLosClientesOrdenadosDeManeraAscendentePorSuDni() throws ClienteDuplicadoException {
		GestorCompania gestorCompania = new GestorCompania();

		Cliente clienteCreadoTres = new Cliente(3, "NombreCliente", "ApellidoCliente", 20);
		Cliente clienteCreadoDos = new Cliente(2, "NombreCliente", "ApellidoCliente", 20);
		Cliente clienteCreadoUno = new Cliente(1, "NombreCliente", "ApellidoCliente", 20);

		gestorCompania.agregarCliente(clienteCreadoTres);
		gestorCompania.agregarCliente(clienteCreadoDos);
		gestorCompania.agregarCliente(clienteCreadoUno);

		assertEquals(1, gestorCompania.getClientes().first().getDocumento(), 0.01);
		assertEquals(3, gestorCompania.getClientes().last().getDocumento(), 0.01);
	}
	
	@Test
	public void dadoQueExisteUnaCompaniaConSuscripcionesAPlanesBasicosYPremiumSePuedenListarLasSuscripcionesAPlanesPremium() {
		GestorCompania gestorCompania = new GestorCompania();

		Cliente clienteCreadoUno = new Cliente(1, "NombreCliente", "ApellidoCliente", 20);
		Plan planCreadoUno = new Plan(1, "NombrePlan");
		Canal canalCreadoUno = new Canal(5, "NombreCanal", TipoCanal.DEPORTES, TipoClasificacion.BASICO);
		Suscripcion suscripcionCreadaUno = new Suscripcion(1, clienteCreadoUno, planCreadoUno, canalCreadoUno);

		Cliente clienteCreadoDos = new Cliente(2, "NombreCliente", "ApellidoCliente", 20);
		Plan planCreadoDos = new Plan(2, "NombrePlan");
		Canal canalCreadoDos = new Canal(10, "NombreCanal", TipoCanal.DEPORTES, TipoClasificacion.PREMIUM);
		Suscripcion suscripcionCreadaDos = new Suscripcion(2, clienteCreadoDos, planCreadoDos, canalCreadoDos);

		gestorCompania.agregarSuscripcion(suscripcionCreadaUno);
		gestorCompania.agregarSuscripcion(suscripcionCreadaDos);

		ArrayList<Suscripcion> suscripcionesPlanesPremium = gestorCompania.suscripcionesPlanesPremium();

		assertEquals(1, suscripcionesPlanesPremium.size());
		assertEquals(TipoClasificacion.PREMIUM, suscripcionesPlanesPremium.get(0).getCanal().getTipoClasificacion());
	}
	
	@Test
	public void dadoQueExisteUnaCompaniaConSuscripcionesAPlanesBasicosYPremiumSePuedenListarLosClientesSuscritosAPlanesBasicos() {
		GestorCompania gestorCompania = new GestorCompania();

		Cliente clienteCreadoUno = new Cliente(1, "NombreCliente", "ApellidoCliente", 20);
		Plan planCreadoUno = new Plan(1, "NombrePlan");
		Canal canalCreadoUno = new Canal(5, "NombreCanal", TipoCanal.DEPORTES, TipoClasificacion.BASICO);
		Suscripcion suscripcionCreadaUno = new Suscripcion(1, clienteCreadoUno, planCreadoUno, canalCreadoUno);

		Cliente clienteCreadoDos = new Cliente(2, "NombreCliente", "ApellidoCliente", 20);
		Plan planCreadoDos = new Plan(2, "NombrePlan");
		Canal canalCreadoDos = new Canal(10, "NombreCanal", TipoCanal.DEPORTES, TipoClasificacion.PREMIUM);
		Suscripcion suscripcionCreadaDos = new Suscripcion(2, clienteCreadoDos, planCreadoDos, canalCreadoDos);

		gestorCompania.agregarSuscripcion(suscripcionCreadaUno);
		gestorCompania.agregarSuscripcion(suscripcionCreadaDos);

		ArrayList<Suscripcion> suscripcionesPlanesBasico = gestorCompania.suscripcionesPlanesBasico();

		assertEquals(1, suscripcionesPlanesBasico.size());
		assertEquals(TipoClasificacion.BASICO, suscripcionesPlanesBasico.get(0).getCanal().getTipoClasificacion());
	}
	
	@Test
	public void dadoQueExisteUnaCompaniaConSuscripcionesAPlanesBasicosYPremiumSePuedeMostrarElTotalDePrecioParaUnPlanBasicoOPremium() {
		GestorCompania gestorCompania = new GestorCompania();

		Cliente clienteCreadoUno = new Cliente(1, "NombreCliente", "ApellidoCliente", 20);
		Plan planCreadoUno = new Plan(1, "NombrePlan");
		Canal canalCreadoUno = new Canal(5, "NombreCanal", TipoCanal.DEPORTES, TipoClasificacion.BASICO);
		gestorCompania.agregarCanal(canalCreadoUno);
		Suscripcion suscripcionCreadaUno = new Suscripcion(1, clienteCreadoUno, planCreadoUno, canalCreadoUno);

		Cliente clienteCreadoDos = new Cliente(2, "NombreCliente", "ApellidoCliente", 20);
		Plan planCreadoDos = new Plan(2, "NombrePlan");
		Canal canalCreadoDos = new Canal(10, "NombreCanal", TipoCanal.DEPORTES, TipoClasificacion.PREMIUM);
		gestorCompania.agregarCanal(canalCreadoDos);
		Suscripcion suscripcionCreadaDos = new Suscripcion(2, clienteCreadoDos, planCreadoDos, canalCreadoDos);

		Cliente clienteCreadoTres = new Cliente(1, "NombreCliente", "ApellidoCliente", 20);
		Plan planCreadoTres = new Plan(1, "NombrePlan");
		Canal canalCreadoTres = new Canal(15, "NombreCanal", TipoCanal.DEPORTES, TipoClasificacion.BASICO);
		gestorCompania.agregarCanal(canalCreadoTres);
		Suscripcion suscripcionCreadaTres = new Suscripcion(1, clienteCreadoTres, planCreadoTres, canalCreadoTres);

		gestorCompania.agregarSuscripcion(suscripcionCreadaUno);
		gestorCompania.agregarSuscripcion(suscripcionCreadaDos);
		gestorCompania.agregarSuscripcion(suscripcionCreadaTres);

		assertEquals(10000, gestorCompania.obtenerTotalPrecioPorTipoClasificacionPlan(TipoClasificacion.BASICO), 0.01);
		assertEquals(6000, gestorCompania.obtenerTotalPrecioPorTipoClasificacionPlan(TipoClasificacion.PREMIUM), 0.01);
	}
}