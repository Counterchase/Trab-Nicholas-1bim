/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.List;
import model.Cliente;

/**
 *
 * @author santos
 */
public class ClienteTableModel extends TableModelPadrao {

    public ClienteTableModel(List<Cliente> lista) {
        super(lista, new String[]{
            "Código", "Nome",
            "E-mail", "Celular"
        });
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente res = (Cliente)getRow(rowIndex);
        switch (columnIndex) {
            case 0: return res.getId();
            case 1: return res.getNome();
            case 2: return res.getEmail();
            case 3: return res.getCelular();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1: case 2: case 3:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

}
