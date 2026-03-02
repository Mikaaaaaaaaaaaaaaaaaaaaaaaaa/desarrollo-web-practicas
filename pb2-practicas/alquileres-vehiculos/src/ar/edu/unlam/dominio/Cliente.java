package ar.edu.unlam.dominio;

public abstract class Cliente implements Comparable<Cliente> {

	private String identificador;

	public Cliente(String identificador) {
		this.identificador = identificador;
	}

	public String getIdentificador() {
		return identificador;
	}

	@Override
	public int compareTo(Cliente clienteDos) {
		return this.identificador.compareTo(clienteDos.getIdentificador());
	}
}