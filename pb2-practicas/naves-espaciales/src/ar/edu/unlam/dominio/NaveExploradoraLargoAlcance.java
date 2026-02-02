package ar.edu.unlam.dominio;

public class NaveExploradoraLargoAlcance extends Nave {

	public NaveExploradoraLargoAlcance(String id, String nombre, Double capacidadMaximaCombustible) {
		super(id, nombre, capacidadMaximaCombustible);
	}

	@Override
	Double calcularConsumo(Integer horas) {
		Double consumoBase = super.getConsumoBase() * 0.8;
		return consumoBase * horas;
	}
}