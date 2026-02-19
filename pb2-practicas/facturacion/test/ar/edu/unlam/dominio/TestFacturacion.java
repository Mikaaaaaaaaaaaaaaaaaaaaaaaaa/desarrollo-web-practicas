package ar.edu.unlam.dominio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unlam.dominio.exception.ClienteDuplicadoException;
import ar.edu.unlam.dominio.exception.ClienteInexistenteException;
import ar.edu.unlam.dominio.exception.NroCteDuplicadoException;

public class TestFacturacion {

	private Gestion gestion;
	private ResponsableInscripto responsable1, responsable2;
	private Monotributista monotributista1;
	private ConsumidorFinal consumidor1;
	private Factura factura1;

	@Before
	public void setUp() {
		this.gestion = new Gestion();

		this.responsable1 = new ResponsableInscripto("30-11111111-1", "Empresa Uno S.A.");
		this.responsable2 = new ResponsableInscripto("30-22222222-2", "Empresa Dos S.A.");
		this.monotributista1 = new Monotributista("20-11111111-1", "Zárate", "Ana", "Librería El Saber");
		this.consumidor1 = new ConsumidorFinal("12345678", "Pérez", "Juan");
		
//		Factura(numCte, fecha, subTotal, cliente) {
		this.factura1 = new Factura(1001, "2024-07-28", 15000.0, this.responsable1);
	}

	@Test
	public void queSePuedaAgregarUnClienteResponsableInscripto() throws ClienteDuplicadoException {
		this.gestion.agregarCliente(this.responsable1);

		assertTrue(this.gestion.existeCliente(this.responsable1));
		assertTrue(this.gestion.getClientes().contains(this.responsable1));

		assertFalse(this.gestion.existeCliente(this.consumidor1));
		assertFalse(this.gestion.getClientes().contains(this.consumidor1));
	}

	@Test(expected = ClienteDuplicadoException.class)
	public void queNoSePuedaAgregarUnClienteDuplicado() throws ClienteDuplicadoException {
		try {
			this.gestion.agregarCliente(this.responsable1);

		} catch (ClienteDuplicadoException clienteDuplicadoException) {
			fail();
		}

		Monotributista monotributistaDuplicado = new Monotributista(this.responsable1.getCuit(), "Álvarez", "Carlos", "Taller Mecánico");
		this.gestion.agregarCliente(monotributistaDuplicado);
	}

	@Test
	public void queSePuedaAgregarFactura() throws ClienteDuplicadoException, ClienteInexistenteException, NroCteDuplicadoException {
		this.gestion.agregarCliente(this.responsable1);
		this.gestion.agregarFactura(this.factura1);
		
		assertTrue(this.gestion.getFacturas().contains(this.factura1));
	}

	@Test(expected = ClienteInexistenteException.class)
	public void queNoSePuedaAgregarFacturaAClienteInexistente() throws ClienteDuplicadoException, ClienteInexistenteException, NroCteDuplicadoException {
		
		this.gestion.agregarFactura(this.factura1);
	}

	@Test
	public void queSeCreeMonotributistaConAtributosCorrectos() {
		
		assertEquals("20-11111111-1", this.monotributista1.getCuit());
		assertEquals("Zárate", this.monotributista1.getApellido());
		assertEquals("Ana", this.monotributista1.getNombre());
		assertEquals("Librería El Saber", this.monotributista1.getNombreFantasia());
	}

	@Test(expected = NroCteDuplicadoException.class)
	public void queNoSePuedaAgregarFacturasConNroCteDuplicado() throws NroCteDuplicadoException, ClienteDuplicadoException, ClienteInexistenteException {
		this.gestion.agregarCliente(this.responsable1);
		this.gestion.agregarCliente(this.monotributista1);
		this.gestion.agregarCliente(this.consumidor1);

		Factura factura2 = new Factura(this.factura1.getNroCte(), "2024-07-28", 1000.0, this.monotributista1);

		this.gestion.agregarFactura(this.factura1);
		this.gestion.agregarFactura(factura2);
	}

