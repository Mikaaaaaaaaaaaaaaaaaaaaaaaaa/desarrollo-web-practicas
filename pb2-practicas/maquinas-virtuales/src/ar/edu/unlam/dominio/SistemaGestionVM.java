package ar.edu.unlam.dominio;

import java.util.TreeSet;

import ar.edu.unlam.dominio.exception.UsuarioDuplicadoException;

public class SistemaGestionVM {

	private TreeSet<Usuario> usuarios;

	public SistemaGestionVM() {
		this.usuarios = new TreeSet<>(new ComparatorUsuariosOrdeadosCorreoDescendente());
	}

	public void registrarUsuario(Usuario usuario) throws UsuarioDuplicadoException {
		if (this.usuarios.contains(usuario) == true) {
			throw new UsuarioDuplicadoException("Error, usuario duplicado.");
		}

		this.usuarios.add(usuario);
	}

	public Boolean agregarVmUsuarioRegistrado(Vm vm, String correo) {
		for (Usuario usuario : this.usuarios) {
			if (usuario.getCorreo().equals(correo)) {
				return usuario.agregarVmUsuario(vm);
			}
		}

		return false;
	}

	public TreeSet<Usuario> getUsuarios() {
		return usuarios;
	}

	public TreeSet<Usuario> obtenerUsuariosOrdenadosPorCorreoDescendente() {
		TreeSet<Usuario> usuariosOrdenados = new TreeSet<>(new ComparatorUsuariosOrdeadosCorreoDescendente());
		for (Usuario usuario : this.usuarios) {
			usuariosOrdenados.add(usuario);
		}

		return usuariosOrdenados;
	}
}