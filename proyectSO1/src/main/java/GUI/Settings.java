/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import DAO.Methods;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author asmal
 */
public class Settings extends JDialog{
    private JTextField ssdField;
    private JTextField ramField;
    private boolean ssdValidated;
    private boolean ramValidated;

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
                Methods methods = new Methods();
                if (!ssdField.getText().equals("")) {
                    if (methods.isInt(ssdField.getText())) {
                        validateSizeSSD(ssdField.getText());
                    }
                    else {
                        String message = "ERROR DE CONFIGURACION [VALORES SSD INVALIDOS]";
                        JOptionPane.showMessageDialog(new JFrame(), message, "ERROR", JOptionPane.ERROR_MESSAGE);
                        ssdValidated = false;
                    }
                }
                else {
                    ssdValidated = true;
                }
                
                if (!ramField.getText().equals("")) {
                    if (methods.isInt(ramField.getText())) {
                        validateSizeRam(ramField.getText());
                    }
                    else {
                        String message = "ERROR DE CONFIGURACION [VALORES RAM INVALIDOS]";
                        JOptionPane.showMessageDialog(new JFrame(), message, "ERROR", JOptionPane.ERROR_MESSAGE);
                        ssdValidated = false;
                    }
                }
                else {
                    ramValidated = true;
                }
                
                validateMemories();
                
                
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
    public void validateSizeSSD(String num) {
        if (Integer.parseInt(num)<512) {
            String message = "ERROR DE CONFIGURACION [SSD>512]";
            JOptionPane.showMessageDialog(new JFrame(), message, "ERROR", JOptionPane.ERROR_MESSAGE);
            ssdValidated = false;
        }
        else {
            ssdValidated = true;
        }
    }
    
    public void validateSizeRam(String num) {
        if (Integer.parseInt(num)<256) {
            String message = "ERROR DE CONFIGURACION [RAM>256]";
            JOptionPane.showMessageDialog(new JFrame(), message, "ERROR", JOptionPane.ERROR_MESSAGE);
            ssdValidated = false;
        }
        else{
            ssdValidated = true;
        }
    }
    
    public void validateMemories() {
        if (ssdValidated & ramValidated)
            dispose();
    }
}
