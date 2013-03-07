/* 
  Code Reimagined, Class for storing information about the sprites
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

public class Sprite {
	//origen abajo izquierda
	public int x;
	public int y;
	private String name;
	// offsets in mario world coords (units in sprites)
	int posx;
	int posy;
	
	public Sprite(String name, int x, int y){
		
		this.x=x;
		this.y=y;
		this.name = name;
	}
	
	public void setPos(int x, int y){
		posx=x;
		posy=y;
	}
	public String toString() {
		return name+"("+posx+","+posy+")";
	}
	
}
