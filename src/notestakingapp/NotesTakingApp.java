package notestakingapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NotesTakingApp extends JFrame {

    NotesTakingApp() {
        setLayout(null);

        ImageIcon logo = new ImageIcon("D://Java Revision//NotesTakingApp//src//assets//leves.jpg");
        JLabel logo_label = new JLabel(logo, JLabel.LEFT);
        logo_label.setBounds(0, 0, 190, 100);
        add(logo_label);

//        ImageIcon logo2=new ImageIcon("D://Java Revision//NotesTakingApp//src//assets//leves.jpg");
        JLabel logo_label2 = new JLabel(logo, JLabel.LEFT);
        logo_label2.setBounds(190, 0, 190, 100);
        add(logo_label2);

        JLabel logo_label3 = new JLabel(logo, JLabel.LEFT);
        logo_label3.setBounds(380, 0, 300, 100);
        add(logo_label3);

        ImageIcon student_icon = new ImageIcon("D://Java Revision//NotesTakingApp//src//assets//Student_1.jpg");
        JLabel student_img = new JLabel(student_icon, JLabel.CENTER);
        student_img.setBounds(20, 20, 300, 500);

        JPanel login_panel = new JPanel();
        login_panel.setLayout(null);
        login_panel.setBackground(Color.WHITE);
        login_panel.setBounds(300, 0, 400, 500);

        //Headding
        JLabel headding = new JLabel("Log In", JLabel.CENTER);
        headding.setBounds(0, 100, 400, 35);
        headding.setFont(new Font("Arial", Font.BOLD, 30));
        login_panel.add(headding);

        TextField username = new TextField(" ");
        username.setBounds(20, 150, 340, 30);
        login_panel.add(username);

        TextField password = new TextField(" ");
        password.setBounds(20, 190, 340, 30);
        login_panel.add(password);

        JButton Login = new JButton("Log In");
        Login.setBounds(20, 230, 160, 30);
        login_panel.add(Login);

        JButton Cancle = new JButton("Cancle");
        Cancle.setBounds(200, 230, 160, 30);
        login_panel.add(Cancle);

        JButton SignUp = new JButton("Sign Up");
        SignUp.setBounds(20, 270, 340, 30);
        login_panel.add(SignUp);

        Login.addActionListener((e) -> {

            connection con = new connection();
            PreparedStatement myStmt;
            ResultSet result;

            String user = username.getText();
            String pass = password.getText();

            System.out.println(user+" "+pass);
            if (!" ".equals(user) && !"".equals(pass)) {

                try {
                    String query = "SELECT * from user where Username=? and Password=?";
                    myStmt = con.con.prepareStatement(query);
//                    System.out.println(user);
                    myStmt.setString(1, user);
                    myStmt.setString(2, pass);
                    result = myStmt.executeQuery();
                    if(result.next())
                    {
                        System.out.println("Not Good to go");
                        JOptionPane.showMessageDialog(null, "Please enter proper username and password");
                    } else {
                        System.out.println("Good to go");
                        setVisible(false);
                        
                        Main main= new Main(user);
                    }
  
                } catch (SQLException ex) {
                    Logger.getLogger(NotesTakingApp.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Please enter proper username and password");
            }

        });
        Cancle.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                System.exit(0);
            }
        });

        SignUp.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Sign_Up();
            }
        });

        add(login_panel);
        add(student_img);
        setLocation(200, 200);
        this.getContentPane().setBackground(Color.WHITE);
        setSize(700, 500);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new NotesTakingApp();
    }


}
