package universe.graphics;

public class StandardMaterial extends Material {

	public Texture ambient;
	public Texture diffuse;
	public Texture specular;
	public Texture emissive;
	public Texture height;
	public Texture normal;
	public Texture glossy;
	public Texture opacity;
	public Texture displacement;
	public Texture lightmap;
	public Texture reflection;

	public float ambientIntensity;
	public float shininess;
	public float shininessIntensity;
	public float refractionIndex;
	
	public Color ambientColor     = Color.BLACK;	
	public Color diffuseColor 	  = Color.BLACK;
	public Color specularColor 	  = Color.BLACK;
	public Color emissiveColor 	  = Color.BLACK;
	public Color transparentColor = Color.BLACK;
	
	public boolean twosided = false;
	
	public StandardMaterial(Graphics graphics) {
		super(graphics);
		this.shader = graphics.loadShader("test/shaders/frag_standard.glsl", "test/shaders/vert_standard.glsl");
	}
	
	@Override
	public void setup() {
		if (ambient != null)
			shader.setSampler("material.ambient", ambient);
		if (diffuse != null)
			shader.setSampler("material.diffuse", diffuse);
		if (specular != null)
			shader.setSampler("material.specular", specular);
		
		shader.setFloat("material.ambientIntensity", ambientIntensity);
		shader.setFloat("material.shininess", shininess);
		shader.setFloat("material.shininessIntensity", shininessIntensity);
//		shader.setFloat("material.refractionIndex", refractionIndex);
		
		if (ambientColor != null)
			shader.setColor("material.diffuseColor", diffuseColor);
		if (diffuseColor != null)
			shader.setColor("material.ambientColor", ambientColor);
		if (specularColor != null)
			shader.setColor("material.specularColor", specularColor);
		if (emissiveColor != null)
			shader.setColor("material.emissiveColor", emissiveColor);
		if (transparentColor != null)
			shader.setColor("material.transparentColor", transparentColor);
	}
}
