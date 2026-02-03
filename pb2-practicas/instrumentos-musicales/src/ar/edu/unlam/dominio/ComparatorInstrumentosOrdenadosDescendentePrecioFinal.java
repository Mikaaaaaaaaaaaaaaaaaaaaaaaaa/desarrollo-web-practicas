package ar.edu.unlam.dominio;

import java.util.Comparator;

public class ComparatorInstrumentosOrdenadosDescendentePrecioFinal implements Comparator<Instrumento> {

	@Override
	public int compare(Instrumento instrumentoUno, Instrumento instrumentoDos) {
		return instrumentoDos.calcularPrecioFinal().compareTo(instrumentoUno.calcularPrecioFinal());
	}
}