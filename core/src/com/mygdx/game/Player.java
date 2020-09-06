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
  private Direction direction;
  private int x;
  private int y;

  public Player(int upKey, int dnKey, int x, int y) {
    this.upKey = upKey;
    this.dnKey = dnKey;
    this.direction = Direction.NEUTRAL;
    this.x = x;
    this.y = y;
    this.sprite = new Sprite(TEXTURE);
  }

  public void draw(final SpriteBatch batch) {
    sprite.draw(batch);
  }

  public void dispose() {
    TEXTURE.dispose();
  }

  @Override
  public boolean keyDown(int keycode) {
    System.out.println("keycode = " + keycode);
    if (keycode == upKey) {
      this.sprite.setY(this.sprite.getY() + 1);
      this.direction = Direction.UP;
      return true;
    } else if (keycode == dnKey) {
      this.sprite.setY(this.sprite.getY() - 1);
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
