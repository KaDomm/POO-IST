package entities;

public class Alpha {

	private Column parent;

	private int parentIndex;

	private Column child;

	private int childIndex;

	private double alpha;

	private Boolean belongTree;

	/**
	 * @param parent
	 * @param parentIndex
	 * @param child
	 * @param childIndex
	 * @param alpha
	 * @param belongTree
	 */
	public Alpha(Column parent, int parentIndex, Column child, int childIndex, double alpha, Boolean belongTree) {
		super();
		this.parent = parent;
		this.parentIndex = parentIndex;
		this.child = child;
		this.childIndex = childIndex;
		this.alpha = alpha;
		this.belongTree = belongTree;
	}

	/**
	 * @return the parent
	 */
	public Column getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Column parent) {
		this.parent = parent;
	}

	/**
	 * @return the child
	 */
	public Column getChild() {
		return child;
	}

	/**
	 * @param child the child to set
	 */
	public void setChild(Column child) {
		this.child = child;
	}

	/**
	 * @return the alpha
	 */
	public double getAlpha() {
		return alpha;
	}

	/**
	 * @param alpha the alpha to set
	 */
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	/**
	 * @return the belongTree
	 */
	public Boolean getBelongTree() {
		return belongTree;
	}

	/**
	 * @param belongTree the belongTree to set
	 */
	public void setBelongTree(Boolean belongTree) {
		this.belongTree = belongTree;
	}

	/**
	 * @return the parentIndex
	 */
	public int getParentIndex() {
		return parentIndex;
	}

	/**
	 * @param parentIndex the parentIndex to set
	 */
	public void setParentIndex(int parentIndex) {
		this.parentIndex = parentIndex;
	}

	/**
	 * @return the childIndex
	 */
	public int getChildIndex() {
		return childIndex;
	}

	/**
	 * @param childIndex the childIndex to set
	 */
	public void setChildIndex(int childIndex) {
		this.childIndex = childIndex;
	}

	@Override
	public String toString() {
		return "[" + parentIndex + "][" + childIndex + "] = " + alpha + "\n";
	}

}
