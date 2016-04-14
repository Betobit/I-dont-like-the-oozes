package mx.heroesofanzu.game.sprites.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import mx.heroesofanzu.game.screens.PlayScreen;
import mx.heroesofanzu.game.sprites.Entity;

/**
 * Created by jesusmartinez on 12/04/16.
 */
public class Ooze extends Entity {

	private Body body;

	public Ooze(PlayScreen screen, float x, float y){
		super(screen, x, y);
	}

	@Override
	protected void defineEnemy() {
		spriteSheet = new Texture("enemies/ooze.png");
		textureRegion = new TextureRegion(spriteSheet, 372, 80);

		TextureRegion[][] splited = textureRegion.split(73, 80);
		TextureRegion[] frames = new TextureRegion[5];

		for(int i=0; i < 5; i++)
			frames[i] = splited[0][i];

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

		enemyAnimation = new Animation(0.1f, frames);
	}

	@Override
	public void update(float delta) {
		duration += delta;
		TextureRegion frame = enemyAnimation.getKeyFrame(duration, true);

		float px = screen.getPlayer().getXPosition();
		float py = screen.getPlayer().getYPosition();

		if( px < getX() ) {
			body.applyLinearImpulse(new Vector2(-0.37f, 0), body.getWorldCenter(), true);
		} else {
			body.applyLinearImpulse(new Vector2(0.37f, 0), body.getWorldCenter(), true);
		}

		if( py < getY() ) {
			body.applyLinearImpulse(new Vector2(0, -0.37f), body.getWorldCenter(), true);
		} else {
			body.applyLinearImpulse(new Vector2(0, 0.37f), body.getWorldCenter(), true);
		}

		setX(body.getPosition().x - 12);
		setY(body.getPosition().y - 6);
		batch.begin();
		batch.draw(frame, getX(), getY(), 24, 25);
		batch.end();
	}
}
