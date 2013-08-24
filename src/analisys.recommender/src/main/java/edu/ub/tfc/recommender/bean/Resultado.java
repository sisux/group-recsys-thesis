package edu.ub.tfc.recommender.bean;

/**
 * Bean que se utilizar‡ para pintar por pantalla la evaluaci—n
 * @author David Gil De Arce
 */
public class Resultado {
	
	private double userPearsonMae;
	private double userPearsonRmse;
	private long userPearsonTime;

	private double userCosineMae;
	private double userCosineRmse;
	private long userCosineTime;

	private double userSpearmanMae;
	private double userSpearmanRmse;
	private long userSpearmanTime;

	private double userEuclideanMae;
	private double userEuclideanRmse;
	private long userEuclideanTime;

	private double itemPearsonMae;
	private double itemPearsonRmse;
	private long itemPearsonTime;

	private double itemCosineMae;
	private double itemCosineRmse;
	private long itemCosineTime;

	private double itemSpearmanMae;
	private double itemSpearmanRmse;
	private long itemSpearmanTime;

	private double itemEuclideanMae;
	private double itemEuclideanRmse;
	private long itemEuclideanTime;


	/**
	 */
	public Resultado() {
		super();
	}
	/**
	 * @return the userPearsonMae
	 */
	public double getUserPearsonMae() {
		return this.userPearsonMae;
	}
	/**
	 * @param userPearsonMae the userPearsonMae to set
	 */
	public void setUserPearsonMae(final double userPearsonMae) {
		this.userPearsonMae = userPearsonMae;
	}
	/**
	 * @return the userPearsonRmse
	 */
	public double getUserPearsonRmse() {
		return this.userPearsonRmse;
	}
	/**
	 * @param userPearsonRmse the userPearsonRmse to set
	 */
	public void setUserPearsonRmse(final double userPearsonRmse) {
		this.userPearsonRmse = userPearsonRmse;
	}
	/**
	 * @return the userPearsonTime
	 */
	public long getUserPearsonTime() {
		return this.userPearsonTime;
	}
	/**
	 * @param userPearsonTime the userPearsonTime to set
	 */
	public void setUserPearsonTime(final long userPearsonTime) {
		this.userPearsonTime = userPearsonTime;
	}
	/**
	 * @return the userCosineMae
	 */
	public double getUserCosineMae() {
		return this.userCosineMae;
	}
	/**
	 * @param userCosineMae the userCosineMae to set
	 */
	public void setUserCosineMae(final double userCosineMae) {
		this.userCosineMae = userCosineMae;
	}
	/**
	 * @return the userCosineRmse
	 */
	public double getUserCosineRmse() {
		return this.userCosineRmse;
	}
	/**
	 * @param userCosineRmse the userCosineRmse to set
	 */
	public void setUserCosineRmse(final double userCosineRmse) {
		this.userCosineRmse = userCosineRmse;
	}
	/**
	 * @return the userCosineTime
	 */
	public long getUserCosineTime() {
		return this.userCosineTime;
	}
	/**
	 * @param userCosineTime the userCosineTime to set
	 */
	public void setUserCosineTime(final long userCosineTime) {
		this.userCosineTime = userCosineTime;
	}
	/**
	 * @return the itemPearsonMae
	 */
	public double getItemPearsonMae() {
		return this.itemPearsonMae;
	}
	/**
	 * @param itemPearsonMae the itemPearsonMae to set
	 */
	public void setItemPearsonMae(final double itemPearsonMae) {
		this.itemPearsonMae = itemPearsonMae;
	}
	/**
	 * @return the itemPearsonRmse
	 */
	public double getItemPearsonRmse() {
		return this.itemPearsonRmse;
	}
	/**
	 * @param itemPearsonRmse the itemPearsonRmse to set
	 */
	public void setItemPearsonRmse(final double itemPearsonRmse) {
		this.itemPearsonRmse = itemPearsonRmse;
	}
	/**
	 * @return the itemPearsonTime
	 */
	public long getItemPearsonTime() {
		return this.itemPearsonTime;
	}
	/**
	 * @param itemPearsonTime the itemPearsonTime to set
	 */
	public void setItemPearsonTime(final long itemPearsonTime) {
		this.itemPearsonTime = itemPearsonTime;
	}
	/**
	 * @return the itemCosineMae
	 */
	public double getItemCosineMae() {
		return this.itemCosineMae;
	}
	/**
	 * @param itemCosineMae the itemCosineMae to set
	 */
	public void setItemCosineMae(final double itemCosineMae) {
		this.itemCosineMae = itemCosineMae;
	}
	/**
	 * @return the itemCosineRmse
	 */
	public double getItemCosineRmse() {
		return this.itemCosineRmse;
	}
	/**
	 * @param itemCosineRmse the itemCosineRmse to set
	 */
	public void setItemCosineRmse(final double itemCosineRmse) {
		this.itemCosineRmse = itemCosineRmse;
	}
	/**
	 * @return the itemCosineTime
	 */
	public long getItemCosineTime() {
		return this.itemCosineTime;
	}
	/**
	 * @param itemCosineTime the itemCosineTime to set
	 */
	public void setItemCosineTime(final long itemCosineTime) {
		this.itemCosineTime = itemCosineTime;
	}


