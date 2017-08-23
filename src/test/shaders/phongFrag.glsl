#version 330 core

out vec4 fragColor;

in vs_out {
	vec3 position;
	vec2 texcoord;
	vec3 normal;
} attr;

struct Material {
	sampler2D diffuse;
	sampler2D specular;
	float shininess;
};

struct BaseLight {
	vec3 ambient;
	vec3 diffuse;
	vec3 specular;
};

struct DirectionalLight {
	vec3 direction;
};

uniform Material material;
uniform DirectionalLight light;

uniform vec3 viewPos;

void main() {
	//Lighting maps
	vec3 diffuseMap = vec3(texture(material.diffuse, attr.texcoord));
	vec3 specularMap = vec3(texture(material.specular, attr.texcoord));

	//Ambient
	vec3 ambient = light.ambient * diffuseMap;
	
	//Diffuse
	vec3 normal = normalize(attr.normal);
	vec3 lightDir = normalize(light.position - attr.position);
	float diff = max(dot(normal, lightDir), 0.0);
	vec3 diffuse = light.diffuse * diff * diffuseMap;
	
	//Specular
	vec3 viewDir = normalize(viewPos - attr.position);
	vec3 reflectDir = reflect(-lightDir, normal);
	float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
	vec3 specular = light.specular * spec * specularMap;

	fragColor = vec4(ambient + diffuse + specular, 1.0f);
}
