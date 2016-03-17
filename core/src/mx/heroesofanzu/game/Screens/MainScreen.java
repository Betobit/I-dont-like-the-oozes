package mx.heroesofanzu.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import mx.heroesofanzu.game.HeroesOfAnzu;

/**
 * Created by jesusmartinez on 12/03/16.
 */
public class MainScreen extends MyScreen {

    private Sprite logo;
    private Texture texture;
    private Texture dragon;
    private TextureRegion dragonRegion;
    private Animation dragonAnimation;
    private float duration = 0;

    private int position = 120;
    private Sprite skin;
    private Texture textureSkin;

    public MainScreen(HeroesOfAnzu game) {
        super(game);
    }

    @Override
    public void show() {
        texture = new Texture("logo.png");
        logo = new Sprite(texture);
        dragon = new Texture("dragon.png");
        dragonRegion = new TextureRegion(dragon, 376, 68);

        // Set skin
        textureSkin = new Texture("skin.png");
        skin = new Sprite(textureSkin);

        // Set logo
        logo.setSize(256, 128);
        logo.setPosition(Gdx.graphics.getWidth()/2-logo.getWidth()/2,
                Gdx.graphics.getHeight()-logo.getHeight()-40);

        // Get frames and set animation.
        TextureRegion[][] splited = dragonRegion.split(94, 68);
        TextureRegion[] frames = new TextureRegion[4];

        for(int i=0; i < 4; i++)
            frames[i] = splited[0][i];

        dragonAnimation= new Animation(0.08f, frames);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isTouched()) {
            position += 20;
        }

        duration += delta;
        TextureRegion frame = dragonAnimation.getKeyFrame(duration, true);
        skin.setPosition(300, position);

        batch.begin();
        logo.draw(batch);
        batch.draw(frame, 300, position);
        skin.draw(batch);
        batch.end();

        if( position > 100 )
            position -= 10;

    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    private void input() {
        Gdx.input.isKeyPressed(Input.Keys.A);
    }
}
