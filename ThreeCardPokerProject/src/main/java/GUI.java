import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GUI extends Application {

	/* TODO:
	 * 1. Finish results screen
	 * 2. New Look
	 * 3. Start Over
	 * 4. Quit (menuBar)
	 * 5. Change draw deck button
	 * 6. Give player info on the left such as winnings
	 * 7. Update fold/play button design
	 * 8. Update text view to show more info
	 */
	
	Stage stage, resultStage;
	BorderPane menuRoot, drawRoot, gameRoot, resultsRoot;
	GridPane bettingRoot, gpCards;
	HashMap<String, Scene> sceneMap;
	VBox vbMenu, vbDrawDeck1, vbDrawDeck2, vbGameButtons, vbDeckP1, vbDeckP2, vbDeckDealer, vbResults1, vbResults2;
	HBox hbBet1P1, hbBet2P1, hbBet1P2, hbBet2P2, hbBetNames, hbDrawDecks, hbFP1, hbFP2, hbFoldPlay, hbGameBets;
	HBox hbBets1, hbBets2, hbCardsP1, hbCardsP2, hbCardsDealer, hbResults1, hbResults2, hbAllResults;
	Text tBet, tPlayer1, tPlayer2, tP1Bet1, tP1Bet2, tP1Bet3, tP2Bet1, tP2Bet2, tP2Bet3;
	Text tP1Status, tP2Status, tWinnings1, tWinnings2, tWin1, tWin2, tWin3, tWin4, tWin5, tWin6, tAnte1, tPP1;
	Text ppAmt1, ppAmt2, tTotalWin1, tTotalWin2, tCash1, tCash2;
	Button start, quit, bTitle, bBet1PlusP1, bBet1MinusP1, bBet1PlusP2, bBet1MinusP2, bBet2PlusP1, bBet2MinusP1, bBet2PlusP2, bBet2MinusP2;
	Button bSubmitBets, bP1Card1, bP1Card2, bP1Card3, bP2Card1, bP2Card2, bP2Card3, bDealerCard1, bDealerCard2, bDealerCard3;
	Button bDrawCards, bFold1, bFold2, bPlay1, bPlay2;
	Button bRevealDealer, bNextRound;
	Image iTitle, iStart, iQuit, iDrawBackground;
	ImageView ivTitle, ivStart, ivQuit, ivP1Card1, ivP1Card2, ivP1Card3, ivP2Card1, ivP2Card2, ivP2Card3, ivDealerCard1, ivDealerCard2, ivDealerCard3;
	ImageView ivBack1, ivBack2, ivBack3, ivBack4, ivBack5, ivBack6, ivDrawCards;
	String sTitle;
	MenuBar menuBar;
	Menu mMenu;
	MenuItem miStartOver, miNewLook, miQuit;
	ListView<String> lvDisplayActions;
	TextField tfAnteBetP1, tfAnteBetP2, tfPPBetP1, tfPPBetP2;
	ThreeCardPokerGame game;
	ArrayList<ImageView> alDealerCards, alPlayer1Cards, alPlayer2Cards;
	Boolean playBet1, playBet2, player1Win, player2Win, queenHigh, newLook, p1Fold, p2Fold;
	Rectangle rRed, rBlue, rBlack;
	int amtWon1, amtWon2;
	double xCenter, yCenter;
	static final int picHeight = 726;
	static final int picWidth = 500;
	
	
	public static void main(String[] args) {
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		stage.centerOnScreen();
		xCenter = stage.getX();
		yCenter = stage.getY();
		newLook = false;
		p1Fold = false;
		p2Fold = false;
		setGame();
		setUpMenu();
	}
	
	//Set up game classes
	public void setGame() {
		game = new ThreeCardPokerGame();
		game.playerOne.setHand(game.theDealer.dealHand());
		game.playerTwo.setHand(game.theDealer.dealHand());
	}
	
	public void setUpMenu() {
		sceneMap = new HashMap<String, Scene>();
		lvDisplayActions = new ListView<String>();
		
		player1Win = false;
		player2Win = false;
		queenHigh = true;
		amtWon1 = 0;
		amtWon2 = 0;
		
		
		stage.setTitle("Three Card Poker");
		menuRoot = new BorderPane();
		menuRoot.setStyle("-fx-background-color: #009900;");
		
		mMenu = new Menu("Options");
		
		miStartOver = new MenuItem("Start Over");
		miNewLook = new MenuItem("New Look");
		miQuit = new MenuItem("Quit");
		
		mMenu.getItems().add(miStartOver);
		mMenu.getItems().add(miNewLook);
		mMenu.getItems().add(miQuit);
		
		menuBar = new MenuBar(mMenu);
		
		
		bTitle = new Button();
		bTitle.setDisable(true);
		iTitle = new Image("title.png");
		ivTitle = new ImageView(iTitle);
		iStart = new Image("start.png");
		ivStart = new ImageView(iStart);
		ivStart.setFitHeight(100);
		ivStart.setFitWidth(200);
		ivStart.setPreserveRatio(true);
		iQuit = new Image("quit.png");
		ivQuit = new ImageView(iQuit);
		ivQuit.setFitHeight(100);
		ivQuit.setFitWidth(200);
		ivQuit.setPreserveRatio(true);
		
		
		start = new Button();
		start.setStyle("-fx-background-color: #990000;");
		quit = new Button();
		quit.setStyle("-fx-background-color: #990000;");
		start.setGraphic(ivStart);
		quit.setGraphic(ivQuit);
		
		vbMenu = new VBox(ivTitle, start, quit);
		start.setMaxSize(200, 100);
		quit.setMaxSize(200, 100);
		vbMenu.setSpacing(10);
		vbMenu.setAlignment(Pos.CENTER);
		menuRoot.setCenter(vbMenu);
		
		menuButtonListeners();
		
		if (newLook) {
			menuRoot.setStyle("-fx-background-color: #D3AF37");
		}
		
	    Scene scene = new Scene(menuRoot, 1280,720);
		stage.setScene(scene);
		stage.show();
		sceneMap.put("menu", scene);
	}
	
	public void menuButtonListeners() {
		start.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				createBettingScreen();
			}
		});
		quit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
	}
	
	public void createBettingScreen() {
		bettingRoot = new GridPane();
		bettingRoot.setAlignment(Pos.CENTER);
		bettingRoot.setStyle("-fx-background-color: #17C4E8");
		
		tBet = new Text("Place your bets!");
		tBet.setFill(Color.WHITE);
		tBet.setStyle("-fx-font: Verdana; -fx-font-size: 32px;");
		
		
		bBet1PlusP1 = new Button("+");
		bBet1PlusP2 = new Button("+");
		tfAnteBetP1 = new TextField("Ante: 5");
		tfAnteBetP2 = new TextField("Ante: 5");
		bBet1MinusP1 = new Button("-");
		bBet1MinusP2 = new Button("-");
		bBet2PlusP1 = new Button("+");
		bBet2PlusP2 = new Button("+");
		tfPPBetP1 = new TextField("Pair-Plus: 0");
		tfPPBetP2 = new TextField("Pair-Plus: 0");
		bBet2MinusP1 = new Button("-");
		bBet2MinusP2 = new Button("-");
		
		bBet1PlusP1.setStyle("-fx-background-color: #5CE817");
		bBet2PlusP1.setStyle("-fx-background-color: #5CE817");
		bBet1PlusP2.setStyle("-fx-background-color: #5CE817");
		bBet2PlusP2.setStyle("-fx-background-color: #5CE817");
		bBet1MinusP1.setStyle("-fx-background-color: #E83B17");
		bBet2MinusP1.setStyle("-fx-background-color: #E83B17");
		bBet1MinusP2.setStyle("-fx-background-color: #E83B17");
		bBet2MinusP2.setStyle("-fx-background-color: #E83B17");
		
		tfAnteBetP1.setEditable(false);
		tfAnteBetP2.setEditable(false);
		
		// Add 3 Columns and 4 Rows
		bettingRoot.getColumnConstraints().addAll(new ColumnConstraints(), new ColumnConstraints(), new ColumnConstraints());
		bettingRoot.getRowConstraints().addAll(new RowConstraints(), new RowConstraints(), new RowConstraints(), new RowConstraints());
		bettingRoot.setAlignment(Pos.CENTER);
		
		hbBet1P1 = new HBox(bBet1MinusP1, tfAnteBetP1, bBet1PlusP1);
		hbBet2P1 = new HBox(bBet2MinusP1, tfPPBetP1, bBet2PlusP1);
		hbBet1P2 = new HBox(bBet1MinusP2, tfAnteBetP2, bBet1PlusP2);
		hbBet2P2 = new HBox(bBet2MinusP2, tfPPBetP2, bBet2PlusP2);
		
		bSubmitBets = new Button("Submit Bets!");
		bSubmitBets.setStyle("-fx-background-color: Green");
		bSubmitBets.setAlignment(Pos.CENTER);
		
		hbBet1P1.setSpacing(5);
		hbBet2P1.setSpacing(5);
		hbBet1P2.setSpacing(5);
		hbBet2P2.setSpacing(5);
		
		bettingRoot.add(tBet, 1, 0);
		bettingRoot.add(hbBet1P1, 0, 1);
		bettingRoot.add(hbBet2P1, 0, 2);
		bettingRoot.add(hbBet1P2, 2, 1);
		bettingRoot.add(hbBet2P2, 2, 2);
		bettingRoot.add(bSubmitBets, 1, 3);
		
		bettingRoot.setMargin(tBet, new Insets(5));
		bettingRoot.setMargin(hbBet1P1, new Insets(5));
		bettingRoot.setMargin(hbBet2P1, new Insets(5));
		bettingRoot.setMargin(hbBet1P2, new Insets(5));
		bettingRoot.setMargin(hbBet2P2, new Insets(5));
		bettingRoot.getColumnConstraints().get(1).setHalignment(HPos.CENTER);
		setBettingListeners();
		
		if (newLook) {
			bettingRoot.setStyle("-fx-background-color: Black");
			bBet1PlusP1.setStyle("-fx-background-color: #34e220");
			bBet1PlusP2.setStyle("-fx-background-color: #34e220");
			bBet1MinusP1.setStyle("-fx-background-color: #9449dc");
			bBet1MinusP2.setStyle("-fx-background-color:#9449dc");
			bBet2PlusP1.setStyle("-fx-background-color: #34e220");
			bBet2PlusP2.setStyle("-fx-background-color: #34e220");
			bBet2MinusP1.setStyle("-fx-background-color: #9449dc");
			bBet2MinusP2.setStyle("-fx-background-color: #9449dc");
			tBet.setStyle("-fx-font: Impact; -fx-font-size: 32px;");
			bSubmitBets.setStyle("-fx-background-color: White; -fx-font: Impact");
		}
		
		Scene scPopUp = new Scene(bettingRoot, 900, 600);
		sceneMap.put("betting", scPopUp);
		stage.setTitle("Place your bets!");
		stage.setScene(scPopUp);
	}
	
	public void setBettingListeners() {
		final EventHandler<ActionEvent> minus = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Player 1 Bets
				if (event.getSource() == bBet1MinusP1) {
					if (game.playerOne.anteBet <= 5) {
						return;
					}
					game.playerOne.setAnteBet(game.playerOne.getAnteBet() - 1);
					tfAnteBetP1.setText(String.valueOf(game.playerOne.anteBet));
				}
				if (event.getSource() == bBet2MinusP1) {
					if (game.playerOne.pairPlusBet <= 0) {
						return;
					}
					game.playerOne.setPairPlusBet(game.playerOne.getPairPlusBet() - 1);
					if (game.playerOne.getPairPlusBet() < 5) {
						game.playerOne.setPairPlusBet(0);
					}
					tfPPBetP1.setText(String.valueOf(game.playerOne.getPairPlusBet()));
				}
				
				// Player 2 Bets
				if (event.getSource() == bBet1MinusP2) {
					if (game.playerTwo.anteBet <= 5) {
						return;
					}
					game.playerTwo.setAnteBet(game.playerTwo.getAnteBet() - 1);
					tfAnteBetP2.setText(String.valueOf(game.playerTwo.anteBet));
				}
				if (event.getSource() == bBet2MinusP2) {
					if (game.playerTwo.pairPlusBet <= 0) {
						return;
					}
					game.playerTwo.setPairPlusBet(game.playerTwo.getPairPlusBet() - 1);
					if (game.playerTwo.getPairPlusBet() < 5) {
						game.playerTwo.setPairPlusBet(0);
					}
					tfPPBetP2.setText(String.valueOf(game.playerTwo.getPairPlusBet()));
				}
			}
		};
		final EventHandler<ActionEvent> plus = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Player 1 Bets
				if (event.getSource() == bBet1PlusP1) {
					if (game.playerOne.anteBet >= 25 || game.playerOne.anteBet > game.playerOne.cash) {
						return;
					}
					game.playerOne.setAnteBet(game.playerOne.getAnteBet() + 1);
					tfAnteBetP1.setText(String.valueOf(game.playerOne.anteBet));
				}
				if (event.getSource() == bBet2PlusP1) {
					if (game.playerOne.pairPlusBet >= 25 || game.playerOne.pairPlusBet > game.playerOne.cash) {
						return;
					}
					game.playerOne.setPairPlusBet(game.playerOne.getPairPlusBet() + 1);
					if (game.playerOne.getPairPlusBet() < 5) {
						game.playerOne.setPairPlusBet(5);
					}
					tfPPBetP1.setText(String.valueOf(game.playerOne.getPairPlusBet()));
				}
				
				// Player 2 Bets
				if (event.getSource() == bBet1PlusP2) {
					if (game.playerTwo.anteBet >= 25 || game.playerTwo.anteBet > game.playerTwo.cash) {
						return;
					}
					game.playerTwo.setAnteBet(game.playerTwo.getAnteBet() + 1);
					tfAnteBetP2.setText(String.valueOf(game.playerTwo.anteBet));
				}
				if (event.getSource() == bBet2PlusP2) {
					if (game.playerTwo.pairPlusBet >= 25 || game.playerTwo.pairPlusBet > game.playerTwo.cash) {
						return;
					}
					game.playerTwo.setPairPlusBet(game.playerTwo.getPairPlusBet() + 1);
					if (game.playerTwo.getPairPlusBet() < 5) {
						game.playerTwo.setPairPlusBet(5);
					}
					tfPPBetP2.setText(String.valueOf(game.playerTwo.getPairPlusBet()));
				}
			}
		};
		bBet1PlusP1.setOnAction(plus);
		bBet2PlusP1.setOnAction(plus);
		bBet1PlusP2.setOnAction(plus);
		bBet2PlusP2.setOnAction(plus);
		
		bBet1MinusP1.setOnAction(minus);
		bBet2MinusP1.setOnAction(minus);
		bBet1MinusP2.setOnAction(minus);
		bBet2MinusP2.setOnAction(minus);
		
		bSubmitBets.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				game.playerOne.cash = game.playerOne.cash - game.playerOne.anteBet - game.playerOne.pairPlusBet;
				game.playerTwo.cash = game.playerTwo.cash - game.playerTwo.anteBet - game.playerTwo.pairPlusBet;
				createDrawScreen();
			}
		});
	}
	
	public void createDrawScreen() {
		drawRoot = new BorderPane();
		playBet1 = false;
		playBet2 = false;
		
		iDrawBackground = new Image("draw2.jpg");
		drawRoot.setBackground(new Background(new BackgroundImage(iDrawBackground, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
		
		tPlayer1 = new Text("Player 1:");
		tPlayer2 = new Text("Player 2: ");
		tPlayer1.setFill(Color.WHITE);
		tPlayer2.setFill(Color.WHITE);
		tPlayer1.setStyle("-fx-font: Verdana; -fx-font-size: 32px;");
		tPlayer2.setStyle("-fx-font: Verdana; -fx-font-size: 32px;");
		
		ppAmt1 = new Text();
		ppAmt2 = new Text();
		
		hbBetNames = new HBox(tPlayer1, tPlayer2);
		hbBetNames.setPadding(new Insets(7));
		hbBetNames.setSpacing(650);
		
		bP1Card1 = new Button();
		bP1Card2 = new Button();
		bP1Card3 = new Button();
		bP2Card1 = new Button();
		bP2Card2 = new Button();
		bP2Card3 = new Button();
		
		bDrawCards = new Button();
		bDrawCards.setStyle("-fx-background-color: Transparent");
		
		ivDrawCards = new ImageView(new Image("drawCards.png"));
		ivDrawCards.setFitHeight(120);
		ivDrawCards.setFitWidth(200);
		ivDrawCards.setPreserveRatio(true);
		bDrawCards.setGraphic(ivDrawCards);
		
		// Player 1/2 Card Images
		ivP1Card1 = new ImageView(new Image(game.playerOne.getHand().get(0).getiLocation()));
		ivP1Card1.setFitHeight(picHeight / 5);
		ivP1Card1.setFitWidth(picWidth / 5);
		ivP1Card1.setPreserveRatio(true);
		alPlayer1Cards = new ArrayList<ImageView>();
		alPlayer1Cards.add(ivP1Card1);
		ivP1Card2 = new ImageView(new Image(game.playerOne.getHand().get(1).getiLocation()));
		ivP1Card2.setFitHeight(picHeight / 5);
		ivP1Card2.setFitWidth(picWidth / 5);
		ivP1Card2.setPreserveRatio(true);
		alPlayer1Cards.add(ivP1Card2);
		ivP1Card3 = new ImageView(new Image(game.playerOne.getHand().get(2).getiLocation()));
		ivP1Card3.setFitHeight(picHeight / 5);
		ivP1Card3.setFitWidth(picWidth / 5);
		ivP1Card3.setPreserveRatio(true);
		//bP1Card3.setGraphic(ivP1Card3);
		alPlayer1Cards.add(ivP1Card3);
		
		ivP2Card1 = new ImageView(new Image(game.playerTwo.getHand().get(0).getiLocation()));
		ivP2Card1.setFitHeight(picHeight / 5);
		ivP2Card1.setFitWidth(picWidth / 5);
		ivP2Card1.setPreserveRatio(true);
		//bP2Card1.setGraphic(ivP2Card1);
		alPlayer2Cards = new ArrayList<ImageView>();
		alPlayer2Cards.add(ivP2Card1);
		ivP2Card2 = new ImageView(new Image(game.playerTwo.getHand().get(1).getiLocation()));
		ivP2Card2.setFitHeight(picHeight / 5);
		ivP2Card2.setFitWidth(picWidth / 5);
		ivP2Card2.setPreserveRatio(true);
		//bP2Card2.setGraphic(ivP2Card2);
		alPlayer2Cards.add(ivP2Card2);
		ivP2Card3 = new ImageView(new Image(game.playerTwo.getHand().get(2).getiLocation()));
		ivP2Card3.setFitHeight(picHeight / 5);
		ivP2Card3.setFitWidth(picWidth / 5);
		ivP2Card3.setPreserveRatio(true);
		//bP2Card3.setGraphic(ivP2Card3);
		alPlayer2Cards.add(ivP2Card3);
		
		ivBack1 = new ImageView(new Image("back.jpg"));
		ivBack1.setFitHeight(picHeight / 5);
		ivBack1.setFitWidth(picWidth / 5);
		ivBack1.setPreserveRatio(true);
		ivBack2 = new ImageView(new Image("back.jpg"));
		ivBack2.setFitHeight(picHeight / 5);
		ivBack2.setFitWidth(picWidth / 5);
		ivBack2.setPreserveRatio(true);
		ivBack3 = new ImageView(new Image("back.jpg"));
		ivBack3.setFitHeight(picHeight / 5);
		ivBack3.setFitWidth(picWidth / 5);
		ivBack3.setPreserveRatio(true);
		ivBack4 = new ImageView(new Image("back.jpg"));
		ivBack4.setFitHeight(picHeight / 5);
		ivBack4.setFitWidth(picWidth / 5);
		ivBack4.setPreserveRatio(true);
		ivBack5 = new ImageView(new Image("back.jpg"));
		ivBack5.setFitHeight(picHeight / 5);
		ivBack5.setFitWidth(picWidth / 5);
		ivBack5.setPreserveRatio(true);
		ivBack6 = new ImageView(new Image("back.jpg"));
		ivBack6.setFitHeight(picHeight / 5);
		ivBack6.setFitWidth(picWidth / 5);
		ivBack6.setPreserveRatio(true);
		
		bP1Card1.setGraphic(ivBack1);
		bP1Card2.setGraphic(ivBack2);
		bP1Card3.setGraphic(ivBack3);
		bP2Card1.setGraphic(ivBack4);
		bP2Card2.setGraphic(ivBack5);
		bP2Card3.setGraphic(ivBack6);
		
		bP1Card1.setStyle("-fx-background-color: Red");
		bP1Card2.setStyle("-fx-background-color: Red");
		bP1Card3.setStyle("-fx-background-color: Red");
		bP2Card1.setStyle("-fx-background-color: #565ae2");
		bP2Card2.setStyle("-fx-background-color: #565ae2");
		bP2Card3.setStyle("-fx-background-color: #565ae2");
		
		alDealerCards = new ArrayList<ImageView>();
		ivDealerCard1 = new ImageView(new Image(game.theDealer.getDealersHand().get(0).getiLocation()));
		ivDealerCard1.setFitHeight(picHeight / 6);
		ivDealerCard1.setFitWidth(picWidth / 6);
		ivDealerCard1.setPreserveRatio(true);
		alDealerCards.add(ivDealerCard1);
		ivDealerCard2 = new ImageView(new Image(game.theDealer.getDealersHand().get(1).getiLocation()));
		ivDealerCard2.setFitHeight(picHeight / 6);
		ivDealerCard2.setFitWidth(picWidth / 6);
		ivDealerCard2.setPreserveRatio(true);
		alDealerCards.add(ivDealerCard2);
		ivDealerCard3 = new ImageView(new Image(game.theDealer.getDealersHand().get(2).getiLocation()));
		ivDealerCard3.setFitHeight(picHeight / 6);
		ivDealerCard3.setFitWidth(picWidth / 6);
		ivDealerCard3.setPreserveRatio(true);
		alDealerCards.add(ivDealerCard3);
		
		bFold1 = new Button("Fold");
		bFold2 = new Button("Fold");
		bPlay1 = new Button("Play");
		bPlay2 = new Button("Play");
		
		
		hbFP1 = new HBox(bFold1, bPlay1);
		hbFP2 = new HBox(bFold2, bPlay2);
		
		
		// Holds the deck of player 1
		vbDrawDeck1 = new VBox(bP1Card1, bP1Card2, bP1Card3);
		
		// Holds the deck of player 2
		vbDrawDeck2 = new VBox(bP2Card1, bP2Card2, bP2Card3);
		
		VBox temp = new VBox(bDrawCards, ppAmt1, ppAmt2);
		
		// Combines the each deck VBox into an HBox
		hbDrawDecks = new HBox(vbDrawDeck1, temp, vbDrawDeck2);
		hbDrawDecks.setSpacing(225);
		hbDrawDecks.setHgrow(bDrawCards, Priority.ALWAYS);
		
		
		hbFoldPlay = new HBox(hbFP1, hbFP2);
		hbFoldPlay.setSpacing(750);
		hbFoldPlay.setVisible(false);
		
		bPlay1.setOnAction(e-> {
			game.playerOne.playBet = game.playerOne.anteBet;
			playBet1 = true;
			bPlay1.setVisible(false);
			bFold1.setVisible(false);
			game.playerOne.cash = game.playerOne.cash - game.playerOne.playBet;
			checkPlayBet();
		});
		bPlay2.setOnAction(e-> {
			game.playerTwo.playBet = game.playerTwo.anteBet;
			playBet2 = true;
			bPlay2.setVisible(false);
			bFold2.setVisible(false);
			game.playerTwo.cash = game.playerTwo.cash - game.playerTwo.playBet;
			checkPlayBet();
		});
		bFold1.setOnAction(e -> {
			game.playerOne.playBet = 0;
			hbFP1.setVisible(false);
			bPlay1.setVisible(false);
			playBet1 = true;
			p1Fold = true;
			checkPlayBet();
		});
		bFold2.setOnAction(e -> {
			game.playerTwo.playBet = 0;
			hbFP2.setVisible(false);
			bPlay2.setVisible(false);
			playBet2 = true;
			p2Fold = true;
			checkPlayBet();
		});
		drawCardsListener();
		
		drawRoot.setTop(hbBetNames);
		drawRoot.setCenter(hbDrawDecks);
		drawRoot.setBottom(hbFoldPlay);
	
		if (newLook) {
			drawRoot.setBackground(null);
			drawRoot.setStyle("-fx-background-color: Black");
			bP1Card1.setStyle("-fx-background-color: #9449dc");
			bP1Card2.setStyle("-fx-background-color: #9449dc");
			bP1Card3.setStyle("-fx-background-color: #9449dc");
			bP2Card1.setStyle("-fx-background-color: #34e220");
			bP2Card2.setStyle("-fx-background-color: #34e220");
			bP2Card3.setStyle("-fx-background-color: #34e220");
			ppAmt1.setStyle("-fx-font: 16 Impact");
			ppAmt1.setFill(Color.MEDIUMPURPLE);
			ppAmt2.setStyle("-fx-font: 16 Impact");
			ppAmt2.setFill(Color.LIGHTGREEN);
		}
		
		Scene drawScene = new Scene(drawRoot, 900, 600);
		sceneMap.put("draw", drawScene);
		stage.setTitle("Place your bets!");
		stage.setScene(drawScene);
	}
	
	public void drawCardsListener(){
		bDrawCards.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				PauseTransition pause = new PauseTransition(Duration.seconds(1));
				pause.setOnFinished(e -> {
					bP1Card1.setGraphic(alPlayer1Cards.get(0));
					bP2Card1.setGraphic(alPlayer2Cards.get(0));
				});
				pause.play();
				PauseTransition pause2 = new PauseTransition(Duration.seconds(2));
				pause2.setOnFinished(e -> {
					bP1Card2.setGraphic(alPlayer1Cards.get(1));
					bP2Card2.setGraphic(alPlayer2Cards.get(1));
				});
				pause2.play();
				PauseTransition pause3 = new PauseTransition(Duration.seconds(3));
				pause3.setOnFinished(e -> {
					bP1Card3.setGraphic(alPlayer1Cards.get(2));
					bP2Card3.setGraphic(alPlayer2Cards.get(2));
					hbFoldPlay.setVisible(true);
					showPPWin();
				});
				pause3.play();
			}
		});
	}
	
	public void showPPWin() {
		ppAmt1.setText("P1 Pair-Plus: $" + ThreeCardLogic.evalPPWinnings(game.playerOne.getHand(), game.playerOne.pairPlusBet));
		ppAmt2.setText("P2 Pair-Plus: $" + ThreeCardLogic.evalPPWinnings(game.playerTwo.getHand(), game.playerTwo.pairPlusBet));
		game.playerOne.totalWinnings = game.playerOne.totalWinnings + ThreeCardLogic.evalPPWinnings(game.playerOne.getHand(), game.playerOne.pairPlusBet);
		game.playerTwo.totalWinnings = game.playerTwo.totalWinnings + ThreeCardLogic.evalPPWinnings(game.playerTwo.getHand(), game.playerTwo.pairPlusBet);
		ppAmt1.setFill(Color.RED);
		ppAmt1.setStyle("-fx-font: 16 Verdana");
		ppAmt2.setFill(Color.BLUE);
		ppAmt2.setStyle("-fx-font: 16 Verdana");
	}
	
	public void checkPlayBet() {
		if (playBet1 && playBet2) {
			lvDisplayActions.getItems().add("Game Started");
			createGameScreen();
		}
	}
	
	public void createGameScreen() {
		stage.setTitle("Three Card Poker");
		gameRoot = new BorderPane();
		gpCards = new GridPane();
		readPlayerCards();
		
		tP1Bet1 = new Text("Ante: $" + game.playerOne.anteBet);
		tP2Bet1 = new Text("Ante: $" + game.playerTwo.anteBet);
		tP1Bet2 = new Text("PP: $" + game.playerOne.pairPlusBet);
		tP2Bet2 = new Text("PP: $" + game.playerTwo.pairPlusBet);
		tP1Bet3 = new Text("Play: $" + game.playerOne.playBet);
		tP2Bet3 = new Text("Play: $" + game.playerTwo.playBet);
		
		tP1Bet1.setFill(Color.WHITE);
		tP2Bet1.setFill(Color.WHITE);
		tP1Bet1.setStyle("-fx-font: 16 Verdana;-fx-background-color: white;");
		tP2Bet1.setStyle("-fx-font: 16 Verdana;-fx-background-color: white;");
		tP1Bet2.setFill(Color.WHITE);
		tP2Bet2.setFill(Color.WHITE);
		tP1Bet2.setStyle("-fx-font: 16 Verdana;-fx-background-color: white;");
		tP2Bet2.setStyle("-fx-font: 16 Verdana;-fx-background-color: white;");
		tP1Bet3.setFill(Color.WHITE);
		tP2Bet3.setFill(Color.WHITE);
		tP1Bet3.setStyle("-fx-font: 16 Verdana;-fx-background-color: white;");
		tP2Bet3.setStyle("-fx-font: 16 Verdana;-fx-background-color: white;");
		
		hbBets1 = new HBox(tP1Bet1, tP1Bet2, tP1Bet3);
		hbBets2 = new HBox(tP2Bet1, tP2Bet2, tP2Bet3);
		hbBets1.setSpacing(10);
		hbBets2.setSpacing(10);
		hbGameBets = new HBox(hbBets1, hbBets2);
		hbGameBets.setSpacing(100);
		
		gpCards.getRowConstraints().addAll(new RowConstraints(), new RowConstraints());
		gpCards.getColumnConstraints().addAll(new ColumnConstraints(), new ColumnConstraints(), new ColumnConstraints());
		
		ivP1Card1.setFitHeight(picHeight / 6);
		ivP1Card1.setFitWidth(picWidth / 6);
		ivP1Card2.setFitHeight(picHeight / 6);
		ivP1Card2.setFitWidth(picWidth / 6);
		ivP1Card3.setFitHeight(picHeight / 6);
		ivP1Card3.setFitWidth(picWidth / 6);
		ivP2Card1.setFitHeight(picHeight / 6);
		ivP2Card1.setFitWidth(picWidth / 6);
		ivP2Card2.setFitHeight(picHeight / 6);
		ivP2Card2.setFitWidth(picWidth / 6);
		ivP2Card3.setFitHeight(picHeight / 6);
		ivP2Card3.setFitWidth(picWidth / 6);
		ivBack1.setFitHeight(picHeight / 6);
		ivBack1.setFitWidth(picHeight / 6);
		ivBack2.setFitHeight(picHeight / 6);
		ivBack2.setFitWidth(picHeight / 6);
		ivBack3.setFitHeight(picHeight / 6);
		ivBack3.setFitWidth(picHeight / 6);
		
		
		hbCardsP1 = new HBox(bP1Card1, bP1Card2, bP1Card3);
		hbCardsP2 = new HBox(bP2Card1, bP2Card2, bP2Card3);
		
		bP1Card1.setStyle("-fx-background-color: transparent");
		bP1Card2.setStyle("-fx-background-color: transparent");
		bP1Card3.setStyle("-fx-background-color: transparent");
		bP2Card1.setStyle("-fx-background-color: transparent");
		bP2Card2.setStyle("-fx-background-color: transparent");
		bP2Card3.setStyle("-fx-background-color: transparent");
		
		bDealerCard1 = new Button();
		bDealerCard2 = new Button();
		bDealerCard3 = new Button();
		
		bDealerCard1.setStyle("-fx-background-color: transparent");
		bDealerCard2.setStyle("-fx-background-color: transparent");
		bDealerCard3.setStyle("-fx-background-color: transparent");
		
		bDealerCard1.setGraphic(ivBack1);
		bDealerCard2.setGraphic(ivBack2);
		bDealerCard3.setGraphic(ivBack3);
		
		hbCardsDealer = new HBox(bDealerCard1, bDealerCard2, bDealerCard3);
		
		rRed = new Rectangle();
		rRed.setFill(Color.RED);
		rRed.setWidth((picWidth / 6) * 4 - 35);
		rRed.setHeight(3);
		vbDeckP1 = new VBox(hbCardsP1, rRed);
		rBlue = new Rectangle();
		rBlue.setFill(Color.BLUE);
		rBlue.setWidth((picWidth / 6) * 4 - 35);
		rBlue.setHeight(3);
		vbDeckP2 = new VBox(hbCardsP2, rBlue);
		rBlack = new Rectangle();
		rBlack.setFill(Color.BLACK);
		rBlack.setWidth((picWidth / 6) * 4 - 35);
		rBlack.setHeight(3);
		vbDeckDealer = new VBox(hbCardsDealer, rBlack);
		
		gpCards.add(vbDeckP1, 0, 1);
		gpCards.add(vbDeckP2, 2, 1);
		gpCards.add(vbDeckDealer, 1, 0);
		gpCards.setAlignment(Pos.CENTER);
		
		bRevealDealer = new Button("Reveal Dealers Cards");
		bRevealDealer.setOnAction(e-> countdownDealer());
		
		Text p1 = new Text("P1: ");
		p1.setFill(Color.WHITE);
		p1.setStyle("-fx-font: 16 Verdana");
		Text p2 = new Text("P2: ");
		p2.setStyle("-fx-font: 16 Verdana");
		p2.setFill(Color.WHITE);
		tTotalWin1 = new Text("Total: $" + game.playerOne.getTotalWinnings());
		tTotalWin2 = new Text("Total: $" + game.playerTwo.getTotalWinnings());
		tCash1 = new Text("Cash: $" + game.playerOne.cash);
		tCash2 = new Text("Cash: $" + game.playerTwo.cash);
		tTotalWin1.setFill(Color.RED);
		tTotalWin2.setFill(Color.BLUE);
		tCash1.setFill(Color.RED);
		tCash2.setFill(Color.BLUE);
		
		tTotalWin1.setStyle("-fx-font: 24 Verdana");
		tTotalWin2.setStyle("-fx-font: 24 Verdana");
		tCash1.setStyle("-fx-font: 24 Verdana");
		tCash2.setStyle("-fx-font: 24 Verdana");
		
		
		vbGameButtons = new VBox(bRevealDealer, p1, tCash1, tTotalWin1, p2, tCash2, tTotalWin2);
		vbGameButtons.setSpacing(20);
		vbGameButtons.setAlignment(Pos.BASELINE_LEFT);
		
		
		menuBarListeners();
		gameRoot.setCenter(gpCards);
		gameRoot.setBottom(hbGameBets);
		gameRoot.setRight(lvDisplayActions);
		gameRoot.setTop(menuBar);
		gameRoot.setLeft(vbGameButtons);
		
		
		gameRoot.setBackground(new Background(
				new BackgroundImage(new Image("gameBack.jpg"),
						BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
						BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
		
		if (newLook) {
			gameRoot.setBackground(null);
			gameRoot.setStyle("-fx-background-color: Black");
			tP1Bet1.setStyle("-fx-font: 16 Impact;-fx-background-color: white;");
			tP2Bet1.setStyle("-fx-font: 16 Impact;-fx-background-color: white;");
			tP1Bet2.setStyle("-fx-font: 16 Impact;-fx-background-color: white;");
			tP2Bet2.setStyle("-fx-font: 16 Impact;-fx-background-color: white;");
			tP1Bet3.setStyle("-fx-font: 16 Impact;-fx-background-color: white;");
			tP2Bet3.setStyle("-fx-font: 16 Impact;-fx-background-color: white;");
			rRed.setFill(Color.MEDIUMPURPLE);
			rBlue.setFill(Color.LIGHTGREEN);
			ppAmt1.setFill(Color.MEDIUMPURPLE);
			ppAmt1.setStyle("-fx-font: 16 Impact");
			ppAmt2.setFill(Color.LIGHTGREEN);
			ppAmt2.setStyle("-fx-font: 16 Impact");
		}
		Scene scene = new Scene(gameRoot, 1300,720);
		stage.setScene(scene);
		stage.show();
		sceneMap.put("game", scene);
	}
	
	
	public void readPlayerCards() {
		int p1 = ThreeCardLogic.evalHand(game.playerOne.getHand());
		int p2 = ThreeCardLogic.evalHand(game.playerTwo.getHand());
		
		if (p1 == 0) {
			lvDisplayActions.getItems().add("Player 1's Hand: High Card");
		} else if (p1 == 1) {
			lvDisplayActions.getItems().add("Player 1's Hand: Straight Flush");
		} else if (p1 == 2) {
			lvDisplayActions.getItems().add("Player 1's Hand: Three of a Kind");
		} else if (p1 == 3) {
			lvDisplayActions.getItems().add("Player 1's Hand: Straight");
		} else if (p1 == 4) {
			lvDisplayActions.getItems().add("Player 1's Hand: Flush");
		} else if (p1 == 5) {
			lvDisplayActions.getItems().add("Player 1's Hand: Pair");
		}
		
		if (p2 == 0) {
			lvDisplayActions.getItems().add("Player 2's Hand: High Card");
		} else if (p2 == 1) {
			lvDisplayActions.getItems().add("Player 2's Hand: Straight Flush");
		} else if (p2 == 2) {
			lvDisplayActions.getItems().add("Player 2's Hand: Three of a Kind");
		} else if (p2 == 3) {
			lvDisplayActions.getItems().add("Player 2's Hand: Straight");
		} else if (p2 == 4) {
			lvDisplayActions.getItems().add("Player 2's Hand: Flush");
		} else if (p2 == 5) {
			lvDisplayActions.getItems().add("Player 2's Hand: Pair");
		}
	}
	
	public void countdownDealer() {
		PauseTransition pause = new PauseTransition(Duration.seconds(1));
		pause.setOnFinished(e -> {
			lvDisplayActions.getItems().add("Revealing Dealer's Hand: 5");
		});
		pause.play();
		PauseTransition pause2 = new PauseTransition(Duration.seconds(2));
		pause2.setOnFinished(e -> {
			lvDisplayActions.getItems().add("Revealing Dealer's Hand: 4");
		});
		pause2.play();
		PauseTransition pause3 = new PauseTransition(Duration.seconds(3));
		pause3.setOnFinished(e -> {
			lvDisplayActions.getItems().add("Revealing Dealer's Hand: 3");
		});
		pause3.play();
		PauseTransition pause4 = new PauseTransition(Duration.seconds(4));
		pause4.setOnFinished(e -> {
			lvDisplayActions.getItems().add("Revealing Dealer's Hand: 2");
		});
		pause4.play();
		PauseTransition pause5 = new PauseTransition(Duration.seconds(5));
		pause5.play();
		pause5.setOnFinished(e -> {
			lvDisplayActions.getItems().add("Revealing Dealer's Hand: 1");
			flipDealersCards();
		});
		pause5.play();
	}
	
	public void flipDealersCards() {
		PauseTransition pause = new PauseTransition(Duration.seconds(1));
		pause.setOnFinished(e -> {
			bDealerCard1.setGraphic(ivDealerCard1);
		});
		pause.play();
		PauseTransition pause2 = new PauseTransition(Duration.seconds(2));
		pause2.setOnFinished(e -> {
			bDealerCard2.setGraphic(ivDealerCard2);
		});
		pause2.play();
		PauseTransition pause3 = new PauseTransition(Duration.seconds(3));
		pause3.setOnFinished(e -> {
			bDealerCard3.setGraphic(ivDealerCard3);
			readDealersHand();
			checkForQueenHigh();
			evaluateWinnings();
			createResultsScreen();
		});
		pause3.play();
		bRevealDealer.setDisable(true);
	}
	
	public void readDealersHand() {
		int val = ThreeCardLogic.evalHand(game.theDealer.getDealersHand());
		if (val == 0) {
			lvDisplayActions.getItems().add("Dealer's Hand: High Card");
		} else if (val == 1) {
			lvDisplayActions.getItems().add("Dealer's Hand: Straight Flush");
		} else if (val == 2) {
			lvDisplayActions.getItems().add("Dealer's Hand: Three of a Kind");
		} else if (val == 3) {
			lvDisplayActions.getItems().add("Dealer's Hand: Straight");
		} else if (val == 4) {
			lvDisplayActions.getItems().add("Dealer's Hand: Flush");
		} else if (val == 5) {
			lvDisplayActions.getItems().add("Dealer's Hand: Pair");
		}
	}
	
	public void evaluateWinnings() {
		tP1Status = new Text();
		tP2Status = new Text();
		
		tWinnings1 = new Text();
		tWinnings2 = new Text();
		tWin1 = new Text();
		tWin2 = new Text();
		tWin3 = new Text();
		tWin4 = new Text();
		tWin5 = new Text();
		tWin6 = new Text();
		
		
		if (ThreeCardLogic.compareHands(game.theDealer.getDealersHand(), game.playerOne.getHand()) == 2 && !p1Fold) {
			player1Win = true;
			tP1Status.setText("P1 WINNER!!");
		} else if (ThreeCardLogic.compareHands(game.theDealer.getDealersHand(), game.playerOne.getHand()) == 1 || p1Fold) {
			player1Win = false;
			tP1Status.setText("P1 LOSS");
		} else {
			player1Win = false;
			tP1Status.setText("P1 TIED");
		}
		if (ThreeCardLogic.compareHands(game.theDealer.getDealersHand(), game.playerTwo.getHand()) == 2 && !p2Fold) {
			player2Win = true;
			tP2Status.setText("P2 WINNER!!");
		} else if (ThreeCardLogic.compareHands(game.theDealer.getDealersHand(), game.playerTwo.getHand()) == 1 || p2Fold) {
			player2Win = false;
			tP2Status.setText("P2 LOSS");
		} else {
			tP2Status.setText("P2 TIED");
		}
		
		if (!queenHigh && !player1Win && !p1Fold) {
			player1Win = true;
			tP1Status.setText("P1 WINNER!!");
		}
		if (!queenHigh && !player2Win && !p1Fold) {
			player2Win = true;
			tP2Status.setText("P2 WINNER!!");
		}
		
		// Add winnings
		if (player1Win) {
			amtWon1 += game.playerOne.getAnteBet();
			amtWon1 += game.playerOne.playBet;
			tWin1.setText("Ante: $" + game.playerOne.getAnteBet());
			tWin2.setText("Pair Plus: $" + game.playerOne.getPairPlusBet());
			tWin3.setText("Play Bet: $" + game.playerOne.getPlayBet());
		} else {
			tWin1.setText("Ante: $0");
			tWin2.setText("Pair Plus: $0");
			tWin3.setText("Play Bet: $0");
			tWin1.setStyle("-fx-background-color: Red");
			tWin2.setStyle("-fx-background-color: Red");
			tWin3.setStyle("-fx-background-color: Red");
		}
		if (player2Win) {
			amtWon2 += game.playerTwo.getAnteBet();
			amtWon2 += game.playerTwo.playBet;
			tWin4.setText("Ante: $" + game.playerTwo.getAnteBet());
			tWin5.setText("Pair Plus: $" + game.playerTwo.getPairPlusBet());
			tWin6.setText("Play Bet: $" + game.playerTwo.getPlayBet());
		} else {
			tWin4.setText("Ante: $0");
			tWin5.setText("Pair Plus: $0");
			tWin6.setText("Play Bet: $0");
			tWin4.setStyle("-fx-background-color: Red");
			tWin5.setStyle("-fx-background-color: Red");
			tWin6.setStyle("-fx-background-color: Red");
		}
		
		tWinnings1.setText("$" + amtWon1);
		tWinnings2.setText("$" + amtWon2);
		tWinnings1.setFill(Color.WHITE);
		tWinnings2.setFill(Color.WHITE);
		
		game.playerOne.addCash(amtWon1);
		game.playerTwo.addCash(amtWon2);
		game.playerOne.totalWinnings = game.playerOne.totalWinnings + amtWon1;
		game.playerTwo.totalWinnings = game.playerTwo.totalWinnings + amtWon2;
		p1Fold = false;
		p2Fold = false;
	}
	
	public void createResultsScreen() {
		resultsRoot = new BorderPane();
		resultStage = new Stage();
		resultStage.setTitle("Results!");
		resultsRoot.setStyle("-fx-background-color: #65E82A");
		
		
		bNextRound = new Button("Next Round");
		bNextRound.setStyle("-fx-background-color: #4DC916");
		bNextRound.setMaxSize(400, 100);
		
		tWin1.setStyle("-fx-font: 16 Verdana");
		tWin2.setStyle("-fx-font: 16 Verdana");
		tWin3.setStyle("-fx-font: 16 Verdana");
		tWin4.setStyle("-fx-font: 16 Verdana");
		tWin5.setStyle("-fx-font: 16 Verdana");
		tWin6.setStyle("-fx-font: 16 Verdana");
		tWin1.setFill(Color.WHITE);
		tWin2.setFill(Color.WHITE);
		tWin3.setFill(Color.WHITE);
		tWin4.setFill(Color.WHITE);
		tWin5.setFill(Color.WHITE);
		tWin6.setFill(Color.WHITE);
		
		hbResults1 = new HBox(tWin1, tWin2, tWin3);
		hbResults2 = new HBox(tWin4, tWin5, tWin6);
		hbResults1.setSpacing(20);
		hbResults2.setSpacing(20);
		
		tP1Status.setStyle("-fx-font: 32 Verdana");
		tP2Status.setStyle("-fx-font: 32 Verdana");
		tWinnings1.setStyle("-fx-font: 28 Verdana");
		tWinnings2.setStyle("-fx-font: 28 Verdana");
		
		tP1Status.setFill(Color.WHITE);
		tP2Status.setFill(Color.WHITE);
		tWinnings1.setFill(Color.WHITE);
		tWinnings2.setFill(Color.WHITE);
		
		vbResults1 = new VBox(tP1Status, tWinnings1, hbResults1);
		vbResults1.setAlignment(Pos.CENTER);
		vbResults2 = new VBox(tP2Status, tWinnings2, hbResults2);
		vbResults2.setAlignment(Pos.CENTER);
		
		hbAllResults = new HBox(vbResults1, vbResults2);
		hbAllResults.setAlignment(Pos.CENTER);
		
		resultsRoot.setCenter(hbAllResults);
		resultsRoot.setBottom(bNextRound);
		
		bNextRound.setOnAction(e -> newRound());
		
		if (newLook) {
			resultsRoot.setStyle("-fx-background-color: #9449dc");
			tWin1.setStyle("-fx-font: 16 Impact");
			tWin2.setStyle("-fx-font: 16 Impact");
			tWin3.setStyle("-fx-font: 16 Impact");
			tWin4.setStyle("-fx-font: 16 Impact");
			tWin5.setStyle("-fx-font: 16 Impact");
			tWin6.setStyle("-fx-font: 16 Impact");
			tTotalWin1.setFill(Color.MEDIUMPURPLE);
			tTotalWin2.setFill(Color.LIGHTGREEN);
			tCash1.setFill(Color.MEDIUMPURPLE);
			tCash2.setFill(Color.LIGHTGREEN);
			tTotalWin1.setStyle("-fx-font: 24 Impact");
			tTotalWin2.setStyle("-fx-font: 24 Impact");
			tCash1.setStyle("-fx-font: 24 Impact");
			tCash2.setStyle("-fx-font: 24 Impact");
		}
		
		Scene scene = new Scene(resultsRoot, 900,600);
		resultStage.setScene(scene);
		resultStage.show();
		sceneMap.put("results", scene);
	}
	
	public void checkForQueenHigh() {
		int val = ThreeCardLogic.evalHand(game.theDealer.getDealersHand());
		if (val == 0 && game.theDealer.getMaxVal() < 12) {
			lvDisplayActions.getItems().add("Dealer does not have at least Queen high; ante wager is pushed");
			queenHigh = false;
		}
	}
	
	public void newRound() {
		game.playerOne.setHand(game.theDealer.dealHand());
		game.playerTwo.setHand(game.theDealer.dealHand());
		game.theDealer.dealNewHand();
		game.playerOne.anteBet = 5;
		game.playerOne.pairPlusBet = 0;
		game.playerOne.playBet = 0;
		game.playerTwo.anteBet = 5;
		game.playerTwo.pairPlusBet = 0;
		game.playerTwo.playBet = 0;
		resultStage.close();
		p1Fold = false;
		p2Fold = false;
		stage.setScene(sceneMap.get("betting"));
	}
	
	public void menuBarListeners() {
		miStartOver.setOnAction(e->startOver());
		miNewLook.setOnAction(e->newLook());
		miQuit.setOnAction(e ->System.exit(0));
	}
	
	public void startOver() {
		game = new ThreeCardPokerGame();
		game.playerOne.setHand(game.theDealer.dealHand());
		game.playerTwo.setHand(game.theDealer.dealHand());
		lvDisplayActions.getItems().clear();
		resultStage.close();
		newLook = false;
		p1Fold = false;
		p2Fold = false;
		stage.setScene(sceneMap.get("menu"));
	}
	
	public void newLook() {
		newLook = true;
	}
}
