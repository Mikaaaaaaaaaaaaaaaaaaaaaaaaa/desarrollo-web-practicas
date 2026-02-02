package ar.edu.unlam.dominio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.TreeSet;

import org.junit.Test;

import ar.edu.unlam.dominio.exception.CantidadEscrituraSuperadaException;
import ar.edu.unlam.dominio.exception.CantidadHorasUsoExcedidaException;
import ar.edu.unlam.dominio.exception.UsuarioDuplicadoException;

public class TestMaquinasVirtuales {
	
	@Test(expected = UsuarioDuplicadoException.class)
	public void dadoQueExisteUnSistemaDeGestionDeMaquinasVirtualesCuandoAgregoUnUsuarioConUnCorreoExistenteObtengoUnaUsuarioDuplicadoException() throws UsuarioDuplicadoException {
		SistemaGestionVM sistemaGestionVM = new SistemaGestionVM();

		Usuario usuarioCreadoUno = new Usuario(1, "CorreoUsuario@gmail.com", "ContraseñaUsuario");
		Usuario usuarioCreadoDos = new Usuario(2, "CorreoUsuario@gmail.com", "ContraseñaUsuario");

		sistemaGestionVM.registrarUsuario(usuarioCreadoUno);
		sistemaGestionVM.registrarUsuario(usuarioCreadoDos);
	}
	
	@Test
	public void dadoQueExisteUnSistemaDeGestionDeMaquinasVirtualesConUsuariosValidosAlConsultarlosObtengoUnaColeccionDeUsuariosOrdenadosPorCorreoDescendentemente() throws UsuarioDuplicadoException {
		SistemaGestionVM sistemaGestionVm = new SistemaGestionVM();

		Usuario usuarioCreadoUno = new Usuario(1, "ACorreoUsuario@gmail.com", "ContraseñaUsuario");
		Usuario usuarioCreadoDos = new Usuario(2, "BCorreoUsuario@gmail.com", "ContraseñaUsuario");
		Usuario usuarioCreadoTres = new Usuario(3, "CCorreoUsuario@gmail.com", "ContraseñaUsuario");

		sistemaGestionVm.registrarUsuario(usuarioCreadoUno);
		sistemaGestionVm.registrarUsuario(usuarioCreadoDos);
		sistemaGestionVm.registrarUsuario(usuarioCreadoTres);

		TreeSet<Usuario> usuariosOrdenadosCorreoDescendente = sistemaGestionVm.obtenerUsuariosOrdenadosPorCorreoDescendente();

		assertEquals("CCorreoUsuario@gmail.com", usuariosOrdenadosCorreoDescendente.first().getCorreo());
		assertEquals("ACorreoUsuario@gmail.com", usuariosOrdenadosCorreoDescendente.last().getCorreo());
	}
	
	@Test
	public void dadoQueExisteUnSistemaDeGestionDeMaquinasVirtualesConAlMenosUnUsuarioCuandoAgregoUnaVMBaseDeDatosObtengoUnResultadoExitoso() throws UsuarioDuplicadoException {
		SistemaGestionVM sistemaGestionVm = new SistemaGestionVM();
		
		Usuario usuarioCreado = new Usuario(1, "CorreoUsuario@gmail.com", "ContraseñaUsuario");
		sistemaGestionVm.registrarUsuario(usuarioCreado);
		
		Vm vmBaseDeDatos = new VmBaseDeDatos(5);
		
		assertTrue(sistemaGestionVm.agregarVmUsuarioRegistrado(vmBaseDeDatos, "CorreoUsuario@gmail.com"));
	}
	
	@Test
	public void dadoQueExisteUnSistemaDeGestionDeMaquinasVirtualesConAlMenosUnUsuarioCuandoAgregoUnaVMAlmacenamientoDeImagenesObtengoUnResultadoExitoso() throws UsuarioDuplicadoException {
		SistemaGestionVM sistemaGestionVm = new SistemaGestionVM();

		Usuario usuarioCreado = new Usuario(1, "CorreoUsuario@gmail.com", "ContraseñaUsuario");
		sistemaGestionVm.registrarUsuario(usuarioCreado);

		Vm vmAlmacenamientoImagenes = new VmAlmacenamientoImagenes(10.0, 10.0, 5);

		assertTrue(sistemaGestionVm.agregarVmUsuarioRegistrado(vmAlmacenamientoImagenes, "CorreoUsuario@gmail.com"));
	}
	
