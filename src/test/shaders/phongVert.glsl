#version 330 core

layout (location = 0) in vec3 position;
layout (location = 3) in vec2 texcoord;
layout (location = 2) in vec3 normal;
layout (location = 1) in vec4 color;

out vs_out {
	vec3 position;
	vec2 texcoord;
	vec3 normal;
	vec4 color;
} attr;

uniform mat4 m_model;
uniform mat4 m_combined;
uniform mat4 m_normal;

void main() {
	attr.position = vec3(m_model * vec4(position, 1.0f));
	attr.texcoord = texcoord;
	attr.normal = mat3(m_normal) * normal;
	attr.color = color;
	
	gl_Position = m_combined * m_model * vec4(position, 1.0f);
}
