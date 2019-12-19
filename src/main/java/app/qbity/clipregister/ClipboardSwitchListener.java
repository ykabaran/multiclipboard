package app.qbity.clipregister;

import com.yildizkabaran.util.log.ILogger;
import com.yildizkabaran.util.log.LogUtil;
import org.jnativehook.keyboard.NativeKeyEvent;

public class ClipboardSwitchListener extends KeyCombination {

	private static final ILogger LOGGER = LogUtil.getLogger(ClipboardSwitchListener.class);

	private boolean isPrimed = false;
	private int currSelection = 1;

	public ClipboardSwitchListener() {
		super(new int[]{NativeKeyEvent.VC_CONTROL, NativeKeyEvent.VC_ALT, NativeKeyEvent.VC_C});
	}

	@Override
	public boolean onKeyDown(int keyCode) {
		boolean handled = super.onKeyDown(keyCode);
		if (handled) {
			return handled;
		}
		if (!this.isPrimed) {
			return handled;
		}
		this.isPrimed = false;

		int newSelection = this.currSelection;
		switch (keyCode) {
			case NativeKeyEvent.VC_0:
				newSelection = 0;
				break;
			case NativeKeyEvent.VC_1:
				newSelection = 1;
				break;
			case NativeKeyEvent.VC_2:
				newSelection = 2;
				break;
			case NativeKeyEvent.VC_3:
				newSelection = 3;
				break;
			case NativeKeyEvent.VC_4:
				newSelection = 4;
				break;
			case NativeKeyEvent.VC_5:
				newSelection = 5;
				break;
			case NativeKeyEvent.VC_6:
				newSelection = 6;
				break;
			case NativeKeyEvent.VC_7:
				newSelection = 7;
				break;
			case NativeKeyEvent.VC_8:
				newSelection = 8;
				break;
			case NativeKeyEvent.VC_9:
				newSelection = 9;
				break;
		}

		if (newSelection == this.currSelection) {
			return handled;
		}

		int oldSelection = this.currSelection;
		this.currSelection = newSelection;
		this.onClipboardChanged(oldSelection, newSelection);
		return true;
	}

	@Override
	public void onDetected() {
		this.isPrimed = true;
		LOGGER.info("primed");
	}

	public void onClipboardChanged(int oldSelection, int newSelection) {

	}
}
