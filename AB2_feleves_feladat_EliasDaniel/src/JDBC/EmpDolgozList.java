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
import javax.swing.JComboBox;

public class EmpDolgozList extends JDialog {
	private JTable table;
	private EmpTM etm;
	private EmpTM ctm;
	private EmpTM mtm;
	private JTextField id;
	private DB_methods dbm = new DB_methods();
	private JButton btnTorles;
	private JButton btnModosit;
	private Checker c = new Checker();
	private String selected = "selected";
	private String selected2 = "selected";

	public EmpDolgozList(JDialog d, EmpTM betm,EmpTM cetm,EmpTM metm) {
		super(d, "Munkahely lista", true);
		etm = betm;
		ctm= cetm;
		mtm = metm;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JButton btnBezar = new JButton("Bez\u00E1r\r\n");
		btnBezar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		JComboBox id_c = new JComboBox();
		id_c.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected = id_c.getSelectedItem().toString();
			}
		});
		id_c.setBounds(98, 200, 152, 21);
		getContentPane().add(id_c);
		id_c.addItem("Válassz!");
		for (int i = 0; i < ctm.getRowCount(); i++) {
			id_c.addItem(RTM(ctm,i,1) + " - "+RTM(ctm,i,2));
		}
		
		JComboBox id_m = new JComboBox();
		id_m.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected2 = id_m.getSelectedItem().toString();
			}
		});
		id_m.setBounds(260, 200, 148, 21);
		getContentPane().add(id_m);

		id_m.addItem("Válassz!");
		for (int i = 0; i < mtm.getRowCount(); i++) {
			id_m.addItem(RTM(mtm,i,1) + " - "+RTM(mtm,i,2));
		}
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
			if(i==0 || i==1 || i==2 || i==4) tc.setPreferredWidth(30); 
				else{tc.setPreferredWidth(100);}
			}
		table.setAutoCreateRowSorter(true);
		TableRowSorter<EmpTM> trs=(TableRowSorter<EmpTM>)table.getRowSorter();
		trs.setSortable(0, false);
		JButton btnhozzaad = new JButton("\u00DAj adat felvitele");
		btnhozzaad.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnhozzaad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(c.goodInt(id, "id"))
					if(selected.contains("Válassz!") || selected2.contains("Válassz!")) {
						dbm.SM("A kijelölt elem nem feltölthetõ", 0);
					}else {
						int index=selected.indexOf(" - ");
						String kod = selected.substring(0,index);
						int index2=selected2.indexOf(" - ");
						String kod2 = selected2.substring(0,index2);
						dbm.Insertdolgoz(RTF(id), kod, kod2);
					}
				
				dispose();
			}
		});
		btnhozzaad.setBounds(193, 233, 138, 21);
		getContentPane().add(btnhozzaad);
		
		id = new JTextField();
		id.setBounds(55, 200, 24, 19);
		getContentPane().add(id);
		id.setColumns(10);
		
		btnTorles = new JButton("T\u00F6rl\u00E9s");
		btnTorles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int db = 0,jel = 0,x = 0;
				for(x = 0;x < etm.getRowCount(); x++)
					if((Boolean)etm.getValueAt(x, 0)) {db++;jel=x;}
				if(db==0)dbm.SM("Nincs kijelölve a törlendõ rekord!", 0);
				if(db>1)dbm.SM("Több rekord van kijelölve!\n Egyszerre csak egy rekord törölhetõ", 0);
				if(db==1) {
					String kod = etm.getValueAt(jel, 1).toString();
					etm.removeRow(jel);
					dbm.DeleteData("dolgoz", kod);
					dbm.SM("A rekord törölve!",1);
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
				if(db==0)dbm.SM("Nincs kijelölve a módosítandó rekord!", 0);
				if(db>1)dbm.SM("Több rekord van kijelölve!\n Egyszerre csak egy rekord módosítható", 0);
				if(db==1) {
					if(modDataPc()>0) {
						boolean ok = true;
						if(c.filled(id)) ok = c.goodInt(id, "Kód");
						if(ok) {
							String mkod = etm.getValueAt(jel, 1).toString();
							if(c.filled(id)) dbm.Update("dolgoz", mkod, "id", c.RTF(id));
							if(!selected.contains("Válassz!")) {
								int index=selected.indexOf(" - ");
								String kod = selected.substring(0,index);
								dbm.Update("dolgoz", mkod, "id_ceg", kod);
							}
							if(!selected2.contains("Válassz!")) {
								int index2=selected2.indexOf(" - ");
								String kod2 = selected2.substring(0,index2);
								dbm.Update("dolgoz", mkod, "id_m", kod2);
							}
							dbm.SM("A rekord módosítva", 1);
							reset(jel);
							dispose();
						}
					}else {
							dbm.SM("Nincs kitöltve egyetlen módosító adatmezõ sem!", 1);
						}
			
		}}});
		btnModosit.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnModosit.setBounds(10, 233, 85, 21);
		getContentPane().add(btnModosit);
		

	}
	public void reset(int i) {
		id.setText("");
		etm.setValueAt(false, i, 0);
	}
	public int modDataPc() {
		int pc = 0;
		if(c.filled(id)) pc++;
		if(!selected.contains("Válassz!")) pc++;
		if(!selected2.contains("Válassz!")) pc++;
		return pc;
	}
	public String RTF(JTextField jtf) {
		return jtf.getText();
	}
	public String RTM(EmpTM etm,int row, int col) {
		return etm.getValueAt(row, col).toString();
	}
}
