package model;

import java.io.Serializable;

/**
 * Model-Klasse für einen User.
 * Serialisierbar um über RMI übertragbar zu sein.
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nutzername;
	private boolean isAdmin;
	private String nachname;
	private String vorname;
	
	/**
	 * Konstruktor.
	 * 
	 * @param nutzername - Der Nutzername des Users.
	 * @param isAdmin - True, wenn der User Admin ist.
	 * @param nachname - Der Nachname des Users.
	 * @param vorname - Der Vorname des Users.
	 */
	public User(String nutzername, boolean isAdmin, String nachname, String vorname) {
		this.nutzername = nutzername;
		this.isAdmin = isAdmin;
		this.nachname = nachname;
		this.vorname = vorname;
	}
	
	/**
	 * Getter-Methode.
	 * 
	 * @return Den Nutzernamen.
	 */
	public String getNutzername() {
		return nutzername;
	}

	/**
	 * Setter-Methode.
	 * 
	 * @param nutzername - Der Nutzername.
	 */
	public void setNutzername(String nutzername) {
		this.nutzername = nutzername;
	}

	/**
	 * Getter-Methode.
	 * 
	 * @return True, wenn der User Admin ist, andernfalls False.
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

	/**
	 * Setter-Methode.
	 * 
	 * @param isAdmin True, wenn der User Admin ist, andernfalls False.
	 */
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * Getter-Methode.
	 * 
	 * @return Den Nachnamen.
	 */
	public String getNachname() {
		return nachname;
	}

	/**
	 * Setter-Methode.
	 * 
	 * @param nachname - Der Nachname.
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	/**
	 * Getter-Methode.
	 * 
	 * @return Den Vornamen.
	 */
	public String getVorname() {
		return vorname;
	}

	/**
	 * Setter-Methode.
	 * 
	 * @param vorname - Der Vorname.
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
}
