package org.view;

import org.conection.ConectionDb;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Store extends JFrame {

    private JFrame frame;
    private JPanel Mainpanel;
    private JTextField textTitle;
    private JTextField textDescription;
    private JTextField textPrice;
    private JTextField textRating;
    private JButton saveButton;
    private JTable products;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton searchButton;
    private JTextField idTextField;
    private JScrollPane tableProducts;

    PreparedStatement pst;
    ConectionDb objcConection = new ConectionDb();
    Connection con =null;
    public void connect(){
        String url = "jdbc:mysql://localhost:3306/tienda";
        String username="root";
        String password="";

        try {
            con = DriverManager.getConnection(url,username,password);
            System.out.println("The connecion was done successfully");

        }catch (SQLException ex){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    void  reload_table(){
        DefaultTableModel model = (DefaultTableModel) products.getModel();
        String [] datos = new String[5];
        model.setRowCount(0);
        try
        {
            pst = objcConection.initConection().prepareStatement("select * from products");
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                model.addRow(datos);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public Store() {
         createUIComponents();
         reload_table();
         setContentPane(Mainpanel);
         setTitle("example");
         setDefaultCloseOperation(EXIT_ON_CLOSE);
         setSize(1000,800);
         setLocationRelativeTo(null);
         setVisible(true);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 String title,description,price,rating;
                 title=textTitle.getText();
                 description=textDescription.getText();
                 price=textPrice.getText();
                 rating=textRating.getText();
                try {

                    pst = objcConection.initConection().prepareStatement("insert into products(title,description,price,rating)values(?,?,?,?)");
                    pst.setString(1, title);
                    pst.setString(2, description);
                    pst.setString(3, price);
                    pst.setString(4, rating);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
                    reload_table();
                    textTitle.setText("");
                    textDescription.setText("");
                    textPrice.setText("");
                    textTitle.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }



            }
        });
    }




    private void createUIComponents() {

        String[] columnNames = {"Id","Title", "Description", "Price", "Rating"};
        Object[][] data = { };
        DefaultTableModel model = new DefaultTableModel(data, columnNames);


        products.setModel(model);
    }



}
