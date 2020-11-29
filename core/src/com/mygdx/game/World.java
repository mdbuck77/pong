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

		if (ballBoundingRectangle.overlaps(playerBounds)) {
			// left player

			float v = (ballBoundingRectangle.y + ballBoundingRectangle.getHeight() / 2 - playerBounds.y) / playerBounds.height;

			if (v < 1d/8d) {
				// bottom edge of player
				ball.dy(-Ball.BALL_STEP * 2);
			} else if (v < 3d/8d) {
				// bottom middle of paddle
				ball.dy(-Ball.BALL_STEP * 1.5f);
			} else if (v < 5d/8d) {
				// center of paddle
				ball.dy(0);
			} else if (v < 7d/8d) {
				// top middle of paddle
				ball.dy(Ball.BALL_STEP * 1.5f);
			} else if (v < 1) {
				// top of paddle
				ball.dy(Ball.BALL_STEP * 2);
			}

			ball.dx(-ball.dx());

			ball.setPosition(playerBounds.x + playerBounds.width / 2, ballBoundingRectangle.y);

		} else if (ballBoundingRectangle.x + ballBoundingRectangle.getWidth() - 1 > this.maxX) {
			ball.dx(-ball.dx());
			ball.setPosition(this.maxX - playerBounds.width + ball.dx(), ballBoundingRectangle.y);
		} else if (ballBoundingRectangle.x < this.minX) {
			ball.dx(-ball.dx());
			ball.setPosition(this.minX + ball.dx(), ballBoundingRectangle.y);
		} else if (ballBoundingRectangle.y >= this.maxY) {
			ball.dy(-ball.dy());
		} else if (ballBoundingRectangle.y <= this.minY) {
			ball.dy(-ball.dy());
		}
	}

}
