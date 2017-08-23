#version 330 core
layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texcoord;

out vs_out {
	vec2 texcoord;
} attrib;

uniform mat4 u_modelview;
uniform mat4 u_projection;

void main() {
	attrib.texcoord = texcoord;
	gl_Position = u_projection * u_modelview * vec4(position, 1.0);
}
