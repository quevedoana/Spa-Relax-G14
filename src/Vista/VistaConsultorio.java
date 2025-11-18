package Vista;

import Modelo.Consultorio;
import Persistencia.ConsultorioData;
import java.util.Arrays;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class VistaConsultorio extends javax.swing.JInternalFrame {

    private ConsultorioData consultorioData = new ConsultorioData();
    private Consultorio consultorio = null;
    private DefaultTableModel modelo = new DefaultTableModel() {

        public boolean isCellEditable(int fila, int column) {
            return column == 1 || column == 2;
        }
    };

    private void armarCabecera() {
        modelo.addColumn("Nro Consultorio");
        modelo.addColumn("Usos");
        modelo.addColumn("Equipamiento");
        modelo.addColumn("Apto");
        jTConsultorio.setModel(modelo);
    }

    private void deshabilitarBotones() {

        btnBorrar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnCambiarApto.setEnabled(false);
    }

    private void habilitarBotones() {
        btnBorrar.setEnabled(true);
        btnActualizar.setEnabled(true);
        btnCambiarApto.setEnabled(true);
    }

    private void inicializarComboTipoEspecialidad() {
        String[] opciones = {"Seleccione...", "Facial", "Corporal", "Masajes", "Depilacion"};
        jCBusos.removeAllItems();
        for (String opt : opciones) {
            jCBusos.addItem(opt);
        }
        jCBusos.setSelectedIndex(0);
    }

    private void inicializarEditorEspecialidadEnTabla() {
        String[] opciones = {"Facial", "Corporal", "Masajes", "Depilacion"};
        TableColumn columnaEspecialidad = jTConsultorio.getColumnModel().getColumn(1);
        JComboBox<String> comboEditor = new JComboBox<>(opciones);
        columnaEspecialidad.setCellEditor(new DefaultCellEditor(comboEditor));
    }

    private void cargarDatos() {
        String activo;
        try {
            modelo.setRowCount(0);

            for (Consultorio con : consultorioData.ListarConsultorios()) {
                if (con.isApto()) {
                    activo = "si";
                } else {
                    activo = "no";
                }
                modelo.addRow(new Object[]{
                    con.getNroConsultorio(),
                    con.getUsos(),
                    con.getEquipamiento(),
                    activo

                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los alumnos " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarConsultorioPorCod() {
        try {
            String cod = txtBuscaPorCod.getText().trim();
            modelo.setRowCount(0);
            if (cod.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un numero para buscar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            modelo.setRowCount(0);

            consultorio = consultorioData.buscarConsultorio(Integer.parseInt(cod));
            String activo;
            if (consultorio != null) {
                if (consultorio.isApto()) {
                    activo = "Si";
                } else {
                    activo = "No";
                }
                modelo.addRow(new Object[]{
                    consultorio.getNroConsultorio(),
                    consultorio.getUsos(),
                    consultorio.getEquipamiento(),
                    activo

                });
            }
            txtBuscaPorCod.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El codigo es un numero: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarConsultorioNuevo() {
        try {

            String especialidad = (String) jCBusos.getSelectedItem();
            String equipamiento = txtEquipamiento.getText().trim();

            if (equipamiento.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El campo equipamiento no puede estar vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                txtEquipamiento.requestFocus();
                return;
            }

            Consultorio con = new Consultorio(especialidad, equipamiento, true);
            consultorioData.guardarConsultorio(con);
            txtEquipamiento.setText("");
            JOptionPane.showMessageDialog(this, "Consultorio agregado correctamente.", "mensaje", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar la consultorio: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void borrarConsultorio() {

        int fila = jTConsultorio.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "seleccione un consultorio a eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        if (fila != -1) {

            int conf = JOptionPane.showConfirmDialog(
                    this,
                    "¿Seguro que desea eliminar el consultorio seleccionada?", "Advertencia", JOptionPane.YES_NO_OPTION);

            if (conf == JOptionPane.YES_OPTION) {
                try {
                    int cod = (int) jTConsultorio.getValueAt(fila, 0);

                    consultorioData.borrarConsultorio(cod);

                    modelo.removeRow(fila);

                    JOptionPane.showMessageDialog(this, "Consultorio eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this,
                            "Error al eliminar el consultorio: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private boolean validarTexto(String texto) {
        if (texto == null || texto.trim().isEmpty()){
            return false;
        }
        
        String regex = "[a-zA-ZáéíóúÁÉÍÓÚñÑ, ]+";
        return texto.matches(regex);
    }

    private void guardarCambiosDesdeTabla() {
        if (jTConsultorio.isEditing()) {
            jTConsultorio.getCellEditor().stopCellEditing();
        }
        int filaSeleccionada = jTConsultorio.getSelectedRow();

        try {
            int nro = Integer.parseInt(modelo.getValueAt(filaSeleccionada, 0).toString());
            String usos = modelo.getValueAt(filaSeleccionada, 1).toString().trim();
            String[] especialidadesPermitidas = {"Facial", "Corporal", "Masajes", "Depilacion"};
            if (!Arrays.asList(especialidadesPermitidas).contains(usos)) {
                JOptionPane.showMessageDialog(this,
                        "Los usos deben ser alguno de los siguientes: Facial, Corporal, Masajes, Depilación.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            String equipamiento = modelo.getValueAt(filaSeleccionada, 2).toString();
            String aptoStr = modelo.getValueAt(filaSeleccionada, 3).toString();
            boolean apto = aptoStr.equals("Si");
            
            if (!validarTexto(equipamiento)) {
                JOptionPane.showMessageDialog(this, "El campo Equipamiento solo debe contener letras y espacios.",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Consultorio consultorioActualizado = new Consultorio(usos,equipamiento,apto);
            consultorioActualizado.setNroConsultorio(nro);

            consultorioData.actualizarConsultorio(consultorioActualizado);

            JOptionPane.showMessageDialog(this, "Consultorio actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            cargarDatos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el consultorio: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cambiarEstado() {
        int fila = jTConsultorio.getSelectedRow();
        Consultorio aux = new Consultorio();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un consultorio", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        aux.setNroConsultorio((int) modelo.getValueAt(fila, 0));
        aux.setUsos((String) modelo.getValueAt(fila, 1));
        aux.setEquipamiento((String) modelo.getValueAt(fila, 2));
        String nuevoEstado = (String) jCBapto.getSelectedItem();
        boolean estadoBoolean = nuevoEstado.equals("Si");

        try {
            if (estadoBoolean) {

                consultorioData.habilitarConsultorio(aux);
            } else {

                consultorioData.deshabilitarConsultorio(aux);
            }
            cargarDatos();

            JOptionPane.showMessageDialog(this, "consultorio apto actualizado", "Exito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cambiar consultorio apto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    public VistaConsultorio() {
        initComponents();
        armarCabecera();
        cargarDatos();
        deshabilitarBotones();
        inicializarComboTipoEspecialidad();
        inicializarEditorEspecialidadEnTabla();
    jTConsultorio.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            int filaSeleccionada = jTConsultorio.getSelectedRow();
            if (filaSeleccionada != -1) {
                habilitarBotones();
            } else {
                deshabilitarBotones();
            }
        }
    });;

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jBbuscar = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnRefrescar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jCBapto = new javax.swing.JComboBox<>();
        btnCambiarApto = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        txtBuscaPorCod = new javax.swing.JTextField();
        btnSalir = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTConsultorio = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jCBusos = new javax.swing.JComboBox<>();
        txtEquipamiento = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();

        setTitle("Consultorio");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 153, 102));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 255));
        jLabel1.setText("Gestion de Consultorios");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 255));
        jLabel2.setText("Nro de Consultorio:");

        jBbuscar.setBackground(new java.awt.Color(0, 153, 255));
        jBbuscar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jBbuscar.setForeground(new java.awt.Color(255, 255, 255));
        jBbuscar.setText("Buscar");
        jBbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBbuscarActionPerformed(evt);
            }
        });

        btnBorrar.setBackground(new java.awt.Color(0, 153, 255));
        btnBorrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBorrar.setForeground(new java.awt.Color(255, 255, 255));
        btnBorrar.setText("Borrar");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        btnActualizar.setBackground(new java.awt.Color(0, 153, 255));
        btnActualizar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnRefrescar.setBackground(new java.awt.Color(0, 153, 255));
        btnRefrescar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRefrescar.setForeground(new java.awt.Color(255, 255, 255));
        btnRefrescar.setText("Refrescar Tabla");
        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 255));
        jLabel3.setText("Apto:");

        jCBapto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        jCBapto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBaptoActionPerformed(evt);
            }
        });

        btnCambiarApto.setBackground(new java.awt.Color(0, 153, 255));
        btnCambiarApto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCambiarApto.setForeground(new java.awt.Color(255, 255, 255));
        btnCambiarApto.setText("Editar");
        btnCambiarApto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarAptoActionPerformed(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(153, 204, 255));

        jSeparator2.setForeground(new java.awt.Color(153, 204, 255));

        txtBuscaPorCod.setBackground(new java.awt.Color(153, 204, 255));

        btnSalir.setBackground(new java.awt.Color(0, 153, 255));
        btnSalir.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("X");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jTConsultorio.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTConsultorio);

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nuevo Consultorio", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 153, 255));
        jLabel7.setText("Usos:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 153, 255));
        jLabel4.setText("Agregar Consultorio");

        jCBusos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Facial", "Corporal", "Masajes", "Depilacion" }));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 153, 255));
        jLabel6.setText("Equipamiento:");

        btnGuardar.setBackground(new java.awt.Color(0, 153, 255));
        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Agregar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtEquipamiento, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jCBusos, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(188, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(212, 212, 212))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnGuardar)
                        .addGap(244, 244, 244))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCBusos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtEquipamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(btnGuardar)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 749, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtBuscaPorCod, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jBbuscar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(279, 279, 279)
                                .addComponent(btnSalir))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(62, 62, 62)
                                .addComponent(jCBapto, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56)
                                .addComponent(btnCambiarApto)
                                .addGap(244, 244, 244))))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnBorrar)
                        .addGap(137, 137, 137)
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(125, 125, 125)
                        .addComponent(btnRefrescar))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(99, 99, 99))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalir)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel1)))
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtBuscaPorCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBbuscar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizar)
                    .addComponent(btnRefrescar)
                    .addComponent(btnBorrar))
                .addGap(14, 14, 14)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCBapto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(btnCambiarApto))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 622, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        borrarConsultorio();
    }//GEN-LAST:event_btnBorrarActionPerformed


    private void jCBaptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBaptoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCBaptoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        agregarConsultorioNuevo();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        guardarCambiosDesdeTabla();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed
        // TODO add your handling code here:
        cargarDatos();
    }//GEN-LAST:event_btnRefrescarActionPerformed

    private void jBbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBbuscarActionPerformed
        // TODO add your handling code here:
        buscarConsultorioPorCod();
    }//GEN-LAST:event_jBbuscarActionPerformed

    private void btnCambiarAptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarAptoActionPerformed
        // TODO add your handling code here:
        cambiarEstado();
    }//GEN-LAST:event_btnCambiarAptoActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnCambiarApto;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton jBbuscar;
    private javax.swing.JComboBox<String> jCBapto;
    private javax.swing.JComboBox<String> jCBusos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTConsultorio;
    private javax.swing.JTextField txtBuscaPorCod;
    private javax.swing.JTextField txtEquipamiento;
    // End of variables declaration//GEN-END:variables

}
