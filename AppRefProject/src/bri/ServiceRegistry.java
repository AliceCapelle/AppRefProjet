package bri;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Vector;

import exception.NonConformeException;

public class ServiceRegistry {
	// cette classe est un registre de services
	// partagée en concurrence par les clients et les "ajouteurs" de services,
	// un Vector pour cette gestion est pratique

	static {
		servicesClasses = new Vector<Class<?>>();
	}
	private static List<Class<?>> servicesClasses;

	// ajoute une classe de service après contrôle de la norme BLTi
	public static void addService(Class<?> c) {
		try {
			verifyBRI(c);
			servicesClasses.add(c);
		} catch (NonConformeException e) {
			// TODO: handle exception
		}
		
	
		// vérifier la conformité par introspection
		// si non conforme --> exception avec message clair
		// si conforme, ajout au vector

	}

	private static boolean verifyBRI(Class<?> c) throws NonConformeException{
		String affiche = "";
		int modifiers = c.getModifiers();

		// implémente bri service
		boolean isBri = false;

		Class<?>[] interfacesImplementees = c.getInterfaces();
		for (Class<?> class1 : interfacesImplementees) {
			if (class1.getName().equals("bri.Service")) {
				isBri = true;
				break;
			}
		}
		if (!isBri) {
			System.out.println("votre classe n'implémente pas ServiceBRi revenez");
			throw new NonConformeException();
		}

		// non abstract
		if (Modifier.isAbstract(modifiers)) {
			System.out.println("votre classe est abstract revenez");
			throw new NonConformeException();
		}
		// public

		if (!Modifier.isPublic(modifiers)) {
			System.out.println("votre classe n'est pas publique revenez");
			throw new NonConformeException();
		}
		// construceteur publique sans exception nomé socket

		try {
			if (c.getConstructor(java.net.Socket.class).getExceptionTypes().length==0){
				int modifier3=c.getConstructor(java.net.Socket.class).getModifiers();
				if(!Modifier.isPublic(modifier3)){
					System.out.println("votre classe n'as pas un constructeur(Socket) revenez");
					throw new NonConformeException();
				}
			}	
		} catch (NoSuchMethodException e) {
			System.out.println("votre classe n'as pas un constructeur(Socket) revenez");
			e.printStackTrace();
			throw new NonConformeException();
		} catch (SecurityException e) {
			System.out.println("votre classe n'as pas un constructeur(Socket) revenez");
			e.printStackTrace();
			throw new NonConformeException();
		}
		
		// un attribut socket private final
		boolean isSocketPrivateFinal = false;
		Field[] f = c.getDeclaredFields();
		for (Field field : f) {
			if (field.getType().equals(java.net.Socket.class)) {
				int modifier = field.getModifiers();
				if (Modifier.isFinal(modifier) && Modifier.isPrivate(modifier)) {
					isSocketPrivateFinal = true;
					break;
				}
			}
		}
		if (!isSocketPrivateFinal) {
			System.out.println("votre classe n'as pas un attribut socket private final revenez");
			throw new NonConformeException();
		}
		// public static stringToString sans exception
		boolean isStringToStringWithoutExep = false;
		Method[] m = c.getDeclaredMethods();
		for (Method method : m) {
			if (method.getReturnType().equals(String.class)&& method.getName().equals("toStringue")) {
				int modifier2 = method.getModifiers();
				Class[] exep = method.getExceptionTypes();
				if (Modifier.isPublic(modifier2)&& Modifier.isStatic(modifier2)&&exep.length==0) {
					isStringToStringWithoutExep = true;
				}
			}
		}
		if (!isStringToStringWithoutExep) {
			System.out.println("votre classe n'as pas StringToString Without Exep revenez");
			throw new NonConformeException();
		} else {
			return true;
		}

	}

	// renvoie la classe de service (numService -1)
	public static void getServiceClass(int numService) {

	}

	// liste les activités présentes
	public static String toStringue() {
		String result = "Activités présentes :##";
		for(int i = 0; i<servicesClasses.size();i++){
			int cpt = i;
			result= servicesClasses.get(i).getSimpleName()+" : "+(cpt++)+"\n";
		}

		return result;
	}

}
