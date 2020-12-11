package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;

import javax.swing.JPanel;

public class Histogram extends JPanel {

	private static final long serialVersionUID = 1L;
	private int[] barValues = new int[10];
	private int[] barHeights = new int[10];
	private int maxValues = 0;
	private int width, height, barWidth, maxBarHeight, gap = 5;
	private Color[] colors = {
			Color.decode("#990000"),
			Color.decode("#8f1405"),
			Color.decode("#85290a"),
			Color.decode("#7a3d0f"),
			Color.decode("#6b5c17"),
			Color.decode("#61701c"),
			Color.decode("#578521"),
			Color.decode("#528f24"),
			Color.decode("#42ad2b"),
			Color.decode("#38c230"),
	};
	
	private JPanel parent;
	
	public Histogram(List<Integer> values, JPanel parent) {
		this.parent = parent;
		
		for(int v: values) barValues[v-1]++;
		for(int v: barValues) {
			if(v>maxValues)maxValues=v;
		}
		
		width = parent.getWidth();
		height = parent.getHeight();
		maxBarHeight = height-50;
		barWidth = (width-11*gap)/10;
		for(int i = 0; i<10; i++) {
			barHeights[i] = (int)(((float)barValues[i]/(float)maxValues)*(float)maxBarHeight);
		}
		
		setBackground(Color.LIGHT_GRAY);
		
		this.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				recalculateValues();
			}
			@Override
			public void componentMoved(ComponentEvent e) {
			}
			@Override
			public void componentShown(ComponentEvent e) {
			}
			@Override
			public void componentHidden(ComponentEvent e) {
			}
		});
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(2));
		for(int i = 0; i < 10; i++) {
			//g2d.drawRect((i+1)*gap + i*barWidth, height, barWidth, -(barHeights[i]));
			
			int x = (i+1)*gap + i*barWidth;
			int h = barHeights[i]<25?25:barHeights[i];
			int y = height-(h)-20;
			
			
			
			g2d.setColor(Color.BLACK);
			g2d.drawRect(x, y, barWidth, h);
			g2d.setColor(colors[i]);
			g2d.fillRect(x, y, barWidth, h);
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.drawString(String.valueOf(i+1), x+(barWidth/2)-5, height-30);
			g2d.setColor(Color.BLACK);
			g2d.drawString(String.valueOf(barValues[i]), x+(barWidth/2)-5, y-5);
		}
		
		
	}
	
	private void recalculateValues() {
		width = parent.getWidth();
		height = parent.getHeight();
		maxBarHeight = height-50;
		barWidth = (width-11*gap)/10;
		for(int i = 0; i<10; i++) {
			barHeights[i] = (int)(((float)barValues[i]/(float)maxValues)*(float)maxBarHeight);
		}
	}

}
