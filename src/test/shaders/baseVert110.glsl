#version 110
attribute vec4 color;

uniform mat4 u_modelview;

varying vec4 vertexColor;

void main() {
	vertexColor = color;
	gl_Position = gl_Vertex * u_modelview;
}
