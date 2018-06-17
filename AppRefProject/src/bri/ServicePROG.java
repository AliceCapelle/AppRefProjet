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
	Developpeur dev;
	Boolean connected = false;

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
		developpeurs.add(new Developpeur("test1", "test1", "ftp://localhost:2121/classes/test1/"));
		developpeurs.add(new Developpeur("test2", "test2", "ftp://localhost:2121/classes/test2/"));
		developpeurs.add(new Developpeur("test3", "test3", "ftp://localhost:2121/classes/test3/"));
	}

	public void run() {
		String login = null;
		String password = null;
		int choix = 0;

		do {
			try {
				out.println("Login : ");
				login = in.readLine();
				out.println("Mot de passe : ");
				password = in.readLine();

				for (Developpeur developpeur : developpeurs) {
					if (developpeur.verifypassword(login, password)) {
						this.dev = developpeur;
						connected = true;
						break;
					}
				}
				if (connected) {
					out.println("Bienvenu ! ##" + "Que voulez vous faire ? ##" + "1 - Load service ##"
							+ "2 - Change ftp##" + "3 - Mettre à jour un service##");
					menu();
					break;
				} else {
					out.println("Mot de passe ou login incorrect ##");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} while (!connected);

		

	}
	
	public void menu() {
		int choix = 0;
		try {
			choix = Integer.parseInt(in.readLine());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch (choix) {
		case 1:
			loadDevClass(dev.getFtp());
			break;
		case 2:
			changeftp();
			break;
		case 3:
			updateService(dev.getFtp());
			break;
		default:
			out.println("Je ne comprend pas ce que vous avez écrit !");
			break;
		}
	}

	public void updateService(String url) {
		String fileNameURL = url;
		URLClassLoader urlcl = null;

		try {
			urlcl = URLClassLoader.newInstance(new URL[] { new URL(fileNameURL) });
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		out.println("Bienvenue dans votre gestionnaire dynamique d'activite BRi ## "
				+ "Veuillez selectionner l'activiter à mettre à jour : ##"
				+ dev.getServices());
		
			try {
				String classeName = in.readLine();
				Class<? extends Runnable> c = (Class<? extends Runnable>) urlcl.loadClass(classeName);
				ServiceRegistry.updateService(c);
				dev.deleteService(c);
				dev.addService(c);
				out.println("Service mis à jour! Que voulez vous faire ? ##" 
						+ "1 - Load service ##" 
						+ "2 - Change ftp##" 
						+ "3 - Mettre à jour un service##");
				menu();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	

	public void changeftp() {
		String ftp = null;
		out.println("Entrez votre nouveau ftp");
		try {
			ftp = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.dev.setFtp(ftp);
		out.println("Ftp changé! Que voulez vous faire ? ##" 
				+ "1 - Load service ##" 
				+ "2 - Change ftp##" 
				+ "3 - Mettre à jour un service##");
		menu();
	}

	public void loadDevClass(String url) {
		String fileNameURL = url;
		URLClassLoader urlcl = null;

		try {
			urlcl = URLClassLoader.newInstance(new URL[] { new URL(fileNameURL) });
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		out.println("Bienvenue dans votre gestionnaire dynamique d'activite BRi ## "
				+ "Pour ajouter une activit�, celle-ci doit etre presente sur votre serveur ftp ##"
				+ "En tapant le nom de la classe, vous pouvez l'integrer ##");
		
			try {
				String classeName = in.readLine();
				Class<? extends Runnable> c = null;
				try {
					c = (Class<? extends Runnable>) urlcl.loadClass(classeName);
				} catch (ClassNotFoundException e) {
					out.println("Class non trouvée...");
				}
				
				ServiceRegistry.addService(c);
				dev.addService(c);
				out.println("Class ajoutée avec succes! Que voulez vous faire ? ##" 
				+ "1 - Load service ##" 
				+ "2 - Change ftp##" 
				+ "3 - Mettre à jour un service##");
				menu();
			} catch (Exception e) {
				e.printStackTrace();
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
