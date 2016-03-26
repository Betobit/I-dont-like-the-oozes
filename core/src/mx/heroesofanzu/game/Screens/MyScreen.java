package mx.heroesofanzu.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.heroesofanzu.game.HeroesOfAnzu;

/*
* Represents one of many application screens, such as a main menu, a settings menu, the game screen and so on.
*/
public abstract class MyScreen implements Screen {

	protected SpriteBatch batch;
	protected HeroesOfAnzu game;
	protected static int width;
	protected static int height;
	private Viewport viewport;
	private OrthographicCamera camera;

	public MyScreen(HeroesOfAnzu heroesOfAnzu) {
		game = heroesOfAnzu;
		batch = game.getBatch();
		width = 800;
		height= 480;
		camera  = new OrthographicCamera();
		viewport = new FillViewport(width, height, camera);
	}

	/*
	* Return the camera of the screen.
	*/
	public OrthographicCamera getCamera() {
		return camera;
	}

	/*
	* Return the viewport.
	*/
	public Viewport getViewport() {
		return viewport;
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void show() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}
}
