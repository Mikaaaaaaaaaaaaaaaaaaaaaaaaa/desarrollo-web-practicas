package ar.edu.unlam.dominio;

import java.util.Objects;

import ar.edu.unlam.dominio.enums.TipoCanal;
import ar.edu.unlam.dominio.enums.TipoClasificacion;

public class Canal implements Comparable<Canal> {

	private Integer numeroCanal;
	private String nombre;
	private TipoCanal tipoCanal;
	private TipoClasificacion tipoClasificacion;
	private Integer valorBase;

	public TipoClasificacion getTipoClasificacion() {
		return tipoClasificacion;
	}

	public Canal(Integer numeroCanal, String nombre, TipoCanal tipoCanal, TipoClasificacion tipoClasificacion) {
		this.numeroCanal = numeroCanal;
		this.nombre = nombre;
		this.tipoCanal = tipoCanal;
		this.tipoClasificacion = tipoClasificacion;
		this.valorBase = 5000;
	}

	public Integer getNumeroCanal() {
		return numeroCanal;
	}

	public Integer getValorBase() {
		return valorBase;
	}

	public Integer calcularPrecioPlan() {
		Integer precio = 0;

		if (this.tipoClasificacion.equals(TipoClasificacion.BASICO)) {
			return precio += this.valorBase;
		} else if (this.tipoClasificacion.equals(TipoClasificacion.PREMIUM)) {
			return precio += (int) (this.valorBase * 1.20);
		}
		return precio;
	}

	@Override
	public int hashCode() {
		return Objects.hash(numeroCanal);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Canal other = (Canal) obj;
		return Objects.equals(numeroCanal, other.numeroCanal);
	}

	@Override
	public int compareTo(Canal canal) {
		return this.numeroCanal.compareTo(canal.getNumeroCanal());
	}
}