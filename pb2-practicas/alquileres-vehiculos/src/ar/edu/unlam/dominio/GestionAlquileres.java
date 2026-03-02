package ar.edu.unlam.dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ar.edu.unlam.dominio.exception.ClienteDuplicadoException;
import ar.edu.unlam.dominio.exception.ClienteInexistenteException;
import ar.edu.unlam.dominio.exception.NroContratoDuplicadoException;

public class GestionAlquileres {

	private Set<Cliente> clientes;
	private Set<Contrato> contratos;

	public GestionAlquileres() {
		this.clientes = new TreeSet<>();
		this.contratos = new TreeSet<>();
	}

	public Set<Cliente> getClientes() {
		return clientes;
	}

	public Set<Contrato> getContratos() {
		return contratos;
	}

	public Boolean agregarCliente(Cliente cliente) throws ClienteDuplicadoException {
		if (this.clientes.contains(cliente) == true) {
			throw new ClienteDuplicadoException("Error, cliente duplicado.");
		}

		return this.clientes.add(cliente);
	}

	public Boolean agregarContrato(Contrato contratoUno) throws NroContratoDuplicadoException, ClienteInexistenteException {
		if (this.contratos.contains(contratoUno) == true) {
			throw new NroContratoDuplicadoException("Error, contrato duplicado.");
		}

		if (this.clientes.contains(contratoUno.getCliente()) == false) {
			throw new ClienteInexistenteException("Error, cliente inexistente.");
		}

		return this.contratos.add(contratoUno);
	}

	public Double calcularRecargo(Contrato contrato, Cliente cliente) {

		Double recargoCalculado = 0.0;
		if (cliente instanceof ClienteParticular) {
			recargoCalculado = contrato.getMontoBase() * 1.15;
		} else if (cliente instanceof ClienteEmpresa) {
			recargoCalculado = contrato.getMontoBase() * 1.08;
		} else if (cliente instanceof ClientePremium) {
			recargoCalculado = contrato.getMontoBase() * 1.0;
		}

		return recargoCalculado;
	}

	public ArrayList<Cliente> getClientesPremium() {

		ArrayList<Cliente> clientesPremium = new ArrayList<>();
		for (Cliente cliente : this.clientes) {
			if (cliente instanceof ClientePremium) {
				clientesPremium.add(cliente);
			}
		}

		return clientesPremium;
	}

	public List<Contrato> listaContratos() {

		ArrayList<Contrato> contratos = new ArrayList<>();
		for (Contrato contrato : this.contratos) {
			contratos.add(contrato);
		}

		return contratos;
	}

	public List<Contrato> queSeMuestrenContratosOrdenadosPorMontoBaseDesc() {

		TreeSet<Contrato> contratosOrdenadosDescendenteMontoBase = new TreeSet<>(new ContratosOrdenadosDescendenteMontoBase());
		for (Contrato contrato : this.contratos) {
			contratosOrdenadosDescendenteMontoBase.add(contrato);
		}

		return new ArrayList<>(contratosOrdenadosDescendenteMontoBase);
	}
}