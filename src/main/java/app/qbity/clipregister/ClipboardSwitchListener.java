package app.qbity.clipregister;

import com.yildizkabaran.util.log.ILogger;
import com.yildizkabaran.util.log.LogUtil;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class ClipboardSwitchListener {

	private static final ILogger LOGGER = LogUtil.getLogger(ClipboardSwitchListener.class);
	private static final int NUM_REGISTERS = 4;

	private int currSelection = 0;
	private final KeyCombinationListener keyCombinationListener;
	private final TrayIcon trayIcon;
	private final Clipboard clipboard;
	private final Transferable[] registers;

	public ClipboardSwitchListener() {
		this.keyCombinationListener = new KeyCombinationListener();

		Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
		this.trayIcon = new TrayIcon(image, "ClipRegister");
		this.trayIcon.setImageAutoSize(true);
		this.trayIcon.setToolTip("Current clipboard number");
		try {
			SystemTray.getSystemTray().add(this.trayIcon);
		} catch (AWTException ex) {
			LOGGER.error(ex);
		}

		this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		this.registers = new Transferable[NUM_REGISTERS];

		for (int i = 0; i < NUM_REGISTERS; i++) {
			int clipNum = i;
			KeyCombination keyCombination = new KeyCombination(new int[]{NativeKeyEvent.VC_CONTROL, NativeKeyEvent.VC_ALT, NativeKeyEvent.VC_SHIFT, NativeKeyEvent.VC_F1 + i}) {
				@Override
				public void onDetected() {
					switchToClipboard(clipNum);
				}
			};
			this.keyCombinationListener.listenFor(keyCombination);
		}
	}

	public NativeKeyListener getNativeKeyListener() {
		return this.keyCombinationListener;
	}

	private void switchToClipboard(int newSelection) {
		int oldSelection = this.currSelection;
		this.currSelection = newSelection;
		this.onChanged(oldSelection, newSelection);
	}

	public void onChanged(int oldSelection, int newSelection) {
		LOGGER.info("onClipboardChanged from {0,number,#} to {1,number,#}", oldSelection, newSelection);
		this.registers[oldSelection] = this.clipboard.getContents(null);
		if (registers[newSelection] != null) {
			this.clipboard.setContents(this.registers[newSelection], null);
		}
		this.trayIcon.displayMessage("Clipboard #" + newSelection, null, TrayIcon.MessageType.INFO);
	}
}
