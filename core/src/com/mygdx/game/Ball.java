package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;

public final class Ball {
	private static final float BALL_DIMENSION = 10;
	public static final int BALL_STEP = 8;

	private static final Pixmap BALL_PIXMAP;

	static {
		BALL_PIXMAP = new Pixmap((int) BALL_DIMENSION, (int) BALL_DIMENSION, Pixmap.Format.RGB888);
		BALL_PIXMAP.setColor(Color.WHITE);
		BALL_PIXMAP.fill();

		// for debugging
//		BALL_PIXMAP.drawPixel(0, 0, Color.RED.toIntBits());
	}

	private static final Texture BALL_TEXTURE;

	static {
		BALL_TEXTURE = new Texture(BALL_PIXMAP);
		BALL_PIXMAP.dispose();
	}

	private final Sprite sprite;
	private final World world;

	private float x;
	private float y;
	private float dy;
	private float dx;

	public Ball(World world, float graphicsWidth, float graphicsHeight) {
		this.world = world;
		this.sprite = new Sprite(BALL_TEXTURE);

		this.x = (graphicsWidth / 2f - BALL_DIMENSION / 2f);
		this.y = graphicsHeight - BALL_DIMENSION;

		this.dx = Math.random() > 0.5 ? BALL_STEP : -BALL_STEP;
		this.dy = BALL_STEP;

		Timer.schedule(new Timer.Task() {

			@Override
			public void run() {
				Ball.this.world.setPosition(Ball.this, Ball.this.x + Ball.this.dx, Ball.this.y + Ball.this.dy);
			}
		}, 0, 0.1f);
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Rectangle boundingRectangle() {
		this.sprite.setPosition(this.x, this.y);
		return this.sprite.getBoundingRectangle();
	}

	public void draw(final SpriteBatch spriteBatch) {
		this.sprite.setPosition(this.x, this.y);
		sprite.draw(spriteBatch);
	}

	public void dispose() {
		BALL_TEXTURE.dispose();
	}

	public float dx() {
		return this.dx;
	}

	public void dx(float dx) {
		this.dx = dx;
	}

	public float dy() {
		return this.dy;
	}

	public void dy(float dy) {
		this.dy = dy;
	}
}
