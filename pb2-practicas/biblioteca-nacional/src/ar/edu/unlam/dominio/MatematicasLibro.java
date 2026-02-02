package ar.edu.unlam.dominio;

public class MatematicasLibro extends Libro implements FotocopiarLibro {

	public MatematicasLibro(Integer codigo, String nombre, String autor) {
		super(codigo, nombre, autor);
	}

	@Override
	public String fotocopiarLibro() {
		return "¡LIBRO DE MATEMÁTICAS <3!.";
	}
}