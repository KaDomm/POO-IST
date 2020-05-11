package entities;

public class Alpha {

	private int parentIndex;

	private int childIndex;

	private double alpha;

	/**
	 * @param parent
	 * @param parentIndex
	 * @param child
	 * @param childIndex
	 * @param alpha
	 * @param belongTree
	 */
	public Alpha(int parentIndex, int childIndex, double alpha) {
		this.parentIndex = parentIndex;
		this.childIndex = childIndex;
		this.alpha = alpha;
	}

	public Alpha(int childIndex, int parentIndx) {
		this.parentIndex = parentIndx;
		this.childIndex = childIndex;
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
