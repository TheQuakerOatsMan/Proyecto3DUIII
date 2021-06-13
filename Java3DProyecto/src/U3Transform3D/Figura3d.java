package U3Transform3D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.geom.GeneralPath;

public class Figura3d {
	
	//MATRICES EN 3D Y EN 2D
	double Fig3D[][];
	double Fig2D[][];
	double Fig3Do[][];
	//LAS MERAS MERAS ORIGINALES
	double Fig3Doo[][];
	int sec[];
	int secPuntos[][];
	//OBSERVER
	int mz=-350;
	int distancia=1000;
	//DESPLAZAR
	int despx=0,despy=0;
	//OPCIONES DE JAVA 2D
	BasicStroke Lineas = null;
	int cantS=0;
	Color colorStroke=null,colorD1=null,colorD2=null,colorD3=null;
	TexturePaint rimg;
	
	//CONSTRUCTOR DE FIGURA Y SECUENCIA
	public Figura3d(double f[][], int s[][], int s2[]) 
	{
		Fig3D = new double [f.length][3];
		Fig3Do = new double [f.length][3];
		Fig3Doo = new double [f.length][3];
		Fig2D = new double [f.length][2];
		sec = new int[s2.length];
		secPuntos = s;
		for (int i = 0; i<Fig3D.length; i++) {
			Fig3Doo[i][0]=Fig3Do[i][0]=Fig3D[i][0] = f[i][0];
			Fig3Doo[i][1]=Fig3Do[i][1]=Fig3D[i][1] = f[i][1];
			Fig3Doo[i][2]=Fig3Do[i][2]=Fig3D[i][2] = f[i][2];
		}
		for (int i = 0; i < secPuntos.length; i++) {
			for (int j = 0; j < secPuntos[i].length; j++) {
				secPuntos[i][j] = s[i][j];
			}
		}
		for (int i = 0; i < s2.length; i++) {
			sec[i] = s2[i];
		}
		//desplazamiento
		despx=400;despy=300;
		//Colores
		colorD1 =new Color(0.33f,.023f,.02f,1f);
    	colorD2=new Color(1f,0f,0f,1f);
		colorD3=new Color(0f,0f,1f,1f);
		colorStroke = Color.DARK_GRAY;

		
	}
	//PARA REINICIAR VALORES
	public void ResetValues() {
		for (int i = 0; i<Fig3D.length; i++) {
			Fig3Do[i][0]=Fig3D[i][0] = Fig3Doo[i][0];
			Fig3Do[i][1]=Fig3D[i][1] = Fig3Doo[i][1];
			Fig3Do[i][2]=Fig3D[i][2] = Fig3Doo[i][2];
		}
		//desplazamiento
		despx=400;despy=300;
		//Colores
		colorD1 =new Color(0.33f,.023f,.02f,1f);
    	colorD2=new Color(1f,0f,0f,1f);
		colorD3=new Color(0f,0f,1f,1f);
		colorStroke = Color.DARK_GRAY;
	}
	public void convert2D(){
		//cx = (d*x)/(z+mz)
		//cy = (d*y)((z+mz)
		for (int i = 0; i < Fig3D.length; i++) {
			Fig2D [i][0] = ((distancia*Fig3D[i][0])/(Fig3D[i][2]+mz));
			Fig2D [i][1] = ((distancia*Fig3D[i][1])/(Fig3D[i][2]+mz));
			Fig2D [i][0] +=despx; 
			Fig2D [i][1] +=despy;
		}
	}
	//HACEMOS LA MOVICION
	public void Mover (int cx,int cy) {
		despx=cx;
		despy=cy;
	}
	//REFLEXIONES
	public void RefX(){
        for (int i = 0; i < Fig3D.length; i++) 
        	Fig3D[i][0] = Fig3D[i][0] * -1;
    }

    public void RefY(){
        for (int i = 0; i < Fig3D.length; i++) 
        	Fig3D[i][1] = Fig3D[i][1] * -1;
    }

    public void RefZ(){
        for (int i = 0; i < Fig3D.length; i++) 
        	Fig3D[i][2] = Fig3D[i][2] * -1;
    }
	
