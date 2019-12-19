package app.qbity.clipregister;

public abstract class KeyCombination {

	private final int[] keyCodes;
	private final int numKeys;
	private final boolean[] pressed;

	public KeyCombination(int[] keyCodes) {
		this.keyCodes = keyCodes;
		this.numKeys = this.keyCodes.length;
		this.pressed = new boolean[this.numKeys];
	}

	public boolean onKeyDown(int keyCode) {
		for (int i = 0; i < this.numKeys; i++) {
			if (this.keyCodes[i] == keyCode) {
				this.pressed[i] = true;
			}
		}

		for (int i = 0; i < this.numKeys; i++) {
			if (!this.pressed[i]) {
				return false;
			}
		}
		this.onDetected();
		return true;
	}

	public boolean onKeyUp(int keyCode) {
		for (int i = 0; i < this.numKeys; i++) {
			if (this.keyCodes[i] == keyCode) {
				this.pressed[i] = false;
			}
		}
		return false;
	}

	public abstract void onDetected();

}
