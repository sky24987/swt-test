

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;


public class ChangeColor extends Shell {

	Point p=new Point(0,0);
	
	Color color1;
	Color color2;
	int i=0;
	
	//´°¿ÚÒÆ¶¯
   public class ShellMoveListenter implements Listener{
    	
        public void handleEvent(Event arg0) {
           switch (arg0.type) {  
                case SWT.MouseDown:  
                    p.x = arg0.x;  
                    p.y = arg0.y;  

                    
                    ChangeColor.this.redraw();
                    break;  
                case SWT.MouseMove:  
                    if (p.x == -1) {  
                        break;  
                    }  
                    Point point = toDisplay(arg0.x, arg0.y);  
                    setLocation(point.x - p.x, point.y - p.y);  
                    break;  
                case SWT.MouseUp:  
                    p.x = -1;  
                    p.y = -1;  
                    break;  
          
                default:  
                    break;  
            }  
        }
    }
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			ChangeColor shell = new ChangeColor(display);
			shell.open();
			//shell.setAlpha(1000);
			shell.layout();

			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	/**
	 * @param display
	 */
	/**
	 * @param display
	 */
	public ChangeColor(Display display) {
		super(display, SWT.SHEET);
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.setBounds(60, 137, 72, 22);
		btnNewButton.setText("New Button");
		btnNewButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
                color1 = new Color(getDisplay(), 100,200,0);
                redraw();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		UIButton btnNewButton_1 = new UIButton(this, SWT.NONE);
		btnNewButton_1.setBounds(149, 137, 72, 22);
		btnNewButton_1.setText("New Button");
		btnNewButton_1.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
                color1 = new Color(getDisplay(), 150,120,110);
                redraw();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		
		createContents();
		Listener listener =new ShellMoveListenter();
		addListener(SWT.MouseDown, listener);
	//	addListener(SWT.MouseMove, listener);
		addListener(SWT.MouseUp, listener);
		color1 = new Color(getDisplay(),120,100,233);
		color2 = new Color(getDisplay(), 255,255,255);
		this.setDragDetect(true);
	
		addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				// TODO Auto-generated method stub
				GC gc = e.gc;
				Rectangle rect  = new Rectangle(0, 0,getSize().x ,getSize().y);
				
				
				gc.setBackground(color1);
				gc.fillRectangle(rect);
				
				gc.setForeground(color1);
				gc.setBackground(color2);
			
				//color1.dispose();
				//color2.dispose();
				
				gc.fillGradientRectangle(0, 60, getSize().x, getSize().y, true);
//				
				gc.dispose();
			
			}
		});
		
	}


	

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
