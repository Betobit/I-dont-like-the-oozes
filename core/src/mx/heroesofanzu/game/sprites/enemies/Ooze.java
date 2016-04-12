package mx.heroesofanzu.game.sprites.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
	protected void defineEnemy() {
		spriteSheet = new Texture("enemies/ooze.png");
		textureRegion = new TextureRegion(spriteSheet, 372, 80);

		TextureRegion[][] splited = textureRegion.split(73, 80);
		TextureRegion[] frames = new TextureRegion[5];

		for(int i=0; i < 5; i++)
			frames[i] = splited[0][i];

		enemyAnimation = new Animation(0.1f, frames);
	}

	@Override
	public void update(float delta) {
		duration += delta;
		TextureRegion frame = enemyAnimation.getKeyFrame(duration, true);

		float px = screen.getPlayer().getXPosition();
		float py = screen.getPlayer().getYPosition();

		if( px < getX() )
			setX(getX()-0.17f);
		else
			setX(getX()+0.17f);

		if( py < getY() )
			setY(getY()-0.17f);
		else
			setY(getY()+0.17f);

		batch.begin();
		batch.draw(frame, getX(), getY(), 24, 25);
		batch.end();
	}
}
