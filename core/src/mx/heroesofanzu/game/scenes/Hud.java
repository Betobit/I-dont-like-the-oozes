package mx.heroesofanzu.game.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.media.jfxmediaimpl.MediaDisposer;

/**
 * Created by jesusmartinez on 10/05/16.
 * Modified by carlossuarez on 5/05/16.
 * Represents the HUD of the game by all the relevant information
 */
public class Hud implements MediaDisposer.Disposable {

	public static final int PADDING = 10;
	private Stage stage;
	private Table table;

	private Label timeLabel;
	private Label scoreLabel;

	private Integer score;
	private float timeCount;
	private int worldTimer;

	public Hud(Viewport viewport) {
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
	}

	public void setLifeBar() {
	}

	/**
	 * return The stage
	 */
	public Stage getStage() {
		return stage;
	}

    /**
     * update timeCount with timer of PlayScreen
     * update timeCountLabel with getStringTimer
     * @param dt Delta time
     */
	public void updateTime(float dt){
        timeCount += dt;
		if(timeCount >= 1) {
			if(worldTimer > 0) {
				worldTimer--;
			}

			timeLabel.setText(String.format("Time: %03d", worldTimer));
			timeCount = 0;
		}
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
