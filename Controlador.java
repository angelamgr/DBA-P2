package Practica2;

import java.util.ArrayList;

public class Controlador {
    private Entorno entorno;
    private GUI gui;

    public Controlador(Entorno entorno, GUI gui) {
        this.entorno = entorno;
        this.gui = gui;
    }

    public ArrayList<Coordenadas> getCoordenadas() {
        return entorno.getCoordenadas();
    }

    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public int[] getAgentPos() {
        return entorno.getAgentPos();
    }

    public int[] getTargetPos() {
        return entorno.getTargetPos();
    }

    public void iniciarEntorno() {
        entorno.mostrarRecorrido();
    }

    public boolean moverAgenteArriba() {
        if (entorno.libreArriba()) {
            entorno.actualizarAgente(entorno.getAgentPos()[0] - 1, entorno.getAgentPos()[1]);
            gui.actualizarMapa();
            verificarObjetivo();
            return true;
        }
        System.out.println("No se pudo mover el agente arriba");
        return false;
    }

    public boolean moverAgenteAbajo() {
        if (entorno.libreAbajo()) {
            entorno.actualizarAgente(entorno.getAgentPos()[0] + 1, entorno.getAgentPos()[1]);
            gui.actualizarMapa();
            verificarObjetivo();
            return true;
        }
        System.out.println("No se pudo mover el agente abajo");
        return false;
    }

    public boolean moverAgenteIzquierda() {
        if (entorno.libreIzda()) {
            entorno.actualizarAgente(entorno.getAgentPos()[0], entorno.getAgentPos()[1] - 1);
            gui.actualizarMapa();
            verificarObjetivo();
            return true;
        }
        System.out.println("No se pudo mover el agente a la izquierda");
        return false;
    }

    public boolean moverAgenteDerecha() {
        if (entorno.libreDcha()) {
            entorno.actualizarAgente(entorno.getAgentPos()[0], entorno.getAgentPos()[1] + 1);
            gui.actualizarMapa();
            verificarObjetivo();
            return true;
        }
        System.out.println("No se pudo mover el agente a la derecha");
        return false;
    }

    public void verificarObjetivo() {
        if (entorno.getAgentPos()[0] == entorno.getTargetPos()[0]
                && entorno.getAgentPos()[1] == entorno.getTargetPos()[1]) {
            // Se abre un JOptionPane para mostrar un mensaje de éxito y la energía
            // consumida
            int energiaConsumida = getEnergia();
            javax.swing.JOptionPane.showMessageDialog(null,
                    "¡Objetivo alcanzado!\nEnergía consumida: " + energiaConsumida, "¡Felicidades!",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            // Si el jugador presiona OK, se cierra la aplicación
            System.exit(0);
        }
    }

    public int getEnergia() {
        return entorno.getCoordenadas().size() - 1;
    }

    public void agentePensar() {
        Direccion decisionAgente = entorno.agentePensar();
        switch (decisionAgente) {
            case ARRIBA:
                moverAgenteArriba();
                break;
            case ABAJO:
                moverAgenteAbajo();
                break;
            case IZQUIERDA:
                moverAgenteIzquierda();
                break;
            case DERECHA:
                moverAgenteDerecha();
                break;
            default:
                System.out.println("Decisión no válida");
                break;
        }
    }
}

