/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.dao;

import connection.Conexao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cliente;

/**
 *
 * @author nicho
 */
public class ClienteDao extends AbstractDao {

    public ClienteDao(Conexao conexao) {
        super(conexao);
    }

    @Override
    public void save(Object object) throws SQLException {
        Cliente cliente = (Cliente) object;
        String sql = "";
        try {
            Integer id = cliente.getId();
            if (id != null && findById(id) != null) {
                sql = "UPDATE cliente SET "
                        + " nome = ?, "
                        + " email = ?, "
                        + " celular = ? "
                        + " WHERE id = ?";
                createPreparedStatement(sql);
                preparedStm.setString(1, cliente.getNome());
                preparedStm.setString(2, cliente.getEmail()+ "");
                preparedStm.setString(3, cliente.getCelular() + "");
                preparedStm.setInt(4, cliente.getId());
            } else {
                sql = "INSERT INTO cliente "
                        + "(id, nome, email, celular) "
                        + "VALUES(DEFAULT, ?, ?, ?)";
                createPreparedStatement(sql);
                preparedStm.setString(1, cliente.getNome());
                preparedStm.setString(2, cliente.getEmail()+ "");
                preparedStm.setString(3, cliente.getCelular()+ "");
            }
            preparedStm.executeUpdate();
            destroyPreparedStatement();

        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(), sql, ex.getCause());
        }
    }

    @Override
    public void delete(Object object) throws SQLException {
        Cliente cliente = (Cliente) object;
        String sql = "";
        try {
            sql = "DELETE FROM cliente WHERE id = ?";
            createPreparedStatement(sql);
            preparedStm.setInt(1, cliente.getId());
            preparedStm.executeQuery();
            destroyPreparedStatement();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(), sql, ex.getCause());
        }
    }

    @Override
    public List list() {
        return list("");
    }

    @Override
    public Object findById(Object id) {
        List lista;
        if (Integer.class == id.getClass() && (Integer) id > 0) {
            lista = this.list(String.format("id = %d", id));
            if (!lista.isEmpty()) {
                return lista.get(0);
            }
        }
        return null;
    }

    @Override
    public List list(String criteria) {
        ArrayList<Cliente> lista = new ArrayList<>();
        try {
            String sql = "SELECT id as r_id, "
                    + "nome as r_nome, "
                    + "email as r_email, "
                    + "celular as r_celular "
                    + " FROM cliente ";

            if (!"".equals(criteria)) {
                sql += " WHERE " + criteria;
            }
            sql += " ORDER BY r_nome ";

            createStatement();
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                lista.add(new Cliente(rs));
            }
            destroyStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }
    
}
