#version 330 core

out vec4 fragColor;

in vs_out {
	vec3 position;
	vec2 texcoord;
	vec3 normal;
	vec4 color;
} attr;

/* struct Material {
	vec3 ambientColor;
	vec3 diffuseColor;
	vec3 specularColor;
	vec3 emissiveColor;
	float shininess;
	float shininessIntensity;
	float ambientIntensity;
};
struct BaseLight {
	vec3 ambient;
	vec3 diffuse;
	vec3 specular;
};

struct DirectionalLight {
	BaseLight base;
	vec3 direction;
};

uniform Material material;
uniform DirectionalLight light;

uniform vec3 viewPos;

 */
void main() {
	//Lighting maps
	//vec3 diffuseMap = vec3(texture(material.diffuse, attr.texcoord));
	//vec3 specularMap = vec3(texture(material.specular, attr.texcoord));
/*
	//Ambient
	vec3 ambient = material.ambientColor * light.base.ambient;// * diffuseMap;
	
	//Diffuse
	vec3 normal = normalize(attr.normal);
	vec3 lightDir = normalize(light.direction);
	float diff = max(dot(normal, lightDir), 0.0);
	vec3 diffuse = diff * (material.diffuseColor * light.base.diffuse);// * diffuseMap;
	
	//Specular
	vec3 viewDir = normalize(viewPos - attr.position);
	vec3 reflectDir = reflect(-lightDir, normal);
	float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
	vec3 specular = spec * (material.specularColor * light.base.specular);// * specularMap;

	//Emissive
	vec3 emissive = material.emissiveColor;
	*/
	//fragColor = vec4(ambient + diffuse + specular + emissive, 1.0f) + vec4(1.0f, 0.0f, 0.0f, 0.0f);
	fragColor = attr.color;
}
