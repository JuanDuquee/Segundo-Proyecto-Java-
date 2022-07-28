
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Juan David
 */
public class UsaVehiculo extends javax.swing.JFrame {

    private LinkedList<Vehiculo> losVehiculos = new LinkedList<>();

    /**
     * Creates new form UsaPropietario
     */
    public UsaVehiculo() {
        initComponents();
        iniciarDatos(losVehiculos);
    }

    public void iniciarDatos(LinkedList<Vehiculo> info) {
        LocalDate fechaAhora = LocalDate.now();
        losVehiculos.add(new VehiculoAfiliado(fechaAhora, new Propietario(2200388, "Juan", 322), "XXX123", 2018));
        losVehiculos.add(new VehiculoAfiliado(fechaAhora, new Propietario(2200389, "Daniel", 315), "XXX456", 2019));
        losVehiculos.add(new VehiculoNoAfiliado("MAPFRE", new Propietario(2200390, "Camilo", 318), "XXX789", 2020));
        losVehiculos.add(new VehiculoNoAfiliado("SURA", new Propietario(2200391, "Manuela", 320), "XXX321", 2021));

        losVehiculos.get(0).agregarRevision(new Revision(fechaAhora, "Buen estado", "PASA"));
        losVehiculos.get(1).agregarRevision(new Revision(fechaAhora, "Revisión", "ALERTA"));
        losVehiculos.get(2).agregarRevision(new Revision(fechaAhora, "Revisión", "ALERTA"));
        losVehiculos.get(3).agregarRevision(new Revision(fechaAhora, "Buen estado", "PASA"));

    }

    public static int busquedaBinariaVehiculo(LinkedList<Vehiculo> V,
            int p, int q, String placa) {
        int posicion = -1;
        if (p <= q) {
            int m = (p + q) / 2;
            if (placa.equalsIgnoreCase(V.get(m).placa)) {
                posicion = m;
            } else {
                if (placa.compareToIgnoreCase(V.get(m).placa) < 0) {
                    posicion = busquedaBinariaVehiculo(V, p, m - 1, placa);
                } else {
                    posicion = busquedaBinariaVehiculo(V, m + 1, q, placa);
                }
            }
        }
        return posicion;
    }

    public static String buscarVehiculo(LinkedList<Vehiculo> info, String placa) {
        String res = "";

        int pos = busquedaBinariaVehiculo(info, 0, info.size() - 1, placa);
        if (pos == -1) {
            res = "No existe el vehiculo con la placa " + placa;
        } else {
                if (info.get(pos) instanceof VehiculoAfiliado) {
                    res = "El vehiculo buscado es: \n" + pos + ". " + info.get(pos).toString() + "\n";
                } else {
                    res = "El vehiculo buscado es: \n" + pos + ". " + info.get(pos).toString() + "\n";
                }
        }
        return res;
    }

    public String listarVehiculos(LinkedList<Vehiculo> info) {
        info.sort(
                (x, y) -> x.placa.compareToIgnoreCase(y.placa)
        );
        String res = "Listado de los Vehiculos Atendidos:\n\n";
        for (int i = 0; i < info.size(); i++) {
            for (int j = 0; j < info.get(i).suRevision.size(); j++) {
                if (info.get(i) instanceof VehiculoAfiliado) {
                    res += "\n" + "placa: " + info.get(i).placa + "\n" + "AFILIADO" + "\n" + "cedula propietario: "
                            + ((VehiculoAfiliado) info.get(i)).suPropietario.cedula + "\n" + 
                            "fecha revision: " + info.get(i).suRevision.get(j).fecha + "\n";
                } else {
                    res += "\n" + "placa: " + info.get(i).placa + "\n" + "NO AFILIADO" + "\n" + "cedula propietario: "
                            + ((VehiculoNoAfiliado) info.get(i)).suPropietario.cedula + "\n" + 
                            "fecha revision: " + info.get(i).suRevision.get(j).fecha + "\n";
                }
            }
        }
        return res;
    }
    
