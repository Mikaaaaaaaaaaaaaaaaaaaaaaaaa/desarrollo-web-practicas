package ar.edu.unlam.dominio;

import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

import ar.edu.unlam.dominio.exception.NaveDuplicadaException;
import ar.edu.unlam.dominio.exception.NaveExcedeTonelajeException;

public class GestorAgencia {

	private TreeSet<Nave> naves;
	private HashSet<Mision> misiones;

	public GestorAgencia() {
		this.naves = new TreeSet<>();
		this.misiones = new HashSet<>();
	}

	public Boolean registrarNave(Nave nave) throws NaveExcedeTonelajeException, NaveDuplicadaException {
		if (nave instanceof NaveCarguera) {
			NaveCarguera naveCarguera = (NaveCarguera) nave;

			if (naveCarguera.getTonelaje() > 30) {
				throw new NaveExcedeTonelajeException("Error, la nave excedió el tonejale máximo.");
			}
		}

		Boolean naveAgregada = this.naves.add(nave);

		if (naveAgregada == false) {
			throw new NaveDuplicadaException("Error, nave duplicada.");
		}

		return naveAgregada;
	}

	public Boolean registrarMision(Nave nave, Integer horas) {
		Double combustibleNecesario = nave.calcularConsumo(horas);

		if (this.naves.contains(nave) == true && nave.getCapacidadMaximaCombustible() >= combustibleNecesario) {
			Mision mision = new Mision(nave, horas);
			this.misiones.add(mision);
			return true;
		}

		return false;
	}

	public TreeSet<Nave> getNaves() {
		return naves;
	}

	public TreeSet<Nave> obtenerNavesSondaOrdenadasPorNombreDescendente() {
		TreeSet<Nave> navesSonda = new TreeSet<>(new NavesOrdenadasPorNombreDescendente());

		for (Nave nave : this.naves) {
			if (nave instanceof NaveSonda) {
				navesSonda.add(nave);
			}
		}

		return navesSonda;
	}

	public TreeMap<Nave, HashSet<Mision>> obtenerReporteDeMisiones() {
		TreeMap<Nave, HashSet<Mision>> reporte = new TreeMap<>();

		for (Mision mision : this.misiones) {
			Nave naveDeLaMision = mision.getNave();

			HashSet<Mision> misionesDeLaNave = reporte.get(naveDeLaMision);

			if (misionesDeLaNave == null) {
				HashSet<Mision> misionesDeLaNaveObtenida = new HashSet<>();
				misionesDeLaNaveObtenida.add(mision);
				reporte.put(naveDeLaMision, misionesDeLaNaveObtenida);
			} else {
				misionesDeLaNave.add(mision);
			}
		}

		return reporte;
	}
}