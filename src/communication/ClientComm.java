package communication;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * @author withakea
 *
 */
public interface ClientComm extends Remote {

	void updateGUI(String gui, String projekt) throws RemoteException;
	
}
