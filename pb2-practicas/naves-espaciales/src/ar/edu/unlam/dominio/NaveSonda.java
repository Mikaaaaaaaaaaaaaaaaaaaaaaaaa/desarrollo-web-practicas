package ar.edu.unlam.dominio;

public class NaveSonda extends Nave {

	public NaveSonda(String id, String nombre, Double capacidadMaximaCombustible) {
		super(id, nombre, capacidadMaximaCombustible);
	}

	@Override
	Double calcularConsumo(Integer horas) {
		return horas * super.getConsumoBase();
	}
}