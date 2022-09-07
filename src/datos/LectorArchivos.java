package datos;

import java.io.IOException;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LectorArchivos implements IFuenteDatos {

    private String nombreArchivo;

    public LectorArchivos(String nombreArchivo){
        this.nombreArchivo = nombreArchivo;
    }
    
    @Override
    public List<String[]> obtenerDatosBase() throws IOException {
        
        Path rutaArchivo = Paths.get(nombreArchivo);
        BufferedReader lector = Files.newBufferedReader(rutaArchivo);
        List<String[]> datosArchivo = new ArrayList<String[]>();
        String linea;
        while ((linea = lector.readLine()) != null) {
            String[] datos = linea.split(",");
            datosArchivo.add(datos);
        }
        lector.close();

        return datosArchivo;

}


    
}