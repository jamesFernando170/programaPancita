package vista;

import java.util.Scanner;
import javax.swing.JOptionPane;

import Excepciones.tiendaException;
import entidades.base.Recorrido;
import entidades.base.Tienda;
import logica.ControlRecorrido;

/**
 * Realiza la interacción con el usuario
 * para poder iniciar el programa y procesar
 * las órdenes de pedido de un recorrido de ventas.
 * 
 * @version 1.0
 */
public class ProgramaPancita {
	private ControlRecorrido control;
	private Recorrido recorrido;

	public ProgramaPancita() {
		this.control = new ControlRecorrido();
	}

	/**
	 * Permite cargar los datos iniciales necesarios
	 * para hacer el recorrido.
	 */
	public void iniciar() {
		this.control.cargarDatosIniciales();
	}

	/**
	 * Es el ciclo de control general del programa,
	 * para saber si hay más ordenes o termina.
	 */
	public void hacerRecorrido() {
		Scanner consola = new Scanner(System.in);
		String respuesta = "S";
		while (respuesta.equals("S")) {
			System.out.println("¿Desea crear orden de pedido (S/N)?");
			respuesta = consola.next();
			if (respuesta.equals("S")) {
				try {
					this.procesarUnaOrden();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Fin del programa. Muchas gracias.");
		consola.close();
	}

	/**
	 * Coordina el proceso para poder crear una orden
	 * de pedido, mostrarla y pedir la aceptación
	 * del usuario.
	 */
	private void procesarUnaOrden() {
		String respuesta = "";
		Scanner pregunta1 = new Scanner(System.in);
		System.out.println("¿Desea crear una orden o salir C/S?");
		respuesta = pregunta1.next();
		pregunta1.close();

		while (respuesta.equals("C")) {
			String codigoTienda = JOptionPane.showInputDialog("Ingresar codigo de la tienda");
			boolean tienda = control.existeTienda(codigoTienda);

			if (tienda == false) {
				String direccion = JOptionPane.showInputDialog("Ingresar direccion del archivo");

				try {
					control.crearOrden(direccion, codigoTienda);

				} catch (tiendaException e) {
					e.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "No se pudo encontrar la tienda");
			}
			String aceptar = JOptionPane.showInputDialog("¿Esta de acuerdo con el pedido para procesarlo A/N?");

			if (aceptar.equals("A")) {
				control.guardarOrden();
			}

			respuesta = JOptionPane.showInputDialog(null, "¿Desea crear una orden o salir C/S?");

		}
	}
}
