package com.sprinthive.pokerhands.handrank;

import com.sprinthive.pokerhands.Card;
import com.sprinthive.pokerhands.CardRank;
import com.sprinthive.pokerhands.Hand;
import com.sprinthive.pokerhands.Suit;

import java.util.Collections;
import java.util.List;

public class GoodPokerHandRanker implements HandRanker{
    @Override
    public HandRank findBestHandRank(List<Card> cards) {

        if (cards.size() != 5) {
            return new NotRankableHandRanker(cards);
        }
        Collections.sort(cards);
        Collections.reverse(cards);

        CardRank cardRank = cards.get(0).getRank();
        Suit suit = cards.get(0).getSuit();

        //check for royal flush
        HandStrength handStrength = HandStrength.ROYAL_FLUSH;
        for (int i = 0 ; i < 4; i++){
            if (cards.get(i).getRank().compareTo(cards.get(i+1).getRank()) != 1) {
                handStrength = HandStrength.FOUR_OF_AS_KIND;
                break;
            }
            if (cards.get(i).getSuit().compareTo(cards.get(i+1).getSuit()) != 0){
                handStrength = HandStrength.FOUR_OF_AS_KIND;
                break;
            }
        }

        if (handStrength.equals(HandStrength.ROYAL_FLUSH)) {

            if (cards.get(0).getRank().equals(CardRank.ACE))
                return new RoyalFlushHandRank(suit);
            else
                return new StraightFlushHandRank(cardRank);
        }

        //now checking for four-of-a-kind
        int foursCount = 0;
        for (int i = 0 ; i < 4; i++){
            if (cards.get(i).getRank().equals(cards.get(i+1).getRank())) {
                foursCount++;
            }
        }

        if (foursCount >= 3){

            cardRank = cards.get(2).getRank();
            return new FourOfAKindHandRank(cardRank);
        }


        if (handStrength == null)
            return new StraightFlushHandRank(cardRank);

        return null;
    }
}
