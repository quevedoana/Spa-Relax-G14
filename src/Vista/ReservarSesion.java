/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Vista;

import Modelo.Consultorio;
import Modelo.DiaDeSpa;
import Modelo.Especialista;
import Modelo.Instalacion;
import Modelo.Tratamiento;
import Modelo.Turno;
import Persistencia.ConsultorioData;
import Persistencia.DiaDeSpaData;
import Persistencia.InstalacionData;
import Persistencia.TurnoData;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author maria
 */
public class ReservarSesion extends javax.swing.JInternalFrame {

    private Tratamiento tratamientoSeleccionado;
    private TurnoData turnoData = new TurnoData();
    private InstalacionData instalacionData = new InstalacionData();
    private ConsultorioData consultorioData = new ConsultorioData();
    private DiaDeSpaData diaSpaData = new DiaDeSpaData();
    private DiaDeSpa diaDeSpa;
    private double montoAcumulado = 0.0;

    private List<Especialista> especialistasDisponibles = new ArrayList<>();
    private List<Consultorio> consultoriosDisponibles = new ArrayList<>();
    private List<Instalacion> instalacionesDisponibles = new ArrayList<>();

    private boolean esSoloInstalacion;

    /**
     * Creates new form ReservarSesion
     */
    public ReservarSesion(Tratamiento tratamiento, DiaDeSpa diaDeSpa) {
        initComponents();
        this.tratamientoSeleccionado = tratamiento;
        this.diaDeSpa = diaDeSpa;
        this.montoAcumulado = diaDeSpa.getMonto();
        textTratamientoSeleccionado.setText(tratamiento.getNombre());

        setTitle("Reservar Tratamiento");
        btnReservarTurno.setText("Agregar Tratamiento al Dia de Spa");

        JOptionPane.showMessageDialog(this,
                "Continuando con su reserva...\n\n"
                + "Dia de Spa: Nro " + diaDeSpa.getCodPack() + "\n"
                + "Cliente: " + diaDeSpa.getCliente().getNombreCompleto() + "\n"
                + "Fecha: " + diaDeSpa.getFechaYHora().toLocalDate() + "\n"
                + "Monto actual: $" + String.format("%.2f", diaDeSpa.getMonto()) + "\n\n"
                + "Complete los detalles del tratamiento:",
                "Paso 2 de 2 - Reservar Tratamiento",
                JOptionPane.INFORMATION_MESSAGE);

        cargarDatosIniciales();
        btnReservarTurno.setToolTipText("Agregar tratamiento al Dia de Spa #" + diaDeSpa.getCodPack());
    }

    public ReservarSesion(DiaDeSpa diaDeSpa) {
        initComponents();
        this.tratamientoSeleccionado = null;
        this.diaDeSpa = diaDeSpa;
        this.montoAcumulado = diaDeSpa.getMonto(); 
        this.esSoloInstalacion = true;
        
        textTratamientoSeleccionado.setEnabled(false);
        comboConsultorios.setEnabled(false);
        comboEspecialistas.setEnabled(false);

        setTitle("Reservar Instalacion - Dia de Spa #" + diaDeSpa.getCodPack());
        textTratamientoSeleccionado.setText("Dia de Spa #" + diaDeSpa.getCodPack());
        btnReservarTurno.setText("Agregar Instalacion al Dia de Spa");

        JOptionPane.showMessageDialog(this,
                "Continuando con su reserva...\n\n"
                + "Dia de Spa: Nro " + diaDeSpa.getCodPack() + "\n"
                + "Cliente: " + diaDeSpa.getCliente().getNombreCompleto() + "\n"
                + "Fecha: " + diaDeSpa.getFechaYHora().toLocalDate() + "\n"
                + "Monto actual: $" + String.format("%.2f", diaDeSpa.getMonto()) + "\n\n"
                + "Seleccione la instalacion que desea reservar:",
                "Paso 2 de 2 - Reservar Instalacion",
                JOptionPane.INFORMATION_MESSAGE);

        btnReservarTurno.setToolTipText("Agregar instalacion al Dia de Spa #" + diaDeSpa.getCodPack());
        cargarDatosInicialesInstalacion();
    }

    private void cargarDatosInicialesInstalacion() {
        comboInstalaciones.removeAllItems();
        instalacionesDisponibles = turnoData.ListaInstalaciones();

        comboInstalaciones.addItem("Sin instalacion adicional");
        for (Instalacion inst : instalacionesDisponibles) {
            if (inst.isEstado()) {
                comboInstalaciones.addItem(inst.getNombre());
            }
        }
        comboHorarios.removeAllItems();

        
        String[] horarios = {
            "09:00", "10:00", "11:00", "12:00",
            "14:00", "15:00", "16:00", "17:00", "18:00"
        };

        for (String horario : horarios) {
            comboHorarios.addItem(horario);
        }
    }

