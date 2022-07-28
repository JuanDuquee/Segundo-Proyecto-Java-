
import java.io.Serializable;
import java.util.ArrayList;
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
public class Vehiculo implements Serializable{
    protected String placa;
    protected int modelo;
    protected ArrayList<Revision> suRevision;

    public Vehiculo(String placa, int modelo, ArrayList<Revision> suRevision) {
        this.placa = placa;
        this.modelo = modelo;
        this.suRevision = suRevision;
    }
    
    public Vehiculo(String placa, int modelo) {
        this.placa = placa;
        this.modelo = modelo;
        suRevision = new ArrayList<>();
    }
    
    public void agregarRevision(Revision nuevaR){
        suRevision.add(nuevaR);
    }
    
    public int calcularNuevaRevision(){
        int res=0;
        for (Revision obj: suRevision) {
            res = obj.calcularValorBase();            
        }
        return res;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getModelo() {
        return modelo;
    }

    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    public ArrayList<Revision> getSuRevision() {
        return suRevision;
    }

    public void setSuRevision(ArrayList<Revision> suRevision) {
        this.suRevision = suRevision;
    }

    @Override
    public String toString() {
        return "Placa Vehiculo: " + placa + ", " + " modelo: " + modelo + "\n" + suRevision + "\n";
    }

    

}
