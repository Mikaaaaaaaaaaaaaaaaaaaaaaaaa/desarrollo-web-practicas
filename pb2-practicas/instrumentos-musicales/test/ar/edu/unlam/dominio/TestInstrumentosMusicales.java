package ar.edu.unlam.dominio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unlam.dominio.enums.EnumMarca;
import ar.edu.unlam.dominio.exception.ExceptionInstrumentoDuplicado;
import ar.edu.unlam.dominio.exception.ExceptionNumeroSerie;
import ar.edu.unlam.dominio.exception.ExceptionNumeroSerieVenta;
import ar.edu.unlam.dominio.exception.ExceptionValorBaseInstrumento;

public class TestInstrumentosMusicales {
	
	private GestorInstrumentoMusical gestorInstrumentoMusical;
	
	private Instrumento instrumentoGuitarraUno;
	
	private Instrumento instrumentoBajo;
	
	private Instrumento instrumentoBateriaUno;
	private Instrumento instrumentoBateriaDos;
	private Venta venta;
	
	private Instrumento instrumentoGuitarraDos;
	private Instrumento instrumentoBateriaTres;
	private Instrumento instrumentoBajoDos;
	
	private Venta ventaGuitarraDos;
	private Venta ventaBateriaTres;
	private Venta ventaBajoDos;
	
	private Instrumento instrumentoBateriaCuatro;
	
	private Instrumento instrumentoGuitarraTres;
	private Instrumento instrumentoBajoTres;
	private Venta ventaGuitarraTres;
	private Venta ventaBajoTres;
	
	private Instrumento instrumentoGuitarraCuatro;
	private Instrumento instrumentoBajoCuatro;
	private Instrumento instrumentoBateriaCinco;
	
	private Instrumento instrumentoBateriaSeis;
	private Instrumento instrumentoBateriaSiete;
	private Instrumento instrumentoBateriaOcho;
	
	@Before
	public void inicializacion() throws ExceptionNumeroSerie {
		this.gestorInstrumentoMusical = new GestorInstrumentoMusical();

		this.instrumentoGuitarraUno = new Guitarra(EnumMarca.FENDER, "123-A", 9, 50.0);

		this.instrumentoBajo = new Bajo(EnumMarca.FENDER, "123-B", 10, 0.0);

		this.instrumentoBateriaUno = new Bateria(EnumMarca.YAMAHA, "123-C", 11, 50.0);
		this.instrumentoBateriaDos = new Bateria(EnumMarca.YAMAHA, "123-D", 11, 50.0);
		this.venta = new Venta(1, instrumentoBateriaUno, 50.0);
		this.venta = new Venta(2, instrumentoBateriaDos, 50.0);

		this.instrumentoGuitarraDos = new Guitarra(EnumMarca.FENDER, "123-E", 12, 50.0);
		this.instrumentoBateriaTres = new Bateria(EnumMarca.FENDER, "123-F", 13, 50.0);
		this.instrumentoBajoDos = new Bajo(EnumMarca.FENDER, "123-G", 14, 0.0);

		this.ventaGuitarraDos = new Venta(3, instrumentoGuitarraDos, 50.0);
		this.ventaBateriaTres = new Venta(4, instrumentoBateriaTres, 50.0);
		this.ventaBajoDos = new Venta(5, instrumentoBajoDos, 50.0);

		this.instrumentoBateriaCuatro = new Bateria(EnumMarca.FENDER, "123-H", 15, 50.0);

		this.instrumentoGuitarraTres = new Guitarra(EnumMarca.FENDER, "123-I", 16, 50.0);
		this.instrumentoBajoTres = new Bajo(EnumMarca.FENDER, "123-J", 17, 50.0);
		this.ventaGuitarraTres = new Venta(6, instrumentoGuitarraTres, 50.0);
		this.ventaBajoTres = new Venta(7, instrumentoBajoTres, 50.0);

		this.instrumentoGuitarraCuatro = new Guitarra(EnumMarca.FENDER, "123-K", 18, 50.0);
		this.instrumentoBajoCuatro = new Bajo(EnumMarca.FENDER, "123-L", 19, 50.0);
		this.instrumentoBateriaCinco = new Bateria(EnumMarca.FENDER, "123-M", 20, 50.0);

		this.instrumentoBateriaSeis = new Bateria(EnumMarca.FENDER, "123-Ñ", 21, 50.0);
		this.instrumentoBateriaSiete = new Bateria(EnumMarca.FENDER, "123-O", 22, 100.0);
		this.instrumentoBateriaOcho = new Bateria(EnumMarca.FENDER, "123-P", 23, 200.0);
	}
	
