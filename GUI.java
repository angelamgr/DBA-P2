package Practica2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUI extends JFrame {
    private MapPanel mapPanel;
    private Controlador controlador;
    private JLabel energiaLabel;

    public GUI(Controlador controlador, Mapa mapa) {
        this.controlador = controlador;
        setTitle("Mapa");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configurar el layout del JFrame
        setLayout(new BorderLayout());

        // Añadir el MapPanel al JFrame
        mapPanel = new MapPanel(controlador.getAgentPos()[0], controlador.getAgentPos()[1],
                controlador.getTargetPos()[0], controlador.getTargetPos()[1], mapa);
        add(mapPanel, BorderLayout.CENTER);

        // Crear y añadir la etiqueta de energía del agente
        energiaLabel = new JLabel("Energía del agente: " + controlador.getEnergia());
        JPanel energiaPanel = new JPanel();
        energiaPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        energiaPanel.add(energiaLabel);
        add(energiaPanel, BorderLayout.SOUTH);
    }

    public void actualizarMapa() {
        mapPanel.actualizarAgente(controlador.getAgentPos()[0], controlador.getAgentPos()[1],
                controlador.getCoordenadas());
        mapPanel.repaint();
        energiaLabel.setText("Energía del agente: " + controlador.getEnergia());
    }
}

class MapPanel extends JPanel {
    private Coordenadas agentePos;
    private Coordenadas targetPos;
    private Mapa mapa;
    final int FILAS_MAPA, COLUMNAS_MAPA;
    private ArrayList<Coordenadas> coordenadas = new ArrayList<>();
    private String direccion = "UP"; // Dirección inicial del agente

    public MapPanel(int filaAgente, int columnaAgente, int filaTarget, int columnaTarget, Mapa mapa) {
        agentePos = new Coordenadas(filaAgente, columnaAgente);
        targetPos = new Coordenadas(filaTarget, columnaTarget);
        this.mapa = mapa;
        FILAS_MAPA = mapa.getFila();
        COLUMNAS_MAPA = mapa.getColumna();
    }

    public void actualizarAgente(int fila, int columna, ArrayList<Coordenadas> coordenadas) {
        // Determinar la dirección del movimiento
        if (columna < agentePos.getY()) {
            direccion = "LEFT";
        } else if (columna > agentePos.getY()) {
            direccion = "RIGHT";
        } else if (fila < agentePos.getX()) {
            direccion = "UP";
        } else if (fila > agentePos.getX()) {
            direccion = "DOWN";
        }

        agentePos.setX(fila);
        agentePos.setY(columna);
        this.coordenadas = coordenadas;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellSize = 40;
        // Añadimos un offset para centrar el mapa
        int offsetX = (getWidth() - COLUMNAS_MAPA * cellSize) / 2;
        int offsetY = (getHeight() - FILAS_MAPA * cellSize) / 2;


        // Dibujar la cuadrícula
        for (int row = 0; row < FILAS_MAPA; row++) {
            for (int col = 0; col < COLUMNAS_MAPA; col++) {
            int x = col * cellSize + offsetX;
            int y = row * cellSize + offsetY;

            // Colorear las celdas con valor -1 de gris
            if (mapa.getValor(row, col) == -1) {
                g.setColor(Color.GRAY);
                g.fillRect(x, y, cellSize, cellSize);
            }

            g.setColor(Color.BLACK);
            g.drawRect(x, y, cellSize, cellSize);
            }
        }

        // Dibujar el agente como un triángulo dentro de la celda
        int x = agentePos.getY() * cellSize + offsetX;
        int y = agentePos.getX() * cellSize + offsetY;
        int[] xPoints;
        int[] yPoints;

        switch (direccion) {
            case "LEFT":
            xPoints = new int[]{x + cellSize, x + cellSize, x};
            yPoints = new int[]{y, y + cellSize, y + cellSize / 2};
            break;
            case "RIGHT":
            xPoints = new int[]{x, x, x + cellSize};
            yPoints = new int[]{y, y + cellSize, y + cellSize / 2};
            break;
            case "DOWN":
            xPoints = new int[]{x, x + cellSize, x + cellSize / 2};
            yPoints = new int[]{y, y, y + cellSize};
            break;
            case "UP":
            default:
            xPoints = new int[]{x, x + cellSize, x + cellSize / 2};
            yPoints = new int[]{y + cellSize, y + cellSize, y};
            break;
        }

        g.setColor(Color.RED);
        g.fillPolygon(xPoints, yPoints, 3);

        // Dibujar el objetivo como un círculo dentro de la celda
        x = targetPos.getY() * cellSize + offsetX;
        y = targetPos.getX() * cellSize + offsetY;
        g.setColor(Color.BLUE);
        g.fillOval(x, y, cellSize, cellSize);

        // Dibujar las casillas visitadas por el agente
        g.setColor(Color.YELLOW);
        for (int i = 0; i < coordenadas.size() - 1; i++) {
            Coordenadas coord = coordenadas.get(i);
            x = coord.getY() * cellSize + offsetX;
            y = coord.getX() * cellSize + offsetY;
            g.fillOval(x + cellSize / 4, y + cellSize / 4, cellSize / 2, cellSize / 2);
        }
    }
}