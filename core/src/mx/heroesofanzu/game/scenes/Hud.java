package mx.heroesofanzu.game.scenes;

import com.badlogic.gdx.Gdx;
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

	public static final int PAD_TOP = 10;
	private Stage stage;
	private Table table;

	private Label timeLabel;
    private Label timeCountLabel;
	private Label scoreLabel;
    private Label scoreCountLabel;

	private Integer score;
	private float timeCount;

	public Hud(Viewport viewport) {
		timeCount = 0f;
		score = 0;

		stage = new Stage(viewport);
		table = new Table();
		table.top().left();
		table.setFillParent(true);

		timeLabel = new Label("Time:  ",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		timeCountLabel = new Label(getStringTimer(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreLabel = new Label("Score:  ",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreCountLabel = new Label(score.toString(),new Label.LabelStyle(new BitmapFont(), Color.WHITE));

		table.add(timeLabel).top().left().padTop(PAD_TOP).padRight(250);
        table.add(scoreLabel).top().right().padTop(PAD_TOP);
        table.row().width(50);
        table.add(timeCountLabel).top().left().padTop(-PAD_TOP / 3).padRight(250).width(100);
		table.add(scoreCountLabel).top().right().padTop(-PAD_TOP / 3);

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
     * @param timer
     */
	public void updateTime(float timer){
        timeCount+=timer/500;
		timeCountLabel.setText(getStringTimer());
	}

    /**
     * this method works together with updateTimer, making timeCount in a String for label
     * @return a toString of timeCount casted in a integer
     */
	public String getStringTimer(){
		Integer i = (int)timeCount;
		return i.toString();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
