/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concorrencia;

import concorrencia.Cliente;
import java.io.IOException;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author nicho
 */
public class TrataCliente implements Runnable {

    private Cliente cliente;
    private Servidor servidor;

    public TrataCliente(Cliente cliente, Servidor servidor) {
        this.cliente = cliente;
        this.servidor = servidor;
    }

    @Override
    public void run() {
        try {
            servidor.executarComando(cliente);
            /**
             * Ao finalizar a execução dos comandos,
             * subentente-se que o cliente finalizou a conexão.
             * Logo, ele deve ser removido da lista.
             */
            servidor.removeCliente(cliente);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(servidor.getGUI(), 
                    ex.getMessage() + " / " + ex.getCause().getMessage(), 
                    "Erro de I/O Server", JOptionPane.ERROR_MESSAGE);
        }
    }

}
