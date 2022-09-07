package logica;

import java.io.IOException;
import java.util.List;

import Excepciones.tiendaException;
import datos.EscritorArchivoOrdenes;
import datos.IFuenteDatos;
import datos.LectorArchivos;
import entidades.base.Producto;
import entidades.base.Recorrido;
import entidades.base.Tienda;
import entidades.pedido.OrdenPedido;

/**
 * Lógica del programa de un recorrido de un vendedor,
 * para crear las órdenes de pedido de las tiendas.
 * 
 * @version 1.0
 */
public class ControlRecorrido {
	private Recorrido recorrido;
	private OrdenPedido ordenEnProceso;

	public ControlRecorrido() {
		this.recorrido = new Recorrido();
		this.ordenEnProceso = null;
	}

	public void cargarDatosIniciales() {
		CargadorDatos cargador = new CargadorDatos(recorrido);
		cargador.cargarDatosIniciales();
	}

	public boolean existeTienda(String codigoTienda) {
		Tienda tienda = recorrido.buscarTienda(codigoTienda);

		if (tienda == null) {
			return true;
		} else {
			return false;
		}
	}

	public String obtenerDatosTienda(String codigoTienda) {
		Tienda tienda = this.recorrido.buscarTienda(codigoTienda);
		if (tienda != null) {
			return tienda.getCodigo() + "-" + tienda.getNombre();
		}
		return "";
	}

	public List<String> obtenerDetallesOrdenados() {
		return this.ordenEnProceso.getDetallesOrdenados();
	}

	public void crearOrden(String nombreArchivo, String codigoTienda) throws tiendaException {
		IFuenteDatos archivoProductos = new LectorArchivos(nombreArchivo);
		List<String[]> datosBaseProductos;
		try {
			datosBaseProductos = archivoProductos.obtenerDatosBase();

			Tienda tienda = this.recorrido.buscarTienda(codigoTienda);
			ordenEnProceso = new OrdenPedido(tienda);

			for (String[] productos : datosBaseProductos) {
				this.crearDetalle(ordenEnProceso, productos);
			}

			for (int i = 0; i < ordenEnProceso.getDetallesOrdenados().size(); i++) {
				System.out.println(ordenEnProceso.toString());
			}

			System.out.println();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void crearDetalle(OrdenPedido ordenPedido, String[] datosBaseDetalle){
		Producto producto = this.recorrido.buscarProducto(datosBaseDetalle[0]);
		this.ordenEnProceso.addDetalle(producto, Integer.valueOf(datosBaseDetalle[1]));
	}

	// COMPLETAR LOS MÉTODOS QUE FALTAN

	/**
	 * Envía la orden para que su información
	 * se guarde en un archivo, y luego
	 * deja el atributo en null para que se
	 * pueda procesar una nueva orden.
	 */
	public void guardarOrden() {
		EscritorArchivoOrdenes escritor = new EscritorArchivoOrdenes();
		escritor.escribirOrden(ordenEnProceso);
		ordenEnProceso = null;
	}
}
