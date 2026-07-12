package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class LoginVentana extends JFrame {
    private JTextField campoCorreo;
    private JPasswordField campoContrasena;

    public LoginVentana() {
        configurarVentana();
        construirInterfaz();
    }

    private void configurarVentana() {
        setTitle("Acceso - Finanzas Personales");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(430, 320));
        setLocationRelativeTo(null);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
    }

    private void construirInterfaz() {
        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
        raiz.setBackground(new Color(245, 247, 250));
        setContentPane(raiz);

        JLabel titulo = new JLabel("Finanzas Personales");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(new Color(28, 37, 54));
        raiz.add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(Color.WHITE);
        formulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 224, 232)),
                BorderFactory.createEmptyBorder(22, 22, 22, 22)));
        raiz.add(formulario, BorderLayout.CENTER);

        campoCorreo = new JTextField(24);
        campoContrasena = new JPasswordField(24);

        agregarCampo(formulario, "Correo personal:", campoCorreo, 0);
        agregarCampo(formulario, "Contrasena:", campoContrasena, 1);

        JButton ingresar = new JButton("Ingresar");
        ingresar.addActionListener(e -> validarIngreso());
        getRootPane().setDefaultButton(ingresar);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(14, 0, 0, 0);
        formulario.add(ingresar, c);
    }

    private void agregarCampo(JPanel panel, String etiqueta, java.awt.Component componente, int fila) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = fila;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(6, 0, 6, 12);
        panel.add(new JLabel(etiqueta), c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = fila;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.insets = new Insets(6, 0, 6, 0);
        panel.add(componente, c);
    }

    private void validarIngreso() {
        String correo = campoCorreo.getText().trim();
        String contrasena = new String(campoContrasena.getPassword()).trim();

        if (!esCorreoValido(correo)) {
            mostrarAviso("Ingrese un correo personal valido.");
            return;
        }
        if (contrasena.isEmpty()) {
            mostrarAviso("Ingrese una contrasena.");
            return;
        }

        new VentanaFinanzas().setVisible(true);
        dispose();
    }

    private boolean esCorreoValido(String correo) {
        int arroba = correo.indexOf('@');
        int punto = correo.lastIndexOf('.');
        return arroba > 0 && punto > arroba + 1 && punto < correo.length() - 1;
    }

    private void mostrarAviso(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Acceso", JOptionPane.WARNING_MESSAGE);
    }
}
