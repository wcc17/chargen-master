package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class MyGdxGame extends ApplicationAdapter 
{
	SpriteBatch batch;
	Texture characterTexture;
	Pet pet;
	Stage stage;
	CharacterAssetManager characterAssetManager;
	
	TextButton button;
	BitmapFont buttonFont;
	String buttonText;
	TextButton flipButton;
	String flipButtonText;
	
	@Override
	public void create () 
	{
		stage = new Stage();
		batch = (SpriteBatch) stage.getBatch();
		
		characterAssetManager = new CharacterAssetManager();
		pet = characterAssetManager.chooseRandomParts();
		
		TextButtonStyle style = new TextButtonStyle();
		buttonFont = new BitmapFont();
		style.font = buttonFont;
		
		button = new TextButton("Press to randomize", style);
		button.setPosition(10, 10);
		button.setSize(100, 50);
		
		button.addListener(new InputListener() 
		{
	        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) 
	        {
	        	pet.remove();
	        	pet = characterAssetManager.chooseRandomParts();
	        	stage.addActor(pet);
	        	return true;
	        }
	        public void touchUp(InputEvent event, float x, float y, int pointer, int button) 
	        {
	        	
	        }
		});
		
		flipButton = new TextButton("Press to flip sprite", style);
		flipButton.setSize(100, 50);
		flipButton.setPosition(Gdx.graphics.getWidth() - 10 - flipButton.getWidth(), 10);
		
		flipButton.addListener(new InputListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) 
	        {
				if(pet.isFlipped())
				{
					pet.setFlip(false);
				}
				else
				{
					pet.setFlip(true);
				}
	        	return true;
	        }
	        public void touchUp(InputEvent event, float x, float y, int pointer, int button) 
	        {
	        	
	        }
		});
		
		Gdx.input.setInputProcessor(stage);
		stage.addActor(button);
		stage.addActor(flipButton);
		stage.addActor(pet);
	}

	@Override
	public void render () 
	{
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
		
		//batch.begin();
		//batch.draw(characterTexture, 0, 0);
		//batch.end();
	}
}
