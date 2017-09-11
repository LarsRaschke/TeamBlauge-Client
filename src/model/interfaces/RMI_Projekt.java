package model.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import model.User;

public interface RMI_Projekt extends Remote {
	
	public void taskHinzufügen(String name, String kommentar, User u) throws RemoteException;

	public List<User> userListe() throws RemoteException;
	
	public List<String[]> taskListe() throws RemoteException;
	
	public String getProjektname() throws RemoteException;
	
	public String getBeschreibung() throws RemoteException;
}
