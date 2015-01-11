package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class CharacterAssetManager 
{
	Body bodyArray[];
	Body currentBody;
	int eyeArray[];
	int mouthArray[];
	ColorPalette colorPaletteArray[];
	ColorPalette currentPalette;
	
	Pet pet;
	
	Pixmap bodyPixMap;
	Pixmap eyePixMap;
	Pixmap mouthPixMap;
	Sprite bodySprite;
	Sprite leftEyeSprite;
	Sprite rightEyeSprite;
	Sprite mouthSprite;
	
	public CharacterAssetManager()
	{
		eyeArray = new int[]{1, 2, 3};
		mouthArray = new int[]{1, 2, 3};
		
		//on each body (since they're different sizes), we need to manually define a region for:
		//eyes
		//mouth
		//nose
		//hair
		//the highest x position I want an eye to have is (160 - 25) = 135 -  = 115.
		//the highest y position I want an eye to have it 140 - 5
		Body body1 = new Body(new Rectangle(50, 40, 65, 40), new Rectangle(50, 10, 55, 60));
		Body body2 = new Body(new Rectangle(50, 40, 65, 40), new Rectangle(50, 10, 55, 60));
		Body body3 = new Body(new Rectangle(50, 40, 45, 40), new Rectangle(50, 10, 45, 60));

		bodyArray = new Body[]{body1, body2, body3};
		
		for(int i = 0; i < bodyArray.length; i++)
		{
			bodyArray[i].imageLocation = "body/body" + (i+1) + ".png";
		}
		
		//eyes are all 25x50
		//mouths are all 50x25
	}
	
	public Pet chooseRandomParts()
	{
		int body, eye, mouth;
		
		body = 1 + (int)(Math.random() * ((3 - 1) + 1));
		eye = 1 + (int)(Math.random() * ((3 - 1) + 1));
		mouth = 1 + (int)(Math.random() * ((3 - 1) + 1));
		
		currentBody = bodyArray[body-1];
		
		String bodyString = currentBody.imageLocation;
		String eyeString = "eye/eye" + eye + ".png";
		String mouthString = "mouth/mouth" + mouth + ".png";
		
		bodyPixMap = new Pixmap(Gdx.files.internal(bodyString));
		eyePixMap = new Pixmap(Gdx.files.internal(eyeString));
		mouthPixMap = new Pixmap(Gdx.files.internal(mouthString));
		
		colorSetup();
		
		Texture bodyTexture;
		Texture eyeTexture;
		Texture mouthTexture;
		
		bodyTexture = new Texture(bodyPixMap);
		eyeTexture = new Texture(eyePixMap);
		mouthTexture = new Texture(mouthPixMap);
		
		bodySprite = new Sprite(bodyTexture);
		leftEyeSprite = new Sprite(eyeTexture);
		rightEyeSprite = new Sprite(eyeTexture);
		mouthSprite = new Sprite(mouthTexture);
		
		//need to make sure left eye isn't touching mouth
		//need to make sure right eye isn't touching mouth
		eyeSetup(); //<--- this method makes sure the eyes aren't touching each other
		mouthSetup(); //<--- this makes sure the mouth is below the lowest eye and isn't touching either eye
		
		//these are some things that need to be adjusted before passing the pet to the game
		//I want to make sure that:
		//1.) no parts are touching (this includes both eyes, mouth, nose)
		//2.) the eyes are above the nose, the nose is above the mouth 
		pet = new Pet(bodySprite, leftEyeSprite, rightEyeSprite, mouthSprite, currentPalette);
		
		return pet;
	}
	
	public void eyeSetup()
	{
		boolean differentiateEyeYValues;
		int random;
		int eye_leftMinX;
		int eye_leftMaxX;
		int eye_leftRandomX;
		int eye_rightMinX;
		int eye_rightMaxX;
		int eye_rightRandomX;
		int eye_minY;
		int eye_maxY;
		int eye_randomY;
		int eye_secondRandomY;
		
		do
		{
			Gdx.app.log("DEBUG", "Running eye setup loop");
			differentiateEyeYValues = false;
			random = 1 + (int)(Math.random() * ((2 - 1) + 1));	//will either be 1 or 2
			if(random == 2)
			{
				differentiateEyeYValues = true;
			}
			
			//now we get to decide random positions for all of the parts
			//the body will have the same position of the pet itself
			//so body is tied to pet, everything else is tied to body
			//eye values first
			//for left eye x position
			eye_leftMinX = (int) currentBody.eyeRegion.x;
			eye_leftMaxX = (int) (eye_leftMinX + (currentBody.eyeRegion.width / 2));
			eye_leftRandomX = eye_leftMinX + (int)(Math.random() * (eye_leftMaxX - eye_leftMinX) + 1);
			
			Gdx.app.log("DEBUG", "left min x: " + eye_leftMinX);
			Gdx.app.log("DEBUG", "left max x: " + eye_leftMaxX);
			Gdx.app.log("DEBUG",  "left random X: " + eye_leftRandomX);
			leftEyeSprite.setX(eye_leftRandomX);
			
			//for right eye x position
			eye_rightMinX = eye_leftMaxX + 1;
			eye_rightMaxX = (int) (currentBody.eyeRegion.x + currentBody.eyeRegion.width);
			eye_rightRandomX = eye_rightMinX + (int)(Math.random() * (eye_rightMaxX - eye_rightMinX) + 1);
			
			Gdx.app.log("DEBUG", "right min x: " + eye_rightMinX);
			Gdx.app.log("DEBUG", "right max x: " + eye_rightMaxX);
			Gdx.app.log("DEBUG", "right random X: " + eye_rightRandomX);
			rightEyeSprite.setX(eye_rightRandomX);
			
			//need at least one random y value
			eye_minY = (int) currentBody.eyeRegion.y;
			eye_maxY = (int) (eye_minY + currentBody.eyeRegion.height);
			eye_randomY = eye_minY + (int)(Math.random() * (eye_maxY - eye_minY) + 1);
			leftEyeSprite.setY(eye_randomY);
			
			if(differentiateEyeYValues)
			{
				eye_secondRandomY = eye_minY + (int)(Math.random() * (eye_maxY - eye_minY) + 1);
				rightEyeSprite.setY(eye_secondRandomY);
			}
			else
			{
				rightEyeSprite.setY(eye_randomY);
			}
			
			Sprite tempSprite;
			if(leftEyeSprite.getX() > rightEyeSprite.getX())
			{
				tempSprite = leftEyeSprite;
				leftEyeSprite = rightEyeSprite;
				rightEyeSprite = leftEyeSprite;
			}
			
		} while(rightEyeSprite.getX() <= (leftEyeSprite.getX() + leftEyeSprite.getWidth()));
	}
	public void mouthSetup()
	{
		//setting up mouth position here
		int mouth_minX;
		int mouth_maxX;
		int mouth_randomX;
		int mouth_minY;
		int mouth_maxY;
		int mouth_randomY;
		
		do
		{
			Gdx.app.log("DEBUG", "Running mouth setup loop");
			mouth_minX = (int) currentBody.mouthRegion.x;
			mouth_maxX = (int) (currentBody.mouthRegion.x + currentBody.mouthRegion.width);
			mouth_randomX = mouth_minX + (int)(Math.random() * (mouth_maxX - mouth_minX) + 1);
			mouth_minY = (int) currentBody.mouthRegion.y;
			mouth_maxY = (int) (currentBody.mouthRegion.y + currentBody.mouthRegion.height);
			mouth_randomY = mouth_minY + (int)(Math.random() * (mouth_maxY - mouth_minY) + 1);
			mouthSprite.setPosition(mouth_randomX, mouth_randomY);
		} while((mouthSprite.getY() + mouthSprite.getHeight()) >= leftEyeSprite.getY() 
				|| (mouthSprite.getY() + mouthSprite.getHeight()) >= rightEyeSprite.getY());
	}

	public void colorSetup()
	{
		colorPaletteArray = new ColorPalette[3];
		
		//light blue, purpleish, light orange
		//main color = 2, 155, 255
		//secondary color = 255, 150, 66
		//extra color = 63, 70, 153
		Color color1 = Color.valueOf("ef8c42ff");
		colorPaletteArray[0] = new ColorPalette(color1, new Color(2, 155, 255, 1), new Color(63, 70, 153, 1));
		
		//light green, light blue, lightish red
		//main color = 183, 255, 0
		//secondary color = 34, 115, 255
		//extra color = 204, 46, 20
		color1 = Color.valueOf("b7ff00ff");
		colorPaletteArray[1] = new ColorPalette(color1, new Color(34, 115, 255, 1), new Color(204, 46, 20, 1));
		
		//light blue, light red, dark yellow
		//main color = 0, 184, 255
		//secondary color = 255, 78, 114
		//extra color = 204, 194, 20
		color1 = Color.valueOf("00b8ffff");
		colorPaletteArray[2] = new ColorPalette(color1, new Color(255, 78, 114, 1), new Color(204, 194, 20, 1));
		
		int i = 0 + (int)(Math.random() * ((2 - 0) + 1));
		Gdx.app.log("DEBUG", "Color palette chosen (0-2): " + i);
		currentPalette = colorPaletteArray[i];
		//Gdx.app.log("DEBUG", "Main color: " + colorPaletteArray[i].mainColor);
	}
	
	public ColorPalette getCurrentPalette()
	{
		return currentPalette;
	}
}

class Body
{
	public Rectangle eyeRegion;
	public Rectangle mouthRegion;
	
	public String imageLocation;
	
	public Body(Rectangle eR, Rectangle mR)
	{
		eyeRegion = eR;
		mouthRegion = mR;
	}
}

class ColorPalette
{
	Color mainColor;
	Color secondaryColor;
	Color extraColor;
	
	public ColorPalette(Color c1, Color c2, Color c3)
	{	
		mainColor = c1;
		secondaryColor = c2;
		extraColor = c3;
	}
	
	public Color getMainColor()
	{
		return mainColor;
	}
	public Color getSecondaryColor()
	{
		return secondaryColor;
	}
	public Color getExtraColor()
	{
		return extraColor;
	}
}
