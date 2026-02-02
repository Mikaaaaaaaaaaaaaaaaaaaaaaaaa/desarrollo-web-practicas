package ar.edu.unlam.dominio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

import ar.edu.unlam.dominio.enums.TipoSala;
import ar.edu.unlam.dominio.exception.ClienteExistenteException;
import ar.edu.unlam.dominio.exception.EventoDuplicadoException;
import ar.edu.unlam.dominio.exception.EventoNoEncontradoException;

public class TestEmpresaEventos {
	
	@Test
	public void dadoQueExisteUnaEmpresaCuandoAgregoUnClienteObtengoUnResultadoExitoso() throws ClienteExistenteException {
		GestorEmpresa gestorEmpresa = new GestorEmpresa();
		Cliente cliente = new Cliente(123, "ApellidoCliente", "NombreCliente");
		
		assertTrue(gestorEmpresa.agregarCliente(cliente));
	}
	
	@Test
	public void dadoQueExisteUnaEmpresaCuandoAgregoUnEventoExistenteObtengoUnaEventoDuplicadoException() throws EventoDuplicadoException {
		GestorEmpresa gestorEmpresa = new GestorEmpresa();

		Cliente clienteCreadoUno = new Cliente(1, "ApellidoClienteUno", "NombreClienteUno");
		LocalDate fechaCreadaUno = LocalDate.of(2025, 10, 30);
		Evento eventoCreadoUno = new EventoTaller("1", fechaCreadaUno, "NombreEvento", TipoSala.SALA_CHICA, clienteCreadoUno, 5, 5);

		gestorEmpresa.agregarEvento(eventoCreadoUno);

		try {

			Cliente clienteCreadoDos = new Cliente(2, "ApellidoClienteDos", "NombreClienteDos");
			LocalDate fechaCreadaDos = LocalDate.of(2025, 10, 30);
			Evento eventoCreadoDos = new EventoTaller("1", fechaCreadaDos, "NombreEvento", TipoSala.SALA_CHICA, clienteCreadoDos, 5, 5);

			gestorEmpresa.agregarEvento(eventoCreadoDos);

		} catch (EventoDuplicadoException eventoDuplicadoException) {
			assertEquals("Error, evento duplicado.", eventoDuplicadoException.getMessage());
		}
	}
		
	@Test
	public void dadoQueExisteUnaEmpresaConEventosCuandoBuscoUnEventoExistentePorSuCodigoObtengoElEvento() throws EventoDuplicadoException {
		GestorEmpresa gestorEmpresa = new GestorEmpresa();

		Cliente clienteCreadoUno = new Cliente(1, "ApellidoClienteUno", "NombreClienteUno");
		LocalDate fechaCreadaUna = LocalDate.of(2025, 10, 30);
		Evento eventoCreadoUno = new EventoTaller("1", fechaCreadaUna, "NombreEvento", TipoSala.SALA_CHICA, clienteCreadoUno, 5, 5);

		gestorEmpresa.agregarEvento(eventoCreadoUno);

		Cliente clienteCreadoDos = new Cliente(2, "ApellidoClienteDos", "NombreClienteDos");
		LocalDate fechaCreadaDos = LocalDate.of(2025, 10, 30);
		Evento eventoCreadoDos = new EventoConferencia("2", fechaCreadaDos, "NombreEvento", TipoSala.SALA_CHICA, clienteCreadoDos, "TemaTratar");

		gestorEmpresa.agregarEvento(eventoCreadoDos);

		Evento eventoEncontradoUno = gestorEmpresa.buscarEventoPorCodigoAlfanumerico("1");
		Evento eventoEncontradoDos = gestorEmpresa.buscarEventoPorCodigoAlfanumerico("2");

		assertEquals("1", eventoEncontradoUno.getCodigoAlfanumerico());
		assertEquals("2", eventoEncontradoDos.getCodigoAlfanumerico());
	}
	
