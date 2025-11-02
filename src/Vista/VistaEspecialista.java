/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Especialista;
import Persistencia.EspecialistaData;
import java.util.Arrays;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Turco
 */
public class VistaEspecialista extends javax.swing.JInternalFrame {
    private EspecialistaData especialistaData = new EspecialistaData();
    private Especialista especialistaAct = null;
    private DefaultTableModel modelo = new DefaultTableModel() {

        public boolean isCellEditable(int fila, int column) {
            return column == 1 || column == 2 || column == 3;// || column == 4;
        }
    };
    /**
     * Creates new form VistaMasajista
     */
    public VistaEspecialista() {
        initComponents();
        armarCabecera();
        inicializarComboTipoEspecialidad();
        cargarDatos();
        inicializarEditorEspecialidadEnTabla();
        deshabilitarBotones();
        jtEspecialista.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            int filaSeleccionada = jtEspecialista.getSelectedRow();
            if (filaSeleccionada != -1) {
                habilitarBotones();
            } else {
                deshabilitarBotones();
            }
        }
    });
    }
    private void deshabilitarBotones() {
        
        btnBorrar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnEditarEstado.setEnabled(false);
        comboEstadoEspecialista.setEnabled(false);
    }
    private void habilitarBotones() {
        btnBorrar.setEnabled(true);
        btnActualizar.setEnabled(true);
        btnEditarEstado.setEnabled(true);
        comboEstadoEspecialista.setEnabled(true);
    }
    private void inicializarComboTipoEspecialidad() {
    String[] opciones = { "Seleccione...","Facial", "Corporal", "Relajación", "Estético" };
    comboTipoEspecialidad.removeAllItems();  // limpia items anteriores
    for (String opt : opciones) {
        comboTipoEspecialidad.addItem(opt);
    }
    comboTipoEspecialidad.setSelectedIndex(0);
    }
    //prueba tabla columna especialidad
    private void inicializarEditorEspecialidadEnTabla() {
        String[] opciones = { "Facial", "Corporal", "Relajación", "Estético" };
        TableColumn columnaEspecialidad = jtEspecialista.getColumnModel().getColumn(3);
        JComboBox<String> comboEditor = new JComboBox<>(opciones);
        columnaEspecialidad.setCellEditor(new DefaultCellEditor(comboEditor));
    }
    //Agregar Especialista nuevo
    private void agregarEspecialistaNuevo() {
        /*String nombreYApe = txtNombreyApellido.getText().trim();
        String especialidad = (String) comboTipoEspecialidad.getSelectedItem();
        long telefono = Long.parseLong(txtTelefono.getText().trim());
        String matricula=txtMatricula.getText().trim();

        Especialista a = new Especialista(matricula, nombreYApe,telefono, especialidad, true);
        especialistaData.guardarEspecialista(a);*/
    String nombreYApe = txtNombreyApellido.getText().trim();
    // Obtener la especialidad seleccionada del combo
    String especialidad = (String) comboTipoEspecialidad.getSelectedItem();
    long telefono;
    String matricula = txtMatricula.getText().trim();

    // Validaciones
    if (nombreYApe.isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Ingrese el nombre y apellido del especialista",
            "Advertencia",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    // Validar que el nombre y apellido contengan solo letras y espacios
    if (!nombreYApe.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
        JOptionPane.showMessageDialog(this,"El nombre y apellido deben contener solo letras y espacios.","Advertencia",JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (especialidad == null || especialidad.equals("Seleccione...")) {
        JOptionPane.showMessageDialog(this,
            "Seleccione una especialidad válida",
            "Advertencia",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    try {
        telefono = Long.parseLong(txtTelefono.getText().trim());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this,
            "Ingrese un número de teléfono válido",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    if (matricula.isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Ingrese la matrícula del especialista",
            "Advertencia",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    if (!matricula.matches("[a-zA-Z0-9]+")) {
    JOptionPane.showMessageDialog(this,
        "La matrícula debe contener solo letras y números, sin espacios ni signos.",
        "Advertencia",
        JOptionPane.WARNING_MESSAGE);
    return;
}

    // Si todo está bien, crear objeto y guardar
    Especialista a = new Especialista(matricula, nombreYApe, telefono, especialidad, true);
    especialistaData.guardarEspecialista(a);

    JOptionPane.showMessageDialog(this,"Especialista agregado correctamente","Éxito",JOptionPane.INFORMATION_MESSAGE);

    }
    
    //Limpiar Campos
    private void limpiarCampos() {
        txtNombreyApellido.setText("");
        //txtEspecialista.setText("");
        txtTelefono.setText("");
        txtMatricula.setText("");
    }
    //Armado de Cabecera
    private void armarCabecera() {
        modelo.addColumn("Matricula");
        modelo.addColumn("Nombre y Apellido");
        modelo.addColumn("Telefono");
        modelo.addColumn("Especialidad");
        modelo.addColumn("Estado");
        jtEspecialista.setModel(modelo);
    }
    //Cargar datos
    private void cargarDatos() {
        String activo;
        try {
            modelo.setRowCount(0);

            for (Especialista a : especialistaData.listarEspecialistas()) {
                if (a.isEstado()) {
                    activo = "Activo";
                } else {
                    activo = "Inactivo";
                }
                modelo.addRow(new Object[]{
                    a.getMatricula(),
                    a.getNombreYApellido(),
                    a.getTelefono(),
                    a.getEspecialidad(),
                    activo

                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar Especialistas " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Buscar Especialista
     private void buscarEspecialistaPorMatricula() {
        try {
            String matri = txtBuscarMatricula.getText().trim();
            modelo.setRowCount(0);
            if (matri.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese una Matricula para buscar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            modelo.setRowCount(0);

            especialistaAct = especialistaData.buscarEspecialista(matri);
            String activo;
            if (especialistaAct != null) {
                if (especialistaAct.isEstado()) {
                    activo = "Activo";
                } else {
                    activo = "Inactivo";
                }
                modelo.addRow(new Object[]{
                    especialistaAct.getMatricula(),
                    especialistaAct.getNombreYApellido(),
                    especialistaAct.getTelefono(),
                    especialistaAct.getEspecialidad(),
                    activo

                });
            }
           txtBuscarMatricula.setText("");
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "La matricula no esta bien escrita o esta vacia: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Borrar Especialista
      private void borrarEspecialista() {
        int fila = jtEspecialista.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un Especialista a eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        if (fila != -1) {
            int conf = JOptionPane.showConfirmDialog(
                    this,
                    "¿Seguro que desea eliminar el Especialista seleccionado?", "Advertencia", JOptionPane.YES_NO_OPTION);

            if (conf == JOptionPane.YES_OPTION) {
                try {
                    String matricula = (String) jtEspecialista.getValueAt(fila, 0);

                    especialistaData.BorrarEspecialista(matricula);

                    modelo.removeRow(fila);

                    JOptionPane.showMessageDialog(this, "Especialista eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this,
                            "Error al eliminar el especialista: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    //Guardar cambios de especialista al apretar boton Actualizar 
      private boolean validarNombreApellido(String nombre) {
    if (nombre == null || nombre.trim().isEmpty()) {
        return false;
    }
    // Expresión regular que acepta letras mayúsculas/minúsculas, vocales con tilde, ñ/Ñ, y espacios.
    String regex = "[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+";
    return nombre.matches(regex);
}
      private void guardarCambiosDesdeTabla() {
          if (jtEspecialista.isEditing()) {
        jtEspecialista.getCellEditor().stopCellEditing();
        }
          int filaSeleccionada = jtEspecialista.getSelectedRow();

        try {
            // obtener datos de la fila seleccionada
            String matri=modelo.getValueAt(filaSeleccionada, 0).toString().trim();
           // Validar matrícula
                if (!matri.matches("[a-zA-Z0-9]+")) {
                JOptionPane.showMessageDialog(this,
                "La matrícula debe contener solo letras y números, sin espacios ni signos.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
                return;
                }
            String nomYApellido = modelo.getValueAt(filaSeleccionada, 1).toString().trim();
            // Validar nombre y apellido
            System.out.println("Valor nombre y apellido: '"+nomYApellido+"'");
          
               // Validar nombre y apellido usando el método
if (!validarNombreApellido(nomYApellido)) {
    JOptionPane.showMessageDialog(this,
        "El nombre y apellido deben contener solo letras y espacios.",
        "Advertencia",
        JOptionPane.WARNING_MESSAGE);
    return;
}
            /* if (!nomYApellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                JOptionPane.showMessageDialog(this,"El nombre y apellido deben contener solo letras y espacios.",
                "Advertencia",JOptionPane.WARNING_MESSAGE);
                return;
                }*/
            //long tel = Long.parseLong(modelo.getValueAt(filaSeleccionada, 2).toString().trim());
            String espe = modelo.getValueAt(filaSeleccionada, 3).toString().trim();
            // Validar especialidad
            String[] especialidadesPermitidas = {"Facial", "Corporal", "Relajación", "Estético"};
                if (!Arrays.asList(especialidadesPermitidas).contains(espe)) {
                JOptionPane.showMessageDialog(this,
                "La especialidad debe ser una de las siguientes: Facial, Corporal, Relajación, Estético.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
                return;
                }
            String estadoStr = modelo.getValueAt(filaSeleccionada, 4).toString();
            boolean estado = estadoStr.equals("Activo");
            // Validar teléfono
            long telefono;
            try {
                 telefono = Long.parseLong(modelo.getValueAt(filaSeleccionada, 2).toString().trim());
                 } catch (NumberFormatException e) {
                   JOptionPane.showMessageDialog(this,
                   "Ingrese un número de teléfono válido.",
                   "Error",
                   JOptionPane.ERROR_MESSAGE);
                   return;
                   }
            Especialista especialistaActualizado = new Especialista(matri, nomYApellido, telefono, espe, estado);
            especialistaActualizado.setMatricula(matri);

            especialistaData.actualizarEspecialista(especialistaActualizado);

            JOptionPane.showMessageDialog(this, "Especialista actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            cargarDatos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar Especialista: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
      //Cambio de Estado del Especialista
    private void cambiarEstadoEspecialista() {
        int fila = jtEspecialista.getSelectedRow();
        Especialista aux = new Especialista();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un Especialista", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        aux.setMatricula((String)(modelo.getValueAt(fila, 0)));
        aux.setNombreYApellido((String) modelo.getValueAt(fila, 1));
        aux.setTelefono((Long) modelo.getValueAt(fila, 2));
        aux.setEspecialidad((String) modelo.getValueAt(fila, 3));
        String nuevoEstado = (String) comboEstadoEspecialista.getSelectedItem();
        boolean estadoBoolean = nuevoEstado.equals("Activo");

        try {
            if (estadoBoolean) {
                
                especialistaData.HabilitarEspecialista(aux);
            } else {
                
                especialistaData.DeshabilitarEspecialista(aux);
            }
            cargarDatos();

            JOptionPane.showMessageDialog(this, "Estado del especialista cambiado a: " + nuevoEstado, "Exito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cambiar estado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

        jLabel1 = new javax.swing.JLabel();
        txtBuscarMatricula = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtEspecialista = new javax.swing.JTable();
        btnBorrar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnRegrescar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        comboEstadoEspecialista = new javax.swing.JComboBox<>();
        btnEditarEstado = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNombreyApellido = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtMatricula = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        comboTipoEspecialidad = new javax.swing.JComboBox<>();

        jLabel1.setText("Matricula del Especialista");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jtEspecialista.setModel(new javax.swing.table.DefaultTableModel(
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
        jtEspecialista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jtEspecialistaMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(jtEspecialista);

        btnBorrar.setText("Borrar");
        btnBorrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBorrarMouseEntered(evt);
            }
        });
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        btnActualizar.setText("Actualizar");
        btnActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnActualizarMouseEntered(evt);
            }
        });
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnRegrescar.setText("Refrescar Tabla");
        btnRegrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegrescarActionPerformed(evt);
            }
        });

        jLabel2.setText("Estado:");

        comboEstadoEspecialista.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));

        btnEditarEstado.setText("Editar Estado");
        btnEditarEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarEstadoActionPerformed(evt);
            }
        });

        jLabel3.setText("Agregar Especialista:");

        jLabel4.setText("Nombre y Apellido:");

        txtNombreyApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreyApellidoActionPerformed(evt);
            }
        });

        jLabel5.setText("Especialidad:");

        jLabel6.setText("Telefono:");

        jLabel7.setText("Matricula:");

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(174, 174, 174)
                        .addComponent(jLabel2)
                        .addGap(40, 40, 40)
                        .addComponent(comboEstadoEspecialista, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(btnEditarEstado))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(11, 11, 11)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel6)
                                        .addComponent(txtNombreyApellido, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                                        .addComponent(txtTelefono))))
                            .addGap(129, 129, 129)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel7)
                                        .addComponent(txtMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                                    .addComponent(btnAgregar))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(comboTipoEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE))))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(23, 23, 23)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane1)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtBuscarMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(28, 28, 28)
                                    .addComponent(btnBuscar))))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(61, 61, 61)
                            .addComponent(btnBorrar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnActualizar)
                            .addGap(110, 110, 110)
                            .addComponent(btnRegrescar)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(222, 222, 222)
                        .addComponent(btnSalir)))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBuscarMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBorrar)
                    .addComponent(btnActualizar)
                    .addComponent(btnRegrescar))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(comboEstadoEspecialista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarEstado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboTipoEspecialidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreyApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAgregar)
                        .addGap(42, 42, 42)
                        .addComponent(btnSalir))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditarEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarEstadoActionPerformed
        // TODO add your handling code here:
        cambiarEstadoEspecialista();
    }//GEN-LAST:event_btnEditarEstadoActionPerformed

    private void txtNombreyApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreyApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreyApellidoActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        agregarEspecialistaNuevo();
        limpiarCampos();
        
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        buscarEspecialistaPorMatricula();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        // TODO add your handling code here:
        borrarEspecialista();
        
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        guardarCambiosDesdeTabla();
        
        
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnRegrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegrescarActionPerformed
        // TODO add your handling code here:
        cargarDatos();
    }//GEN-LAST:event_btnRegrescarActionPerformed

    private void btnActualizarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMouseEntered
        // TODO add your handling code here:
        //btnActualizarInstalacion.setToolTipText("modifica el campo con doble click y luego presiona aqui");
        btnActualizar.setToolTipText("Modifica el campo con doble click y luego presiona aqui");
    }//GEN-LAST:event_btnActualizarMouseEntered

    private void jtEspecialistaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtEspecialistaMouseEntered
        // TODO add your handling code here:
        jtEspecialista.setToolTipText("Doble click en el campo que quiere modificar");
    }//GEN-LAST:event_jtEspecialistaMouseEntered

    private void btnBorrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBorrarMouseEntered
        // TODO add your handling code here:
         btnBorrar.setToolTipText("seleccione de la lista la instalación a borrar");
    }//GEN-LAST:event_btnBorrarMouseEntered


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditarEstado;
    private javax.swing.JButton btnRegrescar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> comboEstadoEspecialista;
    private javax.swing.JComboBox<String> comboTipoEspecialidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtEspecialista;
    private javax.swing.JTextField txtBuscarMatricula;
    private javax.swing.JTextField txtMatricula;
    private javax.swing.JTextField txtNombreyApellido;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
