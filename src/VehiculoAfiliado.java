
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Juan David
 */
public class VehiculoAfiliado extends Vehiculo{
    protected LocalDate fechaAfiliacion;
    protected Propietario suPropietario;

    public VehiculoAfiliado(LocalDate fechaAfiliacion, Propietario suPropietario, String placa, int modelo) {
        super(placa, modelo);
        this.fechaAfiliacion = fechaAfiliacion;
        this.suPropietario = suPropietario;
    }
    
    public int calcularNuevaRevision(){
        LocalDate hoy = LocalDate.now();
        int res,res1;
        res = super.calcularNuevaRevision();
        int difAños;
        difAños = (int) ChronoUnit.YEARS.between(fechaAfiliacion, hoy);
        res1 = res - (difAños*3000);
 
        return res1;
    }

    public LocalDate getFechaAfiliacion() {
        return fechaAfiliacion;
    }

    public void setFechaAfiliacion(LocalDate fechaAfiliacion) {
        this.fechaAfiliacion = fechaAfiliacion;
    }

    public Propietario getSuPropietario() {
        return suPropietario;
    }

    public void setSuPropietario(Propietario suPropietario) {
        this.suPropietario = suPropietario;
    }

    @Override
    public String toString() {
        return "VehiculoAfiliado: \n" + "fechaAfiliacion: " + fechaAfiliacion + "\n" + suPropietario + "\n" + super.toString();
    }
  
}
