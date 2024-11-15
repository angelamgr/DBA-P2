package practica2;

import jade.core.Profile;
import jade.core.ProfileImpl;
import java.io.FileNotFoundException;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import javax.swing.SwingUtilities;

/* public class Practica2 {
    
    public static void main(String[] args) throws FileNotFoundException, InterruptedException, StaleProxyException {
        // Cargar el mapa desde el archivo proporcionado en los argumentos
        Mundo mundo = new Mundo(args[0]);
        
        // Configurar la posición inicial del agente y la posición objetivo
        int inicioX = 4, inicioY = 1;  // posición de inicio del agente
        int objetivoX = mundo.filas - 5, objetivoY = mundo.columnas - 2;  // posición del objetivo
        Entorno entorno = new Entorno(mundo, inicioX, inicioY, objetivoX, objetivoY);

        // Configurar y lanzar JADE
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "127.0.0.1");
        p.setParameter(Profile.CONTAINER_NAME,"contenedor");
        ContainerController cc = rt.createAgentContainer(p);

        // Pasar el entorno al agente como argumento
        Object[] a = new Object[1];
        a[0] = entorno;
        AgentController ac = cc.createNewAgent("AgenteMovil", Agente.class.getCanonicalName(), a);
        ac.start();
    }

    // Método para mostrar el mapa en la consola con la posición del agente y el objetivo
    public static void refrescarMapa(Entorno entorno, Mundo mundo) {
        limpiarConsola();
        System.out.println("\nMapa:");
        for (int i = 0; i < mundo.filas; i++) {
            for (int j = 0; j < mundo.columnas; j++) {
                if (i == entorno.getAgenteX() && j == entorno.getAgenteY()) {
                    System.out.print("A  ");  // Mostrar la posición del agente
                } else if (i == entorno.getObjetivoX() && j == entorno.getObjetivoY()) {
                    System.out.print("G  ");  // Mostrar la posición del objetivo
                } else if (mundo.getCasilla(i, j) == -1) {
                    System.out.print("X  ");  // Obstáculo
                } else {
                    System.out.print(".  ");  // Celda libre
                }
            }
            System.out.println();
        }
    }

    public static void limpiarConsola() {
      System.out.print("\033[H\033[2J");  
      System.out.flush();  
    }
} */

public class Practica2 {
    
    public static void main(String[] args) throws FileNotFoundException, InterruptedException, StaleProxyException {
        // Cargar el mapa desde el archivo proporcionado en los argumentos
        Mundo mundo = new Mundo(args[0]);
        
        // Configurar la posición inicial del agente y la posición objetivo
        Coordenadas inicio = new Coordenadas(16, 10);  // posición de inicio del agente
        Coordenadas objetivo = new Coordenadas(10, 10);  // posición del objetivo

        Entorno entorno = new Entorno(mundo, inicio, objetivo);
        Controlador controlador = new Controlador(entorno, null);
        GUI gui = new GUI(controlador, mundo);
        controlador.setGUI(gui);

        // Configurar y lanzar JADE
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "127.0.0.1");
        p.setParameter(Profile.CONTAINER_NAME,"contenedor");
        ContainerController cc = rt.createAgentContainer(p);

        // Pasar el entorno al agente como argumento
        Object[] a = new Object[2];
        a[0] = entorno;
        a[1] = controlador;
        AgentController ac = cc.createNewAgent("AgenteMovil", Agente.class.getCanonicalName(), a);
        controlador.setAgent(ac);
        controlador.getAgent().start();

        SwingUtilities.invokeLater(() -> {
            gui.setVisible(true);
            gui.requestFocusInWindow(); // Asegurarse de que la GUI tenga el foco para recibir eventos de teclado
        });
    }

    /* // Método para mostrar el mapa en la consola con la posición del agente y el objetivo
    public static void refrescarMapa(Entorno entorno, Mundo mundo) {
        limpiarConsola();
        System.out.println("\nMapa:");
        for (int i = 0; i < mundo.filas; i++) {
            for (int j = 0; j < mundo.columnas; j++) {
                if (i == entorno.getAgenteX() && j == entorno.getAgenteY()) {
                    System.out.print("A  ");  // Mostrar la posición del agente
                } else if (i == entorno.getObjetivoX() && j == entorno.getObjetivoY()) {
                    System.out.print("G  ");  // Mostrar la posición del objetivo
                } else if (mundo.getCasilla(i, j) == -1) {
                    System.out.print("X  ");  // Obstáculo
                } else {
                    System.out.print(".  ");  // Celda libre
                }
            }
            System.out.println();
        }
    }

    public static void limpiarConsola() {
      System.out.print("\033[H\033[2J");  
      System.out.flush();  
    } */
}