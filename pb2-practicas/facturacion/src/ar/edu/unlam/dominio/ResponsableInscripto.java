package ar.edu.unlam.dominio;

public class ResponsableInscripto extends Cliente {

	private String nombreFantasia;

	public ResponsableInscripto(String codigoUnico, String nombreFantasia) {
		super(codigoUnico);
		this.nombreFantasia = nombreFantasia;
	}

	public String getCuit() {
		return super.getCodigoUnico();
	}
}