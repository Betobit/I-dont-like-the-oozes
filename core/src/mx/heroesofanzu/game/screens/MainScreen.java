package mx.heroesofanzu.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import mx.heroesofanzu.game.HeroesOfAnzu;

/**
 * Main menu screen. The first screen which the user interacts.
 */
public class MainScreen extends MyScreen {

	private Texture textureBackground;
	private Sprite background;
	private Stage stage;
	private Skin skin;
	private Table table;

	private int width;
	private int height;

	/**
	 * Constructor
	 */
	public MainScreen(HeroesOfAnzu game) {
		super(game, 800, 480);
		width = getWidth();
		height= getHeight();
	}

	@Override
	public void show() {
		textureBackground = new Texture("background.jpg");
		background = new Sprite(textureBackground);
		stage = new Stage(getViewport());
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		table = new Table();

		// Set background
		background.setPosition(0, 0);
		background.setSize(width, height);

		// Set buttons
		TextButton play = new TextButton("Jugar", skin);
		TextButton store = new TextButton("Tienda", skin);
		play.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				getGame().setScreen(new PlayScreen(getGame()));
			}
		});

		// Set table with the buttons
		table.setFillParent(true);
		table.bottom();

		table.add(play).width(180).height(45);
		table.row().padTop(20).padBottom(20);
		table.add(store).width(180).height(45);

		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		batch.begin();
		background.draw(batch);
		batch.end();

		stage.draw();
		stage.act(delta);
	}

	@Override
	public void dispose() {
		background.getTexture().dispose();
		skin.dispose();
	}
}
