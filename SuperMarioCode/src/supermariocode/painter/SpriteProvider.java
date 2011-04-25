package supermariocode.painter;

import java.net.URL;
import java.util.ArrayList;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

import supermariocode.views.MarioCodeView;

public class SpriteProvider {
	
	
	public SpriteProvider() {			
   
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
	
	
	public Point getSprites(ArrayList tree, int x, int y){
		
		
		// Devolver el tamaño total de la composición
		Point length = new Point(0,0);				
		
		for (int i = 0; i<tree.size(); i+=2) {						
			// el arbol está compuesto por pares (JavaMarioNode, ArrayList)
			
			JavaMarioNode elem = (JavaMarioNode) tree.get(i);
			ArrayList list = (ArrayList) tree.get(i+1);			
			
			
			SpriteComposite comp;
			switch (elem.getNodeType()) {
			case ASTNode.COMPILATION_UNIT:
				return getSprites(list, x, y);
				
			case ASTNode.TYPE_DECLARATION:
				return getSprites(list, x, y);
				
			case ASTNode.FIELD_DECLARATION:
					comp = field(x, y);						 					
					length.x += comp.lenx; // lenx
					if (comp.leny > length.y) {
						length.y = comp.leny; // leny
					}					
					elem.addComposite(comp);
					x += comp.lenx;						
					break;				
			
			case ASTNode.METHOD_DECLARATION:
					
					// coger la lista de block
					list = FindBlock(list);
				
					// añadir la parte izquierda del suelo
					comp = constructorLeft(x, y);
					elem.addComposite(comp);
					
					// ajustamos las coodenadas del lápiz
					x += comp.lenx;
					y += comp.leny;					
					
					// añadimos el suelo del método
					Point methodLength = new Point(0,0); 						
					if (!list.isEmpty()) {
						// obtener la composición del método recursivamente
						methodLength = getSprites(list, x, y);
						System.out.println("METHOD:"+methodLength.x+"x"+methodLength.y);
						
						y -= 2;
						int soilx = x;
						while (soilx < x + methodLength.x) {
							comp = constructorCenter(soilx, y);
							elem.addComposite(comp);
							soilx++;
						}
						x = soilx;
					}
					// añadimos el final del método
					comp = constructorRight(x, y);
					elem.addComposite(comp);
					
					// establecemos el tamaño final de esta composición de sprites
					length.x += methodLength.x+2+2; // el contenido horizontal del metodo mas el cuadro de inicio y el de final
					if (2 + methodLength.y > length.y) {
						length.y = 2 + methodLength.y; // leny
					}																								
					x+=3; // dejamos un hueco entre métodos de tamaño 2					
					break;
					
			case ASTNode.EXPRESSION_STATEMENT:
					comp = expression(x, y);						 
					length.x += comp.lenx; // lenx
					if (comp.leny>length.y) {
						length.y = comp.leny; // leny
					}
					elem.addComposite(comp);					
					x += comp.lenx;
					break;
			case ASTNode.VARIABLE_DECLARATION_STATEMENT:
					comp = local(x, y);						 
					length.x += comp.lenx; // lenx
					if (comp.leny>length.y) {
						length.y = comp.leny; // leny
					}
					elem.addComposite(comp);				
					x += comp.lenx;
					break;				
			case ASTNode.RETURN_STATEMENT:
					comp = ret(x, y);						 
					length.x += comp.lenx; // lenx
					if (comp.posy + comp.leny > length.y) {
						length.y = comp.posy + comp.leny; // leny
					}
					elem.addComposite(comp);				
					x += comp.lenx;
					break;
			case ASTNode.IF_STATEMENT:
				
				list = FindBlock(list);
				comp = ifLeft(x, y);
				elem.addComposite(comp);
				x += comp.lenx;
				y += comp.leny;

				Point thenLength = new Point(0,0); // the size of the corresponding part of stage						
				if (!list.isEmpty()) {					
					thenLength = getSprites(list, x, y);
					System.out.println("THEN:"+thenLength.x+"x"+thenLength.y);
					
					y -= 2;
					int soilx=x;
					while (soilx < x + thenLength.x) {					
						comp = ifCenter(soilx, y);
						elem.addComposite(comp);						
						soilx++;
					}
					x = soilx;
				}
				// final constructor/method terrain
				comp = ifRight(x, y);
				x += comp.lenx;
				elem.addComposite(comp);
				
				length.x += thenLength.x+4;
				if (y + thenLength.y > length.y) {
					length.y = y + thenLength.y; // leny
				}																								
				
				break;
			
			case ASTNode.FOR_STATEMENT:
			
				list = FindBlock(list);
				// initial constructor/method terrain
				comp = forTube(x, y);
				elem.addComposite(comp);				
				x += comp.lenx;
				Point forLength = new Point(0,0);				
				forLength = getSprites(list, x, y);
				System.out.println("FOR:"+forLength.x+"x"+forLength.y);
				
				
				x += forLength.x;
				comp = forTube(x, y);
				elem.addComposite(comp);
				
				x += comp.lenx;
				
				length.x += forLength.x+8;
				if (y + forLength.y > length.y) {
					length.y = y + forLength.y; // leny
				}																								
				break;			
			}			
		}
		return length;
	}

	private ArrayList FindBlock(ArrayList list) {
		int i = 0;
		boolean found = false;
		ArrayList blockList = null;
		while (i<list.size() && !found) {
			JavaMarioNode n = (JavaMarioNode) list.get(i);
			if (n.nodeType == ASTNode.BLOCK) {
				found = true;
				blockList = (ArrayList) list.get(i+1);
			}
			i+=2;
		}
		return blockList;
	}
}
