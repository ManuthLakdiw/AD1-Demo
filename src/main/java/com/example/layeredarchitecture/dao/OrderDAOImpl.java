package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public String generateNewOrderID() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT oid FROM `Orders` ORDER BY oid DESC LIMIT 1;");

        return resultSet.next() ? String.format("OID-%03d", (Integer.parseInt(resultSet.getString("oid").replace("OID-", "")) + 1)) : "OID-001";


    }
}
