package momentumCharacter.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import momentumCharacter.character.TheMomentum;
import momentumCharacter.powers.Momentum;
import momentumCharacter.util.CardStats;

public class BuildMomentum extends BaseCard {
    public static final String ID = makeID("BuildMomentum");

    public static final CardStats info = new CardStats(
            TheMomentum.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            1
    );

    public BuildMomentum() {
        super(ID, info);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        setMagic(magicNumber);
        tags.add(CardTags.EMPTY);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new ApplyPowerAction(
                player,
                player,
                new Momentum(player, this.magicNumber)
        ));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
