import java.util.Scanner;
import java.util.Random;

class Die {
    private final int sides;
    private final Random random;

    public Die(int sides) {
        this.sides = sides;
        this.random = new Random();
    }

    public int roll() {
        return random.nextInt(sides) + 1;
    }
}

class Player {
    private int health;
    private final int strength;
    private final int attack;
    private final Die die;

    public Player(int health, int strength, int attack, int sides) {
        this.health = health;
        this.strength = strength;
        this.attack = attack;
        this.die = new Die(sides);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) {
            health = 0;
        }
    }

    public int attack(Scanner sc) {
        System.out.println("Player attacks and rolls die. Enter die roll:");
        int roll = sc.nextInt();
        return attack * roll;
    }

    public int defend(Scanner sc) {
        System.out.println("Player defends and rolls die. Enter die roll:");
        int roll = sc.nextInt();
        return strength * roll;
    }

    public int getHealth() {
        return health;
    }
}

class Arena {
    private final Player playerA;
    private final Player playerB;
    private final Scanner sc;

    public Arena(Player playerA, Player playerB, Scanner sc) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.sc = sc;
    }

    public void fight() {
        while (playerA.isAlive() && playerB.isAlive()) {
            // Player A attacks, Player B defends
            int attackDamageA = playerA.attack(sc);
            int defendDamageB = playerB.defend(sc);

            int damageTakenB = Math.max(0, attackDamageA - defendDamageB);
            playerB.takeDamage(damageTakenB);

            System.out.println("Player A attacks with " + attackDamageA +
                    ", Player B defends with " + defendDamageB +
                    ", Player B health reduced to " + playerB.getHealth());

            if (!playerB.isAlive()) break;

            // Player B attacks, Player A defends
            int attackDamageB = playerB.attack(sc);
            int defendDamageA = playerA.defend(sc);

            int damageTakenA = Math.max(0, attackDamageB - defendDamageA);
            playerA.takeDamage(damageTakenA);

            System.out.println("Player B attacks with " + attackDamageB +
                    ", Player A defends with " + defendDamageA +
                    ", Player A health reduced to " + playerA.getHealth());
        }

        if (playerA.isAlive()) {
            System.out.println("Game Over. Player A wins!");
        } else {
            System.out.println("Game Over. Player B wins!");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Player A's attributes (health strength attack):");
        int healthA = sc.nextInt();
        int strengthA = sc.nextInt();
        int attackA = sc.nextInt();

        System.out.println("Enter Player B's attributes (health strength attack):");
        int healthB = sc.nextInt();
        int strengthB = sc.nextInt();
        int attackB = sc.nextInt();

        System.out.println("Enter the number of sides on the dice:");
        int sides = sc.nextInt();

        Player playerA = new Player(healthA, strengthA, attackA, sides);
        Player playerB = new Player(healthB, strengthB, attackB, sides);

        Arena arena = new Arena(playerA, playerB, sc);
        arena.fight();

        sc.close();
    }
}