package mx.heroesofanzu.game.sprites.enemies;

import com.badlogic.gdx.math.Vector2;

import mx.heroesofanzu.game.screens.PlayScreen;
import mx.heroesofanzu.game.sprites.Entity;

/**
 * Created by jesusmartinez on 12/04/16.
 */
public class Ooze extends Entity {

	public Ooze(PlayScreen screen, float x, float y){
		super(screen, x, y);
	}

	@Override
	public void update(float delta) {
		super.update(delta);

		float px = getScreen().getPlayer().getXPosition();
		float py = getScreen().getPlayer().getYPosition();

		if( px < getX() ) {
			getBody().applyLinearImpulse(new Vector2(-0.37f, 0), getBody().getWorldCenter(), true);
		} else {
			getBody().applyLinearImpulse(new Vector2(0.37f, 0), getBody().getWorldCenter(), true);
		}

		if( py < getY() ) {
			getBody().applyLinearImpulse(new Vector2(0, -0.37f), getBody().getWorldCenter(), true);
		} else {
			getBody().applyLinearImpulse(new Vector2(0, 0.37f), getBody().getWorldCenter(), true);
		}
	}
}
