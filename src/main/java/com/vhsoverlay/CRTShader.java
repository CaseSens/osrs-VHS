package com.vhsoverlay;

import org.lwjgl.opengl.GL20;

public class CRTShader
{
	private int programID;
	private int fragmentShaderId;

	private static final String FRAGMENT_SHADER_CODE = ""
		+ "#version 330 core\n"
		+ "in vec2 TexCoords;\n"
		+ "out vec4 fragColor;\n"
		+ "uniform sampler2D scene;\n"
		+ "uniform vec2 iResolution;\n"
		+ "uniform float iTime;\n"
		+ "float warp = 0.75;\n"
		+ "float scan = 0.75;\n"
		+ "void main() {\n"
		+ "    vec2 uv = TexCoords;\n"
		+ "    vec2 dc = abs(0.5 - uv);\n"
		+ "    dc *= dc;\n"
		+ "    uv.x -= 0.5; uv.x *= 1.0 + (dc.y * (0.3 * warp)); uv.x += 0.5;\n"
		+ "    uv.y -= 0.5; uv.y *= 1.0 + (dc.x * (0.4 * warp)); uv.y += 0.5;\n"
		+ "    if (uv.y > 1.0 || uv.x < 0.0 || uv.x > 1.0 || uv.y < 0.0) {\n"
		+ "        fragColor = vec4(0.0, 0.0, 0.0, 1.0);\n"
		+ "    } else {\n"
		+ "        float apply = abs(sin(TexCoords.y * iResolution.y) * 0.5 * scan);\n"
		+ "        fragColor = vec4(mix(texture(scene, uv).rgb, vec3(0.0), apply), 1.0);\n"
		+ "    }\n"
		+ "}\n";

	public CRTShader()
	{
		programID = GL20.glCreateProgram();
		fragmentShaderId = createShader(FRAGMENT_SHADER_CODE, GL20.GL_FRAGMENT_SHADER);
		link();
	}

	private int createShader(String shaderCode, int shaderType)
	{
		int shaderId = GL20.glCreateShader(shaderType);
		GL20.glShaderSource(shaderId, shaderCode);
		GL20.glCompileShader(shaderId);

		return shaderId;
	}

	private void link()
	{
		GL20.glAttachShader(programID, fragmentShaderId);
		GL20.glLinkProgram(programID);
		// TODO: Check for linking errors and handle them
	}

	public void use()
	{
		GL20.glUseProgram(programID);
	}

	public void cleanup()
	{
		GL20.glUseProgram(0); // Stop using the program
		GL20.glDetachShader(programID, fragmentShaderId);
		GL20.glDeleteShader(fragmentShaderId);
		GL20.glDeleteProgram(programID);
	}

	// TODO: Methods to set uniforms like iResolution, iTime, etc. go below
}
