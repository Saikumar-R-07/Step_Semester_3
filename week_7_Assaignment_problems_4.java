public class week_7_Assaignment_problems_4 {

    interface Attackable {
        String attack();
        String attack(String weaponName);
    }

    interface Defendable {
        String defend();
    }

    static abstract class GameCharacter {
        private static int counter = 0;
        private final String characterId;

        protected GameCharacter() {
            characterId = "CHAR-" + (++counter);
        }

        public abstract String getSpecialMove();

        public String getCharacterId() {
            return characterId;
        }
    }

    static class Warrior extends GameCharacter implements Attackable, Defendable {
        private final String name;

        public Warrior(String name) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Warrior name cannot be blank.");
            }
            this.name = name;
        }

        @Override
        public String attack() {
            return name + " strikes with a blade";
        }

        @Override
        public String attack(String weaponName) {
            if (weaponName == null || weaponName.trim().isEmpty()) {
                throw new IllegalArgumentException("Weapon name cannot be blank.");
            }
            return name + " strikes with an " + weaponName;
        }

        @Override
        public String defend() {
            return name + " raises a shield";
        }

        @Override
        public String getSpecialMove() {
            return name + " unleashes Whirlwind Slash";
        }
    }

    static class Trap implements Defendable {
        private final String trapType;

        public Trap(String trapType) {
            if (trapType == null || trapType.trim().isEmpty()) {
                throw new IllegalArgumentException("Trap type cannot be blank.");
            }
            this.trapType = trapType;
        }

        @Override
        public String defend() {
            return trapType + " triggers automatically";
        }
    }

    static void resolveDefense(Defendable[] combatants) {
        for (Defendable combatant : combatants) {
            if (combatant != null) {
                System.out.println(combatant.defend());
            }
        }
    }

    public static void main(String[] args) {
        Warrior w = new Warrior("Kael");
        Trap t = new Trap("Spike Pit");

        System.out.println(w.attack());
        System.out.println(w.attack("Iron Sword"));
        System.out.println(w.defend());
        System.out.println(w.getSpecialMove());
        System.out.println("Character ID: " + w.getCharacterId());

        System.out.println(t.defend());
        resolveDefense(new Defendable[]{w, t});
    }
}
