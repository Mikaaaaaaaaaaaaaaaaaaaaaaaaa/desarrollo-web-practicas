package ar.edu.unlam.dominio;

import java.util.Comparator;

public class ContratosOrdenadosDescendenteMontoBase implements Comparator<Contrato> {

	@Override
	public int compare(Contrato o1, Contrato o2) {
		return o2.getMontoBase().compareTo(o1.getMontoBase());
	}
}