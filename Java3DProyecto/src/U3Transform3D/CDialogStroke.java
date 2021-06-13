package U3Transform3D;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

public class CDialogStroke extends JDialog implements ActionListener{
	
	JButton ba,ca,color;
	JRadioButton op1,op2,op3,op4,op5,op6,op7,op8,op9;
	ButtonGroup grupoC, grupoJ,grupoX;
	JLabel ltext, ltext2 ,lanch, indi1,indi2, indi3,indi4;
	JSlider barra;
	BasicStroke Stroke,Stroke2;
	Color colorS;
	float anchor;
	int cap,join, randomr,randomg,randomb,tipoF;
	Boolean capB, joinB, solidB, dashB, none;
	
	public CDialogStroke(Interfaz ref, boolean modal) {
		super(ref.vent,modal);
		setSize(800,500);
		setTitle("Strokes con terminaciones");
		setResizable(false);
		setLocationRelativeTo(this);
		this.setBackground(Color.LIGHT_GRAY);
		setLayout(null);		
		barra = new JSlider (JSlider.HORIZONTAL,0,20,0);
		barra.setMinorTickSpacing(2);
		barra.setMajorTickSpacing(5);
		barra.setPaintTicks(true);
		barra.setPaintLabels(true);
		//EL ANCHO DEL SROKE PREDERTEMRINADO
		anchor=3;
		barra.setValue((int)anchor);
		barra.setBounds(450, 60, 300, 50);
		add(barra);
		capB=false;
		joinB=false;
		solidB=false;
		dashB=false;
		none=false;

		
		//Line stroke
		Stroke = new BasicStroke(1.0f);
		Stroke2=null;
		//SETEAMOS POR DEFECTO NUESTRO TIPOF
		tipoF=1; //Es la figura completa
		
		ltext = new JLabel ("Elige tipo de linea:");
		Font Fuente = new Font("Lucida Sans", Font.BOLD, 16);
		ltext.setFont(Fuente);
		ltext2 = new JLabel ("Anchor de la linea");
		ltext2.setFont(Fuente);
		indi1 = new JLabel ("Cap -> Uniones (en Dashed recomendado)");
		indi2 = new JLabel ("Join -> Bordes (en Solid recomendado)");
		indi3 = new JLabel ("Tipo");
		indi4 = new JLabel ("Color Stroke: (tiene el rojo por defecto)");
		lanch = new JLabel ("Anchor (PX):"+anchor);
		ltext.setBounds(10,2, 400, 40);
		ltext2.setBounds(430,2, 400, 40);
		lanch.setBounds(430,31, 110, 30);
		indi1.setBounds(10, 60, 285, 20);
		indi2.setBounds(10, 120, 250, 20);
		indi3.setBounds(10, 180, 200, 20);
		indi4.setBounds(10, 300, 250, 20);

		TitledBorder tb1=new TitledBorder("Tipo de stroke");
		tb1.setTitleJustification(TitledBorder.CENTER);
		TitledBorder tb2=new TitledBorder("Anchor (px)");
		tb2.setTitleJustification(TitledBorder.CENTER);
		ltext.setBorder(tb1);
		ltext2.setBorder(tb2);
		add(ltext);
		add(ltext2);
		add(lanch);
		add(indi1);add(indi2);add(indi3);add(indi4);
		
		op1= creaRadio(true, "Butt Cap", 10, 80, 100, 20);
		op2= creaRadio(false, "Round Cap",120, 80, 100, 20);
		op3= creaRadio(false, "Square Cap",230,80,100, 20);
		op4= creaRadio(false, "Bevel Join",10,150,100,20);
		op5= creaRadio(true, "Miter Join",120, 150, 100, 20);
		op6= creaRadio(false, "Round Join",230,150,100, 20);
		op7= creaRadio(false, "Dashed Line",10,200,100,20);
		op8= creaRadio(true, "Solid line",120, 200,100, 20);
		op9= creaRadio(false, "none",230,200,100,20);

		grupoC = new ButtonGroup ();
		grupoJ = new ButtonGroup ();
		grupoX = new ButtonGroup ();
		grupoC.add(op1);
		grupoC.add(op2);
		grupoC.add(op3);
		grupoJ.add(op4);
		grupoJ.add(op5);
		grupoJ.add(op6);
		grupoX.add(op7);
		grupoX.add(op8);
		grupoX.add(op9);
		add(op1);
		add(op2);
		add(op3);
		add(op4);
		add(op5);
		add(op6);
		add(op7);
		add(op8);
		add(op9);

		//TIPOS DE TERMINACIONES Y UNIONES
		cap=BasicStroke.CAP_BUTT;
		join=BasicStroke.JOIN_MITER;
		ba = new JButton ("Aceptar");
		ca = new JButton ("Cancelar");
		color = new JButton ("Color de Stroke Aleatorio");
		
		colorS=new Color(255,0,0);
		
		ba.setBounds(530,430,100,30);
		ca.setBounds(640,430,100,30);
		color.setBounds(10,330,200,30);
		add(ba);
		add(ca);
		add(color);
		
		barra.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (barra.getValue()!=0)//ANCHOR SI LLEGA A 0 SE ESTABLECE EL 3PX
					anchor=(int)barra.getValue();
				else 
					anchor=3;
				lanch.setText("Anchor (PX):"+anchor);
				repaint();
			}
		});
		ba.addActionListener(this);
		ca.addActionListener(this);
		color.addActionListener(this);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	}
	public JRadioButton creaRadio(boolean b, String txt, int x, int y, int w,int h){
		JRadioButton rb = new JRadioButton(txt,b);
		rb.setBounds(x, y, w, h);
		rb.addActionListener(this);
		return rb;
	}
	public boolean isnone() {
		return none;
	}
	public void actionPerformed(ActionEvent e) {
			
		if (e.getSource()==ba) {
			Stroke2 = Stroke;
			setVisible(false);
			dispose();
			
		}else if(e.getSource()==ca) {
			Stroke2=null;
			setVisible(false);
			dispose();
		}
		else if (e.getSource()==color) {
			//COLOR ALEATORIO
			Random R = new Random();
			randomr=R.nextInt(255);
			randomg=R.nextInt(255);
			randomb=R.nextInt(255);
			//System.out.println(randomb+","+randomg+","+randomb);
			colorS = new Color(randomr, randomg,randomb);
			repaint();
		}
		//OPCIONES
		if (op1.isSelected()) {
			cap=BasicStroke.CAP_BUTT;
			capB = true;
		}
		if (op2.isSelected()) {
				cap=BasicStroke.CAP_ROUND;
				capB = true;
			}
		if (op3.isSelected()) {
				cap=BasicStroke.CAP_SQUARE;
				capB = true;
			}
		if (op4.isSelected()) {
			join=BasicStroke.JOIN_BEVEL;
			joinB = true;
		}
		if (op5.isSelected()) {
			join=BasicStroke.JOIN_MITER;
			joinB = true;
		}
		if (op6.isSelected()) {
			join=BasicStroke.JOIN_ROUND;
			joinB = true;
		}if (op7.isSelected()) {
			dashB=true;
			solidB=false;
			none=false;
		}
		if (op8.isSelected()) {
			solidB=true;
			dashB=false;
			none=false;
		}
		if (op9.isSelected()) {
			none=true;
			solidB=false;
			dashB=false;
		}
		repaint();
	}
	
	public void paint(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D)g;
		if (barra.getValue()!=0)//ANCHOR SI LLEGA A 0 SE ESTABLECE EL 3PX
			anchor=(int)barra.getValue();
		else 
			anchor=3;
		if (solidB) {
			if (capB && joinB ==false)//CAP CON UN JOIN BEVEL
				Stroke=new BasicStroke(anchor,cap, BasicStroke.JOIN_BEVEL);
			else if (joinB==true && capB==true)//SE PONEN NORMAL
				Stroke=new BasicStroke(anchor,cap, join);
			else if (capB==false && joinB == true)
				Stroke=new BasicStroke(anchor,BasicStroke.CAP_BUTT, join);
		}else if (dashB) {
			//ES DASH ES UNO PREDETERMINADO
			float dash[]= {15f,0f,3f};
			if (capB && joinB ==false)//CAP CON UN JOIN BEVEL
				Stroke=new BasicStroke(anchor,cap, BasicStroke.JOIN_BEVEL,8f,dash,10f);
			else if (joinB==true && capB==true)//SE PONEN NORMAL
				Stroke=new BasicStroke(anchor,cap, join,9f,dash,15f);
			else if (capB==false && joinB == true)
				Stroke=new BasicStroke(anchor,BasicStroke.CAP_BUTT, join,7f,dash,20f);
		}
		if(!none) {
			g2.setStroke(Stroke);
			g2.setColor(colorS); 
			g2.drawRect(520,200,150,150);
			 g2.dispose();
		}
		else{
			Stroke=null;
			g2.setColor(Color.LIGHT_GRAY); 
		g2.fillRect(520,250,150,150);
		}
	}
	public void Despliega () {
		setVisible(true);
	}
	public BasicStroke ReturnS () {
		return Stroke2;
	}
	//regreso color
	public Color returnC() {
		return colorS;
	}
	public int retornaTF () {
		return tipoF;
	}
}
