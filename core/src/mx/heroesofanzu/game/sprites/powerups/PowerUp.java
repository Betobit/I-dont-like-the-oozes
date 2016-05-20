package mx.heroesofanzu.game.sprites.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import mx.heroesofanzu.game.sprites.Player;

/**
 * Created by jesusmartinez on 16/05/16.
 */
public class PowerUp extends Sprite {

	private boolean active;
	private int timeLimit;
	private float counter;
	private Player player;

	public PowerUp(Player player, String name, boolean active) {
		super(new Texture("powerups/" + name));
		this.active = active;
		this.player = player;
		timeLimit = 5;
		counter = 0f;
	}

	/**
	 * Apply power up
	 */
	public void enable() {
		if(isActive() && player != null) {
			player.setVelocity(25);
			player.applyVector();
		}
	}

	/**
	 * Disable power up
	 */
	public void disable() {
		if(player != null) {
			player.setVelocity(0);
			player.applyVector();
		}
	}
	/**
	 * Sum delta time to the counter.
	 * @return delta Delta time
	 */
	public void sumCounter(float delta) {
		counter += delta;
	}

	/**
	 * @return Get the status of the power up.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Change the status of the power up.
	 * @param active The new status
	 */
	private void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public void draw(Batch batch) {
		super.draw(batch);
		if(counter >= timeLimit) {
			setActive(false);
			counter = 0;
		}
	}

}
