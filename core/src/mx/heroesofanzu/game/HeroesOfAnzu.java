package mx.heroesofanzu.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import mx.heroesofanzu.game.Screens.MainScreen;

public class HeroesOfAnzu extends Game {
	private SpriteBatch batch;

	public SpriteBatch getBatch() {
		return batch;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MainScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
	}
}
