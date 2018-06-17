package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/*
 * Ce client se connecte � un serveur dont le protocole est 
 * menu-choix-question-r�ponse client-r�ponse service
 * il n'y a qu'un �change (pas de boucle)
 * la r�ponse est saisie au clavier en String
 * 
 * Le protocole d'�change est suffisant pour le tp4 avec ServiceInversion 
 * ainsi que tout service qui pose une question, traite la donn�e du client et envoie sa r�ponse 
 * mais est bien sur susceptible de (nombreuses) am�liorations
 */
class Application {
		private final static int PORT_AMA=3000;
		private final static int PORT_PROG=3500;
		private final static String HOST = "localhost"; 
	
	public static void main(String[] args) {
		Socket s = null;		
		String choix;
		int port =0;
		
		Scanner cl = new Scanner(System.in);
		
		do {
			System.out.println("Bonjour Client, �tes vous 1 : amateur ou 2: developpeur?");
			choix = cl.next();
		} while (!(choix.equals("1")||choix.equals("2")));
		
		if(choix.equals("1")){
			port=PORT_AMA;
		}else if (choix.equals("2")){
			port=PORT_PROG;
		}
		
		try {
			s = new Socket(HOST, port);
			BufferedReader sin = new BufferedReader (new InputStreamReader(s.getInputStream ( )));
			PrintWriter sout = new PrintWriter (s.getOutputStream ( ), true);
			BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));			
		
			System.out.println("Connect� au serveur " + s.getInetAddress() + ":"+ s.getPort());
			
			String line;
			
			
			
			while (true){
				
				// reception + affichage
					line = sin.readLine();
					System.out.println(line.replaceAll("##", "\n"));					
				// saisie + envoie 
					sout.println(clavier.readLine());
			}
		} catch (IOException e) { System.err.println("Fin de la connexion"); }
		// Refermer dans tous les cas la socket
		try { if (s != null) s.close(); } 
		catch (IOException e2) { ; }
		
		
		
		
//		
//		try {
//			s = new Socket(HOST, PORT_AMA);
//
//			BufferedReader sin = new BufferedReader (new InputStreamReader(s.getInputStream ( )));
//			PrintWriter sout = new PrintWriter (s.getOutputStream ( ), true);
//			BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));			
//		
//			System.out.println("Connect� au serveur " + s.getInetAddress() + ":"+ s.getPort());
//			
//			String line;
//		// menu et choix du service
//			line = sin.readLine();
//			System.out.println(line.replaceAll("##", "\n"));
//		// saisie/envoie du choix
//			sout.println(clavier.readLine());
//			
//		// r�ception/affichage de la question
//			System.out.println(sin.readLine());
//		// saisie clavier/envoie au service de la r�ponse
//			sout.println(clavier.readLine());
//		// r�ception/affichage de la r�ponse
//			System.out.println(sin.readLine());
//				
//			
//		}
//		catch (IOException e) { System.err.println("Fin de la connexion"); }
//		// Refermer dans tous les cas la socket
//		try { if (s != null) s.close(); } 
//		catch (IOException e2) { ; }		
	}
}
