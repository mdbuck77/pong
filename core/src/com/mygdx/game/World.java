package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

public final class World {
	private final int minX;
	private final int minY;
	private final int maxX;
	private final int maxY;
	private final Player player;

	public World(int minX, int minY, int maxX, int maxy, Player player) {
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxy;
		this.player = player;
	}

	public void setPosition(final Ball ball, float newX, float newY) {
		ball.setPosition(newX, newY);
		final Rectangle ballBoundingRectangle = ball.boundingRectangle();

		final Rectangle playerBounds = player.boundingRectangle();
		final float ballDX = ball.dx();

		if (ballBoundingRectangle.overlaps(playerBounds)) {
			System.out.println("collided!!!");

			ball.dx(-ballDX);

			ball.setPosition(playerBounds.x + playerBounds.width, ballBoundingRectangle.y);
		} else if (ballBoundingRectangle.x + ballBoundingRectangle.getWidth() - 1 > this.maxX) {
			ball.dx(-ballDX);
			ball.setPosition(ballBoundingRectangle.x - ballBoundingRectangle.getWidth(), ballBoundingRectangle.y);
		} else if (ballBoundingRectangle.x < 0) {
			ball.dx(-ballDX);
			ball.setPosition(playerBounds.x, ballBoundingRectangle.y);
		}
	}

}
