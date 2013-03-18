package supermariocode.painter.nodes;

import org.eclipse.swt.graphics.Rectangle;

import supermariocode.painter.SpriteComposite;
import supermariocode.painter.SpriteProvider;


public class ExpressionStatementNode extends JavaMarioNode {

	public ExpressionStatementNode(String name, int nodeType, int _linenumber) {
		super(name, nodeType, _linenumber);
		// TODO Auto-generated constructor stub
	}
	
	Rectangle getExpressionSprites(JavaMarioNode elem, int x, int y) {

		SpriteComposite comp = SpriteProvider.expression(x, y);
		elem.rectangle = SpriteProvider.getUnionBox(new Rectangle(x, y, 0, 0),comp.box);
		elem.addComposite(comp);
		
		return elem.rectangle;
	}
	
   @Override
	public Rectangle getSprites(int x, int y) {
		// TODO Auto-generated method stub
		return getExpressionSprites(this, x, y); 
	}
}
