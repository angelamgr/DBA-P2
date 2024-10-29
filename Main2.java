package Practica2;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main2 {
    public static void main(String[] args) {
        Mapa mapa = new Mapa("mapWithComplexObstacle2.txt"); // Asumiendo que tienes una clase Mapa
        int[] APos = {5, 7}; // Posición inicial del agente
        int[] TPos = {3, 5}; // Posición del objetivo
        Entorno entorno = new Entorno(mapa, APos, TPos);
        Controlador controlador = new Controlador(entorno, null);
        GUI gui = new GUI(controlador, mapa);
        controlador.setGUI(gui);

        gui.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    controlador.agentePensar();
                }
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui.setVisible(true);
                gui.requestFocusInWindow(); // Asegurarse de que la GUI tenga el foco para recibir eventos de teclado
            }
        });
    }
}