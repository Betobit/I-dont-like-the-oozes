package mx.heroesofanzu.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import mx.heroesofanzu.game.HeroesOfAnzu;
import mx.heroesofanzu.game.sprites.Player;
import mx.heroesofanzu.game.sprites.enemies.Ooze;
import mx.heroesofanzu.game.util.DirectionGestureDetector;

/**
 * Created by jesusmartinez on 25/03/16.
 */
public class PlayScreen extends MyScreen {

	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;
	private World world;
	private RayHandler rayHandler;

	private Body alarm;
	private int width;
	private int height;

	private ArrayList<Ooze> oozes;
	private float timer;
	private Player playerTest;

	private Box2DDebugRenderer b2dr;

	/**
	 * Constructor
	 */
	public PlayScreen(HeroesOfAnzu game) {
		super(game, 400, 240);
		width = getWidth();
		height = getHeight();
	}

	/**
	 * Attach a Point light to the given body.
	 * @param body The body to attach the light.
	 * @param color Color of the light.
	 * @param distance Distance of the light.
	 */
	private void attachLightToBody(Body body, Color color, int distance) {
		new PointLight(rayHandler, 200, color, distance, width/2, height/2)
				.attachToBody(body);
	}

	/**
	 * @return Return the world.
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * @return Return the player.
	 */
	public Player getPlayer() {
		return playerTest;
	}

	/**
	 * Create box2d bodies with or without light.
	 * @param index The layer to draw.
	 * @param light if true the box2d body will have a light attached.
	 */
	private void drawBodies(int index, boolean light) {

		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;

		for(MapObject object : tiledMap.getLayers().get(index).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2));

			body = world.createBody(bdef);

			shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2 );
			fdef.shape = shape;
			body.createFixture(fdef);

			if(light)
				attachLightToBody(body, Color.RED, 30);
		}
	}

	@Override
	public void show() {
		timer = 0;
		b2dr = new Box2DDebugRenderer();

		// Load map and world.
		tiledMap = new TmxMapLoader().load("level1.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, batch);
		world = new World(new Vector2(0, 0), true);

		// Set light world.
		rayHandler = new RayHandler(world);
		rayHandler.setAmbientLight(0.4f);

		// Define enemies.
		oozes = new ArrayList<Ooze>();

		// Draw world.
		drawBodies(3, true);
		drawBodies(4, false);

		// Set player.
		playerTest = new Player(this, width / 2 + 80, height / 2);
		attachLightToBody(playerTest.getBody(), Color.CHARTREUSE, 80);

		// Set light alarm.
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		bdef.type = BodyDef.BodyType.KinematicBody;
		bdef.position.set(width/2, height/2);
		alarm = world.createBody(bdef);

		PolygonShape shapeAlarm = new PolygonShape();
		shapeAlarm.setAsBox(1, 2);
		fdef.shape = shapeAlarm;
		alarm.createFixture(fdef);

		// Attach lights.
		ConeLight p2 = new ConeLight(rayHandler, 200, Color.BLUE, 60, width/2, height/2,0, 40);
		p2.attachToBody(alarm);
		attachLightToBody(alarm, Color.CORAL, 80);

	}

	@Override
	public void render(float delta) {
		super.render(delta);

		tiledMapRenderer.setView(getCamera());
		tiledMapRenderer.render();
		world.step(delta, 6, 2);
		rayHandler.setCombinedMatrix(getCamera());
		rayHandler.updateAndRender();

		alarm.setTransform(alarm.getWorldCenter(), alarm.getAngle() + 0.08f);

		timer+=delta;
		// Create ooze every 3 seconds.
		if (timer >= 4 && oozes.size() < 8) {
			timer-= 4;
			oozes.add(new Ooze(this, MathUtils.random(width), MathUtils.random(height)));
		}

		// Render all oozes.
		for(Ooze o : oozes)
			o.update(delta);

		//b2dr.render(world, getCamera().combined);
		playerTest.update(delta);
		/*
		batch.begin();
		tiledMapRenderer.renderTileLayer((TiledMapTileLayer)tiledMap.getLayers().get(2));
		batch.end();
		*/
	}

	@Override
	public void dispose() {
		rayHandler.dispose();
		tiledMap.dispose();
	}
}
