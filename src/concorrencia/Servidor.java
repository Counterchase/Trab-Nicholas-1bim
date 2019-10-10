/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concorrencia;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.ClienteController;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.ClienteAtualizarDialog;

/**
 *
 * @author nicho
 */
public class Servidor {

    private List<Cliente> listaIpClientes;
    private ServerSocket serverSocket;
    private File textFile;

    Gson gson = new Gson();
    Type clienteType = new TypeToken<model.Cliente>() {
    }.getType();
    Type clienteTypeLista = new TypeToken<List<model.Cliente>>() {
    }.getType();

    public Servidor(Integer porta, String pathFile) throws IOException {
        serverSocket = new ServerSocket(porta);
        this.textFile = new File(pathFile);
        this.listaIpClientes = new ArrayList<>();
//        initServerMonitor();
    }

    private void initServerMonitor() throws UnknownHostException, IOException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String msg = String.format("[%s] Servidor iniciado\n"
                + "IP do Servidor: %s\n",
                sdf.format(cal.getTime()),
                InetAddress.getLocalHost().getHostAddress());
    }

    public void fechar() throws IOException {
        if (listaIpClientes.size() > 0) {
            /**
             * Primeiro encaminha a solicitação de encerramento de conexão aos
             * clientes
             */
            Iterator<Cliente> it = listaIpClientes.iterator();
            while (it.hasNext()) {
                Cliente cliente = it.next();
                if (cliente.estaConectado()) {
                    PrintStream ps = new PrintStream(cliente.getOutputStream());
                    /**
                     * Força a desconexão pelo cliente
                     */
                    ps.println("---DISCONNECTED---");
                }
            }
        }
        serverSocket.close();
    }

    public void removeCliente(Cliente cliente) {
        listaIpClientes.remove(cliente);
    }

    public String lerArquivo() throws FileNotFoundException, IOException {
        if (textFile.canRead()) {
            FileReader fileRead = new FileReader(textFile);
            StringBuilder sb;
            try (BufferedReader lerArquivo = new BufferedReader(fileRead)) {
                sb = new StringBuilder();
                // lê a primeira linha
                String linha = lerArquivo.readLine();
                do {
                    sb.append(linha);
                    sb.append("\n");
                    linha = lerArquivo.readLine();
                } while (linha != null);
            }
            return sb.toString();
        }
        throw new IOException("Arquivo não pode ser lido!");
    }

    private void escreverArquivo(String texto) throws IOException {
        if (textFile.canWrite()) {
            try (FileWriter fileWrite = new FileWriter(textFile)) {
                PrintWriter printWrite = new PrintWriter(fileWrite);
                printWrite.print(texto);
            }
        } else {
            throw new IOException("Arquivo não pode ser escrito!");
        }
    }

    /**
     * Aguarda as conexões dos cliente e executa suas operações
     *
     * @throws IOException
     * @throws java.net.SocketException
     */
    public void executa() throws IOException, SocketException {
        while (true) {
            // aceita um cliente
            Socket clientSocket = serverSocket.accept();
            Cliente cliente = new Cliente(clientSocket);
            // Registra o cliente no servidor

            // cria o tratador de cliente em uma nova thread
            TrataCliente tc = new TrataCliente(cliente, this);
            Thread t = new Thread(tc);
            t.start();
        }
    }

    public void executarComando(Cliente cliente) throws IOException, SQLException, ClassNotFoundException {
        // Identifica o comando a ser executado
        Scanner s = new Scanner(cliente.getInputStream());
        ClienteController cc = new ClienteController();
        PrintStream ps;
        String conteudo;
        boolean isToWriteFile = false;
        StringBuilder sb = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String comando = "";
        while (s.hasNextLine()) {
            comando = s.nextLine().trim();
            switch (comando) {
                //esse metodo envia pro cliente as coisas
                case "lerArquivo":
                    // Lê os dados do arquivo de texto
                    // E envia para o cliente os dados pelo fluxo de comunicação
                    conteudo = lerArquivo();
                    // Captura o fluxo de saída para envio de dados
                    ps = new PrintStream(cliente.getOutputStream());
                    /**
                     * Envia mensagem informando que um conjunto de dados será
                     * enviado
                     */
                    ps.println("---NEWDATA---");
                    // envia o conteúdo do arquivo para o cliente
                    ps.println(conteudo);
                    /**
                     * Envia mensagem informando os dados já foram enviados
                     */
                    ps.println("---ENDDATA---");
                    break;
                //esse metodo habilita a gravação do que foi recebido pelo servidor
                case "gravarArquivo":
                    isToWriteFile = true;
                    break;

                /**
                 * Indica que a transmissão dos dados do arquivo já finalizaram.
                 */
                case "---ENDWRITE---":
                    escreverArquivo(sb.toString());

                    //método que grava o cliente recebido no banco
                    System.out.println(sb.toString());
                    model.Cliente c = gson.fromJson(sb.toString(), clienteType);
                    try {
                        cc.salvar(c);
                    } catch (SQLException | NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                    // adicoina o conteúdo do arquivo na área de texto
                    isToWriteFile = false;
                    /**
                     * cria a classe StringBuilder para recomeçar a escrita do
                     * texto no arquivo a ser enviado para o cliente
                     */
                    sb = new StringBuilder();
                    break;
                case "---SENDLIST---":
                    //Gera a lista de clientes com base numa consulta no banco
                    conteudo = gson.toJson(cc.listar(), clienteTypeLista);
                    // Captura o fluxo de saída para envio de dados
                    ps = new PrintStream(cliente.getOutputStream());
                    /**
                     * Envia mensagem informando que um conjunto de dados será
                     * enviado
                     */
                    ps.println("---NEWDATA---");
                    // envia o conteúdo do arquivo para o cliente
                    ps.println(conteudo);
                    /**
                     * Envia mensagem informando os dados já foram enviados
                     */
                    ps.println("---ENDDATA---");
                    break;
                default:
                    if (isToWriteFile) {
                        // captura o texto enviado pelo cliente
                        sb.append(comando);
                        sb.append("\n");
                    }
            }
        }
    }
}
