package ar.edu.unlam.dominio;

public class Venta {
	
	private static Integer contador = 0;
	private Integer identificador;
	
	private Instrumento instrumentoMusical;
	private Double montoFinal;
	private GestorInstrumentoMusical gestorInstrumentoMusical;
	
	public Venta(Integer identificador, Instrumento instrumentoMusical, Double montoFinal) {
		this.contador++;
		this.identificador = this.contador;
		
		this.instrumentoMusical = instrumentoMusical;
		this.montoFinal = montoFinal;
	}
	
	public void setGestorInstrumentoMusical(GestorInstrumentoMusical gestorInstrumentoMusical) {
		this.gestorInstrumentoMusical = gestorInstrumentoMusical;
	}

	public Instrumento getInstrumentoMusical() {
		return instrumentoMusical;
	}
	
	public Double calcularPrecioFinal() {
		
		Double total = 0.0;
		if (this.instrumentoMusical == null) {
			return 0.0;
		}

		for (Instrumento instrumento : this.gestorInstrumentoMusical.getInstrumentos()) {
			total += instrumento.calcularPrecioFinal();
		}

		return total;
	}
}