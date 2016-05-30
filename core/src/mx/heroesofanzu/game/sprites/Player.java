package mx.heroesofanzu.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import mx.heroesofanzu.game.screens.PlayScreen;
import mx.heroesofanzu.game.util.DirectionGestureDetector;

/**
 * Created by jesusmartinez on 12/04/16.
 * Modificated by carlossuarez 9/5/16
 */
public class Player extends Entity {
	
    private Vector2 vector;
	private int velocity;
	private DIRECTION currentDirection;
	private enum DIRECTION {
		NONE,
		UP,
		DOWN,
		LEFT,
		RIGHT
	}

	public Player(PlayScreen screen, float x, float y) {
		super(screen, x, y);
		vector = new Vector2(0, 0);
		currentDirection = DIRECTION.NONE;
		velocity = 0;
	}

	/**
	 * Input handler for desktop tests.
	 */
	private void inputHandler() {
		if( Gdx.input.isKeyPressed(Input.Keys.RIGHT) ) {
			vector.set(50 + velocity, 0);
			currentDirection = DIRECTION.RIGHT;
		} else if( Gdx.input.isKeyPressed(Input.Keys.LEFT) ) {
			vector.set(-50 - velocity, 0);
			currentDirection = DIRECTION.LEFT;
		} else if( Gdx.input.isKeyPressed(Input.Keys.UP) ) {
			vector.set(0, 50 + velocity);
			currentDirection = DIRECTION.UP;
		} else if( Gdx.input.isKeyPressed(Input.Keys.DOWN) ) {
			vector.set(0, -50 - velocity);
			currentDirection = DIRECTION.DOWN;
		}
	}

	/**
	 * Set velocity
	 * @param velocity New Velocity
	 */
	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

	/**
	 * @return Return the Y position of the Box2d getBody().
	 */
	public float getXPosition() {
		return getBody().getPosition().x;
	}

	/**
	 * Apply the vector to the body using the velocity
	 */
	public void applyVector() {
		switch (currentDirection) {
			case UP:
				vector.set(0, 50 + velocity);
			break;

			case DOWN:
				vector.set(0, -50 - velocity);
			break;

			case LEFT:
				vector.set(-50 - velocity, 0);
			break;

			case RIGHT:
				vector.set(50 + velocity, 0);
			break;
		}
	}

	/**
	 * @return Return the X position of the Box2d body.
	 */
	public float getYPosition() {
		return getBody().getPosition().y;
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		getBody().setLinearVelocity(vector.x, vector.y);
		inputHandler();
	}

	private void setPlayerGestures() {
		Gdx.input.setInputProcessor(new DirectionGestureDetector(new DirectionGestureDetector.DirectionListener() {
			@Override
			public void onUp() {
				vector.set(0, 50 + velocity);
				currentDirection = DIRECTION.UP;
			}

			@Override
			public void onRight() {
				vector.set(50 + velocity, 0);
				currentDirection = DIRECTION.RIGHT;
			}

			@Override
			public void onLeft() {
				vector.set(-50 - velocity, 0);
				currentDirection = DIRECTION.LEFT;
			}

			@Override
			public void onDown() {
				vector.set(0, -50 - velocity);
				currentDirection = DIRECTION.DOWN;
			}
		}));
	}

	@Override
	protected void defineSpriteSheet() {
		TextureRegion[] frames = new TextureRegion[6];
		for(int i = 0; i < 6; i++) {
			setSpriteSheet(new Texture("player/player_" + (i + 1) + ".png"));
			setTextureRegion(new TextureRegion(getSpriteSheet(), 70, 70));
			frames[i] = getTextureRegion();
			setAnimation(new Animation(0.06f, frames));
		}
        setPlayerGestures();
	}
}
