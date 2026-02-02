package ar.edu.unlam.dominio;

import java.util.Comparator;

public class EventoPorCodigoComparator implements Comparator<Evento> {

	@Override
	public int compare(Evento eventoUno, Evento eventoDos) {
		return eventoUno.getCodigoAlfanumerico().compareTo(eventoDos.getCodigoAlfanumerico());
	}
}