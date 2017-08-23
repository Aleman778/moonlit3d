#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texcoord;
layout (location = 2) in vec3 normal;

out vs_out {
	vec3 position;
	vec2 texcoord;
	vec3 normal;
} attr;

uniform mat4 m_model;
uniform mat4 m_view;
uniform mat4 m_projection;
uniform mat4 m_normal;

void main() {
	attr.position = vec3(m_model * vec4(position, 1.0f));
	attr.texcoord = texcoord;
	attr.normal = mat3(m_normal) * normal;
	
	gl_Position = m_projection * m_view * m_model * vec4(position, 1.0f);
}
