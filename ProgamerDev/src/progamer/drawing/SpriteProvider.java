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

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import progamer.drawing.nodes.JavaMarioNode;


public class SpriteProvider {

	

	public static SpriteComposite field(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.strangeYellowBox, 0, 2);
		return comp;
	}

	public static SpriteComposite expression(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.normalYellowBox, 0, 2);
		return comp;
	}

	public static SpriteComposite local(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.questionYellowBox, 0, 2);
		return comp;
	}

	public static SpriteComposite constructorLeft(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landTerrainLeftSide, 0, 0);
		comp.addSprite(comp.landTerrainLeftSideUp, 0, 1);
		return comp;
	}

	public static SpriteComposite constructorCenter(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landTerrain, 0, 0);
		comp.addSprite(comp.landTerrainSoil, 0, 1);
		return comp;
	}

	public static SpriteComposite constructorRight(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landTerrainRightSide, 0, 0);
		comp.addSprite(comp.landTerrainRightSideUp, 0, 1);
		return comp;
	}

	public static SpriteComposite ifLeft(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		//comp.addSprite(comp.noneSprite, 0, 0);
		comp.addSprite(comp.landMountainLeftSide, 0, 0);
		comp.addSprite(comp.landMountainLeftSideUp, 0, 1);
		return comp;
	}

	public static SpriteComposite ifCenter(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landMountain, 0, 0);
		comp.addSprite(comp.landMountainSoil, 0, 1);
		return comp;
	}

	public static SpriteComposite mountain(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landMountain, 0, 0);
		return comp;
	}
	
	
	public static SpriteComposite brick(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.brick, 0, 0);
		return comp;
	}

	public static SpriteComposite leftMountain(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landMountainLeftSide, 0, 0);
		return comp;
	}

	public static SpriteComposite rightMountain(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landMountainRightSide, 0, 0);
		return comp;
	}

	public static SpriteComposite ifRight(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landMountainRightSide, 0, 0);
		comp.addSprite(comp.landMountainRightSideUp, 0, 1);

		return comp;
	}

	public static SpriteComposite ret(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.doorBrownDownSide, 1, 0);
		comp.addSprite(comp.doorBrownUpSide, 1, 1);
		return comp;
	}

	public static SpriteComposite forTubeLeft(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.noneSprite, 0, 0);
		comp.addSprite(comp.tubeGreenLeftSideDown, 1, 0);
		comp.addSprite(comp.tubeGreenLeftSideUp, 1, 1);
		comp.addSprite(comp.tubeGreenRihtSideDown, 2, 0);
		comp.addSprite(comp.tubeGreenRightSideUp, 2, 1);
		//comp.addSprite(comp.noneSprite, 3, 0);

		return comp;
	}
	

	public static SpriteComposite forTubeRight(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		//comp.addSprite(comp.noneSprite, 0, 0);
		comp.addSprite(comp.tubeGreenLeftSideDown, 0, 0);
		comp.addSprite(comp.tubeGreenLeftSideUp, 0, 1);
		comp.addSprite(comp.tubeGreenRihtSideDown, 1, 0);
		comp.addSprite(comp.tubeGreenRightSideUp, 1, 1);
		comp.addSprite(comp.noneSprite, 2, 0);

		return comp;
	}

	public static Rectangle getUnionBox(Rectangle r1, Rectangle r2) {
		
		Rectangle unionBox = new Rectangle(0, 0, 0, 0);
		unionBox.x = min(r1.x, r2.x);
		unionBox.y = min(r1.y, r2.y);
		unionBox.width = max(r1.x+r1.width-unionBox.x, r2.x+r2.width-unionBox.x);
		unionBox.height = max(r1.y+r1.height-unionBox.y, r2.y+r2.height-unionBox.y);
		return unionBox;
	}
	
	private static int min(int x, int x2) {
		if (x<x2) return x;
		return x2;
	}
	private static int max(int x, int x2) {
		if (x>x2) return x;
		return x2;
	}

	
	
	

	

}
