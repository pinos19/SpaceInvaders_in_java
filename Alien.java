

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Alien {
	private double x;
	private double y;
	private ImageView iv_alien;
	private double cadence_tir;
	private double dx;
	private double vitesse_tir_dx;
	private double vitesse_tir_dy;
	private double dy;
	private int compteur_tir_aliens;
	private double ligne;
	private ImageView iv_explosion;
	public Alien(double x,double y ,double dx,double dy,double vitesse_tir_dx,double vitesse_tir_dy,double cadence_tir,Group root,double ligne) {
		this.ligne=ligne;
		this.x=x;
		this.y=y;
		this.dx=dx;
		this.dy=dy;
		this.vitesse_tir_dx=vitesse_tir_dx;
		this.vitesse_tir_dy=vitesse_tir_dy;
		this.cadence_tir=cadence_tir;
		this.compteur_tir_aliens=0;
		Image img = new Image("alien.png");
		Image img2 = new Image("explosion.png");
		iv_explosion= new ImageView(img2);
		iv_explosion.setFitWidth(40);
		iv_explosion.setFitHeight(40);
		iv_alien = new ImageView(img);
		iv_alien.setX(x);
		iv_alien.setY(y);
		iv_alien.setFitWidth(40);
		iv_alien.setFitHeight(40);
		root.getChildren().add(iv_alien);
	}
	public boolean actualiser(Group root,ArrayList<Tir> list_tir_fusee,int[] score,Label affiche_score,ArrayList<ImageView> affichage_vies,int[] vies,double[] tab_ligne,ArrayList<ImageView> explosions) {
		if(this.in_out()) {
			root.getChildren().remove(affichage_vies.get(0));
			affichage_vies.remove(0);
			root.getChildren().remove(iv_alien);
			vies[0]+=-1;
			return true;
		}else {
			if(this.destruction(list_tir_fusee, root,score,affiche_score)) {
				root.getChildren().remove(iv_alien);
				tab_ligne[(int)ligne-1]+=1;
				iv_explosion.setX(x);
				iv_explosion.setY(y);
				explosions.add(iv_explosion);
				root.getChildren().add(iv_explosion);
				return true;
			}else {
				x=x+dx;
				y=y+dy;
				iv_alien.setX(x);
				iv_alien.setY(y);
				return false;
			}
			
		}
	}
	private boolean in_out() {
		if(x<0 || x>500 || y<-40 || y>400) {
			return true;
		}
		return false;
	}
	
	
	
	private boolean destruction(ArrayList<Tir> list_tir,Group root,int[] score,Label affiche_score) {
		int i;
		double d;
		for(i=0;i<list_tir.size();i++) {
			d=Math.hypot(Math.abs(list_tir.get(i).getPY()-(y+20)), Math.abs(list_tir.get(i).getPX()-(x+20)));
			if(d<35+list_tir.get(i).getPRadius()) {
				root.getChildren().remove(list_tir.get(i).getProjectile());
				root.getChildren().remove(iv_alien);
				list_tir.remove(i);
				score[0]+=10;
				affiche_score.setText(String.valueOf(score[0]));
				return true;
			}
		}
		return false;
	}
	public void tir(Group root,ArrayList<Tir> list_tir) {
		if(compteur_tir_aliens>cadence_tir) {
			Tir nv_tir = new Tir(false,x+20,y+43,vitesse_tir_dx,vitesse_tir_dy,root);
			list_tir.add(nv_tir);
			compteur_tir_aliens=0;
		}
		compteur_tir_aliens++;
	}
	public double getCadenceTir() {
		return cadence_tir;
	}
}
