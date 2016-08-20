package com.watabou.pixeldungeon.actors.hero;

import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Badges;
import com.watabou.pixeldungeon.Bones;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.GamesInProgress.Info;
import com.watabou.pixeldungeon.ResultDescriptions;
import com.watabou.pixeldungeon.actors.Actor;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.buffs.Barkskin;
import com.watabou.pixeldungeon.actors.buffs.Bleeding;
import com.watabou.pixeldungeon.actors.buffs.Blindness;
import com.watabou.pixeldungeon.actors.buffs.Buff;
import com.watabou.pixeldungeon.actors.buffs.Burning;
import com.watabou.pixeldungeon.actors.buffs.Champ;
import com.watabou.pixeldungeon.actors.buffs.Charm;
import com.watabou.pixeldungeon.actors.buffs.Combo;
import com.watabou.pixeldungeon.actors.buffs.Cripple;
import com.watabou.pixeldungeon.actors.buffs.Fletching;
import com.watabou.pixeldungeon.actors.buffs.Fury;
import com.watabou.pixeldungeon.actors.buffs.GasesImmunity;
import com.watabou.pixeldungeon.actors.buffs.Hunger;
import com.watabou.pixeldungeon.actors.buffs.Invisibility;
import com.watabou.pixeldungeon.actors.buffs.Light;
import com.watabou.pixeldungeon.actors.buffs.ManaRegeneration;
import com.watabou.pixeldungeon.actors.buffs.Ooze;
import com.watabou.pixeldungeon.actors.buffs.Paralysis;
import com.watabou.pixeldungeon.actors.buffs.Poison;
import com.watabou.pixeldungeon.actors.buffs.Regeneration;
import com.watabou.pixeldungeon.actors.buffs.Roots;
import com.watabou.pixeldungeon.actors.buffs.SkillCD;
import com.watabou.pixeldungeon.actors.buffs.Sleep;
import com.watabou.pixeldungeon.actors.buffs.SnipersMark;
import com.watabou.pixeldungeon.actors.buffs.Vertigo;
import com.watabou.pixeldungeon.actors.buffs.WandMastery;
import com.watabou.pixeldungeon.actors.buffs.Weakness;
import com.watabou.pixeldungeon.actors.hero.HeroAction.Ascend;
import com.watabou.pixeldungeon.actors.hero.HeroAction.Attack;
import com.watabou.pixeldungeon.actors.hero.HeroAction.Buy;
import com.watabou.pixeldungeon.actors.hero.HeroAction.Cook;
import com.watabou.pixeldungeon.actors.hero.HeroAction.Descend;
import com.watabou.pixeldungeon.actors.hero.HeroAction.Interact;
import com.watabou.pixeldungeon.actors.hero.HeroAction.Move;
import com.watabou.pixeldungeon.actors.hero.HeroAction.OpenChest;
import com.watabou.pixeldungeon.actors.hero.HeroAction.PickUp;
import com.watabou.pixeldungeon.actors.hero.HeroAction.Storage;
import com.watabou.pixeldungeon.actors.hero.HeroAction.Unlock;
import com.watabou.pixeldungeon.actors.mobs.Mob;
import com.watabou.pixeldungeon.actors.mobs.npcs.NPC;
import com.watabou.pixeldungeon.effects.CheckedCell;
import com.watabou.pixeldungeon.effects.Flare;
import com.watabou.pixeldungeon.effects.Speck;
import com.watabou.pixeldungeon.items.Amulet;
import com.watabou.pixeldungeon.items.Ankh;
import com.watabou.pixeldungeon.items.DewVial;
import com.watabou.pixeldungeon.items.Dewdrop;
import com.watabou.pixeldungeon.items.Heap;
import com.watabou.pixeldungeon.items.Heap.Type;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.items.KindOfWeapon;
import com.watabou.pixeldungeon.items.TomeOfMastery;
import com.watabou.pixeldungeon.items.armor.Armor.Glyph;
import com.watabou.pixeldungeon.items.keys.GoldenKey;
import com.watabou.pixeldungeon.items.keys.IronKey;
import com.watabou.pixeldungeon.items.keys.SkeletonKey;
import com.watabou.pixeldungeon.items.potions.Potion;
import com.watabou.pixeldungeon.items.potions.PotionOfMight;
import com.watabou.pixeldungeon.items.potions.PotionOfStrength;
import com.watabou.pixeldungeon.items.rings.RingOfAccuracy.Accuracy;
import com.watabou.pixeldungeon.items.rings.RingOfDetection.Detection;
import com.watabou.pixeldungeon.items.rings.RingOfElements.Resistance;
import com.watabou.pixeldungeon.items.rings.RingOfEvasion.Evasion;
import com.watabou.pixeldungeon.items.rings.RingOfHaste.Haste;
import com.watabou.pixeldungeon.items.rings.RingOfShadows.Shadows;
import com.watabou.pixeldungeon.items.rings.RingOfThorns.Thorns;
import com.watabou.pixeldungeon.items.scrolls.Scroll;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfEnchantment;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfRecharging;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.watabou.pixeldungeon.items.wands.Wand;
import com.watabou.pixeldungeon.items.weapon.melee.MeleeWeapon;
import com.watabou.pixeldungeon.items.weapon.missiles.MissileWeapon;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.levels.Terrain;
import com.watabou.pixeldungeon.levels.features.AlchemyPot;
import com.watabou.pixeldungeon.levels.features.Chasm;
import com.watabou.pixeldungeon.levels.features.Sign;
import com.watabou.pixeldungeon.plants.Earthroot.Armor;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.scenes.InterlevelScene;
import com.watabou.pixeldungeon.scenes.InterlevelScene.Mode;
import com.watabou.pixeldungeon.scenes.SurfaceScene;
import com.watabou.pixeldungeon.sprites.CharSprite;
import com.watabou.pixeldungeon.sprites.CharSprite.State;
import com.watabou.pixeldungeon.sprites.HeroSprite;
import com.watabou.pixeldungeon.ui.AttackIndicator;
import com.watabou.pixeldungeon.ui.BuffIndicator;
import com.watabou.pixeldungeon.utils.GLog;
import com.watabou.pixeldungeon.windows.WndMessage;
import com.watabou.pixeldungeon.windows.WndResurrect;
import com.watabou.pixeldungeon.windows.WndStorage;
import com.watabou.pixeldungeon.windows.WndTradeItem;
import com.watabou.pixeldungeon.windows.WndUpdates;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

