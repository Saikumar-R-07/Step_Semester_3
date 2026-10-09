import java.util.*;

// Question 1: The Code Sprint Judging Desk
public class week_8_Assaignment_problems_1 {
    interface ScoringRule {
        double calculate(double idea, double execution, double presentation);
        String name();
    }

    static class InnovationTrack implements ScoringRule {
        public double calculate(double idea, double execution, double presentation) {
            return idea * 0.50 + execution * 0.30 + presentation * 0.20;
        }
        public String name() { return "Innovation"; }
    }

    static class OpenTrack implements ScoringRule {
        public double calculate(double idea, double execution, double presentation) {
            return (idea + execution + presentation) / 3.0;
        }
        public String name() { return "Open"; }
    }

    static class Student {
        final String name;
        Student(String name) { this.name = name; }
    }

    enum HackathonState { OPEN, JUDGING, PUBLISHED }

    static class Score {
        final double idea, execution, presentation, total;
        Score(double idea, double execution, double presentation, ScoringRule rule) {
            if (idea < 0 || idea > 10 || execution < 0 || execution > 10 ||
                presentation < 0 || presentation > 10) {
                throw new IllegalArgumentException("Each rating must be between 0 and 10.");
            }
            this.idea = idea;
            this.execution = execution;
            this.presentation = presentation;
            this.total = rule.calculate(idea, execution, presentation);
        }
    }

    static class Project {
        final String title;
        Score score;
        Project(String title) { this.title = title; }
    }

    static class Team {
        final String name;
        final List<Student> members;
        final ScoringRule track;
        Project project;
        Team(String name, List<Student> members, ScoringRule track) {
            this.name = name; this.members = new ArrayList<>(members); this.track = track;
        }
    }

    static class Judge {
        final String name;
        Judge(String name) { this.name = name; }
        void score(Project project, Team team, double idea, double execution, double presentation,
                   Hackathon hackathon) {
            hackathon.recordScore(project, team, idea, execution, presentation);
        }
    }

    static class Hackathon {
        final String name;
        HackathonState state = HackathonState.OPEN;
        final Map<String, Team> teams = new LinkedHashMap<>();
        final Map<String, String> studentTeam = new HashMap<>();
        Hackathon(String name) { this.name = name; }

        boolean registerTeam(Team team) {
            if (state != HackathonState.OPEN) {
                System.out.println("Registration failed: Registration is closed.");
                return false;
            }
            if (team.members.size() < 2 || team.members.size() > 4) {
                System.out.println("Registration failed: A team must have 2 to 4 members.");
                return false;
            }
            if (teams.containsKey(team.name)) {
                System.out.println("Registration failed: Team name already exists.");
                return false;
            }
            for (Student s : team.members) {
                if (studentTeam.containsKey(s.name)) {
                    System.out.println("Registration failed: " + s.name + " already belongs to a team.");
                    return false;
                }
            }
            teams.put(team.name, team);
            for (Student s : team.members) studentTeam.put(s.name, team.name);
            System.out.println("Team " + team.name + " registered (" + team.members.size()
                    + " members, " + team.track.name() + " track).");
            return true;
        }

        boolean submit(String teamName, String projectName) {
            Team team = teams.get(teamName);
            if (state != HackathonState.OPEN || team == null || team.project != null) {
                System.out.println("Submission failed: Team missing, submission closed, or project already submitted.");
                return false;
            }
            team.project = new Project(projectName);
            state = HackathonState.JUDGING;
            System.out.println("Project '" + projectName + "' submitted by " + teamName + ".");
            return true;
        }

        void recordScore(Project project, Team team, double idea, double execution, double presentation) {
            if (state == HackathonState.PUBLISHED) {
                System.out.println("Rescore rejected: Results have already been published.");
                return;
            }
            if (team.project != project) {
                System.out.println("Score rejected: Project does not belong to this team.");
                return;
            }
            try {
                project.score = new Score(idea, execution, presentation, team.track);
                System.out.println("Score recorded for '" + project.title + "'.");
                System.out.printf(Locale.US, "Final score: %.2f.%n", project.score.total);
            } catch (IllegalArgumentException e) {
                System.out.println("Score rejected: " + e.getMessage());
            }
        }

        void publishResults() {
            if (state == HackathonState.PUBLISHED) {
                System.out.println("Results already published.");
                return;
            }
            state = HackathonState.PUBLISHED;
            System.out.println("Results published.");
        }
    }

    public static void main(String[] args) {
        Hackathon hackathon = new Hackathon("Code Sprint");
        Team byteBusters = new Team("ByteBusters",
                Arrays.asList(new Student("Asha"), new Student("Ravi"), new Student("Neha")),
                new InnovationTrack());
        hackathon.registerTeam(byteBusters);

        Team soloCoder = new Team("SoloCoder",
                Arrays.asList(new Student("Kiran")), new OpenTrack());
        hackathon.registerTeam(soloCoder);

        hackathon.submit("ByteBusters", "SmartAttend");
        Judge judge = new Judge("Judge 1");
        judge.score(byteBusters.project, byteBusters, 8, 7, 9, hackathon);
        hackathon.publishResults();
        judge.score(byteBusters.project, byteBusters, 10, 7, 9, hackathon);
    }
}
