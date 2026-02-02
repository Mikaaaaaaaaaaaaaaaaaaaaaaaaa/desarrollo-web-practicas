package ar.edu.unlam.dominio;

public class HistoriaLibro extends Libro implements FotocopiarLibro {

	public HistoriaLibro(Integer codigo, String nombre, String autor) {
		super(codigo, nombre, autor);
	}

	@Override
	public String fotocopiarLibro() {

		return "¡LIBRO DE HISTORIA <3!.";
	}
}