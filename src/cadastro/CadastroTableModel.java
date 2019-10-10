package cadastro;

import cadastro.Cliente;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class CadastroTableModel extends AbstractTableModel {

    private List<String> cabecalho;

    //item - linhas da tabela
    private List<Cliente> listaClientes;

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public CadastroTableModel() {
        cabecalho = new ArrayList<>();
        listaClientes = new ArrayList<>();

        cabecalho.add("ID");
        cabecalho.add("NOME");
        cabecalho.add("CELULAR");
        cabecalho.add("E-MAIL");
    }

    public List<String> getCabecalho() {
        return cabecalho;
    }

    public void setCabecalho(List<String> cabecalho) {
        this.cabecalho = cabecalho;
    }

    @Override
    public String getColumnName(int column) {
        return cabecalho.get(column);
    }

    @Override
    public int getRowCount() {
        return listaClientes.size();
    }

    @Override
    public int getColumnCount() {
        return cabecalho.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                //retornar o id
                return listaClientes.get(rowIndex).getId();
            case 1:
                //retornar o nome
                return listaClientes.get(rowIndex).getNome();
            case 2:
                return listaClientes.get(rowIndex).getCelular();
            case 3:
                return listaClientes.get(rowIndex).getEmail();
            default:
                return null;
        }
    }

}
