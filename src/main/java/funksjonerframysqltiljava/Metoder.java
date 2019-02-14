package funksjonerframysqltiljava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class Metoder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/* README
	 * 
	 * For aa koble til databasen maa man sette brukernavn, passord og url i getConnectio-metoden.
	 * Funksjonene er basert på en database med folgende tabeller og attributter i ()
	 * (nn) = not null / verdiene her kan ikke vaere tomme
	 * Fag(int idFag (nn),String FagKode(nn),String FagNavn)
	 * Bruker(int idBruker(nn), String Epost(nn), String Passord(nn))
	 * Bruker_has_Fag(int idBruker(nn),int idFag(nn),String Rolle(nn))
	 */
	
//lager connection til databasen som er noedvendig for aa manipulere den
public static Connection getConnection() throws Exception{
	try {
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/mydb";
		String username = "root";
		String password = "passord";
		Class.forName(driver);
		
		Connection conn = DriverManager.getConnection(url, username,password);
		//System.out.println("Connected");
		return conn;
	} catch(Exception e ) {System.out.println(e);}
	
	return null;
}

//setter inn fag med fagkode og fagnavn. Naturlig admin-funksjon
public static void leggInnFag(String fagKodeInput, String fagNavnInput) throws Exception{
	try {
		Connection con = getConnection();
		//gjoer fagkoden til store bokstaver
		String fagKode = fagKodeInput.toUpperCase();
		String fagNavn = fagNavnInput.toLowerCase();
		
		//Legger inn fag med id,fagkode og fagnavn dersom det ikke allerede eksisterer
		if (fagEksisterer(fagKode)==false) {
			int idFag = nyFagId();
			PreparedStatement leggInnFag = con.prepareStatement("INSERT INTO Fag (idFag,FagKode,FagNavn) VALUES('"+ idFag+"','"+ fagKode+"','"+ fagNavn+"')");
			leggInnFag.executeUpdate();
			System.out.println("Lagt inn fag "+ fagKode);
		}
		//Sjekker om fagkode mangler fagnavn og oppdaterer fagnavn dersom fagkode eksisterer.
		else if (fagEksisterer(fagKode)){
			PreparedStatement statement = con.prepareStatement("SELECT FagNavn From Fag WHERE FagKode = '"+fagKode+"'");
			ResultSet fagNavnNull = statement.executeQuery();
			fagNavnNull.next();
			//System.out.println(fagNavnNull.next());
			String fagNavnType = fagNavnNull.getString("FagNavn");
			
			
			if (fagNavnType==null) {
				
				PreparedStatement leggInnNavn = con.prepareStatement("UPDATE Fag SET FagNavn = '"+fagNavn+"' WHERE FagKode = '"+fagKode+"'");
				leggInnNavn.executeUpdate();
				
				System.out.println("Oppdatert navn paa fag" + fagKode + " til " + fagNavn);
			}
			else {
				System.out.println("Fag "+ fagKode+ " eksisterer");
			}
			
		}
		//Gir beskjed om at faget eksisterer
		else {
			System.out.println("Fag "+ fagKode+ " eksisterer");
		}
	} catch(Exception e) {System.out.println(e);}
	finally {
		//System.out.println("Insert Completed");
	}
}
//setter inn fag med fagkode. Naturlig admin-funksjon.
public static void leggInnFag(String fagKodeInput) throws Exception{
	try {
		Connection con = getConnection();
		//gjoer fagkoden til store bokstaver
		String fagKode = fagKodeInput.toUpperCase();
		
		//legger inn fag om det ikke eksisterer
		if (fagEksisterer(fagKode)==false) {
			int idFag = nyFagId();
			PreparedStatement leggInnFag = con.prepareStatement("INSERT INTO Fag (idFag,FagKode) VALUES('"+ idFag+"','"+ fagKode+"')");
			leggInnFag.executeUpdate();
			System.out.println("Lagt inn fag "+ fagKode);
		}
		else {
			System.out.println("Fag "+fagKode+ " eksisterer.");
		}
	} catch(Exception e) {System.out.println(e);}
	finally {
		//System.out.println("Insert Completed");
	}
}

//sjekker om faget eksisterer i Fagtabellen
public static boolean fagEksisterer(String fagKode) throws Exception  {
	boolean eksisterer = false;
	try {
		Connection con = getConnection();
		PreparedStatement statement = con.prepareStatement("SELECT FagKode FROM Fag");
		
		ResultSet result = statement.executeQuery();
		
		
		while(result.next() && eksisterer==false) {
			if (Objects.equals(result.getString("FagKode"),fagKode)) {
				eksisterer = true;
			}
		}
		//System.out.println(eksisterer);
	}catch(Exception e) {System.out.println(e);

	}
	return eksisterer;
	
}


//finner siste fagIden i lista og plusser paa 1. Dette gir ny, unik fagId.
public static int nyFagId() throws Exception{
	int nyId = 0;
	try {
		Connection con = getConnection();
		PreparedStatement statement = con.prepareStatement("SELECT * FROM Fag ORDER BY idFag DESC LIMIT 1");
		ResultSet forrigeId = statement.executeQuery();
		forrigeId.next();
		nyId = forrigeId.getInt("idFag") + 1;
		//System.out.println(nyId);
	}catch(Exception e) {System.out.println(e);}
	return nyId;
	
}

//sjekker om epost eksisterer i bruker
public static boolean epostEksisterer(String epost) throws Exception{
	boolean eksisterer = false;
	try {
		Connection con = getConnection();
		PreparedStatement statement = con.prepareStatement("SELECT Epost FROM Bruker");
		
		ResultSet result = statement.executeQuery();
		
		
		while(result.next() && eksisterer==false) {
			if (Objects.equals(result.getString("Epost"),epost)) {
				eksisterer = true;
			}
		}
		//System.out.println(eksisterer);
	}catch(Exception e) {System.out.println(e);

	}
	return eksisterer;
	
}

