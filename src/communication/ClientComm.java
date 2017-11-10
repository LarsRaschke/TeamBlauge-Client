package communication;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientComm extends Remote{

	void notifyChanges(String gui) throws RemoteException;
	
}
