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
        g.drawImage(imgBG, 0, 0);
        
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
	
	


}
