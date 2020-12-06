package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;

public final class World {
	private final int minX;
	private final int minY;
	private final int maxX;
	private final int maxY;
	private final Player player1;
	private final Player player2;
	private final Sound collisionSound;
	private final Sound scoreSound;

	public World(int minX, int minY, int maxX, int maxy, Player player1, Player player2, Sound collisionSound, Sound scoreSound) {
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxy;
		this.player1 = player1;
		this.player2 = player2;
		this.collisionSound = collisionSound;
		this.scoreSound = scoreSound;
	}

	public void setPosition(final Ball ball, float newX, float newY) {
		ball.setPosition(newX, newY);

		final Player thePlayer;
		if (ball.dx() < 0) {
			// only need to check for collisions with player 1
			thePlayer = this.player1;
		} else {
			// only need to check for collisions with player 2
			thePlayer = this.player2;
		}

		final Rectangle ballBoundingRectangle = ball.boundingRectangle();

		final Rectangle playerBounds = thePlayer.boundingRectangle();

		if (ballBoundingRectangle.overlaps(playerBounds)) {
			this.collisionSound.play();

			final float signum = Math.signum(ball.dx());

			final float v = (ballBoundingRectangle.y - playerBounds.y) / playerBounds.getHeight();

			if (v < 1d/8d) {
				// bottom edge of player
				ball.dy(signum * Ball.BALL_STEP * 2);
			} else if (v < 3d/8d) {
				// bottom middle of paddle
				ball.dy(signum * Ball.BALL_STEP);
			} else if (v < 5d/8d) {
				// center of paddle
				ball.dy(0);
			} else if (v < 7d/8d) {
				// top middle of paddle
				ball.dy(signum * Ball.BALL_STEP);
			} else if (v < 1) {
				// top of paddle
				ball.dy(signum * Ball.BALL_STEP * 2);
			}

			ball.dx(-ball.dx());

			ball.setPosition(playerBounds.x + playerBounds.width / 2, ballBoundingRectangle.y);

		} else if (ballBoundingRectangle.x + ballBoundingRectangle.getWidth() - 1 > this.maxX) {
			this.scoreSound.play();
			ball.dx(-ball.dx());
			ball.setPosition(this.maxX - playerBounds.width + ball.dx(), ballBoundingRectangle.y);
			this.player1.score();
		} else if (ballBoundingRectangle.x < this.minX) {
			this.scoreSound.play();
			ball.dx(-ball.dx());
			ball.setPosition(this.minX + ball.dx(), ballBoundingRectangle.y);
			this.player2.score();
		} else if (ballBoundingRectangle.y + ballBoundingRectangle.getHeight() > this.maxY) {
			this.collisionSound.setPitch(this.collisionSound.play(), 0.5f);
			ball.dy(-ball.dy());
			ball.setPosition(ballBoundingRectangle.x, this.maxY - ballBoundingRectangle.getHeight());
		} else if (ballBoundingRectangle.y < this.minY) {
			this.collisionSound.setPitch(this.collisionSound.play(), 0.5f);
			ball.dy(-ball.dy());
			ball.setPosition(ballBoundingRectangle.x, this.minY);
		}
	}

}
