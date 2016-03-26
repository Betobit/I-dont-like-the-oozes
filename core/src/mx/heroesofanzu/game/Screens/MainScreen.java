package mx.heroesofanzu.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

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
		stage = new Stage();
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		table = new Table();

		// Set background
		background.setPosition(0, 0);
		background.setSize(HeroesOfAnzu.getScreenWidth(), HeroesOfAnzu.getScreenHeight());

		// Set logo
		Texture textureLogo = new Texture("logo.png");
		logo = new Sprite(textureLogo);
		logo.setPosition(HeroesOfAnzu.getScreenWidth()/2 - logo.getWidth()/2,
				HeroesOfAnzu.getScreenHeight() - logo.getHeight() - 40);

		// Set buttons
		TextButton play = new TextButton("Jugar", skin);
		TextButton store = new TextButton("Tienda", skin);
		play.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y){
				game.setScreen(new GameScreen(game));
			}
		});

		// Set table with the buttons
		table.setFillParent(true);
		table.bottom();
		table.add(play).width(300).height(80);
		table.row().padTop(20).padBottom(150);
		table.add(store).minWidth(300).height(80);

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
