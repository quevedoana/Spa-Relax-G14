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
import java.util.Date;
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
    private List<Cliente> listaClientes = new ArrayList<>();
    private VistaTurno vistaTurnoPadre;

    public AgregarDiaDeSpa(Long dni) {
        this(null, dni);
    }

    public AgregarDiaDeSpa() {
        this(null, null);
    }

    public AgregarDiaDeSpa(VistaTurno vistaTurnoPadre, Long dni) {
        initComponents();
        this.vistaTurnoPadre = vistaTurnoPadre;
        jSHora.setModel(new javax.swing.SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(jSHora, "HH:mm");
        jSHora.setEditor(editor);
        if (dni != null) {
            cargarCliente(dni);
        } else {
            cargarClientes();
        }

        setTitle("Crear Dia de Spa");

        btnCrear.setText("Crear Dia de Spa y Continuar con Reserva");
    }

    public void cargarClientes() {
        jCCliente.removeAllItems();
        listaClientes.clear();

        listaClientes = clienteData.ListarCliente();

        if (listaClientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay clientes disponibles");
            return;
        }

        for (Cliente cliente : listaClientes) {
            jCCliente.addItem(cliente.getNombreCompleto() + " - DNI: " + cliente.getDni());
        }
    }

    public void cargarCliente(long dni) {
        jCCliente.removeAllItems();
        listaClientes.clear();

        Cliente cliente = clienteData.buscarClientePorDni(dni);
        if (cliente != null) {
            jCCliente.addItem(cliente.getNombreCompleto() + " - DNI: " + cliente.getDni());
            listaClientes.add(cliente);
        } else {
            JOptionPane.showMessageDialog(this, "Cliente no encontrado con DNI: " + dni);
        }
    }

    private LocalDateTime obtenerFechaHoraDesdeUI() {
        try {
            java.util.Date fecha = jDFecha.getDate();
            if (fecha == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una fecha válida");
                return null;
            }

            java.util.Date horaCompleta = (java.util.Date) jSHora.getValue();
            if (horaCompleta == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una hora válida");
                return null;
            }

            LocalDate fechaLocal = fecha.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            LocalTime horaLocal = horaCompleta.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalTime()
                    .withSecond(0)
                    .withNano(0);

            return LocalDateTime.of(fechaLocal, horaLocal);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al obtener fecha y hora: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private Cliente obtenerClienteSeleccionado() {
        if (jCCliente.getSelectedIndex() == -1) {
            return null;
        }

        int indexCliente = jCCliente.getSelectedIndex();
        if (indexCliente >= 0 && indexCliente < listaClientes.size()) {
            return listaClientes.get(indexCliente);
        }
        return null;
    }

    private void crearDiaDeSpa() {
        try {
            if (jCCliente.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente");
                return;
            }

            Cliente cliente = obtenerClienteSeleccionado();
            if (cliente == null) {
                JOptionPane.showMessageDialog(this, "Cliente no valido");
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
            if (fechaHora.isBefore(LocalDateTime.now())) {
                JOptionPane.showMessageDialog(this, "No puede seleccionar una fecha y hora pasadas");
                return;
            }
            DiaDeSpa nuevoDia = new DiaDeSpa();
            nuevoDia.setFechaYHora(fechaHora);
            nuevoDia.setPreferencias(txtPreferencias.getText().trim());
            nuevoDia.setCliente(cliente);
            nuevoDia.setEstado(true);
            nuevoDia.setMonto(0.0);
            nuevoDia.setSesiones(new ArrayList<>());

            diaSpaData.guardarDiaDeSpa(nuevoDia);

            if (vistaTurnoPadre != null) {
                vistaTurnoPadre.diaDeSpaCreado(nuevoDia);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Dia de spa creado exitosamente. Ahora puede proceder con las reservas desde el menu principal.");

                VistaTurno reserva = new VistaTurno();
                reserva.setVisible(true);

                javax.swing.JDesktopPane desktop = (javax.swing.JDesktopPane) this.getParent();
                desktop.add(reserva);

                java.awt.Dimension desktopSize = desktop.getSize();
                java.awt.Dimension jifSize = reserva.getSize();
                reserva.setLocation((desktopSize.width - jifSize.width) / 2,
                        (desktopSize.height - jifSize.height) / 2);
                reserva.toFront();
            }

            this.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al crear dia de spa: " + e.getMessage()
                    + "\n\nPor favor, verifique los datos e intente nuevamente.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
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
        jLabel5 = new javax.swing.JLabel();
        btnCrear = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jSHora = new javax.swing.JSpinner();
        txtPreferencias = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jDFecha = new com.toedter.calendar.JDateChooser();
        jCCliente = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jBSalir = new javax.swing.JButton();
        btnCancelarDia = new javax.swing.JButton();

        setTitle("Agregar Dia de Spa");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 255));
        jLabel5.setText("Agregar Día de Spa");

        btnCrear.setBackground(new java.awt.Color(0, 153, 255));
        btnCrear.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnCrear.setForeground(new java.awt.Color(255, 255, 255));
        btnCrear.setText("Crear Dia de Spa");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 153, 255));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Hora:");

        txtPreferencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPreferenciasActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Preferencias:");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Fecha:");

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cliente:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel3)
                        .addGap(7, 7, 7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jDFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jSHora, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jCCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtPreferencias, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jSHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addComponent(jDFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPreferencias, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jBSalir.setBackground(new java.awt.Color(0, 153, 255));
        jBSalir.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jBSalir.setForeground(new java.awt.Color(255, 255, 255));
        jBSalir.setText("X");
        jBSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalirActionPerformed(evt);
            }
        });

        btnCancelarDia.setBackground(new java.awt.Color(0, 153, 255));
        btnCancelarDia.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnCancelarDia.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelarDia.setText("Cancelar");
        btnCancelarDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarDiaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(btnCrear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelarDia)
                .addGap(88, 88, 88))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jBSalir)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(135, 135, 135))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBSalir)
                .addGap(2, 2, 2)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarDia)
                    .addComponent(btnCrear))
                .addContainerGap(52, Short.MAX_VALUE))
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

    private void jBSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jBSalirActionPerformed

    private void btnCancelarDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarDiaActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelarDiaActionPerformed

    private void txtPreferenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPreferenciasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPreferenciasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarDia;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton jBSalir;
    private javax.swing.JComboBox<String> jCCliente;
    private com.toedter.calendar.JDateChooser jDFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSpinner jSHora;
    private javax.swing.JTextField txtPreferencias;
    // End of variables declaration//GEN-END:variables
}
