package com.watabou.pixeldungeon.actors.hero;

import com.watabou.pixeldungeon.Badges;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.pixeldungeon.items.KindOfWeapon;
import com.watabou.pixeldungeon.items.armor.Armor;
import com.watabou.pixeldungeon.items.bags.Bag;
import com.watabou.pixeldungeon.items.keys.IronKey;
import com.watabou.pixeldungeon.items.keys.Key;
import com.watabou.pixeldungeon.items.rings.Ring;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.watabou.pixeldungeon.items.wands.Wand;
import com.watabou.pixeldungeon.items.weapon.missiles.Bow;
import com.watabou.pixeldungeon.windows.WndUpdates;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;
import java.util.Iterator;

public class Storage implements Iterable<Item> {
    public static final int BACKPACK_SIZE = 5;
    public Armor armor;
    public Bag backpack;
    public Bow bow;
    private Hero owner;
    public Ring ring1;
    public Ring ring2;
    public KindOfWeapon weapon;

    /* renamed from: com.watabou.pixeldungeon.actors.hero.Storage.1 */
    class C00211 extends Bag {
        C00211() {
            this.name = "Storage";
            this.size = Storage.BACKPACK_SIZE;
        }
    }

    private class ItemIterator implements Iterator<Item> {
        private int backpackIndex;
        private Iterator<Item> backpackIterator;
        private Item[] equipped;
        private int index;

        private ItemIterator() {
            this.index = 0;
            this.backpackIterator = Storage.this.backpack.iterator();
            this.equipped = new Item[]{Storage.this.weapon, Storage.this.armor, Storage.this.ring1, Storage.this.ring2};
            this.backpackIndex = this.equipped.length;
        }

        public boolean hasNext() {
            for (int i = this.index; i < this.backpackIndex; i++) {
                if (this.equipped[i] != null) {
                    return true;
                }
            }
            return this.backpackIterator.hasNext();
        }

        public Item next() {
            while (this.index < this.backpackIndex) {
                Item[] itemArr = this.equipped;
                int i = this.index;
                this.index = i + 1;
                Item item = itemArr[i];
                if (item != null) {
                    return item;
                }
            }
            return (Item) this.backpackIterator.next();
        }

        public void remove() {
            Item[] itemArr;
            switch (this.index) {
                case WndUpdates.ID_SEWERS /*0*/:
                    itemArr = this.equipped;
                    Storage.this.weapon = null;
                    itemArr[0] = null;
                case WndUpdates.ID_PRISON /*1*/:
                    itemArr = this.equipped;
                    Storage.this.armor = null;
                    itemArr[1] = null;
                case WndUpdates.ID_CAVES /*2*/:
                    itemArr = this.equipped;
                    Storage.this.ring1 = null;
                    itemArr[2] = null;
                case WndUpdates.ID_METROPOLIS /*3*/:
                    itemArr = this.equipped;
                    Storage.this.ring2 = null;
                    itemArr[3] = null;
                default:
                    this.backpackIterator.remove();
            }
        }
    }

    public Storage(Hero owner) {
        this.weapon = null;
        this.armor = null;
        this.ring1 = null;
        this.ring2 = null;
        this.bow = null;
        this.owner = owner;
        this.backpack = new C00211();
        this.backpack.owner = owner;
    }

    public void storeInBundle(Bundle bundle) {
        this.backpack.storeInBundle2(bundle);
    }

    public void restoreFromBundle(Bundle bundle) {
        this.backpack.clear();
        this.backpack.restoreFromBundle2(bundle);
    }

    public <T extends Item> T getItem(Class<T> itemClass) {
        Iterator it = iterator();
        while (it.hasNext()) {
            Item item = (Item) it.next();
            if (itemClass.isInstance(item)) {
                return item;
            }
        }
        return null;
    }

    public <T extends Key> T getKey(Class<T> kind, int depth) {
        Iterator it = this.backpack.iterator();
        while (it.hasNext()) {
            Item item = (Item) it.next();
            if (item.getClass() == kind && ((Key) item).depth == depth) {
                return (Key) item;
            }
        }
        return null;
    }

    public void countIronKeys() {
        IronKey.curDepthQuantity = 0;
        Iterator it = this.backpack.iterator();
        while (it.hasNext()) {
            Item item = (Item) it.next();
            if ((item instanceof IronKey) && ((IronKey) item).depth == Dungeon.depth) {
                IronKey.curDepthQuantity++;
            }
        }
    }

    public void identify() {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Item) it.next()).identify();
        }
    }

    public void observe() {
        if (this.weapon != null) {
            this.weapon.identify();
            Badges.validateItemLevelAquired(this.weapon);
        }
        if (this.armor != null) {
            this.armor.identify();
            Badges.validateItemLevelAquired(this.armor);
        }
        if (this.ring1 != null) {
            this.ring1.identify();
            Badges.validateItemLevelAquired(this.ring1);
        }
        if (this.ring2 != null) {
            this.ring2.identify();
            Badges.validateItemLevelAquired(this.ring2);
        }
        Iterator it = this.backpack.iterator();
        while (it.hasNext()) {
            ((Item) it.next()).cursedKnown = true;
        }
    }

    public void uncurseEquipped() {
        ScrollOfRemoveCurse.uncurse(this.owner, this.armor, this.weapon, this.ring1, this.ring2);
    }

    public Item randomUnequipped() {
        return (Item) Random.element(this.backpack.items);
    }

    public void resurrect(int depth) {
        for (Item item : (Item[]) this.backpack.items.toArray(new Item[0])) {
            if (item instanceof Key) {
                if (((Key) item).depth == depth) {
                    item.detachAll(this.backpack);
                }
            } else if (!(item.unique || item.isEquipped(this.owner))) {
                item.detachAll(this.backpack);
            }
        }
        if (this.weapon != null) {
            this.weapon.cursed = false;
            this.weapon.activate(this.owner);
        }
        if (this.armor != null) {
            this.armor.cursed = false;
        }
        if (this.ring1 != null) {
            this.ring1.cursed = false;
            this.ring1.activate(this.owner);
        }
        if (this.ring2 != null) {
            this.ring2.cursed = false;
            this.ring2.activate(this.owner);
        }
    }

    public int charge(boolean full) {
        int count = 0;
        Iterator it = iterator();
        while (it.hasNext()) {
            Item item = (Item) it.next();
            if (item instanceof Wand) {
                Wand wand = (Wand) item;
                if (wand.curCharges < wand.maxCharges) {
                    wand.curCharges = full ? wand.maxCharges : wand.curCharges + 1;
                    count++;
                    wand.updateQuickslot();
                }
            }
        }
        return count;
    }

    public int discharge() {
        int count = 0;
        Iterator it = iterator();
        while (it.hasNext()) {
            Item item = (Item) it.next();
            if (item instanceof Wand) {
                Wand wand = (Wand) item;
                if (wand.curCharges > 0) {
                    wand.curCharges--;
                    count++;
                    wand.updateQuickslot();
                }
            }
        }
        return count;
    }

    public Iterator<Item> iterator() {
        return new ItemIterator();
    }
}
