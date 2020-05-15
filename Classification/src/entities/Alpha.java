package entities;

/**
 * @author USER-Admin
 *
 */
public class Alpha {

	private int parentIndex;

	private int childIndex;

	private double alpha;

	/**
	 * @param parentIndex parentIndex
	 * @param childIndex  childIndex
	 * @param alpha       alpha
	 */
	public Alpha(int parentIndex, int childIndex, double alpha) {
		this.parentIndex = parentIndex;
		this.childIndex = childIndex;
		this.alpha = alpha;
	}

	/**
	 * @param childIndex childIndex
	 * @param parentIndx parentIndx
	 */
	public Alpha(int childIndex, int parentIndx) {
		this.parentIndex = parentIndx;
		this.childIndex = childIndex;
	}

	/**
	 * @return alpha
	 */
	public double getAlpha() {
		return alpha;
	}

	/**
	 * @param alpha setAlpha
	 */
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	/**
	 * @return parentIndex
	 */
	public int getParentIndex() {
		return parentIndex;
	}

	/**
	 * @param parentIndex set
	 */
	public void setParentIndex(int parentIndex) {
		this.parentIndex = parentIndex;
	}

	/**
	 * @return childIndex
	 */
	public int getChildIndex() {
		return childIndex;
	}

	/**
	 * @param childIndex set
	 */
	public void setChildIndex(int childIndex) {
		this.childIndex = childIndex;
	}

}