	//TRANSFROMACIONES
	public void rotX(int grados) {
		//[x,y*cos&-z*sen&,y*sen&+z*cos&]
		double gra = Math.toRadians(grados);
		double coseno= Math.cos(gra);
		double seno= Math.sin(gra);
		for (int i = 0; i < Fig3Do.length; i++) {
			//VARIABLES DE RESPALDO
			double x = Fig3Do[i][0];
			double y = Fig3Do[i][1];
			double z = Fig3Do[i][2];
			Fig3Do[i][0]=x;
			Fig3Do[i][1]=y*coseno-z*seno;
			Fig3Do[i][2]=y*seno+z*coseno;
		}
	}
		public void rotZ(int grados) {
			//[x,y*cos&-z*sen&,y*sen&+z*cos&]
			double gra = Math.toRadians(grados);
			double coseno= Math.cos(gra);
			double seno= Math.sin(gra);
			for (int i = 0; i < Fig3D.length; i++) {
				//VARIABLES DE RESPALDO
				double x = Fig3Do[i][0];
				double y = Fig3Do[i][1];
				double z = Fig3Do[i][2];
				Fig3D[i][0]=x*coseno-y*seno;
				Fig3D[i][1]=x*seno+y*coseno;;
				Fig3D[i][2]=z;
			}
		}
		//ROTACION CON LAS TRES FORMAS
		public void rotXYZ(int grx,int gry,int grz) {
			double vr[][] = rotXt(grx);
			vr = rotYt(gry, vr);
			rotZt(grz, vr);
		}
		//con las COORD HOMOGENEAS{
		public void RotacionXYZH (int grx,int gry,int grz) {
			double gr1= Math.toRadians(grx);
			double gr2= Math.toRadians(gry);
			double gr3= Math.toRadians(grz);
			double sa1 = Math.sin(gr1);
			double ca1 = Math.cos(gr1);
			double sa2 = Math.sin(gr2);
			double ca2 = Math.cos(gr2);
			double sa3 = Math.sin(gr3);
			double ca3 = Math.cos(gr3);
			for (int i = 0; i < Fig3D.length; i++) {
				//V DE RESPALDO
				double x = Fig3Do[i][0];
				double y = Fig3Do[i][1];
				double z = Fig3Do[i][2];
				Fig3D[i][0]=(x*(ca2*ca3)+y*((sa1*-sa2)*ca3+(ca1*-sa3))+z*((ca1*-sa2)*(ca3)+(-sa1*-sa3)));
				Fig3D[i][1]=(x*(ca2*sa3)+y*((sa1*-sa2)*sa3+(ca1*ca3))+z*((ca1*-sa2)*(sa3)+(-sa1*ca3)));
				Fig3D[i][2]=((x*sa2)+y*(sa1*ca2)+z*(ca1*ca2));
			}
		}
		//ROTACION RESULTANTE DE X y Y
		public double[][] rotXt(int grados) {
			//[x,y*cos&-z*sen&,y*sen&+z*cos&]
			double gra = Math.toRadians(grados);
			double coseno= Math.cos(gra);
			double seno= Math.sin(gra);
			double vt[][]= new double [Fig3Doo.length][3];
			
			for (int i = 0; i < Fig3Doo.length; i++) {
				//VARIABLES DE RESPALDO
				double x = Fig3Doo[i][0];
				double y = Fig3Doo[i][1];
				double z = Fig3Doo[i][2];
				vt[i][0]=x;
				vt[i][1]=y*coseno-z*seno;
				vt[i][2]=y*seno+z*coseno;
			}
			return vt;
		}

