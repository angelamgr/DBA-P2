package Practica2;

import java.util.ArrayList;

public class Entorno {
	private Mapa map;
	private int[] AgentPos = new int[2];
	private int[] TargetPos = new int[2];

	private ArrayList<Coordenadas> coordenadas = new ArrayList<>();

	public Entorno(Mapa mapa, int[] APos, int[] TPos) {
		map = mapa;
		AgentPos = APos;
		TargetPos = TPos;
		coordenadas.add(new Coordenadas(AgentPos[0], AgentPos[1]));
	}

	public int[] getAgentPos() {
		return AgentPos;
	}

	public int[] getTargetPos() {
		return TargetPos;
	}

	public boolean libreArriba() {
		return !(AgentPos[0] == 0 || map.getValor(AgentPos[0] - 1, AgentPos[1]) == -1);
	}

	public boolean libreAbajo() {
		return !(AgentPos[0] == map.getFila() - 1 || map.getValor(AgentPos[0] + 1, AgentPos[1]) == -1);
	}

	public boolean libreIzda() {
		return !(AgentPos[1] == 0 || map.getValor(AgentPos[0], AgentPos[1] - 1) == -1);
	}

	public boolean libreDcha() {
		return !(AgentPos[1] == map.getColumna() - 1 || map.getValor(AgentPos[0], AgentPos[1] + 1) == -1);
	}

	public void actualizarAgente(int fila, int columna) {
		AgentPos[0] = fila;
		AgentPos[1] = columna;
		coordenadas.add(new Coordenadas(fila, columna));
	}

	public void mostrarRecorrido() {
		for (int i = 0; i < coordenadas.size(); i++) {
			map.setValor(coordenadas.get(i).getX(), coordenadas.get(i).getY(), 5);
		}
	}
	
	public ArrayList<Coordenadas> getCoordenadas(){
		return coordenadas;
	}

	public int funcionHeuristica(int[] posAgente, int[] posObjetivo){
		return Math.abs(posAgente[0] - posObjetivo[0]) + Math.abs(posAgente[1] - posObjetivo[1]);
	}

	public int funcionHeuristicaConObstaculos(int[] posAgente, int[] posObjetivo) {
		int heuristica = funcionHeuristica(posAgente, posObjetivo);
		for (Coordenadas coord : coordenadas) {
			if (coord.getX() == posAgente[0] && coord.getY() == posAgente[1]) {
				heuristica += 10; // Incrementa el costo si ya has pasado por esta casilla
			}
		}
		return heuristica;
	}

	public Direccion agentePensar(){
		int[] posAgente = getAgentPos();
		int[] posObjetivo = getTargetPos();
		int costo = 0;
		int costoMovimiento = 0;
		int costoTotal = 0;
		Direccion direccion = null;
		Direccion direccionFinal = null;
		int costoFinal = Integer.MAX_VALUE;
		if(libreArriba()){
			costo = funcionHeuristicaConObstaculos(new int[]{posAgente[0] - 1, posAgente[1]}, posObjetivo);
			costoMovimiento = costo + 1;
			costoTotal = costoMovimiento;
			if(costoTotal < costoFinal){
				costoFinal = costoTotal;
				direccionFinal = Direccion.ARRIBA;
			}
		}
		if(libreAbajo()){
			costo = funcionHeuristicaConObstaculos(new int[]{posAgente[0] + 1, posAgente[1]}, posObjetivo);
			costoMovimiento = costo + 1;
			costoTotal = costoMovimiento;
			if(costoTotal < costoFinal){
				costoFinal = costoTotal;
				direccionFinal = Direccion.ABAJO;
			}
		}
		if(libreIzda()){
			costo = funcionHeuristicaConObstaculos(new int[]{posAgente[0], posAgente[1] - 1}, posObjetivo);
			costoMovimiento = costo + 1;
			costoTotal = costoMovimiento;
			if(costoTotal < costoFinal){
				costoFinal = costoTotal;
				direccionFinal = Direccion.IZQUIERDA;
			}
		}
		if(libreDcha()){
			costo = funcionHeuristicaConObstaculos(new int[]{posAgente[0], posAgente[1] + 1}, posObjetivo);
			costoMovimiento = costo + 1;
			costoTotal = costoMovimiento;
			if(costoTotal < costoFinal){
				costoFinal = costoTotal;
				direccionFinal = Direccion.DERECHA;
			}
		}
		return direccionFinal;
	}
}