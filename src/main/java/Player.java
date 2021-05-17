import java.util.Objects;

public class Player {
    private final String id;
    private final String name;
    private final String totalScore;
    private final String goal;
    private final String pass;
    private final String penalty;

    public Player(String id, String name, String totalScore, String goal, String pass, String penalty) {
        this.id = id;
        this.name = name;
        this.totalScore = totalScore;
        this.goal = goal;
        this.pass = pass;
        this.penalty = penalty;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public String getGoal() {
        return goal;
    }

    public String getPass() {
        return pass;
    }

    public String getPenalty() {
        return penalty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id) &&
                Objects.equals(name, player.name) &&
                Objects.equals(totalScore, player.totalScore) &&
                Objects.equals(goal, player.goal) &&
                Objects.equals(pass, player.pass) &&
                Objects.equals(penalty, player.penalty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, totalScore, goal, pass, penalty);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", totalScore='" + totalScore + '\'' +
                ", goal='" + goal + '\'' +
                ", pass='" + pass + '\'' +
                ", penalty='" + penalty + '\'' +
                '}';
    }

    public String prettyPrint() {
        return name + ": очки - " + totalScore + ", голы - " + goal + ", пасы - " + pass + ", штрафное время - " + penalty;
    }
}
