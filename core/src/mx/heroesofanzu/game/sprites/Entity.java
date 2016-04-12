package mx.heroesofanzu.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

import mx.heroesofanzu.game.screens.PlayScreen;

/**
 * Created by jesusmartinez on 08/04/16.
 */
public abstract class Entity extends Sprite {

	protected World world;
	protected Animation enemyAnimation;
	protected Texture spriteSheet;
	protected TextureRegion textureRegion;

	protected float duration = 0;
	protected SpriteBatch batch;
	protected PlayScreen screen;

	public Entity(PlayScreen screen, float x, float y) {
		this.screen = screen;
		this.world = screen.getWorld();
		this.batch = screen.getGame().getBatch();
		setPosition(x, y);
		defineEnemy();
	}

	protected abstract void defineEnemy();
	public abstract void update(float delta);
}
