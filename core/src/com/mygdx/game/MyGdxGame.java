package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
  private OrthographicCamera orthographicCamera;
  private SpriteBatch batch;
  private Ball ball;
  private Player player1;

  @Override
  public void create() {
    int graphicsWidth = Gdx.graphics.getWidth();
    int graphicsHeight = Gdx.graphics.getHeight();

    player1 = new Player(Input.Keys.W, Input.Keys.S, graphicsHeight);

    Gdx.input.setInputProcessor(new InputMultiplexer(player1));

    this.orthographicCamera = new OrthographicCamera(graphicsWidth, graphicsHeight);
    this.orthographicCamera.translate(graphicsWidth / 2f, graphicsHeight / 2f);

    this.ball = new Ball(graphicsWidth, graphicsHeight);

    this.batch = new SpriteBatch();
  }

  @Override
  public void render() {
    this.player1.doit();

    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    this.orthographicCamera.update();
    batch.setProjectionMatrix(this.orthographicCamera.combined);

    batch.begin();

    ball.draw(this.batch);
    player1.draw(this.batch);

    batch.end();
  }

  @Override
  public void dispose() {
    batch.dispose();
    this.ball.dispose();
  }
}
