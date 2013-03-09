/* 
  Code Reimagined, Class for painting the AST
    Copyright (C) 2010-2013 José Javier Asensio Montiel

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    */
package supermariocode.painter;


import java.net.URL;
import java.util.ArrayList;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import supermariocode.views.MarioCodeView;

public class MarioPainter  {
	
	private static ArrayList tree;
	
	private static int scale = 16;
	
	
	private int base;
	public Image img;
	public Image imgBG;
	public GC g;
	
	public MarioPainter(int base, GC g) {
		
        img = new Image(MarioCodeView.myCanvas.getDisplay(),
    			MarioCodeView.class.getResourceAsStream("smwtileset.gif"));
        imgBG = new Image(MarioCodeView.myCanvas.getDisplay(),
    			MarioCodeView.class.getResourceAsStream("background1.jpg"));
        this.base = base;
        this.g = g;
       // g.drawImage(imgBG, 0, 0);
       
        
	}
	
	public void paintTree(ArrayList l) {
		for (int i = 0; i<l.size(); i+=2) {
			JavaMarioNode node = (JavaMarioNode) l.get(i);
			ArrayList content = (ArrayList) l.get(i+1);
			
			for (SpriteComposite sc: node.compList) {
				for (Sprite s: sc.spriteList) {
					g.drawImage(img, s.x, s.y, scale, scale, s.posx*scale, base - (s.posy+1)*scale, scale, scale);
				}
			}
			
			if (!content.isEmpty()) {
				paintTree(content);
			}
			
		}		
	}
	
	public void paintTreeDebug(ArrayList l) {
		for (int i = 0; i<l.size(); i+=2) {
			JavaMarioNode node = (JavaMarioNode) l.get(i);
			ArrayList content = (ArrayList) l.get(i+1);
			
			for (SpriteComposite sc: node.compList) {
				//Tomamos el spritecomposite para obtener las coordenadas para la generaci�n de rectangulos:
				System.out.println(sc.spriteList);
				System.out.println(sc);
				int xx = sc.posx*scale;
				int yy = base - (sc.posy+sc.leny)*scale;
				int lxx = sc.lenx*scale;
				int lyy = sc.leny*scale;
				g.drawRectangle(xx, yy, lxx, lyy);
				System.out.println(xx/scale +" " + yy/scale +" " + lxx/scale +" " + lyy/scale);
					
			}
			
			if (!content.isEmpty()) {
				paintTreeDebug(content);
			}
		}	
		
	}
	
	


}
