package org.conection;

import org.view.Main;

import javax.swing.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConectionDb {
 public Connection initConection (){
     String url = "jdbc:mysql://localhost:3306/tienda";
     String username="root";
     String password="";
     Connection c=null;
     try {
         c = DriverManager.getConnection(url,username,password);
         JOptionPane.showMessageDialog(null,"The connection was done successfully");
     }catch (SQLException ex){
         Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
     }
     return c;
 }
}
