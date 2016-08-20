package com.watabou.pixeldungeon.actors.hero;

import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Badges;
import com.watabou.pixeldungeon.Badges.Badge;
import com.watabou.pixeldungeon.items.BombTriggerBeacon;
import com.watabou.pixeldungeon.items.KindOfWeapon;
import com.watabou.pixeldungeon.items.TomeOfKnowledge;
import com.watabou.pixeldungeon.items.TomeOfMastery;
import com.watabou.pixeldungeon.items.armor.Armor;
import com.watabou.pixeldungeon.items.armor.ClothArmor;
import com.watabou.pixeldungeon.items.bags.Keyring;
import com.watabou.pixeldungeon.items.food.Food;
import com.watabou.pixeldungeon.items.potions.PotionOfHealing;
import com.watabou.pixeldungeon.items.potions.PotionOfStrength;
import com.watabou.pixeldungeon.items.rings.Ring;
import com.watabou.pixeldungeon.items.rings.RingOfShadows;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfBloodyRitual;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfHome;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfIdentify;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfReadiness;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfSacrifice;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfSkill;
import com.watabou.pixeldungeon.items.wands.WandOfMagicMissile;
import com.watabou.pixeldungeon.items.weapon.melee.Dagger;
import com.watabou.pixeldungeon.items.weapon.melee.DualSwords;
import com.watabou.pixeldungeon.items.weapon.melee.Knuckles;
import com.watabou.pixeldungeon.items.weapon.melee.ShortSword;
import com.watabou.pixeldungeon.items.weapon.missiles.Arrow;
import com.watabou.pixeldungeon.items.weapon.missiles.Boomerang;
import com.watabou.pixeldungeon.items.weapon.missiles.Bow;
import com.watabou.pixeldungeon.items.weapon.missiles.Dart;
import com.watabou.pixeldungeon.ui.QuickSlot;
import com.watabou.pixeldungeon.windows.WndUpdates;
import com.watabou.utils.Bundle;

public enum HeroClass {
    WARRIOR("warrior"),
    MAGE("mage"),
    ROGUE("rogue"),
    HUNTRESS("huntress");
    
    private static final String CLASS = "class";
    public static final String[] HUN_PERKS;
    public static final String[] MAG_PERKS;
    public static final String[] ROG_PERKS;
    public static final String[] WAR_PERKS;
    private String title;

