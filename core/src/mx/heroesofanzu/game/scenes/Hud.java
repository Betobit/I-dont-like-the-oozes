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

import mx.heroesofanzu.game.screens.PlayScreen;

/**
 * Created by jesusmartinez on 10/05/16.
 * Modified by carlossuarez on 5/05/16.
 * Represents the Hud of the game by all the relevant information
 */
public class Hud implements MediaDisposer.Disposable {

	public static final int PADDING = 10;
	private Stage stage;
	private Table table;

	private Label timeLabel;
	private Label scoreLabel;

	private float timeCount;
	private int worldTimer;
	private int score;
	private Sprite bubble;
	private SpriteBatch batch;

	public Hud(Viewport viewport, SpriteBatch batch) {
		this.batch = batch;
		worldTimer = 250;
		timeCount = 0;
		score = 0;

		stage = new Stage(viewport);
		table = new Table();
		table.top().left();
		table.setFillParent(true);

		timeLabel = new Label(String.format("Time:  %03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreLabel = new Label(String.format("Score: %04d ", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

		timeLabel.setFontScale(0.5f);
		scoreLabel.setFontScale(0.5f);

		table.add(timeLabel).top().left().pad(PADDING).expandX();
        table.add(scoreLabel).top().right().pad(PADDING);
        table.row().width(50);

		stage.addActor(table);

		setLifeBar();
		drawPowerUpBubble();
	}

	public void setLifeBar() {
	}

	/**
	 * Draw power up container
	 */
	private void drawPowerUpBubble() {
		Texture texture = new Texture("bubble.png");
		bubble = new Sprite(texture);
		bubble.setSize(40, 40);
		bubble.setPosition(350, 10);
	}

	/**
	 * Add one to the score
	 */
	public void tickScore() {
		score++;
	}

    /**
     * Update timeCount and score.
     * @param dt Delta time
     */
	public void update(float dt){
        timeCount += dt;
		if(timeCount >= 1) {
			if(worldTimer > 0) {
				worldTimer--;
			}

			timeCount = 0;
		}

		timeLabel.setText(String.format("Time: %03d", worldTimer));
		scoreLabel.setText(String.format("Score: %04d ", score));
		stage.draw();

		batch.begin();
		bubble.draw(batch);
		batch.end();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
