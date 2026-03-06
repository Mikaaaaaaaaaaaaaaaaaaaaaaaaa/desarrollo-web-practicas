package ar.edu.unlam.dominio;

public class Bateria {
//	Trabajaremos con una batería, la cual posee las siguientes características: 
//	Tiene un modelo: Ejemplo: A1234. 
//	Tiene un porcentaje actual de batería. Ejemplo 100%. 
//	Tiene un indicador para saber si es necesario recargar la batería. Ejemplo: SI/NO. 

	private String modelo;
	private double porcentajeActual;
	private boolean recargarBateria;
	private int cantidadAplicaciones;

//	Al momento de crear la batería para operar, la carga actual de la batería, deberá ser 100%. 
	public Bateria(String modelo) {
		this.modelo = modelo;
		this.porcentajeActual = 100;
		this.recargarBateria = false;
		this.cantidadAplicaciones = 0;
	}

	public double usarAplicacion(int nivelAplicacion) {
//	La aplicación deberá contar con las siguientes operaciones: 

//	1. Usar aplicación: Simula la ejecución de una aplicación que consume cierto porcentaje de batería cada vez que se utiliza. 

//	Luego muestra el porcentaje actual de la batería.

//	El usuario deberá indicar el nivel de la aplicación, para que la batería pueda actualizar el porcentaje actual: 
//	a. Si la aplicación es de nivel 1, consume 5% del porcentaje actual de batería.
//	b. Si la aplicación es de nivel 2, consume 10% del porcentaje actual de batería.
//	c. Si la aplicación es de nivel 3, consume 20% del porcentaje actual de batería.

//	Para poder usar alguna aplicación, es necesario que la batería no esté en modo “Recargar”. 
		double porcentajeConsumido = 0.0;

		if (necesitaRecargar() == false) {
			if (nivelAplicacion == 1) {
				porcentajeConsumido = this.porcentajeActual * 0.05; // Consume el 5%
			} else if (nivelAplicacion == 2) {
				porcentajeConsumido = this.porcentajeActual * 0.10; // Consume el 10%
			} else if (nivelAplicacion == 3) {
				porcentajeConsumido = this.porcentajeActual * 0.20; // Consume el 20%
			}
		}

		this.cantidadAplicaciones++;

		this.porcentajeActual -= porcentajeConsumido;
		return porcentajeActual;
	}

	public double cargarBateria() {
//	2. Cargar batería:
//	Cuando el usuario utiliza esta opción, se incrementa 10% el porcentaje actual de la batería.
//	Se debe mostrar el nuevo porcentaje de la batería.

//	No es posible cargar más del 100% la batería.
//	En caso de cargar por demás la batería, el porcentaje final deberá ser 100%.
//	Ejemplo: carga actual 95%, si se carga será 105%, debe quedar 100% 

		this.porcentajeActual += 10;

		if (this.porcentajeActual <= 100) {
			return this.porcentajeActual;
		}

		return this.porcentajeActual = 100;
	}

	public double cargaRapida() {
//	3. Carga rápida:
//	Incrementa en 20% el porcentaje actual de la batería, sólo si el porcentaje actual de la batería no supera el 5%.
//	Se debe mostrar el nuevo porcentaje de la batería.

		if (this.porcentajeActual <= 5) {
			this.porcentajeActual += 20;
			if (this.porcentajeActual > 100) {
				this.porcentajeActual = 100;
			}
		}
		return this.porcentajeActual;
	}

	public boolean necesitaRecargar() {
//	No es posible descargar la batería más allá de cero.
//	En caso de que el uso de una aplicación exceda el límite, deberá quedar 0% (similar al ejemplo anterior). 
//	En esta condición (carga actual 0%), se debe recargar la batería.

//	Es necesario contabilizar los usos para mostrar el detalle de uso actual de la batería.
//	También se debe incluir si es necesario cargarla o no.

		if (this.porcentajeActual <= 0 && this.recargarBateria == false) {
			return this.recargarBateria = true;
		} else if (this.porcentajeActual > 100) {
			this.porcentajeActual = 0.0;
		}

		return this.recargarBateria;
	}

	public int getCantidadAplicaciones() {
//	Es necesario contabilizar los usos para mostrar el detalle de uso actual de la batería.
//	También se debe incluir si es necesario cargarla o no.

		return this.cantidadAplicaciones;
	}
	
	@Override
	public String toString() {
//	4. Ver estado actual:
//	Muestra los datos de la batería, al momento de ser solicitados.
//	Se debe incluir la cantidad de aplicaciones que se usaron y si se debe recargar o no.

		return "\nModelo: " + this.modelo + "\nPorcentaje Actual: " + this.porcentajeActual + "\n¿Necesita recargar?: " + this.recargarBateria + "\nCantidad aplicaciones: " + this.cantidadAplicaciones;
	}
}