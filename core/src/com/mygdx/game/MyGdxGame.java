package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

public class MyGdxGame extends ApplicationAdapter {
  private static final int BALL_DIMENSION = 16;

  private int graphicsWidth;
  private int graphicsHeight;

  private enum Direction {
    LEFT,
    RIGHT;
  }

  private OrthographicCamera orthographicCamera;

  private SpriteBatch batch;

  private Texture ballTexture;
  private Sprite sprite;

  // ball stuff
  private float x;
  private float y;
  private Direction direction = Direction.LEFT;

  // player 1
  private Player player1;

  @Override
  public void create() {
    player1 = new Player(Input.Keys.W, Input.Keys.S, 0, 0);

    Gdx.input.setInputProcessor(new InputMultiplexer(player1));

    graphicsWidth = Gdx.graphics.getWidth();
    graphicsHeight = Gdx.graphics.getHeight();

    this.orthographicCamera = new OrthographicCamera(graphicsWidth, graphicsHeight);
    this.orthographicCamera.translate(graphicsWidth / 2, graphicsHeight / 2);

    this.x = graphicsWidth / 2 - BALL_DIMENSION / 2;
    this.y = graphicsHeight / 2 - BALL_DIMENSION / 2;

    final Pixmap ball = ball();
    this.ballTexture = new Texture(ball);
    ball.dispose();

    this.sprite = new Sprite(this.ballTexture);

    this.batch = new SpriteBatch();

    Timer.schedule(new Timer.Task() {
      @Override
      public void run() {
        switch (direction) {
          case LEFT:
            if (x == 0) {
              direction = Direction.RIGHT;
              x = 0;
            } else {
              x = x - 4;
            }
            break;

          case RIGHT:
            if (x >= graphicsWidth - sprite.getWidth()) {
              direction = Direction.LEFT;
              x = graphicsWidth - sprite.getWidth();
            } else {
              x = x + 4;
            }
            break;
          default:
            throw new UnsupportedOperationException(String.valueOf(direction));
        }
        sprite.setPosition(x, y);
      }
    }, 0, 0.1f);
  }

  @Override
  public void render() {
    this.player1.doit();

    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    this.orthographicCamera.update();
    batch.setProjectionMatrix(this.orthographicCamera.combined);

    batch.begin();

    sprite.draw(this.batch);
    player1.draw(this.batch);

    batch.end();
  }

  @Override
  public void dispose() {
    batch.dispose();
    ballTexture.dispose();
  }

  private static Pixmap ball() {
    final Pixmap pixmap = new Pixmap(BALL_DIMENSION, BALL_DIMENSION, Pixmap.Format.RGB888);
    pixmap.setColor(Color.WHITE);
    pixmap.fill();
    return pixmap;
  }
}
