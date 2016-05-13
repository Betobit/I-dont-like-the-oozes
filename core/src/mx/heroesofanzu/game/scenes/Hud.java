package mx.heroesofanzu.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.media.jfxmediaimpl.MediaDisposer;


/**
 * Created by jesusmartinez on 10/05/16.
 */
public class Hud implements MediaDisposer.Disposable {

	public static final int PAD_TOP = 27;
	private Stage stage;
	private Table table;
	private Label timeLabel,timeCountLabel;
	private Label scoreLabel,scoreCountLabel;
	private Integer score;
	private float timeCount;
	private Skin skin;

	public Hud(Viewport viewport) {
		timeCount = 0f;
		score = 0;

		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		stage = new Stage(viewport);
		table = new Table();
		table.top().left();
		table.setFillParent(true);

		timeLabel = new Label("Time:  ",skin);
		timeCountLabel = new Label(getStringTimer(), skin);
		scoreLabel = new Label("Score:  ",skin);
		scoreCountLabel = new Label(score.toString(),skin);

		table.add(timeLabel).top().padTop(PAD_TOP);
		table.add(timeCountLabel).top().padTop(PAD_TOP).width(240);
		table.add(scoreLabel).top().padTop(PAD_TOP);
		table.add(scoreCountLabel).top().padTop(PAD_TOP);

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

	public void updateTime(float timer){
		timeCount+=timer/200;
		timeCountLabel.setText(getStringTimer());
	}

	public String getStringTimer(){
		Integer i = (int)timeCount;
		return i.toString();
	}

	@Override
	public void dispose() {
		skin.dispose();
		stage.dispose();
	}
}
