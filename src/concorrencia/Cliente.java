/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concorrencia;

import concorrencia.Cliente;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JOptionPane;
/**
 *
 * @author nicho
 */
public class Cliente {

    private String hostAdrress;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    private ClientGUI clienteGUI;

    /**
     * Construtor utilizado pelo Servidor para capturar e criar um cliente
     *
     * @param socket
     * @throws IOException
     */
    public Cliente(Socket socket) throws IOException {
        this.socket = socket;
        this.hostAdrress = socket.getInetAddress().getHostAddress();
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
    }

    /**
     * Construtor utilizado para capturar a conexáo realizada pelo programa
     * cliente
     *
     * @param clientGUI Formulário do cliente
     * @param host IP do servidor
     * @param port Porta do servidor
     * @throws IOException
     */
    public Cliente(ClientGUI clientGUI, String host, Integer port) throws IOException {
        this(new Socket(host, port));
        this.clienteGUI = clientGUI;
        // inicia a thread para aguardar as respostas do servidor
        waitForServerResponse();
        mostrarMensagem("Conexão realizada com sucesso!");
    }

    public void mostrarMensagem(String msg) {
        JOptionPane.showMessageDialog(clienteGUI,
                msg, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Mostra a mensagem de erro capturada na exceção
     *
     * @param erro Mensagem do erro
     * @param tipoErro Tipo do erro (IOException, UnknowHost....)
     */
    public void mostrarErro(String erro, String tipoErro) {
        JOptionPane.showMessageDialog(clienteGUI,
                erro, tipoErro, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Desabilita os campos do cliente
     */
    public void disableClientGUI() {
        clienteGUI.enableComponents(true);
    }

    public boolean estaConectado() {
        return !socket.isClosed();
    }

    public void desconectar() throws IOException {
        inputStream.close();
        outputStream.close();
        socket.close();
        mostrarMensagem("Desconexão realizada com sucesso!");
        clienteGUI.enableComponents(true);
    }

    private void waitForServerResponse() throws IOException {
        // Thread para receberr mensagens do servidor
        Recebedor r = new Recebedor(this);
        new Thread(r).start();
    }

    /**
     * Solicita a leitura do arquivo no servidor.
     *
     * @throws IOException
     */
    public void lerArquivo() throws IOException {
        PrintStream ps = new PrintStream(this.outputStream);
        ps.println("lerArquivo");
    }

    /**
     * Solicita que os dados digitados no cliente sejam gravados no arquivo de
     * texto do servidor
     *
     * @throws IOException
     */
    public void gravarArquivo() throws IOException {
        PrintStream ps = new PrintStream(this.outputStream);
        ps.println("gravarArquivo");
        ps.println(clienteGUI.getTextoAreaLocal());
        /**
         * Imprime um texto para indicar o final da transmissão dos dados a
         * serem gravados no arquivo
         */
        ps.println("---ENDWRITE---");
        mostrarMensagem("Texto enviado com sucesso!");
    }

    public void executaComando(Cliente cliente) throws IOException {
        // recebe as mensagens do servidor e as imprime na tela
        Scanner s = new Scanner(cliente.getInputStream());
        String sData = "";
        boolean newData = false;
        while (s.hasNextLine()) {
            sData = s.nextLine();
            switch (sData) {
                case "---DISCONNECTED---":
                    cliente.desconectar();
                    cliente.mostrarMensagem(
                            "Cliente desconectado pelo servidor");
                    break;
                case "---NEWDATA---":
                    /**
                     * Indica que o cliente está recebendo um novo texto. Logo,
                     * limpa a area de text na primeira leitura
                     */
                    clienteGUI.clearAreaTextoServidor();
                    newData = true;
                    break;
                case "---ENDDATA---":
                    cliente.mostrarMensagem("Arquivo lido e atualizado "
                            + "no campo com sucesso!");
                    newData = false;
                    break;
                default:
                    if (newData) {
                        // Adiciona o texto capturado do servidor.
                        clienteGUI.updateAreaTextoServidor(sData + "\n");
                    }
            }
        }
    }

    public OutputStream getOutputStream() {
        return this.outputStream;
    }

    public InputStream getInputStream() {
        return this.inputStream;
    }

    public String getHost() {
        return this.hostAdrress;
    }

    @Override
    public String toString() {
        return this.hostAdrress;
    }

}
