package mx.heroesofanzu.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import mx.heroesofanzu.game.HeroesOfAnzu;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Heroes of Anzu";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new HeroesOfAnzu(), config);
	}
}
