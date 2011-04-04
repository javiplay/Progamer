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





public class Painter  {
	
	private static ArrayList tree;
	private static ArrayList oldtree;
	
	private static int scale = 16;
	
	
	private int base;
	public Image img;
	
	public Painter(int y) {
		ImageDescriptor imgdesc = createImageDescriptorFor("icons/smwtileset.gif");
        img = imgdesc.createImage();
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
		if (treeChanged()) {
			Color c = new Color(g.getDevice(), 248, 224, 176);
			g.setBackground(c);
			g.fillRectangle(new Rectangle(0, 0, MarioCodeView.width, MarioCodeView.height));
			if (tree!= null) {
				paintTree(tree, g);
			}
			g.dispose();
		}
	}
	
	public int totalInArrayList(ArrayList al) {
		int total;
		total = al.size();
		for (Object o: al){
			if (o instanceof ArrayList) {
				total--;
				total+=totalInArrayList((ArrayList) o);			
			}		
		}
		return total; 
	}
	
	public boolean treeChanged() {
		// compara el nuevo arbol con el anterior
		if (oldtree!=null)
			System.out.println("OLD:"+oldtree.toString());
		if (tree!=null) 
			System.out.println("NEW:"+tree.toString());
		if (oldtree != null && tree != null) {
			
			if (totalInArrayList(oldtree) == totalInArrayList(tree)) {				
				System.out.println("No pintar!!");
				return false;
			}
			else 
				return true;
			

		}
		return false;
	}
	public ImageDescriptor createImageDescriptorFor(String id) {
		  URL url = Platform.getBundle("es.javiplay.eclipse.plugin.smwcode").
		      getEntry(id);
		  return ImageDescriptor.createFromURL(url);
	}
	public void setTree(ArrayList treelist) {
		oldtree = tree;
		tree = treelist;
	}
	

}
