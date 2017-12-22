package universe.util;

import universe.core.Node;
import universe.graphics.Graphics;
import universe.graphics.Shader;
import universe.graphics.Shape;
import universe.graphics.ShapeMode;

import org.lwjgl.assimp.*;

import static org.lwjgl.assimp.Assimp.*;

public class AssimpHelper {

	private static final String importers =
			  ".3d .3ds .3mf .ac .ac3d .acc .amj .ase .ask .b3d .blend .bvh .cob .cms .dae "
			+ ".collada .dxf .enff .fbx .glb .gltf .hmb .ifc .irr .irrmesh .lwo .lws .lxo "
			+ ".md2 .md3 .md5 .mdc .mdl .mesh .mesh.xml .mot .ms3d .ndo .nff .obj .off .ogex "
			+ ".ply .pmx .prj .q3o .q3s .raw .scn .sib .smd .stl .stp .ter .uc .vta .x .x3d .xgl .zgl ";
	
	private static final String exporters =
			  ".dae .stl .obj .ply .x .3ds .json .assbin .step .gltf ";
	
	/**
	 * Static class
	 */
	private AssimpHelper() {}
	
	public static boolean supportedImporter(String extension) {
		return importers.contains("." + extension.toLowerCase() + " ");
	}
	
	public static boolean supportedExporter(String extension) {
		return importers.contains("." + extension.toLowerCase() + " ");
	}
	public static Node processNode(AINode node, Graphics renderer) {
		return null;
	}
	
	public static Shape processMesh(Graphics graphics, AIMesh mesh, ShapeMode mode, boolean dynamic) {
		//Shape result = new Shape(graphics, mode, dynamic);
		
		return null;
	}
}
