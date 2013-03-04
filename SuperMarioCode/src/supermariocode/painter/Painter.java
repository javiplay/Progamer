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
		
        img = new Image(MarioCodeView.myCanvas.getDisplay(),
    			MarioCodeView.class.getResourceAsStream("smwtileset.gif"));
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
			g.fillRectangle(new Rectangle(0, 0, MarioCodeView.width, MarioCodeView.height));
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
