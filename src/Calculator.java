/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author admin
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class  Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private JPanel gridPanel;
    private JButton clearButton;
    private StringBuilder currentInput;
    private double firstOperand = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    public Calculator() {
        // Set Nimbus Look and Feel for a modern UI appearance
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, default look and feel will be used.
        }

        setTitle("Calculator");
        setSize(350, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(230, 230, 250)); // light lavender background

        // Create display field with padding and a border
        display = new JTextField();
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Verdana", Font.BOLD, 28));
        display.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(display, BorderLayout.NORTH);

        // Create a grid panel for the main calculator buttons
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(4, 4, 8, 8));
        gridPanel.setBackground(new Color(230, 230, 250));
        gridPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Define button labels for digits and operators
        String[] buttons = {"7", "8", "9", "/",
                            "4", "5", "6", "*",
                            "1", "2", "3", "-",
                            "0", ".", "=", "+"};

        // Create and style buttons
        for (String label : buttons) {
            JButton button = new JButton(label);
            button.setFont(new Font("Verdana", Font.BOLD, 24));
            button.setBackground(new Color(255, 250, 240)); // floral white
            button.setForeground(Color.DARK_GRAY);
            button.setFocusPainted(false);
            button.addActionListener(this);
            gridPanel.add(button);
        }
        add(gridPanel, BorderLayout.CENTER);

        // Create a panel for the clear button with some spacing
        JPanel clearPanel = new JPanel();
        clearPanel.setBackground(new Color(230, 230, 250));
        clearPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
        clearButton = new JButton("C");
        clearButton.setFont(new Font("Verdana", Font.BOLD, 24));
        clearButton.setBackground(new Color(255, 182, 193)); // light pink
        clearButton.setForeground(Color.DARK_GRAY);
        clearButton.setFocusPainted(false);
        clearButton.addActionListener(this);
        clearPanel.add(clearButton);
        add(clearPanel, BorderLayout.SOUTH);

        currentInput = new StringBuilder();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // If a number or decimal point is pressed, append to current input
        if (command.matches("[0-9\\.]")) {
            if (startNewNumber) {
                currentInput.setLength(0);
                startNewNumber = false;
            }
            currentInput.append(command);
            display.setText(currentInput.toString());
        } 
        // If clear button is pressed, reset all values
        else if (command.equals("C")) {
            firstOperand = 0;
            operator = "";
            currentInput.setLength(0);
            display.setText("");
            startNewNumber = true;
        } 
        // If equals is pressed, perform the calculation
        else if (command.equals("=")) {
            if (!operator.isEmpty() && currentInput.length() > 0) {
                double secondOperand = Double.parseDouble(currentInput.toString());
                double result = calculate(firstOperand, secondOperand, operator);
                display.setText(String.valueOf(result));
                // Prepare for next calculation
                firstOperand = result;
                operator = "";
                startNewNumber = true;
            }
        } 
        // If an operator button is pressed
        else {
            if (currentInput.length() > 0) {
                if (!operator.isEmpty()) {
                    // Chain calculations if needed
                    double secondOperand = Double.parseDouble(currentInput.toString());
                    firstOperand = calculate(firstOperand, secondOperand, operator);
                    display.setText(String.valueOf(firstOperand));
                } else {
                    firstOperand = Double.parseDouble(currentInput.toString());
                }
                operator = command;
                startNewNumber = true;
            }
        }
    }

    // Method to perform arithmetic operations
    private double calculate(double a, double b, String op) {
        switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) {
                    JOptionPane.showMessageDialog(this, "Division by zero error", "Error", JOptionPane.ERROR_MESSAGE);
                    return a;
                }
                return a / b;
            default:
                return b;
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calc = new Calculator();
            calc.setVisible(true);
        });
    }
}
