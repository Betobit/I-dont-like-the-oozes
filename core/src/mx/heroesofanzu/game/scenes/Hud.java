package mx.heroesofanzu.game.scenes;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.media.jfxmediaimpl.MediaDisposer;

/**
 * Created by jesusmartinez on 10/05/16.
 */
public class HUD implements MediaDisposer.Disposable {

	private Stage stage;
	private Table table;

	public HUD(Viewport viewport) {
		stage = new Stage(viewport);
		table = new Table();
		table.top();
		table.setFillParent(true);
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

	@Override
	public void dispose() {

	}
}
