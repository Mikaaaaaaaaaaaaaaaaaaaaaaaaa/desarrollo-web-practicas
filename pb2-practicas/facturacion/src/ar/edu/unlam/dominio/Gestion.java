package ar.edu.unlam.dominio;

import java.util.Set;
import java.util.TreeSet;

import ar.edu.unlam.dominio.exception.ClienteDuplicadoException;
import ar.edu.unlam.dominio.exception.ClienteInexistenteException;
import ar.edu.unlam.dominio.exception.NroCteDuplicadoException;

public class Gestion {

	private Set<Cliente> clientes;
	private Set<Factura> facturas;

	public Gestion() {
		this.clientes = new TreeSet<>();
		this.facturas = new TreeSet<>();
	}

	public void agregarCliente(Cliente cliente) throws ClienteDuplicadoException {
		if (this.clientes.contains(cliente) == false) {
			this.clientes.add(cliente);
			return;
		}

		throw new ClienteDuplicadoException("Error, cliente duplicado.");
	}

	public boolean existeCliente(Cliente cliente) throws ClienteDuplicadoException {
		if (this.clientes.contains(cliente) == true) {
			return true;
		}

		return false;
	}

	public Set<Cliente> getClientes() {
		return this.clientes;
	}

	public TreeSet<Factura> getFacturas() {
		return (TreeSet<Factura>) facturas;
	}

	public void agregarFactura(Factura factura1) throws ClienteInexistenteException, NroCteDuplicadoException {
		if (getClientes().size() == 0) {
			throw new ClienteInexistenteException("Error, cliente inexistente.");
		}

		if (this.facturas.contains(factura1) == true) {
			throw new NroCteDuplicadoException("Error, número de comprobante duplicado.");
		}

		if (this.facturas.contains(factura1) == false) {
			this.facturas.add(factura1);
			return;
		}

		return;
	}

	public TreeSet<Monotributista> getMonotributistas() {
		TreeSet<Monotributista> monotributistas = new TreeSet<>();

		for (Cliente cliente : this.clientes) {
			if (cliente instanceof Monotributista) {
				Monotributista monotributista = (Monotributista) cliente;
				monotributistas.add(monotributista);
			}
		}

		return monotributistas;
	}

	public TreeSet<Factura> facturasOrdenadasDescendenteSubTotal() {
		TreeSet<Factura> facturasOrdenadasDescendenteSubTotal = new TreeSet<>(new ComparatorfacturasOrdenadasDescendenteSubTotal());

		for (Factura factura : this.facturas) {
			facturasOrdenadasDescendenteSubTotal.add(factura);
		}

		return facturasOrdenadasDescendenteSubTotal;
	}
}