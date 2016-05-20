package mx.heroesofanzu.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by jesusmartinez on 20/05/16.
 */
public class Door extends Sprite {

	private boolean open;

	public Door(float x, float y) {
		super(new Texture("door/door-off.png"));
		open = false;
		setPosition(x, y);
		setSize(13, 22);
	}

	public boolean isOpen() {
		return open;
	}

	/**
	 * Open door and change texture
	 */
	public void open() {
		if(!isOpen()) {
			getTexture().dispose();
			setTexture(new Texture("door/door-on.png"));
			open = true;
		}
	}

	@Override
	public void draw(Batch batch) {
		super.draw(batch);
	}
}
