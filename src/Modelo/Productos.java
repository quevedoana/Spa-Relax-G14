/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.util.*;

/**
 *
 * @author maria
 */
public class Productos {
    private Map<String, List<String>> productosPorTipo;
    
    public Productos() {
        productosPorTipo = new HashMap<>();
        inicializarProductos();
    }
    
    private void inicializarProductos() {
        // Productos para tratamientos FACIALES
        List<String> faciales = Arrays.asList(
            "Crema hidratante", "Serum vitamina C", "Mascarilla de arcilla",
            "Tónico facial", "Protector solar", "Aceite de rosa mosqueta"
        );
        
        // Productos para tratamientos CORPORALES
        List<String> corporales = Arrays.asList(
            "Aceite de masaje", "Crema reafirmante", "Gel de aloe vera",
            "Sales de baño", "Aceite esencial de lavanda", "Loción corporal"
        );
        
        // Productos para tratamientos de RELAJACIÓN
        List<String> relajacion = Arrays.asList(
            "Aceite esencial de lavanda", "Velas aromáticas", "Incienso",
            "Aceite de almendras", "Sales de Epsom", "Aceite de eucalipto"
        );
        
        // Productos para tratamientos ESTÉTICOS
        List<String> esteticos = Arrays.asList(
            "Esmalte de uñas", "Quita cutículas", "Crema para manos",
            "Aceite de cutículas", "Mascarilla capilar", "Gel fijador"
        );
        
        productosPorTipo.put("Facial", faciales);
        productosPorTipo.put("Corporal", corporales);
        productosPorTipo.put("Relajacion", relajacion);
        productosPorTipo.put("Estetico", esteticos);
    }
    
    public List<String> getProductosPorTipo(String tipo) {
        return productosPorTipo.getOrDefault(tipo, new ArrayList<>());
    }
}
