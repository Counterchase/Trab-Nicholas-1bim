/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concorrencia;

import concorrencia.Cliente;
import concorrencia.ClientGUI;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author nicho
 */
public class ClientGUI extends JFrame {

    private JTextField txtIp;
    private JSpinner spinPorta;
    private JButton btConectar;
    private JButton btDesconectar;
    private JButton btCopiar;

    private JTextArea areaTextoServidor;
    private JTextArea areaTextoLocal;
    private JButton btLerArquivo;
    private JButton btEnviarTexto;

    private Cliente cliente;
    private ButtonAction btAction;

    public ClientGUI() {
        super("Cliente");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel principal = new JPanel(new BorderLayout(5, 5));
        add(principal);

        btAction = new ButtonAction(this);
        principal.add(painelConexao(), BorderLayout.PAGE_START);
        principal.add(painelTexto(), BorderLayout.CENTER);
        enableComponents(true);

        pack();
        setVisible(true);
    }

    private JPanel painelConexao() {
        JPanel panel = new JPanel(new GridLayout());
        String msgErro = "";
        try {
            String sIp = InetAddress.getLocalHost().getHostAddress();
            JLabel lblIpcliente = new JLabel(String.format("IP local: %s", sIp));
            Font font = lblIpcliente.getFont().deriveFont(16.0f);
            lblIpcliente.setFont(font);
            JLabel lblServidor = new JLabel("Dados do servidor");
            lblServidor.setFont(font);
            JLabel lblIpservidor = new JLabel("IP do Servidor: ");
            lblIpservidor.setFont(font);
            JLabel lblPorta = new JLabel("Porta: ");
            lblPorta.setFont(font);

            txtIp = new JTextField(10);
            txtIp.setFont(font);
            txtIp.setText(sIp);
            spinPorta = new JSpinner(new SpinnerNumberModel(12345, 1, 65535, 1));
            spinPorta.setFont(font);
            spinPorta.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    if (key == KeyEvent.VK_ENTER) {
                        btConectar.doClick();
                    }
                }
            });
            btConectar = new JButton("Conectar");
            btConectar.setFont(font);
            btDesconectar = new JButton("Desconectar");
            btDesconectar.setFont(font);
            txtIp.addActionListener(btAction);

            btConectar.addActionListener(btAction);
            btDesconectar.addActionListener(btAction);

            panel.add(lblServidor, BorderLayout.PAGE_START);
            JPanel panelCentro = new JPanel(new GridLayout(0, 1, 5, 5));

            panelCentro.add(lblIpcliente);
            panelCentro.add(lblIpservidor);
            panelCentro.add(txtIp);
            panelCentro.add(lblPorta);
            panelCentro.add(spinPorta);
            panelCentro.add(btConectar);
            panelCentro.add(btDesconectar);

            panel.add(panelCentro, BorderLayout.CENTER);
        } catch (UnknownHostException ex) {
            msgErro = ex.getMessage();
        } finally {
            if (!"".equals(msgErro)) {
                JOptionPane.showMessageDialog(null, msgErro, "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return panel;
    }

    private JPanel painelTexto() {
        JLabel lblTexto = new JLabel("Texto em arquivo no Servidor");
        Font font = lblTexto.getFont().deriveFont(16.0f);
        lblTexto.setFont(font);

        JLabel lblTextoAlterado = new JLabel("Texto a enviar para o Servidor");
        lblTextoAlterado.setFont(font);

        areaTextoServidor = new JTextArea(10, 40);
        areaTextoServidor.setEditable(false);
        JScrollPane scrollTexto = new JScrollPane();
        scrollTexto.setViewportView(areaTextoServidor);

        areaTextoLocal = new JTextArea(10, 40);
        JScrollPane scrollAlterado = new JScrollPane();
        scrollAlterado.setViewportView(areaTextoLocal);

        btLerArquivo = new JButton("Ler arquivo no servidor");
        btLerArquivo.setFont(font);
        btLerArquivo.addActionListener(btAction);

        btCopiar = new JButton("Copiar texto");
        btCopiar.setFont(font);
        btCopiar.addActionListener(btAction);

        btEnviarTexto = new JButton("Enviar arquivo para o servidor");
        btEnviarTexto.setFont(font);
        btEnviarTexto.addActionListener(btAction);

        JPanel panelTexto = new JPanel(new BorderLayout(5, 5));
        panelTexto.add(lblTexto, BorderLayout.PAGE_START);
        panelTexto.add(scrollTexto, BorderLayout.CENTER);

        JPanel panelTextoAlterado = new JPanel(new BorderLayout(5, 5));
        panelTextoAlterado.add(lblTextoAlterado, BorderLayout.PAGE_START);
        panelTextoAlterado.add(scrollAlterado, BorderLayout.CENTER);

        JPanel panelButton = new JPanel(new FlowLayout());
        panelButton.add(btLerArquivo);
        panelButton.add(btCopiar);
        panelButton.add(btEnviarTexto);

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(panelTexto, BorderLayout.PAGE_START);
        panel.add(panelTextoAlterado, BorderLayout.CENTER);
        panel.add(panelButton, BorderLayout.PAGE_END);

        return panel;
    }

    public void enableComponents(boolean active) {
        txtIp.setEnabled(active);
        spinPorta.setEnabled(active);
        btConectar.setEnabled(active);

        btDesconectar.setEnabled(!active);
        areaTextoServidor.setEnabled(!active);
        areaTextoLocal.setEnabled(!active);
        btLerArquivo.setEnabled(!active);
        btEnviarTexto.setEnabled(!active);
        btCopiar.setEnabled(!active);
    }

    private void clearFields() {
        areaTextoServidor.setText("");
        areaTextoLocal.setText("");
    }

    public void clearAreaTextoServidor() {
        areaTextoServidor.setText("");
    }

    public void updateAreaTextoServidor(String texto) {
        areaTextoServidor.append(texto);
    }

    public String getTextoAreaLocal() {
        return areaTextoLocal.getText();
    }

    private class ButtonAction implements ActionListener {

        private ClientGUI clientGUI;

        public ButtonAction(ClientGUI clientGUI) {
            this.clientGUI = clientGUI;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            try {
                if (source == txtIp) {
                    spinPorta.requestFocusInWindow();
                } else if (source == btConectar) {
                    clearFields();
                    enableComponents(false);
                    cliente = new Cliente(clientGUI, txtIp.getText(),
                            (Integer) spinPorta.getValue());
                } else if (source == btDesconectar) {
                    cliente.desconectar();
                } else if (source == btLerArquivo) {
                    cliente.lerArquivo();
                } else if (source == btEnviarTexto) {
                    cliente.gravarArquivo();
                } else if (source == btCopiar) {
                    areaTextoLocal.setText(areaTextoServidor.getText());
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                        "Erro de I/O", JOptionPane.ERROR_MESSAGE);
                enableComponents(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                enableComponents(true);
            }
        }

    }

    public static void main(String args[]) {
        new ClientGUI();
    }

}
