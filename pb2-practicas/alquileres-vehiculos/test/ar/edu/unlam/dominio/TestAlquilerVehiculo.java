package ar.edu.unlam.dominio;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unlam.dominio.exception.ClienteDuplicadoException;
import ar.edu.unlam.dominio.exception.ClienteInexistenteException;
import ar.edu.unlam.dominio.exception.NroContratoDuplicadoException;

public class TestAlquilerVehiculo {

	private GestionAlquileres gestionAlquileres;
	private ClienteEmpresa clienteEmpresaUno;
	private ClienteParticular clienteParticularUno;
	private ClientePremium clientePremiumUno;

	@Before
	public void setUp() {
		this.gestionAlquileres = new GestionAlquileres();

		this.clienteEmpresaUno = new ClienteEmpresa("30-11111111-1", "Empresa Uno S.A.", "AutoRent");
		this.clienteParticularUno = new ClienteParticular("12345678", "Juan", "Pérez");
		this.clientePremiumUno = new ClientePremium("87654321", "Ana", "López", 1000);
	}

	@Test
	public void queSePuedaAgregarUnClienteEmpresa() throws ClienteDuplicadoException, ClienteInexistenteException {
		this.gestionAlquileres.agregarCliente(this.clienteEmpresaUno);

//      Completar assertions.
		assertTrue(this.gestionAlquileres.getClientes().contains(this.clienteEmpresaUno));
	}

	@Test(expected = ClienteDuplicadoException.class)
	public void queNoSePuedaAgregarClienteDuplicado() throws ClienteDuplicadoException, ClienteInexistenteException {
		this.gestionAlquileres.agregarCliente(this.clienteEmpresaUno);

		ClienteEmpresa empresaDuplicada = new ClienteEmpresa("30-11111111-1", "Otra Empresa", "OtroNombre");
		this.gestionAlquileres.agregarCliente(empresaDuplicada);
	}

	@Test
	public void queSePuedaAgregarContrato() throws Exception {
		this.gestionAlquileres.agregarCliente(this.clienteEmpresaUno);

		Contrato contrato = new Contrato(1001, "2024-08-01", "2024-08-10", 10000.0, this.clienteEmpresaUno);
		this.gestionAlquileres.agregarContrato(contrato);

//      Completar.
		assertTrue(this.gestionAlquileres.getContratos().contains(contrato));
	}

	@Test(expected = ClienteInexistenteException.class)
	public void queNoSePuedaAgregarContratoAClienteInexistente() throws NroContratoDuplicadoException, ClienteInexistenteException {
		Contrato contrato = new Contrato(1002, "2024-08-01", "2024-08-10", 10000.0, this.clienteEmpresaUno);
		this.gestionAlquileres.agregarContrato(contrato);
	}

	@Test
	public void queCadaContratoTengaElRecargoCorrecto() throws Exception {
		this.gestionAlquileres.agregarCliente(this.clienteEmpresaUno);
		this.gestionAlquileres.agregarCliente(this.clienteParticularUno);
		this.gestionAlquileres.agregarCliente(this.clientePremiumUno);

		Contrato ContratoClienteCreadoUno = new Contrato(1, "2024-08-01", "2024-08-05", 1000.0, this.clienteEmpresaUno);
		Contrato ContratoClienteCreadoDos = new Contrato(2, "2024-08-01", "2024-08-05", 1000.0, this.clienteParticularUno);
		Contrato ContratoClienteCreadoTres = new Contrato(3, "2024-08-01", "2024-08-05", 1000.0, this.clientePremiumUno);

		this.gestionAlquileres.agregarContrato(ContratoClienteCreadoUno);
		this.gestionAlquileres.agregarContrato(ContratoClienteCreadoDos);
		this.gestionAlquileres.agregarContrato(ContratoClienteCreadoTres);

//		Completar asserts de recargo.
		assertTrue(this.gestionAlquileres.calcularRecargo(ContratoClienteCreadoUno, this.clienteEmpresaUno) == (1080));
		assertTrue(this.gestionAlquileres.calcularRecargo(ContratoClienteCreadoDos, this.clienteParticularUno) == (1150));
		assertTrue(this.gestionAlquileres.calcularRecargo(ContratoClienteCreadoTres, this.clientePremiumUno) == (1000));
	}

