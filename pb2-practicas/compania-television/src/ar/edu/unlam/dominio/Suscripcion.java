package ar.edu.unlam.dominio;

public class Suscripcion {

	private Integer identificadorUnico;
	private Cliente cliente;
	private Plan plan;
	private Canal canal;

	public Suscripcion(Integer identificadorUnico, Cliente cliente, Plan plan, Canal canal) {
		this.identificadorUnico = identificadorUnico;
		this.cliente = cliente;
		this.plan = plan;
		this.canal = canal;
	}

	public Canal getCanal() {
		return canal;
	}
}