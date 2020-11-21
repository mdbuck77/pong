package com.mygdx.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public final class Player implements InputProcessor {
	private static final int PLAYER_WIDTH = 16;
	private static final int PLAYER_HEIGHT = 64;
	private static final int PLAYER_STEP = 7;

	private static final Pixmap PIXMAP = new Pixmap(PLAYER_WIDTH, PLAYER_HEIGHT, Pixmap.Format.RGB888);

	static {
		PIXMAP.setColor(Color.WHITE);
		PIXMAP.fill();
	}

	private static final Texture TEXTURE = new Texture(PIXMAP);

	static {
		PIXMAP.dispose();
	}

	private enum Direction {
		UP,
		DOWN,
		NEUTRAL
	}

	private final Sprite sprite;
	private final int upKey;
	private final int dnKey;
	private final int maxY;

	private Direction direction;

	public Player(int upKey, int dnKey, int screenHeight) {
		this.upKey = upKey;
		this.dnKey = dnKey;
		this.maxY = screenHeight - TEXTURE.getHeight();
		this.direction = Direction.NEUTRAL;
		this.sprite = new Sprite(TEXTURE);
		this.sprite.setY(screenHeight / 2f - TEXTURE.getHeight() / 2f);
	}

	public void draw(final SpriteBatch batch) {
		sprite.draw(batch);
	}

	public void dispose() {
		TEXTURE.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == upKey && Float.compare(this.sprite.getY(), this.maxY) != 0) {
			final float newY = Math.min(this.maxY, this.sprite.getY() + PLAYER_STEP);
			this.sprite.setY(newY);
			this.direction = Direction.UP;
			return true;
		}

		if (keycode == dnKey && Float.compare(this.sprite.getY(), 0) != 0) {
			final float newY = Math.max(0, this.sprite.getY() - PLAYER_STEP);
			this.sprite.setY(newY);
			this.direction = Direction.DOWN;
			return true;
		}

		return false;
	}

	public void doit() {
		switch (this.direction) {
			case UP:
				keyDown(this.upKey);
				break;
			case DOWN:
				keyDown(this.dnKey);
				break;
			default:
				// do nothing
		}
	}

	@Override
	public boolean keyUp(int keycode) {
		// todo: handle case with multiple keys down and up (e.g. 'w' down, then 's' down then 'w' up
		this.direction = Direction.NEUTRAL;
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}