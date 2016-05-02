package mx.heroesofanzu.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import mx.heroesofanzu.game.screens.PlayScreen;
import mx.heroesofanzu.game.util.DirectionGestureDetector;

/**
 * Created by jesusmartinez on 12/04/16.
 */
public class Player extends Entity {

	private Vector2 direction;

	public Player(PlayScreen screen, float x, float y) {
		super(screen, x, y);
		direction = new Vector2(16f, 0);
	}

	@Override
	protected void defineEnemy() {
		super.defineEnemy();
		setPlayerGestures();
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		setX(getBody().getPosition().x - 12);
		setY(getBody().getPosition().y - 6);
		getBody().setLinearVelocity(direction.x, direction.y);
	}

	/**
	 * @return Return the Y position of the Box2d getBody().
	 */
	public float getXPosition() {
		return getBody().getPosition().x;
	}

	/**
	 * @return Return the X position of the Box2d body.
	 */
	public float getYPosition() {
		return getBody().getPosition().y;
	}

	private void setPlayerGestures() {
		Gdx.input.setInputProcessor(new DirectionGestureDetector(new DirectionGestureDetector.DirectionListener() {

			@Override
			public void onUp() {
				direction.set(0, 50);
			}

			@Override
			public void onRight() {
				direction.set(50, 0);
			}

			@Override
			public void onLeft() {
				direction.set(-50, 0);
			}

			@Override
			public void onDown() {
				direction.set(0, -50);
			}
		}));
	}
}
