package mx.heroesofanzu.game.Screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import mx.heroesofanzu.game.HeroesOfAnzu;

/*
* Main menu screen. The first screen which the user interacts.
*/
public class MainScreen extends MyScreen {

    private Texture textureBackground;
    private Sprite background;

    public MainScreen(HeroesOfAnzu game) {
        super(game);
    }

    @Override
    public void show() {
        textureBackground = new Texture("background.jpg");
        background = new Sprite(textureBackground);

        background.setPosition(0, 0);
        background.setSize(HeroesOfAnzu.getScreenWidth(), HeroesOfAnzu.getScreenHeight());
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        batch.end();
    }
}
