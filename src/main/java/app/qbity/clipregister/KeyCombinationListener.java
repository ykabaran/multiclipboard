package app.qbity.clipregister;

import com.yildizkabaran.util.log.ILogger;
import com.yildizkabaran.util.log.LogUtil;
import java.util.ArrayList;
import java.util.List;
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
		LOGGER.debug("pressed {0,number,#}:{1}", keyCode, NativeKeyEvent.getKeyText(keyCode));
		this.combinations.forEach((combination) -> {
			combination.onKeyDown(keyCode);
		});
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		int keyCode = e.getKeyCode();
		LOGGER.debug("released {0,number,#}:{1}", keyCode, NativeKeyEvent.getKeyText(keyCode));
		this.combinations.forEach((combination) -> {
			combination.onKeyUp(keyCode);
		});
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {

	}

}