public class Hero extends Char {
    private static final String ATTACK = "attackSkill";
    private static final String DEFENSE = "defenseSkill";
    private static final String Difficulty = "editdifficulty";
    private static final String EXPERIENCE = "exp";
    private static final String LEVEL = "lvl";
    private static final String SKILL = "skill";
    private static final String SKILLACCURANCY = "skillAccurancy";
    private static final String SKILLDEFENCE = "skillDefence";
    private static final String SKILLEND = "skillend";
    private static final String SKILLFIRSTCOOL = "skillFirstCooldown";
    private static final String SKILLHERB = "skillherb";
    private static final String SKILLHUNT = "skillhunt";
    private static final String SKILLLOOT = "skillloot";
    private static final String SKILLMEDITATION = "skillmed";
    private static final String SKILLMELEE = "skillmelee";
    private static final String SKILLRANGED = "skillRanged";
    private static final String SKILLSECONDCOOL = "skillSecondCooldown";
    private static final String SKILLSTEALTH = "skillstealth";
    private static final String SKILLSTR = "skillstr";
    private static final String SKILLVENOM = "skillvenom";
    private static final String SKILLWAND = "skillwand";
    public static final int STARTING_STR = 10;
    private static final String STRENGTH = "STR";
    private static final float TIME_TO_REST = 1.0f;
    private static final float TIME_TO_SEARCH = 2.0f;
    private static final String TXT_LEAVE = "One does not simply leave Pixel Dungeon.";
    private static final String TXT_LEVEL_UP = "level up!";
    private static final String TXT_LOCKED_CHEST = "This chest is locked and you don't have matching key";
    private static final String TXT_LOCKED_DOOR = "You don't have a matching key";
    private static final String TXT_NEW_LEVEL = "Welcome to level %d! Now you are healthier and more focused. It's easier for you to hit enemies and dodge their attacks.\nYou gained 2 skill points!";
    private static final String TXT_NOTICED_SMTH = "You noticed something";
    private static final String TXT_SEARCH = "search";
    private static final String TXT_SOMETHING_ELSE = "There is something else here";
    private static final String TXT_WAIT = "...";
    public static final String TXT_YOU_NOW_HAVE = "You now have %s";
    public int STR;
    private int attackSkill;
    public float awareness;
    public Belongings belongings;
    public HeroAction curAction;
    private int defenseSkill;
    public int difficulty;
    private Char enemy;
    public int exp;
    public HeroClass heroClass;
    public Glyph killerGlyph;
    public HeroAction lastAction;
    public int lvl;
    public MissileWeapon rangedWeapon;
    public boolean ready;
    public boolean restoreHealth;
    public int skillAccurancy;
    public int skillDefence;
    public int skillEnd;
    public boolean skillFirstActive;
    public int skillFirstCooldown;
    public int skillHunter;
    public int skillLoot;
    public int skillMana;
    public int skillMeditation;
    public int skillMelee;
    public int skillPoints;
    public int skillRanged;
    public boolean skillSecondActive;
    public int skillSecondCooldown;
    public int skillStealth;
    public int skillStr;
    public int skillVenom;
    public int skillWand;
    public Storage storage;
    public HeroSubClass subClass;
    private Item theKey;
    private ArrayList<Mob> visibleEnemies;
    public boolean weakened;

    public interface Doom {
        void onDeath();
    }

