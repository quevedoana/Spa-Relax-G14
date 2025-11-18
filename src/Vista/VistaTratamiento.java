/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Tratamiento;
import Persistencia.TratamientoData;
import java.util.*;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Anitabonita
 */
public class VistaTratamiento extends javax.swing.JInternalFrame {

    private TratamientoData tratamientoData = new TratamientoData();
    private DefaultTableModel modeloTabla;

    public VistaTratamiento() {
        initComponents();
        inicializarTabla();
        cargarTratamientosPorTipo(null);
        inicializarEditorEspecialidadEnTabla();

        ButtonGroup grupoTipos = new ButtonGroup();
        grupoTipos.add(btnFacial);
        grupoTipos.add(btnCorporal);
        grupoTipos.add(btnRelajacion);
        grupoTipos.add(btnEstetico);

        btnFacial.addActionListener(this::btnFacialActionPerformed);
        btnCorporal.addActionListener(this::btnCorporalActionPerformed);
        btnRelajacion.addActionListener(this::btnRelajacionActionPerformed);
        btnEstetico.addActionListener(this::btnEsteticoActionPerformed);

        btnActualizarTablaTrata.addActionListener(this::btnActualizarTablaTrataActionPerformed);
        btnAgregarTratam.addActionListener(this::btnAgregarTratamActionPerformed);
        btnEliminarTrata.addActionListener(this::btnEliminarTrataActionPerformed);
        btnModificarTrata.addActionListener(this::btnModificarTrataActionPerformed);
    }
    //Inicializado de tabla
    private void inicializarTabla() {
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {      
                return column == 1 || column == 2 || column == 3 || column == 4 || column == 5;
            }
        };

        String[] columnas = {"Código", "Nombre", "Tipo", "Detalle", "Duración (min)", "Costo", "Producto", "Activo"};
        for (String columna : columnas) {
            modeloTabla.addColumn(columna);
        }

