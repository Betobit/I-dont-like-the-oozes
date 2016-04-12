package mx.heroesofanzu.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import mx.heroesofanzu.game.screens.PlayScreen;
import mx.heroesofanzu.game.util.DirectionGestureDetector;

/**
 * Created by jesusmartinez on 12/04/16.
 */
public class Player extends Entity{

	private Body body;

	public Player(PlayScreen screen, float x, float y) {
		super(screen, x, y);
	}

	@Override
	protected void defineEnemy() {
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		bdef.type = BodyDef.BodyType.DynamicBody;
		bdef.position.set(getX(), getY());
		body = world.createBody(bdef);

		CircleShape circle = new CircleShape();
		circle.setRadius(5.8f);
		fdef.shape = circle;
		fdef.density = 0f;
		fdef.restitution = 0f;
		body.createFixture(fdef);

		setPlayerGestures();
	}

	@Override
	public void update(float delta) {
		inputHandler();
	}

	/**
	 * @return Return the Box2d body.
	 */
	public Body getBody() {
		return body;
	}

	/**
	 * @return Return the Y position of the Box2d body.
	 */
	public float getXPosition() {
		return body.getPosition().x;
	}

	/**
	 * @return Return the X position of the Box2d body.
	 */
	public float getYPosition() {
		return body.getPosition().y;
	}

	/**
	 * Input handler for desktop tests.
	 */
	private void inputHandler() {
		if( Gdx.input.isKeyPressed(Input.Keys.RIGHT) )
			body.applyLinearImpulse(new Vector2(16f, 0), body.getWorldCenter(), true);
		else if( Gdx.input.isKeyPressed(Input.Keys.LEFT) )
			body.applyLinearImpulse(new Vector2(-16f, 0), body.getWorldCenter(), true);
		else if( Gdx.input.isKeyPressed(Input.Keys.UP) )
			body.applyLinearImpulse(new Vector2(0, 16f), body.getWorldCenter(), true);
		else if( Gdx.input.isKeyPressed(Input.Keys.DOWN) )
			body.applyLinearImpulse(new Vector2(0, -16f), body.getWorldCenter(), true);
	}

	private void setPlayerGestures(){
		Gdx.input.setInputProcessor(new DirectionGestureDetector(new DirectionGestureDetector.DirectionListener() {

			@Override
			public void onUp() {
				body.applyLinearImpulse(new Vector2(0, 4f), body.getWorldCenter(), true);
			}

			@Override
			public void onRight() {
				body.applyLinearImpulse(new Vector2(4f, 0), body.getWorldCenter(), true);
			}

			@Override
			public void onLeft() {
				body.applyLinearImpulse(new Vector2(-4f, 0), body.getWorldCenter(), true);
			}

			@Override
			public void onDown() {
				body.applyLinearImpulse(new Vector2(0, -4f), body.getWorldCenter(), true);
			}
		}));
	}
}
