

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SpaceInvaders extends Application{
	
	private Fusee fusee;
	private ArrayList<Alien> aliens;
	private ArrayList<Tir> p_aliens;
	private ArrayList<Tir> p_fusee;
	private Group root;
	private boolean droite;
	private boolean gauche;
	private boolean bas;
	private boolean haut;
	private boolean feu;
	private AnimationTimer loop;
	private int[] vies=new int[1];
	private ArrayList<ImageView> affichage_vies = new ArrayList<ImageView>();
	private Niveau1 nv;
	private Label affiche_score;
	private int[] score= new int[1];
	private double[] tab_ligne;
	private boolean bonus;
	private int compteur_bonus;
	private Label message_bonus;
	private ArrayList<ImageView> explosions;
	private int compteur_explosion;
	
	public void start(Stage st) {
		try {
			/// INITIALISATION
			init_game();
			
			Scene sc = new Scene(root,500,400);
			sc.setFill(Color.LIGHTGRAY);
			/// BOUCLE
			
			loop = new AnimationTimer() {
				public void handle(long arg0) {
					if(nv.jeu(aliens, root)) {
						finNiveau(st);
					}
					
					explosion();
					affichage_bonus();
					deplacement_et_tir_alien();
					deplacement_et_tir_fusee(st);
					update();
					
				}
			};
			loop.start();
			
			
			
			sc.setOnKeyPressed(e->{
				switch(e.getCode()) {
					case LEFT:
						gauche=true;
						break;
					case RIGHT:
						droite=true;
						break;
					case UP:
						haut=true;
						break;
					case DOWN:
						bas=true;
						break;
					case SPACE:
						feu=true;
					default:
						break;	
				}
				
				
				
			});
			sc.setOnKeyReleased(e->{
				switch(e.getCode()) {
					case LEFT:
						gauche=false;
						break;
					case RIGHT:
						droite=false;
						break;
					case UP:
						haut=false;
						break;
					case DOWN:
						bas=false;
						break;
					case SPACE:
						feu=false;
						break;
					default:
						break;	
				}
				
				
			}
			);
			
			
			st.setResizable(false);
			st.setTitle("Space Invaders");
			st.setScene(sc);
			st.show();
			
			
			}catch(Exception e) {
					e.printStackTrace();
			}
		
	}
	private void deplacement_et_tir_fusee(Stage st) {
		for(int i=0;i<tab_ligne.length;i++) {
			if(tab_ligne[i]==12) {
				bonus();
				tab_ligne[i]=0;
			}
		}
		if(fusee.destruction(p_aliens, root,vies,affichage_vies)) {
			loop.stop();
			gameOver(st);
		}
		if(droite) {
			fusee.droite();
		}
		if(gauche) {
			fusee.gauche();
		}
		if(bas) {
			fusee.bas();
		}
		if(haut) {
			fusee.haut();
		}
		if(feu) {
			gestionTir();
			
		}
		
	}
	private void deplacement_et_tir_alien(){
		int i;
		for(i=0;i<aliens.size();i++) {
			aliens.get(i).tir(root,p_aliens);
			if(aliens.get(i).actualiser(root,p_fusee,score,affiche_score,affichage_vies,vies,tab_ligne,explosions)) {
				aliens.remove(i);
			}
			
		}
	}
	private void update() {
		int i;
		for(i=0;i<p_aliens.size();i++) {
			if(p_aliens.get(i).actualiser(root)) {
				p_aliens.remove(i);
			}
		}
		for(i=0;i<p_fusee.size();i++) {
			if(p_fusee.get(i).actualiser(root,p_aliens)) {
				p_fusee.remove(i);
			}
		}
	}
	private void init_game() {
		int i;
		// constantes de d�placement
		droite=false;
		bas=false;
		haut=false;
		gauche=false;
		bonus=false;
		compteur_bonus=0;
		compteur_explosion=0;
		score[0]=0;
		// constantes de tir
		feu=false;
		// Arraylists de projectiles et aliens
		p_fusee = new ArrayList<Tir>();
		p_aliens = new ArrayList<Tir>();
		aliens = new ArrayList<Alien>();
		explosions = new ArrayList<ImageView>();
		// conteneur et fusee
		root = new Group();
		fusee = new Fusee(230,360,5,-5,10,root,score[0]);
		// affichage des vies
		vies[0]=3;
		Image img_coeur = new Image("coeur.png");
		ImageView ivc1=new ImageView(img_coeur);
		ivc1.setFitHeight(20);
		ivc1.setFitWidth(20);
		ivc1.setX(440);
		ivc1.setY(0);
		ImageView ivc2=new ImageView(img_coeur);
		ivc2.setFitHeight(20);
		ivc2.setFitWidth(20);
		ivc2.setX(460);
		ivc2.setY(0);
		ImageView ivc3=new ImageView(img_coeur);
		ivc3.setFitHeight(20);
		ivc3.setFitWidth(20);
		ivc3.setX(480);
		ivc3.setY(0);
		affichage_vies.add(ivc1);
		affichage_vies.add(ivc2);
		affichage_vies.add(ivc3);
		root.getChildren().addAll(affichage_vies);
		
		// initialisation du niveau 1
		nv= new Niveau1(30);
		
		//initialisattion du score et message bonus
		affiche_score = new Label(String.valueOf(score[0]));
		affiche_score.setFont(new Font(20));
		affiche_score.setPrefWidth(70);
		affiche_score.setPrefHeight(17);
		affiche_score.setLayoutX(0);
		affiche_score.setLayoutY(0);
		affiche_score.setAlignment(Pos.CENTER_LEFT);
		root.getChildren().add(affiche_score);
		message_bonus = new Label("Bonus +30 !");
		message_bonus.setFont(new Font(20));
		message_bonus.setTextFill(Color.INDIANRED);
		message_bonus.setPrefWidth(150);
		message_bonus.setPrefHeight(25.0);
		message_bonus.setLayoutX(250-message_bonus.getPrefWidth()/2);
		message_bonus.setLayoutY(70);
		message_bonus.setAlignment(Pos.CENTER);
		
		// gestion tableau des lignes
		
		tab_ligne = new double[nv.getNbLigne()];
		for(i=0;i<tab_ligne.length;i++) {
			tab_ligne[i]=0;
		}
		
		
		
	}
	private void gameOver(Stage st) {
		double gap=10;
		// affichage game over
		Label message = new Label("Game Over !");
		message.setFont(new Font("Lucida Sans Typewriter Devanagari",50));
		message.setTextFill(Color.DARKMAGENTA);
		message.setAlignment(Pos.CENTER);
		message.setPrefWidth(300);
		message.setPrefHeight(100);
		message.relocate(250-message.getPrefWidth()/2, 200-message.getPrefHeight()/2);
		
		
		// gestion des boutons restart et quit
		Button restart = new Button("Restart");
		Button quit = new Button("Quit");
		restart.setPrefHeight(30);
		restart.setPrefWidth(100);
		restart.setLayoutX(250-restart.getPrefWidth()-gap);
		restart.setLayoutY(message.getLayoutY()+message.getPrefHeight());
		quit.setLayoutX(250+gap);
		quit.setLayoutY(message.getLayoutY()+message.getPrefHeight());
		quit.setPrefHeight(30);
		quit.setPrefWidth(100);
		root.getChildren().addAll(message,restart,quit);
		quit.setOnAction(event->st.close());
		restart.setOnAction(event->start(st));
	}
	private void finNiveau(Stage st) {
		double gap=10;
		loop.stop();
		// affichage fin niveau et warning
		Label message = new Label("lvl 1 completed ! score : "+String.valueOf(score[0]));
		message.setFont(new Font("Lucida Sans Typewriter Devanagari",25));
		message.setTextFill(Color.DARKMAGENTA);
		message.setAlignment(Pos.CENTER);
		message.setPrefWidth(350);
		message.setPrefHeight(100);
		message.relocate(250-message.getPrefWidth()/2, 200-message.getPrefHeight()/2);
		Label message_nv_2 = new Label("lvl 2 under construction");
		message_nv_2.setRotate(40);
		message_nv_2.setFont(new Font("Lucida Sans Typewriter Devanagari",15));
		message_nv_2.setTextFill(Color.DARKRED);
		message_nv_2.setAlignment(Pos.CENTER);
		message_nv_2.setPrefWidth(300);
		message_nv_2.setPrefHeight(100);
		message_nv_2.setLayoutX(250);
		message_nv_2.setLayoutY(100);
		
		// gestion des boutons restart et quit
		Button restart = new Button("Restart");
		Button quit = new Button("Quit");
		Button nv_2 = new Button("next level");
		nv_2.setPrefHeight(30);
		nv_2.setPrefWidth(100);
		nv_2.setLayoutX(250-nv_2.getPrefWidth()/2);
		nv_2.setLayoutY(message.getLayoutY()+message.getPrefHeight());
		
		restart.setPrefHeight(30);
		restart.setPrefWidth(100);
		restart.setLayoutX(250-restart.getPrefWidth()-gap-nv_2.getPrefWidth()/2);
		restart.setLayoutY(message.getLayoutY()+message.getPrefHeight());
		
		quit.setLayoutX(250+gap+nv_2.getPrefWidth()/2);
		quit.setLayoutY(message.getLayoutY()+message.getPrefHeight());
		quit.setPrefHeight(30);
		quit.setPrefWidth(100);
		root.getChildren().addAll(message,restart,quit,nv_2,message_nv_2);
		
		// actions
		quit.setOnAction(event->st.close());
		restart.setOnAction(event->start(st));
	}
	private void bonus() {
		
		root.getChildren().add(message_bonus);
		score[0]+=30;
		affiche_score.setText(String.valueOf(score[0]));
		bonus=true;
	}
	private void affichage_bonus() {
		if(bonus) {
			if(compteur_bonus>50) {
				root.getChildren().remove(message_bonus);
				compteur_bonus=0;
				bonus=false;
			}
			compteur_bonus++;
		}
	}	
	private void explosion() {
		if(explosions.size()!=0) {
			if(compteur_explosion>10) {
				root.getChildren().remove(explosions.get(0));
				explosions.remove(0);
				compteur_explosion=0;
			}
			compteur_explosion++;
		}
	}
	private void gestionTir() {
		if(fusee.getTouche()) {
			fusee.tir(root,p_fusee);
			fusee.setTouche(false);
			fusee.setScore(score[0]);
		}else {
			if(score[0]-fusee.getScore()>100) {
				fusee.tir_evolue(root, p_fusee);
			}else {
				fusee.tir(root, p_fusee);
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	

}