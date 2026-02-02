package ar.edu.unlam.dominio;

import java.util.Comparator;

public class NavesOrdenadasPorNombreDescendente implements Comparator<Nave> {

	@Override
	public int compare(Nave notaUno, Nave notaDos) {

		return notaDos.getNombre().compareTo(notaUno.getNombre());
	}
}