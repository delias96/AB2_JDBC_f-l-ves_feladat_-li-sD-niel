package JDBC;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class Leker extends JDialog {

	private DB_methods dbm = new DB_methods();
	private JTextField textField;
	
	public Leker(JDialog d) {
		super(d,"Lekérdezések",true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Az \u00E1tlag fizet\u00E9s a dolgoz\u00F3kn\u00E1l:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(28, 23, 185, 22);
		getContentPane().add(lblNewLabel);
		
		JLabel lblfizetes = new JLabel("");
		lblfizetes.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblfizetes.setBounds(235, 23, 78, 19);
		getContentPane().add(lblfizetes);
		
		JButton btnatlag = new JButton("Sz\u00E1mol");
		btnatlag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fizetes= String.valueOf(dbm.atlagfizetes());
				lblfizetes.setText(fizetes+" HUF");
			}
		});
		btnatlag.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnatlag.setBounds(272, 52, 85, 21);
		getContentPane().add(btnatlag);
		
		JLabel lblNewLabel_1 = new JLabel("H\u00E1ny ember dolgozik egy c\u00E9gn\u00E9l:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(28, 119, 208, 13);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblmunkas = new JLabel("");
		lblmunkas.setHorizontalAlignment(SwingConstants.CENTER);
		lblmunkas.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblmunkas.setBounds(235, 120, 176, 13);
		getContentPane().add(lblmunkas);
		
		JButton btndolgoz = new JButton("Keres");
		btndolgoz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ceg = textField.getText();
				String db = String.valueOf(dbm.dolgoz(ceg));
				lblmunkas.setText(db+" db van a cégnél");
			}
		});
		btndolgoz.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btndolgoz.setBounds(272, 143, 85, 21);
		getContentPane().add(btndolgoz);
		
		textField = new JTextField();
		textField.setBounds(117, 144, 96, 19);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("C\u00E9g n\u00E9v:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(28, 147, 79, 13);
		getContentPane().add(lblNewLabel_2);

	}
}
