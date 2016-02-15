package gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.MediaTracker;

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
		System.out.println("painticon entered");
		//while (imageIcon == null || imageIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
			System.out.println("paint icon LOOP");
			g.drawString("Reticulating splines...", x, y);
			if (!retrieving) {
				retrieving = true;

				retrievalThread = new Thread(new Runnable() {
					public void run() {
						System.out.println("THREAD RUNING!!!!!!");
						try {
							while (imageIcon == null || imageIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
								imageIcon = new ImageIcon(imagePath);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				retrievalThread.start();
//				try {
//					retrievalThread.start();
//					retrievalThread.join();
//				} catch (InterruptedException e) {
//					// do nothing???
//				}
//				retrieving = false;
			//}
			c.repaint();
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				// do nothing
//			}
		}
		
//		System.out.println("imageIcon: " + imageIcon);
//		System.out.printf("Aborted %d, Errored %d, Complete %d\n", 
//				MediaTracker.ABORTED, MediaTracker.ERRORED, MediaTracker.COMPLETE);;
//		System.out.println("load status: " + imageIcon.getImageLoadStatus());
//		imageIcon.paintIcon(c,g,x,y);
//		System.out.println("paint icon exited");
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
