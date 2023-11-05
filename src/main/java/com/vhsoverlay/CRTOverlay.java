package com.vhsoverlay;

import java.awt.Dimension;
import java.awt.Graphics2D;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import org.lwjgl.opengl.GL11;

public class CRTOverlay extends Overlay
{
	private CRTShader crtShader;
	public CRTOverlay() {
		setLayer(OverlayLayer.ABOVE_SCENE);

		// Rendering code using OpenGL
		crtShader = new CRTShader();

		// TODO: Set uniforms (ex: glUniform2f(iResolutionLocation, screenWidth, screenHeight);)
	}


	@Override
	public Dimension render(Graphics2D graphics)
	{
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		crtShader.use();

		// TODO: Set uniforms and render with the shader


		GL11.glDisable(GL11.GL_BLEND);

		return null;
	}

	public void shutdown() {
		crtShader.cleanup();
	}
}
