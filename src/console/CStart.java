package console;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import plugins.LoadPlugins;

public class CStart {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void main(String[] args) {
		//Display MOTD
		File motdFile = new File("res\\motd");
		try {
			BufferedReader in = new BufferedReader(new FileReader(motdFile));
			String line = in.readLine();
			while(line != null)
			{
			  System.out.println(line);
			  line = in.readLine();
			}
			in.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			printSystemMessage("MOTD FILE(res/motd) NOT FOUND");
		}catch(IOException e){
			printSystemMessage("ERROR READING MOTD FILE");
		}
		//INSTANTIATION THINGS
		Scanner sc = new Scanner(System.in);
		boolean running = true;
		try {
			LoadPlugins.load();
		} catch (InstantiationException e) {
			System.err.println("INSTANTIATIONEXCEPTION THROWN! Probably an error Instantiating the Class containing the Command");
		} catch (IllegalAccessException e) {
			System.err.println("ILLEGALACCESSEXCEPTION THROWN!");
		} catch (ClassNotFoundException e) {
			System.err.println("Plugin-Container Class not found");
		} catch (IOException e) {
			System.err.println("IO ERROR");
		} catch (NullPointerException e) {
			System.err.println("NULL POINTER ERROR");
		}
		// MAIN LOOP
		while(running){
			//PRINTING THE INDICATOR
			System.out.print(">> ");
			//RETRIEVING THE COMMAND PARTS
			String commandLine = sc.nextLine();
			if(commandLine.equals("exit")){running=false;break;}
			String[] clParts = commandLine.split(" ");
			String actualCommand = clParts[0];
			List<String> argumentParts = new ArrayList<String>();
			//Part splitting
			for(String part:clParts){
				if(!part.equals(actualCommand)){
					argumentParts.add(part);
				}
			}
			//ATTEMPTED EXECUTION
			try{
				LoadPlugins.doPluginCommand(actualCommand, argumentParts.toArray(new String[argumentParts.size()]));
			}catch(NullPointerException npe){
				printSystemMessage("ERROR: CLASS NOT FOUND");
			}
		}
		sc.close();
	}
	public static void printSystemMessage(String s){
		System.out.println("[@]: "+s);
	}

}
