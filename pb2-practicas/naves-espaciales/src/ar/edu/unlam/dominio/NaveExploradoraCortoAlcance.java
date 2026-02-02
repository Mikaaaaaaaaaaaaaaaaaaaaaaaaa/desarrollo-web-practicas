package ar.edu.unlam.dominio;

public class NaveExploradoraCortoAlcance extends Nave {

	public NaveExploradoraCortoAlcance(String id, String nombre, Double capacidadMaximaCombustible) {
		super(id, nombre, capacidadMaximaCombustible);
	}

	@Override
	Double calcularConsumo(Integer horas) {
		Double consumoBase = super.getConsumoBase() * 0.9;
		return consumoBase * horas;
	}
}