import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private JTextField initialPositionField;
    private JTextField finalPositionField;
    private JButton submitButton;

    public GUI() {
        setTitle("Agent Position Input");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        // Create components
        JLabel initialPositionLabel = new JLabel("Posición inicial (fila columna):");
        initialPositionField = new JTextField();
        JLabel finalPositionLabel = new JLabel("Posición final (fila columna):");
        finalPositionField = new JTextField();
        submitButton = new JButton("Submit");

        // Add components to frame
        add(initialPositionLabel);
        add(initialPositionField);
        add(finalPositionLabel);
        add(finalPositionField);
        add(submitButton);

        // Add button action listener
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String initialPosition = initialPositionField.getText();
                String finalPosition = finalPositionField.getText();

                // Process the input
                String[] initialPos = initialPosition.trim().split("\\s+");
                String[] finalPos = finalPosition.trim().split("\\s+");

                int[] AgentPos = new int[2];
                int[] TargetPos = new int[2];

                AgentPos[0] = Integer.parseInt(initialPos[0]);
                AgentPos[1] = Integer.parseInt(initialPos[1]);
                TargetPos[0] = Integer.parseInt(finalPos[0]);
                TargetPos[1] = Integer.parseInt(finalPos[1]);

                // Print the positions to the console
                System.out.println("Posición inicial del Agente: " + AgentPos[0] + ", " + AgentPos[1]);
                System.out.println("Posición final del Agente: " + TargetPos[0] + ", " + TargetPos[1]);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}