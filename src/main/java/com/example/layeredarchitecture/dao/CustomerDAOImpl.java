package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
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

    @Override
    public void saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Customer (id,name, address) VALUES (?,?,?)");
        preparedStatement.setString(1, customerDTO.getId());
        preparedStatement.setString(2, customerDTO.getName());
        preparedStatement.setString(3, customerDTO.getAddress());
        preparedStatement.executeUpdate();

//        executeQuary ----> for "SELECT query"   return-Resultset
//
//        executeUpdate ------> for "INSERT" , "UPDATE" , "DELETE" queries return-row affected count(int)
//
//        execute ------> for any query
//        return- if(select) true
//        return- if(update ,insert , delete) false
//        (value doesn't have or have)


    }


    @Override
    public void updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Customer SET name = ?, address = ? WHERE id = ?");
        preparedStatement.setString(1, customerDTO.getName());
        preparedStatement.setString(2, customerDTO.getAddress());
        preparedStatement.setString(3, customerDTO.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customer WHERE id = ?");
        preparedStatement.setString(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    @Override
    public void deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM customer WHERE id = ?");
        preparedStatement.setString(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public String generateNewCustomerID() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement statement = connection.createStatement();
//        ResultSet rst = connection.createStatement().executeQuery("SELECT id FROM Customer ORDER BY id DESC LIMIT 1;");
        ResultSet resultSet = statement.executeQuery("SELECT id FROM Customer ORDER BY id DESC LIMIT 1;");
        if (resultSet.next()) {
            String id = resultSet.getString(1);
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        }
        return "C00-001" ;
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customer WHERE id = ?");
        preparedStatement.setString(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new CustomerDTO(id + "", resultSet.getString("name"), resultSet.getString("address"));


    }

}
