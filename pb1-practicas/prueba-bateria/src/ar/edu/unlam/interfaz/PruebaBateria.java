package ar.edu.unlam.interfaz;

import java.util.Scanner;

import ar.edu.unlam.dominio.Bateria;

public class PruebaBateria {

	private final static Scanner TECLADO = new Scanner(System.in);
	private static final int USAR_APLICACION = 1;
	private static final int CARGAR_BATERIA = 2;
	private static final int CARGA_RAPIDA = 3;
	private static final int VER_ESTADO_ACTUAL = 4;
	private static final int FINALIZAR = 5;

	public static void main(String[] args) {
		
		System.out.println("Prueba bateria.");
		Bateria bateria = new Bateria("A1234");

		int numeroIngresado = 0;
		do {
			menuConsola();
			numeroIngresado = enteroIngresado("\nIngrese una opción [1-5]: ");

			switch (numeroIngresado) {
			case USAR_APLICACION:
				usarAplicacion(bateria);
				break;

			case CARGAR_BATERIA:
				cargarBateria(bateria);
				break;

			case CARGA_RAPIDA:
				cargaRapida(bateria);
				break;

			case VER_ESTADO_ACTUAL:
				verEstadoActual(bateria);
				break;

			case FINALIZAR:
				finalizar();
				break;

			default:
				opcionInvalida();
				break;
			}
		} while (numeroIngresado != FINALIZAR);
	}

	private static void usarAplicacion(Bateria bateria) {
		int nivelIngresado = 0;
		do {
			stringConsola("\nIngrese el nivel de la aplicación [1-3]: ");
			nivelIngresado = TECLADO.nextInt();

			if (nivelIngresado < 1 && nivelIngresado > 3) {
				stringConsola("\nNivel inválido, ingrese un nuevo nivel: ");
			}
		} while (nivelIngresado < 1 || nivelIngresado > 3);

		stringConsola("\nPorcentaje actual de la bateria: " + bateria.usarAplicacion(nivelIngresado));
	}

	private static void cargarBateria(Bateria bateria) {
		stringConsola("\nNuevo porcentaje de bateria: " + bateria.cargarBateria());
	}

	private static void cargaRapida(Bateria bateria) {
		stringConsola("\nNuevo porcentaje de la bateria: " + bateria.cargaRapida());
	}

	private static void verEstadoActual(Bateria bateria) {
		stringConsola("\nEstado: " + bateria.toString());
	}

	private static void finalizar() {
		stringConsola("\nHas salido del programa.");
	}

	private static void opcionInvalida() {
		stringConsola("\nOpción inválida.");
	}

	private static void menuConsola() {
		stringConsola("\nMenú:");
		stringConsola("1. Usar aplicación.");
		stringConsola("2. Cargar bateria.");
		stringConsola("3. Carga rápida.");
		stringConsola("4. Ver estado actual.");
		stringConsola("5. Finalizar.");
	}

	private static int enteroIngresado(String mensaje) {
		System.out.println(mensaje);

		return TECLADO.nextInt();
	}

	private static void stringConsola(String mensaje) {
		System.out.println(mensaje);
	}
}