package mx.heroesofanzu.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by carlossuarez on 18/05/2016.
 * Represents the HP of the player using 9Patch
 */
public class HealthPool {

    private int currentHP;
    private float hpX;
    private float hpY;
    private Integer hpPercentage;

    private NinePatch hpBarBackground;
    private NinePatch hpBar;

    private Label hpLabel;

    private SpriteBatch batch;

    /**
     * Constructor of HealthPool
     *
     * @param x
     * @param y
     * @param batch
     */
    public HealthPool(float x, float y, SpriteBatch batch) {
        this.batch = batch;

        currentHP = 100;
        hpX = x;
        hpY = y;

        updatePercentage();

        hpLabel = new Label(hpPercentage.toString() + "% HP", new Label.LabelStyle(new BitmapFont(), Color.RED));
        hpLabel.setFontScale(0.6f);

        hpBar = new NinePatch(new Texture(Gdx.files.internal("lifebar/hpbar.9.png")), 8, 8, 8, 8);
        hpBarBackground = new NinePatch(new Texture(Gdx.files.internal("lifebar/hpbarbackground.9.png")), 8, 8, 8, 8);
    }

    /**
     * method for the damage dealing by the Oozes
     *
     * @param damage
     */
    public void healthReduction(int damage) {
        if (currentHP > 10) {
            if (currentHP > damage) {
                for (int it = 0; it < damage; it++) {
                    currentHP--;
                }
            } else
                while (currentHP > 10) {
                    currentHP--;
                }
        }
    }

    /**
     * Displays the Hpbar and HpBackground
     */
    public void draw() {
        hpBarBackground.draw(batch, hpX, hpY, 110, 55);
        hpBar.draw(batch, hpX + 6.2f, hpY - 0.2f, currentHP, 55);
    }

    /**
     * Update HpPercenrage and add this in hpLabel
     */
    public void update() {
        updatePercentage();
        hpLabel.setText(hpPercentage.toString() + "% HP");
    }

    /**
     * update the Hp integer in base 100
     */
    private void updatePercentage() {
        hpPercentage = ((currentHP - 10) * 100) / 90;
    }

    /**
     * @return HpX
     */
    public float getHpX() {
        return hpX;
    }

    /**
     * @return HpY
     */
    public float getHpY() {
        return hpY;
    }

    /**
     * @return HpLabel
     */
    public Label getHpLabel() {
        return hpLabel;
    }
}
