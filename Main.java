/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Practica2;

import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;

import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

/**
 *
 * @author adrianrv007
 */
public class Main {
	public static void main(String args[])
	{
		int [] AgentPos = new int [2];
		int [] TargetPos = new int [2];
		
		String input;
		String [] aux;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
/******************************************************************************/

//		Runtime rt = jade.core.Runtime.instance();
//        
//        Profile p = new ProfileImpl();
//        p.setParameter(Profile.MAIN_HOST, "localhost");
//        p.setParameter(Profile.CONTAINER_NAME, "dba_server");
//		
//		ContainerController cc = rt.createAgentContainer(p);
		
		try {
			System.out.print("Introduzca la posicion inicial del Agente (fila columna): ");
			input = br.readLine();
			
			aux = input.trim().split("\\s+");
			AgentPos[0] = Integer.parseInt(aux[0]);
			AgentPos[1] = Integer.parseInt(aux[1]);
			
			System.out.print("Introduzca la posicion final del Agente (fila columna): ");
			input = br.readLine();
			
			aux = input.trim().split("\\s+");
			TargetPos[0] = Integer.parseInt(aux[0]);
			TargetPos[1] = Integer.parseInt(aux[1]);
			
//			AgentController agent = cc.createNewAgent("Agent", Agente.class.getCanonicalName(), null);
//			agent.start();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		catch(NumberFormatException e){
			System.out.println("Error: introduce números válidos.");
		}
//		catch(StaleProxyException e){
//			System.out.println("Error al crear el agente: " + e.getMessage());
//		}
            
/******************************************************************************/
		
		Mapa mapaSinObstaculos = new Mapa("mapWithoutObstacle.txt");
		
		mapaSinObstaculos.posicionarAgente(AgentPos[0], AgentPos[1]);
		mapaSinObstaculos.posicionarObjetivo(TargetPos[0], TargetPos[1]);
		
//		mapaSinObstaculos.posicionarAgente(2, 3);
//		mapaSinObstaculos.setValor(2, 2, 5);
		
		mapaSinObstaculos.pintarMapa();
		
		Entorno entorno = new Entorno(mapaSinObstaculos, AgentPos, TargetPos);
		
		System.out.println(entorno.libreArriba());
	}
}
