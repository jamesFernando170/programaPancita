package logica;

import java.io.IOException;
import java.util.List;

import datos.IFuenteDatos;
import datos.LectorArchivos;
import entidades.base.Producto;
import entidades.base.Recorrido;
import entidades.base.Tienda;

/**
 * Se encarga de crear los objetos a partir
 * de arreglos de cadenas de texto con la información
 * (esas cadenas pueden provenir de diferentes
 * fuentes, por eso usa una interfaz,
 * para que se puedan cambiar fácilmente).
 * 
 * @version 1.0
 */
public class CargadorDatos {
	private Recorrido recorrido;

	public CargadorDatos(Recorrido recorrido) {
		this.recorrido = recorrido;
	}

	/**
	 * Carga los datos iniciales que necesita el programa:
	 * tiendas y productos.
	 */
	public void cargarDatosIniciales() {
		// SE DEBE CAMBIAR ESTA FUENTE, QUE ES DE PRUEBA
		// POR UNA QUE LEA LOS DATOS DE UN ARCHIVO.
		//IFuenteDatos fuenteDatosTiendas = new DatosPruebaTiendas();
		IFuenteDatos fuenteDatosTiendas = new LectorArchivos("Tiendas.txt");
		
		List<String[]> datosBaseTiendas;
		try {
			datosBaseTiendas = fuenteDatosTiendas.obtenerDatosBase();
			this.cargarDatosTiendas(datosBaseTiendas);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		IFuenteDatos fuenteDatosProductos = new LectorArchivos("Productos.txt");
		List<String[]> datosBaseProductos;
		try {
			datosBaseProductos = fuenteDatosProductos.obtenerDatosBase();
			this.cargarDatosProductos(datosBaseProductos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * A partir de los datos base (cadenas de texto),
	 * obtiene los datos de las tiendas,
	 * crea los objetos y los guarda en el objeto recorrido.
	 */
	private void cargarDatosTiendas(List<String[]> datosBase) {
		for (String[] datoBaseTienda: datosBase) {
			String codigo = datoBaseTienda[0];
			String nombre = datoBaseTienda[1];
			Tienda tienda = new Tienda(codigo, nombre);
			this.recorrido.addTienda(tienda);
		}
	}
	
	/**
	 * A partir de los datos base (cadenas de texto),
	 * obtiene los datos de los productos,
	 * crea los objetos y los guarda en el objeto recorrido.
	 */
	private void cargarDatosProductos(List<String[]> datosBase) {
		for (String[] datoBaseProducto: datosBase) {
			String codigo = datoBaseProducto[0];
			String nombre = datoBaseProducto[1];
			double valorUnitario = Double.parseDouble(datoBaseProducto[2]);
			Producto producto = new Producto(codigo, nombre, valorUnitario);
			this.recorrido.addProducto(producto);
		}
	}
}