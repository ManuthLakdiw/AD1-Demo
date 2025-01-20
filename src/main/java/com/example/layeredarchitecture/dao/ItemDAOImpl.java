package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.ItemDTO;

import java.sql.*;
import java.util.ArrayList;

public class ItemDAOImpl {

    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> items = new ArrayList<>();

        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM item");
        while (resultSet.next()) {
            ItemDTO item = new ItemDTO();
            item.setCode(resultSet.getString(1));
            item.setDescription(resultSet.getString(2));
            item.setQtyOnHand(resultSet.getInt(3));
            item.setUnitPrice(resultSet.getBigDecimal(4));

            items.add(item);
        }
        return items;
    }

    public void saveItem(ItemDTO item) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)");
        statement.setString(1, item.getCode());
        statement.setString(2, item.getDescription());
        statement.setInt(3, item.getQtyOnHand());
        statement.setBigDecimal(4, item.getUnitPrice());
        statement.executeUpdate();
    }

    public void updateItem(ItemDTO item) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?");
        statement.setString(1, item.getDescription());
        statement.setBigDecimal(2, item.getUnitPrice());
        statement.setInt(3, item.getQtyOnHand());
        statement.setString(4, item.getCode());
        statement.executeUpdate();
    }

    public void deleteItem(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM Item WHERE code=?");
        statement.setString(1, code);
        statement.executeUpdate(); //  මඟින් SQL එක database එකට යවලා, එය ක්‍රියාත්මක කරනවා.
    }

    public boolean existsItem(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Item WHERE code=?");
        statement.setString(1, code);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    public String generateNewItemID() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1;");
        if (resultSet.next()) {
            String id = resultSet.getString("code");
            int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemId);
        } else {
            return "I00-001";
        }
    }
}
