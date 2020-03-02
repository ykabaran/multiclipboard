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

public class Main {

	private static final ILogger LOGGER = LogUtil.getLogger(Main.class);

	public static void main(String[] args) {
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.WARNING);
		logger.setUseParentHandlers(false);

		ClipboardSwitchListener clipboardSwitchListener = new ClipboardSwitchListener();
		try {
			GlobalScreen.setEventDispatcher(new SynchronousExecutorService());
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			LOGGER.error(ex);
			System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(clipboardSwitchListener.getNativeKeyListener());
		LOGGER.info("registered listener");
	}

}