    /* renamed from: com.watabou.pixeldungeon.actors.hero.Hero.1 */
    static /* synthetic */ class C00191 {
        static final /* synthetic */ int[] $SwitchMap$com$watabou$pixeldungeon$actors$hero$HeroSubClass;
        static final /* synthetic */ int[] $SwitchMap$com$watabou$pixeldungeon$items$Heap$Type;

        static {
            $SwitchMap$com$watabou$pixeldungeon$actors$hero$HeroSubClass = new int[HeroSubClass.values().length];
            try {
                $SwitchMap$com$watabou$pixeldungeon$actors$hero$HeroSubClass[HeroSubClass.GLADIATOR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$watabou$pixeldungeon$actors$hero$HeroSubClass[HeroSubClass.BATTLEMAGE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$watabou$pixeldungeon$actors$hero$HeroSubClass[HeroSubClass.SNIPER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            $SwitchMap$com$watabou$pixeldungeon$items$Heap$Type = new int[Type.values().length];
            try {
                $SwitchMap$com$watabou$pixeldungeon$items$Heap$Type[Type.TOMB.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$watabou$pixeldungeon$items$Heap$Type[Type.SKELETON.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$watabou$pixeldungeon$items$Heap$Type[Type.HEAP.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$watabou$pixeldungeon$items$Heap$Type[Type.FOR_SALE.ordinal()] = 4;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public Hero() {
        this.heroClass = HeroClass.ROGUE;
        this.subClass = HeroSubClass.NONE;
        this.attackSkill = STARTING_STR;
        this.defenseSkill = 5;
        this.ready = false;
        this.curAction = null;
        this.lastAction = null;
        this.killerGlyph = null;
        this.restoreHealth = false;
        this.rangedWeapon = null;
        this.weakened = false;
        this.lvl = 1;
        this.exp = 0;
        this.skillPoints = 3;
        this.skillStr = 0;
        this.skillMelee = 0;
        this.skillEnd = 0;
        this.skillLoot = 0;
        this.skillStealth = 0;
        this.skillVenom = 0;
        this.skillHunter = 0;
        this.skillMana = 0;
        this.skillWand = 0;
        this.skillMeditation = 0;
        this.skillAccurancy = 0;
        this.skillRanged = 0;
        this.skillDefence = 0;
        this.skillFirstCooldown = 0;
        this.skillSecondCooldown = 0;
        this.skillFirstActive = false;
        this.skillSecondActive = false;
        this.difficulty = 0;
        this.name = "you";
        this.HT = 20;
        this.HP = 20;
        this.STR = STARTING_STR;
        this.awareness = 0.1f;
        this.belongings = new Belongings(this);
        this.storage = new Storage(this);
        this.visibleEnemies = new ArrayList();
    }

    public int STR() {
        return this.weakened ? this.STR - 2 : this.STR;
    }

    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        this.heroClass.storeInBundle(bundle);
        this.subClass.storeInBundle(bundle);
        bundle.put(ATTACK, this.attackSkill);
        bundle.put(DEFENSE, this.defenseSkill);
        bundle.put(STRENGTH, this.STR);
        bundle.put(LEVEL, this.lvl);
        bundle.put(EXPERIENCE, this.exp);
        bundle.put(SKILL, this.skillPoints);
        bundle.put(SKILLEND, this.skillEnd);
        bundle.put(SKILLSTR, this.skillStr);
        bundle.put(SKILLMELEE, this.skillMelee);
        bundle.put(SKILLHUNT, this.skillHunter);
        bundle.put(SKILLLOOT, this.skillLoot);
        bundle.put(SKILLSTEALTH, this.skillStealth);
        bundle.put(SKILLVENOM, this.skillVenom);
        bundle.put(SKILLHERB, this.skillMana);
        bundle.put(SKILLWAND, this.skillWand);
        bundle.put(SKILLMEDITATION, this.skillMeditation);
        bundle.put(SKILLACCURANCY, this.skillAccurancy);
        bundle.put(SKILLRANGED, this.skillRanged);
        bundle.put(SKILLDEFENCE, this.skillDefence);
        bundle.put(SKILLFIRSTCOOL, this.skillFirstCooldown);
        bundle.put(SKILLSECONDCOOL, this.skillSecondCooldown);
        bundle.put(Difficulty, this.difficulty);
        this.belongings.storeInBundle(bundle);
        this.storage.storeInBundle(bundle);
    }

    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        this.heroClass = HeroClass.restoreInBundle(bundle);
        this.subClass = HeroSubClass.restoreInBundle(bundle);
        this.attackSkill = bundle.getInt(ATTACK);
        this.defenseSkill = bundle.getInt(DEFENSE);
        this.STR = bundle.getInt(STRENGTH);
        updateAwareness();
        this.lvl = bundle.getInt(LEVEL);
        this.exp = bundle.getInt(EXPERIENCE);
        this.skillPoints = bundle.getInt(SKILL);
        this.skillEnd = bundle.getInt(SKILLEND);
        this.skillStr = bundle.getInt(SKILLSTR);
        this.skillMelee = bundle.getInt(SKILLMELEE);
        this.skillHunter = bundle.getInt(SKILLHUNT);
        this.skillLoot = bundle.getInt(SKILLLOOT);
        this.skillStealth = bundle.getInt(SKILLSTEALTH);
        this.skillVenom = bundle.getInt(SKILLVENOM);
        this.skillMana = bundle.getInt(SKILLHERB);
        this.skillWand = bundle.getInt(SKILLWAND);
        this.skillMeditation = bundle.getInt(SKILLMEDITATION);
        this.skillAccurancy = bundle.getInt(SKILLACCURANCY);
        this.skillRanged = bundle.getInt(SKILLRANGED);
        this.skillDefence = bundle.getInt(SKILLDEFENCE);
        this.skillFirstCooldown = bundle.getInt(SKILLFIRSTCOOL);
        this.skillSecondCooldown = bundle.getInt(SKILLSECONDCOOL);
        this.difficulty = bundle.getInt(Difficulty);
        Dungeon.difficulty = this.difficulty;
        this.belongings.restoreFromBundle(bundle);
        this.storage.restoreFromBundle(bundle);
    }

    public static void preview(Info info, Bundle bundle) {
        info.level = bundle.getInt(LEVEL);
    }

    public String className() {
        return (this.subClass == null || this.subClass == HeroSubClass.NONE) ? this.heroClass.title() : this.subClass.title();
    }

    public void live() {
        GLog.m1i(this.heroClass.toString(), new Object[0]);
        Buff.affect(this, Regeneration.class);
        Buff.affect(this, ManaRegeneration.class);
        Buff.affect(this, Hunger.class);
        Buff.affect(this, Fletching.class);
        Buff.affect(this, WandMastery.class);
        Buff.affect(this, SkillCD.class);
    }

    public int tier() {
        return this.belongings.armor == null ? 0 : this.belongings.armor.tier;
    }

    public boolean shoot(Char enemy, MissileWeapon wep) {
        this.rangedWeapon = wep;
        boolean result = attack(enemy);
        this.rangedWeapon = null;
        return result;
    }

    public int attackSkill(Char target) {
        int bonus = 0;
        Iterator it = buffs(Accuracy.class).iterator();
        while (it.hasNext()) {
            bonus += ((Accuracy) ((Buff) it.next())).level;
        }
        float accuracy = bonus == 0 ? TIME_TO_REST : (float) Math.pow(1.4d, (double) bonus);
        if (this.rangedWeapon != null && Level.distance(this.pos, target.pos) == 1) {
            accuracy *= 0.5f;
        }
        KindOfWeapon wep = this.rangedWeapon != null ? this.rangedWeapon : this.belongings.weapon;
        if (wep != null) {
            return (int) ((((float) (this.attackSkill + (this.skillAccurancy / 3))) * accuracy) * wep.acuracyFactor(this));
        }
        return (int) (((float) (this.attackSkill + (this.skillAccurancy / 3))) * accuracy);
    }

    public int defenseSkill(Char enemy) {
        int bonus = 0;
        Iterator it = buffs(Evasion.class).iterator();
        while (it.hasNext()) {
            bonus += ((Evasion) ((Buff) it.next())).level;
        }
        float evasion = bonus == 0 ? TIME_TO_REST : (float) Math.pow(1.2d, (double) bonus);
        if (this.paralysed) {
            evasion /= TIME_TO_SEARCH;
        }
        int aEnc = this.belongings.armor != null ? this.belongings.armor.STR - STR() : 0;
        if (aEnc > 0) {
            return (int) (((double) (((float) (this.defenseSkill + (this.skillDefence / 3))) * evasion)) / Math.pow(1.5d, (double) aEnc));
        }
        if (this.heroClass != HeroClass.ROGUE) {
            return (int) (((float) (this.defenseSkill + (this.skillDefence / 3))) * evasion);
        }
        if (!(this.curAction == null || this.subClass != HeroSubClass.FREERUNNER || isStarving())) {
            evasion *= TIME_TO_SEARCH;
        }
        return (int) (((float) ((this.defenseSkill + (this.skillDefence / 3)) - aEnc)) * evasion);
    }

    public int dr() {
        int dr = 0;
        if (this.belongings.armor != null) {
            dr = Math.max(this.belongings.armor.DR, 0);
        }
        Barkskin barkskin = (Barkskin) buff(Barkskin.class);
        if (barkskin != null) {
            return dr + barkskin.level();
        }
        return dr;
    }

    public int damageRoll() {
        int dmg = 1;
        KindOfWeapon wep = this.rangedWeapon != null ? this.rangedWeapon : this.belongings.weapon;
        if (wep != null) {
            dmg = wep.damageRoll(this);
        } else if (STR() > STARTING_STR) {
            dmg = Random.IntRange(1, STR() - 9);
        }
        return buff(Fury.class) != null ? (int) (((float) dmg) * Sleep.SWS) : dmg;
    }

    public float speed() {
        int aEnc;
        boolean z = false;
        if (this.belongings.armor != null) {
            aEnc = this.belongings.armor.STR - STR();
        } else {
            aEnc = 0;
        }
        if (aEnc > 0) {
            return (float) (((double) super.speed()) * Math.pow(1.3d, (double) (-aEnc)));
        }
        float speed = super.speed();
        HeroSprite heroSprite = (HeroSprite) this.sprite;
        if (this.subClass == HeroSubClass.FREERUNNER && !isStarving()) {
            z = true;
        }
        return heroSprite.sprint(z) ? speed * 1.6f : speed;
    }

    public float attackDelay() {
        KindOfWeapon wep = this.rangedWeapon != null ? this.rangedWeapon : this.belongings.weapon;
        if (wep != null) {
            return wep.speedFactor(this);
        }
        return TIME_TO_REST;
    }

    public void spend(float time) {
        int hasteLevel = 0;
        Iterator it = buffs(Haste.class).iterator();
        while (it.hasNext()) {
            hasteLevel += ((Haste) ((Buff) it.next())).level;
        }
        if (hasteLevel != 0) {
            time = (float) (((double) time) * Math.pow(1.1d, (double) (-hasteLevel)));
        }
        super.spend(time);
    }

    public void spendAndNext(float time) {
        busy();
        spend(time);
        next();
    }

    public boolean act() {
        super.act();
        if (this.paralysed) {
            this.curAction = null;
            spendAndNext(TIME_TO_REST);
            return false;
        }
        checkVisibleMobs();
        AttackIndicator.updateState();
        if (this.curAction == null) {
            if (this.restoreHealth) {
                if (isStarving() || this.HP >= this.HT) {
                    this.restoreHealth = false;
                } else {
                    spend(TIME_TO_REST);
                    next();
                    return false;
                }
            }
            ready();
            return false;
        }
        this.restoreHealth = false;
        this.ready = false;
        if (this.curAction instanceof Move) {
            return actMove((Move) this.curAction);
        }
        if (this.curAction instanceof Interact) {
            return actInteract((Interact) this.curAction);
        }
        if (this.curAction instanceof Buy) {
            return actBuy((Buy) this.curAction);
        }
        if (this.curAction instanceof PickUp) {
            return actPickUp((PickUp) this.curAction);
        }
        if (this.curAction instanceof OpenChest) {
            return actOpenChest((OpenChest) this.curAction);
        }
        if (this.curAction instanceof Unlock) {
            return actUnlock((Unlock) this.curAction);
        }
        if (this.curAction instanceof Descend) {
            return actDescend((Descend) this.curAction);
        }
        if (this.curAction instanceof Ascend) {
            return actAscend((Ascend) this.curAction);
        }
        if (this.curAction instanceof Attack) {
            return actAttack((Attack) this.curAction);
        }
        if (this.curAction instanceof Cook) {
            return actCook((Cook) this.curAction);
        }
        if (this.curAction instanceof Storage) {
            return actStorage((Storage) this.curAction);
        }
        return false;
    }

    public void busy() {
        this.ready = false;
    }

    private void ready() {
        this.sprite.idle();
        this.curAction = null;
        this.ready = true;
        GameScene.ready();
    }

    public void interrupt() {
        if (!(!isAlive() || this.curAction == null || this.curAction.dst == this.pos)) {
            this.lastAction = this.curAction;
        }
        this.curAction = null;
    }

    public void resume() {
        this.curAction = this.lastAction;
        this.lastAction = null;
        act();
    }

    private boolean actMove(Move action) {
        if (getCloser(action.dst)) {
            return true;
        }
        if (Dungeon.level.map[this.pos] == 29) {
            Sign.read(this.pos);
        }
        ready();
        return false;
    }

    private boolean actInteract(Interact action) {
        NPC npc = action.npc;
        if (Level.adjacent(this.pos, npc.pos)) {
            ready();
            this.sprite.turnTo(this.pos, npc.pos);
            npc.interact();
            return false;
        } else if (Level.fieldOfView[npc.pos] && getCloser(npc.pos)) {
            return true;
        } else {
            ready();
            return false;
        }
    }

    private boolean actBuy(Buy action) {
        int dst = action.dst;
        if (this.pos == dst || Level.adjacent(this.pos, dst)) {
            ready();
            Heap heap = (Heap) Dungeon.level.heaps.get(dst);
            if (heap != null && heap.type == Type.FOR_SALE && heap.size() == 1) {
                GameScene.show(new WndTradeItem(heap, true));
            }
            return false;
        } else if (getCloser(dst)) {
            return true;
        } else {
            ready();
            return false;
        }
    }

    private boolean actCook(Cook action) {
        int dst = action.dst;
        if (Dungeon.visible[dst]) {
            ready();
            AlchemyPot.operate(this, dst);
            return false;
        } else if (getCloser(dst)) {
            return true;
        } else {
            ready();
            return false;
        }
    }

    private boolean actPickUp(PickUp action) {
        int dst = action.dst;
        if (this.pos == dst) {
            Heap heap = (Heap) Dungeon.level.heaps.get(this.pos);
            if (heap != null) {
                Item item = heap.pickUp();
                if (item.doPickUp(this)) {
                    if (!(item instanceof Dewdrop)) {
                        boolean important;
                        if ((((item instanceof ScrollOfUpgrade) || (item instanceof ScrollOfEnchantment)) && ((Scroll) item).isKnown()) || (((item instanceof PotionOfStrength) || (item instanceof PotionOfMight)) && ((Potion) item).isKnown())) {
                            important = true;
                        } else {
                            important = false;
                        }
                        if (important) {
                            GLog.m3p(TXT_YOU_NOW_HAVE, item.name());
                        } else {
                            GLog.m1i(TXT_YOU_NOW_HAVE, item.name());
                        }
                    }
                    if (!heap.isEmpty()) {
                        GLog.m1i(TXT_SOMETHING_ELSE, new Object[0]);
                    }
                    this.curAction = null;
                } else {
                    Dungeon.level.drop(item, this.pos).sprite.drop();
                    ready();
                }
            } else {
                ready();
            }
            return false;
        } else if (getCloser(dst)) {
            return true;
        } else {
            ready();
            return false;
        }
    }

    private boolean actOpenChest(OpenChest action) {
        int dst = action.dst;
        if (Level.adjacent(this.pos, dst) || this.pos == dst) {
            Heap heap = (Heap) Dungeon.level.heaps.get(dst);
            if (heap == null || heap.type == Type.HEAP || heap.type == Type.FOR_SALE) {
                ready();
                return false;
            }
            this.theKey = null;
            if (heap.type == Type.LOCKED_CHEST || heap.type == Type.CRYSTAL_CHEST) {
                this.theKey = this.belongings.getKey(GoldenKey.class, Dungeon.depth);
                if (this.theKey == null) {
                    GLog.m4w(TXT_LOCKED_CHEST, new Object[0]);
                    ready();
                    return false;
                }
            }
            switch (C00191.$SwitchMap$com$watabou$pixeldungeon$items$Heap$Type[heap.type.ordinal()]) {
                case WndUpdates.ID_PRISON /*1*/:
                    Sample.INSTANCE.play(Assets.SND_TOMB);
                    Camera.main.shake(TIME_TO_REST, 0.5f);
                    break;
                case WndUpdates.ID_CAVES /*2*/:
                    break;
                default:
                    Sample.INSTANCE.play(Assets.SND_UNLOCK);
                    break;
            }
            spend(TIME_TO_REST);
            this.sprite.operate(dst);
            return false;
        } else if (getCloser(dst)) {
            return true;
        } else {
            ready();
            return false;
        }
    }

    private boolean actUnlock(Unlock action) {
        int doorCell = action.dst;
        if (Level.adjacent(this.pos, doorCell)) {
            this.theKey = null;
            int door = Dungeon.level.map[doorCell];
            if (door == STARTING_STR) {
                this.theKey = this.belongings.getKey(IronKey.class, Dungeon.depth);
            } else if (door == 25) {
                this.theKey = this.belongings.getKey(SkeletonKey.class, Dungeon.depth);
            }
            if (this.theKey != null) {
                spend(TIME_TO_REST);
                this.sprite.operate(doorCell);
                Sample.INSTANCE.play(Assets.SND_UNLOCK);
                return false;
            }
            GLog.m4w(TXT_LOCKED_DOOR, new Object[0]);
            ready();
            return false;
        } else if (getCloser(doorCell)) {
            return true;
        } else {
            ready();
            return false;
        }
    }

    private boolean actDescend(Descend action) {
        int stairs = action.dst;
        if (this.pos == stairs && this.pos == Dungeon.level.exit) {
            this.curAction = null;
            Hunger hunger = (Hunger) buff(Hunger.class);
            if (!(hunger == null || hunger.isStarving())) {
                hunger.satisfy(-36.0f);
            }
            InterlevelScene.mode = Mode.DESCEND;
            Game.switchScene(InterlevelScene.class);
            return false;
        } else if (getCloser(stairs)) {
            return true;
        } else {
            ready();
            return false;
        }
    }

    private boolean actStorage(Storage action) {
        int stairs = action.dst;
        if (this.pos == stairs && this.pos == Dungeon.level.storage) {
            GameScene.show(new WndStorage(Dungeon.hero.storage, null, WndStorage.Mode.ALL, null));
            ready();
            return false;
        } else if (getCloser(stairs)) {
            return true;
        } else {
            ready();
            return false;
        }
    }

    private boolean actAscend(Ascend action) {
        int stairs = action.dst;
        if (this.pos == stairs && this.pos == Dungeon.level.entrance) {
            if (Dungeon.depth != 1) {
                this.curAction = null;
                Hunger hunger = (Hunger) buff(Hunger.class);
                if (!(hunger == null || hunger.isStarving())) {
                    hunger.satisfy(-36.0f);
                }
                InterlevelScene.mode = Mode.ASCEND;
                Game.switchScene(InterlevelScene.class);
                return false;
            } else if (this.belongings.getItem(Amulet.class) == null) {
                GameScene.show(new WndMessage(TXT_LEAVE));
                ready();
                return false;
            } else {
                Dungeon.win(ResultDescriptions.WIN);
                Dungeon.deleteGame(Dungeon.hero.heroClass, true);
                Game.switchScene(SurfaceScene.class);
                return false;
            }
        } else if (getCloser(stairs)) {
            return true;
        } else {
            ready();
            return false;
        }
    }

    private boolean actAttack(Attack action) {
        this.enemy = action.target;
        if (Level.adjacent(this.pos, this.enemy.pos) && this.enemy.isAlive() && !isCharmedBy(this.enemy)) {
            spend(attackDelay());
            this.sprite.attack(this.enemy.pos);
            return false;
        } else if (Level.fieldOfView[this.enemy.pos] && getCloser(this.enemy.pos)) {
            return true;
        } else {
            ready();
            return false;
        }
    }

    public void rest(boolean tillHealthy) {
        spendAndNext(TIME_TO_REST);
        if (!tillHealthy) {
            this.sprite.showStatus(CharSprite.DEFAULT, TXT_WAIT, new Object[0]);
        }
        this.restoreHealth = tillHealthy;
    }

    public int attackProc(Char enemy, int damage) {
        KindOfWeapon wep = this.rangedWeapon != null ? this.rangedWeapon : this.belongings.weapon;
        if (wep == null) {
            return damage;
        }
        wep.proc(this, enemy, damage);
        switch (C00191.$SwitchMap$com$watabou$pixeldungeon$actors$hero$HeroSubClass[this.subClass.ordinal()]) {
            case WndUpdates.ID_PRISON /*1*/:
                if (wep instanceof MeleeWeapon) {
                    return damage + ((Combo) Buff.affect(this, Combo.class)).hit(enemy, damage);
                }
                return damage;
            case WndUpdates.ID_CAVES /*2*/:
                if (wep instanceof Wand) {
                    Wand wand = (Wand) wep;
                    if (wand.curCharges >= wand.maxCharges) {
                        wand.use();
                    } else if (damage > 0) {
                        wand.curCharges++;
                        wand.updateQuickslot();
                        ScrollOfRecharging.charge(this);
                    }
                    damage += wand.curCharges;
                    break;
                }
                break;
            case WndUpdates.ID_METROPOLIS /*3*/:
                break;
            default:
                return damage;
        }
        if (this.rangedWeapon == null) {
            return damage;
        }
        ((SnipersMark) Buff.prolong(this, SnipersMark.class, attackDelay() * 1.1f)).object = enemy.id();
        return damage;
    }

    public int defenseProc(Char enemy, int damage) {
        Thorns thorns = (Thorns) buff(Thorns.class);
        if (thorns != null) {
            int dmg = Random.IntRange(0, damage);
            if (dmg > 0) {
                enemy.damage(dmg, thorns);
            }
        }
        Armor armor = (Armor) buff(Armor.class);
        if (armor != null) {
            damage = armor.absorb(damage);
        }
        if (this.belongings.armor != null) {
            return this.belongings.armor.proc(enemy, this, damage);
        }
        return damage;
    }

    public void damage(int dmg, Object src) {
        this.restoreHealth = false;
        super.damage(dmg, src);
        if (this.subClass == HeroSubClass.BERSERKER && this.HP > 0 && ((float) this.HP) <= ((float) this.HT) * Fury.LEVEL) {
            Buff.affect(this, Fury.class);
        }
    }

    private void checkVisibleMobs() {
        ArrayList<Mob> visible = new ArrayList();
        boolean newMob = false;
        Iterator it = Dungeon.level.mobs.iterator();
        while (it.hasNext()) {
            Mob m = (Mob) it.next();
            if (Level.fieldOfView[m.pos] && m.hostile) {
                visible.add(m);
                if (!this.visibleEnemies.contains(m)) {
                    newMob = true;
                }
            }
        }
        if (newMob) {
            interrupt();
            this.restoreHealth = false;
        }
        this.visibleEnemies = visible;
    }

    public int visibleEnemies() {
        return this.visibleEnemies.size();
    }

    public Mob visibleEnemy(int index) {
        return (Mob) this.visibleEnemies.get(index % this.visibleEnemies.size());
    }

    private boolean getCloser(int target) {
        if (this.rooted) {
            Camera.main.shake(TIME_TO_REST, TIME_TO_REST);
            return false;
        }
        int step = -1;
        if (!Level.adjacent(this.pos, target)) {
            boolean[] p = Level.passable;
            boolean[] v = Dungeon.level.visited;
            boolean[] m = Dungeon.level.mapped;
            boolean[] passable = new boolean[Level.LENGTH];
            int i = 0;
            while (i < Level.LENGTH) {
                boolean z;
                if (p[i] && (v[i] || m[i])) {
                    z = true;
                } else {
                    z = false;
                }
                passable[i] = z;
                i++;
            }
            step = Dungeon.findPath(this, this.pos, target, passable, Level.fieldOfView);
        } else if (Actor.findChar(target) == null) {
            if (Level.pit[target] && !this.flying && !Chasm.jumpConfirmed) {
                Chasm.heroJump(this);
                interrupt();
                return false;
            } else if (Level.passable[target] || Level.avoid[target]) {
                step = target;
            }
        }
        if (step == -1) {
            return false;
        }
        int oldPos = this.pos;
        move(step);
        this.sprite.move(oldPos, this.pos);
        spend(TIME_TO_REST / speed());
        return true;
    }

    public boolean handle(int cell) {
        if (cell == -1) {
            return false;
        }
        if (Dungeon.level.map[cell] != 42 || cell == this.pos) {
            if (Level.fieldOfView[cell]) {
                Char ch = Actor.findChar(cell);
                if (ch instanceof Mob) {
                    if (ch instanceof NPC) {
                        this.curAction = new Interact((NPC) ch);
                    } else {
                        this.curAction = new Attack(ch);
                    }
                }
            }
            if (Level.fieldOfView[cell]) {
                Heap heap = (Heap) Dungeon.level.heaps.get(cell);
                if (heap != null) {
                    switch (C00191.$SwitchMap$com$watabou$pixeldungeon$items$Heap$Type[heap.type.ordinal()]) {
                        case WndUpdates.ID_METROPOLIS /*3*/:
                            this.curAction = new PickUp(cell);
                            break;
                        case WndUpdates.ID_HALLS /*4*/:
                            HeroAction pickUp = (heap.size() != 1 || heap.peek().price() <= 0) ? new PickUp(cell) : new Buy(cell);
                            this.curAction = pickUp;
                            break;
                        default:
                            this.curAction = new OpenChest(cell);
                            break;
                    }
                }
            }
            if (Dungeon.level.map[cell] == STARTING_STR || Dungeon.level.map[cell] == 25) {
                this.curAction = new Unlock(cell);
            } else if (cell == Dungeon.level.exit) {
                this.curAction = new Descend(cell);
            } else if (cell == Dungeon.level.entrance) {
                this.curAction = new Ascend(cell);
            } else if (cell == Dungeon.level.storage) {
                this.curAction = new Storage(cell);
            } else {
                this.curAction = new Move(cell);
                this.lastAction = null;
            }
        } else {
            this.curAction = new Cook(cell);
        }
        return act();
    }

    public void earnExp(int exp) {
        this.exp += exp;
        boolean levelUp = false;
        while (this.exp >= maxExp()) {
            this.exp -= maxExp();
            this.lvl++;
            switch (this.difficulty) {
                case WndUpdates.ID_SEWERS /*0*/:
                case WndUpdates.ID_PRISON /*1*/:
                case WndUpdates.ID_CAVES /*2*/:
                    this.HT += 5;
                    this.HP += 5;
                    break;
                case WndUpdates.ID_METROPOLIS /*3*/:
                    this.HT += 4;
                    this.HP += 4;
                    break;
                case WndUpdates.ID_HALLS /*4*/:
                    this.HT += 2;
                    this.HP += 2;
                    break;
            }
            this.attackSkill++;
            this.defenseSkill++;
            if (this.lvl < STARTING_STR) {
                updateAwareness();
            }
            levelUp = true;
        }
        if (levelUp) {
            GLog.m3p(TXT_NEW_LEVEL, Integer.valueOf(this.lvl));
            this.sprite.showStatus(CharSprite.POSITIVE, TXT_LEVEL_UP, new Object[0]);
            Sample.INSTANCE.play(Assets.SND_LEVELUP);
            this.skillPoints += 2;
            Badges.validateLevelReached();
        }
        if (this.subClass == HeroSubClass.WARLOCK) {
            int value = Math.min(this.HT - this.HP, ((Dungeon.depth - 1) / 5) + 1);
            if (value > 0) {
                this.HP += value;
                this.sprite.emitter().burst(Speck.factory(0), 1);
            }
            ((Hunger) buff(Hunger.class)).satisfy(TomeOfMastery.TIME_TO_READ);
        }
    }

    public int maxExp() {
        return (this.lvl * 5) + 5;
    }

    void updateAwareness() {
        this.awareness = (float) (1.0d - Math.pow(this.heroClass == HeroClass.ROGUE ? 0.85d : 0.9d, ((double) (Math.min(this.lvl, 9) + 1)) * 0.5d));
    }

    public boolean isStarving() {
        return ((Hunger) buff(Hunger.class)).isStarving();
    }

    public void add(Buff buff) {
        super.add(buff);
        if (this.sprite != null) {
            if (buff instanceof Burning) {
                GLog.m4w("You catch fire!", new Object[0]);
                interrupt();
            } else if (buff instanceof Paralysis) {
                GLog.m4w("You are paralysed!", new Object[0]);
                interrupt();
            } else if (buff instanceof Poison) {
                GLog.m4w("You are poisoned!", new Object[0]);
                interrupt();
            } else if (buff instanceof Ooze) {
                GLog.m4w("Caustic ooze eats your flesh. Wash away it!", new Object[0]);
            } else if (buff instanceof Roots) {
                GLog.m4w("You can't move!", new Object[0]);
            } else if (buff instanceof Weakness) {
                GLog.m4w("You feel weakened!", new Object[0]);
            } else if (buff instanceof Blindness) {
                GLog.m4w("You are blinded!", new Object[0]);
            } else if (buff instanceof Fury) {
                GLog.m4w("You become furious!", new Object[0]);
                this.sprite.showStatus(CharSprite.POSITIVE, "furious", new Object[0]);
            } else if (buff instanceof Charm) {
                GLog.m4w("You are charmed!", new Object[0]);
            } else if (buff instanceof Cripple) {
                GLog.m4w("You are crippled!", new Object[0]);
            } else if (buff instanceof Bleeding) {
                GLog.m4w("You are bleeding!", new Object[0]);
            } else if (buff instanceof Vertigo) {
                GLog.m4w("Everything is spinning around you!", new Object[0]);
                interrupt();
            } else if (buff instanceof Light) {
                this.sprite.add(State.ILLUMINATED);
            } else if (buff instanceof Champ) {
                this.sprite.add(State.CHAMPRED);
            }
        }
        BuffIndicator.refreshHero();
    }

    public void remove(Buff buff) {
        super.remove(buff);
        if (buff instanceof Light) {
            this.sprite.remove(State.ILLUMINATED);
        }
        BuffIndicator.refreshHero();
    }

    public int stealth() {
        int stealth = super.stealth();
        Iterator it = buffs(Shadows.class).iterator();
        while (it.hasNext()) {
            stealth += ((Shadows) ((Buff) it.next())).level;
        }
        if (this.skillStealth > 0) {
            return stealth + ((this.skillStealth / 5) + 1);
        }
        return stealth;
    }

    public void die(Object cause) {
        this.curAction = null;
        DewVial.autoDrink(this);
        if (isAlive()) {
            new Flare(8, 32.0f).color(16777062, true).show(this.sprite, TIME_TO_SEARCH);
            return;
        }
        Actor.fixTime();
        super.die(cause);
        Ankh ankh = (Ankh) this.belongings.getItem(Ankh.class);
        if (ankh == null) {
            reallyDie(cause);
            return;
        }
        Dungeon.deleteGame(Dungeon.hero.heroClass, false);
        GameScene.show(new WndResurrect(ankh, cause));
    }

    public static void reallyDie(Object cause) {
        int[] map = Dungeon.level.map;
        boolean[] visited = Dungeon.level.visited;
        boolean[] discoverable = Level.discoverable;
        for (int i = 0; i < Level.LENGTH; i++) {
            int terr = map[i];
            if (discoverable[i]) {
                visited[i] = true;
                if ((Terrain.flags[terr] & 8) != 0) {
                    Level.set(i, Terrain.discover(terr));
                    GameScene.updateMap(i);
                }
            }
        }
        Bones.leave();
        Dungeon.observe();
        Dungeon.hero.belongings.identify();
        int pos = Dungeon.hero.pos;
        ArrayList<Integer> passable = new ArrayList();
        for (int valueOf : Level.NEIGHBOURS8) {
            int cell = pos + Integer.valueOf(valueOf).intValue();
            if (Level.passable[cell] || Level.avoid[cell]) {
                if (Dungeon.level.heaps.get(cell) == null) {
                    passable.add(Integer.valueOf(cell));
                }
            }
        }
        Collections.shuffle(passable);
        Collection items = new ArrayList(Dungeon.hero.belongings.backpack.items);
        Iterator it = passable.iterator();
        while (it.hasNext()) {
            Integer cell2 = (Integer) it.next();
            if (items.isEmpty()) {
                break;
            }
            Item item = (Item) Random.element(items);
            Dungeon.level.drop(item, cell2.intValue()).sprite.drop(pos);
            items.remove(item);
        }
        GameScene.gameOver();
        if (cause instanceof Doom) {
            ((Doom) cause).onDeath();
        }
        Dungeon.deleteGame(Dungeon.hero.heroClass, true);
    }

    public void move(int step) {
        super.move(step);
        if (!this.flying) {
            if (Level.water[this.pos]) {
                Sample.INSTANCE.play(Assets.SND_WATER, TIME_TO_REST, TIME_TO_REST, Random.Float(0.8f, 1.25f));
            } else {
                Sample.INSTANCE.play(Assets.SND_STEP);
            }
            Dungeon.level.press(this.pos, this);
        }
    }

    public void onMotionComplete() {
        Dungeon.observe();
        search(false);
        super.onMotionComplete();
    }

    public void onAttackComplete() {
        AttackIndicator.target(this.enemy);
        attack(this.enemy);
        this.curAction = null;
        Invisibility.dispel();
        super.onAttackComplete();
    }

    public void onOperateComplete() {
        if (this.curAction instanceof Unlock) {
            if (this.theKey != null) {
                this.theKey.detach(this.belongings.backpack);
                this.theKey = null;
            }
            int doorCell = ((Unlock) this.curAction).dst;
            Level.set(doorCell, Dungeon.level.map[doorCell] == STARTING_STR ? 5 : 26);
            GameScene.updateMap(doorCell);
        } else if (this.curAction instanceof OpenChest) {
            if (this.theKey != null) {
                this.theKey.detach(this.belongings.backpack);
                this.theKey = null;
            }
            Heap heap = (Heap) Dungeon.level.heaps.get(((OpenChest) this.curAction).dst);
            if (heap.type == Type.SKELETON) {
                Sample.INSTANCE.play(Assets.SND_BONES);
            }
            heap.open(this);
        }
        this.curAction = null;
        super.onOperateComplete();
    }

    public boolean search(boolean intentional) {
        float level;
        boolean smthFound = false;
        int positive = 0;
        int negative = 0;
        Iterator it = buffs(Detection.class).iterator();
        while (it.hasNext()) {
            int bonus = ((Detection) ((Buff) it.next())).level;
            if (bonus > positive) {
                positive = bonus;
            } else if (bonus < 0) {
                negative += bonus;
            }
        }
        int distance = (positive + 1) + negative;
        if (intentional) {
            level = (TIME_TO_SEARCH * this.awareness) - (this.awareness * this.awareness);
        } else {
            level = this.awareness;
        }
        if (distance <= 0) {
            level /= (float) (2 - distance);
            distance = 1;
        }
        int cx = this.pos % 32;
        int cy = this.pos / 32;
        int ax = cx - distance;
        if (ax < 0) {
            ax = 0;
        }
        int bx = cx + distance;
        if (bx >= 32) {
            bx = 31;
        }
        int ay = cy - distance;
        if (ay < 0) {
            ay = 0;
        }
        int by = cy + distance;
        if (by >= 32) {
            by = 31;
        }
        for (int y = ay; y <= by; y++) {
            int x = ax;
            int p = ax + (y * 32);
            while (x <= bx) {
                if (Dungeon.visible[p]) {
                    if (intentional) {
                        this.sprite.parent.addToBack(new CheckedCell(p));
                    }
                    if (Level.secret[p] && (intentional || Random.Float() < level)) {
                        int oldValue = Dungeon.level.map[p];
                        GameScene.discoverTile(p, oldValue);
                        Level.set(p, Terrain.discover(oldValue));
                        GameScene.updateMap(p);
                        ScrollOfMagicMapping.discover(p);
                        smthFound = true;
                    }
                }
                x++;
                p++;
            }
        }
        if (intentional) {
            this.sprite.showStatus(CharSprite.DEFAULT, TXT_SEARCH, new Object[0]);
            this.sprite.operate(this.pos);
            if (smthFound) {
                spendAndNext(Random.Float() < level ? TIME_TO_SEARCH : 4.0f);
            } else {
                spendAndNext(TIME_TO_SEARCH);
            }
        }
        if (smthFound) {
            GLog.m4w(TXT_NOTICED_SMTH, new Object[0]);
            Sample.INSTANCE.play(Assets.SND_SECRET);
            interrupt();
        }
        return smthFound;
    }

    public void resurrect(int resetLevel) {
        this.HP = this.HT;
        Dungeon.gold = 0;
        this.exp = 0;
        this.belongings.resurrect(resetLevel);
        live();
    }

    public HashSet<Class<?>> resistances() {
        Resistance r = (Resistance) buff(Resistance.class);
        return r == null ? super.resistances() : r.resistances();
    }

    public HashSet<Class<?>> immunities() {
        return ((GasesImmunity) buff(GasesImmunity.class)) == null ? super.immunities() : GasesImmunity.IMMUNITIES;
    }

    public void next() {
        super.next();
    }
}