	@Test
	public void queCadaFacturaTengaElImpuestoCorrecto() throws ClienteDuplicadoException, ClienteInexistenteException, NroCteDuplicadoException {
		this.gestion.agregarCliente(this.responsable1);
		this.gestion.agregarCliente(this.monotributista1);
		this.gestion.agregarCliente(this.consumidor1);

		Factura factura1 = new Factura(1001, "2024-07-28", 1000.0, this.responsable1);
		Factura factura2 = new Factura(2001, "2024-07-28", 1000.0, this.monotributista1);
		Factura factura3 = new Factura(3001, "2024-07-28", 1000.0, this.consumidor1);

		this.gestion.agregarFactura(factura1);
		this.gestion.agregarFactura(factura2);
		this.gestion.agregarFactura(factura3);

		assertEquals(0.0, factura1.getImpuestos(), 0.01);
		assertEquals(105.0, factura2.getImpuestos(), 0.01);
		assertEquals(210.0, factura3.getImpuestos(), 0.01);
	}

	@Test
	public void queSeObtenganSoloLosMonotributistas() throws ClienteDuplicadoException {
		Monotributista monotributista1 = new Monotributista("20-11133111-1", "Zárate", "Ana", "Librería El Saber");
		Monotributista monotributista2 = new Monotributista("20-22224222-2", "Álvarez", "Carlos", "Taller Mecánico");

		this.gestion.agregarCliente(this.responsable1);
		this.gestion.agregarCliente(this.responsable2);
		
//      gestion.agregarCliente(monotributista1);
		
		this.gestion.agregarCliente(this.consumidor1);
		this.gestion.agregarCliente(monotributista1);
		this.gestion.agregarCliente(monotributista2);

		// Completar....
		this.gestion.getMonotributistas();

		assertTrue(this.gestion.getMonotributistas().first().equals(monotributista1));
		assertTrue(this.gestion.getMonotributistas().last().equals(monotributista2));
		assertEquals(2, this.gestion.getMonotributistas().size());
	}

	@Test
	public void queSeMuestrenFacturasOrdenadasPorNroCte() throws ClienteDuplicadoException, ClienteInexistenteException, NroCteDuplicadoException {
		Factura factura1 = new Factura(1005, "2024-07-28", 12000.0, this.responsable1);
		Factura factura2 = new Factura(1002, "2024-07-28", 8000.0, this.monotributista1);
		Factura factura3 = new Factura(1008, "2024-07-28", 4500.0, this.consumidor1);
		Factura factura4 = new Factura(1001, "2024-07-28", 15000.0, this.responsable2);

		this.gestion.agregarCliente(this.responsable1);
		this.gestion.agregarCliente(this.responsable2);
		this.gestion.agregarCliente(this.monotributista1);
		this.gestion.agregarCliente(this.consumidor1);

		this.gestion.agregarFactura(factura1);
		this.gestion.agregarFactura(factura2);
		this.gestion.agregarFactura(factura3);
		this.gestion.agregarFactura(factura4);

		// Completar.....
		assertTrue(this.gestion.getFacturas().first().equals(factura4));
		assertTrue(this.gestion.getFacturas().last().equals(factura3));
		assertEquals(4, this.gestion.getFacturas().size());
	}

	@Test
	public void queSeMuestrenFacturasOrdenadasPorTotalDesc() throws ClienteDuplicadoException, ClienteInexistenteException, NroCteDuplicadoException {
		Factura factura1 = new Factura(1005, "2024-07-28", 12000.0, this.responsable1);
		Factura factura2 = new Factura(1002, "2024-07-28", 8000.0, this.monotributista1);
		Factura factura3 = new Factura(1008, "2024-07-28", 4500.0, this.consumidor1);
		Factura factura4 = new Factura(1001, "2024-07-28", 15000.0, this.responsable2);

		this.gestion.agregarCliente(this.responsable1);
		this.gestion.agregarCliente(this.responsable2);
		this.gestion.agregarCliente(this.monotributista1);
		this.gestion.agregarCliente(this.consumidor1);

		this.gestion.agregarFactura(factura1);
		this.gestion.agregarFactura(factura2);
		this.gestion.agregarFactura(factura3);
		this.gestion.agregarFactura(factura4);

		// Completar....
		assertTrue(this.gestion.facturasOrdenadasDescendenteSubTotal().first().equals(factura4));
		assertTrue(this.gestion.facturasOrdenadasDescendenteSubTotal().last().equals(factura3));
	}
}