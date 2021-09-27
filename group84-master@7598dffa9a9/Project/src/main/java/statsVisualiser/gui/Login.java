/**
 * This is the program that is used to validate the user's account. SYSTEM STARTED THROUGH THIS CLASS.
 * @author  Kohei Yasui, Terrence Ju, John Dick, Kevin Yang
 * Changelog
 * March 31, 2021 
 * Made changes to ensure the program reads correctly from the given database file.
 * 
 * April 7, 2021 (3:26 pm) 
 * Made some changes to validate the user.
 */

package statsVisualiser.gui;

// Importing from abstract window toolkit.
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Importing from swing toolkit.
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.google.gson.internal.bind.TreeTypeAdapter;

import org.jfree.chart.util.ArrayUtils;

// Importing io items
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.HashMap;

// Importing the required io reader classes to read the text file.
import java.io.BufferedReader;
import java.io.FileReader;

import java.util.Arrays;

// Importing the dictionary class so it can be used to store the user's credentials.
import java.util.Dictionary;

/**
 * This class handles the Login part of the system
 */
public class Login extends JFrame implements ActionListener {
    // Main menu panel (the thing that the items are drawn onto)
    JPanel panel; 

    // Labels for username, password, and message.
    JLabel user_label, password_label, message;

    // Textfield to accept username text from user.
    JTextField userName_text;

    // Textfield to accept password from the user (allows the password to be hidden)
    JPasswordField password_text;

    // Buttons to allow the user to cancel or submit their credentials.
    JButton submit, cancel;

    // Dictionary used to store the username - password pairs. We are going to 
    // reuse the dictionary class.
    private static HashMap <String, String> credentials = new HashMap <String, String>();
    /**
     * This Login method is the constructor of the Login 
     */
    public Login() {
        
        // Creating the label for the username field.
        user_label = new JLabel();
        user_label.setText("User Name :");
        userName_text = new JTextField();
        
        // Creating the label for the password field.
        password_label = new JLabel();
        password_label.setText("Password :");
        password_text = new JPasswordField();

        // Creating a button for accepting the user's credentials.
        submit = new JButton("SUBMIT");
        
        // Creating a jpanel for the main display so that items can be written onto it.
        panel = new JPanel(new GridLayout(3, 1));

        // Adding the labels onto the panel.
        panel.add(user_label);
        panel.add(userName_text);
        panel.add(password_label);
        panel.add(password_text);

        // Creating the message button and adding the buttons onto the main display.
        message = new JLabel();
        panel.add(message);
        panel.add(submit);
        
        // Set a close default.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Adding the listeners to the components so they can respond to events.
        submit.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setTitle("Login User Session");
        setSize(400, 110);

        // Centers in screen
        setLocationRelativeTo(null);
        setVisible(true);

    }

    /**
     * This method is used to grab the username - password pairs from the login database text file.
     */
    public static void getLogins() {

        String line= "";

        // Trying to read the file and storing the values into a dictionary.
        try{

            // Creating a reader for the file.
            BufferedReader read = new BufferedReader (new FileReader ("Project/src/main/java/statsVisualiser/gui/loginDatabank.txt"));

            // Reading the file line by line.
            line = read.readLine();

            // While the current line is not null, we will do some things.
            while ((line = read.readLine()) != null){
                
                // Split the line according to a space.
                String[] countryData = line.split (" ");

                // Populate the dictionary with the items in the current line.
                credentials.put (countryData[0], countryData[1]);
            }   
        }

        // Catch an IO exception and print out the accompanying error.
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * This method is used to test the login program.
     * @param args Array for the system mandated arguments.
     */
    public static void main(String[] args) {
        new Login(); // Static object so it just needs to be created into memory (and not actually assigned to a specific object).
        getLogins(); // Call the getLogins method.
    }

    /**
     * This method is used to determine if an event has occurred on screen.
     * @param ae ActionEvent used to respond to events.
     */
    public void actionPerformed(ActionEvent ae) {
        
        // Grabbing what the user has entered for the username.
        String userName = userName_text.getText().trim();
        
        // This line is unnecessary for the most part but it can help in ignoring depreciated APIs.
        @SuppressWarnings("deprecation")

        // Grabbing what the user has entered for the password.
		String password = password_text.getText().trim();
       
        // Check if the credentials pair is found in the dictionary. Only if they are can the user access the system.
        if (credentials.containsKey(userName)){
            if (credentials.get(userName).equals(password)){
                try {
                    MainPage.main(null);
                    System.out.println("Success");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            // If the credentials password is not correct then the user is not allowed access.
            else{
                JOptionPane.showMessageDialog(this, " Invalid user. ");
                System.exit(0);
            }
            this.dispose();
        }

        // Otherwise the user has entered something that is not allowed.
        else {
        	JOptionPane.showMessageDialog(this, " Invalid user. ");
            System.exit(0);
        }
    }
}