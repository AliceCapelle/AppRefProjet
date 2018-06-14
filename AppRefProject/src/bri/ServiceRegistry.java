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
	// partag�e en concurrence par les clients et les "ajouteurs" de services,
	// un Vector pour cette gestion est pratique

	static {
		servicesClasses = new Vector<Class<?>>();
	}
	private static List<Class<?>> servicesClasses;

	// ajoute une classe de service apr�s contr�le de la norme BLTi
	public static void addService(Class<?> c) {
		try {
			verifyBRI(c);
			servicesClasses.add(c);
			System.out.println("service ajout�!");
		} catch (NonConformeException e) {
			// TODO: handle exception
		}
		
	
		// v�rifier la conformit� par introspection
		// si non conforme --> exception avec message clair
		// si conforme, ajout au vector

	}

	private static boolean verifyBRI(Class<?> c) throws NonConformeException{
		String affiche = "";
		int modifiers = c.getModifiers();

		// impl�mente bri service
		boolean isBri = false;

		Class<?>[] interfacesImplementees = c.getInterfaces();
		for (Class<?> class1 : interfacesImplementees) {
			if (class1.getName().equals("bri.Service")) {
				isBri = true;
				break;
			}
		}
		if (!isBri) {
			throw new NonConformeException("Votre classe n'impl�mente pas ServiceBRi revenez");
		}

		// non abstract
		if (Modifier.isAbstract(modifiers)) {
			throw new NonConformeException("Votre classe est abstract revenez");
		}
		// public

		if (!Modifier.isPublic(modifiers)) {
			throw new NonConformeException("Votre classe n'est pas publique revenez");
		}
		// construceteur publique sans exception nom� socket

		try {
			if (c.getConstructor(java.net.Socket.class).getExceptionTypes().length==0){
				int modifier3=c.getConstructor(java.net.Socket.class).getModifiers();
				if(!Modifier.isPublic(modifier3)){
					throw new NonConformeException("Votre classe n'as pas un constructeur(Socket) revenez");
				}
			}	
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new NonConformeException("Votre classe n'as pas un constructeur(Socket) revenez");
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new NonConformeException("Votre classe n'as pas un constructeur(Socket) revenez");
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
			throw new NonConformeException("Votre classe n'as pas un attribut socket private final revenez");
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
			throw new NonConformeException("Votre classe n'as pas StringToString Without Exep revenez");
		} else {
			return true;
		}

	}

	// renvoie la classe de service (numService -1)
	public static void getServiceClass(int numService) {

	}

	// liste les activit�s pr�sentes
	public static String toStringue() {
		String result = "Activit�s pr�sentes :##";
		for(int i = 0; i<servicesClasses.size();i++){
			int cpt = i;
			result= servicesClasses.get(i).getSimpleName()+" : "+(cpt++)+"\n";
		}

		return result;
	}

}
