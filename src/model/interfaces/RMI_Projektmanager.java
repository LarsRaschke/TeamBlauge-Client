package model.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import model.User;

public interface RMI_Projektmanager extends Remote {
	
	public void erstelleProjekt(User u, String projektname, String beschreibung) throws RemoteException;

}
