package com.dsi.cu23;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Interfaz para el registro de revisión manual usando Swing
 */
public class InterfazRegistrarRevManual {
    private JFrame ventana;
    private GestorRegistrarRevManual gestor;
    private JTable table;

    public InterfazRegistrarRevManual() {
        this.habilitarVentana();
        this.gestor = new GestorRegistrarRevManual(this);
    }

    public void habilitarVentana() {
        this.ventana = new JFrame("Registrar Revisión Manual");
        this.ventana.setSize(600, 800);
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
    public JFrame getVentana() { return ventana; }
    public GestorRegistrarRevManual getGestor() { return gestor; }

    /**
     * Muestra una lista de eventos sísmicos en una tabla con un radio button para seleccionar una fila.
     * @param listaDeEventos Lista de eventos sísmicos a mostrar.
     */
    public void mostrarEventos(List<EventoSismico> listaDeEventos) {
        String[] columnNames = {"Fecha", "Coordenas","Magnitud"};
        Object[][] data = new Object[listaDeEventos.size()][3];
        for (int i = 0; i < listaDeEventos.size(); i++) {
            EventoSismico es = listaDeEventos.get(i);
            data[i][0] = es.toString();
            data[i][1] = es.getCoordenadas().toString();
            data[i][2] = es.getMagnitud();
        }

        this.table = new JTable();
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Solo una fila seleccionable

        table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return String.class; // Columna de label
                } else if (columnIndex == 1) {
                    return String.class; // Columna de coordenadas
                } else {
                    return Double.class; // Columna de magnitud
                }
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No editable
            }
        });

        table.getColumnModel().getColumn(0).setPreferredWidth(300);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setPreferredSize(new Dimension(550, 100));
        
        this.ventana.add(scrollPane);
        this.actualizarVentana();
    }

    /**
     * Muestra un boton para solicitar la selección de un evento.
     */
    public void solicitarSeleccionEvento() {
        JButton botonSeleccionarEvento = new JButton("Seleccionar Evento");
        botonSeleccionarEvento.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonSeleccionarEvento.addActionListener(e -> {
            System.out.println("Botón de selección de evento presionado.");
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                // EventoSismico eventoSeleccionado = this.gestor.getEventos().get(selectedRow);
                // System.out.println("Evento seleccionado: " + eventoSeleccionado.toLabel());
                // this.gestor.solicitarSeleccionEvento(eventoSeleccionado);
                this.gestor.solicitarSeleccionEvento(selectedRow);
                System.out.println("Evento seleccionado: " + selectedRow);
                limpiarVentana();
            }
            else {
                JOptionPane.showMessageDialog(this.ventana, "Por favor, seleccione un evento de la lista.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        this.ventana.add(botonSeleccionarEvento);
        this.actualizarVentana();
    }

    private void actualizarVentana() {
        this.ventana.revalidate();
        this.ventana.repaint();
    }

    private void limpiarVentana() {
        this.ventana.getContentPane().removeAll();
        this.ventana.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio superior
        this.ventana.add(InterfazRegistrarRevManual.getTitulo("Registrar Revisión Manual"));
        this.ventana.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio superior
    }

    public static JLabel getTitulo (String label) {
        JLabel titulo = new JLabel(label);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        return titulo;
    }
}
