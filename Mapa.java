/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Practica2;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author adrianrv007
 */
public class Mapa {
	private int fils, cols;
	private int [][] matriz2D;
	
	Mapa(String mapa)
	{
		//Usamos getResourceAsStream para cargar el archivo de la classpath 
		InputStream input = getClass().getClassLoader().getResourceAsStream("Practica2/Mapas/" + mapa);
		
		if (input == null){
			System.out.println("El archivo no se pudo encontrar. Revise que el nombre del archivo es correcto.");
			return;
		}
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(input))){
			fils = Integer.parseInt(br.readLine().trim());
			cols = Integer.parseInt(br.readLine().trim());
			matriz2D = new int [fils][cols];
			
			for (int i = 0; i < fils; i++){
				String[] datosFila = br.readLine().trim().split("\\s+");
				
				for (int j = 0; j < cols; j++){
					matriz2D[i][j] = Integer.parseInt(datosFila[j]);
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public int getValor(int fila, int columna)
	{
		return matriz2D[fila][columna];
	}
	
	public void setValor(int fila, int columna, int valor)
	{
		matriz2D[fila][columna] = valor;
	}
	
	public int getFila()
	{
		return fils;
	}
	
	public int getColumna()
	{
		return cols;
	}
	
	public void pintarMapa()
	{
		for (int i = 0; i < fils; i++){
			for (int j = 0; j < cols; j++){
				System.out.print(matriz2D[i][j] + "\t");
			}
			
			System.out.println();
		}
	}
	
	public void posicionarAgente(int fila, int columna)
	{
		if (matriz2D[fila][columna] == 0)
			matriz2D[fila][columna] = 1;
		else
			System.out.println("Esa posición no es válida");
	}
	
	public void posicionarObjetivo(int fila, int columna)
	{
		if (matriz2D[fila][columna] == 0)
			matriz2D[fila][columna] = 2;
	}
}
