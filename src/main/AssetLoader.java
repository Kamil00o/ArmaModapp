package main;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class AssetLoader {
	public static Image loadImage(String filename) {
		try {
			InputStream is = AssetLoader.class.getResourceAsStream(filename);
			return ImageIO.read(is);
		} catch (IOException e) {
			return null;
		}
	}
}
