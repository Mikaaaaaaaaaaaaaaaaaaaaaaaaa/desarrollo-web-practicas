package ar.edu.unlam.dominioTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unlam.dominio.Acompaniante;
import ar.edu.unlam.dominio.AutobusVehiculo;
import ar.edu.unlam.dominio.Chofer;
import ar.edu.unlam.dominio.MotocicletaVehiculo;
import ar.edu.unlam.dominio.Pasajero;
import ar.edu.unlam.dominio.exception.AcompanianteCantidadExcedida;

public class TestEmpresaTransporte {

	private MotocicletaVehiculo motocicletas;
	private Chofer choferUno;
	private Chofer choferDos;
	private Pasajero pasajeroUno;
	private Pasajero pasajeroDos;
	private AutobusVehiculo autobusUno;
	private AutobusVehiculo autobusDos;
	private Acompaniante acompanianteUno;
	private Acompaniante acompanianteDos;
	private AutobusVehiculo colectivoAutobus;

	@Before
	public void inicializacion() {
		choferUno = new Chofer("EvangelinaChoferUno", 22, 12345678);
		motocicletas = new MotocicletaVehiculo(choferUno);
		this.acompanianteUno = new Acompaniante("EvangelinaAcompañanteUno", 22, 12345678);
		this.acompanianteDos = new Acompaniante("EvangelinaAcompañanteDos", 22, 12345678);

		choferDos = new Chofer("EvangelinaChoferDos", 22, 123456789);
		pasajeroUno = new Pasajero("EvangelinaPasajeraUno", 22, 12345678);
		pasajeroDos = new Pasajero("EvangelinaPasajeraDos", 22, 12345678);

		autobusUno = new AutobusVehiculo(choferUno);
		autobusDos = new AutobusVehiculo(choferDos);

		this.colectivoAutobus = new AutobusVehiculo(choferUno);
	}

	@Test
	public void dadoQueExisteUnaMotoSeQuiereAgregarUnAcompañanteYSeObtieneUnResultadoExitoso() throws AcompanianteCantidadExcedida {

		assertTrue(this.motocicletas.agregarAcompaniante(acompanianteUno));
	}

	@Test
	public void dadoQueExisteUnaMotoConUnAcompañanteSeQuiereAgregarOtroAcompañanteYNoSePuedeAgregar() throws AcompanianteCantidadExcedida {
		this.motocicletas.agregarAcompaniante(acompanianteUno);

		try {
			this.motocicletas.agregarAcompaniante(acompanianteDos);
			
		} catch (AcompanianteCantidadExcedida acompanianteCantidadExcedida) {
			assertEquals("Error, no se ha podido agregar el acompañante.", acompanianteCantidadExcedida.getMessage());
		}
	}

	@Test
	public void dadoQueNoHayAcompañantesSeQuiereCambiarDeChoferYSeObtieneUnResultadoExitoso() {
		this.motocicletas.asignarChofer(choferUno);

		assertTrue(this.motocicletas.cambiarChofer(choferDos));
	}

	@Test
	public void dadoQueHayUnAcompañantesSeQuiereCambiarDeChoferSeEsperaQueNoSePuedaCambiar() throws AcompanianteCantidadExcedida {
		this.motocicletas.agregarAcompaniante(acompanianteUno);
		this.motocicletas.asignarChofer(choferUno);
		this.motocicletas.asignarChofer(choferDos);

		assertFalse(this.motocicletas.cambiarChofer(choferDos));
	}

	@Test
	public void dadoQueExisteUnColectivoConUnPasajeroYSeQuiereCambiarDeChoferQueNoSePueda() {

		this.colectivoAutobus.agregarPasajero(pasajeroUno);
		this.colectivoAutobus.asignarChofer(choferUno);
		this.colectivoAutobus.asignarChofer(choferDos);

		assertFalse(this.colectivoAutobus.cambiarChofer(choferDos));
	}

	@Test
	public void dadoQueExisteUnColectivoSeQuierenSubir21PasajerosYNoSePuede() {
		this.colectivoAutobus.asignarChofer(choferUno);

		for (int i = 1; i <= 21; i++) {
			Boolean pasajeraAgregada = this.colectivoAutobus.agregarPasajero(new Pasajero("Evangelina " + i, 22, 12345678));

			if (i == 21) {
				assertFalse(pasajeraAgregada);
			}
		}

		assertEquals(20, this.colectivoAutobus.getPasajeros().size());
	}
}