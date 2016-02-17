package gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.MediaTracker;
import java.io.File;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class LoadingProxy implements Icon {

	volatile ImageIcon imageIcon;
	final String imagePath;
	Thread retrievalThread;
	boolean retrieving = false;

	public LoadingProxy(String path) {
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
							URL imageURL = null;
							if (imageFile.exists()) {
								imageURL = imageFile.toURI().toURL();
							}
							if (imageURL != null) {
								imageIcon = new ImageIcon(imageURL);
							}
							if (imageIcon == null) {
								retrieving = false;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				retrievalThread.start();
			}
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				// do nothing
//			}
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
