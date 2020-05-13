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
	 * @param parentIndex
	 * @param childIndex
	 * @param alpha
	 */
	public Alpha(int parentIndex, int childIndex, double alpha) {
		this.parentIndex = parentIndex;
		this.childIndex = childIndex;
		this.alpha = alpha;
	}

	/**
	 * @param childIndex
	 * @param parentIndx
	 */
	public Alpha(int childIndex, int parentIndx) {
		this.parentIndex = parentIndx;
		this.childIndex = childIndex;
	}

	/**
	 * @return
	 */
	public double getAlpha() {
		return alpha;
	}

	/**
	 * @param alpha
	 */
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	/**
	 * @return
	 */
	public int getParentIndex() {
		return parentIndex;
	}

	/**
	 * @param parentIndex
	 */
	public void setParentIndex(int parentIndex) {
		this.parentIndex = parentIndex;
	}

	/**
	 * @return
	 */
	public int getChildIndex() {
		return childIndex;
	}

	/**
	 * @param childIndex
	 */
	public void setChildIndex(int childIndex) {
		this.childIndex = childIndex;
	}

}