    /* renamed from: com.watabou.pixeldungeon.actors.hero.HeroClass.1 */
    static /* synthetic */ class C00201 {
        static final /* synthetic */ int[] $SwitchMap$com$watabou$pixeldungeon$actors$hero$HeroClass;

        static {
            $SwitchMap$com$watabou$pixeldungeon$actors$hero$HeroClass = new int[HeroClass.values().length];
            try {
                $SwitchMap$com$watabou$pixeldungeon$actors$hero$HeroClass[HeroClass.WARRIOR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$watabou$pixeldungeon$actors$hero$HeroClass[HeroClass.MAGE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$watabou$pixeldungeon$actors$hero$HeroClass[HeroClass.ROGUE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$watabou$pixeldungeon$actors$hero$HeroClass[HeroClass.HUNTRESS.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static {
        WAR_PERKS = new String[]{"Warriors start with 11 points of Strength.", "Warriors start with less mana.", "Warriors start with a unique short sword. This sword can be later \"reforged\" to upgrade another melee weapon.", "Warriors are less proficient with missile weapons.", "Any piece of food restores some health when eaten.", "Potions of Strength are identified from the beginning.", "Passive skills: Training, Melee & Endurance.", "Active skills: Smash & Smite."};
        MAG_PERKS = new String[]{"Mages start with a unique Wand of Magic Missile. This wand can be later \"disenchanted\" to upgrade another wand.", "Mages start with more mana.", "Mages recharge their wands faster.", "When eaten, any piece of food restores 1 charge for all wands in the inventory.", "Mages can use wands as a melee weapon.", "Scrolls of Identify are identified from the beginning.", "Passive skills: Meditation, Wand Master & Spirituality.", "Active skills: Random Teleport & Summon."};
        ROG_PERKS = new String[]{"Rogues start with a Ring of Shadows+1.", "Rogues identify a type of a ring on equipping it.", "Rogues are proficient with light armor, dodging better while wearing one.", "Rogues are proficient in detecting hidden doors and traps.", "Rogues can go without food longer.", "Scrolls of Magic Mapping are identified from the beginning.", "Passive skills: Looting, Stealth & Venom.", "Active skills: Shadow Clone & Disable Trap."};
        HUN_PERKS = new String[]{"Huntresses start with 15 points of Health.", "Huntresses start with a unique upgradeable boomerang.", "Huntresses are proficient with missile weapons and get a damage bonus for excessive strength when using them.", "Huntresses gain more health from dewdrops.", "Huntresses sense neighbouring monsters even if they are hidden behind obstacles.", "Passive skills: Accuracy, Ranged & Fletching.", "Active skills: Flame Arrow & Frost Shot."};
    }

    private HeroClass(String title) {
        this.title = title;
    }

    public void initHero(Hero hero) {
        hero.heroClass = this;
        initCommon(hero);
        switch (C00201.$SwitchMap$com$watabou$pixeldungeon$actors$hero$HeroClass[ordinal()]) {
            case WndUpdates.ID_PRISON /*1*/:
                initWarrior(hero);
                break;
            case WndUpdates.ID_CAVES /*2*/:
                initMage(hero);
                break;
            case WndUpdates.ID_METROPOLIS /*3*/:
                initRogue(hero);
                break;
            case WndUpdates.ID_HALLS /*4*/:
                initHuntress(hero);
                break;
        }
        if (Badges.isUnlocked(masteryBadge())) {
            new TomeOfMastery().collect();
        }
        hero.updateAwareness();
    }

    private static void initCommon(Hero hero) {
        Belongings belongings = hero.belongings;
        Armor clothArmor = new ClothArmor();
        belongings.armor = clothArmor;
        clothArmor.identify();
        new Food().identify().collect();
        new Keyring().collect();
    }

    public Badge masteryBadge() {
        switch (C00201.$SwitchMap$com$watabou$pixeldungeon$actors$hero$HeroClass[ordinal()]) {
            case WndUpdates.ID_PRISON /*1*/:
                return Badge.MASTERY_WARRIOR;
            case WndUpdates.ID_CAVES /*2*/:
                return Badge.MASTERY_MAGE;
            case WndUpdates.ID_METROPOLIS /*3*/:
                return Badge.MASTERY_ROGUE;
            case WndUpdates.ID_HALLS /*4*/:
                return Badge.MASTERY_HUNTRESS;
            default:
                return null;
        }
    }

    private static void initAll(Hero hero) {
        new ScrollOfSkill().setKnown();
        new ScrollOfReadiness().setKnown();
        new ScrollOfHome().setKnown();
        new ScrollOfSacrifice().setKnown();
        new ScrollOfBloodyRitual().setKnown();
        new TomeOfKnowledge().collect();
        new Bow().collect();
        new Arrow(15).collect();
        new BombTriggerBeacon().collect();
        switch (hero.difficulty) {
            case WndUpdates.ID_PRISON /*1*/:
                new PotionOfHealing().identify().collect();
                new PotionOfHealing().identify().collect();
                new Food().identify().collect();
                new Food().identify().collect();
            case WndUpdates.ID_METROPOLIS /*3*/:
                hero.HT -= 4;
                hero.HP -= 4;
            case WndUpdates.ID_HALLS /*4*/:
                hero.HT -= 8;
                hero.HP -= 8;
            default:
        }
    }

    private static void initWarrior(Hero hero) {
        initAll(hero);
        hero.STR++;
        hero.MMP = 15;
        hero.MP = 15;
        Belongings belongings = hero.belongings;
        KindOfWeapon shortSword = new ShortSword();
        belongings.weapon = shortSword;
        shortSword.identify();
        new Dart(8).identify().collect();
        QuickSlot.primaryValue = Dart.class;
        new PotionOfStrength().setKnown();
    }

    private static void initMage(Hero hero) {
        initAll(hero);
        hero.MMP = 25;
        hero.MP = 25;
        Belongings belongings = hero.belongings;
        KindOfWeapon knuckles = new Knuckles();
        belongings.weapon = knuckles;
        knuckles.identify();
        WandOfMagicMissile wand = new WandOfMagicMissile();
        wand.identify().collect();
        QuickSlot.primaryValue = wand;
        new ScrollOfIdentify().setKnown();
    }

    private static void initRogue(Hero hero) {
        initAll(hero);
        hero.MMP = 20;
        hero.MP = 20;
        Belongings belongings = hero.belongings;
        KindOfWeapon dualSwords = new DualSwords();
        belongings.weapon = dualSwords;
        dualSwords.identify();
        belongings = hero.belongings;
        Ring ringOfShadows = new RingOfShadows();
        belongings.ring1 = ringOfShadows;
        ringOfShadows.upgrade().identify();
        new Dart(8).identify().collect();
        hero.belongings.ring1.activate(hero);
        QuickSlot.primaryValue = Dart.class;
        new ScrollOfMagicMapping().setKnown();
    }

    private static void initHuntress(Hero hero) {
        initAll(hero);
        int i = hero.HT - 5;
        hero.HT = i;
        hero.HP = i;
        hero.MMP = 20;
        hero.MP = 20;
        Belongings belongings = hero.belongings;
        KindOfWeapon dagger = new Dagger();
        belongings.weapon = dagger;
        dagger.identify();
        Boomerang boomerang = new Boomerang();
        boomerang.identify().collect();
        QuickSlot.primaryValue = boomerang;
    }

    public String title() {
        return this.title;
    }

    public String spritesheet() {
        switch (C00201.$SwitchMap$com$watabou$pixeldungeon$actors$hero$HeroClass[ordinal()]) {
            case WndUpdates.ID_PRISON /*1*/:
                return Assets.WARRIOR;
            case WndUpdates.ID_CAVES /*2*/:
                return Assets.MAGE;
            case WndUpdates.ID_METROPOLIS /*3*/:
                return Assets.ROGUE;
            case WndUpdates.ID_HALLS /*4*/:
                return Assets.HUNTRESS;
            default:
                return null;
        }
    }

    public String[] perks() {
        switch (C00201.$SwitchMap$com$watabou$pixeldungeon$actors$hero$HeroClass[ordinal()]) {
            case WndUpdates.ID_PRISON /*1*/:
                return WAR_PERKS;
            case WndUpdates.ID_CAVES /*2*/:
                return MAG_PERKS;
            case WndUpdates.ID_METROPOLIS /*3*/:
                return ROG_PERKS;
            case WndUpdates.ID_HALLS /*4*/:
                return HUN_PERKS;
            default:
                return null;
        }
    }

    public void storeInBundle(Bundle bundle) {
        bundle.put(CLASS, toString());
    }

    public static HeroClass restoreInBundle(Bundle bundle) {
        String value = bundle.getString(CLASS);
        return value.length() > 0 ? valueOf(value) : ROGUE;
    }
}
