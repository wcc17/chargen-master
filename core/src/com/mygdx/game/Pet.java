package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Pet extends Actor
{
	public Texture bodyTexture;
	public Texture leftEyeTexture;
	public Texture rightEyeTexture;
	public Texture mouthTexture;
	
	public Sprite bodySprite;
	public Sprite leftEyeSprite;
	public Sprite rightEyeSprite;
	public Sprite mouthSprite;
	
	public ColorPalette colorPalette;
	
	public boolean flipped;
	
	public Pet(Sprite bS, Sprite l_eS, Sprite r_eS, Sprite mS, ColorPalette cP)
	{
		bodySprite = bS;
		leftEyeSprite = l_eS;
		rightEyeSprite = r_eS;
		mouthSprite = mS;
		colorPalette = cP;
		
		this.setX(Gdx.graphics.getWidth()/2 - (this.getWidth()));
		this.setY(Gdx.graphics.getHeight()/2 - (this.getHeight()));
		bodySprite.setPosition(this.getX(), this.getY());
		
		Gdx.app.log("DEBUG", "Pet position: (" + this.getX() + ", " + this.getY() + ")");
		Gdx.app.log("DEBUG", "Body position: (" + bodySprite.getX() + ", " + bodySprite.getY() + ")");
		Gdx.app.log("DEBUG", "Left Eye Position: (" + leftEyeSprite.getX() + ", " + leftEyeSprite.getY() + ")");
		Gdx.app.log("DEBUG", "Right Eye Position: (" + rightEyeSprite.getX() + ", " + rightEyeSprite.getY() + ")");
		Gdx.app.log("DEBUG", "Mouth position: (" + mouthSprite.getX() + ", " + mouthSprite.getY() + ")");
	}
	
	@Override
    public void draw(Batch batch, float alpha)
	{
		super.draw(batch, alpha);
		
		batch.setColor(colorPalette.mainColor);
        batch.draw(bodySprite, bodySprite.getX(), bodySprite.getY());
        batch.setColor(Color.WHITE);
        
        //eye sprites positions are set according to a 160x140 texture (ie, x can be no higher than 160, etc)
        //so their positions always need to be in addition to body's position
        batch.draw(leftEyeSprite, bodySprite.getX() + leftEyeSprite.getX(), bodySprite.getY() + leftEyeSprite.getY());
        batch.draw(rightEyeSprite, bodySprite.getX() + rightEyeSprite.getX(), bodySprite.getY() + rightEyeSprite.getY());
        batch.draw(mouthSprite, bodySprite.getX() + mouthSprite.getX(), bodySprite.getY() + mouthSprite.getY());
    }
	
	public void setFlip(boolean set)
	{
		leftEyeSprite.setFlip(set, false);
		rightEyeSprite.setFlip(set, false);
		mouthSprite.setFlip(set, false);
		//bodySprite.setFlip(set, false);
		
		boolean newPositionsChosen = false;
		float newLeftEyeX = 0;
		float newRightEyeX = 0;
		float newMouthX = 0;
		
		//flip all body parts
		if(set)
		{
			//if its not already flipped, otherwise they're in the right place
			if(!flipped)
			{
				//find how much distance between body part and width of body
				//this is where the flipped body part should go + body's position
				newPositionsChosen = true;
				
				//bodySprite.getX() = 400
				//bodySprite.getWidth() = 160
				//right side of bodySprite at 560 (bodySprite.getX() + bodySprite.getWidth()
				//eye x = 40	(leftEyeSprite.getX())
				//eye x + width = 65 (leftEyeSprite.getX() + leftEyeSprite.getWidth())
				//eye x + width + bodySprite.getX() = 465 (leftEyeSprite.getX() + leftEyeSprite.getWidth() + bodySprite.getX())
				//
				//space between right side and eye = 560 - 465
				//the eye on the other side needs to be 560 - 465 away
				//(bodySprite.getX() + bodySprite.getWidth()) 
				//					- (leftEyeSprite.getX() + leftEyeSprite.getWidth() + bodySprite.getX())
				//bodySprite.getWidth() - (leftEyeSprite.getX() + leftEyeSprite.getWidth())
				
				//gets the distance from the eye to the right side of the body
				//puts the eye at the left side of the body plus this distance
				newLeftEyeX = bodySprite.getWidth() - (leftEyeSprite.getX() + leftEyeSprite.getWidth());
				newRightEyeX = bodySprite.getWidth() - (rightEyeSprite.getX() + rightEyeSprite.getWidth());
				newMouthX = bodySprite.getWidth() - (mouthSprite.getX() + mouthSprite.getWidth());
			}
		}
		else if(!set)
		{
			//gets area between beginning of body and eye
			//then subtracts this value from the right side of the body
			//which gives the new position for the eye
			if(flipped)
			{
				//need a bodysprite get x and get width
				//bodySprite.getX() + leftEyeSprite.getX()
				//leftEyeSprite
				//(bodySprite.getX() + bodySprite.getWidth()) - leftEyeSprite.getX()
				//newMouthX = bodySprite.getWidth() - (mouthSprite.getX() + mouthSprite.getWidth())
				//a = b - (c + d)
				//i want c
				//a = b - c - d
				//a - b + d = c
				newPositionsChosen = true;
				
				newLeftEyeX = (leftEyeSprite.getX() - bodySprite.getWidth() + leftEyeSprite.getWidth()) * (-1);
				newRightEyeX = (rightEyeSprite.getX() - bodySprite.getWidth() + rightEyeSprite.getWidth()) * (-1);
				newMouthX = (mouthSprite.getX() - bodySprite.getWidth() + mouthSprite.getWidth()) * (-1);
			}
		}
		
		//set flipped so we can tell if its flipped next time
		flipped = set;
		
		if(newPositionsChosen)
		{
			leftEyeSprite.setPosition(newLeftEyeX, leftEyeSprite.getY());
			rightEyeSprite.setPosition(newRightEyeX,  rightEyeSprite.getY());
			mouthSprite.setPosition(newMouthX, mouthSprite.getY());
		}
		
	}
	
	public boolean isFlipped()
	{
		return flipped;
	}
}
