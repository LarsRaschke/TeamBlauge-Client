package communication;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerComm extends Remote {

	void register(ClientComm client) throws RemoteException;

    void unregister(ClientComm client) throws RemoteException;

    void notifyClients(ClientComm client) throws RemoteException;
	
}
