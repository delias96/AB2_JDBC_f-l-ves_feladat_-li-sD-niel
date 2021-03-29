package JDBC;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Program extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private EmpTM etm;
	private EmpTM ctm;
	private EmpTM mtm;
	private DB_methods db = new DB_methods();
	
	
	public Program(JFrame f) {
		super(f,"Menü",true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton btnmunkalista = new JButton("Munk\u00E1s list\u00E1z\u00E1sa");
			btnmunkalista.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					etm = db.ReadMunkas();
					EmpMunkasList l = new EmpMunkasList(Program.this, etm);
					l.setVisible(true);
				}
			});
			btnmunkalista.setFont(new Font("Tahoma", Font.PLAIN, 13));
			btnmunkalista.setBounds(26, 29, 161, 32);
			contentPanel.add(btnmunkalista);
		}
		
		JButton btnceglista = new JButton("C\u00E9gek list\u00E1z\u00E1sa");
		btnceglista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				etm = db.ReadCeg();
				EmpCegList cl = new EmpCegList(Program.this,etm);
				cl.setVisible(true);
			}
		});
		btnceglista.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnceglista.setBounds(222, 29, 173, 32);
		contentPanel.add(btnceglista);
		
		JButton btnNewButton = new JButton("Munkahely Lista");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				etm = db.ReadDolgoz();
				ctm = db.ReadCeg();
				mtm = db.ReadMunkas();
				EmpDolgozList dl = new EmpDolgozList(Program.this,etm,ctm,mtm);
				dl.setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton.setBounds(26, 82, 161, 32);
		contentPanel.add(btnNewButton);
		
		JButton btnfizeteslista = new JButton("Fizet\u00E9s Lista");
		btnfizeteslista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fizetes f = new fizetes(Program.this);
				f.setVisible(true);
			}
		});
		btnfizeteslista.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnfizeteslista.setBounds(222, 82, 173, 32);
		contentPanel.add(btnfizeteslista);
		
		JButton btnLekér = new JButton("Lek\u00E9rdez\u00E9sek");
		btnLekér.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Leker lek = new Leker(Program.this);
				lek.setVisible(true);
			}
		});
		btnLekér.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLekér.setBounds(26, 135, 161, 32);
		contentPanel.add(btnLekér);
	}
}
