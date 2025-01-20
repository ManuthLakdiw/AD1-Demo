package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerDAOImpl {

    public ArrayList<CustomerDTO> loadAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> customers = new ArrayList<>(); // this is created for store many customers (customerDto)
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM customer");

        while (resultSet.next()) {
            CustomerDTO customerDTO = new CustomerDTO(); // this is created for store one customerData (each customer id , name , address)
            customerDTO.setId(resultSet.getString(1));
            customerDTO.setName(resultSet.getString(2));
            customerDTO.setAddress(resultSet.getString(3));

            customers.add(customerDTO); // add one customer (with details) for customer list
        }
     return customers; // return customers list
    }

}
