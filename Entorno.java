/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Practica2;

import java.util.ArrayList;

/**
 *
 * @author adrianrv007
 */
public class Entorno {
	private Mapa map;
	private int [] AgentPos = new int [2];
	private int [] TargetPos = new int [2];
	
	private ArrayList<Coordenadas> coordenadas = new ArrayList<>();
	
	Entorno(Mapa mapa, int [] APos, int [] TPos)
	{
		map = mapa;
		AgentPos = APos;
		TargetPos = TPos;
		coordenadas.add(new Coordenadas(AgentPos[0], AgentPos[1]));
	}
	
	boolean libreArriba()
	{		
		return !(AgentPos[0] == 0 || map.getValor(AgentPos[0]-1, AgentPos[1]) == -1);
	}
	
	boolean libreAbajo()
	{		
		return !(AgentPos[0] == map.getFila() || map.getValor(AgentPos[0]+1, AgentPos[1]) == -1);
	}
	
	boolean libreIzda()
	{
		return !(AgentPos[1] == 0 || map.getValor(AgentPos[0], AgentPos[1]-1) == -1);
	}
	
	boolean libreDcha()
	{
		return !(AgentPos[1] == map.getColumna() || map.getValor(AgentPos[0], AgentPos[1]+1) == -1);
	}
	
	public void actualizarAgente(int fila, int columna)
	{
		AgentPos[0] = fila;
		AgentPos[1] = columna;
		coordenadas.add(new Coordenadas(fila, columna));
		
	}
	
	public void mostrarRecorrido()
	{
		for (int i = 0; i < coordenadas.size(); i++){
			map.setValor(coordenadas.get(i).getX(), coordenadas.get(i).getY(), 5);
		}
	}
}
