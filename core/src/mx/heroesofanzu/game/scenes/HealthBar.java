package mx.heroesofanzu.game.scenes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by jesusmartinez on 20/05/16.
 */
public class HealthBar extends Actor {

	private Sprite loadingBarForeground;
	private Sprite loadingBar;
	private float currentHP;

	public HealthBar() {
		Texture foreground = new Texture("lifebar/healthbar_foreground.png");
		Texture bar = new Texture("lifebar/healthbar_power.png");
		currentHP = 1.0f;

		loadingBarForeground = new Sprite(foreground);
		loadingBar = new Sprite(bar);

		loadingBarForeground.setSize(95, 30);
		loadingBar.setSize(95, 30);
	}

	/**
	 * Reduce currentHP
	 * @param damage
	 */
	public void healthReduction(float damage) {
		if (currentHP > 0) {
			currentHP -= damage;
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		loadingBar.setSize(95*currentHP, 30);
		loadingBar.draw(batch);
		loadingBarForeground.draw(batch);
	}
}