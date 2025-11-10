/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Vista;

import Modelo.Tratamiento;
import Persistencia.TratamientoData;
import Modelo.Productos;
import javax.swing.*;

/**
 *
 * @author maria
 */
public class AgregarTratamiento extends javax.swing.JInternalFrame {

    private TratamientoData tratamientoData = new TratamientoData();
    private Productos producto;
    private VistaTratamiento vistaPadre;

    public AgregarTratamiento() {
        initComponents();
        tratamientoData = new TratamientoData();
        producto = new Productos();

        // Agrupar radio buttons
        ButtonGroup grupoTipos = new ButtonGroup();
        grupoTipos.add(radioFacial);
        grupoTipos.add(radioCorporal);
        grupoTipos.add(radioRelajacion);
        grupoTipos.add(radioEstetico);

        // Agregar listeners a radio buttons para actualizar productos
        radioFacial.addActionListener(e -> actualizarProductosPorTipo("Facial"));
        radioCorporal.addActionListener(e -> actualizarProductosPorTipo("Corporal"));
        radioRelajacion.addActionListener(e -> actualizarProductosPorTipo("Relajacion"));
        radioEstetico.addActionListener(e -> actualizarProductosPorTipo("Estetico"));

        // Agregar listeners a botones
        btnAgregarNuevoTratam.addActionListener(this::btnAgregarNuevoTratamActionPerformed);
        btnCancelarAggTratam.addActionListener(this::btnCancelarAggTratamActionPerformed);
    }

    private void actualizarProductosPorTipo(String tipo) {
        comboProductos.removeAllItems();
        java.util.List<String> productos = producto.getProductosPorTipo(tipo);
        for (String producto : productos) {
            comboProductos.addItem(producto);
        }
    }

    private String obtenerTipoSeleccionado() {
        if (radioFacial.isSelected()) {
            return "Facial";
        }
        if (radioCorporal.isSelected()) {
            return "Corporal";
        }
        if (radioRelajacion.isSelected()) {
            return "Relajacion";
        }
        if (radioEstetico.isSelected()) {
            return "Estetico";
        }
        return "";
    }

    private boolean validarCampos() {// Controles
        if (textNombreTratam.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del tratamiento es obligatorio");
            return false;
        }
        if (textDetalleTratam.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El detalle del tratamiento es obligatorio");
            return false;
        }
        if (obtenerTipoSeleccionado().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un tipo de tratamiento");
            return false;
        }
        if (textDuracionTratam.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La duración es obligatoria");
            return false;
        }
        if (textCostoTratam.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El costo es obligatorio");
            return false;
        }
        return true;
    }

