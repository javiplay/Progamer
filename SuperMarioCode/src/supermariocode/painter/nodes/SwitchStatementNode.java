package supermariocode.painter.nodes;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.graphics.Rectangle;

import supermariocode.painter.SpriteComposite;
import supermariocode.painter.SpriteProvider;


public class SwitchStatementNode extends JavaMarioNode {

	public SwitchStatementNode(String name, int nodeType, int offset) {
		super(name, nodeType, offset);
		// TODO Auto-generated constructor stub
	}
	
	private Rectangle getSwitchStatementSprites(JavaMarioNode elem, int x, int y) {
		System.out.println("---------SWITCH--------");
		System.out.println(elem.toLongString());
		// first create list of cases
		ArrayList<JavaMarioNode> caseNodeList = new ArrayList<JavaMarioNode>();
		ArrayList<JavaMarioNode> elemList = new ArrayList<JavaMarioNode>();
		for (int i = 0; i<elem.children.size(); i++) {
			// create list of elems in case
			JavaMarioNode child = elem.children.get(i);
			
			if (child.nodeType == ASTNode.SWITCH_CASE && (elem.children.get(i-1).nodeType == ASTNode.BREAK_STATEMENT || i<=1)) {				
				elemList = new ArrayList<JavaMarioNode>();
				JavaMarioNode caseNode = new BlockDeclarationNode("BLOCK", ASTNode.BLOCK, child.offset);
				caseNode.children = elemList;
				caseNodeList.add(caseNode);
			}
			elemList.add(child);			
		}
		
		
		
		Rectangle box = new Rectangle(x, y, 0, 0);
		SpriteComposite door = SpriteProvider.ret(x, y);
		elem.addComposite(door);
		box = SpriteProvider.getUnionBox(box, door.box);
		x += door.width;
		for (JavaMarioNode n: caseNodeList) {
			Rectangle blockBox = n.getSprites(x, y+1);
			// add the platform at the bottom
			for (int i=0; i<blockBox.width;i++) {
				SpriteComposite comp = SpriteProvider.brick(x+i, y);
				elem.addComposite(comp);
			}
			blockBox.height += 2;
			box = SpriteProvider.getUnionBox(box, blockBox);
			y+=blockBox.height;			
		}
		
		elem.rectangle = box;
		return box;
	}
	
	@Override
	public Rectangle getSprites(int x, int y) {
		// TODO Auto-generated method stub
		return getSwitchStatementSprites(this,x, y);
	}

}
