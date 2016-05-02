package mx.heroesofanzu.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
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

	private Body body;

	public Entity(PlayScreen screen, float x, float y) {
		this.screen = screen;
		this.world = screen.getWorld();
		this.batch = screen.getGame().getBatch();
		setPosition(x, y);

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

		defineEnemy();
	}

	protected void defineEnemy() {
		spriteSheet = new Texture("enemies/ooze.png");
		textureRegion = new TextureRegion(spriteSheet, 372, 80);

		TextureRegion[][] splited = textureRegion.split(73, 80);
		TextureRegion[] frames = new TextureRegion[5];

		for(int i=0; i < 5; i++)
			frames[i] = splited[0][i];

		enemyAnimation = new Animation(0.1f, frames);
	}

	protected void update(float delta) {
		duration += delta;
		TextureRegion frame = enemyAnimation.getKeyFrame(duration, true);
		batch.begin();
		batch.draw(frame, getX(), getY(), 24, 25);
		batch.end();
	}

	/**
	 * @return Return the Box2d body.
	 */
	public Body getBody() {
		return body;
	}

}
