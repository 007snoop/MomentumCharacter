package momentumCharacter.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import momentumCharacter.powers.Momentum;

public class InnerPeace extends CustomRelic {
    public static final String ID = "InnerPeace";
    private static final int STARTING_MOMENTUM = 1;
    private boolean triggeredThisCombot = false;
    private boolean firstTurnPassed = false;

    public InnerPeace() {
        super(
                ID,
                "burningBlood.png",
                RelicTier.STARTER,
                LandingSound.MAGICAL
        );
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        firstTurnPassed = false;
        triggeredThisCombot = false;
    }

    @Override
    public void atTurnStartPostDraw() {
        if (!firstTurnPassed) {
            firstTurnPassed = true;
            return;
        }

        if (!triggeredThisCombot) {
            triggeredThisCombot = true;
            this.flash();

            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(
                            AbstractDungeon.player,
                            AbstractDungeon.player,
                            new Momentum(AbstractDungeon.player, STARTING_MOMENTUM)
                    )
            );
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new InnerPeace();
    }
}
