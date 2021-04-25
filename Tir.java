

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Tir {
	private boolean camp;
	private double x;
	private double y;
	private double dx;
	private double dy;
	private Circle projectile;
	public Tir(boolean camp,double x,double y,double dx,double dy,Group root) {
		this.camp=camp;
		this.x=x;
		this.y=y;
		this.dx=dx;
		this.dy=dy;
		if(camp) {
			projectile = new Circle(x,y,5);
			projectile.setFill(Color.BLACK);
			root.getChildren().add(projectile);
		}else {
			projectile = new Circle(x,y,3);
			projectile.setFill(Color.RED);
			root.getChildren().add(projectile);
		}
	}
	public boolean actualiser(Group root,ArrayList<Tir> liste_tir_aliens) {
		if(this.in_out()) {
			root.getChildren().remove(projectile);
			return true;
		}else {
			if(collision(liste_tir_aliens,root)) {
				return true;
			}else {
				x=x+dx;
				y=y+dy;
				projectile.setCenterX(x);
				projectile.setCenterY(y);
				return false;
			}
		}
	}
	public boolean actualiser(Group root) {
		if(this.in_out()) {
			root.getChildren().remove(projectile);
			return true;
		}else {
			
			x=x+dx;
			y=y+dy;
			projectile.setCenterX(x);
			projectile.setCenterY(y);
			return false;
			
		}
	}
	private boolean in_out() {
		if(x<-10 || x>500 || y<-10 || y>400) {
			return true;
		}
		return false;
	}
	private boolean collision(ArrayList<Tir> projectile_lst,Group root) {
		int i;
		double d;
		for(i=0;i<projectile_lst.size();i++) {
			d=Math.hypot(Math.abs(projectile_lst.get(i).getPY()-y), Math.abs(projectile_lst.get(i).getPX()-x));
			if(d<this.getPRadius()+projectile_lst.get(i).getPRadius()) {
				root.getChildren().remove(projectile_lst.get(i).getProjectile());
				root.getChildren().remove(projectile);
				projectile_lst.remove(i);
				return true;
			}
		}
		return false;
	}
	public double getPX() {
		return x;
	}
	public double getPY() {
		return y;
	}
	public double getPRadius() {
		return projectile.getRadius();
	}
	public Circle getProjectile() {
		return projectile;
	}
}
