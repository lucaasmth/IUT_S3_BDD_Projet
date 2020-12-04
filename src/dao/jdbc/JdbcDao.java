package dao.jdbc;//package dao.jdbc;

import dao.Dao;

import java.sql.Connection;

public abstract class JdbcDao implements Dao {

    protected Connection connection;

    public JdbcDao(Connection connection) {
        this.connection = connection;
    }

}