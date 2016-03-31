package mx.heroesofanzu.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import mx.heroesofanzu.game.screens.PlayScreen;

/**
 * Created by jesusmartinez on 29/03/16.
 */
public abstract class MySprite extends Sprite {

	private World world;
	private PlayScreen screen;
	private Body body;
	private Vector2 velocity;
	protected Array<TextureRegion> frames;

	public MySprite(PlayScreen screen, float x, float y) {
		this.screen = screen;
		this.world = screen.getWorld();
		frames = new Array<TextureRegion>();
		setPosition(x, y);
		velocity = new Vector2(-1, 2);

		BodyDef bdef = new BodyDef();
		bdef.position.set(getX(), getY());
		bdef.type = BodyDef.BodyType.DynamicBody;
		body = world.createBody(bdef);
	}

	public Body getBody() {
		return body;
	}

	protected PlayScreen getScreen(){
		return screen;
	}

	protected void defineSprite() {
	}
}
