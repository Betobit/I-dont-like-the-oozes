package mx.heroesofanzu.game.sprites.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import mx.heroesofanzu.game.screens.PlayScreen;

/**
 * Created by jesusmartinez on 12/04/16.
 */
public class Ooze extends Enemy {

	public Ooze(PlayScreen screen){
		super(screen, 110, 40);
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

		batch.begin();
		batch.draw(frame, getX(), getY(), 24, 25);
		batch.end();
	}
}
