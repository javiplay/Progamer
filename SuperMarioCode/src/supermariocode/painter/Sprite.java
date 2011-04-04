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
