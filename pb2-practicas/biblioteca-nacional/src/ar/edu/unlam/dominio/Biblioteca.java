package ar.edu.unlam.dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Biblioteca {

	private List<Libro> libros;
	private List<Estudiante> estudiantes;
	private Set<Prestamo> prestamos;

	public Biblioteca() {
		this.libros = new ArrayList<>();
		this.estudiantes = new ArrayList<>();
		this.prestamos = new TreeSet<>();
	}

	public Boolean agregarLibro(Libro nuevoLibro) {
		
		return this.libros.add(nuevoLibro);
	}

	public Boolean prestarLibro(Libro libroPrestado, Estudiante estudiante) {
		if (estudiante.getPrestarLibro() >= 2) {
			return false;
		}

		if (estudiante.getLibroDisponible() == true) {
			Prestamo prestamo = new Prestamo(1, estudiante, libroPrestado);
			estudiante.adquirirLibro();
			return true;
		}

		return false;
	}

	public Boolean devolverLibro(Libro libroADevolver, Estudiante estudiante) {
		Prestamo prestamo = new Prestamo(1, estudiante, libroADevolver);
		estudiante.devolverLibro();

		return false;
	}

	public String obtenerFotocopiable() {
		for (Libro libro : this.libros) {
			if (libro instanceof MatematicasLibro || libro instanceof GeografiaLibro || libro instanceof HistoriaLibro) {
				if (libro instanceof FotocopiarLibro) {
					return ((FotocopiarLibro) libro).fotocopiarLibro();
				}
			}
		}

		return null;
	}
}