

import java.util.ArrayList;

import javafx.scene.Group;

public class Niveau1 {
	private int cadence_apparition;
	private int compteur_apparition;
	private double[][] tableau_deroulement;
	private int indice_absolu;
	private int nb_ligne;
	public Niveau1(int cadence_apparition) {
		int i;
		this.cadence_apparition=cadence_apparition;
		double dx_stade=40,curseur_x=0;
		this.nb_ligne=9;
		compteur_apparition=0;
		indice_absolu=0;
		tableau_deroulement = new double[108][7];
		for(i=0;i<108;i++) {
			if(i<36) { // stade 1
				if(curseur_x==-40) {
					dx_stade=40;
					curseur_x+=dx_stade;
				}
				if(curseur_x==480) {
					dx_stade=-40;
					curseur_x+=dx_stade;
				}
				tableau_deroulement[i][0]=curseur_x;
				tableau_deroulement[i][1]=0;  //vitesse x alien
				tableau_deroulement[i][2]=1; //vitesse y alien
				tableau_deroulement[i][3]=0;   // vitesse x projectile alien
				tableau_deroulement[i][4]=3;   // vitesse y projectile alien
				tableau_deroulement[i][5]=50;   // cadence tir alien
				if(i<12) {
					tableau_deroulement[i][6]=1;
				}else if(i<24) {
					tableau_deroulement[i][6]=2;
				}else {
					tableau_deroulement[i][6]=3;
				}
				curseur_x+=dx_stade;
			}else if(i<72) { // stade 2
				if(curseur_x==-40) {
					dx_stade=40;
					curseur_x+=dx_stade;
				}
				if(curseur_x==480) {
					dx_stade=-40;
					curseur_x+=dx_stade;
				}
				tableau_deroulement[i][0]=curseur_x;
				tableau_deroulement[i][1]=0;  //vitesse x alien
				tableau_deroulement[i][2]=2; //vitesse y alien
				tableau_deroulement[i][3]=0;   // vitesse x projectile alien
				tableau_deroulement[i][4]=3;   // vitesse y projectile alien
				tableau_deroulement[i][5]=40;   // cadence tir alien
				if(i<48) {
					tableau_deroulement[i][6]=4;
				}else if(i<60) {
					tableau_deroulement[i][6]=5;
				}else {
					tableau_deroulement[i][6]=6;
				}
				curseur_x+=dx_stade;
			}else { // stade 3
				if(curseur_x==-40) {
					dx_stade=40;
					curseur_x+=dx_stade;
				}
				if(curseur_x==480) {
					dx_stade=-40;
					curseur_x+=dx_stade;
				}
				tableau_deroulement[i][0]=curseur_x;
				tableau_deroulement[i][1]=0;  //vitesse x alien
				tableau_deroulement[i][2]=2; //vitesse y alien
				tableau_deroulement[i][3]=0;   // vitesse x projectile alien
				tableau_deroulement[i][4]=3;   // vitesse y projectile alien
				tableau_deroulement[i][5]=25;   // cadence tir alien
				if(i<84) {
					tableau_deroulement[i][6]=7;
				}else if(i<96) {
					tableau_deroulement[i][6]=8;
				}else {
					tableau_deroulement[i][6]=9;
				}
				curseur_x+=dx_stade;
				
				
				
				
				
			}
							
		}
	}
	public boolean jeu(ArrayList<Alien> list_aliens,Group root) {
		if(compteur_apparition>cadence_apparition) {
			if(indice_absolu<108) {
				list_aliens.add(new Alien(tableau_deroulement[indice_absolu][0],-40,tableau_deroulement[indice_absolu][1],tableau_deroulement[indice_absolu][2],tableau_deroulement[indice_absolu][3],tableau_deroulement[indice_absolu][4],tableau_deroulement[indice_absolu][5],root,tableau_deroulement[indice_absolu][6]));
				
				
				
				indice_absolu++;
			}else {
				return true;
			}
			compteur_apparition=0;
		}
		compteur_apparition++;
		return false;
	}
	public int getNbLigne() {
		return nb_ligne;
	}
	
}
