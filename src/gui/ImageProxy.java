package gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ImageProxy implements Icon {

	volatile ImageIcon imageIcon;
	final String imagePath;
	Thread retrievalThread;
	boolean retrieving = false;
	MediaTracker imageTracker;
	boolean isTracking = false;
	boolean isAdded = false;

	public ImageProxy(String path) {
		this.imagePath = path;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		if (imageIcon != null && imageIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
			imageIcon.paintIcon(c, g, x, y);
		}
		else {
			g.drawString("Reticulating splines...", x, y);
			if (!retrieving) {
				retrieving = true;
				
				retrievalThread = new Thread(new Runnable() {
					public void run() {
						try {
							File imageFile = new File(imagePath);
							if (imageFile != null && imageFile.exists()) {
								Path path = Paths.get(imageFile.toURI());
								byte[] data = Files.readAllBytes(path);
								imageIcon = new ImageIcon(data);
								if (imageIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
									imageIcon.getImage().flush();
								}
								else {
									imageFile.delete();
								}
							}
							if (imageIcon == null || imageIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
								retrieving = false;
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
				retrievalThread.start();
			}
		}
		c.repaint();
	}


	@Override
	public int getIconWidth() {
		if (imageIcon != null) {
			return imageIcon.getIconWidth();
		} else {
			return 800;
		}
	}

	@Override
	public int getIconHeight() {
		if (imageIcon != null) {
			return imageIcon.getIconHeight();
		} else {
			return 400;
		}
	}

}