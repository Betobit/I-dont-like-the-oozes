package mx.heroesofanzu.game.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.media.jfxmediaimpl.MediaDisposer;

import mx.heroesofanzu.game.sprites.powerups.PowerUp;

/**
 * Created by jesusmartinez on 10/05/16.
 * Modified by carlossuarez on 5/05/16.
 * Represents the Hud of the game by all the relevant information
 */
public class Hud implements MediaDisposer.Disposable {

	public static final int PADDING = 10;
	private Stage stage;
	private Table table;

	private Label scoreLabel;
	private int score;
	private SpriteBatch batch;

	private Sprite bubble;
	private PowerUp powerUp;

	private HealthBar healthBar;

	public Hud(Viewport viewport, SpriteBatch batch) {
		this.batch = batch;
		score = 0;
		healthBar = new HealthBar();

		drawPowerUpBubble();

		stage = new Stage(viewport);
		table = new Table();
		table.top();
		table.setFillParent(true);

		scoreLabel = new Label(String.format("Score: %04d ", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreLabel.setFontScale(0.5f);
		table.add(healthBar).pad(PADDING).padTop(35).expandX();
		table.add(scoreLabel).right().pad(PADDING).padRight(50);

		stage.addActor(table);
	}

	/**
	 * Draw power up container
	 */
	private void drawPowerUpBubble() {
		Texture texture = new Texture("bubble.png");
		bubble = new Sprite(texture);
		bubble.setSize(40, 40);
		bubble.setPosition(350, 195);

		// Default power up
		powerUp = new PowerUp(null, "velocity.png", false);
	}

	/**
	 * Set th bubble power up
	 */
	public void setPowerUp(PowerUp p) {
		powerUp = p;
		powerUp.setSize(25, 25);
		powerUp.setPosition(bubble.getX() + bubble.getWidth() / 6, bubble.getY() + bubble.getHeight() / 6);
	}

	/**
	 * @return Get health bar.
	 */
	public HealthBar getHealthBar() {
		return healthBar;
	}

	/**
	 * @return Get actual power up
	 */
	public PowerUp getPowerUp() {
		return powerUp;
	}

	/**
	 * Add one to the score
	 */
	public void tickScore() {
		score++;
	}

    /**
     * Update timeCount and score.
     */
	public void update(float dt){
		scoreLabel.setText(String.format("Score: %04d ", score));

		stage.draw();
		batch.begin();
		if(powerUp.isActive()) {
			powerUp.sumCounter(dt);
			powerUp.enable();
			powerUp.draw(batch);
		} else {
			powerUp.disable();
		}
		healthBar.draw(batch, 1.0f);
		bubble.draw(batch);
		batch.end();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
