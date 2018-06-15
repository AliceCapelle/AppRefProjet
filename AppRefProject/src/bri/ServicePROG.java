package bri;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import user.Developpeur;

public class ServicePROG implements Runnable {
	private ArrayList<Developpeur> developpeurs;
	private Socket client;
	PrintWriter out = null;
	BufferedReader in = null;
	Developpeur d;

	public ServicePROG(Socket socket) {
		developpeurs = new ArrayList<>();
		createDevs();
		client = socket;
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createDevs() {


		developpeurs.add(new Developpeur("test1", "test1", "ftp://localhost:2121/classes/"));
		developpeurs.add(new Developpeur("test2", "test2", "ftp://localhost:2121/classes/test2/"));
		developpeurs.add(new Developpeur("test3", "test3", "ftp://localhost:2121/classes/test3/"));
	}

	public void run() {
		String login = null;
		String password = null;
		Boolean connected = false;
		int choix = 0;

		do {
			try {
				out.println("Login : ");
				login = in.readLine();
				out.println("Mot de passe : ");
				password = in.readLine();

				for (Developpeur developpeur : developpeurs) {
					if (developpeur.verifypassword(login, password)) {
						this.d = developpeur;
						connected = true;
						break;
					}
				}
				if (connected) {
					out.println("Bienvenue " + login + "! ##" + "Que voulez vous faire ? ##" + "1 - Load class ##");
					choix = Integer.parseInt(in.readLine());
					break;
				} else
					out.println("Mot de passe ou login incorrect ##");
			} catch (IOException e) {
				e.printStackTrace();
			}

		} while (!connected);

		switch (choix) {
		case 1:
			loadClass(d.getFtp());
			break;

		default:
			out.println("Je ne comprend pas ce que vous avez écrit !");
			break;
		}
		

	}

	public void loadClass(String url) {
		String fileNameURL = url;
		URLClassLoader urlcl = null;

		try {
			urlcl = URLClassLoader.newInstance(new URL[] { new URL(fileNameURL) });
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		out.println("Bienvenue dans votre gestionnaire dynamique d'activite BRi ## "
				+ "Pour ajouter une activit�, celle-ci doit etre presente sur votre serveur ftp ##"
				+ "A tout instant, en tapant le nom de la classe, vous pouvez l'integrer ##");

		while (true) {
			try {
				String classeName = in.readLine();
				Class<? extends Runnable> c = (Class<? extends Runnable>) urlcl.loadClass(classeName);
				ServiceRegistry.addService(c);
				out.println("Class ajoutée avec succes!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void finalize() throws Throwable {
		client.close();
	}

	// lancement du service
	public void start() {
		(new Thread(this)).start();
	}

}
