package user;

import java.util.ArrayList;

import bri.Service;

public class Developpeur {
	private ArrayList<Class<? extends Runnable>> mesServices;
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
	public void addService(Class<? extends Runnable> c){
		mesServices.add(c);
	}
	public void deleteService(Class<? extends Runnable> c){
		mesServices.remove(c);
	}
	public String getServices(){
		String result = "tous mes services :##";
		for(int i = 0; i<mesServices.size();i++){
			result= "> " + mesServices.get(i).getSimpleName()+"##";
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