    private void cargarDatosIniciales() {
        
        comboEspecialistas.removeAllItems();
        especialistasDisponibles = turnoData.ListarEspecialistas(tratamientoSeleccionado.getTipo());

        for (Especialista esp : especialistasDisponibles) {
            comboEspecialistas.addItem(esp.getNombreYApellido());
        }

        if (especialistasDisponibles.isEmpty()) {
            comboEspecialistas.addItem("No hay especialistas disponibles");
        }

        
        comboInstalaciones.removeAllItems();
        instalacionesDisponibles = turnoData.ListaInstalaciones();

        comboInstalaciones.addItem("Sin instalación adicional");
        for (Instalacion inst : instalacionesDisponibles) {
            if (inst.isEstado()) {
                comboInstalaciones.addItem(inst.getNombre());
            }
        }

      
        comboConsultorios.removeAllItems();
        consultoriosDisponibles = turnoData.ListarConsultorios(tratamientoSeleccionado.getTipo());

        for (Consultorio cons : consultoriosDisponibles) {
            comboConsultorios.addItem("Consultorio " + cons.getNroConsultorio() + " - " + cons.getUsos());
        }

        if (consultoriosDisponibles.isEmpty()) {
            comboConsultorios.addItem("No hay consultorios disponibles");
        }

        
        comboHorarios.removeAllItems();

       
        String[] horarios = {
            "09:00", "10:00", "11:00", "12:00",
            "14:00", "15:00", "16:00", "17:00", "18:00"
        };

        for (String horario : horarios) {
            comboHorarios.addItem(horario);
        }
    }

    private void calcularTotal() {
        double total = 0.0;

        if (esSoloInstalacion) {
            if (comboInstalaciones.getSelectedIndex() >= 0 && !instalacionesDisponibles.isEmpty()) {
                int index = comboInstalaciones.getSelectedIndex();
                if (index >= 0 && index < instalacionesDisponibles.size()) {
                    Instalacion inst = instalacionesDisponibles.get(index);
                    total = inst.getPrecio30m() * 2;
                }
            }
        } else {
            total = tratamientoSeleccionado.getCosto();

            if (comboInstalaciones.getSelectedIndex() > 0 && !instalacionesDisponibles.isEmpty()) {
                int index = comboInstalaciones.getSelectedIndex() - 1;
                if (index >= 0 && index < instalacionesDisponibles.size()) {
                    Instalacion inst = instalacionesDisponibles.get(index);
                    double costoInstalacion = inst.getPrecio30m() * (tratamientoSeleccionado.getDuracion() / 30.0);
                    total += costoInstalacion;
                }
            }
        }

        textTotal.setText(String.format("$%.2f", total));
    }

