package ar.edu.unlam.dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ar.edu.unlam.dominio.exception.ExceptionInstrumentoDuplicado;
import ar.edu.unlam.dominio.exception.ExceptionNumeroSerieVenta;
import ar.edu.unlam.dominio.exception.ExceptionValorBaseInstrumento;

public class GestorInstrumentoMusical {
	
	private Set<Instrumento> instrumentos;
	private List<Venta> ventas;
	
	public GestorInstrumentoMusical() {
		this.instrumentos = new TreeSet<>();
		this.ventas = new ArrayList<>();
	}
	
	public Set<Instrumento> getInstrumentos() {
		return instrumentos;
	}

	public Boolean registrarInstrumento(Instrumento instrumento) throws ExceptionValorBaseInstrumento, ExceptionInstrumentoDuplicado {
		if (instrumento.getPrecioBase() <= 0) {
			throw new ExceptionValorBaseInstrumento("Error, valor base inválido.");
		} else if (this.instrumentos.contains(instrumento) == true) {
			throw new ExceptionInstrumentoDuplicado("Error, instrumento musical duplicado.");
		}

		return this.instrumentos.add(instrumento);
	}
	
	public Boolean registrarVenta(Venta ventaParametro) throws ExceptionNumeroSerieVenta, ExceptionInstrumentoDuplicado {
		for (Venta venta : this.ventas) {
			if (venta.getInstrumentoMusical().getNumeroSerie().equals(ventaParametro.getInstrumentoMusical().getNumeroSerie())) {
				throw new ExceptionNumeroSerieVenta("Error, número de serie repetido.");
			} else if (venta.getInstrumentoMusical().equals(ventaParametro.getInstrumentoMusical())) {
				throw new ExceptionInstrumentoDuplicado("Error, instrumento musical duplicado.");
			}
		}

		return this.ventas.add(ventaParametro);
	}
	
	public TreeSet<Instrumento> instrumentosOrdenadosDescendentePrecioFinal() {
		TreeSet<Instrumento> instrumentosOrdenadosDescendentePrecioFinal = new TreeSet<>(new ComparatorInstrumentosOrdenadosDescendentePrecioFinal());

		for (Instrumento instrumento : this.instrumentos) {
			if (instrumento instanceof Guitarra || instrumento instanceof Bateria || instrumento instanceof Bajo) {
				instrumentosOrdenadosDescendentePrecioFinal.add(instrumento);
			}
		}

		return instrumentosOrdenadosDescendentePrecioFinal;
	}
	
	public TreeSet<Instrumento> instrumentoBateriasOrdenadasAscendentePrecioFinal() {
		TreeSet<Instrumento> instrumentoBateriasOrdenadasAscendentePrecioFinal = new TreeSet<>(new ComparatorInstrumentoBateriasOrdenadasAscendentePrecioFinal());

		for (Instrumento instrumento : this.instrumentos) {
			if (instrumento instanceof Bateria) {
				Bateria bateriaEncontrada = (Bateria) instrumento;
				instrumentoBateriasOrdenadasAscendentePrecioFinal.add(bateriaEncontrada);
			}
		}

		return instrumentoBateriasOrdenadasAscendentePrecioFinal;
	}
}