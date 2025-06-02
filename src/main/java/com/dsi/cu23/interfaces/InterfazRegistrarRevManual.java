package com.dsi.cu23.interfaces;

import javax.swing.*;

import com.dsi.cu23.controllers.*;
import com.dsi.cu23.models.EstacionSismologica;
import com.dsi.cu23.models.EventoSismico;
import com.dsi.cu23.utils.LocalDateTimeFormat;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * Interfaz para el registro de revisión manual usando Swing
 */
public class InterfazRegistrarRevManual {
    private JFrame ventana;
    private GestorRegistrarRevManual gestor;
    private JTable table;
    private JPanel inputsPanel;
    private JComboBox<String> clasificacionComboBox;
    private JComboBox<String> origenComboBox;
    private JComboBox<String> alcanceComboBox;
    private JTextField magnitudField; // this is gonna be displayed when the user can update data
    private final int ANCHO = 500;
    private final int ALTO = 900;

    public InterfazRegistrarRevManual() {
        this.habilitarVentana();
        this.gestor = new GestorRegistrarRevManual(this);
    }

    public void habilitarVentana() {
        this.ventana = new JFrame("Registrar Revisión Manual");
        this.ventana.setSize(ANCHO, ALTO);
        this.ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.ventana.setResizable(false);
        this.ventana.setLayout(new BoxLayout(this.ventana.getContentPane(), BoxLayout.Y_AXIS));

        // Configurar el cierre de ventana
        this.ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cerrarVentana();
            }
        });
        this.ventana.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio superior
        this.ventana.add(InterfazRegistrarRevManual.getTitulo("Registrar Revisión Manual"));
        this.ventana.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio superior

        this.ventana.setVisible(true);
    }

    public void cerrarVentana() {
        if (this.ventana != null) {
            this.ventana.dispose();
            this.ventana = null;
            System.out.println("Ventana cerrada correctamente.");
        }
    }

    // Getters
    public JFrame getVentana() {
        return ventana;
    }

    public GestorRegistrarRevManual getGestor() {
        return gestor;
    }

    /**
     * Muestra una lista de eventos sísmicos en una tabla con un radio button para
     * seleccionar una fila.
     * 
     * @param listaDeEventos Lista de eventos sísmicos a mostrar.
     */
    public void mostrarEventos(List<EventoSismico> listaDeEventos) {
        String[] columnNames = { "Fecha", "Epicentro", "Hipoepicentro", "Magnitud" };
        Object[][] data = new Object[listaDeEventos.size()][4];
        for (int i = 0; i < listaDeEventos.size(); i++) {
            EventoSismico es = listaDeEventos.get(i);
            data[i][0] = es.toString();
            data[i][1] = es.getCoordenadas()[0].toString();
            data[i][2] = es.getCoordenadas()[1].toString();
            data[i][3] = es.getMagnitud();
        }

        this.table = new JTable();
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return String.class;
                } else if (columnIndex == 1 || columnIndex == 2) {
                    return String.class;
                } else {
                    return Double.class;
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        table.setPreferredScrollableViewportSize(
                new Dimension(300, table.getRowHeight() * Math.min(8, table.getRowCount())));
        table.setFillsViewportHeight(false);
        // table.setAutoResizeMode(JTable.AUTO_RESIZE_ON);

        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(1).setPreferredWidth(60);
        table.getColumnModel().getColumn(2).setPreferredWidth(60);
        table.getColumnModel().getColumn(3).setPreferredWidth(60);

        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setMaximumSize(new Dimension(ANCHO, 90));
        scrollPane.setPreferredSize(new Dimension(ANCHO, 90));

        this.ventana.add(scrollPane);
        this.actualizarVentana();
    }

    public void solicitarSeleccionEvento() {
        this.ventana.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio superior
        JButton botonSeleccionarEvento = new JButton("Seleccionar Evento");
        botonSeleccionarEvento.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonSeleccionarEvento.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                this.tomarEvento(selectedRow);
                this.ventana.remove(botonSeleccionarEvento);
            } else {
                JOptionPane.showMessageDialog(this.ventana, "Por favor, seleccione un evento de la lista.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        this.ventana.add(botonSeleccionarEvento);
        this.actualizarVentana();
    }

    private void tomarEvento(int selectedRow) {
        this.gestor.tomarEvento(selectedRow);
        this.table.setEnabled(false);
    }

    private void actualizarVentana() {
        this.ventana.revalidate();
        this.ventana.repaint();
        this.ventana.setVisible(true);
    }

    public static JLabel getTitulo(String label) {
        JLabel titulo = new JLabel(label);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        return titulo;
    }

    // God save us and bless the person who mantains this code. Theo - 2025
    public void mostrarClasificacionOrigenAlcance(HashMap<String, Object> datos) {
        this.inputsPanel = new JPanel();

        inputsPanel.setLayout(new BoxLayout(inputsPanel, BoxLayout.Y_AXIS));

        inputsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Datos del Evento Seleccionado",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14)));

        // Agregar espacio inicial
        inputsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Clasificación
        this.clasificacionComboBox = new JComboBox<>(this.gestor.getClasificacionesOpciones());
        this.clasificacionComboBox.setSelectedItem(datos.get("Clasificación del sismo"));
        this.clasificacionComboBox.setEnabled(false);
        this.clasificacionComboBox.setFocusable(false);
        this.clasificacionComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.clasificacionComboBox
                .setMaximumSize(new Dimension(Integer.MAX_VALUE, this.clasificacionComboBox.getPreferredSize().height));

        JLabel labelClasificacion = new JLabel("Clasificación:");
        labelClasificacion.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputsPanel.add(labelClasificacion);
        inputsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        inputsPanel.add(this.clasificacionComboBox);
        inputsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Origen
        this.origenComboBox = new JComboBox<>(this.gestor.getOrigenesOpciones());
        this.origenComboBox.setSelectedItem(datos.get("Origen de generación"));
        this.origenComboBox.setEnabled(false);
        this.origenComboBox.setFocusable(false);
        this.origenComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.origenComboBox
                .setMaximumSize(new Dimension(Integer.MAX_VALUE, this.origenComboBox.getPreferredSize().height));

        JLabel labelOrigen = new JLabel("Origen:");
        labelOrigen.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputsPanel.add(labelOrigen);
        inputsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        inputsPanel.add(this.origenComboBox);
        inputsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Alcance
        this.alcanceComboBox = new JComboBox<>(this.gestor.getAlcancesOpciones());
        this.alcanceComboBox.setSelectedItem(datos.get("Alcance del sismo"));
        this.alcanceComboBox.setEnabled(false);
        this.alcanceComboBox.setFocusable(false);
        this.alcanceComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.alcanceComboBox
                .setMaximumSize(new Dimension(Integer.MAX_VALUE, this.alcanceComboBox.getPreferredSize().height));

        JLabel labelAlcance = new JLabel("Alcance:");
        labelAlcance.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputsPanel.add(labelAlcance);
        inputsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        inputsPanel.add(this.alcanceComboBox);

        // Agregar espacio final
        inputsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Configurar el tamaño del panel
        inputsPanel.setMaximumSize(new Dimension(ANCHO - 40, 200));
        inputsPanel.setPreferredSize(new Dimension(ANCHO - 40, 200));
        inputsPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar el panel en la ventana

        this.ventana.add(inputsPanel);
        this.ventana.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio
        this.actualizarVentana();
    }

    public void mostrarSismogramas(List<EstacionSismologica> estaciones, List<BufferedImage> sismogramas) {
        // Crear un JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        tabbedPane.setPreferredSize(new Dimension(ANCHO, 250));
        tabbedPane.setMaximumSize(new Dimension(ANCHO, 250));
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        // Llenar los tabs con imágenes de sismogramas
        for (int i = 0; i < estaciones.size(); i++) {
            EstacionSismologica estacion = estaciones.get(i);
            BufferedImage sismograma = sismogramas.get(i);

            JLabel imagenLabel = new JLabel(new ImageIcon(sismograma));
            JScrollPane scrollPane = new JScrollPane(imagenLabel);
            scrollPane.setPreferredSize(new Dimension(ANCHO, 200));
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
            scrollPane.setMaximumSize(new Dimension(ANCHO, 200));
            tabbedPane.addTab(estacion.getNombre(), scrollPane);
        }

        // Agregar el JTabbedPane a la ventana
        this.ventana.getContentPane().add(tabbedPane, BorderLayout.CENTER);

        this.actualizarVentana();
    }

    public void habilitarOpcionVerMapa() {
        this.ventana.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio superior

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBotones.setMaximumSize(new Dimension(ANCHO - 40, 40));
        panelBotones.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton botonVerMapa = new JButton("Ver Mapa del Evento");
        botonVerMapa.addActionListener(e -> {
            JOptionPane.showMessageDialog(this.ventana,
                    "Funcionalidad de visualización de mapa no implementada.",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton botonRechazarMapa = new JButton("Rechazar ver mapa");
        botonRechazarMapa.addActionListener(e -> {
            this.tomarRechazo();
            // remove buttons and update the window
            this.ventana.remove(panelBotones);
            this.actualizarVentana();
        });

        panelBotones.add(botonVerMapa);
        panelBotones.add(botonRechazarMapa);

        this.ventana.add(panelBotones);
        this.actualizarVentana();
    }

    private void tomarRechazo() {
        this.gestor.tomarRechazo();
    }

    public void permitirModificacionOrigenMagnitudAlcance(double magnitud) {
        this.origenComboBox.setEnabled(true);
        this.origenComboBox.setFocusable(true);
        this.alcanceComboBox.setEnabled(true);
        this.alcanceComboBox.setFocusable(true);

        this.magnitudField = new JTextField();
        this.magnitudField.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.magnitudField.getPreferredSize().height));
        this.magnitudField.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.magnitudField.setEditable(true);
        this.magnitudField.setFocusable(true);
        this.magnitudField.setToolTipText("Ingrese la magnitud del evento sismico");
        this.magnitudField.setText(String.valueOf(magnitud));
        this.magnitudField.setPreferredSize(new Dimension(100, this.magnitudField.getPreferredSize().height));
        this.magnitudField.setEnabled(true);
        this.magnitudField.setFocusable(true);
        this.inputsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio superior
        JLabel labelMagnitud = new JLabel("Magnitud:");
        labelMagnitud.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.inputsPanel.add(labelMagnitud);
        this.inputsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        this.inputsPanel.add(this.magnitudField);
        this.inputsPanel.setMaximumSize(new Dimension(ANCHO - 40, 260));
        // display 2 buttons, "Modificar" and "No hacer modificaciones"
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBotones.setMaximumSize(new Dimension(ANCHO - 40, 40));
        panelBotones.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton botonModificar = new JButton("Modificar");
        botonModificar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this.ventana,
                    "Funcionalidad de modificación de mapa no implementada.",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
        });
        JButton botonNoModificar = new JButton("No hacer modificaciones");
        botonNoModificar.addActionListener(e -> {
            this.ventana.remove(panelBotones);
            this.tomarNoModificar();
        });

        panelBotones.add(botonModificar);
        panelBotones.add(botonNoModificar);
        this.ventana.add(panelBotones);
        this.actualizarVentana();
    }

    private void tomarNoModificar() {
        this.gestor.tomarNoModificar();
    }

    public void solicitarConfirmarRechazoORevisionExperto() {
        // Confirmar evento, Rechazar evento o Solicitar revisión a experto.
        // mostrar los 3 botones en una fila, los botones de confirmar y confirmar deberan de mostrar el mensaje "no implemenetado"
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 10, 0));
        panelBotones.setMaximumSize(new Dimension(ANCHO - 40, 40));
        panelBotones.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton botonConfirmar = new JButton("Confirmar Evento");
        botonConfirmar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this.ventana,
                    "Funcionalidad de confirmación de evento no implementada.",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton botonRechazar = new JButton("Rechazar Evento");
        botonRechazar.addActionListener(e -> {
            this.ventana.remove(panelBotones);
            this.actualizarVentana();
            this.tomarRechazarEvento();
        });

        JButton botonRevisionExperto = new JButton("Solicitar Revisión a Experto");
        botonRevisionExperto.addActionListener(e -> {
            JOptionPane.showMessageDialog(this.ventana,
                    "Funcionalidad de revisión a experto no implementada.",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
        });

        panelBotones.add(botonConfirmar);
        panelBotones.add(botonRechazar);
        panelBotones.add(botonRevisionExperto);
        this.ventana.add(panelBotones);
        this.actualizarVentana();
    }

    private void tomarRechazarEvento() {
        this.gestor.tomarRechazoEvento();
    }

}