    private void reservarSoloInstalacion() {
       
        if (comboInstalaciones.getSelectedIndex() == -1 || comboHorarios.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una instalación y horario");
            return;
        }

        if (instalacionesDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay instalaciones disponibles");
            return;
        }

        try {
            Instalacion instalacionSeleccionada = instalacionesDisponibles.get(comboInstalaciones.getSelectedIndex());

            // Validar formato horario
        String horario = (String) comboHorarios.getSelectedItem();
        if (horario == null || !horario.matches("\\d{1,2}:\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Formato de horario inválido. Use HH:mm");
            return;
        }

        String[] parts = horario.split(":");
        int hour = Integer.parseInt(parts[0].trim());
        int minute = Integer.parseInt(parts[1].trim());
        LocalTime horaSeleccionada = LocalTime.of(hour, minute);

        // Usar la FECHA del diaDeSpa (diaDeSpa.getFechaYHora() es LocalDateTime)
        LocalDate fechaReserva;
        if (diaDeSpa != null && diaDeSpa.getFechaYHora() != null) {
            fechaReserva = diaDeSpa.getFechaYHora().toLocalDate();
        } else {
            // fallback por seguridad (no debería suceder si la vista está vinculada)
            fechaReserva = LocalDate.now();
        }

        LocalDateTime fechaHoraInicio = LocalDateTime.of(fechaReserva, horaSeleccionada);
        LocalDateTime fechaHoraFin = fechaHoraInicio.plusMinutes(60);

           
            double costoInstalacion = instalacionSeleccionada.getPrecio30m() * 2;
            double totalConEsteServicio = montoAcumulado + costoInstalacion;

            int confirmacion = JOptionPane.showConfirmDialog(this,
                "RESUMEN DEL SERVICIO:\n\n" +
                "Instalación: " + instalacionSeleccionada.getNombre() + "\n" +
                "Horario: " + horario + "\n" +
                "Duración: 60 minutos\n" +
                "Costo de este servicio: $" + String.format("%.2f", costoInstalacion) + "\n" +
                "Monto acumulado en el Día de Spa: $" + String.format("%.2f", totalConEsteServicio) + "\n\n" +
                "¿Desea agregar este servicio al día de spa?",
                "Confirmar Servicio",
                JOptionPane.YES_NO_OPTION);

            if (confirmacion != JOptionPane.YES_OPTION) {
                return; 
            }


            Turno nuevoTurno = new Turno(
                    fechaHoraInicio,
                    fechaHoraFin,
                    null,
                    null,
                    null,
                    instalacionSeleccionada,
                    diaDeSpa,
                    true
            );

            turnoData.guardarSesionConPack(nuevoTurno, diaDeSpa.getCodPack());

 
            montoAcumulado += costoInstalacion;
            diaDeSpa.setMonto(montoAcumulado);
            
            diaSpaData.actualizarMontoDiaDeSpa(diaDeSpa.getCodPack(), diaDeSpa.getMonto());


            diaDeSpa.getSesiones().add(nuevoTurno);

            JOptionPane.showMessageDialog(this,
                    "¡Instalación reservada exitosamente!\n" +
                    "Instalación: " + instalacionSeleccionada.getNombre() + "\n" +
                    "Horario: " + horario + "\n" +
                    "Día de Spa: #" + diaDeSpa.getCodPack() + "\n" +
                    "Monto total acumulado: $" + String.format("%.2f", diaDeSpa.getMonto()));

            preguntarContinuarReserva();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al reservar la instalación: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void reservarTratamientoConInstalacion() {
        if (comboEspecialistas.getSelectedIndex() == -1
                || comboConsultorios.getSelectedIndex() == -1
                || comboHorarios.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios");
            return;
        }

        if (especialistasDisponibles.isEmpty() || consultoriosDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay disponibilidad para reservar");
            return;
        }

        try {
            Especialista especialistaSeleccionado = especialistasDisponibles.get(comboEspecialistas.getSelectedIndex());
            Consultorio consultorioSeleccionado = consultoriosDisponibles.get(comboConsultorios.getSelectedIndex());

            Instalacion instalacionSeleccionada = null;
            int duracionTotal = tratamientoSeleccionado.getDuracion();
            double costoInstalacion = 0.0;

            if (comboInstalaciones.getSelectedIndex() > 0) {
                int index = comboInstalaciones.getSelectedIndex() - 1;
                if (index >= 0 && index < instalacionesDisponibles.size()) {
                    instalacionSeleccionada = instalacionesDisponibles.get(index);
                    duracionTotal += 30;
                    costoInstalacion = instalacionSeleccionada.getPrecio30m() * (tratamientoSeleccionado.getDuracion() / 30.0);
                }
            }

            // ---------------------------------------------------------
        String horario = (String) comboHorarios.getSelectedItem();
        if (horario == null || !horario.matches("\\d{1,2}:\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Formato de horario inválido. Use HH:mm");
            return;
        }

        String[] parts = horario.split(":");
        int hour = Integer.parseInt(parts[0].trim());
        int minute = Integer.parseInt(parts[1].trim());
        LocalTime horaSeleccionada = LocalTime.of(hour, minute);

        // Extraer la fecha desde diaDeSpa.fechaYHora (es LocalDateTime)
        LocalDate fechaReserva;
        if (diaDeSpa != null && diaDeSpa.getFechaYHora() != null) {
            fechaReserva = diaDeSpa.getFechaYHora().toLocalDate();
        } else {
            // fallback por seguridad: esto no debería pasar si tu vista está correctamente vinculada
            fechaReserva = LocalDate.now();
        }

        LocalDateTime fechaHoraInicio = LocalDateTime.of(fechaReserva, horaSeleccionada);
        LocalDateTime fechaHoraFin = fechaHoraInicio.plusMinutes(duracionTotal);
        // ---------------------------------------------------------

            double costoServicio = tratamientoSeleccionado.getCosto() + costoInstalacion;
            double totalConEsteServicio = montoAcumulado + costoServicio;

            int confirmacion = JOptionPane.showConfirmDialog(this,
                "RESUMEN DEL SERVICIO:\n\n" +
                "Tratamiento: " + tratamientoSeleccionado.getNombre() + "\n" +
                "Especialista: " + especialistaSeleccionado.getNombreYApellido() + "\n" +
                "Consultorio: " + consultorioSeleccionado.getNroConsultorio() + "\n" +
                (instalacionSeleccionada != null ? "Instalación: " + instalacionSeleccionada.getNombre() + "\n" : "") +
                "Horario: " + horario + "\n" +
                "Duración: " + duracionTotal + " minutos\n" +
                "Costo de este servicio: $" + String.format("%.2f", costoServicio) + "\n" +
                "Monto acumulado en el Día de Spa: $" + String.format("%.2f", totalConEsteServicio) + "\n\n" +
                "¿Desea agregar este servicio al día de spa?",
                "Confirmar Servicio",
                JOptionPane.YES_NO_OPTION);

            if (confirmacion != JOptionPane.YES_OPTION) {
                return; 
            }

            

            Turno nuevoTurno = new Turno(
                    fechaHoraInicio,
                    fechaHoraFin,
                    tratamientoSeleccionado,
                    consultorioSeleccionado,
                    especialistaSeleccionado,
                    instalacionSeleccionada,
                    diaDeSpa,
                    true
            );
            System.out.println("DEBUG - fecha inicio turno = " + nuevoTurno.getFechaYHoraDeInicio());
            turnoData.guardarSesionConPack(nuevoTurno, diaDeSpa.getCodPack());

            montoAcumulado += costoServicio;
            diaDeSpa.setMonto(montoAcumulado);

            diaSpaData.actualizarMontoDiaDeSpa(diaDeSpa.getCodPack(), diaDeSpa.getMonto());

            diaDeSpa.getSesiones().add(nuevoTurno);

            JOptionPane.showMessageDialog(this,
                    "¡Turno reservado exitosamente!\n" +
                    "Tratamiento: " + tratamientoSeleccionado.getNombre() + "\n" +
                    "Especialista: " + especialistaSeleccionado.getNombreYApellido() + "\n" +
                    "Horario: " + horario + "\n" +
                    "Monto total acumulado: $" + String.format("%.2f", diaDeSpa.getMonto()));

            preguntarContinuarReserva();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al reservar el turno: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    

  private void preguntarContinuarReserva() {
    int respuesta = JOptionPane.showConfirmDialog(this,
            "Servicio agregado exitosamente al Día de Spa #" + diaDeSpa.getCodPack() + "\n\n"
            + "¿Desea agregar otro servicio/turno al mismo día de spa?",
            "Continuar con Reservas",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

    if (respuesta == JOptionPane.YES_OPTION) {
        this.dispose();
    } else {
        procesarPagoFinal();
    }
}

private void procesarPagoFinal() {
    double totalAPagar = diaDeSpa.getMonto();
    mostrarResumenFinal();
    
    int opcionPago = JOptionPane.showConfirmDialog(this,
        "PAGO FINAL\n\n" +
        "Total a pagar: $" + String.format("%.2f", totalAPagar) + "\n\n" +
        "¿Desea realizar el pago ahora?",
        "Procesar Pago",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE);

    if (opcionPago == JOptionPane.YES_OPTION) {
        PagoTarjeta pagoFinal = new PagoTarjeta((java.awt.Frame) this.getTopLevelAncestor(), true);
        pagoFinal.setVisible(true);

        if (pagoFinal.pagoEfectuado()) {
            JOptionPane.showMessageDialog(this,
                "Pago procesado exitosamente\n" +
                "¡Gracias por su compra!",
                "Pago Completado",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Pago cancelado\n" +
                "Total pendiente: $" + String.format("%.2f", totalAPagar),
                "Pago No Realizado",
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    this.dispose();
}
   

    private void mostrarResumenFinal() {
        StringBuilder resumen = new StringBuilder();
        resumen.append("Reserva Completa\n\n");
        resumen.append("Día de Spa #").append(diaDeSpa.getCodPack()).append("\n");
        resumen.append("Cliente: ").append(diaDeSpa.getCliente().getNombreCompleto()).append("\n");
        resumen.append("Fecha: ").append(diaDeSpa.getFechaYHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n");
        resumen.append("Total de servicios: ").append(diaDeSpa.getSesiones().size()).append("\n");
        resumen.append("Monto total: $").append(String.format("%.2f", diaDeSpa.getMonto())).append("\n\n");
        
        resumen.append("Servicios contratados:\n");
        double totalCalculado = 0.0;
        for (Turno sesion : diaDeSpa.getSesiones()) {
            if (sesion.getTratamiento() != null) {
                double costo = sesion.getTratamiento().getCosto();
                totalCalculado += costo;
                resumen.append("• ").append(sesion.getTratamiento().getNombre())
                       .append(" - $").append(String.format("%.2f", costo)).append("\n");
            } else if (sesion.getInstalacion() != null) {
                double costo = sesion.getInstalacion().getPrecio30m() * 2;
                totalCalculado += costo;
                resumen.append("• ").append(sesion.getInstalacion().getNombre())
                       .append(" (Instalación) - $").append(String.format("%.2f", costo)).append("\n");
            }
        }
        
        resumen.append("\nTotal verificado: $").append(String.format("%.2f", totalCalculado));

        JOptionPane.showMessageDialog(this,
                resumen.toString(),
                "Reserva Completa - Día de Spa #" + diaDeSpa.getCodPack(),
                JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        textTratamientoSeleccionado = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        comboEspecialistas = new javax.swing.JComboBox<>();
        comboConsultorios = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        comboHorarios = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnPreciosInstalaciones = new javax.swing.JButton();
        comboInstalaciones = new javax.swing.JComboBox<>();
        textTotal = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnReservarTurno = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        jCheckBox1.setText("jCheckBox1");

        setResizable(true);
        setTitle("Reservar Turno");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 102, 51));
        jLabel1.setText("Reservas de Servicios");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Tratamiento Seleccionado:");

        textTratamientoSeleccionado.setBackground(new java.awt.Color(255, 204, 179));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Especialistas:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Consultorios:");

        comboEspecialistas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        comboConsultorios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboConsultorios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboConsultoriosActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Horario:");

        comboHorarios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("(opcional)");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Instalacion:");

        btnPreciosInstalaciones.setBackground(new java.awt.Color(255, 102, 51));
        btnPreciosInstalaciones.setForeground(new java.awt.Color(255, 255, 255));
        btnPreciosInstalaciones.setText("Consultar Precios");
        btnPreciosInstalaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreciosInstalacionesActionPerformed(evt);
            }
        });

        comboInstalaciones.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboInstalaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboInstalacionesActionPerformed(evt);
            }
        });

        textTotal.setBackground(new java.awt.Color(255, 204, 179));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Total:   $");

        btnReservarTurno.setBackground(new java.awt.Color(255, 102, 51));
        btnReservarTurno.setForeground(new java.awt.Color(255, 255, 255));
        btnReservarTurno.setText("Reservar Servicio");
        btnReservarTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReservarTurnoActionPerformed(evt);
            }
        });

        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboEspecialistas, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textTratamientoSeleccionado, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboConsultorios, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboHorarios, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnReservarTurno)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(comboInstalaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel6)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnPreciosInstalaciones)))
                            .addComponent(jLabel1)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnVolver)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnVolver)
                .addGap(2, 2, 2)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(textTratamientoSeleccionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(comboEspecialistas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(comboConsultorios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(comboHorarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboInstalaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPreciosInstalaciones)
                            .addComponent(jLabel6)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(23, 23, 23)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(textTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReservarTurno))
                .addGap(30, 30, 30))
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

    private void comboConsultoriosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboConsultoriosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboConsultoriosActionPerformed

    private void btnPreciosInstalacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreciosInstalacionesActionPerformed
        // TODO add your handling code here:
        StringBuilder precios = new StringBuilder("Precios de Instalaciones:\n\n");
        for (Instalacion inst : instalacionesDisponibles) {
            if (inst.isEstado()) {
                precios.append(inst.getNombre())
                        .append(": $").append(inst.getPrecio30m())
                        .append(" por 30 minutos\n");
            }
        }
        JOptionPane.showMessageDialog(this, precios.toString(), "Precios de Instalaciones", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnPreciosInstalacionesActionPerformed

    private void btnReservarTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservarTurnoActionPerformed
        if (esSoloInstalacion) {
            reservarSoloInstalacion();
        } else {
            reservarTratamientoConInstalacion();
        }
        calcularTotal();
    }//GEN-LAST:event_btnReservarTurnoActionPerformed

    private void comboInstalacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboInstalacionesActionPerformed
        // TODO add your handling code here
        calcularTotal(); // Recalcular cuando cambia la instalación

    }//GEN-LAST:event_comboInstalacionesActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPreciosInstalaciones;
    private javax.swing.JButton btnReservarTurno;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> comboConsultorios;
    private javax.swing.JComboBox<String> comboEspecialistas;
    private javax.swing.JComboBox<String> comboHorarios;
    private javax.swing.JComboBox<String> comboInstalaciones;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField textTotal;
    private javax.swing.JTextField textTratamientoSeleccionado;
    // End of variables declaration//GEN-END:variables
}
