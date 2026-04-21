package app.jongerius.dami.consoleImprovements.api.widgets;

import app.jongerius.dami.consoleImprovements.api.ConsoleTUI;

import java.util.ArrayList;
import java.util.List;

/**
 * A widget that displays hierarchical data as a tree.
 */
public class Tree implements ConsoleTUI.Widget {
    private final Node root;

    /**
     * Constructs a new Tree widget.
     *
     * @param rootLabel The label for the root node of the tree.
     */
    public Tree(String rootLabel) {
        this.root = new Node(rootLabel);
    }

    /**
     * Retrieves the root node of the tree to allow adding children.
     *
     * @return The root Node.
     */
    public Node getRoot() {
        return root;
    }

    @Override
    public String render() {
        StringBuilder sb = new StringBuilder();
        renderNode(sb, root, "", true);
        // Remove trailing newline if present
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '\n') {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    private void renderNode(StringBuilder sb, Node node, String prefix, boolean isTail) {
        sb.append(prefix).append(isTail ? "└── " : "├── ").append(node.label).append("\n");
        for (int i = 0; i < node.children.size(); i++) {
            renderNode(sb, node.children.get(i), prefix + (isTail ? "    " : "│   "), i == node.children.size() - 1);
        }
    }

    /**
     * Represents a single node within the Tree widget.
     */
    public static class Node {
        private final String label;
        private final List<Node> children = new ArrayList<>();

        /**
         * Constructs a new Tree node.
         *
         * @param label The text label of the node.
         */
        public Node(String label) {
            this.label = label;
        }

        /**
         * Adds a child node to this node.
         *
         * @param childLabel The label for the new child node.
         * @return The newly created child Node, allowing for method chaining or further additions.
         */
        public Node addChild(String childLabel) {
            Node child = new Node(childLabel);
            children.add(child);
            return child;
        }
    }
}