	@Test(expected = NroContratoDuplicadoException.class)
	public void queNoSePuedanAgregarContratosDuplicados() throws ClienteDuplicadoException, NroContratoDuplicadoException, ClienteInexistenteException {
		this.gestionAlquileres.agregarCliente(this.clienteEmpresaUno);

		Contrato contratoCreadoUno = new Contrato(2001, "2024-08-01", "2024-08-05", 5000.0, this.clienteEmpresaUno);
		Contrato contratoCreadoDos = new Contrato(2001, "2024-08-01", "2024-08-05", 7000.0, this.clienteEmpresaUno);

		this.gestionAlquileres.agregarContrato(contratoCreadoUno);
		this.gestionAlquileres.agregarContrato(contratoCreadoDos);
	}

	@Test
	public void queSeObtenganSoloLosClientesPremium() throws ClienteDuplicadoException {
		this.gestionAlquileres.agregarCliente(this.clienteEmpresaUno);
		this.gestionAlquileres.agregarCliente(this.clienteParticularUno);
		this.gestionAlquileres.agregarCliente(this.clientePremiumUno);

		List<Cliente> clientesPremium = this.gestionAlquileres.getClientesPremium();

//      Completar assertions.
		assertTrue(clientesPremium.get(0).equals(this.clientePremiumUno));
	}

	@Test
	public void queSeMuestrenContratosOrdenadosPorNroContrato() throws Exception {
		this.gestionAlquileres.agregarCliente(this.clienteEmpresaUno);

		Contrato contratoUno = new Contrato(5, "2024-08-01", "2024-08-05", 1000.0, this.clienteEmpresaUno);
		Contrato contratoDos = new Contrato(1, "2024-08-01", "2024-08-05", 1000.0, this.clienteEmpresaUno);
		Contrato contratoTres = new Contrato(3, "2024-08-01", "2024-08-05", 1000.0, this.clienteEmpresaUno);

		this.gestionAlquileres.agregarContrato(contratoUno);
		this.gestionAlquileres.agregarContrato(contratoDos);
		this.gestionAlquileres.agregarContrato(contratoTres);

		List<Contrato> contratos = this.gestionAlquileres.listaContratos();

//		Completar orden esperado.
		assertTrue(contratos.get(0).getNumeroContrato().equals(1));
		assertTrue(contratos.get(1).getNumeroContrato().equals(3));
		assertTrue(contratos.get(2).getNumeroContrato().equals(5));
	}

	@Test
	public void queSeMuestrenContratosOrdenadosPorMontoBaseDesc() throws ClienteDuplicadoException, NroContratoDuplicadoException, ClienteInexistenteException {
		this.gestionAlquileres.agregarCliente(this.clienteEmpresaUno);

		Contrato contratoUno = new Contrato(1, "2024-08-01", "2024-08-05", 1000.0, this.clienteEmpresaUno);
		Contrato contratoDos = new Contrato(2, "2024-08-01", "2024-08-05", 3000.0, this.clienteEmpresaUno);
		Contrato contratoTres = new Contrato(3, "2024-08-01", "2024-08-05", 2000.0, this.clienteEmpresaUno);

		this.gestionAlquileres.agregarContrato(contratoUno);
		this.gestionAlquileres.agregarContrato(contratoDos);
		this.gestionAlquileres.agregarContrato(contratoTres);

		List<Contrato> contratosOrdenadosDescendenteMontoBase = this.gestionAlquileres.queSeMuestrenContratosOrdenadosPorMontoBaseDesc();

//      Completar orden esperado.
		assertTrue(contratosOrdenadosDescendenteMontoBase.get(0).getMontoBase().equals(contratoDos.getMontoBase()));
		assertTrue(contratosOrdenadosDescendenteMontoBase.get(1).getMontoBase().equals(contratoTres.getMontoBase()));
		assertTrue(contratosOrdenadosDescendenteMontoBase.get(2).getMontoBase().equals(contratoUno.getMontoBase()));
	}
}