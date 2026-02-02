package ar.edu.unlam.dominio;

import java.util.Comparator;

public class ComparatorUsuariosOrdeadosCorreoDescendente implements Comparator<Usuario> {

	@Override
	public int compare(Usuario usuarioUno, Usuario usuarioDos) {

		return usuarioDos.getCorreo().compareTo(usuarioUno.getCorreo());
	}
}