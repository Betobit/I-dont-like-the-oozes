package mx.heroesofanzu.game.sprites.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

		float px = screen.getPlayer().getXPosition();
		float py = screen.getPlayer().getYPosition();

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

		setX(getBody().getPosition().x - 12);
		setY(getBody().getPosition().y - 6);
	}
}
