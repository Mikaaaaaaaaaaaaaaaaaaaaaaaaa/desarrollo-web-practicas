package ar.edu.unlam.dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario implements Comparable<Usuario> {

	private Integer documento;
	private String correo;
	private String contrasenia;

	private List<Vm> almacenamientoVm;

	public Usuario(Integer documento, String correo, String contrasenia) {
		this.documento = documento;
		this.correo = correo;
		this.contrasenia = contrasenia;
		this.almacenamientoVm = new ArrayList<>();
	}

	public String getCorreo() {
		return this.correo;
	}

	public Boolean agregarVmUsuario(Vm vm) {
		return this.almacenamientoVm.add(vm);
	}

	@Override
	public int hashCode() {
		return Objects.hash(correo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(correo, other.correo);
	}

	@Override
	public int compareTo(Usuario usuario) {
		return this.correo.compareTo(usuario.getCorreo());
	}
}