	@Test
	public void dadoQueExisteUnaEmpresaConEventosCuandoVerificoSiUnClienteSeEncuentraEntreLosParticipantesDeUnEventoPorClienteYExisteObtengoUnResultadoPositivo() throws EventoDuplicadoException, ClienteExistenteException {
		GestorEmpresa gestorEmpresa = new GestorEmpresa();
		
		Cliente clienteCreadoUno = new Cliente(1, "ApellidoClienteUno", "NombreClienteUno");
		LocalDate fechaCreadaUna = LocalDate.of(2025, 10, 30);
		Evento eventoCreadoUno = new EventoTaller("1", fechaCreadaUna, "NombreEvento", TipoSala.SALA_CHICA, clienteCreadoUno, 5, 5);
		
		gestorEmpresa.agregarCliente(clienteCreadoUno);
		gestorEmpresa.agregarEvento(eventoCreadoUno);
		
		Cliente clienteEncontrado = gestorEmpresa.buscarClientePorCliente(clienteCreadoUno);
		
		assertEquals(clienteCreadoUno, clienteEncontrado);
	}
	
	@Test(expected = ClienteExistenteException.class)
	public void dadoQueExisteUnaEmpresaConEventosCuandoAgregoUnClienteAUnEventoDondeExisteElClienteObtengoUnaClienteExistenteEnEventoException() throws ClienteExistenteException, EventoNoEncontradoException, EventoDuplicadoException {
		GestorEmpresa gestorEmpresa = new GestorEmpresa();

		Cliente clienteCreadoUno = new Cliente(1, "ApellidoClienteUno", "NombreClienteUno");
		LocalDate fechaCreadaUna = LocalDate.of(2025, 10, 30);
		Evento eventoCreadoUno = new EventoTaller("1", fechaCreadaUna, "NombreEvento", TipoSala.SALA_CHICA, clienteCreadoUno, 5, 5);

		gestorEmpresa.agregarEvento(eventoCreadoUno);

		LocalDate fechaCreadaDos = LocalDate.of(2025, 10, 30);
		Evento eventoCreadoDos = new EventoTaller("2", fechaCreadaDos, "NombreEvento", TipoSala.SALA_CHICA, clienteCreadoUno, 5, 5);

		gestorEmpresa.agregarEvento(eventoCreadoDos);

		gestorEmpresa.agregarClienteEvento("1", clienteCreadoUno);
		gestorEmpresa.agregarClienteEvento("1", clienteCreadoUno);
	}
	
	@Test
	public void dadoQueExisteUnaEmpresaConEventosCuandoAgregoUnClienteAUnTallerSinCupoDondeNoExisteElClienteObtengoUnResultadoFallido() throws EventoDuplicadoException, ClienteExistenteException, EventoNoEncontradoException {
		GestorEmpresa gestorEmpresa = new GestorEmpresa();

		Cliente clienteCreadoUno = new Cliente(1, "ApellidoClienteUno", "NombreClienteUno");
		LocalDate fechaCreadaUna = LocalDate.of(2025, 10, 30);
		EventoTaller eventoCreadoUno = new EventoTaller("1", fechaCreadaUna, "NombreEvento", TipoSala.SALA_CHICA, clienteCreadoUno, 1, 5);

		gestorEmpresa.agregarEvento(eventoCreadoUno);

		Cliente clienteCreadoDos = new Cliente(2, "ApellidoClienteDos", "NombreClienteDos");

		assertTrue(gestorEmpresa.agregarClienteEvento("1", clienteCreadoUno));
		assertFalse(gestorEmpresa.agregarClienteEvento("1", clienteCreadoDos));
	}
	
