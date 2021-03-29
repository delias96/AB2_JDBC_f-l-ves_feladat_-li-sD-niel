package JDBC;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Checker {
private SimpleDateFormat RDF = new SimpleDateFormat("yyyy.MM.dd");
	
	public boolean filled(JTextField a, String an) {
		String s = RTF(a);
		if(s.length()>0) return true;
		else {
			SM("Hiba: a(z)"+an+" mezõ üres!",0);
			return false;
		}
	}
	public boolean goodInt(JTextField a, String an) {
		String s = RTF(a);
		boolean b = filled(a,an);
		if(b) try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			SM("A(z) "+an+" mezõben hibás a számadat!", 0);
			b = false;
		}
		return b;
	}
	
	public boolean DateFormatChecker(String SDate) {
		try {
			Date date = RDF.parse(SDate);
			if(!RDF.format(date).equals(SDate)) {return false;}
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	public boolean goodDate(JTextField a, String an) {
		String s = RTF(a);
		boolean b = filled(a,an);
		if(b && DateFormatChecker(s)) return true;
		else {
			SM("A(z) "+an+" mezõben hibás a dátum!", 0);
			return false;
		}
	}
	public boolean filled(JTextField a) {
		String s = RTF(a);
		if(s.length()>0) return true;
		else return false;
	}
	public int stringToInt(String s) {
		int x = -1;
		try {
			x = Integer.valueOf(s);
		} catch (NumberFormatException e) {
			SM("stringToInt"+e.getMessage(),0);
		}
		return x;
	}
	public String RTF(JTextField jtf) {
		return jtf.getText();
	}
	public static void SM(String msg, int tipus) {
		JOptionPane.showMessageDialog(null, msg, "Program üzenet", tipus);
	}
}
