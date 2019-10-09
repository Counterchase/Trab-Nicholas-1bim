/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author nicho
 */
public class Cliente {
    private Integer id;
    private String nome;
    private String email;
    private String celular;
    
    public Cliente() {
        
    }
    
    public Cliente(Integer id, String nome, String email,
            String celular) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.celular = celular;
    }
    
    public Cliente(ResultSet rs, String args[]) throws SQLException {
        this(rs.getInt(args[0]), rs.getString(args[1]),
                rs.getString(args[2]), rs.getString(args[3]));
    }
    
    public Cliente(ResultSet rs) throws SQLException {
        this(rs, new String[] {"r_id", "r_nome", 
            "r_email", "r_celular"});
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
}
