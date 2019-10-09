/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concorrencia;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author nicho
 */
public class Recebedor implements Runnable {

    private Cliente cliente;

    public Recebedor(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        try {
            cliente.executaComando(cliente);
        } catch (IOException ex) {
            cliente.mostrarErro("Erro ao desconectar cliente", 
                                "I/O Exception");
        }
    }

}
