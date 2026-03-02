package ar.edu.unlam.dominio;

public class ClienteEmpresa extends Cliente {

	private String razonSocial;
	private String nombreFantasia;

	public ClienteEmpresa(String cuit, String razonSocial, String nombreFantasia) {
		super(cuit);
		this.razonSocial = razonSocial;
		this.nombreFantasia = nombreFantasia;
	}
}