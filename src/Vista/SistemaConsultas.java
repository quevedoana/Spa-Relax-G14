/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Vista;

import Modelo.DiaDeSpa;
import Modelo.Especialista;
import Modelo.Instalacion;
import Modelo.Tratamiento;
import Persistencia.DiaDeSpaData;
import Persistencia.EspecialistaData;
import Persistencia.InstalacionData;
import Persistencia.TratamientoData;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author maria
 */
public class SistemaConsultas extends javax.swing.JInternalFrame {

    private EspecialistaData especialistaData;
    private TratamientoData tratamientoData;
    private InstalacionData instalacionData;
    private DiaDeSpaData diaDeSpaData;

    /**
     * Creates new form SistemaConsultas
     */
    public SistemaConsultas() {
        initComponents();
        especialistaData = new EspecialistaData();
        tratamientoData = new TratamientoData();
        instalacionData = new InstalacionData();
        diaDeSpaData = new DiaDeSpaData();
        cargarComboboxes();
        configurarCamposHora();
    }
    private void configurarCamposHora(){
        horaInicioEspecialistasLibres.setModel(new javax.swing.SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(horaInicioEspecialistasLibres, "HH:mm");
        horaInicioEspecialistasLibres.setEditor(editor);
        horaInicioEspecialistasLibres.setValue(new java.util.Date());
        
        horaFinEspecialistasLibres.setModel(new javax.swing.SpinnerDateModel());
        editor = new JSpinner.DateEditor(horaFinEspecialistasLibres, "HH:mm");
        horaFinEspecialistasLibres.setEditor(editor);
        horaFinEspecialistasLibres.setValue(new java.util.Date());
        
        horaInicioInstalacionesLibres.setModel(new javax.swing.SpinnerDateModel());
        editor = new JSpinner.DateEditor(horaInicioInstalacionesLibres, "HH:mm");
        horaInicioInstalacionesLibres.setEditor(editor);
        horaInicioInstalacionesLibres.setValue(new java.util.Date());
        
        horaFinInstalacionesLibres.setModel(new javax.swing.SpinnerDateModel());
        editor = new JSpinner.DateEditor(horaFinInstalacionesLibres, "HH:mm");
        horaFinInstalacionesLibres.setEditor(editor);
        horaFinInstalacionesLibres.setValue(new java.util.Date());
        
        
    }

    private void cargarComboboxes() {

        comboEspecialidades.removeAllItems();
        comboTiposTratam.removeAllItems();

        String[] especialidades = {"facial", "corporal", "relajación", "estético"};
        for (String esp : especialidades) {
            comboEspecialidades.addItem(esp);
            comboTiposTratam.addItem(esp);
        }
    }

    private void mostrarResultadosEnTabla(List<?> resultados, String[] columnas, String tipo) {
        try {
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            modelo.setRowCount(0);

            if (resultados == null || resultados.isEmpty()) {
                tabla.setModel(modelo);
                JOptionPane.showMessageDialog(this, "No se encontraron resultados",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

             if ("array".equals(tipo)) {
            for (Object item : resultados) {
                Object[] filaArray = (Object[]) item;
                modelo.addRow(filaArray);
            }
        } else {
            
            for (Object item : resultados) {
                Object[] fila = null;

                switch (tipo) {
                    case "especialista":
                        Especialista esp = (Especialista) item;
                        fila = new Object[]{
                            esp.getMatricula(),
                            esp.getNombreYApellido(),
                            esp.getTelefono(),
                            esp.getEspecialidad()
                        };
                        break;

                    case "tratamiento":
                        Tratamiento trat = (Tratamiento) item;
                        fila = new Object[]{
                            trat.getCodTratam(),
                            trat.getNombre(),
                            trat.getTipo(),
                            trat.getDuracion() + " min",
                            "$ " + trat.getCosto()
                        };
                        break;

                    case "instalacion":
                        Instalacion inst = (Instalacion) item;
                        fila = new Object[]{
                            inst.getCodInstal(),
                            inst.getNombre(),
                            inst.getDetalleDeUso(),
                            "$ " + inst.getPrecio30m()
                        };
                        break;

                    case "diadespa":
                        DiaDeSpa dia = (DiaDeSpa) item;
                        fila = new Object[]{
                            dia.getCodPack(),
                            dia.getCliente().getNombreCompleto(),
                            dia.getFechaYHora().toString(),
                            dia.getPreferencias(),
                            "$ " + dia.getMonto(),
                            dia.getSesiones() != null ? dia.getSesiones().size() : 0
                        };
                        break;
                }

                if (fila != null) {
                    modelo.addRow(fila);
                }
            }
        }

            tabla.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al mostrar resultados: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
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

        jPanel2 = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        textConsultaSeleccionada = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        comboEspecialidades = new javax.swing.JComboBox<>();
        comboTiposTratam = new javax.swing.JComboBox<>();
        btnConsultarEspecialistas1 = new javax.swing.JButton();
        btnConsultarTratamientos1 = new javax.swing.JButton();
        horaInicioEspecialistasLibres = new javax.swing.JSpinner();
        horaFinEspecialistasLibres = new javax.swing.JSpinner();
        horaFinInstalacionesLibres = new javax.swing.JSpinner();
        horaInicioInstalacionesLibres = new javax.swing.JSpinner();
        fechaInicioTratamientos = new com.toedter.calendar.JDateChooser();
        fechaFinTratamientos = new com.toedter.calendar.JDateChooser();
        fechaInformeDiadeSpa = new com.toedter.calendar.JDateChooser();
        btnConsultarEspacialistasLibre1 = new javax.swing.JButton();
        btnConsultarInformeEstadisticos1 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        btnConsultarInstalacionesLibres1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();

        setTitle("Consultas");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 153, 102));
        jLabel8.setText("Consulta Seleccionada:");

        textConsultaSeleccionada.setColumns(20);
        textConsultaSeleccionada.setRows(5);
        jScrollPane3.setViewportView(textConsultaSeleccionada);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 153, 102));
        jLabel9.setText("1. Especialistas por Especialidad");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 153, 102));
        jLabel10.setText("2. Tratamientos por Tipo");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 153, 102));
        jLabel11.setText("3. Especialistas Libres");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 153, 102));
        jLabel12.setText("4. Instalaciones Libres");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 153, 102));
        jLabel13.setText("5. Tratamientos mas Seleccionados");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 153, 102));
        jLabel14.setText("6. Informes Dias de Spa");

        comboEspecialidades.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        comboTiposTratam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnConsultarEspecialistas1.setBackground(new java.awt.Color(255, 153, 102));
        btnConsultarEspecialistas1.setForeground(new java.awt.Color(255, 255, 255));
        btnConsultarEspecialistas1.setText("Consultar");
        btnConsultarEspecialistas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarEspecialistas1ActionPerformed(evt);
            }
        });

        btnConsultarTratamientos1.setBackground(new java.awt.Color(255, 153, 102));
        btnConsultarTratamientos1.setForeground(new java.awt.Color(255, 255, 255));
        btnConsultarTratamientos1.setText("Consultar");
        btnConsultarTratamientos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarTratamientos1ActionPerformed(evt);
            }
        });

        btnConsultarEspacialistasLibre1.setBackground(new java.awt.Color(255, 153, 102));
        btnConsultarEspacialistasLibre1.setForeground(new java.awt.Color(255, 255, 255));
        btnConsultarEspacialistasLibre1.setText("Consultar");
        btnConsultarEspacialistasLibre1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarEspacialistasLibre1ActionPerformed(evt);
            }
        });

        btnConsultarInformeEstadisticos1.setBackground(new java.awt.Color(255, 153, 102));
        btnConsultarInformeEstadisticos1.setForeground(new java.awt.Color(255, 255, 255));
        btnConsultarInformeEstadisticos1.setText("Consultar");
        btnConsultarInformeEstadisticos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarInformeEstadisticos1ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(255, 153, 102));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Consultar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tabla);

        btnConsultarInstalacionesLibres1.setBackground(new java.awt.Color(255, 153, 102));
        btnConsultarInstalacionesLibres1.setForeground(new java.awt.Color(255, 255, 255));
        btnConsultarInstalacionesLibres1.setText("Consultar");
        btnConsultarInstalacionesLibres1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarInstalacionesLibres1ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 153, 102));
        jLabel15.setText("a");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 153, 102));
        jLabel16.setText("a");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel9)
                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(fechaInformeDiadeSpa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(horaInicioInstalacionesLibres)
                    .addComponent(horaInicioEspecialistasLibres)
                    .addComponent(comboTiposTratam, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboEspecialidades, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fechaInicioTratamientos, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(220, 220, 220))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(fechaFinTratamientos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(horaFinInstalacionesLibres)
                            .addComponent(horaFinEspecialistasLibres)
                            .addComponent(btnConsultarEspecialistas1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnConsultarTratamientos1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnConsultarEspacialistasLibre1)
                            .addComponent(btnConsultarInformeEstadisticos1)
                            .addComponent(btnConsultarInstalacionesLibres1))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 642, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 642, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboEspecialidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnConsultarEspecialistas1)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboTiposTratam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnConsultarTratamientos1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(horaInicioEspecialistasLibres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(horaFinEspecialistasLibres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnConsultarEspacialistasLibre1)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(horaInicioInstalacionesLibres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(horaFinInstalacionesLibres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnConsultarInstalacionesLibres1)
                            .addComponent(jLabel16)
                            .addComponent(jLabel12)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(fechaInicioTratamientos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(fechaFinTratamientos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnConsultarInformeEstadisticos1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton6)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel14)
                                .addComponent(fechaInformeDiadeSpa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 650, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConsultarEspecialistas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarEspecialistas1ActionPerformed
        try {
            String especialidad = (String) comboEspecialidades.getSelectedItem();
            textConsultaSeleccionada.setText("Masajistas de especialidad: " + especialidad);

            List<Especialista> resultados = especialistaData.listarMasajistasPorEspecialidad(especialidad);
            mostrarResultadosEnTabla(resultados, new String[]{"Matricula", "Nombre", "Telefono", "Especialidad"},"especialista");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en consulta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }    }//GEN-LAST:event_btnConsultarEspecialistas1ActionPerformed

    private void btnConsultarTratamientos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarTratamientos1ActionPerformed
        try {
            String tipo = (String) comboTiposTratam.getSelectedItem();
            textConsultaSeleccionada.setText("Tratamientos de tipo: " + tipo);

            List<Tratamiento> resultados = tratamientoData.listarTratamientosPorTipo(tipo);
            mostrarResultadosEnTabla(resultados, new String[]{"Codigo", "Nombre", "Tipo", "Duracion", "Costo"},"tratamiento");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en consulta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnConsultarTratamientos1ActionPerformed

    private void btnConsultarEspacialistasLibre1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarEspacialistasLibre1ActionPerformed
        try {
            java.util.Date inicio = (java.util.Date) horaInicioEspecialistasLibres.getValue();
            java.util.Date fin = (java.util.Date) horaFinEspecialistasLibres.getValue();

            if (inicio == null || fin == null) {
                JOptionPane.showMessageDialog(this, "Seleccione hora inicio y fin", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Timestamp tsInicio = new Timestamp(inicio.getTime());
            Timestamp tsFin = new Timestamp(fin.getTime());

            textConsultaSeleccionada.setText("Masajistas libres de "
                    + new SimpleDateFormat("HH:mm").format(inicio) + " a "
                    + new SimpleDateFormat("HH:mm").format(fin));

            List<Especialista> resultados = especialistaData.listarMasajistasLibresEnFranja(tsInicio, tsFin);
            mostrarResultadosEnTabla(resultados, new String[]{"Matrícula", "Nombre", "Teléfono", "Especialidad"},"especialista");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en consulta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }    }//GEN-LAST:event_btnConsultarEspacialistasLibre1ActionPerformed

    private void btnConsultarInstalacionesLibres1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarInstalacionesLibres1ActionPerformed
        try {
            java.util.Date inicio = (java.util.Date) horaInicioInstalacionesLibres.getValue();
            java.util.Date fin = (java.util.Date) horaFinInstalacionesLibres.getValue();

            if (inicio == null || fin == null) {
                JOptionPane.showMessageDialog(this, "Seleccione hora inicio y fin", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Timestamp tsInicio = new Timestamp(inicio.getTime());
            Timestamp tsFin = new Timestamp(fin.getTime());

            textConsultaSeleccionada.setText("Instalaciones libres de "
                    + new SimpleDateFormat("HH:mm").format(inicio) + " a "
                    + new SimpleDateFormat("HH:mm").format(fin));

            List<Instalacion> resultados = instalacionData.listarInstalacionesLibresEnFranja(tsInicio, tsFin);
            mostrarResultadosEnTabla(resultados, new String[]{"Código", "Nombre", "Detalle", "Precio 30min"},"instalacion");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en consulta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnConsultarInstalacionesLibres1ActionPerformed

    private void btnConsultarInformeEstadisticos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarInformeEstadisticos1ActionPerformed
        try {
            java.util.Date fechaInicio = fechaInicioTratamientos.getDate();
            java.util.Date fechaFin = fechaFinTratamientos.getDate();

            if (fechaInicio == null || fechaFin == null) {
                JOptionPane.showMessageDialog(this, "Seleccione fecha inicio y fin", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Date sqlFechaInicio = new Date(fechaInicio.getTime());
            Date sqlFechaFin = new Date(fechaFin.getTime());

            textConsultaSeleccionada.setText("Informes estadísticos del "
                    + new SimpleDateFormat("dd/MM/yyyy").format(fechaInicio) + " al "
                    + new SimpleDateFormat("dd/MM/yyyy").format(fechaFin));

            // Mostrar instalaciones más solicitadas
            List<Object[]> resultados = instalacionData.listarInstalacionesMasSolicitadas(sqlFechaInicio, sqlFechaFin);
            mostrarResultadosEnTabla(resultados, new String[]{"Instalación", "Detalle", "Cantidad Reservas"}, "array");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en consulta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnConsultarInformeEstadisticos1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            java.util.Date fecha = fechaInformeDiadeSpa.getDate();

            if (fecha == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una fecha", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Date sqlFecha = new Date(fecha.getTime());

            textConsultaSeleccionada.setText("Días de Spa del " + new SimpleDateFormat("dd/MM/yyyy").format(fecha));

            List<Object[]> resultados = diaDeSpaData.generarInformeDiasDeSpaPorFecha(sqlFecha);
            mostrarResultadosEnTabla(resultados, new String[]{"Código", "Cliente", "Fecha", "Preferencias", "Monto", "Sesiones"}, "array");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en consulta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultarEspacialistasLibre1;
    private javax.swing.JButton btnConsultarEspecialistas1;
    private javax.swing.JButton btnConsultarInformeEstadisticos1;
    private javax.swing.JButton btnConsultarInstalacionesLibres1;
    private javax.swing.JButton btnConsultarTratamientos1;
    private javax.swing.JComboBox<String> comboEspecialidades;
    private javax.swing.JComboBox<String> comboTiposTratam;
    private com.toedter.calendar.JDateChooser fechaFinTratamientos;
    private com.toedter.calendar.JDateChooser fechaInformeDiadeSpa;
    private com.toedter.calendar.JDateChooser fechaInicioTratamientos;
    private javax.swing.JSpinner horaFinEspecialistasLibres;
    private javax.swing.JSpinner horaFinInstalacionesLibres;
    private javax.swing.JSpinner horaInicioEspecialistasLibres;
    private javax.swing.JSpinner horaInicioInstalacionesLibres;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable tabla;
    private javax.swing.JTextArea textConsultaSeleccionada;
    // End of variables declaration//GEN-END:variables
}