	/**
	 * @return the userSpearmanMae
	 */
	public double getUserSpearmanMae() {
		return this.userSpearmanMae;
	}
	/**
	 * @param userSpearmanMae the userSpearmanMae to set
	 */
	public void setUserSpearmanMae(final double userSpearmanMae) {
		this.userSpearmanMae = userSpearmanMae;
	}
	/**
	 * @return the userSpearmanRmse
	 */
	public double getUserSpearmanRmse() {
		return this.userSpearmanRmse;
	}
	/**
	 * @param userSpearmanRmse the userSpearmanRmse to set
	 */
	public void setUserSpearmanRmse(final double userSpearmanRmse) {
		this.userSpearmanRmse = userSpearmanRmse;
	}
	/**
	 * @return the userSpearmanTime
	 */
	public long getUserSpearmanTime() {
		return this.userSpearmanTime;
	}
	/**
	 * @param userSpearmanTime the userSpearmanTime to set
	 */
	public void setUserSpearmanTime(final long userSpearmanTime) {
		this.userSpearmanTime = userSpearmanTime;
	}
	/**
	 * @return the userEuclideanMae
	 */
	public double getUserEuclideanMae() {
		return this.userEuclideanMae;
	}
	/**
	 * @param userEuclideanMae the userEuclideanMae to set
	 */
	public void setUserEuclideanMae(final double userEuclideanMae) {
		this.userEuclideanMae = userEuclideanMae;
	}
	/**
	 * @return the userEuclideanRmse
	 */
	public double getUserEuclideanRmse() {
		return this.userEuclideanRmse;
	}
	/**
	 * @param userEuclideanRmse the userEuclideanRmse to set
	 */
	public void setUserEuclideanRmse(final double userEuclideanRmse) {
		this.userEuclideanRmse = userEuclideanRmse;
	}
	/**
	 * @return the userEuclideanTime
	 */
	public long getUserEuclideanTime() {
		return this.userEuclideanTime;
	}
	/**
	 * @param userEuclideanTime the userEuclideanTime to set
	 */
	public void setUserEuclideanTime(final long userEuclideanTime) {
		this.userEuclideanTime = userEuclideanTime;
	}
	/**
	 * @return the itemSpearmanMae
	 */
	public double getItemSpearmanMae() {
		return this.itemSpearmanMae;
	}
	/**
	 * @param itemSpearmanMae the itemSpearmanMae to set
	 */
	public void setItemSpearmanMae(final double itemSpearmanMae) {
		this.itemSpearmanMae = itemSpearmanMae;
	}
	/**
	 * @return the itemSpearmanRmse
	 */
	public double getItemSpearmanRmse() {
		return this.itemSpearmanRmse;
	}
	/**
	 * @param itemSpearmanRmse the itemSpearmanRmse to set
	 */
	public void setItemSpearmanRmse(final double itemSpearmanRmse) {
		this.itemSpearmanRmse = itemSpearmanRmse;
	}
	/**
	 * @return the itemSpearmanTime
	 */
	public long getItemSpearmanTime() {
		return this.itemSpearmanTime;
	}
	/**
	 * @param itemSpearmanTime the itemSpearmanTime to set
	 */
	public void setItemSpearmanTime(final long itemSpearmanTime) {
		this.itemSpearmanTime = itemSpearmanTime;
	}
	/**
	 * @return the itemEuclideanMae
	 */
	public double getItemEuclideanMae() {
		return this.itemEuclideanMae;
	}
	/**
	 * @param itemEuclideanMae the itemEuclideanMae to set
	 */
	public void setItemEuclideanMae(final double itemEuclideanMae) {
		this.itemEuclideanMae = itemEuclideanMae;
	}
	/**
	 * @return the itemEuclideanRmse
	 */
	public double getItemEuclideanRmse() {
		return this.itemEuclideanRmse;
	}
	/**
	 * @param itemEuclideanRmse the itemEuclideanRmse to set
	 */
	public void setItemEuclideanRmse(final double itemEuclideanRmse) {
		this.itemEuclideanRmse = itemEuclideanRmse;
	}
	/**
	 * @return the itemEuclideanTime
	 */
	public long getItemEuclideanTime() {
		return this.itemEuclideanTime;
	}
	/**
	 * @param itemEuclideanTime the itemEuclideanTime to set
	 */
	public void setItemEuclideanTime(final long itemEuclideanTime) {
		this.itemEuclideanTime = itemEuclideanTime;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getUserPearsonMae() + ";" +
		this.getUserPearsonRmse() + ";" +
		this.getUserPearsonTime() + ";" +
		this.getUserCosineMae() + ";" +
		this.getUserCosineRmse() + ";" +
		this.getUserCosineTime() + ";" +
		this.getUserEuclideanMae() + ";" +
		this.getUserEuclideanRmse() + ";" +
		this.getUserEuclideanTime() + ";" +
		this.getUserSpearmanMae() + ";" +
		this.getUserSpearmanRmse() + ";" +
		this.getUserSpearmanTime() + ";" +
		this.getItemPearsonMae() + ";" +
		this.getItemPearsonRmse() + ";" +
		this.getItemPearsonTime() + ";" +
		this.getItemCosineMae() + ";" +
		this.getItemCosineRmse() + ";" +
		this.getItemCosineTime() +
		this.getItemEuclideanMae() + ";" +
		this.getItemEuclideanRmse() + ";" +
		this.getItemEuclideanTime() + ";" +
		this.getItemSpearmanMae() + ";" +
		this.getItemSpearmanRmse() + ";" +
		this.getItemSpearmanTime();
	}


}
