package momentumCharacter.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import momentumCharacter.character.TheMomentum;
import momentumCharacter.powers.Momentum;
import momentumCharacter.util.CardStats;

public class MomentOfOpportunity extends BaseCard {
    public static final String ID = makeID("MomentOfOpportunity");
    public static final CardStats info = new CardStats(
            TheMomentum.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );
    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 5;

    public MomentOfOpportunity() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);


        /*MIGHT REMOVE DUE TO TAG TRIGGERING POWER APPLY*/
        /*tags.add(MomentumMod.MOMENTUM_TRIGGER);*/


        tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        int totalDamage = this.damage;

        if (player.hasPower(Momentum.POWER_ID)) {
            Momentum m = (Momentum) player.getPower(Momentum.POWER_ID);
            int spent = m.spendAllMomentum();

            if (spent > 0) {
                totalDamage += spent * 2;
            }
        }

        addToBot(new DamageAction(
                monster,
                new DamageInfo(player, totalDamage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.SLASH_HEAVY
        ));
    }

    @Override
    public AbstractCard makeCopy() {
        return new MomentOfOpportunity();
    }
}
