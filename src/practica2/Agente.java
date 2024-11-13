package practica2;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import java.util.ArrayList;

public class Agente extends Agent {
    private Entorno entorno;
    private Controlador controlador;

    private Coordenadas ActualPos;
    private Coordenadas TargetPos;
    private Coordenadas InitPos;

    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length >= 1) {
            this.entorno = (Entorno) args[0];
            this.controlador = (Controlador) args[1];
            this.InitPos = entorno.getAgentPos();
            this.TargetPos = entorno.getTargetPos();
            this.ActualPos = InitPos;
            
            // Comportamiento de movimiento con un intervalo de 500 ms
            addBehaviour(new MovimientoBehaviour(this, 400));
        }
    }

    // Comportamiento para el movimiento del agente
    private class MovimientoBehaviour extends TickerBehaviour {
        public MovimientoBehaviour(Agent a, long period) {
            super(a, period);
        }

        @Override
        protected void onTick() {

            // Prioridad de movimiento hacia el objetivo
            Direccion direccion = agentePensar();
            boolean movido = false;
            if (direccion != null) {
                switch (direccion) {
                    case ARRIBA:
                        movido = entorno.moverAgente(-1, 0);
                        break;
                    case ABAJO:
                        movido = entorno.moverAgente(1, 0);
                        break;
                    case IZQUIERDA:
                        movido = entorno.moverAgente(0, -1);
                        break;
                    case DERECHA:
                        movido = entorno.moverAgente(0, 1);
                        break;
                }
            }

            if (!movido) {
                System.out.println("Obstáculo detectado. Buscando una nueva ruta...");
            } else{
                controlador.refrescarMapa();
            }

            // Verificar si el objetivo ha sido alcanzado y detener el comportamiento
            if (entorno.objetivoAlcanzado()) {
                // Se abre un JOptionPane para mostrar un mensaje de éxito y la energía consumida
                int energiaConsumida = entorno.getEnergia();
                javax.swing.JOptionPane.showMessageDialog(null,
                        "¡Objetivo alcanzado!\nEnergía consumida: " + energiaConsumida, "¡Felicidades!",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        stop();  // Finalizar el comportamiento al presionar Ok
                        System.exit(0);
            }
        }
    }

    public int funcionHeuristica(Coordenadas posAgente, Coordenadas posObjetivo) {
        return Math.abs(posAgente.getX() - posObjetivo.getX())
                        + Math.abs(posAgente.getY() - posObjetivo.getY());
    }

    public int funcionHeuristicaConObstaculos(Coordenadas posAgente, Coordenadas posObjetivo) {
            int heuristica = funcionHeuristica(posAgente, posObjetivo);
            for (Coordenadas coord : entorno.getCoordenadas()) {
                if(posAgente.getX() == coord.getX() && posAgente.getY() == coord.getY() &&
                  (!entorno.libreArriba() || !entorno.libreAbajo() || !entorno.libreDcha() || !entorno.libreIzda()))
                {
                    heuristica += 10;
                }
                else if (posAgente.getX() == coord.getX() && posAgente.getY() == coord.getY()) {
                            heuristica += 10; // Incrementa el costo si ya has pasado por esta casilla
                    }
                else if (posAgente.getX() != coord.getX() || posAgente.getY() != coord.getY()){
                    heuristica -=20;
                }
            }
            return heuristica;
    }

    public Direccion agentePensar() {
    // Si ya alcanzó el objetivo, no hace nada
    if (entorno.objetivoAlcanzado()) {
        return null; // No hacer nada si ya está en el objetivo
    }

    // Listar las celdas vecinas que son libres
    ArrayList<Coordenadas> vecinos = new ArrayList<>();
    if (entorno.libreArriba()) vecinos.add(new Coordenadas(ActualPos.getX() - 1, ActualPos.getY())); // Arriba
    if (entorno.libreAbajo()) vecinos.add(new Coordenadas(ActualPos.getX() + 1, ActualPos.getY())); // Abajo
    if (entorno.libreIzda()) vecinos.add(new Coordenadas(ActualPos.getX(), ActualPos.getY() - 1)); // Izquierda
    if (entorno.libreDcha()) vecinos.add(new Coordenadas(ActualPos.getX(), ActualPos.getY() + 1)); // Derecha

    // Si no hay movimientos válidos, no puede hacer nada
    if (vecinos.isEmpty()) {
        return null; // No se puede mover
    }

    // Evaluar el mejor vecino usando la heurística
    Coordenadas mejorMovimiento = null;
    int mejorHeuristica = Integer.MAX_VALUE;
    Direccion mejorDireccion = null;

    for (Coordenadas vecino : vecinos) {
        // Calcular la heurística (distancia Manhattan al objetivo)
        int heuristica = funcionHeuristicaConObstaculos(vecino, TargetPos);
        
        // Si el vecino tiene mejor heurística, actualizamos la mejor dirección
        if (heuristica < mejorHeuristica) {
            mejorHeuristica = heuristica;
            mejorMovimiento = vecino;
            // Determinamos la dirección a la que se debe mover
            if (vecino.getX() == ActualPos.getX() - 1) {
                mejorDireccion = Direccion.ARRIBA;
            } else if (vecino.getX() == ActualPos.getX() + 1) {
                mejorDireccion = Direccion.ABAJO;
            } else if (vecino.getY() == ActualPos.getY() - 1) {
                mejorDireccion = Direccion.IZQUIERDA;
            } else if (vecino.getY() == ActualPos.getY() + 1) {
                mejorDireccion = Direccion.DERECHA;
            }
        }
    }

    // Si encontró el mejor movimiento, lo ejecuta
    if (mejorMovimiento != null) {
        ActualPos = mejorMovimiento;
        return mejorDireccion; // Retorna la dirección más óptima
    }

    return null; // No hay movimiento disponible
}

    private Coordenadas obtenerNuevaPosicion(Coordenadas pos, Direccion direccion) {
        switch (direccion) {
            case ARRIBA:
                return new Coordenadas(pos.getX() - 1, pos.getY());
            case ABAJO:
                return new Coordenadas(pos.getX() + 1, pos.getY());
            case IZQUIERDA:
                return new Coordenadas(pos.getX(), pos.getY() - 1);
            case DERECHA:
                return new Coordenadas(pos.getX(), pos.getY() + 1);
            default:
                return pos;
        }
    }

    private boolean esMovimientoValido(Direccion direccion) {
        switch (direccion) {
            case ARRIBA:
                return entorno.libreArriba();
            case DERECHA:
                return entorno.libreDcha();
            case ABAJO:
                return entorno.libreAbajo();
            case IZQUIERDA:
                return entorno.libreIzda();
            default:
                return false;
        }
    }
}