//sjekker om passord matcher epost. Naturlig godkjenning for innlogging.
public static boolean epostPassordMatch(String epostInput, String passordInput) throws Exception{
	boolean match = false;
	try {
		Connection con = getConnection();
		String epost = epostInput.toLowerCase();
		if (epostEksisterer(epost)){
			PreparedStatement statement = con.prepareStatement("SELECT Passord FROM Bruker Where Epost ='"+epost+"'");
			ResultSet passord = statement.executeQuery();
			passord.next();
			String fasit = passord.getString("Passord");
			
			if (Objects.equals(fasit, passordInput)) {
				match = true;
				System.out.println("Passordet er korrekt.");
			}
			else {
				System.out.println("Passordet er feil.");
			}
			
		}
		else {
			System.out.println("E-post eksisterer ikke");
		}
		
	}catch(Exception e) {System.out.println(e);

	}
	return match;
	
}

//registrerer bruker uten krav til epost
public static void registrerBruker(String epostInput, String passord) {
	try {
		Connection con = getConnection();
		//gjoer fagkoden til smaa bokstaver
		String epost = epostInput.toLowerCase();
		
		
		//Legger inn bruker med id, epost og passord dersom epost ikke eksisterer
		if (epostEksisterer(epost)==false) {
			
			int idBruker = nyBrukerId();
			PreparedStatement registrerBruker = con.prepareStatement("INSERT INTO Bruker (idBruker,Epost,Passord) VALUES('"+ idBruker+"','"+ epost+"','"+ passord+"')");
			registrerBruker.executeUpdate();
			System.out.println("Lagt inn bruker "+ epost);
		}
		
		
		//Gir beskjed om at brukeren eksisterer
		else {
			System.out.println("Brukeren "+ epost+ " eksisterer");
		}
	} catch(Exception e) {System.out.println(e);}
	finally {
		//System.out.println("Insert Completed");
	}
}

//finner siste brukerIden i lista og plusser med 1. Dette gir ny, unik brukerId
public static int nyBrukerId() throws Exception{

	int nyId = 0;
	try {
		Connection con = getConnection();
		PreparedStatement statement = con.prepareStatement("SELECT * FROM Bruker ORDER BY idBruker DESC LIMIT 1");
		ResultSet forrigeId = statement.executeQuery();
		forrigeId.next();
		nyId = forrigeId.getInt("idBruker") + 1;
		System.out.println(nyId);
	}catch(Exception e) {System.out.println(e);}
	return nyId;
}


//returnerer 0 om fagKoden ikke eksisterer.
public static int fraFagKodeTilFagId(String fagKodeInput) {

	int idFag=0;
	try {
		Connection con = getConnection();
		//gjoer fagkoden til store bokstaver
		String fagKode = fagKodeInput.toUpperCase();
		
		//legger inn fag om det ikke eksisterer
		if (fagEksisterer(fagKode)==false) {
			System.out.println("Fagkoden eksisterer ikke.");
		}
		else {
			PreparedStatement statement = con.prepareStatement("SELECT idFag From Fag WHERE FagKode = '"+fagKode+"'");
			ResultSet fagId = statement.executeQuery();
			fagId.next();
			//System.out.println(fagNavnNull.next());
			idFag = fagId.getInt("idFag");
			System.out.println("Fagid for "+fagKode+ " er " + idFag);
			
			
		}
	} catch(Exception e) {System.out.println(e);}
	return idFag;
	
}

//ikke fullfort
public static boolean brukerHarFagEksisterer(int idBrukerInput,int idFagInput, char rolleInput)  {
	boolean eksisterer = false;
	try {
		Connection con = getConnection();
		PreparedStatement finnIdBruker = con.prepareStatement("SELECT idBruker FROM Bruker_has_Fag");
		//PreparedStatement finnIdFag = con.prepareStatement("SELECT idBruker FROM Bruker_has_Fag");
		//PreparedStatement finnRolle = con.prepareStatement("SELECT idBruker FROM Bruker_has_Fag");
		ResultSet idBruker = finnIdBruker.executeQuery();
		//ResultSet idFag = finnIdFag.executeQuery();
		//ResultSet rolle = finnRolle.executeQuery();
		
		
		while(idBruker.next() && eksisterer==false) {
			if (Objects.equals(idBruker.getString("idBruker"),idBrukerInput)) {
				eksisterer = true;
			}
		}
		//System.out.println(eksisterer);
	}catch(Exception e) {System.out.println(e);

	}
	return eksisterer;
	
}

//Legg til kobling i BrukereHarFag
public static void leggTilBrukerHarFag(int idBruker,int idFag, char rolle){
	try {
		Connection con = getConnection();
		
		
		//legger inn kobling om det ikke eksisterer
		if (brukerHarFagEksisterer(idBruker, idFag, rolle)==false) {
			
			PreparedStatement leggInnKobling = con.prepareStatement("INSERT INTO Bruker_has_Fag (idBruker,idFag,rolle) VALUES('"+ idBruker+"','"+ idFag+"','"+ idBruker+"')");
			leggInnKobling.executeUpdate();
			System.out.println("Lagt inn kobling til brukerId"+ idBruker);
		}
		else {
			System.out.println("Fag "+idBruker+ " eksisterer.");
		}
	} catch(Exception e) {System.out.println(e);}
	finally {
		//System.out.println("Insert Completed");
	}
	
}
}


