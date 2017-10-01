package model.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.ZonedDateTime;
import java.util.List;

import model.User;

/**
 * Interface für den RMI-Zugriff auf einen Projekt.
 * 
 * @author withakea
 *
 */
public interface RMI_Projekt extends Remote {

	public String getProjektname() throws RemoteException;
	
	public String getBeschreibung() throws RemoteException;
	
	public User getErsteller() throws RemoteException;
	
	public ZonedDateTime getLetzteAenderung() throws RemoteException;
	
	public ZonedDateTime getErstellungsDatum() throws RemoteException;

	public List<User> userListe() throws RemoteException;
	
	public List<String[]> taskListe() throws RemoteException;
	
	public boolean taskHinzufügen(String name, String kommentar, User u) throws RemoteException;
	
	public boolean userHinzufügen(User user) throws RemoteException;
	
	public void ändereBeschreibung(String beschreibung) throws RemoteException;
	
	public boolean speichereProjekt() throws RemoteException;
}
