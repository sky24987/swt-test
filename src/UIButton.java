import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.DRAWITEMSTRUCT;
import org.eclipse.swt.internal.win32.GRADIENT_RECT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SIZE;
import org.eclipse.swt.internal.win32.TRIVERTEX;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;


public class UIButton extends Button {

	Color selectColor;
	boolean isselect;
	
	public UIButton(Composite parent, int style) {
		super(parent, SWT.PUSH);
		// TODO Auto-generated constructor stub
		selectColor = new Color(getDisplay(), 100,200,1);
	//	unselectColor= new Color(getDisplay(), 200,100,100);
		
		
		addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				// TODO Auto-generated method stub
				GC gc = e.gc;
 
                gc.setLineWidth(1);  
                if(isselect){
                	
                	selectColor = new Color(getDisplay(), 0,100,100);
                }
                	
                
                else{
                	
                	selectColor = new Color(getDisplay(), 100,200,1);
                	
                }
                gc.setBackground(selectColor);  
                gc.fillRoundRectangle(2,2,getSize().x-2,getSize().y-2,7,7);
				gc.dispose();
				isselect=false;
			}
		});
		
		this.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
				isselect = true;
				redraw();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				selectColor = new Color(getDisplay(), 100,200,1);
				redraw();
			}
		});
	}
	
	
	 @Override
	    protected void checkSubclass() {
	        // TODO Auto-generated method stub
	    
	    }
	 
		protected void ownerDraw(DRAWITEMSTRUCT dis) {
			Rectangle rc = new Rectangle(dis.left, dis.top, dis.right - dis.left,
					dis.bottom - dis.top);
			Color clr1 = new Color(getDisplay(), 0, 255, 128);
			Color clr2 = new Color(getDisplay(), 0, 128, 255);
			fillGradientRectangle(dis.hDC, rc, true, clr1, clr2);
			clr1.dispose();
			clr2.dispose();

			SIZE size = new SIZE();
			String text = getText();
			char[] chars = text.toCharArray();
			int oldFont = OS.SelectObject(dis.hDC, getFont().handle);
			OS.GetTextExtentPoint32W(dis.hDC, chars, chars.length, size);
			RECT rcText = new RECT();
			rcText.left = rc.x;
			rcText.top = rc.y;
			rcText.right = rc.x + rc.width;
			rcText.bottom = rc.y + rc.height;
			if ((dis.itemState & OS.ODS_SELECTED) != 0)
				OS.OffsetRect(rcText, 1, 1);
			OS.SetBkMode(dis.hDC, OS.TRANSPARENT);
			OS.DrawTextW(dis.hDC, chars, -1, rcText, OS.DT_SINGLELINE
					| OS.DT_CENTER | OS.DT_VCENTER);
			OS.SelectObject(dis.hDC, oldFont);
		}

		private void fillGradientRectangle(int handle, Rectangle rc,
				boolean vertical, Color clr1, Color clr2) {
			final int hHeap = OS.GetProcessHeap();
			final int pMesh = OS.HeapAlloc(hHeap, OS.HEAP_ZERO_MEMORY,
					GRADIENT_RECT.sizeof + TRIVERTEX.sizeof * 2);
			final int pVertex = pMesh + GRADIENT_RECT.sizeof;

			GRADIENT_RECT gradientRect = new GRADIENT_RECT();
			gradientRect.UpperLeft = 0;
			gradientRect.LowerRight = 1;
			OS.MoveMemory(pMesh, gradientRect, GRADIENT_RECT.sizeof);

			TRIVERTEX trivertex = new TRIVERTEX();
			trivertex.x = rc.x;
			trivertex.y = rc.y;
			trivertex.Red = (short) (clr1.getRed() << 8);
			trivertex.Green = (short) (clr1.getGreen() << 8);
			trivertex.Blue = (short) (clr1.getBlue() << 8);
			trivertex.Alpha = -1;
			OS.MoveMemory(pVertex, trivertex, TRIVERTEX.sizeof);

			trivertex.x = rc.x + rc.width;
			trivertex.y = rc.y + rc.height;
			trivertex.Red = (short) (clr2.getRed() << 8);
			trivertex.Green = (short) (clr2.getGreen() << 8);
			trivertex.Blue = (short) (clr2.getBlue() << 8);
			trivertex.Alpha = -1;
			OS.MoveMemory(pVertex + TRIVERTEX.sizeof, trivertex, TRIVERTEX.sizeof);

			boolean success = OS.GradientFill(handle, pVertex, 2, pMesh, 1,
					vertical ? OS.GRADIENT_FILL_RECT_V : OS.GRADIENT_FILL_RECT_H);
			OS.HeapFree(hHeap, 0, pMesh);

			if (success)
				return;
		}

}
