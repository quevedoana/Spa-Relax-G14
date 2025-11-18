/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Instalacion;
import Persistencia.InstalacionData;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Anitabonita
 */
public class VistaInstalacion extends javax.swing.JInternalFrame {

    private InstalacionData instalacionData = new InstalacionData();
    private Instalacion instalacion = null;
    private DefaultTableModel modelo = new DefaultTableModel() {

        public boolean isCellEditable(int fila, int column) {
            return column == 1 || column == 2 || column == 3;
        }
    };
    //Armado de cabecera
    private void armarCabecera() {
        modelo.addColumn("codInstalacion");
        modelo.addColumn("Nombre");
        modelo.addColumn("Detalles de Uso");
        modelo.addColumn("precio de 30 minutos");
        modelo.addColumn("Estado");
        jTInstalacion.setModel(modelo);
    }
    //Carga de datos a la tabla
    private void cargarDatos() {
        String activo;
        try {
            modelo.setRowCount(0);

            for (Instalacion in : instalacionData.ListarInstalacion()) {
                if (in.isEstado()) {
                    activo = "Activa";
                } else {
                    activo = "Inactiva";
                }
                modelo.addRow(new Object[]{
                    in.getCodInstal(),
                    in.getNombre(),
                    in.getDetalleDeUso(),
                    in.getPrecio30m(),
                    activo

                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la instalación " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Busqueda de instalacion por codigo
    private void buscarInstalacionPorCod() {
        try {
            String cod = txtBuscaPorCod.getText().trim();
            modelo.setRowCount(0);
            if (cod.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un codigo para buscar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            modelo.setRowCount(0);

            instalacion = instalacionData.buscarInstalacion(Integer.parseInt(cod));
            String activo;
            if (instalacion != null) {
                if (instalacion.isEstado()) {
                    activo = "Activa";
                } else {
                    activo = "Inactiva";
                }
                modelo.addRow(new Object[]{
                    instalacion.getCodInstal(),
                    instalacion.getNombre(),
                    instalacion.getDetalleDeUso(),
                    instalacion.getPrecio30m(),
                    activo

                });
            }
            txtBuscaPorCod.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El codigo es un numero: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Funcion que agrega Una instalacion nueva
    private void agregarInstalacionNueva() {
        try {

            String nombre = txtNombre.getText().trim();
            String detalles = txtDetalles.getText().trim();
            String prec = txtPrecio.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El campo 'Nombre' no puede estar vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                txtNombre.requestFocus();
                return;
            }

            if (detalles.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un detalle de uso.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                txtDetalles.requestFocus();
                return;
            }

            if (prec.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un precio.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                txtPrecio.requestFocus();
                return;
            }

            double precio = Double.parseDouble(prec);

            if (precio <= 0) {
                JOptionPane.showMessageDialog(this, "El precio debe ser mayor a 0.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                txtPrecio.requestFocus();
                return;
            }

            Instalacion in = new Instalacion(nombre, detalles, precio, true);
            instalacionData.guardarInstalacion(in);
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Instalación agregada correctamente.", "mensaje", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar la instalación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    //Metodo que borra una Instalacion
    private void borrarInstalacion() {
        
        int fila = jTInstalacion.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "seleccione una instalación a eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

        if (fila != -1) {
            
            
            int conf = JOptionPane.showConfirmDialog(
                    this,
                    "¿Seguro que desea eliminar la instalación seleccionada?", "Advertencia", JOptionPane.YES_NO_OPTION);

            if (conf == JOptionPane.YES_OPTION) {
                try {
                    int cod = (int) jTInstalacion.getValueAt(fila, 0);

                    instalacionData.BorrarInstalacion(cod);

                    modelo.removeRow(fila);

                    JOptionPane.showMessageDialog(this, "instalación eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this,
                            "Error al eliminar la instalación: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    //Funcion para guardar los cambios que haya hecho en la tabla
    private void guardarCambiosDesdeTabla() {
        int filaSeleccionada = jTInstalacion.getSelectedRow();

        try {
            // obtener datos de la fila seleccionada
            int cod = Integer.parseInt(modelo.getValueAt(filaSeleccionada, 0).toString());
            String nombre = modelo.getValueAt(filaSeleccionada, 1).toString().trim();
            String detalle = modelo.getValueAt(filaSeleccionada, 2).toString().trim();
            double precio = Double.parseDouble(modelo.getValueAt(filaSeleccionada, 3).toString());
            String estadoStr = modelo.getValueAt(filaSeleccionada, 4).toString();
            boolean estado = estadoStr.equals("Activa");

            Instalacion instalacionActualizada = new Instalacion(nombre, detalle, precio, estado);
            instalacionActualizada.setCodInstal(cod);
            instalacionData.actualizarInstalacion(instalacionActualizada);

            JOptionPane.showMessageDialog(this, "Instalacion actualizada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            cargarDatos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar instalación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Funcion para guardar el cambio de estado
    private void cambiarEstado() {
        int fila = jTInstalacion.getSelectedRow();
        Instalacion aux = new Instalacion();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una instalación", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        aux.setCodInstal((int) modelo.getValueAt(fila, 0));
        aux.setNombre((String) modelo.getValueAt(fila, 1));
        aux.setDetalleDeUso((String) modelo.getValueAt(fila, 2));
        aux.setPrecio30m((double) modelo.getValueAt(fila, 3));
        String nuevoEstado = (String) comboEstadoInstalacion.getSelectedItem();
        boolean estadoBoolean = nuevoEstado.equals("Activa");

        try {
            if (estadoBoolean) {

                instalacionData.HabilitarInstalacion(aux);
            } else {

                instalacionData.DeshabilitarInstalacion(aux);
            }
            cargarDatos();

            JOptionPane.showMessageDialog(this, "Estado de la instalación cambiado a: " + nuevoEstado, "Exito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cambiar estado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Funcion para limpiar los txt
    private void limpiarCampos() {
        txtNombre.setText("");
        txtDetalles.setText("");
        txtPrecio.setText("");
    }
    //Funcion para deshabilitar botones
    private void deshabilitarBotones() {
        
        btnBorrarInstalacion.setEnabled(false);
        btnActualizarInstalacion.setEnabled(false);
        btnAltaBajaLogica.setEnabled(false);
        comboEstadoInstalacion.setEnabled(false);
    }
    //Funcion para habilitar Botones
    private void habilitarBotones() {
        btnBorrarInstalacion.setEnabled(true);
        btnActualizarInstalacion.setEnabled(true);
        btnAltaBajaLogica.setEnabled(true);
        comboEstadoInstalacion.setEnabled(true);
    }

    /**
     * Creates new form VistaInstalacion
     */
    public VistaInstalacion() {
        initComponents();
        armarCabecera();
        cargarDatos();
        deshabilitarBotones(); 
        //Habilita botones si la tabla es clickeada
    jTInstalacion.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            int filaSeleccionada = jTInstalacion.getSelectedRow();
            if (filaSeleccionada != -1) {
                habilitarBotones();
            } else {
                deshabilitarBotones();
            }
        }
    });
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTInstalacion = new javax.swing.JTable();
        btnBorrarInstalacion = new javax.swing.JButton();
        btnRefrescar = new javax.swing.JButton();
        btnActualizarInstalacion = new javax.swing.JButton();
        btnAltaBajaLogica = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDetalles = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        BtnAgregarAlumno = new javax.swing.JButton();
        jBSalir = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        comboEstadoInstalacion = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtBuscaPorCod = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 102, 51));
        jLabel1.setText("<html><u>Gestión de instalaciones</u></html>");

        jTInstalacion.setModel(new javax.swing.table.DefaultTableModel(
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
        jTInstalacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTInstalacionMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(jTInstalacion);

        btnBorrarInstalacion.setBackground(new java.awt.Color(255, 153, 102));
        btnBorrarInstalacion.setForeground(new java.awt.Color(0, 0, 0));
        btnBorrarInstalacion.setText("Borrar");
        btnBorrarInstalacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBorrarInstalacionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBorrarInstalacionMouseEntered(evt);
            }
        });

        btnRefrescar.setBackground(new java.awt.Color(255, 153, 102));
        btnRefrescar.setForeground(new java.awt.Color(0, 0, 0));
        btnRefrescar.setText("Refrescar Tabla");
        btnRefrescar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRefrescarMouseClicked(evt);
            }
        });

        btnActualizarInstalacion.setBackground(new java.awt.Color(255, 153, 102));
        btnActualizarInstalacion.setForeground(new java.awt.Color(0, 0, 0));
        btnActualizarInstalacion.setText("Actualizar");
        btnActualizarInstalacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnActualizarInstalacionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnActualizarInstalacionMouseEntered(evt);
            }
        });

        btnAltaBajaLogica.setBackground(new java.awt.Color(255, 153, 102));
        btnAltaBajaLogica.setForeground(new java.awt.Color(0, 0, 0));
        btnAltaBajaLogica.setText("Editar Estado");
        btnAltaBajaLogica.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAltaBajaLogicaMouseClicked(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Agregar Instalacion:"));

        jLabel4.setText("Nombre: ");

        jLabel5.setText("Precio de 30 minutos:");

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        txtPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioKeyTyped(evt);
            }
        });

        jLabel6.setText("Detalle de uso: ");

        BtnAgregarAlumno.setBackground(new java.awt.Color(255, 153, 102));
        BtnAgregarAlumno.setForeground(new java.awt.Color(0, 0, 0));
        BtnAgregarAlumno.setText("Agregar");
        BtnAgregarAlumno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnAgregarAlumnoMouseClicked(evt);
            }
        });
        BtnAgregarAlumno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAgregarAlumnoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 497, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(174, 174, 174)
                        .addComponent(jLabel7)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtDetalles, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(93, 93, 93)
                                .addComponent(BtnAgregarAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(16, 16, 16))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BtnAgregarAlumno)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDetalles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jBSalir.setBackground(new java.awt.Color(255, 153, 102));
        jBSalir.setForeground(new java.awt.Color(0, 0, 0));
        jBSalir.setText("Salir");
        jBSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalirActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 102, 51));
        jLabel2.setText("Estado:");

        comboEstadoInstalacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activa", "Inactiva" }));

        jLabel3.setText("Codigo de la instalación: ");

        txtBuscaPorCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscaPorCodActionPerformed(evt);
            }
        });

        btnBuscar.setBackground(new java.awt.Color(255, 153, 102));
        btnBuscar.setForeground(new java.awt.Color(0, 0, 0));
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(jLabel3)
                        .addGap(54, 54, 54)
                        .addComponent(txtBuscaPorCod, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(219, 219, 219)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(121, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(btnBorrarInstalacion, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnActualizarInstalacion, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comboEstadoInstalacion, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(107, 107, 107)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRefrescar)
                    .addComponent(btnAltaBajaLogica))
                .addGap(91, 91, 91))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jBSalir)
                .addGap(336, 336, 336))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscaPorCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(btnBuscar))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBorrarInstalacion)
                    .addComponent(btnActualizarInstalacion)
                    .addComponent(btnRefrescar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboEstadoInstalacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAltaBajaLogica)
                    .addComponent(jLabel2))
                .addGap(37, 37, 37)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBSalir)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBorrarInstalacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBorrarInstalacionMouseClicked
        // TODO add your handling code here:
        borrarInstalacion();
    }//GEN-LAST:event_btnBorrarInstalacionMouseClicked

    private void btnRefrescarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRefrescarMouseClicked
        // TODO add your handling code here:
        cargarDatos();
    }//GEN-LAST:event_btnRefrescarMouseClicked

    private void btnActualizarInstalacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarInstalacionMouseClicked
        // TODO add your handling code here:

        guardarCambiosDesdeTabla();
    }//GEN-LAST:event_btnActualizarInstalacionMouseClicked

    private void btnActualizarInstalacionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarInstalacionMouseEntered
        // TODO add your handling code here:
        btnActualizarInstalacion.setToolTipText("modifica el campo con doble click y luego presiona aqui");

    }//GEN-LAST:event_btnActualizarInstalacionMouseEntered

    private void btnAltaBajaLogicaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAltaBajaLogicaMouseClicked
        // TODO add your handling code here:
        cambiarEstado();
    }//GEN-LAST:event_btnAltaBajaLogicaMouseClicked

    private void BtnAgregarAlumnoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnAgregarAlumnoMouseClicked
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_BtnAgregarAlumnoMouseClicked

    private void BtnAgregarAlumnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAgregarAlumnoActionPerformed
        // TODO add your handling code here:
        agregarInstalacionNueva();
    }//GEN-LAST:event_BtnAgregarAlumnoActionPerformed

    private void jBSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jBSalirActionPerformed

    private void txtBuscaPorCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscaPorCodActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtBuscaPorCodActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        buscarInstalacionPorCod();


    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (Character.isISOControl(c)) {
            return;
        }
        if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
            evt.consume();
            JOptionPane.showMessageDialog(this, "El nombre solo permite letras", "Advertencia", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (Character.isISOControl(c)) {
            return;
        }

        if (!Character.isDigit(c) && c != '.') {
            evt.consume();
            JOptionPane.showMessageDialog(this, "El precio solo permite numeros", "Advertencia", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_txtPrecioKeyTyped

    private void jTInstalacionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTInstalacionMouseEntered
        // TODO add your handling code here:
        jTInstalacion.setToolTipText("doble click en el campo que quiere modificar");
    }//GEN-LAST:event_jTInstalacionMouseEntered

    private void btnBorrarInstalacionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBorrarInstalacionMouseEntered
        // TODO add your handling code here:
        btnBorrarInstalacion.setToolTipText("seleccione de la lista la instalación a borrar");
    }//GEN-LAST:event_btnBorrarInstalacionMouseEntered


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAgregarAlumno;
    private javax.swing.JButton btnActualizarInstalacion;
    private javax.swing.JButton btnAltaBajaLogica;
    private javax.swing.JButton btnBorrarInstalacion;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JComboBox<String> comboEstadoInstalacion;
    private javax.swing.JButton jBSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTInstalacion;
    private javax.swing.JTextField txtBuscaPorCod;
    private javax.swing.JTextField txtDetalles;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