		public double[][] rotY(int grados) {
			//[x,y*cos&-z*sen&,y*sen&+z*cos&]
			double gra = Math.toRadians(grados);
			double coseno= Math.cos(gra);
			double seno= Math.sin(gra);
			//DECLARAMOS EL vTEMPORAL
			double vt[][]= new double[Fig3Doo.length][3];
			for (int i = 0; i < vt.length; i++) {
				//VARIABLES DE RESPALDO
				double x = Fig3Doo[i][0];
				double y = Fig3Doo[i][1];
				double z = Fig3Doo[i][2];
				vt[i][0]=x*coseno-z*seno;
				vt[i][1]=y;
				vt[i][2]=x*seno+z*coseno;
			}
			return vt;
		}
		//ESCALAMIENTO3D 1a alternativa
		public void actDistancia(int dis) {
			distancia=dis;
		}
		//2da ALTERNATIVA, con el factor de escalamiento
		public void Escalar3D(double esc) {
			for (int i = 0; i < Fig3D.length; i++) {
				//SE HACE CON LOS VALORES YA ACTUALIZADOS
				//Fig3D[i][0]=Fig3Do[i][0]=Fig3Do[i][0]*esc;
				//Fig3D[i][1]=Fig3Do[i][1]=Fig3Do[i][1]*esc;
				//Fig3D[i][2]=Fig3Do[i][2]=Fig3Do[i][2]*esc;
				Fig3Do[i][0]= Fig3D[i][0]*=esc;
				Fig3Do[i][1] = Fig3D[i][1]*=esc;
				Fig3Do[i][2] = Fig3D[i][2]*=esc;
			}
		}
		public double[][] rotYt(int grados, double v[][]) {
			//[x,y*cos&-z*sen&,y*sen&+z*cos&]
			double gra = Math.toRadians(grados);
			double coseno= Math.cos(gra);
			double seno= Math.sin(gra);
			double vt[][]= new double [Fig3Do.length][3];
			for (int i = 0; i < Fig3D.length; i++) {
				//VARIABLES DE RESPALDO
				double x = v[i][0];
				double y = v[i][1];
				double z = v[i][2];
				vt[i][0]=x*coseno-z*seno;
				vt[i][1]=y;
				vt[i][2]=x*seno+z*coseno;
			}
			return vt;
		}
		public void rotZt(int grados, double v[][]) {
			//[x,y*cos&-z*sen&,y*sen&+z*cos&]
			double gra = Math.toRadians(grados);
			double coseno= Math.cos(gra);
			double seno= Math.sin(gra);
			double vt[][]= new double [Fig3Do.length][3];
			for (int i = 0; i < Fig3D.length; i++) {
				//VARIABLES DE RESPALDO
				double x = v[i][0];
				double y = v[i][1];
				double z = v[i][2];
				Fig3D[i][0]=x*coseno-y*seno;
				Fig3D[i][1]=x*seno+y*coseno;;
				Fig3D[i][2]=z;
			}
		}
		//VISTA SUPERIOR
		public void VistaSup (Graphics g)
		{
			double figt[][]=rotXt(90);
			//ELIMINAMOS LA PROFUNDIDAD
			for (int i = 0; i < figt.length; i++) {
				figt[i][2]=0;
				figt[i][0]+=50;
				figt[i][1]+=130;
			}
			dVistaSup(g, figt, "Vista Superior",20,90);
		}
		//VISTA LATERAL
		public void VistaLat (Graphics g)
				{
				double figt[][]=rotY(90);
					//ELIMINAMOS LA PROFUNDIDAD
					for (int i = 0; i < figt.length; i++) {
						figt[i][2]=0;
						//INVERTIMOS
						figt[i][1]*=-1;
						figt[i][0]+=50;
						figt[i][1]+=230;
					}
					dVistaSup(g, figt, "Vista Leteral",20,190);
		}
		//VISTA FRONT
		public void VistaFront (Graphics g)
		{
			double figt[][]=new double[Fig3Doo.length][2];
			//ELIMINAMOS LA PROFUNDIDAD
			for (int i = 0; i < figt.length; i++) {
				figt[i][0]=Fig3Doo[i][0];
				figt[i][1]=-Fig3Doo[i][1];
				figt[i][0]+=50;
				figt[i][1]+=330;
			}							
			dVistaSup(g, figt, "Vista Frontal",20,290);
		}
		//DENTRO
		public boolean dentro (int cx,int cy) {
			boolean bandentro=false;
			double minx=Fig2D[0][0],miny=Fig2D[0][1],maxx=0,maxy=0;
			//MENORES
			for (int i = 0; i < Fig2D.length; i++) {
				if (Fig2D[i][0]<minx)
					minx=Fig2D[i][0];
				if (Fig2D[i][1]<miny)
					miny=Fig2D[i][1];
			}
			//MAYORES
			for (int i = 0; i < Fig2D.length; i++) {
				if (Fig2D[i][0]>maxx)
					maxx=Fig2D[i][0];
				if (Fig2D[i][1]>maxy)
					maxy=Fig2D[i][1];
			}
			//VEMOS EN DONDE ESTA EL PUNTO
			if((cx>=minx && cx <maxx)&&(cy>=miny && cy <maxy)){
				bandentro=true;
			}
			return bandentro;
		}
		//DIBUJAMOS LA VITA SUPERIOR
	public void dVistaSup(Graphics g, double ft[][],String mens, int cx, int cy) {
		g.setColor(Color.DARK_GRAY);
			for (int i=0; i<=sec.length-1; i+=2) {
				g.drawLine((int)ft[sec[i]][0], (int)ft[sec[i]][1], 
						(int)ft[sec[i+1]][0], (int)ft[sec[i+1]][1]);
			}
		g.drawString(mens, cx, cy);
		}
	public boolean estaEnRango (boolean ban, int tp, int i) {
		if (ban && (tp==1 || tp==2) && (i>=0 && i<12))
			return true;
		else if (ban && (tp==1 || tp==3) && (i>=12 && i<24))
			return true;
		else if (ban && (tp==1 || tp==4) && (i>=24 && i<44))
			return true;
		return false;
	}
	public void dibujarVista(Graphics g, int v, int tpTXT,boolean Stroke, boolean Texture) {
		Graphics2D g2 = (Graphics2D)g;
		Shape S;
		GeneralPath vista = new GeneralPath();
		vista.moveTo(Fig2D[secPuntos[v][0]][0], Fig2D[secPuntos[v][0]][1]);
		for (int i=v; i < v+1; i++) {
			vista.moveTo(Fig2D[secPuntos[v][0]][0], Fig2D[secPuntos[v][0]][1]);
            for (int j = 0; j < secPuntos[v].length; j++) 
            	vista.lineTo(Fig2D[secPuntos[v][j]][0], Fig2D[secPuntos[v][j]][1]);
            vista.closePath();
            	S = vista;
             	if (Stroke) {
            		g2.setColor(colorStroke);
            		g2.setStroke(new BasicStroke(cantS,Lineas.getEndCap(),Lineas.getLineJoin(),Lineas.getMiterLimit(),Lineas.getDashArray(),Lineas.getDashPhase()));
            		g2.draw(S);
             	}
            	if (Texture) {
            		if (estaEnRango(Texture, tpTXT, v))
                		g2.setPaint(rimg);
            		else {
            			if (i>=0 && i<12) {
                        	g2.setColor(colorD1);
                    	}
                    	else if (i>=12 && i<24) {
                        	g2.setColor(colorD2);
                    	}else if (i>=24 && i<44) {
                        	g2.setColor(colorD3);
                    	}
            		}
            	}
            	else {
            	if (i>=0 && i<12) {
                	g2.setColor(colorD1);
            	}
            	else if (i>=12 && i<24) {
                	g2.setColor(colorD2);
            	}else if (i>=24 && i<44) {
                	g2.setColor(colorD3);
            	}
            	}
            	g2.fill(S);
		}
	}
	//CARGAMOS LOS EVENTOS 2D A EJECUTAR
	public void CargaStroke (BasicStroke bs, int cantB, Color color) {
		Lineas = bs;
		cantS=cantB;
		colorStroke=color;
	}
	public void CargaTextura(TexturePaint texture) {
		rimg=texture;
	}
	public void setColorFigura(int tipo, Color colorn) {
		if (tipo==1) {
			colorD1=colorn;
			colorD2=colorn;
			colorD3=colorn;
		}if (tipo==2) {
			colorD1=colorn;
		}if (tipo==3) {
			colorD2=colorn;
		}if (tipo==4) {
			colorD3=colorn;
		}
	}
	public void dibujar(Graphics g, boolean texture, int tpTXT, boolean stroke, boolean s, boolean r, boolean r1, boolean r2, boolean r3) {
		Graphics2D g2 = (Graphics2D)g;
		Shape S;
		GeneralPath p = new GeneralPath();
		//PUNTOS INICIALES DEL GENERAL
		if (r) {
		if (r1) {
		for (int i=0; i < 11; i++) {
	        GeneralPath figura = new GeneralPath();
			figura.moveTo(Fig2D[secPuntos[i][0]][0], Fig2D[secPuntos[i][0]][1]);
            for (int j = 0; j < secPuntos[i].length; j++) 
            	figura.lineTo(Fig2D[secPuntos[i][j]][0], Fig2D[secPuntos[i][j]][1]);
            figura.closePath();
            	S = figura;
               	//SI HAY CONTORNO
            	if (stroke) {
            		g2.setColor(colorStroke);
            		g2.setStroke(new BasicStroke(cantS,Lineas.getEndCap(),Lineas.getLineJoin(),Lineas.getMiterLimit(),Lineas.getDashArray(),Lineas.getDashPhase()));
            		g2.draw(S);
            	}if(s){
            		g2.setColor(colorStroke);
            		g2.draw(S);
            	}
            	if (texture && (tpTXT==1 || tpTXT==2)) {
            		g2.setPaint(rimg);
            	}else {
                    g2.setColor(colorD1);
            	}
            	g2.fill(S);
		}
		}
		if (r2) {
		for (int i=12; i<24; i++) {
			GeneralPath figura = new GeneralPath();
			figura.moveTo(Fig2D[secPuntos[i][0]][0], Fig2D[secPuntos[i][0]][1]);
            for (int j = 0; j < secPuntos[i].length; j++) 
            	figura.lineTo(Fig2D[secPuntos[i][j]][0], Fig2D[secPuntos[i][j]][1]);
            	figura.closePath();
            	S = figura;
            	//SI HAY CONTORNO
            	if (stroke) {
            		g2.setColor(colorStroke);
            		g2.setStroke(new BasicStroke(cantS,Lineas.getEndCap(),Lineas.getLineJoin(),Lineas.getMiterLimit(),Lineas.getDashArray(),Lineas.getDashPhase()));
            		g2.draw(S);
            	}if(s)  {
            		g2.setColor(colorStroke);
            		g2.draw(S);
            	}
            	if (texture && (tpTXT==1 || tpTXT==3)) {
            		g2.setPaint(rimg);
            	}else {
                	g2.setColor(colorD2);
            	}
            	g2.fill(S);
		}
		}
		if (r3) {
		for (int i=24; i<44; i++) {
			GeneralPath figura = new GeneralPath();
			figura.moveTo(Fig2D[secPuntos[i][0]][0], Fig2D[secPuntos[i][0]][1]);
            for (int j = 0; j < secPuntos[i].length; j++) 
            	figura.lineTo(Fig2D[secPuntos[i][j]][0], Fig2D[secPuntos[i][j]][1]);
            	figura.closePath();
            	S = figura;
            	//SI HAY CONTORNO
            	if (stroke) {
            		g2.setColor(colorStroke);
            		g2.setStroke(new BasicStroke(cantS,Lineas.getEndCap(),Lineas.getLineJoin(),Lineas.getMiterLimit(),Lineas.getDashArray(),Lineas.getDashPhase()));
            		g2.draw(S);
            	}if(s) {
            		g2.setColor(colorStroke);
            		g2.draw(S);
            	}
            	if (texture && (tpTXT==1 || tpTXT==4)) {
            		g2.setPaint(rimg);
            	}else {
                	g2.setColor(colorD3);
            	}
            	g2.fill(S);
                
		}
		}
		}
		if (s) {
		g.setColor(colorStroke);
		GeneralPath figura2 = new GeneralPath();
		figura2.moveTo(Fig2D[sec[0]][0],Fig2D[sec[0]][1]); 
		for (int i=0; i<sec.length; i++) {
			figura2.lineTo(Fig2D[sec[i]][0],Fig2D[sec[i]][1]); 
		}
		for (int i=0; i<=sec.length-1; i+=2) {
			g.drawLine((int)Fig2D[sec[i]][0], (int)Fig2D[sec[i]][1], 
					(int)Fig2D[sec[i+1]][0], (int)Fig2D[sec[i+1]][1]);
		}
		figura2.closePath();
    	S = figura2;
		}
	}
}
