package JDBC;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Bejelentkez extends JFrame {

	private JPanel contentPane;
	private JTextField felhasznalo;
	private JTextField jelszo;
	private DB_methods dbm = new DB_methods();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Bejelentkez frame = new Bejelentkez();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Bejelentkez() {
		dbm.Reg();
		setTitle("Bejelentkezés");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Felhaszn\u00E1l\u00F3 n\u00E9v:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(75, 81, 105, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblJelsz = new JLabel("Jelsz\u00F3:");
		lblJelsz.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblJelsz.setBounds(75, 104, 105, 13);
		contentPane.add(lblJelsz);
		
		felhasznalo = new JTextField();
		felhasznalo.setBounds(188, 80, 126, 19);
		contentPane.add(felhasznalo);
		felhasznalo.setColumns(10);
		
		jelszo = new JTextField();
		jelszo.setBounds(188, 103, 126, 19);
		contentPane.add(jelszo);
		jelszo.setColumns(10);
		
		JButton btnNewButton = new JButton("Bejelentkez\u00E9s");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nev = felhasznalo.getText();
				String jel  = jelszo.getText();
				int pc = dbm.Identification(nev, jel);
				if (pc == 1) {
					dbm.SM("Minden rendben, dolgozhatsz! ",1);
					Program pr = new Program(Bejelentkez.this);
					pr.setVisible(true);
				}else {
					dbm.SM("Nincs jogod az adatbázis eléréséhez! ",2);
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton.setBounds(138, 153, 115, 25);
		contentPane.add(btnNewButton);
	}
}
