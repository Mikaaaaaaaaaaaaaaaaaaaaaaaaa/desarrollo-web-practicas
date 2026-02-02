package ar.edu.unlam.dominio;

public class Prestamo {

	private Integer identificadorUnico;
	private Estudiante estudiante;
	private Libro libroPrestado;

	public Prestamo(Integer identificadorUnico, Estudiante estudiante, Libro libroPrestado) {
		this.identificadorUnico = identificadorUnico;
		this.estudiante = estudiante;
		this.libroPrestado = libroPrestado;
	}
}