import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
public class Banksbi extends Frame implements ActionListener {
    Connection con;
    Statement s;
    ResultSet rs;
    JFrame j;
    TextField tf1, tf2, tf3;
    Banksbi() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/java","bhanu","bhanu");
            Statement s=con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        j = new JFrame();
        Label l1 = new Label("User Name:");
        tf1 = new TextField();
        Label l2 = new Label("Password:");
        tf2 = new TextField();
        Label l3 = new Label("Date Of Expiry:");
        tf3 = new TextField();
        Button b1 = new Button("Login");
        b1.addActionListener(this);
        j.add(l1);
        j.add(tf1);
        j.add(l2);
        j.add(tf2);
        j.add(l3);
        j.add(tf3);
        j.add(b1);
        j.setLayout(new GridLayout(4, 2, 5, 10));
        j.setSize(400, 200);
        j.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        String userName = tf1.getText();
        String password = tf2.getText();
        String expiryDate = tf3.getText();
        try {
            clearFrame(j);
            rs = s.executeQuery("SELECT Balance FROM Bank WHERE User_Name='" + userName + "' AND Password='" + password + "' AND Date_Of_Expiry='" + expiryDate + "'");
            float balance = 0;
            if (rs.next()) {
                balance = rs.getFloat(2);
            }            
            final float finalBalance = balance;
            Label balanceLabel = new Label("Balance: " + finalBalance);
            Button depositButton = new Button("DEPOSIT");
            Button withdrawButton = new Button("WITHDRAW");
            Button checkBalanceButton = new Button("CHECK BALANCE");
            depositButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        clearFrame(j);
                        Label depositLabel = new Label("MONEY TO BE Deposited:");
                        TextField df1 = new TextField();
                        Button b=new Button("Deposit");
                        b.addActionListener(new ActionListener(){
                            public void actionPerformed(ActionEvent e){
                                float x=finalBalance+Float.parseFloat(df1.getText());
                                clearFrame(j);
                                try {
                                    Class.forName("com.mysql.jdbc.Driver");
                                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/java","bhanu","bhanu");
                                    Statement s=con.createStatement();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                s.executeUpdate("UPDATE Bank SET Balance='"+x+"' WHERE User_Name='"+userName+"'");
                                Label l=new Label("Money Deposited");
                                Button b1=new Button("Quit");
                                b1.addActionListener(new ActionListener(){
                                    public void actionPerformed(ActionEvent e){
                                        new Banksbi();
                                    }
                                });
                                j.add(l);
                                j.add(b1);
                                j.setLayout(new GridLayout(1,2,5,5));
                                j.setSize(200,200);
                                j.setVisible(true);
                            }});
                        j.add(depositLabel);
                        j.add(df1);
                        j.add(b);
                        j.setLayout(new FlowLayout());
                        j.setSize(200, 200);
                        j.setVisible(true);
                        // Update balance in the database
                        Label l4 = new Label("MONEY IS DEPOSITED");
                        j.add(l4);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            withdrawButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    clearFrame(j);
                    Label withdrawLabel = new Label("MONEY TO BE WITHDRAWN:");
                    TextField w1 = new TextField();
                    j.add(withdrawLabel);
                    j.add(w1);
                    Button b=new Button("Withdraw");
                    b.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            if (Float.parseFloat(w1.getText())>=finalBalance){
                                float x=finalBalance-Float.parseFloat(w1.getText());
                                try {
                                    Class.forName("com.mysql.jdbc.Driver");
                                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/java","bhanu","bhanu");
                                    Statement s=con.createStatement();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                s.executeUpdate("UPDATE Bank SET Balance='"+x+"' WHERE User_Name='"+userName+"'");
                                clearFrame(j);
                                Label l1=new Label("Money Withdrawn");
                                j.add(l1);
                            }
                            else{
                                Label l2=new Label("Insufficient Balance");
                                j.add(l2);
                            }
                            Button b1=new Button("Quit");
                            b1.addActionListener(new ActionListener(){
                                public void actionPerformed(ActionEvent e){
                                    new Banksbi();
                                }
                            });
                            j.add(b1);
                            j.setLayout(new GridLayout(1,2,5,5));
                            j.setSize(200,200);
                            j.setVisible(true);
                        }
                    });
                    j.add(b);
                    j.setLayout(new FlowLayout());
                    j.setSize(200, 200);
                    j.setVisible(true);
                }
            });
            checkBalanceButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    clearFrame(j);
                    Label balanceLabel = new Label("BALANCE: " + finalBalance); // Access the finalBalance variable
                    j.add(balanceLabel);
                    Button b1=new Button("Quit");
                    b1.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            new Banksbi();
                        }
                    });
                    j.add(b1);
                    j.setLayout(new FlowLayout());
                    j.setSize(200, 200);
                    j.setVisible(true);
                }
            });
            j.add(depositButton);
            j.add(withdrawButton);
            j.add(checkBalanceButton);
            j.add(balanceLabel);
            j.setLayout(new GridLayout(5, 1, 5, 10));
            j.setSize(300, 250);
            j.setVisible(true);
        }catch(Exception e1){
            e1.printStackTrace();
        }
    }
    public static void clearFrame(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();
    }
    public static void main(String arg[]) {
        new Banksbi();
    }
}

