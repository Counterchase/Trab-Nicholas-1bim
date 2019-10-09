/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import connection.Conexao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;
import pattern.dao.ClienteDao;
import pattern.dao.DaoPattern;

/**
 *
 * @author nicho
 */
public class ClienteController {
    private DaoPattern dao;
    
    public ClienteController() throws ClassNotFoundException, SQLException {
        dao = new ClienteDao(Conexao.getInstancia());
    }
    
    public void salvar(Integer id, String nome, String email,
            String celular) throws SQLException {
        Cliente cliente = new Cliente(id, nome, email, celular);
        try {
            dao.save(cliente);
            dao.commit();
        } catch (SQLException ex) {
            dao.rollback();
            throw ex;
        }
    }
    
    public void delete(Integer id) throws SQLException {
        Cliente cliente = buscarPorId(id);
        if (cliente != null) {
            try {
                dao.delete(cliente);
                dao.commit();
            } catch (SQLException ex) {
                dao.rollback();
                throw ex;
            }
        }
    }
    
    public List listar(String nome, 
            String email, String celular) {
        List<String> criterio = new ArrayList<>();
        if (nome != null && !"".equals(nome)) {
            criterio.add("UPPER(nome) LIKE UPPER('%"+nome+"%')");
        }
        if (email != null && !"".equals(email)) {
            criterio.add("UPPER(email) LIKE UPPER('%"+email+"%')");
        }
        if (celular != null && !"".equals(celular)) {
            criterio.add("celular LIKE '%"+celular+"%'");
        }
        String criteria = "";
        if (!criterio.isEmpty()) {
            for(int i = 0; i < criterio.size(); i++) {
                if (i > 0) {
                    criteria += " AND ";
                }
                criteria += criterio.get(i);
            }
        }
        return dao.list(criteria);
    }
    
    public List listar() {
        return this.listar("", "", "");
    }
    
    public Cliente buscarPorId(Integer id) {
        return (Cliente) dao.findById(id);
    }
}
