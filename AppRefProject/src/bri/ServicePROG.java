package bri;


import java.io.*;
import java.net.*;
import java.util.ArrayList;

import user.Developpeur;


public class ServicePROG implements Runnable {
	private ArrayList<Developpeur> developpeurs ;
	private Socket client;
	
	public ServicePROG(Socket socket) {
		developpeurs = new ArrayList<>();
		createDevs();
		client = socket;
	}

	private void createDevs() {
		developpeurs.add(new Developpeur("test1", "test1", "ftp://localhost:2121/test1"));
		developpeurs.add(new Developpeur("test2", "test2", "ftp://localhost:2121/test2"));
		developpeurs.add(new Developpeur("test3", "test3", "ftp://localhost:2121/test3"));
		
	}

	public void run() {
		
		// URLClassLoader sur ftp
//		String fileNameURL = "ftp://localhost:2121/classes/";
//
//		URLClassLoader urlcl= null;
//
//		try {
//			System.out.println(fileNameURL);
//			urlcl = URLClassLoader.newInstance(new URL[] {new URL(fileNameURL)});
//		} catch (MalformedURLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		System.out.println("Bienvenue dans votre gestionnaire dynamique d'activité BRi");
//		System.out.println("Pour ajouter une activité, celle-ci doit être présente sur votre serveur ftp");
//		System.out.println("A tout instant, en tapant le nom de la classe, vous pouvez l'intégrer");
//		System.out.println("Les clients se connectent au serveur 3000 pour lancer une activité");

		
//		while (true){
//		try {
//			String classeName = clavier.next();
//			  // ou file:///c:/etc
//			Class<?> c = urlcl.loadClass(classeName);
//			ServiceRegistry.addService(c);
//			// charger la classe et la déclarer au ServiceRegistry
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}		
	}
	
	protected void finalize() throws Throwable {
		 client.close(); 
	}

	// lancement du service
	public void start() {
		(new Thread(this)).start();		
	}

}
