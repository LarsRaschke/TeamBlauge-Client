package communication;

import java.rmi.RemoteException;

public class Client implements ClientComm{
	
	public Client() {
		
	}

	@Override
	public void notifyChanges(String gui) throws RemoteException {

		if(gui.equals("Projekt"))
		{
			
		}
		else if(gui.equals("Task"))
		{
			
		}
		
	}

}
