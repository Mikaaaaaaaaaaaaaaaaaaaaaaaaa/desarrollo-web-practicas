package ar.edu.unlam.dominio;

import java.util.Comparator;

public class ClientePorApellidoComparator implements Comparator<Cliente> {

	@Override
	public int compare(Cliente clienteUno, Cliente clienteDos) {

		Integer comparacionApellido = clienteUno.getApellido().compareTo(clienteDos.getApellido());

		if (comparacionApellido != 0) {
			return comparacionApellido;
		}

		return clienteUno.getDocumento().compareTo(clienteDos.getDocumento());
	}
}