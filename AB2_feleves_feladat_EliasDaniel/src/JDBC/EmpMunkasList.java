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

public class EmpMunkasList extends JDialog {
	private JTable table;
	private EmpTM etm;
	private JTextField id;
	private JTextField nev;
	private JTextField szul_ido;
	private JTextField munkakor;
	private JTextField fizetes;
	private DB_methods dbm = new DB_methods();
	private JButton btnTorles;
	private JButton btnModosit;
	private Checker c = new Checker();

	public EmpMunkasList(JDialog d, EmpTM betm) {
		super(d, "Munk�s lista", true);
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
		for(int i = 0; i < 6; i++) {
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
						if(c.goodDate(szul_ido, "Sz�let�si id�"))
							if(c.filled(munkakor, "Munkak�r"))
								if(c.goodInt(fizetes, "fizet�s"))
				dbm.Insertmunkas(RTF(id), RTF(nev), RTF(szul_ido), RTF(munkakor), RTF(fizetes));
				dispose();
			}
		});
		btnhozzaad.setBounds(193, 233, 140, 21);
		getContentPane().add(btnhozzaad);
		
		id = new JTextField();
		id.setBounds(38, 200, 24, 19);
		getContentPane().add(id);
		id.setColumns(10);
		
		nev = new JTextField();
		nev.setBounds(64, 200, 76, 19);
		getContentPane().add(nev);
		nev.setColumns(10);
		
		szul_ido = new JTextField();
		szul_ido.setBounds(142, 200, 80, 19);
		getContentPane().add(szul_ido);
		szul_ido.setColumns(10);
		
		munkakor = new JTextField();
		munkakor.setBounds(224, 200, 120, 19);
		getContentPane().add(munkakor);
		munkakor.setColumns(10);
		
		fizetes = new JTextField();
		fizetes.setBounds(344, 200, 78, 19);
		getContentPane().add(fizetes);
		fizetes.setColumns(10);
		
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
					dbm.DeleteData("munkas", kod);
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
						if(ok && c.filled(fizetes)) ok = c.goodInt(fizetes, "Fizet�s");
						if(ok) {
							String mkod = etm.getValueAt(jel, 1).toString();
							if(c.filled(id)) dbm.Update("munkas", mkod, "id", c.RTF(id));
							if(c.filled(nev)) dbm.Update("munkas", mkod, "nev", c.RTF(nev));
							if(c.filled(szul_ido)) dbm.Update("munkas", mkod, "szul_ido", c.RTF(szul_ido));
							if(c.filled(munkakor)) dbm.Update("munkas", mkod, "munkakor", c.RTF(munkakor));
							if(c.filled(fizetes)) dbm.Update("munkas", mkod, "fizetes", c.RTF(fizetes));
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
		szul_ido.setText("");
		munkakor.setText("");
		fizetes.setText("");
		etm.setValueAt(false, i, 0);
	}
	public int modDataPc() {
		int pc = 0;
		if(c.filled(id)) pc++;
		if(c.filled(nev)) pc++;
		if(c.filled(szul_ido)) pc++;
		if(c.filled(munkakor)) pc++;
		if(c.filled(fizetes)) pc++;
		return pc;
	}
	public String RTF(JTextField jtf) {
		return jtf.getText();
	}
}
