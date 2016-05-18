package mx.heroesofanzu.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import mx.heroesofanzu.game.screens.MainScreen;

/**
 * The main class game that delegates to one or many Screens.
 */

public class HeroesOfAnzu extends Game {

	private SpriteBatch batch;

	/**
	 * Return the current common sprite batch.
	 */
	public SpriteBatch getBatch() {
		return batch;
	}

	/**
	 * Get the common resources used by all the game, such as
	 * sounds, sprite batch and atlas.
	 */
	@Override
	public void create() {
		batch = new SpriteBatch();
		setScreen(new MainScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
	}
}
