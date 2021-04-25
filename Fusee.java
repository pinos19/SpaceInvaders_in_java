

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Fusee{
	private double x;
	private double y;
	private double sensibilite_deplacement;
	private double vitesse_tir;
	private ImageView iv_fusee;
	private double cadence_tir;
	private int compteur_tir_fusee;
	private int score;
	private boolean touche;
	
	public Fusee(double x,double y ,double sensi,double vitesse_tir,double cadence_tir,Group root,int score) {
		this.x=x;
		this.y=y;
		touche=false;
		this.score=score;
		this.sensibilite_deplacement=sensi;
		this.vitesse_tir=vitesse_tir;
		this.cadence_tir=cadence_tir;
		this.compteur_tir_fusee=0;
		Image img = new Image("spaceship.png");
		iv_fusee = new ImageView(img);
		iv_fusee.setX(x);
		iv_fusee.setY(y);
		iv_fusee.setFitWidth(40);
		iv_fusee.setFitHeight(40);
		root.getChildren().add(iv_fusee);
	}
	public void haut() {
		if(y>0) {
			y=y-sensibilite_deplacement;
		}
		iv_fusee.setY(y);
	}
	public void bas() {
		if(y<360) {
			y=y+sensibilite_deplacement;
		}
		iv_fusee.setY(y);
	}
	public void gauche() {
		if(x>0) {
			x=x-sensibilite_deplacement;
		}
		iv_fusee.setX(x);
	}
	public void droite() {
		if(x<460) {
			x=x+sensibilite_deplacement;
		}
		iv_fusee.setX(x);
	}
	public boolean destruction(ArrayList<Tir> list_tir,Group root,int[] vies,ArrayList<ImageView> affichage_vies) {
		int i;
		double d;
		for(i=0;i<list_tir.size();i++) {
			d=Math.hypot(Math.abs(list_tir.get(i).getPY()-(y+20)), Math.abs(list_tir.get(i).getPX()-(x+20)));
			if(d<17+list_tir.get(i).getPRadius()) {
				root.getChildren().remove(list_tir.get(i).getProjectile());
				list_tir.remove(i);
				vies[0]+=-1;
				root.getChildren().remove(affichage_vies.get(0));
				affichage_vies.remove(0);
				setTouche(true);
			}
		}
		if(vies[0]==0) {
			return true;
		}else {
			return false;
		}
	}
	public void tir(Group root,ArrayList<Tir> list_tir) {
		if(compteur_tir_fusee > cadence_tir) {
			Tir nv_tir = new Tir(true,x+20,y-5,0,vitesse_tir,root);
			list_tir.add(nv_tir);
			compteur_tir_fusee=0;
		
		}
		compteur_tir_fusee++;
		
	}
	public void tir_evolue(Group root,ArrayList<Tir> list_tir) {
		if(compteur_tir_fusee > cadence_tir) {
			Tir nv_tir1 = new Tir(true,x+14,y-5,0,vitesse_tir,root);
			Tir nv_tir2 = new Tir(true,x+26,y-5,0,vitesse_tir,root);
			list_tir.add(nv_tir1);
			list_tir.add(nv_tir2);
			compteur_tir_fusee=0;
		
		}
		compteur_tir_fusee++;
	}
	public double getCadenceTir() {
		return cadence_tir;
	}
	public int getScore() {
		return score;
	}
	public boolean getTouche() {
		return touche;
	}
	public void setScore(int score) {
		this.score=score;
	}
	public void setTouche(boolean touche) {
		this.touche=touche;
	}
	
	
}
