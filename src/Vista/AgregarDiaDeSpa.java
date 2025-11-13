/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Cliente;
import Modelo.DiaDeSpa;
import Persistencia.ClienteData;
import Persistencia.DiaDeSpaData;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;

/**
 *
 * @author Anitabonita
 */
public class AgregarDiaDeSpa extends javax.swing.JInternalFrame {
    ClienteData clienteData = new ClienteData();
    DiaDeSpaData diaSpaData = new DiaDeSpaData();
    private List<Cliente> listaClientes=new ArrayList<>();;

    /**
     * Creates new form AgregarDiaDeSpa
     */
    public AgregarDiaDeSpa() {
        initComponents();
        jSHora.setModel(new javax.swing.SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(jSHora, "HH:mm");
        jSHora.setEditor(editor);
        cargarClientes();
    }
    
    public void cargarClientes(){
        jCCliente.removeAllItems();
        jCCliente.removeAllItems();
        
        listaClientes = clienteData.ListarCliente(); 
        
        for (Cliente cliente : listaClientes) {
            jCCliente.addItem(cliente.getNombreCompleto() + " - DNI: " + cliente.getDni());
        }
    }
    private LocalDateTime obtenerFechaHoraDesdeUI() {
    try {
        Date fecha = (Date) jDFecha.getDate();
        if (fecha == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una fecha válida");
            return null;
        }
        
        Date horaCompleta = (Date) jSHora.getValue();
        if (horaCompleta == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una hora válida");
            return null;
        }
        
        // Convertir a LocalDate y LocalTime
        LocalDate fechaLocal = fecha.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        
        LocalTime horaLocal = horaCompleta.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalTime()
                .withSecond(0)
                .withNano(0);
        
        // Combinar
        return LocalDateTime.of(fechaLocal, horaLocal);
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al obtener fecha y hora: " + e.getMessage());
        e.printStackTrace();
        return null;
    }
}
    private Cliente obtenerClienteSeleccionado() {
        if (jCCliente.getSelectedIndex() == 0) {
            return null;
        }
        
        int indexCliente = jCCliente.getSelectedIndex() - 1;
        if (indexCliente >= 0 && indexCliente < listaClientes.size()) {
            return listaClientes.get(indexCliente);
        }
        return null;
    }
    
    private void crearDiaDeSpa(){
        try {
            // Validaciones
            if (jCCliente.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente");
                return;
            }
            
            Cliente cliente = obtenerClienteSeleccionado();
            if (cliente == null) {
                JOptionPane.showMessageDialog(this, "Cliente no válido");
                return;
            }
            
            if (txtPreferencias.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese las preferencias del cliente");
                return;
            }
            
            LocalDateTime fechaHora = obtenerFechaHoraDesdeUI();
            if (fechaHora == null) {
                return;
            }
            
            // Validar que la fecha no sea anterior a hoy
            if (fechaHora.isBefore(LocalDateTime.now())) {
                JOptionPane.showMessageDialog(this, "No puede seleccionar una fecha y hora pasadas");
                return;
            }
            
            // Crear nuevo día de spa
            DiaDeSpa nuevoDia = new DiaDeSpa();
            nuevoDia.setFechaYHora(fechaHora);
            nuevoDia.setPreferencias(txtPreferencias.getText().trim());
            nuevoDia.setCliente(cliente);
            nuevoDia.setEstado(true);
            nuevoDia.setMonto(0.0); // Se calculará después con las sesiones
            nuevoDia.setSesiones(new ArrayList<>()); // Lista vacía inicial de sesiones
            
            // Guardar en base de datos
            diaSpaData.guardarDiaDeSpa(nuevoDia);
            
            JOptionPane.showMessageDialog(this, 
                "Día de spa creado exitosamente");
            
            limpiarCampos();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al crear día de spa: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void limpiarCampos() {
        txtPreferencias.setText("");
        jCCliente.setSelectedIndex(0);
    
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
        jLabel1 = new javax.swing.JLabel();
        jCCliente = new javax.swing.JComboBox<>();
        jDFecha = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtPreferencias = new javax.swing.JTextField();
        jSHora = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnCrear = new javax.swing.JButton();

        setClosable(true);
        setTitle("Agregar Dia de Spa");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Cliente");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Fecha");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setText("Preferencias");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setText("Hora");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 102, 51));
        jLabel5.setText("Agregar Dia de Spa");

        btnCrear.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnCrear.setText("Crear Dia de Spa");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addComponent(jLabel5)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jDFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jSHora, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(68, 68, 68)
                        .addComponent(txtPreferencias, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(185, 185, 185)
                .addComponent(btnCrear)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jSHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)))))
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPreferencias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(btnCrear)
                .addContainerGap(59, Short.MAX_VALUE))
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

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        // TODO add your handling code here:
        crearDiaDeSpa();
        
    }//GEN-LAST:event_btnCrearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCrear;
    private javax.swing.JComboBox<String> jCCliente;
    private com.toedter.calendar.JDateChooser jDFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSpinner jSHora;
    private javax.swing.JTextField txtPreferencias;
    // End of variables declaration//GEN-END:variables
}
