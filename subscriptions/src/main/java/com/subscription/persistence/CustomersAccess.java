package com.subscription.persistence;

import java.sql.*;
import java.util.ArrayList;

import com.subscription.model.Customers;

public class CustomersAccess{

    // GET ALL CUSTOMERS FROM TABLE CUSTOMERS
    public ArrayList<Customers> getAllCustomers() throws SQLException{
        Connection conn = null;
        PreparedStatement state = null;
        ResultSet result = null;
        ArrayList<Customers> customersList = new ArrayList<>();

        try{
            Class.forName("org.sqlite.JBDC");
            conn = DriverManager.getConnection("jbdc:sqlite:subscription.db");
            System.out.println("has connected to the database");
            state = conn.prepareStatement("SELECT * FROM customers");
            result = state.executeQuery();

            while(result.next()){
                int id = result.getInt("id");
                String email = result.getString("email");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String phoneNumber = result.getString("phone_number");

                Customers customer = new Customers();

                customer.setId(id);
                customer.setEmail(email);
                customer.setFirst_name(firstName);
                customer.setLast_name(lastName);
                customer.setPhone_number(phoneNumber);

                customersList.add(customer);
            }
        }catch(SQLException | ClassNotFoundException e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        }finally{
            if(result != null) result.close();
            if(state != null) state.close();
            if(conn != null) conn.close();
        }
        return customersList;
    }
    // GET CUSTOMER BY ID FROM TABLE CUSTOMERS
    public Customers getUserById(int id) throws SQLException{
        Connection conn = null;
        PreparedStatement state = null;
        ResultSet result = null;
        Customers customer = new Customers();

        try {
            Class.forName("org.sqlite.JBDC");
            conn = DriverManager.getConnection("jbdc:sqlite:subscription.db");
            System.out.println("has connected to the database");
            state = conn.prepareStatement("SELECT * FROM customers WHERE id = ?");
            state.setInt(1, id);
            result = state.executeQuery();
            
            while(result.next()){
                int idc = result.getInt("id");
                String email = result.getString("email");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String phoneNumber = result.getString("phone_number");

                customer.setId(idc);
                customer.setEmail(email);
                customer.setFirst_name(firstName);
                customer.setLast_name(lastName);
                customer.setPhone_number(phoneNumber);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        }finally{
            if(result != null) result.close();
            if(state != null) state.close();
            if(conn != null) conn.close();
        }

        return customer;
    }
    // 
    public String addNewCustomer(Customers customer) throws SQLException{
        Connection conn = null;
        PreparedStatement state = null;

        String response;

        try {
            Class.forName("org.sqlite.JBDC");
            conn = DriverManager.getConnection("jbdc:sqlite:subscription.db");
            System.out.println("has connected to the database");
            state = conn.prepareStatement("INSERT INTO customers VALUES (?, ?, ?, ?, ?);");
            System.out.println("Inserting data to table customers");
            state.setInt(1, customer.getId());
            state.setString(2, customer.getEmail());
            state.setString(3, customer.getFirst_name());
            state.setString(4, customer.getLast_name());
            state.setString(5, customer.getPhone_number());

            int rowsAffected = state.executeUpdate();
            if(rowsAffected > 0) {
                response = rowsAffected + " row(s) has been affected";
                System.out.println(response);
            }else{
                response = "No rows have been added";
                System.out.println(response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        }finally{
            if(state != null) state.close();
            if(conn != null) conn.close();
        }

        return response;
    }
}