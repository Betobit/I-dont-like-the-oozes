package mx.heroesofanzu.game.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by jesusmartinez on 16/05/16.
 */
public class PowerUp extends Sprite {

	public PowerUp(String name, float x, float y, int width, int height) {
		super(new Texture("powerups/" + name));
		setSize(width, height);
		setPosition(x, y);
	}

	public void fadeIn() {

	}

	public void fadeOut() {

	}
}
