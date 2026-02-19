package ar.edu.unlam.dominio.exception;

public class ClienteInexistenteException extends Exception {

	public ClienteInexistenteException(String mensaje) {
		super(mensaje);
	}
}