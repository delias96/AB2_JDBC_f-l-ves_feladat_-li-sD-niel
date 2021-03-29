package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DB_methods {
	
	private Statement s = null;
	private Connection conn = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
	
	public void Reg() {
		try {
			Class.forName("org.sqlite.JDBC");
			System.out.println("Sikeres driver regisztr�ci�!");
		} catch (ClassNotFoundException e) {
			System.out.println("Hib�s driver regisztr�ci�! " + e.getMessage());
		}
	}
	
	public void Connect() {
		try {
			String url ="jdbc:sqlite:D:/sqlite/jdbcbead";
			conn = DriverManager.getConnection(url);
			System.out.println("Connection OK!");
		}catch (SQLException e) {
			System.out.println("JDBC Connect: "+e.getMessage());
		}
	}
	public void DisConnect() {
		try {
			conn.close();
			System.out.println("DisConnection OK!");
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	//Felhaszn�l� megkeres�se
	public int Identification(String name,String pswd) {
		Connect();
		int pc = -1;
		String sqlp = "select count(*) pc from bejelentkez where felhasznalo='"+name+"' and jelszo='"+pswd+"';";
		try {
			s=conn.createStatement();
			rs = s.executeQuery(sqlp);
			while(rs.next()) {
				pc = rs.getInt("pc");
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		DisConnect();
		return pc;
	}
	//munk�s t�bla beolvas�sa
	public EmpTM ReadMunkas() {
		Connect();
		Object emptmn[] = {"Jel","Id","N�v","Sz�l_id�","Munkak�r","Fizet�s"};
		EmpTM etm= new EmpTM(emptmn, 0);
		String nev = "",szul_ido = "", munkakor = "";
		int id = 0 , fizetes = 0;
		String sqlp = "Select id,nev,szul_ido,munkakor,fizetes from munkas";
		System.out.println(sqlp);
		try {
			s = conn.createStatement();
			rs = s.executeQuery(sqlp);
			while (rs.next()) {
				id = rs.getInt("id");
				nev = rs.getString("nev");
				szul_ido = rs.getString("szul_ido");
				munkakor = rs.getString("munkakor");
				fizetes = rs.getInt("fizetes");
				etm.addRow(new Object[] {false, id,nev,szul_ido,munkakor,fizetes});
			}
			rs.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		DisConnect();
		return etm;
	}
	//C�g t�bla beolvas�sa
	public EmpTM ReadCeg() {
		Connect();
		Object emptmn[] = {"Jel","Id","N�v","Sz�khely","Ad�sz�m"};
		EmpTM etm= new EmpTM(emptmn, 0);
		String nev = "",szekhely = "";
		int id = 0 , adoszam = 0;
		String sqlp = "Select id,nev,szekhely,adoszam from ceg";
		try {
			s = conn.createStatement();
			rs = s.executeQuery(sqlp);
			while (rs.next()) {
				id = rs.getInt("id");
				nev = rs.getString("nev");
				szekhely = rs.getString("szekhely");
				adoszam = rs.getInt("adoszam");
				etm.addRow(new Object[] {false, id,nev,szekhely,adoszam});
			}
			rs.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		DisConnect();
		return etm;
	}
	//dolgoz t�bla beolvas�sa
	public EmpTM ReadDolgoz() {
		Connect();
		Object emptmn[] = {"Jel","ID","C�g ID","C�g N�v","Munk�s ID","Munk�s N�v"};
		EmpTM etm= new EmpTM(emptmn, 0);
		int id = 0,c=0,m=0;
		String id_c = "",id_m="";
		String sqlp = "Select dolgoz.id as did,ceg.id as cid,ceg.nev as cnev,munkas.id as mid,munkas.nev as mnev from ceg inner join dolgoz on dolgoz.id_ceg=ceg.id inner join munkas on dolgoz.id_m=munkas.id";
		try {
			s = conn.createStatement();
			rs = s.executeQuery(sqlp);
			while (rs.next()) {
				id = rs.getInt("did");
				c = rs.getInt("cid");
				id_c= rs.getString("cnev");
				m = rs.getInt("mid");
				id_m = rs.getString("mnev");
				etm.addRow(new Object[] {false, id,c,id_c,m,id_m});
			}
			rs.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		DisConnect();
		return etm;
	}
	//munk�s t�bla felt�lt�se
	public void Insertmunkas(String id,String nev,String szul_ido,String munkakor, String fizetes) {
		Connect();
		String sqlp = "insert into munkas values("+id+", '"+nev+"', '"+szul_ido+"','"+munkakor+"',"+fizetes+")";
		try {
			s = conn.createStatement();
			s.execute(sqlp);
			System.out.println("Insert OK!");
		} catch (SQLException e) {
			System.out.println("JDBC insert: "+e.getMessage());
		}
		DisConnect();
	}
	//c�g t�bla felt�lt�se
	public void Insertceg(String id,String nev,String szekhely,String adoszam) {
		Connect();
		String sqlp = "insert into ceg values("+id+", '"+nev+"', '"+szekhely+"','"+adoszam+"')";
		try {
			s = conn.createStatement();
			s.execute(sqlp);
			System.out.println("Insert OK!");
		} catch (SQLException e) {
			System.out.println("JDBC insert: "+e.getMessage());
		}
		DisConnect();
	}
	//dolgoz t�bla felt�lt�se
	public void Insertdolgoz(String id,String id_c,String id_m) {
		Connect();
		String sqlp = "insert into dolgoz values("+id+", '"+id_c+"', '"+id_m+"')";
		try {
			s = conn.createStatement();
			s.execute(sqlp);
			System.out.println("Insert OK!");
		} catch (SQLException e) {
			System.out.println("JDBC insert: "+e.getMessage());
		}
		DisConnect();
	}
	//adatok t�rl�se
	public void DeleteData(String tabla,String id) {
		Connect();
		String sqlp = "Delete from "+tabla+" where id = "+id+";";
		SM(sqlp,1);
		try {
			s = conn.createStatement();
			s.execute(sqlp);
		} catch (SQLException e) {
			SM("JDBC Delete: "+e.getMessage(),1);
		}
		DisConnect();
	}
	//adatok modosit�sa
	public void Update(String tabla,String id,String mnev, String madat) {
		Connect();
		String sqlp = "update "+tabla+" set "+mnev+"='"+madat+"' where id="+id+";";
		try {
			s = conn.createStatement();
			s.execute(sqlp);
			SM("Update OK!",1);
		} catch (SQLException e) {
			SM("JDBC Update: "+e.getMessage(),0);
		}
		DisConnect();
	}
	//�tlagfizet�s kisz�m�t�sa az adatb�zisb�l
	public int atlagfizetes() {
		Connect();
		int fizetes=0;
		String sqlp = "Select AVG(fizetes) from munkas";
		try {
			s = conn.createStatement();
			rs = s.executeQuery(sqlp);
			fizetes = rs.getInt(1);
		} catch (SQLException e) {
			SM("Lek�rdez�s hiba: "+e.getMessage(), 0);
		}
		DisConnect();
		return fizetes;
	}
	//h�ny ember dolgozik egy c�gn�l
	public int dolgoz(String ceg) {
		Connect();
		int db = 0;
		String sqlp = "Select Count(munkas.id) from ceg inner join dolgoz on dolgoz.id_ceg=ceg.id inner join munkas on dolgoz.id_m=munkas.id where ceg.nev ='"+ceg+"';";
		try {
			s = conn.createStatement();
			rs = s.executeQuery(sqlp);
			db = rs.getInt(1);
		} catch (SQLException e) {
			SM("Lek�rdez�s hiba: "+e.getMessage(), 0);
		}
		DisConnect();
		return db;		
	}
	
	public static void SM(String msg, int tipus) {
		JOptionPane.showMessageDialog(null, msg, "Program �zenet", tipus);
	}
}
