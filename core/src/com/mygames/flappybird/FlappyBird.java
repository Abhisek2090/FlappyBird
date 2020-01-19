package com.mygames.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;

import java.util.Random;

import javax.xml.soap.Text;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture topTube;
	Texture bottomTube;
	Texture gameOver;
	Texture[] birds;
	//ShapeRenderer shapeRenderer;
	Circle birdCircle;
	int score = 0;
	int scoringTube = 0;

	int flapState = 0;
	float birdY = 0;
	float velocity = 0;

	int gameState = 0;
	float gravity =2;
	float gap = 600;

	float maxTubeOffset;
	Random randomGenerator;
	BitmapFont bitmapFont;



	int numberOfTubes = 4;
	float[] tubeX = new float[numberOfTubes];
	float[] tubeOffset = new float[numberOfTubes];
	float tubeVelocity = 4;

	float distanceBetweenTubes;
	Rectangle[] topTubesRectangles;
	Rectangle[] bottomTubesRectangles;


	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		gameOver = new Texture("gameover.png");
		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");



		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");

	//	shapeRenderer = new ShapeRenderer();
		birdCircle = new Circle();
		bitmapFont = new BitmapFont();
		bitmapFont.setColor(Color.WHITE);
		bitmapFont.getData().setScale(10);

		maxTubeOffset = Gdx.graphics.getHeight() / 2 - gap/2 - 100;
		randomGenerator = new Random();

		distanceBetweenTubes = Gdx.graphics.getWidth() * 3/4;

		topTubesRectangles = new Rectangle[numberOfTubes];
		bottomTubesRectangles = new Rectangle[numberOfTubes];

		startGame();

		}

		public void startGame() {
			birdY = Gdx.graphics.getHeight()/2 - birds[0].getHeight()/2;

			for(int i = 0; i<numberOfTubes; i++) {


				tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 400);

				tubeX[i] = Gdx.graphics.getWidth()/2 - topTube.getWidth()/2 + Gdx.graphics.getWidth() + i*distanceBetweenTubes;

				topTubesRectangles[i] = new Rectangle();
				bottomTubesRectangles[i] = new Rectangle();
		}





	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState == 1) {

			if(tubeX[scoringTube] < Gdx.graphics.getWidth()/2) {
				score++;

				Gdx.app.log("Score", String.valueOf(score));

				if(scoringTube < numberOfTubes-1) {
					scoringTube ++;

				}
				else  {
					scoringTube = 0;
				}
			}


			if(Gdx.input.justTouched()) {
				velocity = -30;


			}

			for(int i = 0; i<numberOfTubes; i++) {

				if(tubeX[i] < - topTube.getWidth()) {

					tubeX[i] += numberOfTubes * distanceBetweenTubes;

					tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
				}

				else {

					tubeX[i] = tubeX[i] - tubeVelocity;


				}


				batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
				batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);

				topTubesRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
				bottomTubesRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());

			}



			if(birdY > 0) {
				velocity = velocity + gravity;
				birdY -= velocity;
			}

			else {
				gameState = 2;
			}

		}

		else if(gameState == 0) {
			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		}

		else if(gameState == 2) {
			batch.draw(gameOver,Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2,
					Gdx.graphics.getHeight() / 2- gameOver.getHeight() / 2);

			if(Gdx.input.justTouched()) {
				gameState = 1;
				startGame();
				scoringTube = 0;
				score = 0;
				velocity = 0;
			}
		}


		if (flapState == 0) {
			flapState = 1;
		} else {
			flapState = 0;
		}


		batch.draw(birds[flapState], Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2, birdY);
		bitmapFont.draw(batch, String.valueOf(score), 100,200);
		batch.end();

		birdCircle.set(Gdx.graphics.getWidth()/2, birdY + birds[flapState].getHeight() / 2,
				birds[flapState].getWidth() / 2);



		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.RED);
		//shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);

		for(int i = 0; i<numberOfTubes; i++) {
			//shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight()/2 + gap/2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
			//shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 - gap/2 - bottomTube.getHeight()+tubeOffset[i] , bottomTube.getWidth(), bottomTube.getHeight());

			if(Intersector.overlaps(birdCircle, topTubesRectangles[i]) || Intersector.overlaps(birdCircle, bottomTubesRectangles[i])) {
				gameState = 2;
			}
		}
		//shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
