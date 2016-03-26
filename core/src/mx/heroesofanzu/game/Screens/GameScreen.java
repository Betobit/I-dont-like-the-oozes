package mx.heroesofanzu.game.Screens;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import mx.heroesofanzu.game.HeroesOfAnzu;

/**
 * Created by jesusmartinez on 25/03/16.
 */
public class GameScreen extends MyScreen {

	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;

	public GameScreen(HeroesOfAnzu game) {
		super(game);
	}

	@Override
	public void show() {
		tiledMap = new TmxMapLoader().load("tiled_map.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		tiledMapRenderer.setView(super.getCamera());
		tiledMapRenderer.render();
	}
}
