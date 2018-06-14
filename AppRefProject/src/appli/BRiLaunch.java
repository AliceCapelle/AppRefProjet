package appli;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;

import bri.ServeurBRi;
import bri.ServiceAMA;
import bri.ServiceRegistry;
import bri.ServicePROG;


public class BRiLaunch {
	private final static int PORT_PROG = 3500;
	private final static int PORT_AMA = 3000;
	
	
	public static void main(String[] args) {

		new Thread(new ServeurBRi(PORT_PROG, ServicePROG.class)).start();
		new Thread(new ServeurBRi(PORT_AMA, ServiceAMA.class)).start();
			
	}
}
