package mx.heroesofanzu.game.sprites.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import mx.heroesofanzu.game.screens.PlayScreen;
import mx.heroesofanzu.game.sprites.MySprite;

/**
 * Created by jesusmartinez on 29/03/16.
 */
public class Player extends MySprite {

	private Body body;
	private Animation walkAnimation;
	private float duration;

	public Player(PlayScreen screen, float x, float y) {
		super(screen, x, y);
		body = getBody();
		defineSprite();

		// Get frames and set animation.
		Texture dragon = new Texture("dragon.png");
		TextureRegion dragonRegion = new TextureRegion(dragon, 376, 68);
		TextureRegion[][] splited = dragonRegion.split(94, 68);
		TextureRegion[] frames = new TextureRegion[4];
		for(int i=0; i < 4; i++)
			frames[i] = splited[0][i];

		walkAnimation = new Animation(0.08f, frames);
	}

	public void update(float delta) {
		duration += delta;
		/*getScreen().foo().begin();
		getScreen().foo().draw(walkAnimation.getKeyFrame(duration, true), 300, 300);
		getScreen().foo().end();*/
	}

	public TextureRegion getFrame() {
		return walkAnimation.getKeyFrame(duration, true);
	}

	@Override
	protected void defineSprite() {
		CircleShape circle = new CircleShape();
		circle.setRadius(10);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = circle;
		body.createFixture(fdef);
		body.setActive(true);
	}

}
