package mx.heroesofanzu.game.sprites;

import com.badlogic.gdx.Gdx;
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

	private World world;

	private Animation bodyAnimation;
	private Texture spriteSheet;
	private TextureRegion textureRegion;

	private float duration = 0;
	private SpriteBatch batch;
	private PlayScreen screen;
	private Body body;

	public Entity(PlayScreen screen, float x, float y) {
		this.screen = screen;
		this.world = screen.getWorld();
		this.batch = screen.getGame().getBatch();
		setPosition(x, y);
		setSize(24, 25);

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

		defineBody();
	}

	/**
	 * @return Return PlayScreen.
	 */
	public PlayScreen getScreen() {
		return screen;
	}

	/**
	 * Get the sprite sheet of the entity, split it and create the animation.
	 */
    	protected abstract void defineBody();

	/**
	 * Get the frame of the animation and draw it.
	 * @param delta Delta time
	 */
	protected void update(float delta) {
		duration += delta;
		TextureRegion frame = bodyAnimation.getKeyFrame(duration, true);

		setX(getBody().getPosition().x);
		setY(getBody().getPosition().y);
		batch.begin();
		batch.draw(frame, getX() - 12, getY() - 6, 24, 25);
		batch.end();
	}

	/**
	 * @return Return the Box2d body.
	 */
	public Body getBody() {
		return body;
	}
	/**
	 * @return Return the Animation.
	 */
	public Animation getBodyAnimation() {
        	return bodyAnimation;
    	}

    	public void setBodyAnimation(Animation bodyAnimation) {
        	this.bodyAnimation = bodyAnimation;
    	}
	
	/**
	 * @return Return the Texture.
	 */
    	public Texture getSpriteSheet() {
        	return spriteSheet;
    	}

    	public void setSpriteSheet(Texture spriteSheet) {
        	this.spriteSheet = spriteSheet;
	}
	
	/**
	 * @return Return the TextureRegion.
	 */
	public TextureRegion getTextureRegion() {
	        return textureRegion;
    	}

	public void setTextureRegion(TextureRegion textureRegion) {
	        this.textureRegion = textureRegion;
	 }

}
