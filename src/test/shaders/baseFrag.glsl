#version 330 core

out vec4 fragColor;

in vs_out {
	vec3 position;
	vec2 texcoord;
	vec3 normal;
	vec4 color;
} attr;

uniform sampler2D diffuse;

void main() {
	fragColor = texture(diffuse, attr.texcoord);
}
