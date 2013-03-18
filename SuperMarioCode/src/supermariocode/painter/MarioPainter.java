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
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import supermariocode.painter.nodes.JavaMarioNode;
import supermariocode.views.MarioCodeView;

public class MarioPainter {

	private static ArrayList tree;

	private static int scale = 16;

	private int base;
	public Image img;
	public Image imgBG;
	public GC g;

	// Para a�adir colores a los rectangulos:
	Device d;
	Color magenta;
	Color green;
	Color yellow;
	Color red;
	Color black;
	Color cyan;
	Color gray;

	ArrayList colors;
	int index = 0;
	
	public MarioPainter(int base, GC g) {

		img = new Image(MarioCodeView.myCanvas.getDisplay(),
				MarioCodeView.class.getResourceAsStream("smwtileset.gif"));
		imgBG = new Image(MarioCodeView.myCanvas.getDisplay(),
				MarioCodeView.class.getResourceAsStream("background1.jpg"));
		this.base = base;
		this.g = g;

		// Para incluir colores a los rectangulos:
		d = g.getDevice();
		// Definimos colores:
		magenta = d.getSystemColor(SWT.COLOR_MAGENTA);
		green = d.getSystemColor(SWT.COLOR_GREEN);
		yellow = d.getSystemColor(SWT.COLOR_YELLOW);
		red = d.getSystemColor(SWT.COLOR_RED);
		black = d.getSystemColor(SWT.COLOR_BLACK);
		cyan = d.getSystemColor(SWT.COLOR_CYAN);
		gray = d.getSystemColor(SWT.COLOR_GRAY);
		
		// A�adimos a un array los diferentes colores:
		colors = new ArrayList();
		colors.add(black);
		colors.add(cyan);
		colors.add(green);
		colors.add(red);
		colors.add(magenta);
		colors.add(yellow);
		colors.add(gray);
		// g.drawImage(imgBG, 0, 0);

	}

	
	public void paintTree(JavaMarioNode mn) {
		System.out.println(mn.name);
		ArrayList<JavaMarioNode> content = mn.children;
		System.out.println(mn.compList);
		for (SpriteComposite sc : mn.compList) {

			for (Sprite s : sc.spriteList) {
				g.drawImage(img, s.x, s.y, scale, scale, s.posx * scale, base
						- (s.posy + 1) * scale, scale, scale);
			}
		}
		for (JavaMarioNode child : content) {
			paintTree(child);
		}

	}

	// M�todo que agrega rect�ngulos para el modo debug:
	public void paintTreeDebug(JavaMarioNode mn) {

		//A�adimos un estilo de l�nea a los rect�ngulos
		g.setLineStyle(SWT.LINE_DOT);
		g.setLineWidth(2);
		//Asignamos el color:
		g.setForeground((Color) colors.get(index));
		//Lo dibujamos:
		g.drawRectangle(mn.rectangle.x * scale, base - (mn.rectangle.y + mn.rectangle.height) * scale,
				mn.rectangle.width * scale, mn.rectangle.height * scale);
		
		for (JavaMarioNode child : mn.children) {
			
			index = (index + 1)% colors.size();
			paintTreeDebug(child);
			
			index--;
			if (index < 0) {
				index = colors.size()-1;
			}
		}

	}

}
