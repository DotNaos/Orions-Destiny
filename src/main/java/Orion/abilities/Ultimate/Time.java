package Orion.abilities.Ultimate;



public class Time extends UltimateAbility {
        public float actualDmg;

        public Time() {
                this.bulletSize = 0;
                this.dmg = 5; //Damage depends on distance to player and distance to enemy
                this.heal = 0;
                this.knockback = 0;
                this.slowDown = 0;
                this.staminaCost = 0;
                this.traveltime = 0;
                this.duration = 0;
                this.cooldown = 0;
                String animation;
        }

        @Override
        public void activate() {
                dmgArea = (enemyDistance * bulletDistanceEnemy);
                actualDmg = dmg + dmgArea;
        }
}