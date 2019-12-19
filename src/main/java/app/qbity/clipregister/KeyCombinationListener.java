package app.qbity.clipregister;

import com.yildizkabaran.util.log.ILogger;
import com.yildizkabaran.util.log.LogUtil;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyCombinationListener implements NativeKeyListener {

	private static final ILogger LOGGER = LogUtil.getLogger(KeyCombinationListener.class);

	private final List<KeyCombination> combinations = new ArrayList<>();

	public void listenFor(KeyCombination combination) {
		this.combinations.add(combination);
	}

	public void unlistenFor(KeyCombination combination) {
		this.combinations.remove(combination);
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		int keyCode = e.getKeyCode();
		LOGGER.debug("pressed {0}", NativeKeyEvent.getKeyText(keyCode));
		boolean handled = false;
		for (KeyCombination combination : combinations) {
			handled = handled || combination.onKeyDown(keyCode);
		}
		if (handled) {
			this.consumeEvent(e);
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		int keyCode = e.getKeyCode();
		LOGGER.debug("released {0}", NativeKeyEvent.getKeyText(keyCode));
		boolean handled = false;
		for (KeyCombination combination : combinations) {
			handled = handled || combination.onKeyUp(keyCode);
		}
		if (handled) {
			this.consumeEvent(e);
		}
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {

	}

	private void consumeEvent(NativeKeyEvent e) {
		try {
			Field f = NativeInputEvent.class.getDeclaredField("reserved");
			f.setAccessible(true);
			f.setShort(e, (short) 0x01);

			LOGGER.debug("consumed {0}", NativeKeyEvent.getKeyText(e.getKeyCode()));
		} catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException ex) {
			LOGGER.error(ex);
		}
	}

}
