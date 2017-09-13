package communication.ldap;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchResult;

import model.User;

/**
 * Finale Klasse für LDAP-Abfragen.
 * 
 * @author withakea
 *
 */
public final class Abfragen {
	
	/**
	 * Überprüft, ob das Passwort zu dem Usernamen passt.
	 * 
	 * @param username - Der Username.
	 * @param passwort - Das Passwort.
	 * 
	 * @return True, falls das Passwort korrekt ist, andernfalls False.
	 */
	public static boolean authenticateUser(String username, String passwort)
	{
		boolean authenticate = false;
		
		LdapConnectionClient con = new LdapConnectionClient();
 		NamingEnumeration<SearchResult> results = con.compare("uid=" + username + ", ou=accounts", "(userPassword={0})", passwort);
 		try {
			if(results.hasMore())
			{
				authenticate = true;
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		return authenticate;
	}
	
	/**
	 * Erstellt den zugehörigen User zum Usernamen.
	 * 
	 * @param username - Der gegebene Username.
	 * 
	 * @return Den User, falls der Username vorhanden ist. Ansonsten null.
	 */
	public static User erstelleUser(String username) {
		
		User user = null;
		
		LdapConnectionClient con = new LdapConnectionClient();
		String[] returningAttributes = new String[] {"givenName", "sn"};
 		NamingEnumeration<SearchResult> results = con.search("ou=accounts", "uid=" + username, returningAttributes);
		
		try {
			if(results.hasMore())
			{
				SearchResult sr = (SearchResult) results.next();
				
				String vorname = sr.getAttributes().get("givenName").get().toString();
				String nachname = sr.getAttributes().get("sn").get().toString();
				
				user = new User(username, false, nachname, vorname);
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		return user;
	}

}
