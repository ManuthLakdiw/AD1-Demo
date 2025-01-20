package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.model.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO {

    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    public void saveItem(ItemDTO item) throws SQLException, ClassNotFoundException;

    public void updateItem(ItemDTO item) throws SQLException, ClassNotFoundException;

    public void deleteItem(String code) throws SQLException, ClassNotFoundException;

    public boolean existsItem(String code) throws SQLException, ClassNotFoundException;

    public String generateNewItemID() throws SQLException, ClassNotFoundException;

    public ItemDTO findItem(String code) throws SQLException, ClassNotFoundException;

}
