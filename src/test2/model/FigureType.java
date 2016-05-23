package test2.model;

/**
 * A játék által használt bábuk típusa.
 * 
 * @author Andi
 *
 */
public enum FigureType {
	BLACK("file:resources/figure_black.png"),
	WHITE("file:resources/figure_white.png"),
	NONE("file:resources/figure_empty.png");
	
	private String texturePath;
	
	public String getTexturePath() {
		return texturePath;
	}
	
	private FigureType(String texturePath){
		this.texturePath = texturePath;
	}
}
