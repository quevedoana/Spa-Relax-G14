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
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
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

    private void configurarCamposHora() {
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
                            // Solo si realmente es una lista de DiaDeSpa
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

    private LocalTime convertirSpinnerAHoraSegura(JSpinner spinner) {
        Object raw = spinner.getValue();

        java.util.Date dateValue;

        if (raw instanceof java.sql.Time) {
            //Si el spinner devuelve un Time
            dateValue = new java.util.Date(((java.sql.Time) raw).getTime());
        } else if (raw instanceof java.util.Date) {
            //Si devuelve un util.Date normal
            dateValue = (java.util.Date) raw;
        } else {
            throw new IllegalArgumentException("Valor inesperado en el spinner: " + raw);
        }

        return dateValue.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalTime();
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
        btnConsultarEspacialistasLibre1 = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        btnConsultarInstalacionesLibres1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jDCEspecialistasLibres = new com.toedter.calendar.JDateChooser();
        jDCInstalacionesLibres = new com.toedter.calendar.JDateChooser();
        btnConsultarInformeEstadisticos2 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();

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

        btnSalir.setBackground(new java.awt.Color(255, 153, 102));
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
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

        btnConsultarInformeEstadisticos2.setBackground(new java.awt.Color(255, 153, 102));
        btnConsultarInformeEstadisticos2.setForeground(new java.awt.Color(255, 255, 255));
        btnConsultarInformeEstadisticos2.setText("Consultar");
        btnConsultarInformeEstadisticos2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarInformeEstadisticos2ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 153, 102));
        jLabel17.setText("a");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel10)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(3, 3, 3)
                            .addComponent(jLabel11)))
                    .addComponent(jLabel12))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboTiposTratam, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboEspecialidades, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jDCEspecialistasLibres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDCInstalacionesLibres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnConsultarEspecialistas1, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                            .addComponent(btnConsultarTratamientos1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(horaInicioInstalacionesLibres, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(horaInicioEspecialistasLibres, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(horaFinEspecialistasLibres, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(horaFinInstalacionesLibres, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnConsultarEspacialistasLibre1)
                            .addComponent(btnConsultarInstalacionesLibres1)
                            .addComponent(btnConsultarInformeEstadisticos2))
                        .addGap(43, 43, 43))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(122, 122, 122)
                .addComponent(jLabel8)
                .addGap(50, 50, 50)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jSeparator4)
            .addComponent(jSeparator3)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(375, 375, 375)
                                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel13)
                                .addGap(44, 44, 44)
                                .addComponent(fechaInicioTratamientos, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(64, 64, 64)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addComponent(fechaFinTratamientos, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
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
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(horaInicioEspecialistasLibres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(horaFinEspecialistasLibres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnConsultarEspacialistasLibre1)
                                        .addComponent(jLabel15))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel11)
                                        .addComponent(jDCEspecialistasLibres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(jLabel10)))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(horaInicioInstalacionesLibres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel16)
                                .addComponent(horaFinInstalacionesLibres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnConsultarInstalacionesLibres1))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel12))
                            .addComponent(jDCInstalacionesLibres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fechaFinTratamientos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(fechaInicioTratamientos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)))
                    .addComponent(btnConsultarInformeEstadisticos2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSalir)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 12, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConsultarEspecialistas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarEspecialistas1ActionPerformed
        try {
            String especialidad = (String) comboEspecialidades.getSelectedItem();
            textConsultaSeleccionada.setText("Masajistas de especialidad: " + especialidad);

            List<Especialista> resultados = especialistaData.listarMasajistasPorEspecialidad(especialidad);
            mostrarResultadosEnTabla(resultados, new String[]{"Matricula", "Nombre", "Telefono", "Especialidad"}, "especialista");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en consulta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }    }//GEN-LAST:event_btnConsultarEspecialistas1ActionPerformed

    private void btnConsultarTratamientos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarTratamientos1ActionPerformed
        try {
            String tipo = (String) comboTiposTratam.getSelectedItem();
            textConsultaSeleccionada.setText("Tratamientos de tipo: " + tipo);

            List<Tratamiento> resultados = tratamientoData.listarTratamientosPorTipo(tipo);
            mostrarResultadosEnTabla(resultados, new String[]{"Codigo", "Nombre", "Tipo", "Duracion", "Costo"}, "tratamiento");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en consulta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnConsultarTratamientos1ActionPerformed

    private void btnConsultarEspacialistasLibre1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarEspacialistasLibre1ActionPerformed
        try {
            //Obtener fecha desde el JDateChooser
            java.util.Date fechaSeleccionada = jDCEspecialistasLibres.getDate();
            if (fechaSeleccionada == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una fecha", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            LocalDate fecha = fechaSeleccionada.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            //Obtener horas desde los spinners, de forma segura
            LocalTime hInicio = convertirSpinnerAHoraSegura(horaInicioEspecialistasLibres);
            LocalTime hFin = convertirSpinnerAHoraSegura(horaFinEspecialistasLibres);

            //Combinar fecha y horas
            LocalDateTime inicio = LocalDateTime.of(fecha, hInicio);
            LocalDateTime fin = LocalDateTime.of(fecha, hFin);

            //Validación horaria
            if (!fin.isAfter(inicio)) {
                JOptionPane.showMessageDialog(this, "La hora de fin debe ser mayor a la de inicio", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //Convertir a Timestamp
            Timestamp tsInicio = Timestamp.valueOf(inicio);
            Timestamp tsFin = Timestamp.valueOf(fin);

            //Mostrar texto
            textConsultaSeleccionada.setText("Masajistas libres de " + hInicio + " a " + hFin);

            //Obtener resultados
            List<Especialista> resultados = especialistaData.listarMasajistasLibresEnFranjaPorFecha(tsInicio, tsFin);

            //Mostrar en tabla
            mostrarResultadosEnTabla(
                    resultados,
                    new String[]{"Matrícula", "Nombre", "Teléfono", "Especialidad"},
                    "especialista"
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en consulta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }    }//GEN-LAST:event_btnConsultarEspacialistasLibre1ActionPerformed

    private void btnConsultarInstalacionesLibres1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarInstalacionesLibres1ActionPerformed

        try {
            //Obtener fecha desde el JDateChooser
            java.util.Date fechaSeleccionada = jDCInstalacionesLibres.getDate();
            if (fechaSeleccionada == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una fecha", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            LocalDate fecha = fechaSeleccionada.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            //Obtener horas desde los spinners usando la función de hora segura
            LocalTime hInicio = convertirSpinnerAHoraSegura(horaInicioInstalacionesLibres);
            LocalTime hFin = convertirSpinnerAHoraSegura(horaFinInstalacionesLibres);

            //Combinar fecha y horas
            LocalDateTime inicio = LocalDateTime.of(fecha, hInicio);
            LocalDateTime fin = LocalDateTime.of(fecha, hFin);

            //Validar rango horario
            if (!fin.isAfter(inicio)) {
                JOptionPane.showMessageDialog(this, "La hora fin debe ser mayor a hora inicio", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //Convertir a Timestamp
            Timestamp tsInicio = Timestamp.valueOf(inicio);
            Timestamp tsFin = Timestamp.valueOf(fin);

            //Texto resumen
            textConsultaSeleccionada.setText("Instalaciones libres de " + hInicio + " a " + hFin);

            //Llamar al método de la clase data
            List<Instalacion> resultados = instalacionData.listarInstalacionesLibresEnFranjaPorFecha(tsInicio, tsFin);

            //Mostrar en tabla
            mostrarResultadosEnTabla(
                    resultados,
                    new String[]{"Código", "Nombre", "Detalle", "Precio 30min"},
                    "instalacion"
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en consulta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnConsultarInstalacionesLibres1ActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnConsultarInformeEstadisticos2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarInformeEstadisticos2ActionPerformed
        // TODO add your handling code here:
        try {
        java.util.Date fechaInicio = fechaInicioTratamientos.getDate();
        java.util.Date fechaFin = fechaFinTratamientos.getDate();

        if (fechaInicio == null || fechaFin == null) {
            JOptionPane.showMessageDialog(this, "Seleccione ambas fechas", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (fechaFin.before(fechaInicio)) {
            JOptionPane.showMessageDialog(this, "La fecha fin debe ser posterior a la fecha inicio", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Date sqlFechaInicio = new Date(fechaInicio.getTime());
        Date sqlFechaFin = new Date(fechaFin.getTime());

        textConsultaSeleccionada.setText("Tratamientos más seleccionados del " + 
            new SimpleDateFormat("dd/MM/yyyy").format(fechaInicio) + " al " + 
            new SimpleDateFormat("dd/MM/yyyy").format(fechaFin));

        
        List<Object[]> resultados = tratamientoData.listarTratamientosMasSeleccionados(sqlFechaInicio, sqlFechaFin);
        mostrarResultadosEnTabla(resultados, new String[]{"Tratamiento", "Veces Seleccionado", "Total Recaudado"}, "array");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error en consulta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
        
    }//GEN-LAST:event_btnConsultarInformeEstadisticos2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultarEspacialistasLibre1;
    private javax.swing.JButton btnConsultarEspecialistas1;
    private javax.swing.JButton btnConsultarInformeEstadisticos2;
    private javax.swing.JButton btnConsultarInstalacionesLibres1;
    private javax.swing.JButton btnConsultarTratamientos1;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> comboEspecialidades;
    private javax.swing.JComboBox<String> comboTiposTratam;
    private com.toedter.calendar.JDateChooser fechaFinTratamientos;
    private com.toedter.calendar.JDateChooser fechaInicioTratamientos;
    private javax.swing.JSpinner horaFinEspecialistasLibres;
    private javax.swing.JSpinner horaFinInstalacionesLibres;
    private javax.swing.JSpinner horaInicioEspecialistasLibres;
    private javax.swing.JSpinner horaInicioInstalacionesLibres;
    private com.toedter.calendar.JDateChooser jDCEspecialistasLibres;
    private com.toedter.calendar.JDateChooser jDCInstalacionesLibres;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
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
