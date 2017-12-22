#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texcoord;
layout (location = 2) in vec3 normal;
layout (location = 3) in vec4 color;

out vs_out {
	vec3 position;
	vec2 texcoord;
	vec3 normal;
	vec4 color;
} attr;

uniform mat4 m_model;
uniform mat4 m_view;
uniform mat4 m_projection;

void main() {
	attr.position = vec3(m_model * vec4(position, 1.0f));
	attr.texcoord = texcoord;
	attr.normal = normal;
	attr.color = color;
	
	gl_Position = m_projection * m_view * m_model * vec4(position, 1.0f);
}
