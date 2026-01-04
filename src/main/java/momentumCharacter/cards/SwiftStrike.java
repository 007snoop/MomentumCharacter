package momentumCharacter.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import momentumCharacter.MomentumMod;
import momentumCharacter.character.TheMomentum;
import momentumCharacter.util.CardStats;

public class SwiftStrike extends BaseCard{
    public static final String ID = makeID("SwiftStrike");
    private static final CardStats info = new CardStats(
            TheMomentum.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.BASIC,
            CardTarget.ENEMY,
            1
    );
    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;
    private static final int MOMENTUM_COUNTER = 1;

    public SwiftStrike() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        this.magicNumber = this.baseMagicNumber = MOMENTUM_COUNTER;
        tags.add(MomentumMod.MOMENTUM_TRIGGER);
        tags.add(CardTags.STARTER_STRIKE);
        tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        // check if player has buff or not
        if (player.hasPower(MomentumMod.modID)) {
            // only then add +1
            AbstractPower momentum = player.getPower(MomentumMod.modID);
            momentum.amount += 1;
            momentum.updateDescription();
            momentum.flash();
        }

        addToBot(new DamageAction(
                monster,
                new DamageInfo(player, damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.SLASH_VERTICAL)
        );
    }

    @Override
    public AbstractCard makeCopy() {
        return new SwiftStrike();
    }
}
