package JDBC;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class EmpCegList extends JDialog {
	private JTable table;
	private EmpTM etm;
	private JTextField id;
	private JTextField nev;
	private JTextField szekhely;
	private JTextField adoszam;
	private DB_methods dbm = new DB_methods();
	private JButton btnTorles;
	private JButton btnModosit;
	private Checker c = new Checker();

	public EmpCegList(JDialog d, EmpTM betm) {
		super(d, "C�g lista", true);
		etm = betm;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JButton btnBezar = new JButton("Bez\u00E1r\r\n");
		btnBezar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnBezar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnBezar.setBounds(341, 233, 85, 21);
		getContentPane().add(btnBezar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 416, 180);
		getContentPane().add(scrollPane);
		
		table = new JTable(etm);
		scrollPane.setViewportView(table);
		TableColumn tc= null;
		for(int i = 0; i < 5; i++) {
			tc= table.getColumnModel().getColumn(i);
			if(i==0 || i==1) tc.setPreferredWidth(30); 
				else if(i==4) tc.setPreferredWidth(150);
				else{tc.setPreferredWidth(100);}
			}
		table.setAutoCreateRowSorter(true);
		
		JButton btnhozzaad = new JButton("\u00DAj adat felvitele");
		btnhozzaad.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnhozzaad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(c.goodInt(id, "id"))
					if(c.filled(nev, "N�v"))
							if(c.filled(szekhely, "Sz�khely"))
								if(c.goodInt(adoszam, "Ad�sz�m"))
				dbm.Insertceg(RTF(id), RTF(nev), RTF(szekhely), RTF(adoszam));
				dispose();
			}
		});
		btnhozzaad.setBounds(193, 233, 138, 21);
		getContentPane().add(btnhozzaad);
		
		id = new JTextField();
		id.setBounds(55, 200, 24, 19);
		getContentPane().add(id);
		id.setColumns(10);
		
		nev = new JTextField();
		nev.setBounds(82, 200, 109, 19);
		getContentPane().add(nev);
		nev.setColumns(10);
		
		szekhely = new JTextField();
		szekhely.setBounds(193, 200, 111, 19);
		getContentPane().add(szekhely);
		szekhely.setColumns(10);
		
		adoszam = new JTextField();
		adoszam.setBounds(306, 200, 120, 19);
		getContentPane().add(adoszam);
		adoszam.setColumns(10);
		
		btnTorles = new JButton("T\u00F6rl\u00E9s");
		btnTorles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int db = 0,jel = 0,x = 0;
				for(x = 0;x < etm.getRowCount(); x++)
					if((Boolean)etm.getValueAt(x, 0)) {db++;jel=x;}
				if(db==0)dbm.SM("Nincs kijel�lve a t�rlend� rekord!", 0);
				if(db>1)dbm.SM("T�bb rekord van kijel�lve!\n Egyszerre csak egy rekord t�r�lhet�", 0);
				if(db==1) {
					String kod = etm.getValueAt(jel, 1).toString();
					etm.removeRow(jel);
					dbm.DeleteData("ceg", kod);
					dbm.SM("A rekord t�r�lve!",1);
					dispose();
				}
			}
		});
		btnTorles.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnTorles.setBounds(98, 233, 85, 21);
		getContentPane().add(btnTorles);
		
		btnModosit = new JButton("M\u00F3dos\u00EDt");
		btnModosit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int db = 0,jel = 0,x = 0;
				for(x = 0;x < etm.getRowCount(); x++)
					if((Boolean)etm.getValueAt(x, 0)) {db++;jel=x;}
				if(db==0)dbm.SM("Nincs kijel�lve a m�dos�tand� rekord!", 0);
				if(db>1)dbm.SM("T�bb rekord van kijel�lve!\n Egyszerre csak egy rekord m�dos�that�", 0);
				if(db==1) {
					if(modDataPc()>0) {
						boolean ok = true;
						if(c.filled(id)) ok = c.goodInt(id, "K�d");
						if(ok && c.filled(adoszam)) ok = c.goodInt(adoszam, "Fizet�s");
						if(ok) {
							String mkod = etm.getValueAt(jel, 1).toString();
							if(c.filled(id)) dbm.Update("ceg", mkod, "id", c.RTF(id));
							if(c.filled(nev)) dbm.Update("ceg", mkod, "nev", c.RTF(nev));
							if(c.filled(szekhely)) dbm.Update("ceg", mkod, "szekhely", c.RTF(szekhely));
							if(c.filled(adoszam)) dbm.Update("ceg", mkod, "adoszam", c.RTF(adoszam));
							dbm.SM("A rekord m�dos�tva", 1);
							reset(jel);
							dispose();
						}
					}else {
							dbm.SM("Nincs kit�ltve egyetlen m�dos�t� adatmez� sem!", 1);
						}
			
		}}});
		btnModosit.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnModosit.setBounds(10, 233, 85, 21);
		getContentPane().add(btnModosit);
		TableRowSorter<EmpTM> trs=(TableRowSorter<EmpTM>)table.getRowSorter();
		trs.setSortable(0, false);

	}
	public void reset(int i) {
		id.setText("");
		nev.setText("");
		szekhely.setText("");
		adoszam.setText("");
		etm.setValueAt(false, i, 0);
	}
	public int modDataPc() {
		int pc = 0;
		if(c.filled(id)) pc++;
		if(c.filled(nev)) pc++;
		if(c.filled(szekhely)) pc++;
		if(c.filled(adoszam)) pc++;
		return pc;
	}
	public String RTF(JTextField jtf) {
		return jtf.getText();
	}
}
