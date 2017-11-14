package communication;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import gui.application.Main;
import model.interfaces.RMI_Projekt;

/**
 * 
 * @author withakea
 *
 */
public class Client implements ClientComm {
	
	private Main main;

	public Client(Main main) throws RemoteException {
		
		ClientComm stub_client = (ClientComm) UnicastRemoteObject.exportObject((ClientComm)this, 0);
		
		try {
			Registry registry = LocateRegistry.getRegistry(null);
			ServerComm stub_server = (ServerComm) registry.lookup("Server");
			stub_server.register(stub_client);
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		this.main = main;
	}

	@Override
	public void updateGUI(String gui, String projekt) throws RemoteException {

		main.notifyChanges(gui, projekt);
		
	}
	
	public void somethingChanged(String gui, String projekt) throws RemoteException {

		try {
			Registry registry = LocateRegistry.getRegistry(null);
			ServerComm server = (ServerComm) registry.lookup("Server");
			server.notifyClients((ClientComm)this, gui, projekt);
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
	}

}
