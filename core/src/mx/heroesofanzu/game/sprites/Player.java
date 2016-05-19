package mx.heroesofanzu.game.sprites;

import com.badlogic.gdx.Gdx;
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
	
    private Vector2 direction;
	private int velocity;

	public Player(PlayScreen screen, float x, float y) {
		super(screen, x, y);
		direction = new Vector2(16f, 0);
		velocity = 0;
	}

	/**
	 * More velocity
	 * @param velocity Velocity added
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
	 * @return Return the X position of the Box2d body.
	 */
	public float getYPosition() {
		return getBody().getPosition().y;
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		getBody().setLinearVelocity(direction.x, direction.y);

	}

	private void setPlayerGestures() {
		Gdx.input.setInputProcessor(new DirectionGestureDetector(new DirectionGestureDetector.DirectionListener() {
			@Override
			public void onUp() {
				direction.set(0, 50 + velocity);
			}

			@Override
			public void onRight() {
				direction.set(50 + velocity, 0);
			}

			@Override
			public void onLeft() {
				direction.set(-50 - velocity, 0);
			}

			@Override
			public void onDown() {
				direction.set(0, -50 - velocity);
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
			setBodyAnimation(new Animation(0.06f, frames));
		}
        setPlayerGestures();
	}
}
