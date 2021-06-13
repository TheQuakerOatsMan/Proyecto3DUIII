package U3Transform3D;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CDialogColors extends JDialog implements ActionListener{
	
	int r,ge,b,transparencia,tipoFig;
	JButton ba,bc,ba2,bc2;
	JLabel lbl1,lbl2,lbl3,lbl4,lbl5,lbl6,lbl7,lbl8;
	JSlider trans1, trasn2, RS,GS,BS;
	JTabbedPane panelM;
	Choice colores;
	ButtonGroup bg,bg2;
	JRadioButton rb1,rb2,rb3,rb4,rb5,rb6,rb7,rb8;
	Color Rc,Bc,Bc2, colors[]= {Color.WHITE,Color.BLUE,Color.GREEN,Color.YELLOW,
			Color.ORANGE,Color.CYAN,Color.DARK_GRAY,
			Color.GRAY,Color.RED,Color.PINK,Color.MAGENTA,Color.BLACK};
	//CUADRO DIALOGO PARA CREAR COLORES JCOLORCHOOSER
	public CDialogColors(Interfaz ref, boolean modal) {
		super(ref.vent,modal);
		setTitle("Color Chooser");
		setSize(600,450);
		setResizable(false);
		setLocationRelativeTo(this);
		setLayout(null);
		
		//COlOR NORMAL
		JPanel panel1 = new JPanel(){
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D)g;
				g2.setColor(Bc);
				g2.fillRect(380,70,150,150);

			}
		};
		panel1.setLayout(null);
		lbl1= new JLabel("Color normal");
		Font Fuente = new Font("Lucida Sans", Font.BOLD, 20);
		lbl1.setFont(Fuente);
		lbl2 = new JLabel("Selecciona el color:");
		lbl3 = new JLabel("Transparencia:");
		lbl1.setBounds(20,10, 200, 20);
		lbl2.setBounds(20,30, 200, 20);
		panel1.add(lbl1);
		panel1.add(lbl2);
		colores = new Choice();
		cargaColor();
		colores.setBounds(50,60,200,50);
		panel1.add(colores);
		
		trans1 = new JSlider(JSlider.HORIZONTAL,0,100,100);
		lbl3.setBounds(20, 110, 100, 20);
		trans1.setBounds(20, 130, 300, 60);
		trans1.setMajorTickSpacing(10);
		trans1.setMinorTickSpacing(5);
		trans1.setPaintTicks(true);
		trans1.setPaintLabels(true);
		panel1.add(lbl3);
		//panel1.add(trans1);
		
		ba = new JButton("Aceptar");
		bc = new JButton("Cancelar");
		ba.setBounds(30, 200, 100, 30);
		bc.setBounds(140, 200, 100, 30);
		panel1.add(ba);
		panel1.add(bc);
		tipoFig=1;
		//DONDE SE ENCONTRARA
		JPanel PanelT = new JPanel(new GridLayout (1,4));
		TitledBorder tb3= new TitledBorder("Selecciona el lugar a aplicar");
		tb3.setTitleJustification(TitledBorder.CENTER);
		bg = new ButtonGroup ();
		rb1 = new JRadioButton("En toda la figura", true);
		rb2 = new JRadioButton("Solo decagonos", false);
		rb3 = new JRadioButton("Solo pentagonos", false);
		rb4 = new JRadioButton("Solo triangulos", false);
		bg.add(rb1);bg.add(rb2);bg.add(rb3);bg.add(rb4);
		PanelT.add(rb1);PanelT.add(rb2);PanelT.add(rb3);PanelT.add(rb4);
		PanelT.setBounds(10, 290, 500, 50);
		PanelT.setBorder(tb3);
		panel1.add(PanelT);
		
		
		//COLOR RGB
		lbl4 = new JLabel("Color Red (RED)");
		lbl5 = new JLabel("Color Green (VERDE)");
		lbl6 = new JLabel("Color Blue (Azul)");
		RS = new JSlider(JSlider.HORIZONTAL,0,255,255);
		GS = new JSlider(JSlider.HORIZONTAL,0,255,255);
		BS = new JSlider(JSlider.HORIZONTAL,0,255,255);
		trasn2 = new JSlider(JSlider.HORIZONTAL,0,255,255);
		Bc2= new Color (r,ge,b,transparencia);
		
		r=0; ge=0; b=0; transparencia=0;
		r=RS.getValue(); ge=GS.getValue(); b=BS.getValue();
		transparencia=trasn2.getValue();
		JPanel panel2 = new JPanel() {
				@Override
	            public void paintComponent(Graphics g) {
	            	super.paintComponent(g);
	        		Graphics2D g2 = (Graphics2D) g;
	        		Bc2 = new Color (r,ge,b,transparencia);
	        		g2.setColor(Bc2);
	        		g2.fillRect(380,40,150,150);
	    }
		};
		panel2.setLayout(null);

		lbl4.setBounds(20, 10, 300, 20);
		RS.setBounds(20, 30, 300, 60);
		RS.setMajorTickSpacing(30);
		RS.setMinorTickSpacing(15);
		RS.setPaintTicks(true);
		RS.setPaintLabels(true);
		lbl5.setBounds(20, 90, 300, 20);
		GS.setBounds(20, 110, 300, 60);
		GS.setMajorTickSpacing(30);
		GS.setMinorTickSpacing(15);
		GS.setPaintTicks(true);
		GS.setPaintLabels(true);
		lbl6.setBounds(20, 170, 300, 20);
		BS.setBounds(20, 180, 300, 60);
		BS.setMajorTickSpacing(30);
		BS.setMinorTickSpacing(15);
		BS.setPaintTicks(true);
		BS.setPaintLabels(true);
		panel2.add(lbl4);
		panel2.add(RS);
		panel2.add(lbl5);
		panel2.add(GS);
		panel2.add(lbl6);
		panel2.add(BS);
		lbl3.setBounds(20, 250, 100, 20);
		trasn2.setBounds(20, 270, 300, 60);
		trasn2.setMajorTickSpacing(30);
		trasn2.setMinorTickSpacing(15);
		trasn2.setPaintTicks(true);
		trasn2.setPaintLabels(true);
		panel2.add(lbl3);
		panel2.add(trasn2);
		ba2 = new JButton("Aceptar");
		bc2 = new JButton("Cancelar");
		ba2.setBounds(340, 260, 100, 30);
		bc2.setBounds(450, 260, 100, 30);
		panel2.add(ba2);
		panel2.add(bc2);
		lbl7 = new JLabel ("Resultado (rgb):");
		lbl8 = new JLabel("("+RS.getValue()+","+GS.getValue()+","+BS.getValue()+","+trasn2.getValue()+")");
		lbl7.setBounds(350, 200, 100, 30);
		lbl8.setBounds(350, 230, 150, 30);
		panel2.add(lbl7);
		panel2.add(lbl8);
		//PANEL MENU 
		panelM = new JTabbedPane(JTabbedPane.TOP);
		panelM.setBounds(0, 0 , getWidth(), getHeight());
		panelM.addTab("Color Normal", null, panel1, null);
		panelM.addTab("Color RGB", null, panel2, null);
        add(panelM);
        Rc = buscaCol(0);
        Bc = buscaCol(0);
        
        //DONDE SE ENCONTRARA
      	JPanel PanelT2 = new JPanel(new GridLayout (1,4));
      	bg2 = new ButtonGroup ();
      	rb5 = new JRadioButton("En toda la figura", true);
      	rb6 = new JRadioButton("Solo decagonos", false);
      	rb7 = new JRadioButton("Solo pentagonos", false);
      	rb8 = new JRadioButton("Solo triangulos", false);
      	bg2.add(rb5);bg2.add(rb6);bg2.add(rb7);bg2.add(rb8);
      	PanelT2.add(rb5);PanelT2.add(rb6);PanelT2.add(rb7);PanelT2.add(rb8);
      	PanelT2.setBounds(10, 340, 500, 50);
      	PanelT2.setBorder(tb3);
      	panel2.add(PanelT2);
        ba.addActionListener(this);
		bc.addActionListener(this);
		ba2.addActionListener(this);
		bc2.addActionListener(this);
		rb1.addActionListener(this);
		rb2.addActionListener(this);
		rb3.addActionListener(this);
		rb4.addActionListener(this);
		rb5.addActionListener(this);
		rb6.addActionListener(this);
		rb7.addActionListener(this);
		rb8.addActionListener(this);
		colores.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int cn= colores.getSelectedIndex();
				Bc=buscaCol(cn);
				Rc=Bc;
				repaint();
			}
		});
		RS.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				r=RS.getValue();
				lbl8.setText("("+RS.getValue()+","+GS.getValue()+","+BS.getValue()+","+trasn2.getValue()+")");
				panel2.repaint();
			}
		});
		GS.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				ge=GS.getValue();
				lbl8.setText("("+RS.getValue()+","+GS.getValue()+","+BS.getValue()+","+trasn2.getValue()+")");
				panel2.repaint();
			}
		});
		BS.addChangeListener(new ChangeListener() {
	
			@Override
			public void stateChanged(ChangeEvent arg0) {
				b=BS.getValue();
				lbl8.setText("("+RS.getValue()+","+GS.getValue()+","+BS.getValue()+","+trasn2.getValue()+")");
				panel2.repaint();
			}
		});
		trasn2.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				transparencia=trasn2.getValue();
				lbl8.setText("("+RS.getValue()+","+GS.getValue()+","+BS.getValue()+","+trasn2.getValue()+")");
				panel2.repaint();
			}
		});
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	}
	public Color buscaCol(int c) {
		Color cn= colors[c];
		return cn;
	}
	public void cargaColor() {
		colores.add("WHITE");
		colores.add("BLUE");
		colores.add("GREEN");
		colores.add("YELLOW");
		colores.add("ORANGE");
		colores.add("CYAN");
		colores.add("DARK_GRAY");
		colores.add("GRAY");
		colores.add("RED");
		colores.add("PINK");
		colores.add("MAGENTA");
		colores.add("BLACK");
	}
	public void Despliega () {
		setVisible(true);
	}
	public Color getColorF() {
		return Rc;
	}
	public int getTipoF() {
		return tipoFig;
	}
	public int setTipoF() {
		int t=0;//POR DEFECTO
		if (rb1.isSelected())
			t=1;
		if (rb5.isSelected())
			t=1;
		if (rb2.isSelected())
			t=2;
		if (rb6.isSelected())
			t=2;
		if (rb3.isSelected())
			t=3;
		if (rb7.isSelected())
			t=3;
		if (rb4.isSelected())
			t=4;
		if (rb8.isSelected())
			t=4;
		//System.out.println("tipo->"+t);
		return t;
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==ba) {
			//RETURN COLOR
				Rc=Bc;
			tipoFig = setTipoF();
			setVisible(false);
			dispose();
			
		}
		else if (e.getSource()==rb1 || e.getSource()==rb2
				|| e.getSource()==rb3 || e.getSource()==rb4
				|| e.getSource()==rb5 || e.getSource()==rb6
				|| e.getSource()==rb7 || e.getSource()==rb8) {
				tipoFig = setTipoF();
			}
		else if(e.getSource()==bc || e.getSource()==bc2) {
			Rc=null;
			setVisible(false);
			dispose();
		}
		if (e.getSource()==ba2) {
			//RETURN COLOR
				Rc=Bc2;
			tipoFig = setTipoF();
			setVisible(false);
			dispose();
		}
	}

}
