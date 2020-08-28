package com.example.two_dicepig;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int[] diceNames={R.mipmap.one_dice_foreground,R.mipmap.two_dice_foreground,R.mipmap.three_dice_foreground,R.mipmap.four_dice_foreground,R.mipmap.five_dice_foreground,R.mipmap.six_dice_foreground};

    int totalTurnNum=0;
    int playerOneTotalScore=0;
    int playerTwoTotalScore=0;
    boolean turn=true;
    boolean holdLock=false;
    boolean zeroOut=false;
    boolean aWinner=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView diceOne=findViewById(R.id.diceOne_id);
        ImageView diceTwo=findViewById(R.id.diceTwo_id);
        diceOne.setImageResource(R.mipmap.zero_dice_foreground);
        diceTwo.setImageResource(R.mipmap.zero_dice_foreground);
    }

    public void rollDiceOnClick(View view) {
        if(aWinner!=true) {
            int ranNumOne=getRandomNumber();
            int ranNumTwo=getRandomNumber();
            totalTurnNum+=(ranNumOne+1)+(ranNumTwo+1);
            ImageView diceOne=findViewById(R.id.diceOne_id);
            ImageView diceTwo=findViewById(R.id.diceTwo_id);
            if(ranNumOne+1==1&&ranNumTwo+1==1) {
                totalTurnNum=0;
                zeroOut=true;
                toggleTurn();
            } else if(ranNumOne+1==1||ranNumTwo+1==1) {
              totalTurnNum=0;
              toggleTurn();
            } else if(ranNumOne==ranNumTwo) {
                holdLock=true;
            } else if(ranNumOne!=ranNumTwo) {
                holdLock=false;
            }
            diceOne.setImageResource(diceNames[ranNumOne]);
            diceTwo.setImageResource(diceNames[ranNumTwo]);


            TextView totalTurn=findViewById(R.id.totalTurn_id);
            totalTurn.setText("Total Turn: "+totalTurnNum);
        }

    }

    public void holdOnClick(View view) {
        if(holdLock==false&&aWinner!=true) {
            toggleTurn();
        }
    }

    public int getRandomNumber() {
        return new Random().nextInt(5);
    }

    public void toggleTurn() {//true=playerOne & false=playerTwo
        TextView pOneText=findViewById(R.id.playerOneTotal_id);
        TextView pTwoText=findViewById(R.id.playerTwoTotal_id);
        TextView currentPlayerText=findViewById(R.id.currentPlayer_id);
        TextView totalTurnTextReset=findViewById(R.id.totalTurn_id);
        ImageView winner=findViewById(R.id.winner_id);
        Button btn=findViewById(R.id.restartButton_id);
        if(turn==true) {
            turn=false;
            if(zeroOut==true) {
                playerOneTotalScore=0;
                zeroOut=false;
            }
            currentPlayerText.setText("Current Player: P2");
            playerOneTotalScore+=totalTurnNum;
            pOneText.setText("Player 1 Total: "+playerOneTotalScore);

        } else if(turn==false) {
            turn=true;
            if(zeroOut==true) {
                playerTwoTotalScore=0;
                zeroOut=false;
            }
            playerTwoTotalScore+=totalTurnNum;
            currentPlayerText.setText("Current Player: P1");
            pTwoText.setText("Player 2 Total: "+playerTwoTotalScore);
        }
        if(playerOneTotalScore>=50) {
            winner.setImageResource(R.drawable.p1winner);
            winner.setVisibility(View.VISIBLE);
            aWinner=true;
            btn.setVisibility(View.VISIBLE);
            btn.setClickable(true);
        } else if(playerTwoTotalScore>=50) {
            winner.setImageResource(R.drawable.p2winner);
            winner.setVisibility(View.VISIBLE);
            aWinner=true;
            btn.setVisibility(View.VISIBLE);
            btn.setClickable(true);

        }

        totalTurnNum=0;
        totalTurnTextReset.setText("Total Turn: 0");

    }

    public void restartGame(View view) {
        TextView pOneText=findViewById(R.id.playerOneTotal_id);
        TextView pTwoText=findViewById(R.id.playerTwoTotal_id);
        TextView currentPlayerText=findViewById(R.id.currentPlayer_id);
        TextView totalTurnTextReset=findViewById(R.id.totalTurn_id);
        ImageView winner=findViewById(R.id.winner_id);
        winner.setVisibility(View.INVISIBLE);
        aWinner=false;
        holdLock=false;
        totalTurnNum=0;
        playerTwoTotalScore=0;
        playerOneTotalScore=0;
        zeroOut=false;
        turn=true;
        pOneText.setText("Player 1 Total: 0");
        pTwoText.setText("Player 2 Total: 0");
        currentPlayerText.setText("Current Player: P1");
        totalTurnTextReset.setText("Total Turn: 0");
        ImageView diceOne=findViewById(R.id.diceOne_id);
        ImageView diceTwo=findViewById(R.id.diceTwo_id);
        diceOne.setImageResource(R.mipmap.zero_dice_foreground);
        diceTwo.setImageResource(R.mipmap.zero_dice_foreground);
        view.setVisibility(View.INVISIBLE);
        view.setClickable(false);


    }
}

