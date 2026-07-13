package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.Categoria;
import model.Transaccion;
import servicio.GestorFinanzas;

public class VentanaFinanzas extends JFrame {
    private static final DecimalFormat MONEDA = new DecimalFormat("0.00");
    private static final String[] MESES = {
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };

    private GestorFinanzas gestor;
    private JLabel etiquetaSaldo;
    private JLabel etiquetaIngresos;
    private JLabel etiquetaGastos;
    private JLabel etiquetaMovimientos;

    public VentanaFinanzas() {
        gestor = new GestorFinanzas();
        configurarVentana();
        construirInterfaz();
        actualizarVista();
    }

    private void configurarVentana() {
        setTitle("Finanzas Personales");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 560));
        setLocationRelativeTo(null);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
    }

    private void construirInterfaz() {
        JPanel raiz = new JPanel(new BorderLayout(12, 12));
        raiz.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        raiz.setBackground(new Color(245, 247, 250));
        setContentPane(raiz);

        raiz.add(crearEncabezado(), BorderLayout.NORTH);
        raiz.add(crearPanelAcciones(), BorderLayout.WEST);
        raiz.add(crearPanelResumen(), BorderLayout.CENTER);
    }

    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel titulo = new JLabel("Finanzas Personales");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(new Color(28, 37, 54));

        etiquetaSaldo = new JLabel();
        etiquetaSaldo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        etiquetaSaldo.setHorizontalAlignment(SwingConstants.RIGHT);
        etiquetaSaldo.setForeground(new Color(19, 108, 78));

        panel.add(titulo, BorderLayout.WEST);
        panel.add(etiquetaSaldo, BorderLayout.EAST);
        return panel;
    }

    private JPanel crearPanelAcciones() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 224, 232)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));
        panel.setPreferredSize(new Dimension(260, 0));

        agregarBoton(panel, "Registrar ingreso", 0, new Runnable() {
            public void run() {
                mostrarDialogoMovimiento(Transaccion.TIPO_INGRESO);
            }
        });
        agregarBoton(panel, "Registrar gasto", 1, new Runnable() {
            public void run() {
                mostrarDialogoMovimiento(Transaccion.TIPO_GASTO);
            }
        });
        agregarBoton(panel, "Registrar pago pendiente", 2, new Runnable() {
            public void run() {
                mostrarDialogoPagoPendiente();
            }
        });
        agregarBoton(panel, "Procesar pago pendiente", 3, new Runnable() {
            public void run() {
                procesarPagoPendiente();
            }
        });
        agregarBoton(panel, "Historial de movimientos", 4, new Runnable() {
            public void run() {
                mostrarHistorialMovimientos();
            }
        });
        agregarBoton(panel, "Ver categorias", 5, new Runnable() {
            public void run() {
                mostrarCategorias();
            }
        });
        agregarBoton(panel, "Ver estadisticas", 6, new Runnable() {
            public void run() {
                mostrarEstadisticas();
            }
        });
        agregarBoton(panel, "Buscar por ID", 7, new Runnable() {
            public void run() {
                buscarPorId();
            }
        });
        agregarBoton(panel, "Ordenar por monto", 8, new Runnable() {
            public void run() {
                mostrarOrdenadosPorMonto();
            }
        });
        agregarBoton(panel, "Ultima accion", 9, new Runnable() {
            public void run() {
                mostrarUltimaAccion();
            }
        });

        JButton transferencia = new JButton("Transferencia");
        transferencia.setEnabled(false);
        transferencia.setToolTipText("El proyecto actual no implementa transferencias.");
        panel.add(transferencia, restriccionesBoton(10));

        GridBagConstraints relleno = new GridBagConstraints();
        relleno.gridx = 0;
        relleno.gridy = 11;
        relleno.weighty = 1;
        panel.add(new JPanel(), relleno);
        return panel;
    }

    private JPanel crearPanelResumen() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 224, 232)),
                BorderFactory.createEmptyBorder(24, 24, 24, 24)));

        JLabel titulo = new JLabel("Resumen general");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(28, 37, 54));

        etiquetaIngresos = crearDatoResumen("Ingresos");
        etiquetaGastos = crearDatoResumen("Gastos");
        etiquetaMovimientos = crearDatoResumen("Movimientos");

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 0, 18, 0);
        panel.add(titulo, c);

        JPanel tarjetas = new JPanel(new GridLayout(1, 3, 12, 12));
        tarjetas.setOpaque(false);
        tarjetas.add(crearTarjeta("Total ingresos", etiquetaIngresos));
        tarjetas.add(crearTarjeta("Total gastos", etiquetaGastos));
        tarjetas.add(crearTarjeta("Movimientos", etiquetaMovimientos));

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        panel.add(tarjetas, c);

        JButton historial = new JButton("Abrir historial de movimientos");
        historial.addActionListener(e -> mostrarHistorialMovimientos());
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(22, 0, 0, 0);
        panel.add(historial, c);

        return panel;
    }

    private JLabel crearDatoResumen(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Segoe UI", Font.BOLD, 20));
        etiqueta.setForeground(new Color(28, 37, 54));
        return etiqueta;
    }

    private JPanel crearTarjeta(String titulo, JLabel valor) {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(new Color(248, 250, 252));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 224, 232)),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));

        JLabel etiqueta = new JLabel(titulo);
        etiqueta.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        etiqueta.setForeground(new Color(89, 99, 118));
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(valor, BorderLayout.CENTER);
        return panel;
    }

    private void agregarBoton(JPanel panel, String texto, int fila, final Runnable accion) {
        JButton boton = new JButton(texto);
        boton.addActionListener(e -> accion.run());
        panel.add(boton, restriccionesBoton(fila));
    }

    private GridBagConstraints restriccionesBoton(int fila) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = fila;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.insets = new Insets(0, 0, 8, 0);
        return c;
    }

    private void mostrarDialogoMovimiento(String tipo) {
        FormularioMovimiento formulario = new FormularioMovimiento(this, tipo, false);
        formulario.setVisible(true);
        if (!formulario.fueAceptado()) {
            return;
        }
        try {
            String categoria = formulario.obtenerCategoria();
            int mes = formulario.obtenerMes();
            gestor.registrarMovimiento(tipo, categoria, formulario.obtenerMonto(),
                    mes, formulario.obtenerDescripcion());
            actualizarVista();
            if (Transaccion.TIPO_GASTO.equals(tipo) && gestor.superaLimiteMensual(categoria, mes)) {
                double gastado = gestor.obtenerGastoPorCategoriaYMes(categoria, mes);
                double limite = gestor.buscarCategoria(categoria, Transaccion.TIPO_GASTO).obtenerLimiteMensual();
                JOptionPane.showMessageDialog(this,
                        "Movimiento registrado.\n\nAtencion: la categoria '" + categoria + "' en "
                                + obtenerNombreMes(mes) + " lleva S/ " + MONEDA.format(gastado)
                                + " y supero su limite mensual de S/ " + MONEDA.format(limite) + ".",
                        "Limite superado", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Movimiento registrado correctamente.");
            }
        } catch (IllegalArgumentException error) {
            mostrarError(error.getMessage());
        }
    }

    private void mostrarDialogoPagoPendiente() {
        FormularioMovimiento formulario = new FormularioMovimiento(this, Transaccion.TIPO_GASTO, true);
        formulario.setVisible(true);
        if (!formulario.fueAceptado()) {
            return;
        }
        try {
            gestor.encolarGastoPendiente(formulario.obtenerCategoria(), formulario.obtenerMonto(),
                    formulario.obtenerMes(), formulario.obtenerDescripcion());
            JOptionPane.showMessageDialog(this, "Pago pendiente registrado en la cola.");
        } catch (IllegalArgumentException error) {
            mostrarError(error.getMessage());
        }
    }

    private void procesarPagoPendiente() {
        if (!gestor.hayPagosPendientes()) {
            JOptionPane.showMessageDialog(this, "No hay pagos pendientes.");
            return;
        }
        gestor.procesarGastoPendiente();
        actualizarVista();
        JOptionPane.showMessageDialog(this, "Se proceso el siguiente pago pendiente de la cola.");
    }

    private void mostrarUltimaAccion() {
        final JDialog dialogo = crearDialogo("Ultima accion", 700, 260);
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        dialogo.setContentPane(panel);

        final DefaultTableModel modelo = new DefaultTableModel(new Object[] { "Ultimo movimiento registrado" }, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        cargarUltimoMovimiento(modelo);
        JTable tabla = crearTabla(modelo);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton eliminar = new JButton("Eliminar ultimo movimiento");
        eliminar.addActionListener(e -> {
            if (!gestor.hayUltimoMovimiento()) {
                mostrarError("No hay movimientos para eliminar.");
                return;
            }
            int respuesta = JOptionPane.showConfirmDialog(dialogo,
                    "Desea eliminar el ultimo movimiento registrado?",
                    "Eliminar ultimo movimiento", JOptionPane.YES_NO_OPTION);
            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }
            String eliminado = gestor.eliminarUltimoMovimiento();
            cargarUltimoMovimiento(modelo);
            actualizarVista();
            if (eliminado != null) {
                JOptionPane.showMessageDialog(dialogo, "Se elimino: " + eliminado);
            }
        });

        JPanel acciones = new JPanel(new BorderLayout());
        acciones.add(eliminar, BorderLayout.WEST);
        panel.add(acciones, BorderLayout.SOUTH);

        dialogo.setVisible(true);
    }

    private void cargarUltimoMovimiento(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        modelo.addRow(new Object[] { gestor.obtenerDescripcionUltimoMovimiento() });
    }

    private void mostrarHistorialMovimientos() {
        final JDialog dialogo = crearDialogo("Historial de movimientos", 860, 480);
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        dialogo.setContentPane(panel);

        final DefaultTableModel modelo = crearModeloMovimientos();
        cargarMovimientos(modelo, false);
        final JTable tabla = crearTabla(modelo);

        JButton eliminar = new JButton("Eliminar seleccionado");
        eliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) {
                mostrarError("Seleccione un movimiento del historial.");
                return;
            }
            int filaModelo = tabla.convertRowIndexToModel(fila);
            int id = ((Integer) modelo.getValueAt(filaModelo, 0)).intValue();
            int respuesta = JOptionPane.showConfirmDialog(dialogo,
                    "Desea eliminar el movimiento #" + id + "?",
                    "Eliminar movimiento", JOptionPane.YES_NO_OPTION);
            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }
            if (gestor.eliminarMovimientoPorId(id)) {
                cargarMovimientos(modelo, false);
                actualizarVista();
            } else {
                mostrarError("No se encontro el movimiento seleccionado.");
            }
        });

        JPanel acciones = new JPanel(new BorderLayout());
        acciones.add(eliminar, BorderLayout.WEST);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panel.add(acciones, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }

    private void mostrarCategorias() {
        JDialog dialogo = crearDialogo("Categorias", 760, 440);
        JPanel panel = new JPanel(new GridLayout(1, 2, 12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        dialogo.setContentPane(panel);

        panel.add(crearPanelCategorias("Ingresos", Transaccion.TIPO_INGRESO));
        panel.add(crearPanelCategorias("Gastos", Transaccion.TIPO_GASTO));
        dialogo.setVisible(true);
    }

    private JPanel crearPanelCategorias(String titulo, String tipo) {
    JPanel panel = new JPanel(new BorderLayout(0, 8));
    JLabel etiqueta = new JLabel(titulo);
    etiqueta.setFont(new Font("Segoe UI", Font.BOLD, 16));
    panel.add(etiqueta, BorderLayout.NORTH);

    final boolean esGasto = Transaccion.TIPO_GASTO.equals(tipo);
    final int mesActual = Calendar.getInstance().get(Calendar.MONTH) + 1;

    Object[] columnas = esGasto
            ? new Object[] { "Categoria", "Limite mensual", "Gastado (" + obtenerNombreMes(mesActual) + ")", "Estado" }
            : new Object[] { "Categoria", "Limite mensual" };

    final DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    gestor.recorrerCategoriasPorTipo(tipo, categoria -> agregarCategoriaATabla(modelo, categoria, esGasto, mesActual));

    JTable tabla = crearTabla(modelo);
    if (esGasto) {
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public java.awt.Component getTableCellRendererComponent(JTable tabla, Object valor,
                    boolean seleccionado, boolean foco, int fila, int columna) {
                java.awt.Component componente = super.getTableCellRendererComponent(tabla, valor, seleccionado,
                        foco, fila, columna);
                Object estado = tabla.getModel().getValueAt(fila, tabla.getColumnCount() - 1);
                boolean excedido = "Excedido".equals(estado);
                if (!seleccionado) {
                    componente.setBackground(excedido ? new Color(252, 220, 220) : Color.WHITE);
                    setForeground(excedido ? new Color(160, 30, 30) : Color.BLACK);
                }
                return componente;
            }
        });
    }
    panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
    return panel;
    }

    private void agregarCategoriaATabla(DefaultTableModel modelo, Categoria categoria, boolean esGasto, int mesActual) {
        if (!esGasto) {
            modelo.addRow(new Object[] {
                    categoria.obtenerNombre(),
                    "S/ " + MONEDA.format(categoria.obtenerLimiteMensual())
            });
            return;
        }
        double limite = categoria.obtenerLimiteMensual();
        double gastado = gestor.obtenerGastoPorCategoriaYMes(categoria.obtenerNombre(), mesActual);
        String estado;
        if (limite <= 0) {
            estado = "Sin limite";
        } else if (gastado > limite) {
            estado = "Excedido";
        } else {
            estado = "OK";
        }
        modelo.addRow(new Object[] {
                categoria.obtenerNombre(),
                "S/ " + MONEDA.format(limite),
                "S/ " + MONEDA.format(gastado),
                estado
        });
    }

    private void mostrarEstadisticas() {
        JDialog dialogo = crearDialogo("Estadisticas", 680, 520);
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        dialogo.setContentPane(panel);

        JLabel resumen = new JLabel("Saldo: S/ " + MONEDA.format(gestor.obtenerSaldoActual())
                + "   |   Ingresos: S/ " + MONEDA.format(gestor.obtenerTotalIngresos())
                + "   |   Gastos: S/ " + MONEDA.format(gestor.obtenerTotalGastos()));
        resumen.setFont(new Font("Segoe UI", Font.BOLD, 15));

        DefaultTableModel modelo = new DefaultTableModel(new Object[] { "Mes", "Ingresos", "Gastos" }, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (int mes = 1; mes <= 12; mes++) {
            modelo.addRow(new Object[] {
                    obtenerNombreMes(mes),
                    "S/ " + MONEDA.format(gestor.obtenerMontoMensual(mes, Transaccion.TIPO_INGRESO)),
                    "S/ " + MONEDA.format(gestor.obtenerMontoMensual(mes, Transaccion.TIPO_GASTO))
            });
        }

        panel.add(resumen, BorderLayout.NORTH);
        panel.add(new JScrollPane(crearTabla(modelo)), BorderLayout.CENTER);
        dialogo.setVisible(true);
    }

    private void buscarPorId() {
        Integer id = pedirEntero("Buscar movimiento", "ID a buscar:");
        if (id == null) {
            return;
        }
        Transaccion encontrado = gestor.buscarPorId(id);

        JDialog dialogo = crearDialogo("Resultado de busqueda", 700, 220);
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        dialogo.setContentPane(panel);

        DefaultTableModel modelo = crearModeloMovimientos();
        if (encontrado != null) {
            agregarMovimiento(modelo, encontrado);
        }
        panel.add(new JScrollPane(crearTabla(modelo)), BorderLayout.CENTER);

        if (encontrado == null) {
            JLabel aviso = new JLabel("No se encontro ningun movimiento con ID " + id + ".");
            aviso.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            panel.add(aviso, BorderLayout.NORTH);
        }

        dialogo.setVisible(true);
    }

    private void mostrarOrdenadosPorMonto() {
        JDialog dialogo = crearDialogo("Movimientos ordenados por monto", 860, 480);
        DefaultTableModel modelo = crearModeloMovimientos();
        cargarMovimientos(modelo, true);
        dialogo.setContentPane(new JScrollPane(crearTabla(modelo)));
        dialogo.setVisible(true);
    }

    private void mostrarTextoEnVentana(String titulo, String texto) {
        JDialog dialogo = crearDialogo(titulo, 620, 380);
        JTextArea area = new JTextArea(texto);
        area.setEditable(false);
        area.setFont(new Font("Consolas", Font.PLAIN, 13));
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dialogo.setContentPane(new JScrollPane(area));
        dialogo.setVisible(true);
    }

    private JDialog crearDialogo(String titulo, int ancho, int alto) {
        JDialog dialogo = new JDialog(this, titulo, false);
        dialogo.setSize(ancho, alto);
        dialogo.setLocationRelativeTo(this);
        return dialogo;
    }

    private JTable crearTabla(DefaultTableModel modelo) {
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(28);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setFillsViewportHeight(true);
        return tabla;
    }

    private DefaultTableModel crearModeloMovimientos() {
        return new DefaultTableModel(new Object[] {
                "ID", "Mes", "Tipo", "Categoria", "Monto", "Descripcion"
        }, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private void cargarMovimientos(final DefaultTableModel modelo, boolean ordenadosPorMonto) {
        modelo.setRowCount(0);
        if (ordenadosPorMonto) {
            gestor.recorrerMovimientosOrdenadosPorMonto(valor -> agregarMovimiento(modelo, valor));
        } else {
            gestor.recorrerMovimientos(valor -> agregarMovimiento(modelo, valor));
        }
    }

    private void agregarMovimiento(DefaultTableModel modelo, Transaccion valor) {
        modelo.addRow(new Object[] {
                valor.obtenerId(),
                obtenerNombreMes(valor.obtenerMes()),
                valor.obtenerTipo(),
                valor.obtenerCategoria(),
                "S/ " + MONEDA.format(valor.obtenerMonto()),
                valor.obtenerDescripcion()
        });
    }

    private void actualizarVista() {
        etiquetaSaldo.setText("Saldo actual: S/ " + MONEDA.format(gestor.obtenerSaldoActual()));
        etiquetaIngresos.setText("S/ " + MONEDA.format(gestor.obtenerTotalIngresos()));
        etiquetaGastos.setText("S/ " + MONEDA.format(gestor.obtenerTotalGastos()));
        final int[] cantidad = new int[] { 0 };
        gestor.recorrerMovimientos(valor -> cantidad[0]++);
        etiquetaMovimientos.setText(String.valueOf(cantidad[0]));
    }

    private DefaultComboBoxModel<String> crearModeloCategorias(String tipo) {
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<String>();
        gestor.recorrerCategoriasPorTipo(tipo, categoria -> agregarCategoriaACombo(modelo, categoria));
        return modelo;
    }

    private void agregarCategoriaACombo(DefaultComboBoxModel<String> modelo, Categoria categoria) {
        modelo.addElement(categoria.obtenerNombre());
    }

    private String obtenerNombreMes(int mes) {
        if (mes < 1 || mes > MESES.length) {
            return "Mes " + mes;
        }
        return MESES[mes - 1];
    }

    private Integer pedirEntero(String titulo, String mensaje) {
        String texto = JOptionPane.showInputDialog(this, mensaje, titulo, JOptionPane.PLAIN_MESSAGE);
        if (texto == null) {
            return null;
        }
        try {
            return Integer.valueOf(texto.trim());
        } catch (NumberFormatException error) {
            mostrarError("Ingrese un numero entero valido.");
            return null;
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    private class FormularioMovimiento extends JDialog {
        private JComboBox<String> categoria;
        private JTextField monto;
        private JComboBox<String> mes;
        private JTextField descripcion;
        private boolean aceptado;

        FormularioMovimiento(JFrame owner, String tipo, boolean pendiente) {
            super(owner, pendiente ? "Registrar pago pendiente" : "Registrar " + tipo.toLowerCase(), true);
            construir(tipo);
            pack();
            setResizable(false);
            setLocationRelativeTo(owner);
        }

        private void construir(String tipo) {
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
            setContentPane(panel);

            categoria = new JComboBox<String>(crearModeloCategorias(tipo));
            categoria.setEditable(false);
            monto = new JTextField(14);
            mes = new JComboBox<String>(MESES);
            mes.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));
            descripcion = new JTextField(22);

            agregarCampo(panel, "Tipo:", new JLabel(tipo), 0);
            agregarCampo(panel, "Categoria:", categoria, 1);
            agregarCampo(panel, "Monto:", monto, 2);
            agregarCampo(panel, "Mes:", mes, 3);
            agregarCampo(panel, "Descripcion:", descripcion, 4);

            JPanel botones = new JPanel();
            JButton cancelar = new JButton("Cancelar");
            JButton guardar = new JButton("Guardar");
            cancelar.addActionListener(e -> dispose());
            guardar.addActionListener(e -> {
                if (validarCampos()) {
                    aceptado = true;
                    dispose();
                }
            });
            botones.add(cancelar);
            botones.add(guardar);

            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 5;
            c.gridwidth = 2;
            c.anchor = GridBagConstraints.EAST;
            c.insets = new Insets(10, 0, 0, 0);
            panel.add(botones, c);
        }

        private void agregarCampo(JPanel panel, String etiqueta, java.awt.Component componente, int fila) {
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = fila;
            c.anchor = GridBagConstraints.WEST;
            c.insets = new Insets(4, 0, 4, 10);
            panel.add(new JLabel(etiqueta), c);

            c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = fila;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 1;
            c.insets = new Insets(4, 0, 4, 0);
            panel.add(componente, c);
        }

        boolean fueAceptado() {
            return aceptado;
        }

        String obtenerCategoria() {
            Object seleccion = categoria.getSelectedItem();
            return seleccion == null ? "" : seleccion.toString().trim();
        }

        double obtenerMonto() {
            return Double.parseDouble(monto.getText().trim());
        }

        int obtenerMes() {
            return mes.getSelectedIndex() + 1;
        }

        String obtenerDescripcion() {
            return descripcion.getText().trim();
        }

        private boolean validarCampos() {
            if (obtenerCategoria().isEmpty()) {
                mostrarAvisoFormulario("Seleccione una categoria.");
                categoria.requestFocus();
                return false;
            }
            if (monto.getText().trim().isEmpty()) {
                mostrarAvisoFormulario("Ingrese el monto.");
                monto.requestFocus();
                return false;
            }
            try {
                double valor = Double.parseDouble(monto.getText().trim());
                if (valor <= 0) {
                    mostrarAvisoFormulario("El monto debe ser mayor que cero.");
                    monto.requestFocus();
                    return false;
                }
            } catch (NumberFormatException error) {
                mostrarAvisoFormulario("Ingrese un monto valido.");
                monto.requestFocus();
                return false;
            }
            if (obtenerDescripcion().isEmpty()) {
                mostrarAvisoFormulario("Ingrese una descripcion.");
                descripcion.requestFocus();
                return false;
            }
            return true;
        }

        private void mostrarAvisoFormulario(String mensaje) {
            JOptionPane.showMessageDialog(this, mensaje, "Complete los campos", JOptionPane.WARNING_MESSAGE);
        }
    }
}
