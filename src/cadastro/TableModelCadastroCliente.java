/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadastro;

import java.util.List;

public class TableModelCadastroCliente extends TableModelPadraoCadastro {

    public TableModelCadastroCliente(List linhas) {
        super(linhas);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected String[] criarColunas() {
        return new String[]{"Id", "Nome", "Email", "Celular"};
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente cliente = (Cliente) linhas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return cliente.getId();
            case 1:
                return cliente.getNome();
            case 2:
                return cliente.getEmail();
            case 3:
                return cliente.getCelular();
            default:
                return null;
        }
    }
}