	@Test
	public void dadoQueExisteUnaEmpresaConEventosCuandoConsultoLaRecaudacionTotalDeUnEventoTallerCon10ParticipantesRecibo250000() throws ClienteExistenteException, EventoNoEncontradoException, EventoDuplicadoException {
		GestorEmpresa gestorEmpresa = new GestorEmpresa();
		
		Cliente clienteCreadoUno = new Cliente(1, "ApellidoClienteUno", "NombreClienteUno");
		LocalDate fechaCreadaUna = LocalDate.of(2025, 10, 30);
		EventoTaller eventoCreadoUno = new EventoTaller("1", fechaCreadaUna, "NombreEvento", TipoSala.SALA_CHICA, clienteCreadoUno, 15, 5);
		
		gestorEmpresa.agregarEvento(eventoCreadoUno);
		
		Cliente clienteCreadoDos = new Cliente(2, "ApellidoClienteDos", "NombreClienteDos");
		Cliente clienteCreadoTres = new Cliente(3, "ApellidoClienteTres", "NombreClienteTres");
		Cliente clienteCreadoCuatro = new Cliente(4, "ApellidoClienteCuatro", "NombreClienteCuatro");
		Cliente clienteCreadoCinco = new Cliente(5, "ApellidoClienteCinco", "NombreClienteCinco");
		Cliente clienteCreadoSeis = new Cliente(6, "ApellidoClienteSeis", "NombreClienteSeis");
		Cliente clienteCreadoSiete = new Cliente(7, "ApellidoClienteSiete", "NombreClienteSiete");
		Cliente clienteCreadoOcho = new Cliente(8, "ApellidoClienteOcho", "NombreClienteOcho");
		Cliente clienteCreadoNueve = new Cliente(9, "ApellidoClienteNueve", "NombreClienteNueve");
		Cliente clienteCreadoDiez = new Cliente(10, "ApellidoClienteDiez", "NombreClienteDiez");
		
		gestorEmpresa.agregarClienteEvento("1", clienteCreadoUno);
		gestorEmpresa.agregarClienteEvento("1", clienteCreadoDos);
		gestorEmpresa.agregarClienteEvento("1", clienteCreadoTres);
		gestorEmpresa.agregarClienteEvento("1", clienteCreadoCuatro);
		gestorEmpresa.agregarClienteEvento("1", clienteCreadoCinco);
		gestorEmpresa.agregarClienteEvento("1", clienteCreadoSeis);
		gestorEmpresa.agregarClienteEvento("1", clienteCreadoSiete);
		gestorEmpresa.agregarClienteEvento("1", clienteCreadoOcho);
		gestorEmpresa.agregarClienteEvento("1", clienteCreadoNueve);
		gestorEmpresa.agregarClienteEvento("1", clienteCreadoDiez);
		
		assertEquals(250000, eventoCreadoUno.calcularPrecioTotalTaller(), 0.01);
	}
	
	@Test
	public void dadoQueExisteUnaEmpresaConEventos3ConferenciasObtengoUnaListaCon3Conferencias() throws EventoDuplicadoException {
		GestorEmpresa gestorEmpresa = new GestorEmpresa();

		Cliente clienteCreadoUno = new Cliente(1, "ApellidoClienteUno", "NombreClienteUno");
		LocalDate fechaCreadaUno = LocalDate.of(2025, 10, 30);
		Evento eventoCreadoUno = new EventoConferencia("1", fechaCreadaUno, "NombreEvento", TipoSala.SALA_CHICA, clienteCreadoUno, "TemaTratar");

		Cliente clienteCreadoDos = new Cliente(2, "ApellidoClienteDos", "NombreClienteDos");
		LocalDate fechaCreadaDos = LocalDate.of(2025, 10, 30);
		Evento eventoCreadoDos = new EventoConferencia("2", fechaCreadaDos, "NombreEvento", TipoSala.SALA_CHICA, clienteCreadoDos, "TemaTratar");

		Cliente clienteCreadoTres = new Cliente(3, "ApellidoClienteDos", "NombreClienteDos");
		LocalDate fechaCreadaTres = LocalDate.of(2025, 10, 30);
		Evento eventoCreadoTres = new EventoConferencia("3", fechaCreadaTres, "NombreEvento", TipoSala.SALA_CHICA, clienteCreadoTres, "TemaTratar");

		gestorEmpresa.agregarEvento(eventoCreadoUno);
		gestorEmpresa.agregarEvento(eventoCreadoDos);
		gestorEmpresa.agregarEvento(eventoCreadoTres);

		assertEquals(3, gestorEmpresa.obtenerListaConferenciasExistentes().size());
	}
}