/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package notestakingapp;

import java.sql.*;

/**
 *
 * @author Admin
 */
public class connection {
     Connection con;
     Statement stmt;
  
    connection()
    {
         try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/NoteApp", "root", "####"); //replace #### by your db password

            if (con != null) {
                System.out.print("Connection created successfully\n");

                stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                
            } else {
                System.out.print("Connection is not created\n");
            }
        } catch (ClassNotFoundException | SQLException e) {
             System.out.print("Connection is not created\n");
            System.out.print(e);

        }

    }
    public static void main(String[] args) {
        new connection();
    }
}


