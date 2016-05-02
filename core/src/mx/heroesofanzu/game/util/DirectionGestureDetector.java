package mx.heroesofanzu.game.util;

import com.badlogic.gdx.input.GestureDetector;

/**
 * Created by Trần Xuân Trường.
 * https://github.com/tmtxt/agent-069/blob/master/Agent-069/src/mad/agent069/DirectionGestureDetector.java
 */
public class DirectionGestureDetector extends GestureDetector {
	public interface DirectionListener {
		void onLeft();

		void onRight();

		void onUp();

		void onDown();
	}

	public DirectionGestureDetector(DirectionListener directionListener) {
		super(new DirectionGestureListener(directionListener));
	}

	private static class DirectionGestureListener extends GestureAdapter {
		DirectionListener directionListener;

		public DirectionGestureListener(DirectionListener directionListener) {
			this.directionListener = directionListener;
		}

		@Override
		public boolean fling(float velocityX, float velocityY, int button) {
			if (Math.abs(velocityX) > Math.abs(velocityY)) {
				if (velocityX > 0) {
					directionListener.onRight();
				} else {
					directionListener.onLeft();
				}
			} else {
				if (velocityY > 0) {
					directionListener.onDown();
				} else {
					directionListener.onUp();
				}
			}
			return super.fling(velocityX, velocityY, button);
		}

	}

}
