package mx.heroesofanzu.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import mx.heroesofanzu.game.Screens.MainScreen;

/*
 * The main class game that delegates to one or many Screens.
 */

public class HeroesOfAnzu extends Game {

	private SpriteBatch batch;
	private static int screenWidth;
	private static int screenHeight;

	/*
	*  Return the current common sprite batch.
	*/
	public SpriteBatch getBatch() {
		return batch;
	}

	/*
	* Return the screen width.
	*/
	public static int getScreenWidth() {
		return screenWidth;
	}

	/*
	* Return the screen height.
	*/
	public static int getScreenHeight() {
		return screenHeight;
	}

	/*
	* Get the common resources used by all the game, such as sounds, spritebach and atlas.
	 */
	@Override
	public void create() {
		batch = new SpriteBatch();
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
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
