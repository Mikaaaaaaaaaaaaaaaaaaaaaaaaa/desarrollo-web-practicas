package ar.edu.unlam.dominio;

import java.util.Comparator;

public class ComparatorNaveIdentificadorUnico implements Comparator<Nave> {

	@Override
	public int compare(Nave nave1, Nave nave2) {
		return nave1.getId().compareTo(nave2.getId());
	}
}