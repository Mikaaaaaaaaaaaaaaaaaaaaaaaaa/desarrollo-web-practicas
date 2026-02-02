package ar.edu.unlam.dominio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unlam.dominio.Biblioteca;
import ar.edu.unlam.dominio.Estudiante;
import ar.edu.unlam.dominio.GeografiaLibro;
import ar.edu.unlam.dominio.HistoriaLibro;
import ar.edu.unlam.dominio.Libro;
import ar.edu.unlam.dominio.MatematicasLibro;

public class TestBibliotecaNacional {

	private Biblioteca bibliotecaGestora;
	
	private Libro historiaLibro;
	private Libro geografiaLibro;
	private Libro matematicasLibro;
	
	private Estudiante estudianteUno;
	private Estudiante estudianteDos;
	
	@Before 
	public void inicializacionDeVariables() {
		this.bibliotecaGestora = new Biblioteca();
		
		this.historiaLibro = new HistoriaLibro(50,"Historia Argentina","Evangelina");
		this.geografiaLibro = new GeografiaLibro(15,"Geografía Argentina", "Evangelina");
		this.matematicasLibro = new MatematicasLibro(5, "Expresiones algebraicas", "Evangelina");
		
		this.estudianteUno = new Estudiante(12345678, "Evangelina","Lewandowski");
		this.estudianteDos = new Estudiante(87654321, "Evangelina", "Lewandowski");

		this.bibliotecaGestora.agregarLibro(historiaLibro);
		this.bibliotecaGestora.agregarLibro(geografiaLibro);
		this.bibliotecaGestora.agregarLibro(matematicasLibro);
	}
	
	@Test
	public void dadoQueHayUnLibroDisponibleElEstudiantePuedePedirlo() {
		
		assertTrue(this.bibliotecaGestora.prestarLibro(geografiaLibro, estudianteUno));
	}
	
	@Test
	public void dadoQueUnLibroFuePrestadoOtroEstudianteNoPuedePedirlo() {
		this.estudianteUno.adquirirLibro();
		this.estudianteUno.adquirirLibro();
		
		assertFalse(this.bibliotecaGestora.prestarLibro(geografiaLibro, estudianteUno));
	}
	
	@Test
	public void dadoQueUnEstudiantePidio2LibrosNoPuedeSolicitarOtroMas() {
		
		this.estudianteUno.adquirirLibro();
		this.estudianteUno.adquirirLibro();
		
		assertFalse(this.estudianteUno.adquirirLibro());
	}
	
	@Test 
	
	public void dadoQueUnLibroPrestadoFueDevueltoSePuedePrestarDeNuevo() {
		this.estudianteUno.adquirirLibro();
		this.bibliotecaGestora.prestarLibro(geografiaLibro, estudianteUno);
		this.estudianteUno.devolverLibro();
		
		assertEquals(0, estudianteUno.getPrestarLibro(), 0.01);
		assertTrue(this.bibliotecaGestora.prestarLibro(geografiaLibro, estudianteDos));
	}
	
	@Test
	public void dadoQueHayUnLibroDeMatematicaFotocopiableSePuedeObtenerSuTexto() {

		assertEquals("¡LIBRO DE HISTORIA <3!.", this.bibliotecaGestora.obtenerFotocopiable());
	}
}