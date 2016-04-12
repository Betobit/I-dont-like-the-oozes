package mx.heroesofanzu.game.sprites.enemies;

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
public abstract class Enemy extends Sprite {

	protected Animation enemyAnimation;
	protected Texture spriteSheet;
	protected TextureRegion textureRegion;

	protected float duration = 0;
	protected SpriteBatch batch;

	public Enemy(PlayScreen screen, float x, float y) {
		this.batch = screen.getBatch();
		setPosition(x, y);
		defineEnemy();
	}

	protected abstract void defineEnemy();
	public abstract void update(float delta);
}
