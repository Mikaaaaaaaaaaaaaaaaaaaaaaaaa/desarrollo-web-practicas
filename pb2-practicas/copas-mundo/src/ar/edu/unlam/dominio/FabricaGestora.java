package ar.edu.unlam.dominio;

import java.util.ArrayList;
import java.util.HashSet;

import ar.edu.unlam.dominio.exception.ClienteDuplicadoException;

public class FabricaGestora {

	private HashSet<Cliente> clientes;
	private ArrayList<Copa> copas;
	private ArrayList<Venta> ventas;

	public FabricaGestora() {
		this.clientes = new HashSet<>();
		this.copas = new ArrayList<>();
		this.ventas = new ArrayList<>();
	}

	public ArrayList<Copa> getCopas() {
		return copas;
	}

	public Boolean agregarCliente(Cliente cliente) throws ClienteDuplicadoException {
		if (this.clientes.contains(cliente)) {
			throw new ClienteDuplicadoException("Error, cliente duplicado.");
		}

		this.clientes.add(cliente);
		return true;
	}

	public Boolean agregarCopaMundo(Copa copa) {
		return this.copas.add(copa);
	}

	public ArrayList<Copa> obtenerCopaMundoEstandar() {
		ArrayList<Copa> copasEstandar = new ArrayList<>();

		for (Copa copaUno : this.copas) {
			if (copaUno instanceof CopaEstandar) {
				CopaEstandar copaEncontrada = (CopaEstandar) copaUno;
				copasEstandar.add(copaEncontrada);
			}
		}

		return copasEstandar;
	}

	public Copa obtenerCopaMundoIdentificador(Integer identificador) {
		for (Copa copa : this.copas) {
			if (copa.getIdentificador().equals(identificador)) {
				return copa;
			}
		}

		return null;
	}

	public Venta buscarVenta(Cliente cliente) {
		for (Venta venta : this.ventas) {
			if (venta.getCliente().equals(cliente)) {
				return venta;
			}
		}

		return null;
	}

	public void venderCopaMundoEstandar(Cliente cliente, CopaEstandar copaEstandar, Integer cantidad) {
		Venta ventaEncontrada = buscarVenta(cliente);

		if (ventaEncontrada == null) {
			ventaEncontrada = new Venta(cliente);
			copaEstandar.descontarCantidadStockDisponible(cantidad);
			ventaEncontrada.agregarCopaMundoVenta(copaEstandar);
			this.ventas.add(ventaEncontrada);
		}
	}

	public void venderCopaMundoPersonalizada(Cliente cliente, CopaPersonalizada copaPersonalizada) {
		Venta ventaEncontrada = buscarVenta(cliente);

		if (ventaEncontrada == null) {
			ventaEncontrada = new Venta(cliente);
			ventaEncontrada.agregarCopaMundoVenta(copaPersonalizada);
			this.copas.remove(copaPersonalizada);
			this.ventas.add(ventaEncontrada);
		}
	}
}