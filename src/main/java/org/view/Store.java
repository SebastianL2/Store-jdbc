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
    private JButton refreshButton;

    PreparedStatement pst;
    ConectionDb objcConection = new ConectionDb();
    Connection con =null;
    public void connect(){
        String url = "jdbc:mysql://localhost:3306/tienda";
        String username="root";
        String password="";

        try {
            con = DriverManager.getConnection(url,username,password);
            System.out.println("The connection was done successfully");

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
         setSize(1366,668);
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
                    JOptionPane.showMessageDialog(null, "Record Added!!!!!");
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
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = products.getSelectedRow();
                int[] rows=products.getSelectedColumns();
                if(row >=0){
                    String value = products.getValueAt(row,0).toString();

                    try{
                        pst = objcConection.initConection().prepareStatement("DELETE FROM  products WHERE id = ?");
                        pst.setString(1,value);
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Product Deleted  id: "+value);
                        reload_table();
                    }catch (SQLException e1){
                        e1.printStackTrace();
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Row no selected");
                }

            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = products.getSelectedRow();
                int[] rows=products.getSelectedColumns();
                String title,description,price,rating;
                title=textTitle.getText();
                description=textDescription.getText();
                price=textPrice.getText();
                rating=textRating.getText();
                if(row >=0){
                    String value = products.getValueAt(row,0).toString();

                    try{
                        pst = objcConection.initConection().prepareStatement("UPDATE products SET title=?,description=?,price=?,rating=? WHERE id = ?");
                        pst.setString(1, title);
                        pst.setString(2, description);
                        pst.setString(3, price);
                        pst.setString(4, rating);
                        pst.setString(5,value);
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Product Updated id: "+value);
                        reload_table();
                    }catch (SQLException e1){
                        e1.printStackTrace();
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Row no selected");
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchId;
                searchId = idTextField.getText();
                System.out.println(searchId);
                DefaultTableModel model = (DefaultTableModel) products.getModel();
                String [] datos = new String[5];
                model.setRowCount(0);
                try
                {
                    pst = objcConection.initConection().prepareStatement("select * from products WHERE id= ?");
                    pst.setString(1,searchId);
                    ResultSet rs = pst.executeQuery();

                    if(!rs.next()){
                        JOptionPane.showMessageDialog(null, "Product not found :o ");
                    } else {
                        do {
                            datos[0]=rs.getString(1);
                            datos[1]=rs.getString(2);
                            datos[2]=rs.getString(3);
                            datos[3]=rs.getString(4);
                            datos[4]=rs.getString(5);
                            model.addRow(datos);
                        }while (rs.next());

                    }
                }
                catch (SQLException er)
                {
                    er.printStackTrace();
                }
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reload_table();
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
