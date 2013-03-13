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

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

public class SpriteProvider {

	public SpriteProvider() {

	}

	SpriteComposite field(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.strangeYellowBox, 0, 2);
		return comp;
	}

	SpriteComposite expression(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.normalYellowBox, 0, 2);
		return comp;
	}

	SpriteComposite local(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.questionYellowBox, 0, 2);
		return comp;
	}

	SpriteComposite constructorLeft(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landTerrainLeftSide, 0, 0);
		comp.addSprite(comp.landTerrainLeftSideUp, 0, 1);
		return comp;
	}

	SpriteComposite constructorCenter(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landTerrain, 0, 0);
		comp.addSprite(comp.landTerrainSoil, 0, 1);
		return comp;
	}

	SpriteComposite constructorRight(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landTerrainRightSide, 0, 0);
		comp.addSprite(comp.landTerrainRightSideUp, 0, 1);
		return comp;
	}

	SpriteComposite ifLeft(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		//comp.addSprite(comp.noneSprite, 0, 0);
		comp.addSprite(comp.landMountainLeftSide, 0, 0);
		comp.addSprite(comp.landMountainLeftSideUp, 0, 1);
		return comp;
	}

	SpriteComposite ifCenter(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landMountain, 0, 0);
		comp.addSprite(comp.landMountainSoil, 0, 1);
		return comp;
	}

	SpriteComposite mountain(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landMountain, 0, 0);
		return comp;
	}

	SpriteComposite leftMountain(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landMountainLeftSide, 0, 0);
		return comp;
	}

	SpriteComposite rightMountain(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landMountainRightSide, 0, 0);
		return comp;
	}

	SpriteComposite ifRight(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landMountainRightSide, 0, 0);
		comp.addSprite(comp.landMountainRightSideUp, 0, 1);

		return comp;
	}

	SpriteComposite ret(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.doorBrownDownSide, 1, 0);
		comp.addSprite(comp.doorBrownUpSide, 1, 1);
		return comp;
	}

	SpriteComposite forTube(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		//comp.addSprite(comp.noneSprite, 0, 0);
		comp.addSprite(comp.tubeGreenLeftSideDown, 0, 0);
		comp.addSprite(comp.tubeGreenLeftSideUp, 0, 1);
		comp.addSprite(comp.tubeGreenRihtSideDown, 1, 0);
		comp.addSprite(comp.tubeGreenRightSideUp, 1, 1);
		//comp.addSprite(comp.noneSprite, 3, 0);

		return comp;
	}
	
	
	Rectangle getTypeDeclarationSprites(JavaMarioNode elem, int x, int y) {
		Rectangle boundingBox = new Rectangle(x, y, 0, 0);
		for (JavaMarioNode child : elem.children) {
			if (child.getNodeType() == ASTNode.METHOD_DECLARATION) {
				Rectangle r = getSprites(child, x, y);
				boundingBox = getUnionBox(boundingBox, r);
				x += r.width;
			}
		}
		return boundingBox;				
	}
	
	
	
	
	Rectangle getMethodDeclarationSprites(JavaMarioNode elem, int x, int y){
		
		Rectangle boundingBox = new Rectangle(x, y, 0, 0);
		JavaMarioNode block = elem.getFirstChildOfType(ASTNode.BLOCK);

		SpriteComposite comp = constructorLeft(x, y);
		elem.addComposite(comp);

		x += comp.width;
		y += comp.height;

		Rectangle methodBox = getSprites(block, x, y);

		y -= 2;
		int iterx = 0;
		while (iterx < methodBox.width) {
			comp = constructorCenter(x + iterx, y);
			elem.addComposite(comp);
			iterx++;
		}
		x += iterx;

		comp = constructorRight(x, y);
		elem.addComposite(comp);


		boundingBox.width = methodBox.width + 2*comp.width + 2; // for the last 2 spaces
		boundingBox = getUnionBox(boundingBox, methodBox);
		elem.rectangle = boundingBox;
		return boundingBox;
	}
	
	
	Rectangle getUnionBox(Rectangle r1, Rectangle r2) {
		
		Rectangle unionBox = new Rectangle(0, 0, 0, 0);
		unionBox.x = min(r1.x, r2.x);
		unionBox.y = min(r1.y, r2.y);
		unionBox.width = max(r1.x+r1.width-unionBox.x, r2.x+r2.width-unionBox.x);
		unionBox.height = max(r1.y+r1.height-unionBox.y, r2.y+r2.height-unionBox.y);
		return unionBox;
	}
	
	private int min(int x, int x2) {
		if (x<x2) return x;
		return x2;
	}
	private int max(int x, int x2) {
		if (x>x2) return x;
		return x2;
	}


	Rectangle getBlockSprites(JavaMarioNode elem, int x, int y) {
		
		Rectangle boundingBox = new Rectangle(x, y, 0, 0);
		Rectangle childBox = null;
		
		for (JavaMarioNode child : elem.children) {
			childBox = getSprites(child, x, y);
			boundingBox = getUnionBox(boundingBox, childBox);
			x += childBox.width;						
		}
		elem.rectangle = boundingBox;
		
		return boundingBox;
	}
	
	
	
	Rectangle getExpressionSprites(JavaMarioNode elem, int x, int y) {

		SpriteComposite comp = expression(x, y);
		elem.rectangle = getUnionBox(new Rectangle(x, y, 0, 0),comp.box);
		elem.addComposite(comp);
		
		return elem.rectangle;
	}

	Rectangle getVariableDeclarationSprites(JavaMarioNode elem, int x, int y) {
		
		Rectangle boundingBox = new Rectangle(x, y, 0, 0);
		
		if (elem.children.get(0).getNodeType() == ASTNode.PRIMITIVE_TYPE){
			for (int i = 1; i < elem.children.size() ; i++) {
				SpriteComposite comp = local(x, y);
				elem.addComposite(comp);
				y+=comp.height;
				boundingBox = getUnionBox(boundingBox, comp.box);
			}
		} 
		elem.rectangle = boundingBox;		
		return boundingBox;		
	}
	
	Rectangle getReturnSprites(JavaMarioNode elem, int x, int y) {
		
		SpriteComposite comp = ret(x, y);
		elem.addComposite(comp);
		elem.rectangle = getUnionBox(new Rectangle(x, y, 0, 0),comp.box);
		return elem.rectangle;
	}
	
	Rectangle getIfStatementSprites(JavaMarioNode elem, int x, int y) {
		int initx = x;
		int inity = y;
		JavaMarioNode elseNode = null;
		JavaMarioNode thenNode = null;
		
		if(elem.children.size() == 3){
			elseNode = elem.children.get(2);
		}
		
		if(elem.children.size() >= 2){
			thenNode = elem.children.get(1);
		}
		
		Rectangle elseBox = getSprites(elseNode, x+1, y);
		
		y += elseBox.height;
		
		SpriteComposite comp = ifLeft(x, y);
		elem.addComposite(comp);

		x += comp.width;
		y += comp.height;
		
		Rectangle thenBox = getSprites(thenNode, x, y); 
		
		
		y -= 2;
		int totalWidth = (thenBox.width > elseBox.width) ? thenBox.width : elseBox.width;
		int iterx = 0;
		
		while (iterx < totalWidth) {
			comp = ifCenter(x + iterx, y);
			elem.addComposite(comp);
			iterx++;
		}
		x += iterx;

		comp = ifRight(x, y);
		elem.addComposite(comp);
		
		for (int posx = 0; posx < totalWidth+2; posx++) {
			for (int posy = 0; posy < elseBox.height ; posy++) {
				comp = mountain(initx + posx, inity + posy);
				elem.addComposite(0, comp);
			}
		}
		

		elem.rectangle = getUnionBox(new Rectangle(initx, inity, 0, 0), new Rectangle(initx, inity, totalWidth + 2, thenBox.y + thenBox.height-inity));
		
		return elem.rectangle;
	}
	
	Rectangle getForStatementSprites(JavaMarioNode elem, int x, int y) {

		Rectangle boundingBox = new Rectangle(x, y, 0, 0);
		JavaMarioNode block = elem.getFirstChildOfType(ASTNode.BLOCK);
		SpriteComposite comp = forTube(x, y);		
		elem.addComposite(comp);
		x += comp.width;
		y += comp.height;
		Rectangle blockBox = getSprites(block, x, y);
		boundingBox = getUnionBox(boundingBox, blockBox);
		y -= 2;
		int iterx = 0;
		while (iterx < blockBox.width) {
			comp = constructorCenter(x+iterx, y);
			elem.addComposite(comp);
			iterx++;
		}
		x += iterx;
		comp = forTube(x, y);
		elem.addComposite(comp);
		boundingBox = getUnionBox(boundingBox, comp.box);
	
		elem.rectangle = boundingBox;
		return boundingBox;
	}
	
	public Rectangle getSprites(JavaMarioNode elem, int x, int y) {

		Rectangle boundingBox = new Rectangle(x, y, 0, 0);
		if(elem == null) {
			return boundingBox;
		}

		switch (elem.getNodeType()) {

		case ASTNode.TYPE_DECLARATION:			
			return getTypeDeclarationSprites(elem, x, y);

		case ASTNode.FIELD_DECLARATION:
			return getTypeDeclarationSprites(elem, x, y);

		case ASTNode.METHOD_DECLARATION:			
			return getMethodDeclarationSprites(elem, x, y);

		case ASTNode.BLOCK:
			return getBlockSprites(elem, x, y);
			
		case ASTNode.EXPRESSION_STATEMENT:
			return getExpressionSprites(elem, x, y);
						
		case ASTNode.VARIABLE_DECLARATION_STATEMENT:
			return getVariableDeclarationSprites(elem, x, y);

		case ASTNode.RETURN_STATEMENT:
			return getReturnSprites(elem, x, y);		
					
		case ASTNode.IF_STATEMENT:
			return getIfStatementSprites(elem, x, y);

		case ASTNode.FOR_STATEMENT:
			return getForStatementSprites(elem, x, y);			
			
		default:
			return getSprites(elem.children.get(0), x, y);
		}
	}

}
