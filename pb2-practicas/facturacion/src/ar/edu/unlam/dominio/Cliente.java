package ar.edu.unlam.dominio;

public abstract class Cliente implements Comparable<Cliente> {

	private String codigoUnico;

	public Cliente(String codigoUnico) {
		this.codigoUnico = codigoUnico;
	}

	public String getCodigoUnico() {
		return codigoUnico;
	}

	@Override
	public int compareTo(Cliente clienteDos) {
		return this.codigoUnico.compareTo(clienteDos.getCodigoUnico());
	}
}