/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Cliente;
import Modelo.DiaDeSpa;
import Modelo.Tratamiento;
import Persistencia.ClienteData;
import Persistencia.DiaDeSpaData;
import Persistencia.TratamientoData;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author esteb
 */
public class VistaTurno extends javax.swing.JInternalFrame {

    private TratamientoData tratamientod = new TratamientoData();
    private DefaultTableModel modeloTabla = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int fila, int column) {
            return false;
        }
    };
    private DiaDeSpa diaDeSpaActual;
    private Tratamiento tratamientoPendiente;
     

    /**
     * Creates new form VistaSesion
     */
    public VistaTurno() {
        initComponents();
        armarCabecera();
        cargarTratamientosPorTipo(null);

        ButtonGroup grupoTipos = new ButtonGroup();
        grupoTipos.add(radioFacial);
        grupoTipos.add(radioCorporal);
        grupoTipos.add(radioEstetico);
        grupoTipos.add(radioRelajacion);

        radioFacial.addActionListener(this::radioFacialActionPerformed);
        radioEstetico.addActionListener(this::radioEsteticoActionPerformed);
        radioCorporal.addActionListener(this::radioCorporalActionPerformed);
        radioRelajacion.addActionListener(this::radioRelajacionActionPerformed);

        btnReservar.setToolTipText("Seleccione un tratamiento y cree un Día de Spa para reservar");
        btnReservaInstalacion.setToolTipText("Cree un Día de Spa para reservar una instalación");
        btnConsultar.setToolTipText("Informacion sobre el tratamiento seleccionado");
        btnContacto.setToolTipText("Contacto");

        mostrarMensajeInformativo();

    }
    //Funcion que bbusca un dia de spa que exista para poder sacar el turno
    private void buscarDiaDeSpaExistente() {
    String dniStr = JOptionPane.showInputDialog(this,
            "¿Ya tiene un Día de Spa asignado?\n\n"
            + "Ingrese su DNI para buscar días de spa activos:\n"
            + "(Deje vacío para crear uno nuevo)",
            "Buscar Día de Spa Existente",
            JOptionPane.QUESTION_MESSAGE);

    if (dniStr == null) {
        return;
    }
    if (dniStr.trim().isEmpty()) {
        abrirAgregarCliente();
        return;
    }

    try {
        String dniLimpio = dniStr.trim();
        if (dniLimpio.length() > 8) {
            JOptionPane.showMessageDialog(this,
                    "El DNI no puede tener más de 8 dígitos",
                    "DNI Inválido",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Long dni = Long.parseLong(dniLimpio);
        buscarDiasDeSpaPorDNI(dni);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this,
                "DNI inválido. Por favor ingrese solo números.",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
//Funcion para buscar dia de spa por el dni
    private void buscarDiasDeSpaPorDNI(Long dni) {
        ClienteData clienteData = new ClienteData();
        DiaDeSpaData diaSpaData = new DiaDeSpaData();

        Cliente cliente = clienteData.buscarClientePorDni(dni);

        if (cliente == null) {
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "No se encontró un cliente con DNI: " + dni + "\n\n"
                    + "¿Desea crear un nuevo cliente y día de spa?",
                    "Cliente No Encontrado",
                    JOptionPane.YES_NO_OPTION);

            if (respuesta == JOptionPane.YES_OPTION) {
                abrirAgregarCliente();
                
            }
            return;
        }

        List<DiaDeSpa> diasSpa = diaSpaData.buscarDiasDeSpaPorCliente(cliente.getCodCli());
        List<DiaDeSpa> diasValidos = new ArrayList<>();

        for (DiaDeSpa dia : diasSpa) {
            if (dia.getFechaYHora().toLocalDate().isEqual(LocalDate.now())
                    || dia.getFechaYHora().toLocalDate().isAfter(LocalDate.now())) {
                diasValidos.add(dia);
            }
        }

        if (diasValidos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No tiene días de spa activos o futuros.\n"
                    + "Crearemos uno nuevo para usted.",
                    "Sin Días de Spa",
                    JOptionPane.INFORMATION_MESSAGE);
            abrirAgregarDiaDeSpa("nuevo",dni);
            return;
        }

        mostrarSeleccionDiaDeSpa(diasValidos, cliente);
    }
    //Funcion para mostrar la seleccion de dia de spa usado arriba
    private void mostrarSeleccionDiaDeSpa(List<DiaDeSpa> diasSpa, Cliente cliente) {
        String[] opciones = new String[diasSpa.size() + 1];
        
        for (int i = 0; i < diasSpa.size(); i++) {
            DiaDeSpa dia = diasSpa.get(i);
            String fechaStr = dia.getFechaYHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            opciones[i] = "Día #" + dia.getCodPack() + " - " + fechaStr + " - "
                    + dia.getSesiones().size() + " servicios";
        }
        opciones[diasSpa.size()] = "Crear nuevo día de spa";

        String seleccion = (String) JOptionPane.showInputDialog(this,
                "Seleccione un día de spa existente o cree uno nuevo:\n"
                + "Cliente: " + cliente.getNombreCompleto() + " (DNI: " + cliente.getDni() + ")",
                "Seleccionar Día de Spa",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]);

        if (seleccion == null) {
            return;
        }

        if (seleccion.equals("Crear nuevo día de spa")) {
           
            abrirAgregarDiaDeSpa("nuevo",cliente.getDni());
            
        } else {

            int codPack = Integer.parseInt(seleccion.split("#")[1].split(" ")[0]);
            DiaDeSpa diaSeleccionado = null;

            for (DiaDeSpa dia : diasSpa) {
                if (dia.getCodPack() == codPack) {
                    diaSeleccionado = dia;
                    break;
                }
            }

            if (diaSeleccionado != null) {
                this.diaDeSpaActual = diaSeleccionado;
                continuarConReserva();
            }
        }
    }
    //Funcion para mostrar mensajes informativo
    private void mostrarMensajeInformativo() {
        JOptionPane.showMessageDialog(this,
                "¡Bienvenido al Sistema de Reservas!\n\n"
                + "Para realizar cualquier reserva, primero debe crear un Dia de Spa.\n\n"
                + "Flujo de reserva:\n"
                + "1. Seleccione un tratamiento\n"
                + "2. Haga click en Reservar Tratamiento o Reservar Instalación\n"
                + "3. Cree su Dia de Spa\n"
                + "4. Complete su reserva\n\n"
                + "¡Empecemos!",
                "Bienvenido - Sistema de Reservas",
                JOptionPane.INFORMATION_MESSAGE);
    }
    //Armado de cabecera
    private void armarCabecera() {
        modeloTabla.addColumn("Código");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Detalle");
        modeloTabla.addColumn("Duración");
        modeloTabla.addColumn("Precio");
        tablaTratamientos.setModel(modeloTabla);

        tablaTratamientos.removeColumn(tablaTratamientos.getColumnModel().getColumn(0));

    }
    //Funcion para cargar los tratamiento por tipo
    private void cargarTratamientosPorTipo(String tipo) {
        modeloTabla.setRowCount(0);

        List<Tratamiento> tratamientos;
        if (tipo == null || tipo.isEmpty()) {
            tratamientos = tratamientod.listarTodosTratamientos();
        } else {
            tratamientos = tratamientod.listarTratamientosPorTipo(tipo);
        }

        for (Tratamiento t : tratamientos) {
            Object[] fila = {
                t.getCodTratam(),
                t.getNombre(),
                t.getDetalle(),
                t.getDuracion() + " min",
                "$" + String.format("%.2f", t.getCosto())
            };
            modeloTabla.addRow(fila);
        }
    }
    //Funcion que agrega el abrir dia de spa
    private void abrirAgregarDiaDeSpa(String tipoReserva, long dni) {
        AgregarDiaDeSpa agregarDiaSpa = new AgregarDiaDeSpa(this,dni);
       
        agregarDiaSpa.setVisible(true);

        javax.swing.JDesktopPane desktop = (javax.swing.JDesktopPane) this.getParent();
        desktop.add(agregarDiaSpa);

        java.awt.Dimension desktopSize = desktop.getSize();
        java.awt.Dimension jifSize = agregarDiaSpa.getSize();
        agregarDiaSpa.setLocation((desktopSize.width - jifSize.width) / 2,
                (desktopSize.height - jifSize.height) / 2);
        agregarDiaSpa.toFront();
        
      
    }
    //Funcion para abrir el agregar dia de spa
    private void abrirAgregarDiaDeSpa(String tipoReserva) {
        AgregarDiaDeSpa agregarDiaSpa = new AgregarDiaDeSpa(this,null);
       
        agregarDiaSpa.setVisible(true);

        javax.swing.JDesktopPane desktop = (javax.swing.JDesktopPane) this.getParent();
        desktop.add(agregarDiaSpa);

        java.awt.Dimension desktopSize = desktop.getSize();
        java.awt.Dimension jifSize = agregarDiaSpa.getSize();
        agregarDiaSpa.setLocation((desktopSize.width - jifSize.width) / 2,
                (desktopSize.height - jifSize.height) / 2);
        agregarDiaSpa.toFront();
        
      
    }
    //Funcion para abrir el agregar Cliente
    private void abrirAgregarCliente() {
       AgregarCliente agregarCliente = new AgregarCliente();
        agregarCliente.setVisible(true);
        javax.swing.JDesktopPane desktop = (javax.swing.JDesktopPane) this.getParent();
            desktop.add(agregarCliente);

            java.awt.Dimension desktopSize = desktop.getSize();
            java.awt.Dimension jifSize = agregarCliente.getSize();
            agregarCliente.setLocation((desktopSize.width - jifSize.width) / 2,
                    (desktopSize.height - jifSize.height) / 2);
                    agregarCliente.toFront();
    }
    //Funcion publica de dia de spa creado con carteles
    public void diaDeSpaCreado(DiaDeSpa diaDeSpa) {
        this.diaDeSpaActual = diaDeSpa;

        JOptionPane.showMessageDialog(this,
                "¡Dia de Spa creado exitosamente!\n\n"
                + "Procediendo con su reserva...",
                "Dia de Spa Creado",
                JOptionPane.INFORMATION_MESSAGE);

        continuarConReserva();
    }
 //Funcion para continuar con la reserva
    private void continuarConReserva() {
        if (tratamientoPendiente != null) {
            abrirReservaConTratamiento(tratamientoPendiente);
        } else {
            abrirReservaSoloInstalacion();
        }
    }
    //Abre reserva con el tratamiento
    private void abrirReservaConTratamiento(Tratamiento tratamiento) {
        ReservarSesion reserva = new ReservarSesion(tratamiento, diaDeSpaActual);
        reserva.setVisible(true);

        javax.swing.JDesktopPane desktop = (javax.swing.JDesktopPane) this.getParent();
        desktop.add(reserva);

        java.awt.Dimension desktopSize = desktop.getSize();
        java.awt.Dimension jifSize = reserva.getSize();
        reserva.setLocation((desktopSize.width - jifSize.width) / 2,
                (desktopSize.height - jifSize.height) / 2);
        reserva.toFront();

        tratamientoPendiente = null;
    }
 //Funcion que habre reserva solo de instalacion
    private void abrirReservaSoloInstalacion() {
        ReservarSesion reserva = new ReservarSesion(diaDeSpaActual);
        reserva.setVisible(true);

        javax.swing.JDesktopPane desktop = (javax.swing.JDesktopPane) this.getParent();
        desktop.add(reserva);

        java.awt.Dimension desktopSize = desktop.getSize();
        java.awt.Dimension jifSize = reserva.getSize();
        reserva.setLocation((desktopSize.width - jifSize.width) / 2,
                (desktopSize.height - jifSize.height) / 2);
        reserva.toFront();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        dateUtil1 = new com.toedter.calendar.DateUtil();
        jCalendarBeanInfo1 = new com.toedter.calendar.JCalendarBeanInfo();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaTratamientos = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        radioFacial = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        radioCorporal = new javax.swing.JRadioButton();
        radioRelajacion = new javax.swing.JRadioButton();
        radioEstetico = new javax.swing.JRadioButton();
        btnReservar = new javax.swing.JButton();
        btnContacto = new javax.swing.JButton();
        btnConsultar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        btnReservaInstalacion = new javax.swing.JButton();

        jScrollPane1.setViewportView(jTree1);

        setTitle("Gestion de Turnos");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        tablaTratamientos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaTratamientos.setToolTipText("");
        jScrollPane2.setViewportView(tablaTratamientos);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 255));
        jLabel3.setText("<html>Servicios Disponibles</html>");

        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("(Seleccione el tratamiento de su interes para consultar o hacer una reserva)");

        radioFacial.setText("Facial");
        radioFacial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioFacialActionPerformed(evt);
            }
        });

        jLabel2.setText("Tipo de Tratamiento:");

        radioCorporal.setText("Corporal");
        radioCorporal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioCorporalActionPerformed(evt);
            }
        });

        radioRelajacion.setText("Relajacion");
        radioRelajacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioRelajacionActionPerformed(evt);
            }
        });

        radioEstetico.setText("Estetico");
        radioEstetico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioEsteticoActionPerformed(evt);
            }
        });

        btnReservar.setBackground(new java.awt.Color(0, 153, 255));
        btnReservar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnReservar.setForeground(new java.awt.Color(255, 255, 255));
        btnReservar.setText("Reservar Tratamiento");
        btnReservar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReservarActionPerformed(evt);
            }
        });

        btnContacto.setBackground(new java.awt.Color(0, 153, 255));
        btnContacto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnContacto.setForeground(new java.awt.Color(255, 255, 255));
        btnContacto.setText("Contacto");
        btnContacto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContactoActionPerformed(evt);
            }
        });

        btnConsultar.setBackground(new java.awt.Color(0, 153, 255));
        btnConsultar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnConsultar.setForeground(new java.awt.Color(255, 255, 255));
        btnConsultar.setText("Consultar");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Para Consultas o mas Informacion, contactanos!");

        btnSalir.setBackground(new java.awt.Color(0, 153, 255));
        btnSalir.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("X");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnReservaInstalacion.setBackground(new java.awt.Color(0, 153, 255));
        btnReservaInstalacion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnReservaInstalacion.setForeground(new java.awt.Color(255, 255, 255));
        btnReservaInstalacion.setText("Reservar Instalacion");
        btnReservaInstalacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReservaInstalacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(182, 182, 182)
                .addComponent(btnSalir)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(radioFacial)
                                        .addGap(36, 36, 36)
                                        .addComponent(radioCorporal))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnReservar, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(16, 16, 16)))
                                .addGap(27, 27, 27)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnReservaInstalacion, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(radioRelajacion)
                                        .addGap(18, 18, 18)
                                        .addComponent(radioEstetico))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(btnContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(btnConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 27, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSalir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioFacial)
                    .addComponent(jLabel2)
                    .addComponent(radioCorporal)
                    .addComponent(radioRelajacion)
                    .addComponent(radioEstetico))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReservar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReservaInstalacion, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(btnConsultar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(btnContacto))
                .addGap(14, 14, 14))
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

    private void radioFacialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioFacialActionPerformed
        // TODO add your handling code here:
        cargarTratamientosPorTipo("Facial");
    }//GEN-LAST:event_radioFacialActionPerformed

    private void radioRelajacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioRelajacionActionPerformed
        // TODO add your handling code here:
        cargarTratamientosPorTipo("Relajacion");
    }//GEN-LAST:event_radioRelajacionActionPerformed

    private void radioEsteticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioEsteticoActionPerformed
        // TODO add your handling code here:
        cargarTratamientosPorTipo("Estetico");
    }//GEN-LAST:event_radioEsteticoActionPerformed

    private void btnReservarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservarActionPerformed
        int filaSeleccionada = tablaTratamientos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un tratamiento de la lista para reservar",
                    "Selección Requerida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int codigoTratamiento = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        Tratamiento tratamiento = tratamientod.buscarTratamiento(codigoTratamiento);

        if (tratamiento != null) {
            this.tratamientoPendiente = tratamiento;

            if (diaDeSpaActual == null) {
                buscarDiaDeSpaExistente();
            } else {
                abrirReservaConTratamiento(tratamientoPendiente);
            }
        }
    }//GEN-LAST:event_btnReservarActionPerformed

    private void btnContactoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContactoActionPerformed
        // TODO add your handling code here
        String contacto
                = "SPA ENTRE DEDOS\n\n"
                + "Dirección:\n"
                + "Av. Siempre Viva 123. "
                + "Centro Comercial 'El Descanso'\n"
                + "Local 45, Planta Baja\n\n"
                + "Teléfonos: "
                + "(351) 422-7890  -  "
                + "+54 9 351 512-3456\n\n"
                + "Email:\n"
                + "Para mas informacion: info@spaentrededos.com\n"
                + "Para Reservas: reservas@spaentrededos.com\n\n"
                + "Horarios de Atencion:\n"
                + "Lun-Vie: 9:00-21:00 hs\n"
                + "Sabados: 9:00-16:00 hs\n"
                + "Domingos: Cerrado";

        JOptionPane.showMessageDialog(this,
                contacto,
                "Contacto - Spa Entre Dedos",
                JOptionPane.INFORMATION_MESSAGE);
        this.dispose();

    }//GEN-LAST:event_btnContactoActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        int filaSeleccionada = tablaTratamientos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un tratamiento para consultar");
            return;
        }

        try {
            int codigoTratamiento = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            Tratamiento tratamiento = tratamientod.buscarTratamiento(codigoTratamiento);

            if (tratamiento != null) {
                String mensaje
                        = "Tratamiento: " + tratamiento.getNombre() + "\n\n"
                        + "INFORMACION:\n"
                        + "• Tipo: " + tratamiento.getTipo() + "\n"
                        + "• Duración: " + tratamiento.getDuracion() + " minutos\n"
                        + "• Precio: $" + String.format("%.2f", tratamiento.getCosto()) + "\n"
                        + "• Estado: " + (tratamiento.isActivo() ? "Disponible" : "No disponible") + "\n\n"
                        + "DETALLES:\n"
                        + tratamiento.getDetalle();

                if (tratamiento.getProductos() != null && !tratamiento.getProductos().trim().isEmpty()) {
                    mensaje += "\n\nPRODUCTOS:\n" + tratamiento.getProductos();
                }

                JOptionPane.showMessageDialog(this,
                        mensaje,
                        "Consulta: " + tratamiento.getNombre(),
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error: No se encontró el tratamiento seleccionado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al consultar: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnConsultarActionPerformed

    private void radioCorporalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioCorporalActionPerformed
        // TODO add your handling code here:
        cargarTratamientosPorTipo("Corporal");
    }//GEN-LAST:event_radioCorporalActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnReservaInstalacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservaInstalacionActionPerformed
        this.tratamientoPendiente = null;

        if (diaDeSpaActual == null) {
            buscarDiaDeSpaExistente();
        } else {
            abrirReservaSoloInstalacion();
        }
    }//GEN-LAST:event_btnReservaInstalacionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnContacto;
    private javax.swing.JButton btnReservaInstalacion;
    private javax.swing.JButton btnReservar;
    private javax.swing.JButton btnSalir;
    private com.toedter.calendar.DateUtil dateUtil1;
    private com.toedter.calendar.JCalendarBeanInfo jCalendarBeanInfo1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTree jTree1;
    private javax.swing.JRadioButton radioCorporal;
    private javax.swing.JRadioButton radioEstetico;
    private javax.swing.JRadioButton radioFacial;
    private javax.swing.JRadioButton radioRelajacion;
    private javax.swing.JTable tablaTratamientos;
    // End of variables declaration//GEN-END:variables

}
