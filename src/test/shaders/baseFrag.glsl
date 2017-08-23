#version 330 core

out vec4 fragColor;

in vs_out {
	vec2 texcoord;
} attrib;

uniform sampler2D tex;

void main() {
	vec4 texel = texture(tex, attrib.texcoord);
	fragColor = texel;
}
