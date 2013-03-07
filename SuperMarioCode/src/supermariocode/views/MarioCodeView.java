
/* 
  Code Reimagined, Class for the eclipse view
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
    
package supermariocode.views;



import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CaretEvent;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import supermariocode.painter.MarioPainter;
import supermariocode.painter.SpriteProvider;
/*import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
*/
/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class MarioCodeView extends ViewPart implements ISelectionListener {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "supermariocode.views.MarioCodeView";

	public final static int width = 1200;
	public final static int height = 200;

	private Action action1;
	private Action action2;
	
	
	public static Canvas myCanvas;
	Image image1, image2;
	
	Label label;
	Label labelState;
	Composite control;
	
	

	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	 
	/**
	 * The constructor.
	 */
	public MarioCodeView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		
	    control = parent;	   
		myCanvas = new Canvas(control, SWT.H_SCROLL | SWT.V_SCROLL);

		image2 = new Image(MarioCodeView.myCanvas.getDisplay(),
    			MarioCodeView.class.getResourceAsStream("background1.jpg"));

        //myCanvas.setBackgroundImage(image2);
        //labelState = new Label(parent, SWT.WRAP);
        
        // add myself as a global selection listener
        getSite().getPage().addSelectionListener(this);
        
        
        
        // prime the selection
        selectionChanged(getSite().getPage().getActiveEditor(), getSite().getPage().getSelection());



		
		myCanvas.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub			
				System.out.println("x: "+e.x+", y:"+e.y);
				
			}
		});		
		
		
		// probando esto...de los resource listener 
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IResourceChangeListener listener = new myResourceListener();
		workspace.addResourceChangeListener(listener);
		
		makeActions();
		contributeToActionBars();
	}
	
	public class myResourceListener implements IResourceChangeListener 
	{
		@Override
		public void resourceChanged(IResourceChangeEvent event) {
				// TODO Auto-generated method stub
			
			if (event.getType() ==  IResourceChangeEvent.POST_CHANGE) {
				
				final ArrayList<IResource> changed = new ArrayList<IResource>();
				IResourceDelta rootDelta = event.getDelta();

				//definimos el visitante
				IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
		            public boolean visit(IResourceDelta delta) {
		               //only interested in changed resources (not added or removed)
		               if (delta.getKind() != IResourceDelta.CHANGED)
		                  return true;
		               //only interested in content changes
		               if ((delta.getFlags() & IResourceDelta.CONTENT) == 0)
		                  return true;
		               IResource resource = delta.getResource();
		               //only interested in files with the "java" extension
		               if (resource.getType() == IResource.FILE && 
						"java".equalsIgnoreCase(resource.getFileExtension())) {
		                  changed.add(resource);
		               }
		               return true;
		            }
		         };
		         try {
		             rootDelta.accept(visitor);
		          } catch (CoreException e) {
		             //open error dialog with syncExec or print to plugin log file
		          }

				

		        if (changed.size()>0){
					System.out.println("Evento resourceChanged (tipo POST_CHANGE)");
					for (IResource res: changed ) {
						System.out.println("Resource: "+ res.getName());				
					}
					System.out.println("-----------------------------------------");
		        }
				
				
				// actualizaci�n de la interfaz
				Display disp = control.getDisplay();
				disp.asyncExec(new Runnable() {					
					@Override
					public void run() {
						//label.setText(label.getText()+"\nEvent type: CHANGED");

										
					}
				});					
			}
		}
	}


	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
	}

	/**
	 * Reads a ICompilationUnit and creates the AST DOM for manipulating the
	 * Java source file
	 * 
	 * @param unit
	 * @return
	 */

	private static CompilationUnit parse(ICompilationUnit unit) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(unit);
		parser.setResolveBindings(true);
		return (CompilationUnit) parser.createAST(null); // parse
	}
	
	
	private void makeActions() {
		
		/* Action 1 */
		action1 = new Action() {
			public void run() {
				showMessage("Action 1 executed");
					
				
				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				IWorkspaceRoot root = workspace.getRoot();
				
				// Get all projects in the workspace
				IProject[] projects = root.getProjects();
				
				// Loop over all projects
				for (IProject project : projects) {
					try {
						if (project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {

							IPackageFragment[] packages = JavaCore.create(project)
									.getPackageFragments();
							// parse(JavaCore.create(project));
							for (IPackageFragment mypackage : packages) {
								if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
									for (ICompilationUnit unit : mypackage
											.getCompilationUnits()) {
										
										// Now create the AST for the ICompilationUnits
										CompilationUnit parse = parse(unit);
										TreeVisitor visitor = new TreeVisitor();
										parse.accept(visitor);
										String l = visitor.toString();
										System.out.println(l);
										
										SpriteProvider sp = new SpriteProvider();
										Point size = sp.getSprites(visitor.tree, 0, 0);
										System.out.println(visitor.tree.toString());
										System.out.println("X="+size.x+",Y="+size.y);
										
										//pintar
										Rectangle bounds = new Rectangle(0,0,size.x*16, size.y*16+32);										
										image1 = new Image(myCanvas.getDisplay(), bounds);
										ImageData imgData = image1.getImageData();
										int whitePixel = imgData.palette.getPixel(new RGB(255,255,255));
										System.out.println("WHITE PIXELLLLL = "+ whitePixel);
										imgData.transparentPixel = whitePixel;
										image1 = new Image(myCanvas.getDisplay(), imgData);
										
										
										GC gc = new GC(image1);
										MarioPainter painter = new MarioPainter(size.y*16+32, gc);
										myCanvas.setBackgroundImage(painter.imgBG);
										painter.paintTree(visitor.tree);																			
										image1.getImageData().transparentPixel = image1.getImageData().palette.getPixel(new RGB(255,255,255));										
										gc.dispose();
										painter.img.dispose();
										
										
										//controlar barra de scroll horizontal
										final ScrollBar hBar = myCanvas.getHorizontalBar();
										final Point origin = new Point(0, 0);
									    hBar.addListener(SWT.Selection, new Listener() {
									      public void handleEvent(Event e) {
									        int hSelection = hBar.getSelection();
									        int destX = -hSelection - origin.x;
									        Rectangle rect = image1.getBounds();
									        myCanvas.scroll(destX, 0, 0, 0, rect.width, rect.height, false);
									        origin.x = -hSelection;
									        //myCanvas.redraw();
									      }
									    });
									    // controlar barra de scroll vertical
									    final ScrollBar vBar = myCanvas.getVerticalBar();
									    vBar.addListener(SWT.Selection, new Listener() {
									      public void handleEvent(Event e) {
									        int vSelection = vBar.getSelection();
									        int destY = -vSelection - origin.y;
									        Rectangle rect = image1.getBounds();
									        myCanvas.scroll(0, destY, 0, 0, rect.width, rect.height, false);
									        origin.y = -vSelection;
									        //myCanvas.redraw();
									      }
									    });
									    // cambio de tama�o del canvas
									    myCanvas.addListener(SWT.Resize, new Listener() {
									      public void handleEvent(Event e) {
									        Rectangle rect = image1.getBounds();
									        Rectangle client = myCanvas.getClientArea();
									        hBar.setMaximum(rect.width);
									        vBar.setMaximum(rect.height);
									        hBar.setThumb(Math.min(rect.width, client.width));
									        vBar.setThumb(Math.min(rect.height, client.height));
									        int hPage = rect.width - client.width;
									        int vPage = rect.height - client.height;
									        int hSelection = hBar.getSelection();
									        int vSelection = vBar.getSelection();
									        if (hSelection >= hPage) {
									          if (hPage <= 0)
									            hSelection = 0;
									          origin.x = -hSelection;
									        }
									        if (vSelection >= vPage) {
									          if (vPage <= 0)
									            vSelection = 0;
									          origin.y = -vSelection;
									        }
									        myCanvas.redraw();
									      }
									    });
									    
									    myCanvas.addListener(SWT.Paint, new Listener() {
									      public void handleEvent(Event e) {
									        GC gc = e.gc;
									        gc.drawImage(image1, origin.x, origin.y);
									        /*Rectangle rect = image1.getBounds();
									        Rectangle client = myCanvas.getClientArea();
									        int marginWidth = client.width - rect.width;
									        if (marginWidth > 0) {
									          gc.fillRectangle(rect.width, 0, marginWidth, client.height);
									        }
									        int marginHeight = client.height - rect.height;
									        if (marginHeight > 0) {
									          gc.fillRectangle(0, rect.height, client.width,
									                  marginHeight);
									        }*/
									        //myCanvas.redraw();
									      }
									    });
									    
									    myCanvas.redraw();

										
										//myImage.dispose();
																													
									}
								}
							}
							
						}
					} catch (CoreException e) {
						e.printStackTrace();
					/*} catch (JsonParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JsonMappingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						*/
					}
				}
			}
		};
		action1.setText("Action 1");
		action1.setToolTipText("Action 1 tooltip");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		/* Action 2 */
		action2 = new Action() {
			public void run() {
				showMessage("Action 2 executed");
				image1 = image2;
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
	}

	
	private void showMessage(String message) {
		MessageDialog.openInformation(
			myCanvas.getShell(),
			"Mario View",
			message);
			
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		myCanvas.setFocus();
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		
		if (part != null) {
			Class c = part.getClass();		
			
			if (c.getName().equals("org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor")) {
				
				IEditorPart editor = (IEditorPart) part;
				
				StyledText st = (StyledText) editor.getAdapter(Control.class);
				
				try {
				if (st.getListeners(0).length==0){
					
					st.addCaretListener(new CaretListener() {
						
						@Override
						public void caretMoved(CaretEvent event) {
							// TODO Auto-generated method stub
							System.out.println(event.caretOffset);
							
						}
					});
					}
				}
				catch (NullPointerException e) {
					System.out.println("nooooo");
				
				}
				
				IEditorInput input = editor.getEditorInput();
				System.out.println("Evento de selecci�n sobre un editor java");
				System.out.println("IWortkbenchPart = "+c.getName());				
				System.out.println("IEditorInput = "+input.getName());
				System.out.println("----------------CONTENT-----------------");
				
				System.out.println("----------------------------------------");
			}
		}		
		System.out.println(getSite().getPage().getEditorReferences().length+" editores abiertos.");
	}
}
