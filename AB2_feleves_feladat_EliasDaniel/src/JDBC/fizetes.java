package JDBC;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class fizetes extends JDialog {
	
	private DB_methods dbm = new DB_methods();
	private PreparedStatement ps = null;
	private Connection conn = null;

	public fizetes(JDialog d) {
		super(d,"Fizetés paraméterezetten", true);
		dbm.Reg();
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 53, 416, 200);
		getContentPane().add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		
		JLabel lblNewLabel = new JLabel("Fizet\u00E9sek kateg\u00F3ri\u00E1ja:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 10, 164, 33);
		getContentPane().add(lblNewLabel);
		
		JButton btnkimutat = new JButton("Kimutat");
		btnkimutat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nev; int fizetes;
				String sqlp = "select nev,fizetes from munkas where fizetes > ? and fizetes <= ?;";
				try {
					String url ="jdbc:sqlite:D:/sqlite/jdbcbead";
					conn = DriverManager.getConnection(url);
					ps = conn.prepareStatement(sqlp);
					for (int i = 80000; i <= 200000; i+= 40000) {
						int fiz1 = i-40000,fiz2=i;
						if(i==80000) fiz1=0;
						if(i==200000) fiz2=200001;
						ps.setInt(1,fiz1);
						ps.setInt(2,fiz2);
						ResultSet rs = ps.executeQuery();
						if(fiz1>0)fiz1++;
						textArea.append("\nDolgozók "+fiz1+" és "+fiz2+" közötti fizetéssel: "+System.getProperty("line.separator"));
						while(rs.next()) {
							nev = rs.getString("nev");
							fizetes = rs.getInt("fizetes");
							textArea.append(nev+" - "+ fizetes+System.getProperty("line.separator"));
						}
					}
					conn.close();
				} catch (SQLException e1) {
					dbm.SM("Lekérdezés: "+e1.getMessage(), 0);
				}
			}
		});
		btnkimutat.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnkimutat.setBounds(211, 18, 85, 21);
		getContentPane().add(btnkimutat);
		

		
	}
}
