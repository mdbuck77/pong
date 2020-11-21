package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

public final class Ball {

	private enum Direction {
		LEFT,
		RIGHT
	}

	private static final float BALL_DIMENSION = 16;
	private static final int BALL_STEP = 8;

	private static final Pixmap BALL_PIXMAP;

	static {
		BALL_PIXMAP = new Pixmap((int)BALL_DIMENSION, (int) BALL_DIMENSION, Pixmap.Format.RGB888);
		BALL_PIXMAP.setColor(Color.WHITE);
		BALL_PIXMAP.fill();
	}

	private static final Texture BALL_TEXTURE;

	static {
		BALL_TEXTURE = new Texture(BALL_PIXMAP);
		BALL_PIXMAP.dispose();
	}

	private final Sprite sprite;
	private final float graphicsHeight;
	private final float graphicsWidth;

	private Direction direction = Direction.LEFT;

	public Ball(float graphicsWidth, float graphicsHeight) {
		this.graphicsWidth = graphicsWidth;
		this.graphicsHeight = graphicsHeight;
		this.sprite = new Sprite(BALL_TEXTURE);

		sprite.setX(this.graphicsWidth / 2f - BALL_DIMENSION / 2f);
		sprite.setY(this.graphicsHeight / 2f - BALL_DIMENSION / 2f);

		Timer.schedule(new Timer.Task() {

			@Override
			public void run() {
				float x = sprite.getX();
				float y = sprite.getY();
				switch (direction) {
					case LEFT:
						if (x == 0) {
							direction = Direction.RIGHT;
							x = 0;
						} else {
							x = x - BALL_STEP;
						}
						break;

					case RIGHT:
						if (x >= Ball.this.graphicsWidth - sprite.getWidth()) {
							direction = Direction.LEFT;
							x = Ball.this.graphicsWidth - sprite.getWidth();
						} else {
							x = x + BALL_STEP;
						}
						break;
					default:
						throw new UnsupportedOperationException(String.valueOf(direction));
				}
				sprite.setPosition(x, y);
			}
		}, 0, 0.1f);
	}

	public void draw(final SpriteBatch spriteBatch) {
		sprite.draw(spriteBatch);
	}

	public void dispose() {
		BALL_TEXTURE.dispose();
	}
}
