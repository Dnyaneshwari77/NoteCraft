package notestakingapp;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sign_Up extends JFrame {

    JTextField name_text, email_text, mobile_text, username_text, password_text;
    JComboBox profession_choice;
    JButton btn;

    Sign_Up() {
        setLayout(null);

        JLabel headding = new JLabel("Sign Up", JLabel.CENTER);
        headding.setBounds(0, 20, 550, 55);
        headding.setFont(new Font("Roboto", Font.BOLD, 26));
        add(headding);

        JLabel name = new JLabel("Name :");
        name.setBounds(10, 80, 150, 30);
        name.setFont(new Font("Roboto", Font.BOLD, 18));
        add(name);

        name_text = new JTextField("");
        name_text.setBounds(170, 80, 350, 30);
        add(name_text);

        JLabel Email = new JLabel("Email :");
        Email.setBounds(10, 120, 150, 30);
        Email.setFont(new Font("Roboto", Font.BOLD, 18));
        add(Email);

        email_text = new JTextField("");
        email_text.setBounds(170, 120, 350, 30);
        add(email_text);

        JLabel Mobile_No = new JLabel("Mobile:");
        Mobile_No.setBounds(10, 160, 150, 30);
        Mobile_No.setFont(new Font("Roboto", Font.BOLD, 18));
        add(Mobile_No);

        mobile_text = new JTextField("");
        mobile_text.setBounds(170, 160, 350, 30);
        add(mobile_text);

        String profession[] = {"Student", "Employe", "Teacher"};

        JLabel Profession = new JLabel("Profession:");
        Profession.setBounds(10, 200, 150, 30);
        Profession.setFont(new Font("Roboto", Font.BOLD, 18));
        add(Profession);

        profession_choice = new JComboBox(profession);
        profession_choice.setBounds(170, 200, 350, 30);
        add(profession_choice);

        JLabel Username = new JLabel("Username:");
        Username.setBounds(10, 240, 150, 30);
        Username.setFont(new Font("Roboto", Font.BOLD, 18));
        add(Username);

        username_text = new JTextField("");
        username_text.setBounds(170, 240, 350, 30);
        add(username_text);

        JLabel Password = new JLabel("Password:");
        Password.setBounds(10, 280, 150, 30);
        Password.setFont(new Font("Roboto", Font.BOLD, 18));
        add(Password);

        password_text = new JTextField("");
        password_text.setBounds(170, 280, 350, 30);
        add(password_text);

        JLabel note = new JLabel("Don't forgot username and password", JLabel.CENTER);
        note.setBounds(10, 320, 550, 30);
        note.setFont(new Font("Roboto", Font.ITALIC, 12));
        add(note);

        btn = new JButton("Sign In");
        btn.setBounds(10, 360, 520, 30);
        add(btn);

        btn.addActionListener(e -> {
            sign_up();
        });

        this.getContentPane().setBackground(Color.WHITE);
        setSize(550, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    void sign_up() {
        String name, email, mobile, profession, username, password;
        name = name_text.getText();
        email = email_text.getText();
        mobile = mobile_text.getText();
        profession = profession_choice.getSelectedItem().toString();
        username = username_text.getText();
        password = password_text.getText();

        connection obj = new connection();
        PreparedStatement myStm;

        if (!" ".equals(name) && !"".equals(email) && !"".equals(mobile) && !"".equals(profession) && !"".equals(username) && !"".equals(password)) {
            String sql = "Insert into user (Name,Email,Mobile,Profession,Username,Password) VALUES(?,?,?,?,?,?);";
            try {
                myStm = obj.con.prepareStatement(sql);
                myStm.setString(1, name);
                myStm.setString(2, email);
                myStm.setString(3, mobile);
                myStm.setString(4, profession);
                myStm.setString(5, username);
                myStm.setString(6, password);
                myStm.executeUpdate();
            } catch (SQLException ex) {

                Logger.getLogger(Sign_Up.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please fill all fields");
        }

        name_text.setText("");
        email_text.setText("");
        mobile_text.setText("");
        profession_choice.setSelectedIndex(0);
        username_text.setText("");
        password_text.setText("");
        this.setVisible(false);
        new NotesTakingApp();
    }

    public static void main(String args[]) {
        new Sign_Up();
    }

}
