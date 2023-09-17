/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author asmal
 */
public class Settings extends JDialog{
    private JTextField ssdField;
    private JTextField ramField;

    public Settings(JFrame parent) {
        super(parent, "Settings", true);
        setSize(500, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);

        // Configurar el diseño
        setLayout(new BorderLayout());
        JLabel title = new JLabel();
        title.setText("Settings");

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        JLabel ssdLabel = new JLabel("SSD:");
        ssdField = new JTextField();
        ssdField.setSize(60, 30);
        JLabel ramLabel = new JLabel("RAM:");
        ramField = new JTextField();
        inputPanel.add(title);
        inputPanel.add(ssdLabel);
        inputPanel.add(ssdField);
        inputPanel.add(ramLabel);
        inputPanel.add(ramField);

        JButton loadButton = new JButton("Load Conf File");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Coloca aquí la lógica para cargar la configuración
            }
        });

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Coloca aquí la lógica para guardar la configuración
                dispose(); // Cierra la ventana
            }
        });

        // Agregar componentes al diálogo
        add(inputPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public String getSSDValue() {
        return ssdField.getText();
    }

    public String getRAMValue() {
        return ramField.getText();
    }
}
