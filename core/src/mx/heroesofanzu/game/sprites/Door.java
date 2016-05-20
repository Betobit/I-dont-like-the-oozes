package mx.heroesofanzu.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by jesusmartinez on 20/05/16.
 */
public class Door extends Sprite {

	private boolean opened;

	public Door(float x, float y) {
		super(new Texture("door/door-on.png"));
		opened = false;
		setPosition(x, y);
	}

	@Override
	public void draw(Batch batch) {
		super.draw(batch);
	}
}
