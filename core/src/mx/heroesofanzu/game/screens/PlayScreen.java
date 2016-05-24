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
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
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
import mx.heroesofanzu.game.sprites.CollisionListener;
import mx.heroesofanzu.game.sprites.Door;
import mx.heroesofanzu.game.sprites.Player;
import mx.heroesofanzu.game.sprites.enemies.Ooze;
import mx.heroesofanzu.game.sprites.powerups.PowerUp;

/**
 * Created by jesusmartinez on 25/03/16.
 */
// TODO: Custom class for items on map.
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
	private Sprite gOver;
	private Door door;

	// Entities
	private Player player;
	private ArrayList<Ooze> oozes;

	// Others
	private Hud hud;
	private Sound popSound;
	private ArrayList<Body> alarms;
	private float timer;
	private boolean gameOver;
	private int gOverDelay;

	// Debugging
	private Box2DDebugRenderer b2dr;
	private ShapeRenderer shapeRenderer;

	/**
	 * Constructor
	 */
	public PlayScreen(HeroesOfAnzu game) {
		super(game, 400, 240);
		timer = 0;
		//set GameOver's utility fields
		gameOver = false;
		gOverDelay = 200;

		alarms = new ArrayList<Body>();
		b2dr = new Box2DDebugRenderer();
		shapeRenderer = new ShapeRenderer();
		width = getWidth();
		height = getHeight();
		popSound = Gdx.audio.newSound(Gdx.files.internal("sounds/pop.mp3"));
		hud = new Hud(getViewport(), batch);
	}

	/**
	 * Attach a Point light to the given body.
	 *
	 * @param body     The body to attach the light.
	 * @param color    Color of the light.
	 * @param distance Distance of the light.
	 */
	private void attachLightToBody(Body body, Color color, int distance) {
		new PointLight(rayHandler, 200, color, distance, width / 2, height / 2)
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
		return player;
	}

	private void setAlarms() {
		for (Body b : alarms) {
			ConeLight coneLight = new ConeLight(rayHandler, 200, Color.MAGENTA, 60, width / 2, height / 2, 0, 40);
			coneLight.attachToBody(b);
			attachLightToBody(b, Color.RED, 80);
			b.setActive(false);
		}
	}

	/**
	 * Create box2d bodies.
	 *
	 * @param index    The layer to draw.
	 * @param bodyType Type of the body to draw.
	 * @return ArrayList of created bodies.
	 */
	private ArrayList<Body> createBodies(int index, BodyDef.BodyType bodyType) {
		Body body;
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		ArrayList<Body> bodies = new ArrayList<Body>();

		for (MapObject object : tiledMap.getLayers().get(index).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			bdef.type = bodyType;
			bdef.position.set((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2));

			body = world.createBody(bdef);
			shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
			fdef.shape = shape;
			body.createFixture(fdef);

			bodies.add(body);
		}

		return bodies;
	}

	/**
	 * Create collection
	 *
	 * @param layer      Layer in the TMX map
	 * @param spriteName Image name
	 * @param width      Sprite width
	 * @param height     Sprite height
	 */
	private ArrayList<Sprite> createSpriteCollection(int layer, String spriteName, int width, int height) {
		ArrayList<Sprite> collection = new ArrayList<Sprite>();

		for (MapObject object : tiledMap.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			Sprite s = new Sprite(new Texture(spriteName));
			s.setPosition(rect.getX() + 4, rect.getY() + 4);
			s.setSize(width, height);
			collection.add(s);
		}

		return collection;
	}

	/**
	 * Iterate over a collection, draw all items and detect collision.
	 *
	 * @param sprites  Sprites to draw
	 * @param listener Do something when player overlaps item
	 */
	private void renderSprites(ArrayList<Sprite> sprites, CollisionListener listener) {
		Iterator<Sprite> iterator = sprites.iterator();
		while (iterator.hasNext()) {
			Sprite s = iterator.next();
			s.draw(batch);

			if (player.getBoundingRectangle().overlaps(s.getBoundingRectangle())) {
				listener.onCollision(s);
				iterator.remove();
			}
		}
	}

	/**
	 * Dispose textures of all items in the collection.
	 *
	 * @param collection Sprites
	 */
	private void disposeCollection(ArrayList<Sprite> collection) {
		for (Sprite s : collection) {
			s.getTexture().dispose();
		}
	}

	@Override
	public void show() {
		// Load map and world.
		tiledMap = new TmxMapLoader().load("level1.tmx");
		world = new World(new Vector2(0, 0), true);
		Texture textureMap = new Texture("map.png");
		mapSprite = new Sprite(textureMap);
		mapSprite.setSize(width, height);

		//Load Gameover sprite
		Texture textureGOver = new Texture("Game_Over.png");
		gOver = new Sprite(textureGOver);
		gOver.setPosition(20, height/4+120);

		// Set light world.
		rayHandler = new RayHandler(world);
		rayHandler.setAmbientLight(0.7f);

		// Define enemies.
		oozes = new ArrayList<Ooze>();

		// Set items on map
		door = new Door(width - 15, height / 2 - 25);
		coins = createSpriteCollection(5, "coin.png", 7, 7);
		powerUps = createSpriteCollection(6, "powerup.png", 12, 12);

		// Set box2d bodies.
		createBodies(4, BodyDef.BodyType.StaticBody);
		alarms = createBodies(3, BodyDef.BodyType.KinematicBody);
		setAlarms();

		// Set player.
		player = new Player(this, width / 2, height / 2 - 10);
		attachLightToBody(player.getBody(), Color.BLUE, 60);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		if(!gameOver) {
			timer += delta;
			world.step(delta, 6, 2);

			for (Body a : alarms) {
				a.setTransform(a.getWorldCenter(), a.getAngle() + 0.08f);
			}

			// Draw
			draw();

			// Create ooze every 4 seconds.
			if (timer >= 4 && oozes.size() < 6) {
				timer -= 4;
				oozes.add(new Ooze(this, MathUtils.random(width - 10), MathUtils.random(height)));
			}

			// Iterate all oozes.
			Iterator<Ooze> iterator = oozes.iterator();
			while (iterator.hasNext()) {
				Ooze o = iterator.next();

				if (o.getBoundingRectangle().overlaps(player.getBoundingRectangle())) {
					world.destroyBody(o.getBody());
					hud.getHealthBar().healthReduction(0.2f);
					iterator.remove();
				}
				o.update(delta);
			}

			if (Constants.DEBUGGING) {
				b2dr.render(world, getCamera().combined);
			}

			rayHandler.setCombinedMatrix(getCamera());
			rayHandler.updateAndRender();
			player.update(delta);
			hud.update(delta);

			if (coins.isEmpty()) {
				door.open();
			}

			if (hud.getHealthBar().isEmpty()) {
				gameOver = true;
			}

			if(door.getBoundingRectangle().overlaps(player.getBoundingRectangle()) && door.isOpen()) {
				//Por el momento...
				getGame().setScreen(new MainScreen(getGame()));
			}
		}
		else {
			draw();
			batch.begin();
			gOver.draw(batch);
			batch.end();
			gOverDelay--;
			gOver.setPosition(20, height/4+120 -(100 - gOverDelay/2));
			if(gOverDelay==0) {
				getGame().setScreen(new MainScreen(getGame()));
			}
		}
	}

	public void draw() {
		batch.begin();
		mapSprite.draw(batch);

		// Draw Sprites and detect collision
		renderSprites(powerUps, new CollisionListener() {
			@Override
			public void onCollision(Sprite s) {
				hud.setPowerUp(new PowerUp(player, "velocity.png", true));
			}
		});

		renderSprites(coins, new CollisionListener() {
			@Override
			public void onCollision(Sprite s) {
				hud.tickScore();
				popSound.play(0.2f);
			}
		});

		door.draw(batch);
		batch.end();
	}

	@Override
	public void dispose() {
		rayHandler.dispose();
		tiledMap.dispose();
		popSound.dispose();
		disposeCollection(powerUps);
		disposeCollection(coins);
	}
}