	@Test
	public void deberiaPermitirseElRegistroDeUnaGuitarra() throws ExceptionValorBaseInstrumento, ExceptionInstrumentoDuplicado {

		assertTrue(this.gestorInstrumentoMusical.registrarInstrumento(this.instrumentoGuitarraUno));
	}
	
	@Test(expected = ExceptionValorBaseInstrumento.class)
	public void noDeberiaPermitirseElRegistroDeUnBajoConPrecioBaseInvalido() throws ExceptionValorBaseInstrumento, ExceptionInstrumentoDuplicado {

		assertFalse(this.gestorInstrumentoMusical.registrarInstrumento(this.instrumentoBajo));
	}
	
	@Test(expected = ExceptionNumeroSerieVenta.class)
	public void noDeberiaPermitirseElRegistroDeUnaBateriaEnUnaVentaConElMismoNumeroDeSerie() throws ExceptionNumeroSerieVenta, ExceptionInstrumentoDuplicado {
		this.gestorInstrumentoMusical.registrarVenta(this.venta);

		assertFalse(this.gestorInstrumentoMusical.registrarVenta(this.venta));
	}
	
	@Test
	public void deberiaPermitirseRegistrarUnaVentaConUnaGuitarraUnaBateriaYUnBajo() throws ExceptionValorBaseInstrumento, ExceptionInstrumentoDuplicado, ExceptionNumeroSerieVenta {

		assertTrue(this.gestorInstrumentoMusical.registrarVenta(this.ventaGuitarraDos));
		assertTrue(this.gestorInstrumentoMusical.registrarVenta(this.ventaBateriaTres));
		assertTrue(this.gestorInstrumentoMusical.registrarVenta(this.ventaBajoDos));
	}
	
	@Test
	public void deberiaPoderCalcularseElPrecioFinalDeUnaBateria() throws ExceptionValorBaseInstrumento, ExceptionInstrumentoDuplicado {
		this.gestorInstrumentoMusical.registrarInstrumento(this.instrumentoBateriaCuatro);

		assertEquals(65.0, this.instrumentoBateriaCuatro.calcularPrecioFinal(), 0.01);
	}
	
	@Test
	public void deberiaPoderCalcularseElPrecioFinalDeUnaVentaConUnaGuitarraYUnBajo() throws ExceptionNumeroSerieVenta, ExceptionInstrumentoDuplicado, ExceptionValorBaseInstrumento {
		this.gestorInstrumentoMusical.registrarInstrumento(this.instrumentoGuitarraTres);
		this.gestorInstrumentoMusical.registrarInstrumento(this.instrumentoBajoTres);

		this.gestorInstrumentoMusical.registrarVenta(this.ventaGuitarraTres);

		this.ventaGuitarraTres.setGestorInstrumentoMusical(this.gestorInstrumentoMusical);

		assertEquals(20130.0, this.ventaGuitarraTres.calcularPrecioFinal(), 0.01);
	}
	
	@Test
	public void deberiaPoderObtenerseUnaColeccionDeTodosLosInstrumentosOrdenadosPorPrecioFinalDescendente() throws ExceptionValorBaseInstrumento, ExceptionInstrumentoDuplicado {
		this.gestorInstrumentoMusical.registrarInstrumento(this.instrumentoGuitarraCuatro);
		this.gestorInstrumentoMusical.registrarInstrumento(this.instrumentoBajoCuatro);
		this.gestorInstrumentoMusical.registrarInstrumento(this.instrumentoBateriaCinco);

		assertEquals(20055.0, this.gestorInstrumentoMusical.instrumentosOrdenadosDescendentePrecioFinal().first().calcularPrecioFinal(), 0.01);
		assertEquals(65.0, this.gestorInstrumentoMusical.instrumentosOrdenadosDescendentePrecioFinal().last().calcularPrecioFinal(), 0.01);
	}
	
	@Test
	public void deberiaPoderObtenerseUnaColeccionDeBateriasOrdenadasPorPrecioFinalAscendente() throws ExceptionValorBaseInstrumento, ExceptionInstrumentoDuplicado {
		this.gestorInstrumentoMusical.registrarInstrumento(this.instrumentoBateriaSeis);
		this.gestorInstrumentoMusical.registrarInstrumento(this.instrumentoBateriaSiete);
		this.gestorInstrumentoMusical.registrarInstrumento(this.instrumentoBateriaOcho);

		assertEquals(65.0, this.gestorInstrumentoMusical.instrumentoBateriasOrdenadasAscendentePrecioFinal().first().calcularPrecioFinal(), 0.01);
		assertEquals(260.0, this.gestorInstrumentoMusical.instrumentoBateriasOrdenadasAscendentePrecioFinal().last().calcularPrecioFinal(), 0.01);
	}
}