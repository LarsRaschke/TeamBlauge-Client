package model.interfaces;

import model.User;

public interface RMI_Projekt {
	
	public void userHinzufügen(User u);
	
	public void taskHinzufügen(String name, String kommentar, User u);

}
