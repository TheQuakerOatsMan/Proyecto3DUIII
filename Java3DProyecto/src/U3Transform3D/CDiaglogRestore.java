package U3Transform3D;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CDiaglogRestore extends JDialog {
	
	boolean restore;
	JLabel et1,et2;
	JTextField ct1;
	JButton bac,bca;
	JPanel botones, caja;
	
	
	public CDiaglogRestore(Interfaz ref, boolean modal) {
		super (ref.vent,modal);
		setSize(640, 120);
        setLocationRelativeTo(this);
        setUndecorated(true);        
        caja = new JPanel(new GridLayout(2,1));
        et1 = new JLabel("CONFIRMACION");
		et2 = new JLabel ("¿Estás seguro de querer restaurar la figura? (Se eliminaran todos los cambios)");
		et1.setIcon(new ImageIcon(getClass().getResource("/U3Transform3D/rec/info.png")));
        et2.setFont(new Font("Lucida Sans", Font.PLAIN, 15));
        caja.add(et1);
        caja.add(et2);
        getContentPane().add(caja, BorderLayout.NORTH);
		bac = new JButton("Aceptar");
		bca = new JButton("Cancelar");
		bac.setFont(new Font("Lucida Sans", Font.PLAIN, 18));
		bca.setFont(new Font("Lucida Sans", Font.PLAIN, 18));
        bac.setBackground(Color.LIGHT_GRAY);
        bac.setForeground(Color.WHITE);
        bca.setBackground(Color.LIGHT_GRAY);
        bca.setForeground(Color.WHITE);
        botones = new JPanel(new FlowLayout());
        botones.add(bac);
        botones.add(bca);
        getContentPane().add(botones, BorderLayout.SOUTH);

		bac.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restore = true;
				dispose();
			}
		});
		bca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restore = false;
				dispose();
			}
		});
		
	}
	
	public boolean Mostrar() {
		setVisible(true);
		return restore;
	}
}
