package supermariocode.painter;

import java.net.URL;
import java.util.ArrayList;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

import supermariocode.views.MarioCodeView;

public class SpriteProvider {
	public Image img;
	public ArrayList spritedTree;

	
	
	public SpriteProvider(String spritesSheet) {			
        img = new Image(MarioCodeView.myCanvas.getDisplay(),
    			MarioCodeView.class.getResourceAsStream("smwtileset.gif"));
        spritedTree = new ArrayList();
	}
	
	public SpriteComposite field(int x, int y){
		SpriteComposite comp = new SpriteComposite(x, y);		
		comp.addSprite(comp.strangeYellowBox, 0, 1);
		return comp;		
	}
	
	public SpriteComposite expression(int x, int y){
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.normalYellowBox, 0, 3);
		return comp;		
	}
	
	public SpriteComposite local(int x, int y){
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.questionYellowBox, 0, 3);
		return comp;		
	}
	public SpriteComposite constructorLeft(int x, int y){		
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landTerrainLeftSide, 0, 0);
		comp.addSprite(comp.landTerrainLeftSideUp, 0, 1);
		return comp;
	}
	public SpriteComposite constructorCenter(int x, int y){		
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landTerrain, 0, 0);
		comp.addSprite(comp.landTerrainSoil, 0, 1);
		return comp;
	}
	
	public SpriteComposite constructorRight(int x, int y){		
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landTerrainRightSide, 0, 0);
		comp.addSprite(comp.landTerrainRightSideUp, 0, 1);
		return comp;
	}
	public SpriteComposite ifLeft(int x, int y){		
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.noneSprite, 0, 0);
		comp.addSprite(comp.landMountainLeftSide, 1, 0);
		comp.addSprite(comp.landMountainLeftSideUp, 1, 1);
		return comp;
	}
	public SpriteComposite ifCenter(int x, int y){		
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landMountain, 0, 0);
		comp.addSprite(comp.landMountainSoil, 0, 1);
		return comp;
	}
	
	public SpriteComposite ifRight(int x, int y){		
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landMountainRightSide, 0, 0);
		comp.addSprite(comp.landMountainRightSideUp, 0, 1);
		comp.addSprite(comp.noneSprite, 1, 0);
		
		return comp;
	}
	
	public SpriteComposite ret(int x, int y){		
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.doorBrownDownSide, 1, 0);
		comp.addSprite(comp.doorBrownUpSide, 1, 1);
		return comp;
	}
	
	public SpriteComposite forTube(int x, int y){		
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.noneSprite, 0, 0);		
		comp.addSprite(comp.tubeGreenLeftSideDown, 1, 0);
		comp.addSprite(comp.tubeGreenLeftSideUp, 1, 1);
		comp.addSprite(comp.tubeGreenRihtSideDown, 2, 0);
		comp.addSprite(comp.tubeGreenRightSideUp, 2, 1);
		comp.addSprite(comp.noneSprite, 3, 0);
		
		
		return comp;
	}
	
	
	public Point getSprites(ArrayList tree2, int x, int y){
		
		Point length = new Point(0,0);
		
		for (Object obj: tree2) {
			ArrayList tree;
			Object elem;
			if (obj instanceof ArrayList) {
				elem = ((ArrayList) obj).get(0);
				tree = (ArrayList) obj;
			} else {
				elem = obj;
				tree = tree2;
			}
					
			if (elem instanceof String) {
				if (elem.equals("field")) {
					SpriteComposite comp = field(x, y);						 
					length.x += comp.lenx; // lenx
					if (comp.leny > length.y) {
						length.y = comp.leny; // leny
					}
					tree.add(comp);
					x += comp.lenx;
					if (tree==tree2) break;
				}					 
				if (elem.equals("constructor") || elem.equals("method")) {
						// initial constructor/method terrain
						ArrayList complist = new ArrayList();
						SpriteComposite comp = constructorLeft(x, y);
						complist.add(comp);
						x += comp.lenx;
						y += comp.leny;
						
						// all above the constructor/method soil
						Point p = new Point(0,0); // the size of the total in stage						
						if (tree.size() > 1 && tree.get(1) instanceof ArrayList) {
							p = getSprites((ArrayList)tree.get(1), x, y);
							y -= 2;
							int i=0;
							for (i = x; i < x + p.x; i++) {
								comp = constructorCenter(i, y);
								complist.add(comp);
							}
							x = i;
						}
						// final constructor/method terrain
						comp = constructorRight(x, y);						 
						complist.add(comp);
						length.x += p.x+2;
						length.y += p.y+2;
						tree.add(complist);
						x+=3;
						if (tree==tree2) break;
				}
				if (elem.equals("expression")) {
					SpriteComposite comp = expression(x, y);						 
					length.x += comp.lenx; // lenx
					if (comp.leny > length.y) {
						length.y = comp.leny; // leny
					}				
					tree.add(comp);
					x += comp.lenx;
					if (tree==tree2) break;
	
				}
				if (elem.equals("local")) {
					SpriteComposite comp = local(x, y);						 
					length.x += comp.lenx; // lenx
					if (comp.leny > length.y) {
						length.y = comp.leny; // leny
					}
					tree.add(comp);
					x += comp.lenx;
					if (tree==tree2) break;
	
				}
				if (elem.equals("return")) {
					SpriteComposite comp = ret(x, y);						 
					length.x += comp.lenx; // lenx
					if (comp.leny > length.y) {
						length.y = comp.leny; // leny
					}
					tree.add(comp);
					x += comp.lenx;
					if (tree==tree2) break;
	
				}
				if (elem.equals("if")) {
					// initial constructor/method terrain
					ArrayList complist = new ArrayList();
					SpriteComposite comp = ifLeft(x, y);
					complist.add(comp);
					x += comp.lenx;
					y += comp.leny;
					
					// all above the constructor/method soil
					Point p = new Point(0,0); // the size of the corresponding part of stage						
					if (tree.size() > 1 && tree.get(1) instanceof ArrayList) {
						p = getSprites((ArrayList)tree.get(1), x, y);
						y -= 2;
						int i=0;
						for (i = x; i < x + p.x; i++) {
							comp = ifCenter(i, y);
							complist.add(comp);
						}
						x = i;
					}
					// final constructor/method terrain
					comp = ifRight(x, y);
					x += comp.lenx;
					complist.add(comp);
					length.x += p.x+4;
					length.y += p.y+4;
					tree.add(complist);
					
					if (tree==tree2) break;
				}
				
				if (elem.equals("for")) {
					// initial constructor/method terrain
					ArrayList complist = new ArrayList();
					SpriteComposite comp = forTube(x, y);
					complist.add(comp);
					x += comp.lenx;
					Point p = new Point(0,0);
					p = getSprites((ArrayList)tree.get(1), x, y);
					
					x += p.x;
					comp = forTube(x, y);
					complist.add(comp);
					x += comp.lenx;
					
					length.x += p.x+8;
					length.y += p.y;
					tree.add(complist);
					
					if (tree==tree2) break;
				}
			} //not string
		}
		return length;
	}
	
	
	
}
