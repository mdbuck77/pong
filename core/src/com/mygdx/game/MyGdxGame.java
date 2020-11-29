package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MyGdxGame extends ApplicationAdapter {
  private OrthographicCamera orthographicCamera;
  private SpriteBatch batch;
  private Ball ball;
  private Player player1;

  @Override
  public void create() {
    int graphicsWidth = Gdx.graphics.getWidth();
    int graphicsHeight = Gdx.graphics.getHeight();

    this.player1 = new Player(Input.Keys.W, Input.Keys.S, graphicsHeight);

    Gdx.input.setInputProcessor(new InputMultiplexer(player1));

    this.orthographicCamera = new OrthographicCamera(graphicsWidth, graphicsHeight);
    this.orthographicCamera.translate(graphicsWidth / 2f, graphicsHeight / 2f);

    final World world = new World(0, 0, graphicsWidth - 1, graphicsHeight - 1, this.player1);
    this.ball = new Ball(world, graphicsWidth, graphicsHeight);

    this.batch = new SpriteBatch();
  }

  @Override
  public void render() {
    this.orthographicCamera.update();

    this.player1.moveIt();

    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // draw center of field
    final ShapeRenderer shapeRenderer = new ShapeRenderer();
    shapeRenderer.setProjectionMatrix(orthographicCamera.combined);
    shapeRenderer.setColor(Color.WHITE);

    final float midX = Gdx.graphics.getWidth() / 2f;

    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

    float y = 0;
    while (y < Gdx.graphics.getHeight()) {
      shapeRenderer.line(midX, y, midX, y + 8);
      y += 16;
    }

    shapeRenderer.end();

    // draw players and ball
    batch.setProjectionMatrix(this.orthographicCamera.combined);

    batch.begin();

    ball.draw(this.batch);
    player1.draw(this.batch);

    batch.end();
  }

  @Override
  public void dispose() {
    this.player1.dispose();
    this.ball.dispose();
  }

  @Override
  public void resize(int width, int height) {
    this.orthographicCamera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    this.orthographicCamera.update();
  }
}
