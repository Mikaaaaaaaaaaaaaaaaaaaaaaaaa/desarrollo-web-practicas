package ar.edu.unlam.dominio;

import java.util.Comparator;

public class ComparatorInstrumentoBateriasOrdenadasAscendentePrecioFinal implements Comparator<Instrumento> {

	@Override
	public int compare(Instrumento instrumentoUno, Instrumento instrumentoDos) {

		if (instrumentoUno instanceof Bateria && instrumentoDos instanceof Bateria) {
			return instrumentoUno.calcularPrecioFinal().compareTo(instrumentoDos.calcularPrecioFinal());
		}

		return 0;
	}
}