        tablaTratamientosFiltrados.setModel(modeloTabla);
    }
    //Carga de tratamientos por tipo de tratamiento pasado por parametro

    private void cargarTratamientosPorTipo(String tipo) {
        modeloTabla.setRowCount(0);

        List<Tratamiento> tratamientos;
        if (tipo == null || tipo.isEmpty()) {
            tratamientos = tratamientoData.listarTodosTratamientos(); 
        } else {
            tratamientos = tratamientoData.listarTratamientosPorTipo(tipo);
        }

        for (Tratamiento t : tratamientos) {
            Object[] fila = {
                t.getCodTratam(),
                t.getNombre(),
                t.getTipo(),
                t.getDetalle(),
                t.getDuracion(),
                t.getCosto(),
                t.getProductos(),
                t.isActivo() ? "Sí" : "No"
            };
            modeloTabla.addRow(fila);
        }
    }
    
    //Inicializa el editor de la especialidad
    private void inicializarEditorEspecialidadEnTabla() {
        String[] opciones = { "Facial", "Corporal", "Relajación", "Estético" };
        TableColumn columnaEspecialidad = tablaTratamientosFiltrados.getColumnModel().getColumn(2);
        JComboBox<String> comboEditor = new JComboBox<>(opciones);
        columnaEspecialidad.setCellEditor(new DefaultCellEditor(comboEditor));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaTratamientosFiltrados = new javax.swing.JTable();
        btnFacial = new javax.swing.JRadioButton();
        btnCorporal = new javax.swing.JRadioButton();
        btnRelajacion = new javax.swing.JRadioButton();
        btnEstetico = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        btnEliminarTrata = new javax.swing.JButton();
        btnModificarTrata = new javax.swing.JButton();
        btnActualizarTablaTrata = new javax.swing.JButton();
        btnAgregarTratam = new javax.swing.JButton();
        Salir = new javax.swing.JButton();

        setResizable(true);
        setTitle("Ver Tratamientos");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tablaTratamientosFiltrados.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tablaTratamientosFiltrados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablaTratamientosFiltrados);

        btnFacial.setText("Facial");
        btnFacial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFacialActionPerformed(evt);
            }
        });

        btnCorporal.setText("Corporal");
        btnCorporal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCorporalActionPerformed(evt);
            }
        });

        btnRelajacion.setText("Relajacion");
        btnRelajacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRelajacionActionPerformed(evt);
            }
        });

        btnEstetico.setText("Estetico");
        btnEstetico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEsteticoActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(0, 153, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 255));
        jLabel1.setText("TRATAMIENTOS");

        btnEliminarTrata.setBackground(new java.awt.Color(0, 153, 255));
        btnEliminarTrata.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEliminarTrata.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarTrata.setText("Eliminar");
        btnEliminarTrata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTrataActionPerformed(evt);
            }
        });

        btnModificarTrata.setBackground(new java.awt.Color(0, 153, 255));
        btnModificarTrata.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnModificarTrata.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarTrata.setText("Modificar");
        btnModificarTrata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarTrataActionPerformed(evt);
            }
        });

        btnActualizarTablaTrata.setBackground(new java.awt.Color(0, 153, 255));
        btnActualizarTablaTrata.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnActualizarTablaTrata.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizarTablaTrata.setText("Actualizar");
        btnActualizarTablaTrata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarTablaTrataActionPerformed(evt);
            }
        });

        btnAgregarTratam.setBackground(new java.awt.Color(0, 153, 255));
        btnAgregarTratam.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAgregarTratam.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarTratam.setText("Agregar Tratamiento");
        btnAgregarTratam.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        btnAgregarTratam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarTratamActionPerformed(evt);
            }
        });

        Salir.setBackground(new java.awt.Color(0, 153, 255));
        Salir.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Salir.setForeground(new java.awt.Color(255, 255, 255));
        Salir.setText("X");
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(btnEliminarTrata)
                .addGap(92, 92, 92)
                .addComponent(btnModificarTrata)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnActualizarTablaTrata)
                .addGap(76, 76, 76))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnFacial)
                        .addGap(18, 18, 18)
                        .addComponent(btnCorporal)
                        .addGap(18, 18, 18)
                        .addComponent(btnRelajacion))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(13, 13, 13)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnEstetico)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Salir)
                        .addContainerGap())))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(219, 219, 219)
                        .addComponent(btnAgregarTratam, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Salir)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFacial)
                    .addComponent(btnCorporal)
                    .addComponent(btnRelajacion)
                    .addComponent(btnEstetico))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminarTrata)
                    .addComponent(btnModificarTrata)
                    .addComponent(btnActualizarTablaTrata))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(btnAgregarTratam, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFacialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFacialActionPerformed
        // TODO add your handling code here:
        cargarTratamientosPorTipo("Facial");
    }//GEN-LAST:event_btnFacialActionPerformed

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_SalirActionPerformed
    private boolean validarLetra(String nombre) {
    if (nombre == null || nombre.trim().isEmpty()) {
        return false;
    }
    String regex = "[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+";
    return nombre.matches(regex);
}
    //Funcion de modificar action performed que sirve para modificar con lo nuevo que haya agregado
    private void btnModificarTrataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarTrataActionPerformed
        // TODO add your handling code here:
        if (tablaTratamientosFiltrados.isEditing()) {
        tablaTratamientosFiltrados.getCellEditor().stopCellEditing();
        }
        int filaSeleccionada = tablaTratamientosFiltrados.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un tratamiento para modificar");
            return;
        }

        try {
            int codTratam = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            String producto = (String) modeloTabla.getValueAt(filaSeleccionada, 6);
            boolean activo = "Sí".equals(modeloTabla.getValueAt(filaSeleccionada, 7));

            String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
            if (!validarLetra(nombre)) {
    JOptionPane.showMessageDialog(this,
        "El nombre debe contener solo letras y espacios.",
        "Advertencia",
        JOptionPane.WARNING_MESSAGE);
    return;
}
            //String tip = (String) modeloTabla.getValueAt(filaSeleccionada, 2);
            String tipo = modeloTabla.getValueAt(filaSeleccionada, 2).toString().trim();
            String detalle = (String) modeloTabla.getValueAt(filaSeleccionada, 3);

            int duracion;
            double costo;

            try {
                Object duracionObj = modeloTabla.getValueAt(filaSeleccionada, 4);
                if (duracionObj instanceof String) {
                    String duracionStr = ((String) duracionObj).replaceAll("[^0-9]", "");
                    duracion = duracionStr.isEmpty() ? 0 : Integer.parseInt(duracionStr);
                } else {
                    duracion = (int) duracionObj;
                }

                Object costoObj = modeloTabla.getValueAt(filaSeleccionada, 5);
                if (costoObj instanceof String) {
                    String costoStr = ((String) costoObj).replace("$", "").replace(",", ".").trim();
                    costo = costoStr.isEmpty() ? 0.0 : Double.parseDouble(costoStr);
                } else {
                    costo = (double) costoObj;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error en formato: Duración (número entero) y Costo (número decimal)");
                return;
            }

            // controles
            if (nombre == null || nombre.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío");
                return;
            }

           
            Tratamiento tratamientoModificado = new Tratamiento(nombre,detalle,tipo,duracion,costo,activo,producto);
            //tratamientoModificado.setCodTratam(codTratam);
            tratamientoModificado.setCodTratam(codTratam);

             

           if (tratamientoData.ModificarTratamiento(tratamientoModificado)) {
                JOptionPane.showMessageDialog(this, "Tratamiento modificado correctamente");
                cargarTratamientosPorTipo(tipo); // Recargar tabla
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo modificar el tratamiento");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, " Error al modificar: " + e.getMessage());
        }
    }//GEN-LAST:event_btnModificarTrataActionPerformed
    //Boton eliminar
    private void btnEliminarTrataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTrataActionPerformed
        // TODO add your handling code here:
        int filaSeleccionada = tablaTratamientosFiltrados.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un tratamiento para eliminar");
            return;
        }

        int codTratam = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea eliminar el tratamiento: " + nombre + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (tratamientoData.BajaTratamiento(codTratam)) {
                JOptionPane.showMessageDialog(this, "Tratamiento eliminado correctamente");
                cargarTratamientosPorTipo(null);
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el tratamiento");
            }
        }
    }//GEN-LAST:event_btnEliminarTrataActionPerformed
    //BOTON que agrega el tratamiento 
    private void btnAgregarTratamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarTratamActionPerformed
        // TODO add your handling code here:
         AgregarTratamiento agregarTratamiento = new AgregarTratamiento();
        agregarTratamiento.setVisible(true);
        javax.swing.JDesktopPane desktop = (javax.swing.JDesktopPane) this.getParent();
            desktop.add(agregarTratamiento);

            java.awt.Dimension desktopSize = desktop.getSize();
            java.awt.Dimension jifSize = agregarTratamiento.getSize();
            agregarTratamiento.setLocation((desktopSize.width - jifSize.width) / 2,
                    (desktopSize.height - jifSize.height) / 2);
                    agregarTratamiento.toFront();
    }//GEN-LAST:event_btnAgregarTratamActionPerformed
    //Actualiza la tabla
    private void btnActualizarTablaTrataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarTablaTrataActionPerformed
        // TODO add your handling code here:
         cargarTratamientosPorTipo(null);
        // Deseleccionar radio buttons
        btnFacial.setSelected(false);
        btnCorporal.setSelected(false);
        btnRelajacion.setSelected(false);
        btnEstetico.setSelected(false);
        JOptionPane.showMessageDialog(this, "Tabla actualizada correctamente");
    }//GEN-LAST:event_btnActualizarTablaTrataActionPerformed
    //Los 3 radio buttons
    private void btnCorporalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCorporalActionPerformed
        // TODO add your handling code here:
        cargarTratamientosPorTipo("Corporal");
    }//GEN-LAST:event_btnCorporalActionPerformed

    private void btnRelajacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRelajacionActionPerformed
        // TODO add your handling code here:
        cargarTratamientosPorTipo("Relajacion");
    }//GEN-LAST:event_btnRelajacionActionPerformed

    private void btnEsteticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEsteticoActionPerformed
        // TODO add your handling code here:
        cargarTratamientosPorTipo("Estetico");
    }//GEN-LAST:event_btnEsteticoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Salir;
    private javax.swing.JButton btnActualizarTablaTrata;
    private javax.swing.JButton btnAgregarTratam;
    private javax.swing.JRadioButton btnCorporal;
    private javax.swing.JButton btnEliminarTrata;
    private javax.swing.JRadioButton btnEstetico;
    private javax.swing.JRadioButton btnFacial;
    private javax.swing.JButton btnModificarTrata;
    private javax.swing.JRadioButton btnRelajacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaTratamientosFiltrados;
    // End of variables declaration//GEN-END:variables
}
