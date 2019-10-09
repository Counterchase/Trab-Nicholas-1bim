/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concorrencia;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author nicho
 */
public class ServerGUI extends JFrame {

    private JSpinner spinPorta;
    private JButton btAbrir;
    private JButton btFechar;
    private JTextArea areaMonitor;
    private JTextArea areaTexto;
    private JList listaHosts;

    private Servidor servidor;
    private Thread threadServer;

    public ServerGUI() {
        super("Servidor");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        try {
            JPanel principal = new JPanel(new BorderLayout(5, 5));
            add(principal);
            principal.add(painelConexao(), BorderLayout.PAGE_START);
            principal.add(painelMonitoramento(), BorderLayout.CENTER);

            clearFields();
            enableComponents(true);

            pack();
            setVisible(true);
        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "Erro de host desconhecido",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel painelConexao() throws UnknownHostException {
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        String sIp = InetAddress.getLocalHost().getHostAddress();
        JLabel lblIpservidor = new JLabel(String.format("IP: %s", sIp));
        Font font = lblIpservidor.getFont().deriveFont(16.0f);
        lblIpservidor.setFont(font);
        panel.add(lblIpservidor, BorderLayout.PAGE_START);

        JPanel cabecalhoCentro = new JPanel(
                new FlowLayout(FlowLayout.LEFT));
        panel.add(cabecalhoCentro, BorderLayout.CENTER);
        JLabel lblPorta = new JLabel("Porta: ");
        lblPorta.setFont(font);
        spinPorta = new JSpinner(new SpinnerNumberModel(12345, 0, 65535, 1));
        spinPorta.setFont(font);
        btAbrir = new JButton("Abrir");
        btAbrir.setFont(font);
        btFechar = new JButton("Fechar");
        btFechar.setFont(font);

        ButtonAction btAction = new ButtonAction(this);
        btAbrir.addActionListener(btAction);
        btFechar.addActionListener(btAction);
        spinPorta.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btAbrir.doClick();
                }
            }
        });

        cabecalhoCentro.add(lblPorta);
        cabecalhoCentro.add(spinPorta);
        cabecalhoCentro.add(btAbrir);
        cabecalhoCentro.add(btFechar);
        return panel;
    }

    private JPanel painelMonitoramento() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JLabel lblMonitor = new JLabel("Monitor de Mensagens");
        Font font = lblMonitor.getFont().deriveFont(16.0f);
        lblMonitor.setFont(font);
        JLabel lblArquivo = new JLabel("Conteúdo atual do arquivo");
        lblArquivo.setFont(font);
        JLabel lblHosts = new JLabel("Hosts conectados");
        lblHosts.setFont(font);

        areaMonitor = new JTextArea(10, 40);
        areaMonitor.setEditable(false);
        JScrollPane scrollMonitor = new JScrollPane();
        scrollMonitor.setViewportView(areaMonitor);

        areaTexto = new JTextArea(10, 40);
        areaTexto.setEditable(false);
        JScrollPane scrollArquivo = new JScrollPane();
        scrollArquivo.setViewportView(areaTexto);

        /**
         * Define o autoscroll da area de texto monitor e dos dados do arquivo
         * de texto
         */
        DefaultCaret monitorCaret = (DefaultCaret) areaMonitor.getCaret();
        monitorCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        DefaultCaret textoCaret = (DefaultCaret) areaTexto.getCaret();
        textoCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        /**
         * ***********************************************
         */

        listaHosts = new JList(new DefaultListModel());

        JScrollPane scrollHosts = new JScrollPane();
        scrollHosts.setViewportView(listaHosts);

        JPanel corpoInicioLinha = new JPanel(new BorderLayout(5, 5));
        panel.add(corpoInicioLinha, BorderLayout.LINE_START);
        JPanel corpoCentro = new JPanel(new BorderLayout(5, 5));
        panel.add(corpoCentro, BorderLayout.CENTER);
        JPanel corpoFimLinha = new JPanel(new BorderLayout(5, 5));
        panel.add(corpoFimLinha, BorderLayout.LINE_END);

        corpoInicioLinha.add(lblMonitor, BorderLayout.PAGE_START);
        corpoInicioLinha.add(scrollMonitor, BorderLayout.CENTER);
        corpoCentro.add(lblArquivo, BorderLayout.PAGE_START);
        corpoCentro.add(scrollArquivo, BorderLayout.CENTER);
        corpoFimLinha.add(lblHosts, BorderLayout.PAGE_START);
        corpoFimLinha.add(scrollHosts, BorderLayout.CENTER);
        return panel;
    }

    private void enableComponents(boolean active) {
        spinPorta.setEnabled(active);
        btAbrir.setEnabled(active);

        btFechar.setEnabled(!active);
        areaMonitor.setEnabled(!active);
        areaTexto.setEnabled(!active);
        listaHosts.setEnabled(!active);
    }

    private void clearFields() {
        areaTexto.setText("");
        areaMonitor.setText("");
        DefaultListModel model = (DefaultListModel) listaHosts.getModel();
        model.removeAllElements();
    }

    public void registerToMonitor(String msg) {
        areaMonitor.append(msg);
    }

    public void updateAreaTexto(String value) {
        areaTexto.setText(value);
    }

    public void addClienteIp(Cliente cliente) {
        DefaultListModel model = (DefaultListModel) listaHosts.getModel();
        model.addElement(cliente);
        /**
         * Atualiza automaticamente o scroll da lista
         */
        listaHosts.ensureIndexIsVisible(model.size() - 1);
    }

    public void removeClienteIp(Cliente cliente) {
        DefaultListModel model = (DefaultListModel) listaHosts.getModel();
        model.removeElement(cliente);
    }

    private class ButtonAction implements ActionListener {

        private ServerGUI serverGUI;

        public ButtonAction(ServerGUI serverGUI) {
            this.serverGUI = serverGUI;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            try {
                if (source == btAbrir) {
                    enableComponents(false);
                    clearFields();
                    servidor = new Servidor(
                            (Integer) spinPorta.getValue(),
                            "./src/resource/texto.txt");

                    // thread para aguardar as conexões dos clientes
                    threadServer = new Thread(() -> {
                        try {
                            servidor.executa();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(),
                                    "Erro de IO",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    });
                    threadServer.start();
                } else if (source == btFechar) {
                    servidor.fechar();
                    areaMonitor.append(String.format("Servidor encerrado as %s\n",
                            sdf.format(cal.getTime())));
                    enableComponents(true);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                        "Erro de IO", JOptionPane.ERROR_MESSAGE);
                enableComponents(true);
            }
        }

    }

    public static void main(String args[]) {
        new ServerGUI();
    }

}
