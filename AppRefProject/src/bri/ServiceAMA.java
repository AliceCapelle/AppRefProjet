package bri;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.*;

import exception.OutOfBounds;

public class ServiceAMA implements Runnable {

	private Socket client;

	public ServiceAMA(Socket socket) {
		client = socket;
	}

	public void run() {
<<<<<<< HEAD
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
			out.println(ServiceRegistry.toStringue()+"##Tapez le num�ro de service d�sir� :");
			int choix = Integer.parseInt(in.readLine());
			 
			// instancier le service num�ro "choix" en lui passant la socket "client"
			// invoquer run() pour cette instance ou la lancer dans un thread � part 
=======
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			

			Class<? extends Runnable> c;
			boolean choixIsOk = false;

				out.println(ServiceRegistry.toStringue()+ "##Tapez le num�ro de service d�sir� :");
				int choix = Integer.parseInt(in.readLine());
				if(choix-1>=0&&choix<ServiceRegistry.getSizeOfServicesClasses()){
					c = ServiceRegistry.getServiceClass(choix - 1);
					choixIsOk = true;
					try {
						new Thread(c.getConstructor(Socket.class).newInstance(client)).start();
						System.out.println("ok on lance le service");
					} catch (InstantiationException | IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException | NoSuchMethodException
							| SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else
					System.out.println("vous avez selectionn� un mauvais choix dsl");
					
>>>>>>> e7392a9f468e13c9be90b9323febb3a89a999c37
				
	

			// instancier le service num�ro "choix" en lui passant la socket
			// "client"
			// invoquer run() pour cette instance ou la lancer dans un thread �
			// part

		} catch (IOException e) {
			// Fin du service
		}

		try {
			client.close();
		} catch (IOException e2) {
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
