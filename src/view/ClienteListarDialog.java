/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import concorrencia.Servidor;
import controller.ClienteController;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import model.Cliente;

/**
 *
 * @author santos
 */
public class ClienteListarDialog extends javax.swing.JFrame {

    private final ClienteController controller;
    private List<Cliente> lista;

    private Servidor servidor;
    private Thread threadServer;
    private Thread threadRefresh;

    /**
     * Creates new form ClienteListarDialog
     */
    public ClienteListarDialog() throws ClassNotFoundException, SQLException {
        super("Lista de Clientes");
        controller = new ClienteController();
        lista = (List<Cliente>) controller.listar();
        initComponents();
    }

    private void refreshTable() {
        String sId = txtSearchCodigo.getText();
        Integer id = sId.matches("\\d+") ? Integer.parseInt(sId) : null;
        if (id != null) {
            Cliente cliente = controller.buscarPorId(id);
            lista.clear();
            lista.add(cliente);
        } else {
            lista = controller.listar(txtSearchNome.getText(),
                    txtSearchEmail.getText(), txtSearchCelular.getText());
        }
        tabela.setModel(new ClienteTableModel(lista));
        tabela.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        lblSearchCodigo = new javax.swing.JLabel();
        lblSearchNome = new javax.swing.JLabel();
        txtSearchCodigo = new javax.swing.JTextField();
        txtSearchNome = new javax.swing.JTextField();
        btPesquisar = new javax.swing.JButton();
        lblEmail = new javax.swing.JLabel();
        lblCelular = new javax.swing.JLabel();
        txtSearchEmail = new javax.swing.JTextField();
        txtSearchCelular = new javax.swing.JTextField();
        btnAbrirSocket = new javax.swing.JToggleButton();
        btnRefreshTable = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        btFechar = new javax.swing.JButton();
        btAlterar = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        btCriar = new javax.swing.JButton();

        lblTitulo.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        lblTitulo.setText("Listagem de Clientes");

        mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 15))); // NOI18N

        lblSearchCodigo.setText("Código:");

        lblSearchNome.setText("Nome:");

        txtSearchCodigo.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        txtSearchCodigo.setToolTipText("Digite o código da conta");
        txtSearchCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchCodigoKeyReleased(evt);
            }
        });

        txtSearchNome.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        txtSearchNome.setToolTipText("Digite o nome para pesquisar");

        btPesquisar.setText("Pesquisar");
        btPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPesquisarActionPerformed(evt);
            }
        });

        lblEmail.setText("E-mail:");

        lblCelular.setText("Celular:");

        btnAbrirSocket.setText("Ouvir");
        btnAbrirSocket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirSocketActionPerformed(evt);
            }
        });

        btnRefreshTable.setText("Atualiza Listagem");
        btnRefreshTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSearchCodigo)
                    .addComponent(lblEmail)
                    .addComponent(lblSearchNome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(txtSearchEmail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btPesquisar)
                        .addGap(12, 12, 12))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(txtSearchNome)
                        .addContainerGap())
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(txtSearchCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCelular)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearchCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRefreshTable)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAbrirSocket)
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(btnAbrirSocket)
                .addGap(22, 22, 22)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblSearchCodigo)
                        .addComponent(txtSearchCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblCelular))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearchCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRefreshTable)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSearchNome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblEmail)
                    .addComponent(txtSearchEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btPesquisar))
                .addContainerGap())
        );

        tabela.setModel(new ClienteTableModel(lista));
        tabela.setColumnSelectionAllowed(true);
        tabela.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tabela);
        tabela.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        btFechar.setText("Fechar janela");
        btFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFecharActionPerformed(evt);
            }
        });

        btAlterar.setText("Alterar cliente selecionado");
        btAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAlterarActionPerformed(evt);
            }
        });

        btExcluir.setText("Excluir cliente selecionado");
        btExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirActionPerformed(evt);
            }
        });

        btCriar.setText("Criar novo cliente");
        btCriar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCriarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitulo)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btCriar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btExcluir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btAlterar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btFechar)))
                        .addGap(0, 48, Short.MAX_VALUE))
                    .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btFechar)
                    .addComponent(btAlterar)
                    .addComponent(btCriar)
                    .addComponent(btExcluir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFecharActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btFecharActionPerformed

    private void btAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterarActionPerformed
        // TODO add your handling code here:
        int iRow = tabela.getSelectedRow();
        if (iRow >= 0) {
            Cliente objeto = lista.get(iRow);
            ClienteAtualizarDialog dialog
                    = new ClienteAtualizarDialog(
                            ClienteListarDialog.this, true,
                            objeto);
            dialog.setVisible(true);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(null, "Nenhuma linha foi selecionada");
        }
    }//GEN-LAST:event_btAlterarActionPerformed

    private void btExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirActionPerformed
        // TODO add your handling code here:
        int iRow = tabela.getSelectedRow();
        if (iRow >= 0) {
            Cliente objeto = lista.get(iRow);
            System.out.println(objeto.getId());

            int resposta = JOptionPane.showConfirmDialog(this,
                    "Deseja excluir o item selecionado?",
                    "Excluir item", JOptionPane.YES_NO_OPTION);
            if (resposta == 0) {
                try {
                    controller.delete(objeto.getId());
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                    JOptionPane.showMessageDialog(this,
                            String.format("Erro ao excluir isto.\nMensagem: %s",
                                    ex.getMessage()),
                            "Erro ao excluir item",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(null, "Nenhuma linha foi selecionada");
        }
    }//GEN-LAST:event_btExcluirActionPerformed

    private void btCriarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCriarActionPerformed
        // TODO add your handling code here:
        ClienteAtualizarDialog dialog
                = new ClienteAtualizarDialog(
                        ClienteListarDialog.this, true);
        dialog.setVisible(true);
        refreshTable();
    }//GEN-LAST:event_btCriarActionPerformed

    private void btPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPesquisarActionPerformed
        // TODO add your handling code here:
        refreshTable();
    }//GEN-LAST:event_btPesquisarActionPerformed

    private void txtSearchCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchCodigoKeyReleased
        // TODO add your handling code here:
        if (!Pattern.matches("[\\d]", "" + evt.getKeyChar())) {
            JOptionPane.showMessageDialog(this, "O campo código aceita somente números.");
            txtSearchCodigo.setText("");
        }
    }//GEN-LAST:event_txtSearchCodigoKeyReleased

    private void btnAbrirSocketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirSocketActionPerformed
        if (btnAbrirSocket.isSelected() == true) {
            try {
                this.servidor = new Servidor((Integer) 12345, "./src/resource/texto.txt");
            } catch (IOException ex) {
                Logger.getLogger(ClienteListarDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
            // thread para aguardar as conexões dos clientes
            threadServer = new Thread(() -> {
                try {
                    this.servidor.executa();

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                            "Erro de IO",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
            this.threadServer.start();
            btnAbrirSocket.setText("Ouvindo...");
        } else {
            try {
                this.servidor.fechar();
                btnAbrirSocket.setText("Ouvir");
            } catch (IOException ex) {
                Logger.getLogger(ClienteListarDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_btnAbrirSocketActionPerformed

    private void btnRefreshTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshTableActionPerformed
        refreshTable();
    }//GEN-LAST:event_btnRefreshTableActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAlterar;
    private javax.swing.JButton btCriar;
    private javax.swing.JButton btExcluir;
    private javax.swing.JButton btFechar;
    private javax.swing.JButton btPesquisar;
    private javax.swing.JToggleButton btnAbrirSocket;
    private javax.swing.JButton btnRefreshTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCelular;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblSearchCodigo;
    private javax.swing.JLabel lblSearchNome;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtSearchCelular;
    private javax.swing.JTextField txtSearchCodigo;
    private javax.swing.JTextField txtSearchEmail;
    private javax.swing.JTextField txtSearchNome;
    // End of variables declaration//GEN-END:variables
}
