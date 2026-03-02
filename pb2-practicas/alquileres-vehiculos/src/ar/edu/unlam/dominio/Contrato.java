package ar.edu.unlam.dominio;

public class Contrato implements Comparable<Contrato> {

	private Integer numeroContrato;
	private String fechaInicio;
	private String fechaFin;
	private Double montoBase;
	private Cliente cliente;

	public Contrato(Integer numeroContrato, String fechaInicio, String fechaFin, Double montoBase, Cliente cliente) {
		this.numeroContrato = numeroContrato;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.montoBase = montoBase;
		this.cliente = cliente;
	}

	public Double getMontoBase() {
		return montoBase;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Integer getNumeroContrato() {
		return numeroContrato;
	}

	@Override
	public int compareTo(Contrato contratoDos) {
		return this.numeroContrato.compareTo(contratoDos.getNumeroContrato());
	}
}