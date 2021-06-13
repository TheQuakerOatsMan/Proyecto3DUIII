package U3Transform3D;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Interfaz extends JPanel implements ChangeListener, ActionListener{
	JFrame vent;
	JTabbedPane Opciones,Lienzo;
	Figura3d F;
	JSlider S1,S2,S3,S4;
	JPanel P2,PCentr,PVistas;
	JTextField txt1;
	//relleno y lineas
	JRadioButton rbstroke1,rbstroke2,rbre1,rbre2,rbre3,rbre4,rbre5,rbl1,rbl2;
	JButton btniz,btnder,btnR,btnC,btnF,btnBS;
	ButtonGroup bg1,bg2;
	Image FondoLienzo, img;
	URL ruta;
	int vista = -1, cantB=3,tipoFTexture=1,tipoColor=1;
	boolean arrastra = false, pintaC=false,stroke=false,relleno=false,rtri=false,rpen=false,rdeca=false;
	//PARA JAVA2D
	Action ColorF,Stroke,Reset,Relleno;
	BasicStroke Strok=null;
	Color colorStrok,newColor;
	TexturePaint rimg;
	boolean contorno,bandera,colorFondo,txtPaint;
	
	public Interfaz(double fig[][], int secf[][], int secf2[]) {
		vent = new JFrame("Transformaciones en 3D");
		vent.setSize(1000,635);
		setSize(1000,600);
		F = new Figura3d(fig,secf,secf2);
		//NO ESCALABLE
		vent.setResizable(false);
		//POSICION CENTRO
		vent.setLocationRelativeTo(this);
		//NULO
		cargaOpciones();
		cargaPFig();
		vent.add(this);
		vent.setVisible(true);
		vent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void cargaOpciones() {
		Opciones = new JTabbedPane(JTabbedPane.TOP);
		Opciones.setBounds(0, 0, getWidth(), 100);
		//PANEL1
		JPanel P1 = new JPanel ();
		P1.setLayout(null);
        JPanel Herramientas = new JPanel();
        JPanel Herramientas2 = new JPanel();
        
        //ACCIONES RAPIDAS
        Border borde = BorderFactory.createTitledBorder("Contorno o Stroke");
        Herramientas.setBorder(borde);
        Herramientas.setBounds(0, 0, 170, 70);
        Herramientas.setLayout(new GridLayout(2,1));
        
        //LINEAS O STROKE
        rbstroke1 = new JRadioButton("Sin contorno", false);
        rbstroke2 = new JRadioButton("Con contorno", true);
        bg1 = new ButtonGroup();
        bg1.add(rbstroke1);
        bg1.add(rbstroke2);
        Herramientas.add(rbstroke1);
        Herramientas.add(rbstroke2);
        
        //EVENTOS DE JAVA2D
		Strok=null;
		contorno=false;
		rimg=null;
		txtPaint=false;
		newColor=null;
		colorFondo=false;
        
        //RELLENO
        stroke=true;
        relleno=true;
        rpen=true;
        rtri=true;
        rdeca=true;
        bandera=false;
        borde = BorderFactory.createTitledBorder("Relleno en figura");
        Herramientas2.setBorder(borde);
        Herramientas2.setBounds(175, 0, 425, 70);
        Herramientas2.setLayout(new GridLayout(2,3));
        
        rbre1 = new JRadioButton("Relleno completo", true);
        rbre2 = new JRadioButton("Sin relleno", false);
        rbre3 = new JRadioButton("Solo en triangulos", false);
        rbre4 = new JRadioButton("Solo en decagonos", false);
        rbre5 = new JRadioButton("Solo en pentagonos", false);
        bg2 = new ButtonGroup();
        bg2.add(rbre1);bg2.add(rbre2);
        bg2.add(rbre3);bg2.add(rbre4);bg2.add(rbre5);
        Herramientas2.add(rbre1);
        Herramientas2.add(rbre2);
        Herramientas2.add(rbre3);
        Herramientas2.add(rbre4);
        Herramientas2.add(rbre5);
        
        //JAVA2D
  
        borde = BorderFactory.createTitledBorder("Opciones JAVA2D");
        JPanel HJava2 = new JPanel();
        HJava2.setLayout(new GridLayout(1,4));
        HJava2.setBorder(borde);
        HJava2.setBounds(630, 0, 290, 70);
        Opciones.addTab("Menu", null, P1,null);
        P1.add(Herramientas);
        P1.add(Herramientas2);
        P1.add(HJava2);
		vent.add(Opciones);
		
		//API2D
	    //ACCIONES CON ACTIONs
        ActJava2D();
        ruta = getClass().getResource("/U3Transform3D/rec/barra.png");
        btnBS = CreaBoton(ruta, 320, 20, 40, 40, "Las lineas las podemos crear con un Stroke", Stroke);
        KeyStroke KS21 = KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.ALT_DOWN_MASK);
        btnBS.getActionMap().put("", Stroke);
        btnBS.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KS21, "");
        
        ruta = getClass().getResource("/U3Transform3D/rec/rgb.png");
        btnC = CreaBoton(ruta, 370, 20, 40, 40, "Establece un color nuevo a la figura", ColorF);
        KeyStroke KS22 = KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK);
        btnC.getActionMap().put("", ColorF);
        btnC.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KS22, "");

        ruta = getClass().getResource("/U3Transform3D/rec/image.png");
        btnF = CreaBoton(ruta, 470, 20, 40, 40, "Rellena el fondo con una imagen predeterminada", Relleno);
        KeyStroke KS24 = KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.ALT_DOWN_MASK);
        btnF.getActionMap().put("", Relleno);
        btnF.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KS24, "");

        ruta = getClass().getResource("/U3Transform3D/rec/resto.png");
        btnR = CreaBoton(ruta, 570, 20, 40, 40, "Reinicia valores", Reset);
        KeyStroke KS26 = KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_DOWN_MASK);
        btnR.getActionMap().put("", Reset);
        btnR.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KS26, "");
        
        HJava2.add(btnBS);HJava2.add(btnC);HJava2.add(btnF);HJava2.add(btnR);
	}
	public void cargaPFig() {
        Lienzo = new JTabbedPane(JTabbedPane.TOP);
        Lienzo.setBounds(0, 101, getWidth(), getHeight() - 100);
        
        PCentr = new JPanel();
        PCentr.setLayout(new BorderLayout());
        
        //FONDO
        ruta = getClass().getResource("/U3Transform3D/rec/cuadricula.png");    //ESTABLECER FONDO
        FondoLienzo = new ImageIcon(ruta).getImage(); 
        
        P2 = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
    		super.paintComponent(g);
    		Graphics2D g2= (Graphics2D)g;
            g2.drawImage(FondoLienzo, 0, 0, getWidth(), getHeight(), this);
    		//SI SOLO ES UNA CARA
    		if (pintaC) {
    			if (contorno)
    				F.CargaStroke(Strok, cantB, colorStrok);
    			if (txtPaint)
    				F.CargaTextura(rimg);
        		F.convert2D();
        		F.dibujarVista(g,vista,tipoFTexture,contorno,txtPaint);
    		}else {
    			//SI SON TODAS CARAS
    			F.convert2D();
    			//LINEA DE STROKE
    			if (contorno)
    				F.CargaStroke(Strok, cantB, colorStrok);
    			if (txtPaint)
    				F.CargaTextura(rimg);
    			if (colorFondo)
    				F.setColorFigura(tipoColor, newColor);
    			F.dibujar(g, txtPaint,tipoFTexture, contorno, stroke,relleno,rdeca,rpen,rtri);
            }
    		}
        };
		P2.setLayout(null);
		P2.setBounds(0, 0, getWidth(), getHeight());
		PVistas = new JPanel() {
			@Override
            public void paintComponent(Graphics g) {
    		super.paintComponent(g);
    		F.VistaSup(g);
    		F.VistaLat(g);
    		F.VistaFront(g);
            }
		};
		TitledBorder tbv=new TitledBorder("Panel vistas");
		PVistas.setBorder(tbv);
		JPanel pcaras = new JPanel(new GridLayout(1,3));
		btnder = new JButton(">");
		btniz = new JButton ("<");
		txt1 = new JTextField (2);
		txt1.setText(""+(vista+1));
		PVistas.add(btniz);
		PVistas.add(txt1);
		PVistas.add(btnder);
		PCentr.add(PVistas,BorderLayout.WEST);
		
        JPanel ps1= new JPanel(new GridLayout (1,1));
		TitledBorder tb1=new TitledBorder("Rot en el Eje X");
		JPanel Sur = new JPanel(new GridLayout(1,3));
		tb1.setTitleJustification(TitledBorder.CENTER);
		S1 = new JSlider(JSlider.HORIZONTAL,0,360,0);
		S1.setMinorTickSpacing(20);
		S1.setMajorTickSpacing(60);
		S1.setPaintTicks(true);
		S1.setPaintLabels(true);
		ps1.add(S1);
		ps1.setBorder(tb1);
		//S2
		JPanel ps2= new JPanel(new GridLayout (1,1));
		TitledBorder tb2=new TitledBorder("Rot en el Eje Y");
		tb2.setTitleJustification(TitledBorder.CENTER);
		S2 = new JSlider(JSlider.HORIZONTAL,0,360,0);
		S2.setMinorTickSpacing(20);
		S2.setMajorTickSpacing(60);
		S2.setPaintTicks(true);
		S2.setPaintLabels(true);
		ps2.add(S2);
		ps2.setBorder(tb2);
		//S3
		JPanel ps3= new JPanel(new GridLayout (1,1));
		TitledBorder tb3=new TitledBorder("Rot en el Eje Z");
		tb3.setTitleJustification(TitledBorder.CENTER);
		S3 = new JSlider(JSlider.HORIZONTAL,0,360,0);
		S3.setMinorTickSpacing(20);
		S3.setMajorTickSpacing(60);
		S3.setPaintTicks(true);
		S3.setPaintLabels(true);
		ps3.add(S3);
		ps3.setBorder(tb3);
		Sur.add(ps1);
		Sur.add(ps2);
		Sur.add(ps3);
		PCentr.add(Sur,BorderLayout.SOUTH);
		
		JPanel ps4= new JPanel(new GridLayout (1,1));
		TitledBorder tb4=new TitledBorder("Zoom");
		tb4.setTitleJustification(TitledBorder.CENTER);
		S4 = new JSlider(JSlider.VERTICAL,100,2300,1000);
		S4.setMinorTickSpacing(50);
		S4.setMajorTickSpacing(100);
		S4.setPaintLabels(true);
		S4.setPaintTicks(true);
		ps4.add(S4);
		ps4.setBorder(tb4);
		PCentr.add(ps4,BorderLayout.EAST);
		PCentr.add(P2,BorderLayout.CENTER);
        Lienzo.addTab("Cuadro", null, PCentr, null);
		vent.add(Lienzo);
		
		//ACCIONES
		P2.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				int sentRot = e.getWheelRotation();
				if(sentRot>0)
					F.Escalar3D(1.05);
				else if (sentRot<0)
					F.Escalar3D(.95);
				P2.repaint();
			}
		});
		P2.addMouseListener(new MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				int cx=e.getX(), cy=e.getY();
				arrastra = F.dentro(cx, cy);
			}
		});
		P2.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(java.awt.event.MouseEvent e) {
				//SI SE PUEDE ARRASTRAR
				if (arrastra) {
					int cx=e.getX(), cy=e.getY();
					F.Mover(cx, cy);
					P2.repaint();
				}
			}
		});
		S1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				//GENERAMOS ROTACION EJE X, ahora encadenada
				F.RotacionXYZH(S1.getValue(),S2.getValue(),S3.getValue());
				P2.repaint();
			}
		});
		S2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				//GENERAMOS ROTACION EJE Y
				F.RotacionXYZH(S1.getValue(),S2.getValue(),S3.getValue());
				P2.repaint();
			}
		});
		S3.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				//GENERAMOS ROTACION EJE Z ahora encadenada
				F.RotacionXYZH(S1.getValue(),S2.getValue(),S3.getValue());
				P2.repaint();
			}
		});
		S4.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int dis=S4.getValue();
				F.actDistancia(dis);
				P2.repaint();
			}
		});
		//ACCIONES DE LAS CARAS
		btniz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				vista--;
				dibujaVista(vista);
				P2.repaint();
				
			}
		});
		btnder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vista++;
				dibujaVista(vista);
				P2.repaint();
			}
		});
		//LINEA ACTION
		rbstroke1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stroke=false;
				P2.repaint();
			}
		});
		rbstroke2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stroke=true;
				P2.repaint();
			}
		});
		rbre1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relleno=true;
				rtri=true;
				rpen=true;
				rdeca=true;
				P2.repaint();
			}
		});
		rbre2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relleno=false;
				P2.repaint();
			}
		});
		rbre5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rtri=false;
				rpen=true;
				rdeca=false;
				relleno=true;
				P2.repaint();
			}
		});
		rbre4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rtri=false;
				rpen=false;
				rdeca=true;
				relleno=true;
				P2.repaint();
			}
		});
		rbre3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rtri=true;
				rpen=false;
				rdeca=false;
				relleno=true;
				P2.repaint();
			}
		});
	}
	//METODO PARA CREAR BOTONES
	public JButton CreaBoton(URL ruta, int x, int y, int w, int h, String TT, Action a) {
        JButton b = new JButton(a);
        b.setIcon(new ImageIcon(ruta));
        b.setBounds(x, y, w, h);
        b.setToolTipText(TT);
        return b;
    }
	public void dibujaVista(int v) {
		if (v>=0 && v<=44) {
			if (v<=43) {
				vista=v;
				pintaC=true;
			}else {
				v=vista=-1;
				pintaC=false;
			}
		}
		else if (v<-1) {
			v=vista=43;
			pintaC=true;
		}else if (v==-1) {
			v=vista=-1;
			pintaC=false;
		}
		txt1.setText(""+(v+1));
		txt1.setEnabled(false);
	}
	//ACIONES DE LOS BOTONES
	public void ActJava2D() {
		//REINICIA todo
		Reset = new AbstractAction("") {
			public void actionPerformed(ActionEvent e) {
				//RESTAURAR SI HAY CAMBIO SE RESTAURA
				boolean bande=new CDiaglogRestore(Interfaz.this,true).Mostrar(); 
				//System.out.println("_>"+bande);
				if(bande) {
				F.ResetValues();
				Strok=null;
				contorno=false;
				rimg=null;
				txtPaint=false;
				newColor=null;
				colorFondo=false;
				tipoFTexture=1;
				tipoColor=1;
				//PARTE HERRAMIENTAS
				stroke=true;
		        relleno=true;
		        rpen=true;
		        rtri=true;
		        rdeca=true;
		        S1.setValue(0);
		        S2.setValue(0);
		        S3.setValue(0);
		        S4.setValue(1000);
		        vista=-1;
		        pintaC=false;
		        txt1.setText(""+(vista+1));
				colorStrok=Color.DARK_GRAY;
				P2.repaint();
				bandera=false;
				cantB=0;
				JOptionPane.showMessageDialog(null,"Valores restaurados");
				}
			}
		};
		//contorno o stroke
		Stroke = new AbstractAction("") {
			public void actionPerformed(ActionEvent e) {
				BasicStroke sAnt=Strok;
				CDialogStroke c;
				Color colorAnt =colorStrok;
				int cantAnt = cantB;
				try {
					//activa
					c = new CDialogStroke(Interfaz.this, true);
					c.Despliega();
					Strok = c.ReturnS();
					colorStrok = c.returnC();
					if(c.isnone()) {
						contorno=false;
					}
				} catch (Exception e2) {
					//Marca error y pasamos el anterior
					Strok=sAnt;
					colorStrok=Color.DARK_GRAY;
				}
				if (Strok==null) {
					Strok=sAnt;
					colorStrok=colorAnt;
					cantB=cantAnt;
				}else {
					contorno=true;
					cantB=(int)Strok.getLineWidth();
					}
				P2.repaint();
			}
	}; 
	ColorF = new AbstractAction("") {
		public void actionPerformed(ActionEvent e) {
			Color colorAnt = newColor;
			int tipoAnt=tipoColor;
			CDialogColors cuadroColor= new CDialogColors(Interfaz.this, true);
			try {
				cuadroColor.Despliega();
				newColor=cuadroColor.getColorF();
				tipoColor=cuadroColor.getTipoF();
			}catch (Exception e3) {
				if (!txtPaint) {
					newColor=colorAnt;
					tipoColor=tipoAnt;
					}
			}
			if (newColor==null) {
				if (!txtPaint) {
					newColor=colorAnt;
					tipoColor=tipoAnt;
					}
			}
			else {//SETEAMOS BANDERAS
				newColor = cuadroColor.getColorF();
				tipoColor = cuadroColor.getTipoF();
				colorFondo=true;
				if (txtPaint)
					txtPaint=false;
			}
			P2.repaint();
		}
	};
	Relleno = new AbstractAction("") {
		public void actionPerformed(ActionEvent e) {
			TexturePaint rimgAnt=rimg;
			CDialogRellenoImg c= new CDialogRellenoImg(Interfaz.this, true);
			c.Despliega();
			rimg=c.getTextura();
			tipoFTexture=c.getTipoF();
			if (!(rimg ==null)) {
				txtPaint= true;
				//SETEAMOS BANDERAS
				if (colorFondo) {
					colorFondo=false;
				}
				P2.repaint();
			}else {
				rimg=rimgAnt;
				if (!colorFondo||!txtPaint)
					rimg=rimgAnt;
			}
		}
	};
	}
	
	/*
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		F.convert2D();
		F.dibujar(g);
		F.VistaSup(g);
		F.VistaLat(g);
		F.VistaFront(g);
	}*/
	public static void main(String[] args) {
		//VECTOR UNIDIMENSIONAL
		double C0  = ((1 + Math.sqrt(5)) / 4)*15;
		double C1  = ((3 + Math.sqrt(5)) / 4)*15;
		double C2  = ((1 + Math.sqrt(5)) / 2)*15;
		double C3  = ((5 + Math.sqrt(5)) / 4)*15;
		double C4  = ((2 + Math.sqrt(5)) / 2)*15;
		
		double cfig2[][] = {
						{ 0.5*15,  0.5*15,   C4},
						{ 0.5*15,  0.5*15,  -C4},
						{ 0.5*15, -0.5*15,   C4},
						{ 0.5*15, -0.5*15,  -C4},
						{-0.5*15,  0.5*15,   C4},
						{-0.5*15,  0.5*15,  -C4},
						{-0.5*15, -0.5*15,   C4},
						{-0.5*15, -0.5*15,  -C4},
						{  C4,  0.5*15,  0.5*15},
						{  C4,  0.5*15, -0.5*15},
						{  C4, -0.5*15,  0.5*15},
						{  C4, -0.5*15, -0.5*15},
						{ -C4,  0.5*15,  0.5*15},
						{ -C4,  0.5*15, -0.5*15},
						{ -C4, -0.5*15,  0.5*15},
						{ -C4, -0.5*15, -0.5*15},
						{ 0.5*15,   C4,  0.5*15},
						{ 0.5*15,   C4, -0.5*15},
						{ 0.5*15,  -C4,  0.5*15},
						{ 0.5*15,  -C4, -0.5*15},
						{-0.5*15,   C4,  0.5*15},
						{-0.5*15,   C4, -0.5*15},
						{-0.5*15,  -C4,  0.5*15},
						{-0.5*15,  -C4, -0.5*15},
						{ 0.0*15,   C1,   C3},
						{ 0.0*15,   C1,  -C3},
						{ 0.0*15,  -C1,   C3},
						{ 0.0*15,  -C1,  -C3},
						{  C3,  0.0*15,   C1},
						{  C3,  0.0*15,  -C1},
						{ -C3,  0.0*15,   C1},
						{ -C3,  0.0*15,  -C1},
						{  C1,   C3,  0.0*15},
						{  C1,  -C3,  0.0*15},
						{ -C1,   C3,  0.0*15},
						{ -C1,  -C3,  0.0*15},
						{  C1,   C0,   C2},
						{  C1,   C0,  -C2},
						{  C1,  -C0,   C2},
						{  C1,  -C0,  -C2},
						{ -C1,   C0,   C2},
						{ -C1,   C0,  -C2},
						{ -C1,  -C0,   C2},
						{ -C1,  -C0,  -C2},
						{  C2,   C1,   C0},
						{  C2,   C1,  -C0},
						{  C2,  -C1,   C0},
						{  C2,  -C1,  -C0},
						{ -C2,   C1,   C0},
						{ -C2,   C1,  -C0},
						{ -C2,  -C1,   C0},
						{ -C2,  -C1,  -C0},
						{  C0,   C2,   C1},
						{  C0,   C2,  -C1},
						{  C0,  -C2,   C1},
						{  C0,  -C2,  -C1},
						{ -C0,   C2,   C1},
						{ -C0,   C2,  -C1},
						{ -C0,  -C2,   C1},
						{ -C0,  -C2,  -C1},
		};
		//Figura de 55 secuencias, seccioanadas en 3
		int secf2[][]={
				{  0, 24, 56, 48, 12, 14, 50, 58, 26,  2 },
				{  0, 36, 44, 32, 17, 21, 34, 48, 40,  4 },
				{  7,  3, 39, 47, 33, 18, 22, 35, 51, 43 },
				{  7,  5, 25, 53, 45,  9, 11, 47, 55, 27 },
				{ 10,  8, 44, 52, 24,  4,  6, 26, 54, 46 },
				{ 10, 11, 29, 37, 53, 17, 16, 52, 36, 28 },
				{ 13, 31, 43, 59, 23, 22, 58, 42, 30, 12 },
				{ 13, 49, 57, 25,  1,  3, 27, 59, 51, 15 },
				{ 19, 33, 46, 38,  2,  6, 42, 50, 35, 23 },
				{ 19, 55, 39, 29,  9,  8, 28, 38, 54, 18 },
				{ 20, 16, 32, 45, 37,  1,  5, 41, 49, 34 },
				{ 20, 21, 57, 41, 31, 15, 14, 30, 40, 56 },
				{ 24, 52, 16, 20, 56 },
				{ 25, 57, 21, 17, 53 },
				{ 26, 58, 22, 18, 54 },
				{ 27, 55, 19, 23, 59 },
				{ 28, 36,  0,  2, 38 },
				{ 29, 39,  3,  1, 37 },
				{ 30, 42,  6,  4, 40 },
				{ 31, 41,  5,  7, 43 },
				{ 32, 44,  8,  9, 45 },
				{ 33, 47, 11, 10, 46 },
				{ 34, 49, 13, 12, 48 },
				{ 35, 50, 14, 15, 51 },
				{ 24,  0,  4 },
				{ 25,  5,  1 },
				{ 26,  6,  2 },
				{ 27,  3,  7 },
				{ 28,  8, 10 },
				{ 29, 11,  9 },
				{ 30, 14, 12 },
				{ 31, 13, 15 },
				{ 32, 16, 17 },
				{ 33, 19, 18 },
				{ 34, 21, 20 },
				{ 35, 22, 23 },
				{ 36, 52, 44 },
				{ 37, 45, 53 },
				{ 38, 46, 54 },
				{ 39, 55, 47 },
				{ 40, 48, 56 },
				{ 41, 57, 49 },
				{ 42, 58, 50 },
				{ 43, 51, 59 }
		};
		int secf3 []= {		
		 0, 24, 56, 48, 12, 14, 50, 58, 26, 0,
		 2,  0, 36, 44, 32, 17, 21, 34, 48, 40, 4,2,
		 7,  3, 39, 47, 33, 18, 22, 35, 51, 43,7,
		 7,  5, 25, 53, 45,  9, 11, 47, 55, 27 ,7,
		 10,  8, 44, 52, 24,  4,  6, 26, 54, 46 ,10,
		 10, 11, 29, 37, 53, 17, 16, 52, 36, 28 ,10,
		 13, 31, 43, 59, 23, 22, 58, 42, 30, 12 ,13,
		 13, 49, 57, 25,  1,  3, 27, 59, 51, 15 ,13,
		 19, 33, 46, 38,  2,  6, 42, 50, 35, 23 ,19,
		 19, 55, 39, 29,  9,  8, 28, 38, 54, 18 ,19,
		 20, 16, 32, 45, 37,  1,  5, 41, 49, 34 ,20,
		 20, 21, 57, 41, 31, 15, 14, 30, 40, 56 ,20,
		 24, 52, 16, 20, 56 ,24,
		 25, 57, 21, 17, 53 ,25,
		 26, 58, 22, 18, 54 ,26,
		 27, 55, 19, 23, 59 ,27,
		 28, 36,  0,  2, 38 ,28,
		 29, 39,  3,  1, 37 ,29,
		 30, 42,  6,  4, 40 ,30,
		 31, 41,  5,  7, 43 ,31,
		 32, 44,  8,  9, 45 ,32,
		 33, 47, 11, 10, 46 ,33,
		 34, 49, 13, 12, 48 ,34,
		 35, 50, 14, 15, 51 ,35,
		 24,  0,  4 ,24,
		 25,  5,  1 ,25,
		 26,  6,  2 ,26,
		 27,  3,  7 ,27,
		 28,  8, 10 ,28,
		 29, 11,  9 ,29,
		 30, 14, 12 ,30,
		 31, 13, 15 ,31,
		 32, 16, 17 ,32,
		 33, 19, 18 ,33,
		 34, 21, 20 ,34,
		 35, 22, 23 ,35,
		 36, 52, 44 ,36,
		 37, 45, 53 ,37,
		 38, 46, 54 ,38,
		 39, 55, 47 ,39,
		 40, 48, 56 ,40,
		 41, 57, 49 ,41,
		 42, 58, 50 ,42,
		 43, 51, 59 ,43};
		new Interfaz(cfig2,secf2,secf3);
	}
	@Override
	public void actionPerformed(ActionEvent e) {

	}
	public void stateChanged(ChangeEvent e) {
			//HOMOGENEAS
		
	}
}
