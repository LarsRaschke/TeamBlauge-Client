package model.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMI_Task extends Remote {

	public String getName() throws RemoteException;
	
	public String getKommentar() throws RemoteException;
	
	public int getFarbe() throws RemoteException;
}
