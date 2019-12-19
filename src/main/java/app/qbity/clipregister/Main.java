package app.qbity.clipregister;

import com.yildizkabaran.util.log.ILogger;
import com.yildizkabaran.util.log.LogUtil;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;

public class Main {

	private static final ILogger LOGGER = LogUtil.getLogger(Main.class);

	public static void main(String[] args) {
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.WARNING);
		logger.setUseParentHandlers(false);

		try {
			GlobalScreen.setEventDispatcher(new SynchronousExecutorService());
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			LOGGER.error(ex);
			System.exit(1);
		}

		KeyCombinationListener listener = new KeyCombinationListener();

		SystemTray tray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
		TrayIcon trayIcon = new TrayIcon(image, "ClipRegister");
		trayIcon.setImageAutoSize(true);
		trayIcon.setToolTip("Current clipboard number");
		try {
			tray.add(trayIcon);
		} catch (AWTException ex) {
			LOGGER.error(ex);
		}

		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable[] registers = new Transferable[10];
		listener.listenFor(new ClipboardSwitchListener() {
			@Override
			public void onClipboardChanged(int oldSelection, int newSelection) {
				LOGGER.info("onClipboardChanged from {0,number,#} to {1,number,#}", oldSelection, newSelection);
				registers[oldSelection] = clipboard.getContents(null);
				if (registers[newSelection] != null) {
					clipboard.setContents(registers[newSelection], null);
				}
				trayIcon.displayMessage("Clipboard #" + newSelection, null, MessageType.INFO);
			}
		});

//		listener.listenFor(new KeyCombination(new int[]{NativeKeyEvent.VC_ESCAPE}) {
//			@Override
//			public void onDetected() {
//				LOGGER.info("escape detected");
//				System.exit(0);
//			}
//		});

		GlobalScreen.addNativeKeyListener(listener);
		LOGGER.info("registered listener");
	}

}
