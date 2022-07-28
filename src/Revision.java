
import java.io.Serializable;
import java.time.LocalDate;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Juan David
 */
public class Revision implements Serializable{
    protected LocalDate fecha;
    protected String descripcion;
    protected String conceptoReparacion;

    public Revision(LocalDate fecha, String descripcion, String conceptoReparacion) {
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.conceptoReparacion = conceptoReparacion;
    }
    
    public int calcularValorBase(){
        int res = 50000;
        return res;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getConceptoReparacion() {
        return conceptoReparacion;
    }

    public void setConceptoReparacion(String conceptoReparacion) {
        this.conceptoReparacion = conceptoReparacion;
    }

    @Override
    public String toString() {
        return "Revision: " + " fecha: " + fecha + ", Descripcion: " + descripcion + ", Concepto: " + conceptoReparacion + "\n";
    }
    
    
}
