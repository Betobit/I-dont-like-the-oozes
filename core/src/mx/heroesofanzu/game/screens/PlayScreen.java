package mx.heroesofanzu.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.Iterator;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import mx.heroesofanzu.game.Constants;
import mx.heroesofanzu.game.HeroesOfAnzu;
import mx.heroesofanzu.game.scenes.Hud;
import mx.heroesofanzu.game.sprites.Player;
import mx.heroesofanzu.game.sprites.enemies.Ooze;
import mx.heroesofanzu.game.sprites.powerups.PowerUp;

/**
 * Created by jesusmartinez on 25/03/16.
 */
public class PlayScreen extends MyScreen {

	// World
	private World world;
	private RayHandler rayHandler;
	private int width;
	private int height;

	// Map
	private TiledMap tiledMap;
	private ArrayList<Sprite> coins;
	private ArrayList<Sprite> powerUps;
	private Sprite mapSprite;

	// Entities
	private Player playerTest;
	private ArrayList<Ooze> oozes;

	// Others
	private Hud hud;
	private Sound popSound;
	private ArrayList<Body> alarms;
	private float timer;

	// Debugging
	private Box2DDebugRenderer b2dr;
	private ShapeRenderer shapeRenderer;

	/**
	 * Constructor
	 */
	public PlayScreen(HeroesOfAnzu game) {
		super(game, 400, 240);
		timer = 0;
		alarms = new ArrayList<Body>();
		b2dr = new Box2DDebugRenderer();
		shapeRenderer = new ShapeRenderer();
		width = getWidth();
		height = getHeight();
		popSound = Gdx.audio.newSound(Gdx.files.internal("sounds/pop.mp3"));
		//hud = new Hud(getViewport(), batch);
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

	private void setAlarms() {
		for(Body b : alarms) {
			ConeLight coneLight = new ConeLight(rayHandler, 200, Color.MAGENTA, 60, width/2, height/2,0, 40);
			coneLight.attachToBody(b);
			attachLightToBody(b, Color.RED, 40);
			b.setActive(false);
		}
	}

	/**
	 * Create box2d bodies.
	 * @param index The layer to draw.
	 * @param bodyType Type of the body to draw.
	 * @return ArrayList of created bodies.
	 */
	private ArrayList<Body> createBodies(int index, BodyDef.BodyType bodyType) {
		Body body;
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		ArrayList<Body> bodies = new ArrayList<Body>();

		for(MapObject object : tiledMap.getLayers().get(index).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			bdef.type = bodyType;
			bdef.position.set((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2));

			body = world.createBody(bdef);
			shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2 );
			fdef.shape = shape;
			body.createFixture(fdef);

			bodies.add(body);
		}

		return bodies;
	}

	@Override
	public void show() {
		// Load map and world.
		tiledMap = new TmxMapLoader().load("level1.tmx");
		world = new World(new Vector2(0, 0), true);
		Texture textureMap = new Texture("map.png");
		mapSprite = new Sprite(textureMap);
		mapSprite.setSize(width, height);

		// Set light world.
		rayHandler = new RayHandler(world);
		rayHandler.setAmbientLight(0.7f);

		// Define enemies.
		oozes = new ArrayList<Ooze>();

		// Set coins.
		coins = new ArrayList<Sprite>();
		for(MapObject object : tiledMap.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			Sprite s = new Sprite(new Texture("coin.png"));
			s.setPosition(rect.getX() + 4, rect.getY() + 4);
			s.setSize(10, 10);
			s.setAlpha(0.95f);
			coins.add(s);
		}

		// Set power ups.
		powerUps = new ArrayList<Sprite>();
		for(MapObject object : tiledMap.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			Sprite s = new Sprite(new Texture("powerup.png"));
			s.setPosition(rect.getX(), rect.getY());
			s.setSize(12, 12);
			powerUps.add(s);
		}

		// Set box2d bodies.
		createBodies(4, BodyDef.BodyType.StaticBody);
		alarms = createBodies(3, BodyDef.BodyType.KinematicBody);
		setAlarms();

		// Set player.
		playerTest = new Player(this, width / 2, height / 2);
		attachLightToBody(playerTest.getBody(), Color.BLUE, 80);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		timer+=delta;
		world.step(delta, 6, 2);

		for(Body a : alarms) {
			a.setTransform(a.getWorldCenter(), a.getAngle() + 0.08f);
		}

		// Draw power ups
		batch.begin();
		mapSprite.draw(batch);
		Iterator<Sprite> powerIterator = powerUps.iterator();
		while(powerIterator.hasNext()) {
			Sprite s = powerIterator.next();
			s.draw(batch);
			if (playerTest.getBoundingRectangle().overlaps(s.getBoundingRectangle())) {
				//hud.setPowerUp(new PowerUp("velocity.png", true));
				//playerTest.applyPowerUp();
				powerIterator.remove();
			}
		}

		// Draw coins
		Iterator<Sprite> coinsIterator = coins.iterator();
		while(coinsIterator.hasNext()) {
			Sprite s = coinsIterator.next();
			s.draw(batch);
			
			if(playerTest.getBoundingRectangle().overlaps(s.getBoundingRectangle())) {
				//hud.tickScore();
				coinsIterator.remove();
				popSound.play(0.08f);
			}
		}
		batch.end();

		// Create ooze every 4 seconds.
		if (timer >= 4 && oozes.size() < 8) {
			timer-= 4;
			oozes.add(new Ooze(this, MathUtils.random(width - 10), MathUtils.random(height)));
		}

		// Iterate all oozes.
		Iterator<Ooze> iterator = oozes.iterator();
		while(iterator.hasNext()) {
			Ooze o = iterator.next();

			if(o.getBoundingRectangle().overlaps(playerTest.getBoundingRectangle())) {
				world.destroyBody(o.getBody());
				//hud.getHpBar().healthReduction(20);
				iterator.remove();
			}

			if(Constants.DEBUGGING) {
				shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
				Rectangle rect = o.getBoundingRectangle();
				shapeRenderer.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
				shapeRenderer.rect(playerTest.getX(), playerTest.getY(), playerTest.getWidth(), playerTest.getHeight());
				shapeRenderer.end();
			}
			o.update(delta);
		}


		if(Constants.DEBUGGING) {
			b2dr.render(world, getCamera().combined);
		}

		rayHandler.setCombinedMatrix(getCamera());
		rayHandler.updateAndRender();
		playerTest.update(delta);
		//hud.update(delta);
	}

	@Override
	public void dispose() {
		rayHandler.dispose();
		tiledMap.dispose();
		popSound.dispose();
	}
}
