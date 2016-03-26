package mx.heroesofanzu.game.Screens;

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

/*
* Main menu screen. The first screen which the user interacts.
*/
public class MainScreen extends MyScreen {

	private Texture textureBackground;
	private Sprite background;
	private Stage stage;
	private Skin skin;
	private Table table;
	private Sprite logo;

	public MainScreen(HeroesOfAnzu game) {
		super(game);
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

		// Set logo
		Texture textureLogo = new Texture("logo.png");
		logo = new Sprite(textureLogo);
		logo.setSize(384, 196);
		logo.setPosition(width/2 - logo.getWidth()/2, height - logo.getHeight() - 40);

		// Set buttons
		TextButton play = new TextButton("Jugar", skin);
		TextButton store = new TextButton("Tienda", skin);
		play.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(new PlayScreen(game));
			}
		});

		// Set table with the buttons
		table.setFillParent(true);
		table.bottom();

		table.add(play).width(200).height(60);
		table.row().padTop(20).padBottom(50);
		table.add(store).width(200).height(60);;

		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		batch.begin();
		background.draw(batch);
		logo.draw(batch);
		batch.end();

		stage.draw();
		stage.act(delta);
	}

	@Override
	public void dispose() {
		background.getTexture().dispose();
		logo.getTexture().dispose();
		skin.dispose();
	}
}