	@Test
	public void dadoQueExisteUnSistemaDeGestionDeMaquinasVirtualesConAlMenosUnUsuarioYUnaVMBaseDeDatosCuandoSuperoLaCapacidadDeHorasDeUsoObtengoUnaCantidadDeHorasDeUsoExcedidaException() throws UsuarioDuplicadoException, CantidadHorasUsoExcedidaException {
		SistemaGestionVM sistemaGestionVm = new SistemaGestionVM();

		Usuario usuarioCreadoUno = new Usuario(1, "CorreoUsuario@gmail.com", "ContraseñaUsuario");
		VmBaseDeDatos vmBaseDeDatos = new VmBaseDeDatos(5);

		sistemaGestionVm.registrarUsuario(usuarioCreadoUno);
		sistemaGestionVm.agregarVmUsuarioRegistrado(vmBaseDeDatos, "CorreoUsuario@gmail.com");

		try {
			vmBaseDeDatos.horasUso(6);
		} catch (CantidadHorasUsoExcedidaException cantidadHorasUsoExcedidaException) {

			assertEquals("Error, se ha superado la cantidad de horas.", cantidadHorasUsoExcedidaException.getMessage());
		}
	}
	
	@Test
	public void dadoQueExisteUnSistemaDeGestionDeMaquinasVirtualesConAlMenosUnUsuarioYUnaVMAlmacenamientoDeImagenesCuandoSuperoLaCapacidadDeHorasDeUsoObtengoUnaCantidadEscriturasObtengoUnaCantidadDeEscriturasSuperadaException() throws UsuarioDuplicadoException, CantidadEscrituraSuperadaException {
		SistemaGestionVM sistemaGestionVm = new SistemaGestionVM();

		Usuario usuarioCreado = new Usuario(1, "CorreoUsuario@gmail.com", "ContraseñaUsuario");
		sistemaGestionVm.registrarUsuario(usuarioCreado);

		VmAlmacenamientoImagenes vmAlmacenamientoImagenes = new VmAlmacenamientoImagenes(10.0, 10.0, 5);
		sistemaGestionVm.agregarVmUsuarioRegistrado(vmAlmacenamientoImagenes, "CorreoUsuario@gmail.com");

		try {
			vmAlmacenamientoImagenes.lecturasEscriturasUso(11);
		} catch (CantidadEscrituraSuperadaException cantidadEscrituraSuperadaException) {
			assertEquals("Error, se ha superado la cantidad de escrituras.", cantidadEscrituraSuperadaException.getMessage());
		}
	}
	
	@Test
	public void dadoQueExisteUnSistemaDeGestionDeMaquinasVirtualesConAlMenosUnUsuarioYUnaVMBaseDeDatosUsadaCuandoObtengoElCostoFinalPor10GBY5HorasRecibo1125() throws UsuarioDuplicadoException {
		SistemaGestionVM sistemaGestionVm = new SistemaGestionVM();

		Usuario usuarioCreado = new Usuario(1, "CorreoUsuario@gmail.com", "ContraseñaUsuario");
		sistemaGestionVm.registrarUsuario(usuarioCreado);

		Vm vmBaseDeDatos = new VmBaseDeDatos(5);
		sistemaGestionVm.agregarVmUsuarioRegistrado(vmBaseDeDatos, "CorreoUsuario@gmail.com");
		vmBaseDeDatos.setCapacidadAlmacenamiento(10.0);

		assertEquals(1125, vmBaseDeDatos.calcularCostoExtra(), 0.01);
	}
	
	@Test
	public void dadoQueExisteUnSistemaDeGestionDeMaquinasVirtualesConAlMenosUnUsuarioYUnaVMAlmacenamientoDeImagenesUsadaCuandoObtengoElCostoFinalPor20LecturasY10EscriturasRecibo540() throws UsuarioDuplicadoException {
		SistemaGestionVM sistemaGestionVm = new SistemaGestionVM();

		Usuario usuarioCreado = new Usuario(1, "CorreoUsuario@gmail.com", "ContraseñaUsuario");
		sistemaGestionVm.registrarUsuario(usuarioCreado);

		VmAlmacenamientoImagenes vmAlmacenamientoImagenes = new VmAlmacenamientoImagenes(20.0, 10.0, 5);
		sistemaGestionVm.agregarVmUsuarioRegistrado(vmAlmacenamientoImagenes, "CorreoUsuario@gmail.com");

		assertEquals(540, vmAlmacenamientoImagenes.calcularCostoExtra(), 0.01);
	}
}