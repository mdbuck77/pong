package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MyGdxGame extends ApplicationAdapter {
  private static final float DIGIT_WIDTH = 16;
  private static final float DIGIT_HEIGHT = DIGIT_WIDTH * 2;

  private OrthographicCamera orthographicCamera;
  private SpriteBatch batch;
  private Ball ball;
  private Player player1;
  private Player player2;
  private Sound collisionSound;
  private Sound scoreSound;

  @Override
  public void create() {
    this.collisionSound = Gdx.audio.newSound(Gdx.files.internal("collision.wav"));
    this.scoreSound = Gdx.audio.newSound(Gdx.files.internal("score-sound.wav"));

    int graphicsWidth = Gdx.graphics.getWidth();
    int graphicsHeight = Gdx.graphics.getHeight();

    this.player1 = new Player(Input.Keys.W, Input.Keys.S, graphicsHeight, 0);
    this.player2 = new Player(Input.Keys.I, Input.Keys.K, graphicsHeight, graphicsWidth - 10);

    Gdx.input.setInputProcessor(new InputMultiplexer(player1, player2));

    this.orthographicCamera = new OrthographicCamera(graphicsWidth, graphicsHeight);
    this.orthographicCamera.translate(graphicsWidth / 2f, graphicsHeight / 2f);

    final World world = new World(0, 0, graphicsWidth - 1, graphicsHeight - 1, this.player1, player2, collisionSound, scoreSound);
    this.ball = new Ball(world, graphicsWidth, graphicsHeight);

    this.batch = new SpriteBatch();
  }

  @Override
  public void render() {
    this.orthographicCamera.update();

    this.player1.moveIt();
    this.player2.moveIt();

    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // draw player 1's score
    drawScore(this.player1.getScore(), this.orthographicCamera.viewportWidth / 4);
    drawScore(this.player2.getScore(), 3f * this.orthographicCamera.viewportWidth / 4);

    // draw center of field
    final ShapeRenderer shapeRenderer = new ShapeRenderer();
    shapeRenderer.setProjectionMatrix(orthographicCamera.combined);
    shapeRenderer.setColor(Color.WHITE);

    final float midX = this.orthographicCamera.viewportWidth / 2;

    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

    float y = 0;
    while (y < this.orthographicCamera.viewportHeight) {
      shapeRenderer.line(midX, y, midX, y + 8);
      y += 16;
    }

    shapeRenderer.end();

    // draw players and ball
    batch.setProjectionMatrix(this.orthographicCamera.combined);

    batch.begin();

    ball.draw(this.batch);
    player1.draw(this.batch);
    player2.draw(this.batch);

    batch.end();
  }

  private void drawScore(final int score, final float midX, final ShapeRenderer shapeRenderer) {
    switch (score) {
      case 0:
        shapeRenderer.rectLine(midX - DIGIT_WIDTH / 2,            0, midX + DIGIT_WIDTH / 2,            0, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX + DIGIT_WIDTH / 2,            0, midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT, midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT, midX - DIGIT_WIDTH / 2,            0, 4, Color.WHITE, Color.WHITE);
        break;

      case 1:
        shapeRenderer.rect(midX + DIGIT_WIDTH / 2f, 0f, 4, DIGIT_HEIGHT, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);
        break;

      case 2:
        shapeRenderer.rectLine(midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT, midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT, midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT - DIGIT_HEIGHT / 2f, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT - DIGIT_HEIGHT / 2f, midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT - DIGIT_HEIGHT / 2f, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT - DIGIT_HEIGHT / 2f, midX - DIGIT_WIDTH / 2, 0, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX - DIGIT_WIDTH / 2, 0, midX + DIGIT_WIDTH / 2, 0, 4, Color.WHITE, Color.WHITE);
        break;

      case 3:
        shapeRenderer.rectLine(midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT, midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT, midX + DIGIT_WIDTH / 2, 0, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX + DIGIT_WIDTH / 2, 0, midX - DIGIT_WIDTH / 2, 0, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT / 2, midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT / 2, 4, Color.WHITE, Color.WHITE);
        break;

      case 4:
        shapeRenderer.rectLine(midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT, midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT / 2, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT / 2, midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT / 2, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT, midX + DIGIT_WIDTH / 2, 0, 4, Color.WHITE, Color.WHITE);
        break;

      case 5:
        shapeRenderer.rectLine(midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT, midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT, midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT / 2, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT / 2, midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT / 2, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT / 2, midX + DIGIT_WIDTH / 2, 0, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX + DIGIT_WIDTH / 2, 0, midX - DIGIT_WIDTH / 2, 0, 4, Color.WHITE, Color.WHITE);
        break;

      case 6:
        shapeRenderer.rectLine(midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT, midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT, midX - DIGIT_WIDTH / 2, 0, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX - DIGIT_WIDTH / 2, 0, midX + DIGIT_WIDTH / 2, 0, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX + DIGIT_WIDTH / 2, 0, midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT / 2, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT / 2, midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT / 2, 4, Color.WHITE, Color.WHITE);
        break;

      case 7:
        shapeRenderer.rectLine(midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT, midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT, midX + DIGIT_WIDTH / 2, 0, 4, Color.WHITE, Color.WHITE);
        break;

      case 8:
        shapeRenderer.rectLine(midX - DIGIT_WIDTH / 2,           0, midX + DIGIT_WIDTH / 2,           0, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX + DIGIT_WIDTH / 2,           0, midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT, midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT, midX - DIGIT_WIDTH / 2,           0, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT / 2, midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT / 2, 4, Color.WHITE, Color.WHITE);
        break;

      case 9:
        shapeRenderer.rectLine(midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT / 2, midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT / 2, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT / 2, midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX - DIGIT_WIDTH / 2, DIGIT_HEIGHT, midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX + DIGIT_WIDTH / 2, DIGIT_HEIGHT, midX + DIGIT_WIDTH / 2, 0, 4, Color.WHITE, Color.WHITE);
        shapeRenderer.rectLine(midX + DIGIT_WIDTH / 2, 0, midX - DIGIT_WIDTH / 2, 0, 4, Color.WHITE, Color.WHITE);
        break;

      default:
        final String strScore = String.valueOf(score);
        float newMidX = midX - (DIGIT_WIDTH + 10) * strScore.length() / 2f;
        for (int i = 0, len = strScore.length(); i < len; ++i) {
          final char c = strScore.charAt(i);
          drawScore(Integer.parseInt(Character.toString(c)), newMidX);
          newMidX += DIGIT_WIDTH + 10;
        }
        break;
    }
  }

  private void drawScore(final int score, final float midX) {
    final ShapeRenderer shapeRenderer = new ShapeRenderer();
    shapeRenderer.setProjectionMatrix(orthographicCamera.combined);
    shapeRenderer.translate(0, orthographicCamera.viewportHeight - DIGIT_HEIGHT - 10, 0);
    shapeRenderer.setColor(Color.WHITE);

    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

    drawScore(score, midX, shapeRenderer);

    shapeRenderer.end();
  }

  @Override
  public void dispose() {
    this.collisionSound.dispose();
    this.scoreSound.dispose();
    this.player1.dispose();
    this.player2.dispose();
    this.ball.dispose();
  }
}