    public int busquedaPlaca(LinkedList<Vehiculo> info, String placaS)
    {
        int pos = -1;
        for (int i = 0; i < info.size(); i++) {
            if (placaS.equalsIgnoreCase(info.get(i).placa)){
                pos = i;
                break;
            }            
        }
        return pos;
    }
    
    public void almacenarInformacion(LinkedList<Vehiculo> info) {
        String nombreArchivo = "vehiculo.obj";
        ObjectOutputStream salida = null;
        try {
            // almacenamos infor
            salida = new ObjectOutputStream(
                    new FileOutputStream(nombreArchivo));
            salida.writeObject(info);

            JOptionPane.showMessageDialog(null, "Se han almacenado los datos exitosamente");
        } catch (Exception e) {
            System.out.println("Error almacenando los datos " + e.getMessage());
        } finally {
            try {
                salida.close();
            } catch (Exception e) {
                System.out.println("Error cerrando ");
            }
        }
    }

    public void recuperarArchivoObjetos(LinkedList<Vehiculo> bd) {

        // Actualizar el nombre del archivo
        String nombreArchivo = "vehiculo.obj";
        LinkedList<Vehiculo> laBD = new LinkedList();
        ObjectInputStream entrada = null;
        try {
            // lectura de datos
            entrada = new ObjectInputStream(
                    new FileInputStream(nombreArchivo));

            // leer flujo y almacenarlo en laBD. Usar casting        
            laBD = (LinkedList<Vehiculo>) entrada.readObject();
            // opcional el borrar los datos existentes en bd
            bd.clear();
            JOptionPane.showMessageDialog(null,
                    "La información de los vehiculos fue eliminada");
            // pasar los datos de laBD a bd
            bd.addAll(laBD);

            JOptionPane.showMessageDialog(null, "Se han recuperado los datos exitosamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error recuperando los datos " + e.getMessage() + " -- " + e.toString());
        } finally {
            try {
                entrada.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error cerrando ");
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        InsertarVehiculo = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();
        Salir = new javax.swing.JButton();
        IngresarRevision = new javax.swing.JButton();
        ReporteVehiculos = new javax.swing.JButton();
        ReporteUnVehiculo = new javax.swing.JButton();
        AlmacenarDatos = new javax.swing.JButton();
        RecuperarDatos = new javax.swing.JButton();
        LimpiarCasillas = new javax.swing.JButton();
        LimpiarInformacion = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Vehiculo");

        jLabel2.setText("Placa");

        jLabel3.setText("Modelo");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setText("Propietario");

        jLabel8.setText("Celular");

        jLabel9.setText("Cedula");

        jLabel10.setText("Nombre");

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel11.setText("Revisión");

        jLabel13.setText("Descripción");

        jLabel14.setText("Concepto");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALERTA", "PASA" }));

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));

        jLabel15.setText("Vehiculo No Afiliado");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MAPFRE", "SURA", "ALLIANZ", "SOLIDARIA", "LIBERTY" }));

        jLabel16.setText("Aseguradora");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        InsertarVehiculo.setText("Ingresar Vehiculo");
        InsertarVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertarVehiculoActionPerformed(evt);
            }
        });

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Afiliado", "No Afiliado" }));

        Salir.setText("Salir");
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });

        IngresarRevision.setText("Ingresar Revisión");
        IngresarRevision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IngresarRevisionActionPerformed(evt);
            }
        });

        ReporteVehiculos.setText("Reporte Vehiculos");
        ReporteVehiculos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReporteVehiculosActionPerformed(evt);
            }
        });

        ReporteUnVehiculo.setText("Reporte un vehiculo");
        ReporteUnVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReporteUnVehiculoActionPerformed(evt);
            }
        });

        AlmacenarDatos.setText("Almacenar Datos");
        AlmacenarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlmacenarDatosActionPerformed(evt);
            }
        });

        RecuperarDatos.setText("Recuperar Datos");
        RecuperarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RecuperarDatosActionPerformed(evt);
            }
        });

        LimpiarCasillas.setText("Limpiar Casillas");
        LimpiarCasillas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LimpiarCasillasActionPerformed(evt);
            }
        });

        LimpiarInformacion.setText("Limpiar información");
        LimpiarInformacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LimpiarInformacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(158, 158, 158)
                        .addComponent(jLabel7)
                        .addGap(181, 181, 181)
                        .addComponent(jLabel11)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(9, 9, 9))
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(32, 32, 32)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(104, 104, 104)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel14))
                                        .addGap(37, 37, 37)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField8)
                                            .addComponent(jComboBox2, 0, 90, Short.MAX_VALUE))
                                        .addGap(0, 91, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(IngresarRevision)
                                        .addGap(112, 112, 112))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addComponent(InsertarVehiculo)
                                .addGap(0, 0, Short.MAX_VALUE))))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(ReporteVehiculos)
                        .addGap(27, 27, 27)
                        .addComponent(ReporteUnVehiculo)
                        .addGap(45, 45, 45)
                        .addComponent(AlmacenarDatos)
                        .addGap(65, 65, 65)
                        .addComponent(RecuperarDatos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                        .addComponent(Salir)
                        .addGap(35, 35, 35))
                    .addComponent(jScrollPane1)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(LimpiarInformacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(LimpiarCasillas)
                .addGap(144, 144, 144))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(IngresarRevision)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(InsertarVehiculo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LimpiarCasillas)
                    .addComponent(LimpiarInformacion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Salir)
                    .addComponent(ReporteVehiculos)
                    .addComponent(ReporteUnVehiculo)
                    .addComponent(AlmacenarDatos)
                    .addComponent(RecuperarDatos))
                .addContainerGap())
        );

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel4.setText("TALLER MECÁNICO-AUTOMOTRIZ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(229, 229, 229)
                .addComponent(jLabel4)
                .addContainerGap(242, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RecuperarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RecuperarDatosActionPerformed
        recuperarArchivoObjetos(losVehiculos);
    }//GEN-LAST:event_RecuperarDatosActionPerformed

    private void AlmacenarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlmacenarDatosActionPerformed
        almacenarInformacion(losVehiculos);
    }//GEN-LAST:event_AlmacenarDatosActionPerformed

    private void ReporteUnVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReporteUnVehiculoActionPerformed
        try {
            String laPlaca = JOptionPane.showInputDialog("Digite la placa del vehiculo para mostrar");
            jTextArea1.setText(buscarVehiculo(losVehiculos, laPlaca));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Se dió un error al ejecutar estas instrucciones"
                + "\n info Mesagge:" + e.getMessage());
        }
    }//GEN-LAST:event_ReporteUnVehiculoActionPerformed

    private void ReporteVehiculosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReporteVehiculosActionPerformed
        try {
            jTextArea1.setText(listarVehiculos(losVehiculos));
            //            jTextArea1.setText(desplegarTodos(losVehiculosH));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Se dió un error al ejecutar estas instrucciones"
                + "\n info Mesagge:");
        }
    }//GEN-LAST:event_ReporteVehiculosActionPerformed

    private void IngresarRevisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IngresarRevisionActionPerformed
        try {
            String laPlaca = JOptionPane.showInputDialog("Digite la placa del vehiculo \n"
                + "que desea realizarle revisión");

            int pos = busquedaPlaca(losVehiculos,laPlaca);
            if(pos == -1){
                JOptionPane.showMessageDialog(null, "Vehiculo inexistente");
            }else{
                //ATRIBUTOS REVISION Y ALMACENAMIENTO
                LocalDate laFecha = LocalDate.parse(JOptionPane.showInputDialog("Digite la fecha de la revisión en formato AAAA-MM-DD"));
                String laDescripcion = jTextField8.getText();
                String elConcepto = jComboBox2.getSelectedItem().toString();
                losVehiculos.get(pos).agregarRevision(new Revision(laFecha, laDescripcion, elConcepto));

                int res=0;
                //CONDICION PARA CALCULAR EL VALOR A PAGAR POR LA REVISION SI ES AFILIADO O NO
                if(losVehiculos.get(pos) instanceof VehiculoAfiliado){
                    res = ((VehiculoAfiliado) losVehiculos.get(pos)).calcularNuevaRevision();
                    JOptionPane.showMessageDialog(null,"Valor total a pagar por la revisión: " + res);
                    JOptionPane.showMessageDialog(null, "Se ha ingresado la revisión satisfactoriamente");
                }else{
                    res = losVehiculos.get(pos).calcularNuevaRevision();
                    JOptionPane.showMessageDialog(null,"Valor total a pagar por la revisión: " + res);
                    JOptionPane.showMessageDialog(null, "Se ha ingresado la revisión satisfactoriamente");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Se dió un error al ejecutar estas instrucciones"
                + "\n info Mesagge:" + e.getMessage());
        }
    }//GEN-LAST:event_IngresarRevisionActionPerformed

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_SalirActionPerformed

    private void InsertarVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InsertarVehiculoActionPerformed
        try {
            String tipoVehiculo = jComboBox3.getSelectedItem().toString();
            //VEHICULO
            String laPlaca = jTextField1.getText();
            int elModelo = Integer.parseInt(jTextField2.getText());
            //PROPIETARIO
            int laCedula = Integer.parseInt(jTextField4.getText());
            String elNombre = jTextField5.getText();
            int elCelular = Integer.parseInt(jTextField6.getText());
            Propietario obj = new Propietario(laCedula, elNombre, elCelular);
            //CONDICION SI ES VEHICULO AFILIADO O NO
            if (tipoVehiculo.equalsIgnoreCase("Afiliado")) {
                LocalDate laFechaAfiliacion = LocalDate.parse(JOptionPane.showInputDialog("Digite la fecha de afiliacion en formato AAAA-MM-DD"));
                losVehiculos.add(new VehiculoAfiliado(laFechaAfiliacion, obj, laPlaca, elModelo));
                JOptionPane.showMessageDialog(null, "Se ha ingresado el vehiculo afiliado exitosamente");
            } else {
                String laAseguradora = jComboBox1.getSelectedItem().toString();
                losVehiculos.add(new VehiculoNoAfiliado(laAseguradora, obj, laPlaca, elModelo));
                JOptionPane.showMessageDialog(null, "Se ha ingresado el vehiculo no afiliado exitosamente");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Se dió un error al ejecutar estas instrucciones"
                + "\n info Mesagge:" + e.getMessage());
        }
    }//GEN-LAST:event_InsertarVehiculoActionPerformed

    private void LimpiarCasillasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimpiarCasillasActionPerformed
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField8.setText("");
    }//GEN-LAST:event_LimpiarCasillasActionPerformed

    private void LimpiarInformacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimpiarInformacionActionPerformed
        jTextArea1.setText("");
    }//GEN-LAST:event_LimpiarInformacionActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UsaVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UsaVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UsaVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UsaVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UsaVehiculo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AlmacenarDatos;
    private javax.swing.JButton IngresarRevision;
    private javax.swing.JButton InsertarVehiculo;
    private javax.swing.JButton LimpiarCasillas;
    private javax.swing.JButton LimpiarInformacion;
    private javax.swing.JButton RecuperarDatos;
    private javax.swing.JButton ReporteUnVehiculo;
    private javax.swing.JButton ReporteVehiculos;
    private javax.swing.JButton Salir;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField8;
    // End of variables declaration//GEN-END:variables
}
