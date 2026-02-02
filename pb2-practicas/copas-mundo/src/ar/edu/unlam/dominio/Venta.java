package ar.edu.unlam.dominio;

import java.util.ArrayList;

public class Venta {

	private Cliente cliente;
	private ArrayList<Copa> copas;

	public Venta(Cliente cliente) {
		this.cliente = cliente;
		this.copas = new ArrayList<>();
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Boolean agregarCopaMundoVenta(Copa copa) {
		this.copas.add(copa);
		return true;
	}
}