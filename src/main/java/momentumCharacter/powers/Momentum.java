package momentumCharacter.powers;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import momentumCharacter.MomentumMod;
import momentumCharacter.util.GeneralUtils;
import momentumCharacter.util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static momentumCharacter.MomentumMod.makeID;

public class Momentum extends AbstractPower {
    private static final String POWER_ID = makeID("Momentum");
    private static PowerStrings getPowerStrings(String ID)
    {
        return CardCrawlGame.languagePack.getPowerStrings(ID);
    }
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    protected AbstractCreature source;
    private static final int MAX_MOMENTUM = 10;
    protected String[] DESCRIPTIONS;

    //Will not display if at 0. You can override renderAmount to render it however you want.
    //amount2 will not stack like the normal amount variable when stacking a power.
    public int amount2 = 0;
    protected Color redColor2 = Color.RED.cpy();
    protected Color greenColor2 = Color.GREEN.cpy();

    public Momentum(AbstractCreature o, int amount) {
        this.ID = POWER_ID;
        this.owner = o;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(this.ID);
        this.name = strings.NAME;
        this.DESCRIPTIONS = strings.DESCRIPTIONS;

        loadRegion("strength");
        updateDescription();
    }

    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);

        if (this.amount2 != 0) {
            if (!this.isTurnBased) {
                float alpha = c.a;
                c = this.amount2 > 0 ? this.greenColor2 : this.redColor2;
                c.a = alpha;
            }

            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount2), x, y + 15.0F * Settings.scale, this.fontScale, c);
        }
    }



    // after card resolves add "Momentum" counter to player
    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (!card.hasTag(MomentumMod.MOMENTUM_TRIGGER)) {
            return;
        }

        if (this.amount < MAX_MOMENTUM) {
            this.amount++;
            if (this.amount % 2 == 0) { this.flash(); }
            updateDescription();
        }
    }

    // spend momentum for counter increase
    public int spendMomentum(int amountToSpend) {
        int spend = Math.min(amountToSpend, this.amount);
        this.amount -= spend;
        updateDescription();
        this.flash();
        return spend;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
