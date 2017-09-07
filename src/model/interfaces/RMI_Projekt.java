package model.interfaces;

import java.rmi.Remote;

import model.User;

public interface RMI_Projekt extends Remote{
	
	public void taskHinzufügen(String name, String kommentar, User u);

}
