package mx.heroesofanzu.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
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

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import mx.heroesofanzu.game.HeroesOfAnzu;
import mx.heroesofanzu.game.Util.DirectionGestureDetector;

/**
 * Created by jesusmartinez on 25/03/16.
 */
public class PlayScreen extends MyScreen {

	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;

	//Box2d variables
	private World world;
	private Box2DDebugRenderer b2dr;

	// Player
	private Body player;
	private RayHandler rayHandler;
	private PointLight pointLight;

	private Body alarm;

	public PlayScreen(HeroesOfAnzu game) {
		super(game);
	}

	@Override
	public void show() {
		tiledMap = new TmxMapLoader().load("tiled_map.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, batch);

		b2dr = new Box2DDebugRenderer();
		world = new World(new Vector2(0, 0), true);
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;

		//*
		bdef.type = BodyDef.BodyType.DynamicBody;
		bdef.position.set(width/2+80, height/2);

		player = world.createBody(bdef);
		CircleShape circle = new CircleShape();
		circle.setRadius(5.6f);
		fdef.shape = circle;
		fdef.density = 0f;
		fdef.restitution = 0f;
		player.createFixture(fdef);

		rayHandler = new RayHandler(world);
		rayHandler.setAmbientLight(0.5f);

		// Alarm
		bdef.type = BodyDef.BodyType.KinematicBody;
		bdef.position.set(width/2, height/2);
		alarm = world.createBody(bdef);
		PolygonShape shapeAlarm = new PolygonShape();

		shapeAlarm.set(new float[]{
				0,0,
				1,0,
				1, 1,
				0, 2
		});

		fdef.shape = shapeAlarm;
		alarm.createFixture(fdef);

		new PointLight(rayHandler, 200, Color.RED, 30, width/2, height/2);
		ConeLight p2 = new ConeLight(rayHandler, 200, Color.BLUE, 60, width/2, height/2,0, 40);
		p2.attachToBody(alarm);

		pointLight = new PointLight(rayHandler, 400, Color.CORAL, 80, 0, 0);
		pointLight.attachToBody(player);
		//*

		// Create ground bodies/fixtures
		for(MapObject object : tiledMap.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2));

			body = world.createBody(bdef);

			shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2 );
			fdef.shape = shape;
			body.createFixture(fdef);
		}

		Gdx.input.setInputProcessor(new DirectionGestureDetector(new DirectionGestureDetector.DirectionListener() {

			@Override
			public void onUp() {
				player.applyLinearImpulse(new Vector2(0, 4f), player.getWorldCenter(), true);
			}

			@Override
			public void onRight() {
				player.applyLinearImpulse(new Vector2(4f, 0), player.getWorldCenter(), true);
			}

			@Override
			public void onLeft() {
				player.applyLinearImpulse(new Vector2(-4f, 0), player.getWorldCenter(), true);
			}

			@Override
			public void onDown() {
				player.applyLinearImpulse(new Vector2(0, -4f), player.getWorldCenter(), true);

			}
		}));

	}

	@Override
	public void render(float delta) {
		super.render(delta);
		inputHandler();
		tiledMapRenderer.setView(getCamera());
		tiledMapRenderer.render();
		b2dr.render(world, getCamera().combined);
		world.step(1 / 60f, 6, 2);
		rayHandler.setCombinedMatrix(getCamera());
		rayHandler.updateAndRender();

		// Rotate alarm
		/*
		float angle = (alarm.getAngle()) * (MathUtils.PI/180);
		Vector2 p = alarm.getPosition();
		p.x = 400 + MathUtils.sin(angle)*20;
		p.y = 240 + MathUtils.cos(angle)*20;
		*/

		alarm.setTransform(alarm.getWorldCenter(), alarm.getAngle()+0.08f);
	}

	@Override
	public void dispose() {
		rayHandler.dispose();
		tiledMap.dispose();
	}

	private void inputHandler() {
		if( Gdx.input.isKeyPressed(Input.Keys.RIGHT) )
			player.applyLinearImpulse(new Vector2(16f, 0), player.getWorldCenter(), true);
		else if( Gdx.input.isKeyPressed(Input.Keys.LEFT) )
			player.applyLinearImpulse(new Vector2(-16f, 0), player.getWorldCenter(), true);
		else if( Gdx.input.isKeyPressed(Input.Keys.UP) )
			player.applyLinearImpulse(new Vector2(0, 16f), player.getWorldCenter(), true);
		else if( Gdx.input.isKeyPressed(Input.Keys.DOWN) )
			player.applyLinearImpulse(new Vector2(0, -16f), player.getWorldCenter(), true);

	}
}
