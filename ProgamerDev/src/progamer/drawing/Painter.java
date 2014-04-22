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
    
    
package progamer.drawing;


import java.net.URL;
import java.util.ArrayList;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import progamer.views.ProgamerMainView;




/**
 * Esto es una prueba de subir los cambios
 * @author Javier
 *
 */

public class Painter  {
	
	private static ArrayList tree;
	private static ArrayList oldtree;
	
	private static int scale = 16;
	
	
	private int base;
	public Image img;
	
	public Painter(int y) {
		
        img = new Image(ProgamerMainView.myCanvas.getDisplay(),
    			ProgamerMainView.class.getResourceAsStream("smwtileset.gif"));
        base = y;
	}
	
	private void paintTree(ArrayList l, GC g) {
		for (Object e:l) {
			if (e instanceof ArrayList) 
				paintTree((ArrayList) e, g);
			if (e instanceof SpriteComposite) {
				SpriteComposite sc = (SpriteComposite) e;
				for (Sprite s: sc.spriteList)	
					g.drawImage(img, s.x, s.y, scale, scale, s.posx*scale, base - (s.posy+1)*scale, scale, scale); 
			}
		}
	}
	
	public void paint(GC g){
		
			Color c = new Color(g.getDevice(), 248, 224, 176);
			g.setBackground(c);
			
			g.fillRectangle(new Rectangle(0, 0, ProgamerMainView.width, ProgamerMainView.height));
			if (tree!= null) {
				paintTree(tree, g);
			}			
			
			g.dispose();
			
		
	}
	

	
	

	public void setTree(ArrayList treelist) {
		oldtree = tree;
		tree = treelist;
	}
	

}
