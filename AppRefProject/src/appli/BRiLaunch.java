package appli;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;

import bri.ServeurBRi;
import bri.ServiceRegistry;

public class BRiLaunch {
	private final static int PORT_PROG = 3000;
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner clavier = new Scanner(System.in);
		
		// URLClassLoader sur ftp
		String fileNameURL = "ftp://localhost:2121/classes/";

		URLClassLoader urlcl= null;

		try {
			System.out.println(fileNameURL);
			urlcl = URLClassLoader.newInstance(new URL[] {new URL(fileNameURL)});
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("Bienvenue dans votre gestionnaire dynamique d'activité BRi");
		System.out.println("Pour ajouter une activité, celle-ci doit être présente sur votre serveur ftp");
		System.out.println("A tout instant, en tapant le nom de la classe, vous pouvez l'intégrer");
		System.out.println("Les clients se connectent au serveur 3000 pour lancer une activité");
		
		new Thread(new ServeurBRi(PORT_PROG)).start();
		
		while (true){
				try {
					String classeName = clavier.next();
					  // ou file:///c:/etc
					Class<?> c = urlcl.loadClass(classeName);
					ServiceRegistry.addService(c);
					// charger la classe et la déclarer au ServiceRegistry
				} catch (Exception e) {
					System.out.println(e);
				}
			}		
	}
}
