package notestakingapp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import java.sql.*;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class Main extends JFrame {

    static JPanel menu, buttonholder;
    static JTextField title;
    static JTextArea description;
    static JScrollPane scroll;
    static JButton Submit, Edit, Delete, IdButton;

    Main(String username) {

        JPanel menupanel = new JPanel();

        GridBagLayout gridebag = new GridBagLayout();
        menupanel.setLayout(gridebag);

        menu = new JPanel();
        buttonholder = new JPanel();
        title = new JTextField();
        description = new JTextArea();
        scroll = new JScrollPane(description);
        IdButton = new JButton("Button");
        IdButton.setVisible(false);

        description.setLineWrap(true);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        menu.setBackground(Color.pink);
        buttonholder.setBackground(Color.green);

        GridBagConstraints constr = new GridBagConstraints();
        constr.gridx = 0;
        constr.gridy = 0;
        constr.gridheight = 8;
        constr.gridwidth = 2;
        constr.weightx = 3.0;
        constr.weighty = 1.0;
        constr.insets = new Insets(1, 1, 1, 1);
        constr.anchor = GridBagConstraints.CENTER;
        constr.fill = GridBagConstraints.BOTH;
        menupanel.add(menu, constr);

        constr.gridx = 2;
        constr.gridy = 0;
        constr.gridheight = 1;
        constr.gridwidth = 7;
        constr.weightx = 2.0;
        constr.weighty = 0;
        constr.ipadx = 20;
        constr.ipady = 20;
        constr.insets = new Insets(1, 1, 1, 1);
        constr.anchor = GridBagConstraints.CENTER;
        constr.fill = GridBagConstraints.BOTH;
        menupanel.add(title, constr);

        constr.gridx = 2;
        constr.gridy = 1;
        constr.gridheight = 5;
        constr.gridwidth = 3;
        constr.weightx = 2.0;
        constr.weighty = 1.0;
        constr.insets = new Insets(2, 2, 2, 2);
        constr.anchor = GridBagConstraints.CENTER;
        constr.fill = GridBagConstraints.BOTH;
        menupanel.add(scroll, constr);

        constr.gridx = 2;
        constr.gridy = 6;
        constr.gridheight = 1;
        constr.gridwidth = 3;
        constr.weightx = 1.0;
        constr.weighty = 0;
        constr.insets = new Insets(2, 2, 2, 2);
        constr.anchor = GridBagConstraints.CENTER;
        constr.fill = GridBagConstraints.BOTH;

        menupanel.add(IdButton, constr);
        constr.gridx = 2;
        constr.gridy = 7;
        constr.gridheight = 1;
        constr.gridwidth = 3;
        constr.weightx = 2.0;
        constr.weighty = 0.1;
        constr.insets = new Insets(1, 1, 1, 1);
        constr.anchor = GridBagConstraints.CENTER;
        constr.fill = GridBagConstraints.BOTH;
        menupanel.add(buttonholder, constr);

        BoxLayout sidemenu = new BoxLayout(menu, BoxLayout.Y_AXIS);
        menu.setLayout(sidemenu);
        menu.add(Box.createRigidArea(new Dimension(5, 15)));

        Submit = new JButton("Submit");
        Edit = new JButton("Update");
        Delete = new JButton("Delete");
        Delete.setEnabled(false);
        Edit.setEnabled(false);

        buttonholder.add(Submit);
        buttonholder.add(Edit);
        buttonholder.add(Delete);

        try {
            showSidemenu(username);
        } catch (SQLException ex) {
            Logger.getLogger(getName()).log(Level.SEVERE, null, ex);
        }

        Submit.addActionListener((e) -> {
            connection obj = new connection();
            PreparedStatement myStmt;
            String text, content;

            text = title.getText();
            content = description.getText();

            if (!"".equals(text) && !"".equals(content)) {
                String sql = "insert into notes "
                        + " (Title, Discription,User)" + " values (?, ?,?)";

                try {
                    myStmt = obj.con.prepareStatement(sql);
                    myStmt.setString(1, text);
                    myStmt.setString(2, content);
                    myStmt.setString(3, username);
                    // 3. Execute SQL query
                    myStmt.executeUpdate();
                    obj.con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(getName()).log(Level.SEVERE, null, ex);
                }

                title.setText("");
                description.setText("");
                try {
                    showSidemenu(username);
                } catch (SQLException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No data entered");
                System.out.println("Please enter data");
            }
        });

        add(menupanel);
        setSize(900, 700);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void showSidemenu(String name) throws SQLException {
        System.out.print("sidemenu called ");
        menu.removeAll();
        menu.repaint();
        menu.revalidate();

        connection obj = new connection();
        PreparedStatement myStmt;

        ResultSet result;
        String query = "Select * from notes where User= ?";
        myStmt = obj.con.prepareStatement(query);
        myStmt.setString(1, name);
        result = myStmt.executeQuery();

        while (result.next()) {
            String titleString = result.getString(2);
            int Id = result.getInt(1);
            JLabel sideitem = new JLabel(titleString);

            System.out.println("Data");
            // Showing data into textfield nd textarea after clicking on the label in sidemenu
            sideitem.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Delete.setEnabled(true);
                    Edit.setEnabled(true);
                    String titleString = sideitem.getText();
                    connection obj = new connection();
                    PreparedStatement myStmt;
                    ResultSet result;
                    String query = "SELECT * from notes WHERE PersonID= ?";
                    try {
                        myStmt = obj.con.prepareStatement(query);
                        myStmt.setInt(1, Id);
                        result = myStmt.executeQuery();

                        if (result.next()) {
                            IdButton.setText(Integer.toString(result.getInt(1)));
                            title.setText(result.getString(2));
                            description.setText(result.getString(3));

                        }
                        Submit.setEnabled(false);

                        Edit.addActionListener(ee -> {
                            PreparedStatement myStm;
                            String text = title.getText();
                            String content = description.getText();
                            int Id = Integer.parseInt(IdButton.getText());

                            if (!"".equals(text) && !"".equals(content)) {
                                String sql = "UPDATE  notes set Title=? , Discription=? WHERE  PersonID=? ";

                                try {
                                    myStm = obj.con.prepareStatement(sql);
                                    myStm.setString(1, text);
                                    myStm.setString(2, content);
                                    myStm.setInt(3, Id);
                                    myStm.executeUpdate();
                                    showSidemenu(name);
                                    JOptionPane.showMessageDialog(null, "Data updated");
                                } catch (SQLException ex) {
                                    Logger.getLogger(getName()).log(Level.SEVERE, null, ex);
                                }

                                title.setText("");
                                description.setText("");
                                Delete.setEnabled(false);
                                Edit.setEnabled(false);
                                Submit.setEnabled(true);

                            }

                        });

                        Delete.addActionListener(ed -> {
                            PreparedStatement myStm;
                            int Id = Integer.parseInt(IdButton.getText());
                            String sql = "DELETE FROM  notes  WHERE  PersonID=?;";

                            try {
                                myStm = obj.con.prepareStatement(sql);
                                myStm.setInt(1, Id);
                                myStm.executeUpdate();

                                showSidemenu(name);
                            } catch (SQLException ex) {
                                Logger.getLogger(getName()).log(Level.SEVERE, null, ex);
                            }
                            title.setText("");
                            description.setText("");
                            Submit.setEnabled(true);
                            Delete.setEnabled(false);
                            Edit.setEnabled(false);
                        });

                    } catch (SQLException ex) {
                        Logger.getLogger(getName()).log(Level.SEVERE, null, ex);
                    }

                }

            });

            JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
            separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
            menu.add(sideitem);
            menu.add(Box.createRigidArea(new Dimension(5, 15)));
            menu.add(separator);
            menu.add(Box.createRigidArea(new Dimension(5, 15)));
            menu.add(sideitem);

        }
        obj.con.close();
    }

//    public static void main(String arg[]) {
//        new Main("Dnyaneshwari");
//    }

}
