package ar.edu.unlam.dominio;

public class GeografiaLibro extends Libro implements FotocopiarLibro {

	public GeografiaLibro(Integer codigo, String nombre, String autor) {
		super(codigo, nombre, autor);
	}

	@Override
	public String fotocopiarLibro() {

		return "¡LIBRO DE GEOGRAFÍA <3!.";
	}
}