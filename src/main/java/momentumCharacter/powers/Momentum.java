package momentumCharacter.powers;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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

import static momentumCharacter.BasicMod.makeID;

public abstract class Momentum extends AbstractPower {
    private static final String POWER_ID = makeID("Momentum");
    private static PowerStrings getPowerStrings(String ID)
    {
        return CardCrawlGame.languagePack.getPowerStrings(ID);
    }
    private final String NAME = getPowerStrings(ID).NAME;
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    protected AbstractCreature source;
    private static final int MAX_MOMENTUM = 10;
    protected String[] DESCRIPTIONS;

    //Will not display if at 0. You can override renderAmount to render it however you want.
    //amount2 will not stack like the normal amount variable when stacking a power.
    public int amount2 = 0;
    protected Color redColor2 = Color.RED.cpy();
    protected Color greenColor2 = Color.GREEN.cpy();

    public Momentum(String id, PowerType powerType, boolean isTurnBased, AbstractCreature owner, int amount) {
        this(id, powerType, isTurnBased, owner, null, amount);
    }
    public Momentum(String id, PowerType powerType, boolean isTurnBased, AbstractCreature owner, AbstractCreature source, int amount) {
        this(id, powerType, isTurnBased, owner, source, amount, true);
    }
    public Momentum(String id, PowerType powerType, boolean isTurnBased, AbstractCreature owner, AbstractCreature source, int amount, boolean initDescription) {
        this(id, powerType, isTurnBased, owner, source, amount, initDescription, true);
    }

    // main constructor
    public Momentum(String id, PowerType powerType, boolean isTurnBased, AbstractCreature owner, AbstractCreature source, int amount, boolean initDescription, boolean loadImage) {
        this.ID = id;
        this.isTurnBased = isTurnBased;

        PowerStrings strings = getPowerStrings(this.ID);
        this.name = strings.NAME;
        this.DESCRIPTIONS = strings.DESCRIPTIONS;

        this.owner = owner;
        this.source = source;
        this.amount = amount;
        this.type = powerType;

        if (loadImage)
        {
            String unPrefixed = GeneralUtils.removePrefix(id);
            Texture normalTexture = TextureLoader.getPowerTexture(unPrefixed);
            Texture hiDefImage = TextureLoader.getHiDefPowerTexture(unPrefixed);
            if (hiDefImage != null)
            {
                region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
                if (normalTexture != null)
                    region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
            }
            else
            {
                this.img = normalTexture;
                region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
            }
        }

        if (initDescription)
            this.updateDescription();
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
        if (this.amount < MAX_MOMENTUM) {
            this.flash();
            this.amount++;
            updateDescription();
        }
    }
}
