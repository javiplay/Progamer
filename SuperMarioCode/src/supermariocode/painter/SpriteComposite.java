
/* 
  Code Reimagined, Class for storing a collection of sprites
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

public class SpriteComposite {
	int posx;
	int posy;
	int lenx;
	int leny;
	
	
	public Sprite noneSprite 				= new Sprite("noneSprite", 0, 0);
	
	public Sprite strangeYellowBox 			= new Sprite("strangeYellowBox", 274,64);
	public Sprite normalYellowBox 			= new Sprite("normalYellowBox", 173,164);
	public Sprite questionYellowBox 		= new Sprite("questionYellowBox", 207,181);
	
	public Sprite landTerrainLeftSide 		= new Sprite("landTerrainLeftSide",427,219);
	public Sprite landTerrainLeftSideUp 	= new Sprite("landTerrainLeftSideUp",427,202);	
	public Sprite landTerrainSoil 			= new Sprite("landTerrainSoil",444,202);
	public Sprite landTerrainRightSideUp	= new Sprite("landTerrainRightSide", 461,202);
	public Sprite landTerrainRightSide 		= new Sprite("landTerrainRightSide", 461,219);
	public Sprite landTerrain 				= new Sprite("landTerrain",444,219);
	
	public Sprite landMountainLeftSide 		= new Sprite("landMountainLeftSide",427,270);
	public Sprite landMountainLeftSideUp	= new Sprite("landMountainSoil",427,253);
	public Sprite landMountainSoil 			= new Sprite("landMountain",444,253);	
	public Sprite landMountainRightSideUp 	= new Sprite("landMountainSoil",461,253);
	public Sprite landMountainRightSide 	= new Sprite("landMountainRightSide",461,270);
	public Sprite landMountain 				= new Sprite("landMountain",444,270);
	
	public Sprite jumperGreen 				= new Sprite("jumperGreen",293,114);
	public Sprite doorBrownUpSide 			= new Sprite("doorBrownUpSide",113, 1);
	public Sprite doorBrownDownSide 		= new Sprite("doorBrownDownSide",113,17);
	public Sprite tubeGreenLeftSideUp 		= new Sprite("tubeGreenLeftSideUp",1,178);
	public Sprite tubeGreenLeftSideDown 	= new Sprite("tubeGreenLeftSideDown",1,195);
	public Sprite tubeGreenRightSideUp 		= new Sprite("tubeGreenRightSideUp",18,178);
	public Sprite tubeGreenRihtSideDown 	= new Sprite("tubeGreenRihtSideDown",18,195);
	//TODO, que posicion del sprite corresponde al mario
	
	
	public ArrayList<Sprite> spriteList;
		
	public SpriteComposite(int x, int y){
		spriteList = new ArrayList();
		posx = x;
		posy = y;
	};
	
	public boolean addSprite(Sprite s, int mwx, int mwy) {
		s.setPos(posx + mwx, posy + mwy);
		if (mwx+1>lenx)
			lenx = mwx+1;
		if (mwy+1>leny)
			leny = mwy+1;
		spriteList.add(s);
		return true;
	};
	
	public String toString() {
		
		String result = new String("Comp("+posx+","+posy+","+lenx+","+leny+")");
		
		return result;	
	}

};
