
package Vista;

import Modelo.Consultorio;
import Persistencia.ConsultorioData;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class VistaConsultorio extends javax.swing.JInternalFrame {
    
    private ConsultorioData consultorioData = new ConsultorioData();
    private DefaultTableModel modelo;

    
    public VistaConsultorio() {
        initComponents();
        
        modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int fila, int column){
                return column != 0;
            }
        };
        armarCabecera();
        
        inicializarComboUsos();
        soloNumeros(txtNroConNew);
        soloNumeros(txtNroConBusqueda);
        refrescarTabla();
    }
    
   

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jBbuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jBborrar = new javax.swing.JButton();
        jBactualizar = new javax.swing.JButton();
        jBrefrescar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jCBapto = new javax.swing.JComboBox<>();
        jBaceptarApto = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNroConBusqueda = new javax.swing.JTextField();
        txtNroConNew = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jRBcamilla = new javax.swing.JRadioButton();
        jRBvacuumterapia = new javax.swing.JRadioButton();
        jRBvaporizador = new javax.swing.JRadioButton();
        jRBems = new javax.swing.JRadioButton();
        jRBradiofrecuencia = new javax.swing.JRadioButton();
        jRBcavitador = new javax.swing.JRadioButton();
        jBsalir = new javax.swing.JButton();
        jBguardar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jCBusos = new javax.swing.JComboBox<>();
        jBaceptarUsos = new javax.swing.JButton();

        setTitle("Consultorio");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel1.setText("Consultorio");

        jLabel2.setText("Nro de Consultorio:");

        jBbuscar.setText("Buscar");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nro", "Usos", "Apto", "Equipamiento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jBborrar.setText("Borrar");
        jBborrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBborrarActionPerformed(evt);
            }
        });

        jBactualizar.setText("Actualizar");

        jBrefrescar.setText("Refrescar");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Apto:");

        jCBapto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        jCBapto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBaptoActionPerformed(evt);
            }
        });

        jBaceptarApto.setText("Aceptar");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Agregar Consultorio");

        jLabel5.setText("Nro de Consultorio:");

        jLabel6.setText("Equipamiento:");

        jRBcamilla.setText("Camilla");

        jRBvacuumterapia.setText("Vacuumterapia");

        jRBvaporizador.setText("Vaporizador");

        jRBems.setText("EMS");

        jRBradiofrecuencia.setText("Radiofrecuencia");

        jRBcavitador.setText("Cavitador");

        jBsalir.setText("Salir");

        jBguardar.setText("Guardar");
        jBguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBguardarActionPerformed(evt);
            }
        });

        jLabel7.setText("Usos");

        jCBusos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Facial", "Corporal", "Masajes", "Depilacion" }));

        jBaceptarUsos.setText("Aceptar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(jLabel4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 5, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel3))
                                .addGap(110, 110, 110)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jCBapto, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(84, 84, 84)
                                        .addComponent(jBaceptarApto))
                                    .addComponent(jCBusos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(20, 20, 20)
                                    .addComponent(txtNroConBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jBbuscar))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jBborrar)
                                    .addGap(90, 90, 90)
                                    .addComponent(jBactualizar)
                                    .addGap(82, 82, 82)
                                    .addComponent(jBrefrescar))))
                        .addGap(20, 20, 20))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jBaceptarUsos))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(txtNroConNew))
                            .addComponent(jSeparator2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRBcamilla)
                                    .addComponent(jRBems))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRBvacuumterapia)
                                    .addComponent(jRBradiofrecuencia))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRBcavitador)
                                    .addComponent(jRBvaporizador)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jBsalir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jBguardar)))
                        .addGap(23, 23, 23))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jBbuscar)
                    .addComponent(txtNroConBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBactualizar)
                    .addComponent(jBborrar)
                    .addComponent(jBrefrescar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCBapto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBaceptarApto)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCBusos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jBaceptarUsos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNroConNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jRBvaporizador)
                    .addComponent(jRBvacuumterapia)
                    .addComponent(jRBcamilla))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRBcavitador)
                    .addComponent(jRBradiofrecuencia)
                    .addComponent(jRBems))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBguardar)
                    .addComponent(jBsalir))
                .addGap(65, 65, 65))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 466, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBborrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBborrarActionPerformed
       try {
        int nro = Integer.parseInt(txtNroConNew.getText());
        consultorioData.borrarConsultorio(nro);
        JOptionPane.showMessageDialog(this, "Consultorio eliminado correctamente");
        refrescarTabla();
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Ingrese un número válido de consultorio");
    }
    }//GEN-LAST:event_jBborrarActionPerformed

    
    
    private void jCBaptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBaptoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCBaptoActionPerformed

    private void jBguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBguardarActionPerformed
        // TODO add your handling code here:
        try {
        //Toma número de consultorio
        int nro = Integer.parseInt(txtNroConNew.getText().trim());

        //Verifica que haya al menos un equipamiento seleccionado
        StringBuilder equipamiento = new StringBuilder();
        if (jRBcamilla.isSelected()) equipamiento.append("Camilla, ");
        if (jRBvacuumterapia.isSelected()) equipamiento.append("Vacuumterapia, ");
        if (jRBvaporizador.isSelected()) equipamiento.append("Vaporizador, ");
        if (jRBems.isSelected()) equipamiento.append("EMS, ");
        if (jRBradiofrecuencia.isSelected()) equipamiento.append("Radiofrecuencia, ");
        if (jRBcavitador.isSelected()) equipamiento.append("Cavitador, ");

        if (equipamiento.length() == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione al menos un equipamiento.");
            return;
        }
        
        

        // Eliminar última coma y espacio
        equipamiento.setLength(equipamiento.length() - 2);

        //Crear objeto Consultorio
        Consultorio con = new Consultorio(nro, null, equipamiento.toString(), true);

        //Guardar en BD
        consultorioData.guardarConsultorio(con);

        //Mostrar mensaje y limpiar
        JOptionPane.showMessageDialog(this, "Consultorio guardado correctamente.");
        txtNroConNew.setText("");
        jRBcamilla.setSelected(false);
        jRBvacuumterapia.setSelected(false);
        jRBvaporizador.setSelected(false);
        jRBems.setSelected(false);
        jRBradiofrecuencia.setSelected(false);
        jRBcavitador.setSelected(false);

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Ingrese un número de consultorio válido.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al guardar el consultorio: " + e.getMessage());
    }
        
    }//GEN-LAST:event_jBguardarActionPerformed

    private void soloNumeros(javax.swing.JTextField campo) {
    campo.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyTyped(java.awt.event.KeyEvent evt) {
            char c = evt.getKeyChar();
            if (!Character.isDigit(c)) {
                evt.consume();
            }
        }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBaceptarApto;
    private javax.swing.JButton jBaceptarUsos;
    private javax.swing.JButton jBactualizar;
    private javax.swing.JButton jBborrar;
    private javax.swing.JButton jBbuscar;
    private javax.swing.JButton jBguardar;
    private javax.swing.JButton jBrefrescar;
    private javax.swing.JButton jBsalir;
    private javax.swing.JComboBox<String> jCBapto;
    private javax.swing.JComboBox<String> jCBusos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRBcamilla;
    private javax.swing.JRadioButton jRBcavitador;
    private javax.swing.JRadioButton jRBems;
    private javax.swing.JRadioButton jRBradiofrecuencia;
    private javax.swing.JRadioButton jRBvacuumterapia;
    private javax.swing.JRadioButton jRBvaporizador;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtNroConBusqueda;
    private javax.swing.JTextField txtNroConNew;
    // End of variables declaration//GEN-END:variables

    private void armarCabecera() {
    modelo.addColumn("Nro Consultorio");
    modelo.addColumn("Usos");
    modelo.addColumn("Apto");
    modelo.addColumn("Equipamiento");
    jTable1.setModel(modelo);
}

private void inicializarComboUsos() {
    jCBusos.removeAllItems();
    jCBusos.addItem("Facial");
    jCBusos.addItem("Corporal");
    jCBusos.addItem("Masajes");
    jCBusos.addItem("Depilacion");
}

private void refrescarTabla() {
    modelo.setRowCount(0); // Limpia la tabla
    List<Consultorio> lista = consultorioData.ListarConsultorios();
    for (Consultorio c : lista) {
        modelo.addRow(new Object[]{
            c.getNroConsultorio(),
            c.getUsos(),
            c.isApto() ? "Sí" : "No",
            c.getEquipamiento()
        });
    }
}

private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {                                         
    this.dispose(); // cierra solo esta ventana
    // o System.exit(0); si querés cerrar todo el programa
}

}