    private void limpiarFormulario() {
        textNombreTratam.setText("");
        textDetalleTratam.setText("");
        textDuracionTratam.setText("");
        textCostoTratam.setText("");
        radioFacial.setSelected(false);
        radioCorporal.setSelected(false);
        radioRelajacion.setSelected(false);
        radioEstetico.setSelected(false);
        comboProductos.removeAllItems();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        textNombreTratam = new javax.swing.JTextField();
        textDetalleTratam = new javax.swing.JTextField();
        textDuracionTratam = new javax.swing.JTextField();
        textCostoTratam = new javax.swing.JTextField();
        comboProductos = new javax.swing.JComboBox<>();
        radioFacial = new javax.swing.JRadioButton();
        radioEstetico = new javax.swing.JRadioButton();
        radioCorporal = new javax.swing.JRadioButton();
        radioRelajacion = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnAgregarNuevoTratam = new javax.swing.JButton();
        btnCancelarAggTratam = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setTitle("Agregar Tratamiento");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 153, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 153, 102), null));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tipo:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nombre del Tratamiento:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Detalle:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Producto:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Duracion:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Costo:   $");

        comboProductos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));

        radioFacial.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        radioFacial.setForeground(new java.awt.Color(255, 255, 255));
        radioFacial.setText("Facial");

        radioEstetico.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        radioEstetico.setForeground(new java.awt.Color(255, 255, 255));
        radioEstetico.setText("Estetico");

        radioCorporal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        radioCorporal.setForeground(new java.awt.Color(255, 255, 255));
        radioCorporal.setText("Corporal");

        radioRelajacion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        radioRelajacion.setForeground(new java.awt.Color(255, 255, 255));
        radioRelajacion.setText("Relajacion");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("min");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(22, 22, 22)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(jLabel6)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(textDuracionTratam, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel8)
                                                                .addGap(25, 25, 25)
                                                                .addComponent(jLabel7)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(textCostoTratam, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel5)
                                                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                .addComponent(jLabel2)
                                                                                .addComponent(jLabel4)))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                                .addComponent(radioFacial)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addComponent(radioEstetico)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addComponent(radioCorporal)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addComponent(radioRelajacion))
                                                                        .addComponent(textDetalleTratam, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(comboProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(16, 16, 16)
                                                .addComponent(jLabel3)
                                                .addGap(18, 18, 18)
                                                .addComponent(textNombreTratam, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(textNombreTratam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(radioFacial)
                                        .addComponent(radioEstetico)
                                        .addComponent(radioCorporal)
                                        .addComponent(radioRelajacion))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(textDetalleTratam, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4))
                                .addGap(20, 20, 20)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(comboProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(textDuracionTratam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7)
                                        .addComponent(textCostoTratam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8))
                                .addContainerGap(32, Short.MAX_VALUE))
        );

        jLabel1.setBackground(new java.awt.Color(255, 153, 102));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 102));
        jLabel1.setText("AGREGAR TRATAMIENTO NUEVO");

        btnAgregarNuevoTratam.setBackground(new java.awt.Color(255, 153, 102));
        btnAgregarNuevoTratam.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAgregarNuevoTratam.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarNuevoTratam.setText("Agregar");
        btnAgregarNuevoTratam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarNuevoTratamActionPerformed(evt);
            }
        });

        btnCancelarAggTratam.setBackground(new java.awt.Color(255, 153, 102));
        btnCancelarAggTratam.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCancelarAggTratam.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelarAggTratam.setText("Cancelar");
        btnCancelarAggTratam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarAggTratamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(16, 16, 16)
                                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(119, 119, 119)
                                                .addComponent(jLabel1))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(128, 128, 128)
                                                .addComponent(btnAgregarNuevoTratam)
                                                .addGap(98, 98, 98)
                                                .addComponent(btnCancelarAggTratam)))
                                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAgregarNuevoTratam)
                                        .addComponent(btnCancelarAggTratam))
                                .addContainerGap(26, Short.MAX_VALUE))
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
    }// </editor-fold>                        

    private void btnAgregarNuevoTratamActionPerformed(java.awt.event.ActionEvent evt) {
        if (!validarCampos()) {
            return;
        }

        try {
            
            String nombre = textNombreTratam.getText().trim();
            String detalle = textDetalleTratam.getText().trim();
            String tipo = obtenerTipoSeleccionado();
            int duracion = Integer.parseInt(textDuracionTratam.getText().trim());
            double costo = Double.parseDouble(textCostoTratam.getText().trim());
            String producto = (String) comboProductos.getSelectedItem();
            
            

            
            Tratamiento nuevoTratamiento = new Tratamiento(
                    nombre, detalle, tipo, duracion, costo, true, producto
            );

            
            tratamientoData.AltaTratamiento(nuevoTratamiento);

            JOptionPane.showMessageDialog(this, "Tratamiento agregado correctamente con codigo: " + nuevoTratamiento.getCodTratam());

            limpiarFormulario();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en formato numerico: Duracion y Costo deben ser numeros validos");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar tratamiento: " + e.getMessage());
        }
    }

    private void btnCancelarAggTratamActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        this.dispose();
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnAgregarNuevoTratam;
    private javax.swing.JButton btnCancelarAggTratam;
    private javax.swing.JComboBox<String> comboProductos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton radioCorporal;
    private javax.swing.JRadioButton radioEstetico;
    private javax.swing.JRadioButton radioFacial;
    private javax.swing.JRadioButton radioRelajacion;
    private javax.swing.JTextField textCostoTratam;
    private javax.swing.JTextField textDetalleTratam;
    private javax.swing.JTextField textDuracionTratam;
    private javax.swing.JTextField textNombreTratam;
    // End of variables declaration                   
}
