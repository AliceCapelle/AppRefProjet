package user;

import java.util.ArrayList;

import bri.Service;

public class Developpeur {
	private ArrayList<Service> mesServices;
	private String login,password,ftp;
	
	
	public Developpeur(String login, String password, String ftp) {
	this.login=login;
	this.password=password;
	this.ftp=ftp;
	mesServices= new ArrayList<>();
	}
	
	public String getFtp() {
		return ftp;
	}
	public String getLogin() {
		return login;
	}
	public void setFtp(String ftp) {
		this.ftp = ftp;
	}
	public void addService(Service newService){
		mesServices.add(newService);
	}
	public void deleteService(Service oldService){
		mesServices.remove(oldService);
	}
	public String getServices(){
		String result = "tous mes services :##";
		for(int i = 0; i<mesServices.size();i++){
			int cpt = i;
			result= mesServices.get(i).getSimpleName()+" : "+(cpt++)+"##";
		}

		return result;
	}
	public boolean verifypassword(String login, String password){

		if(this.login.equals(login) && this.password.equals(password)){
			return true;
		}else 
			return false;
		
	}
}
