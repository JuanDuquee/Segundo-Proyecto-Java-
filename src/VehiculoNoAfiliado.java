
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
public class VehiculoNoAfiliado extends Vehiculo{
    protected String aseguradora;
    protected Propietario suPropietario;

    public VehiculoNoAfiliado(String aseguradora, Propietario suPropietario, String placa, int modelo) {
        super(placa, modelo);
        this.aseguradora = aseguradora;
        this.suPropietario = suPropietario;
    }

    public String getAseguradora() {
        return aseguradora;
    }

    public void setAseguradora(String aseguradora) {
        this.aseguradora = aseguradora;
    }

    public Propietario getSuPropietario() {
        return suPropietario;
    }

    public void setSuPropietario(Propietario suPropietario) {
        this.suPropietario = suPropietario;
    }

    @Override
    public String toString() {
        return "VehiculoNoAfiliado: \n" + "aseguradora: " + aseguradora + "\n" + suPropietario + "\n" + super.toString();
    }
    
}
