package com.itensis.ecat.initialize;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
class CreateImage {
	static String createImage(String path) {
		boolean imageCreatedSuccessfull = false;
		try {
			String text = "Image";

			BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = img.createGraphics();
			Font font = new Font("Arial", Font.PLAIN, 48);
			g2d.setFont(font);
			FontMetrics fm = g2d.getFontMetrics();
			int width = fm.stringWidth(text);
			int height = fm.getHeight();
			g2d.dispose();

			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			g2d = img.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
					RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
					RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_DITHERING,
					RenderingHints.VALUE_DITHER_ENABLE);
			g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
					RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
					RenderingHints.VALUE_STROKE_PURE);
			g2d.setFont(font);
			fm = g2d.getFontMetrics();
			g2d.setColor(Color.BLACK);
			g2d.drawString(text, 0, fm.getAscent());
			g2d.dispose();

			File file = new File(path + "Text.png");
			imageCreatedSuccessfull = ImageIO.write(img, "png", file);
			if(imageCreatedSuccessfull){
				return "Text.png";
			}else{
				return "";
			}
		}catch (IOException ex){
			log.error(ex.getMessage(), ex);
			return "";
		}
	}
}
