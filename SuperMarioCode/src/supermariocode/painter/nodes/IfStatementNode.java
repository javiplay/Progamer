package supermariocode.painter.nodes;

import org.eclipse.swt.graphics.Rectangle;

import supermariocode.painter.SpriteComposite;
import supermariocode.painter.SpriteProvider;


public class IfStatementNode extends JavaMarioNode {

	public IfStatementNode(String name, int nodeType, int offset) {
		super(name, nodeType, offset);
		// TODO Auto-generated constructor stub
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
		
		Rectangle elseBox = elseNode.getSprites(x+1, y);
		
		y += elseBox.height;
		
		SpriteComposite comp = SpriteProvider.ifLeft(x, y);
		elem.addComposite(comp);

		x += comp.width;
		y += comp.height;
		
		Rectangle thenBox = thenNode.getSprites(x, y); 
		
		
		y -= 2;
		int totalWidth = (thenBox.width > elseBox.width) ? thenBox.width : elseBox.width;
		int iterx = 0;
		
		while (iterx < totalWidth) {
			comp = SpriteProvider.ifCenter(x + iterx, y);
			elem.addComposite(comp);
			iterx++;
		}
		x += iterx;

		comp = SpriteProvider.ifRight(x, y);
		elem.addComposite(comp);
		
		for (int posx = 0; posx < totalWidth+2; posx++) {
			for (int posy = 0; posy < elseBox.height ; posy++) {
				comp = SpriteProvider.mountain(initx + posx, inity + posy);
				elem.addComposite(0, comp);
			}
		}
		

		elem.rectangle = SpriteProvider.getUnionBox(new Rectangle(initx, inity, 0, 0), new Rectangle(initx, inity, totalWidth + 2, thenBox.y + thenBox.height-inity));
		
		return elem.rectangle;
	}
	@Override
	public Rectangle getSprites(int x, int y) {
		// TODO Auto-generated method stub
		return getIfStatementSprites(this, x, y);
	}

}
