
import org.eclipse.swt.internal.win32.DRAWITEMSTRUCT;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public abstract class OwnerDrawButton extends Button {
	public OwnerDrawButton(Composite parent, int style) {
		super(parent, style);
		int osStyle = OS.GetWindowLong(handle, OS.GWL_STYLE);
		osStyle |= OS.BS_OWNERDRAW;
		OS.SetWindowLong(handle, OS.GWL_STYLE, osStyle);
	}

	

	protected abstract void ownerDraw(DRAWITEMSTRUCT dis);
}