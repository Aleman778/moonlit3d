package universe.core;

import java.util.HashSet;

public final class Group {
	
	private final Node root;
	private final HashSet<Node> groupSet;
	private final HashSet<Node> renderableSet;
	
	public Group(Node root) {
		this.root = root;
		this.groupSet = new HashSet<>();
		this.renderableSet = new HashSet<>();
	}
	
	public Node[] getContents() {
		return groupSet.toArray(new Node[groupSet.size()]);
	}
	
	public Node getRoot() {
		return root;
	}
}
