import java.util.*;

public class week_8_Practice_problems_1 {

    interface Question {
        String getPrompt();
        boolean isCorrect(String answer);
    }

    static class MultipleChoiceQuestion implements Question {
        private final String prompt;
        private final String correctAnswer;

        MultipleChoiceQuestion(String prompt, String correctAnswer) {
            this.prompt = prompt;
            this.correctAnswer = correctAnswer;
        }

        public String getPrompt() {
            return prompt;
        }

        public boolean isCorrect(String answer) {
            return correctAnswer.equalsIgnoreCase(answer == null ? "" : answer.trim());
        }
    }

    static class TrueFalseQuestion implements Question {
        private final String prompt;
        private final boolean correctAnswer;

        TrueFalseQuestion(String prompt, boolean correctAnswer) {
            this.prompt = prompt;
            this.correctAnswer = correctAnswer;
        }

        public String getPrompt() {
            return prompt;
        }

        public boolean isCorrect(String answer) {
            return Boolean.toString(correctAnswer).equalsIgnoreCase(
                    answer == null ? "" : answer.trim());
        }
    }

    static class Examination {
        private final String title;
        private final List<Question> questions = new ArrayList<>();

        Examination(String title) {
            this.title = title;
        }

        void addQuestion(Question question) {
            questions.add(question);
        }

        String getTitle() {
            return title;
        }

        List<Question> getQuestions() {
            return Collections.unmodifiableList(questions);
        }
    }

    static class Student {
        private final String name;
        private final Set<String> submittedExams = new HashSet<>();

        Student(String name) {
            this.name = name;
        }

        Attempt startExamination(Examination examination) {
            if (submittedExams.contains(examination.getTitle())) {
                throw new IllegalStateException("A submitted attempt already exists for this examination.");
            }
            System.out.println("Examination '" + examination.getTitle()
                    + "' started by " + name + ".");
            return new Attempt(this, examination);
        }

        void markSubmitted(String examTitle) {
            submittedExams.add(examTitle);
        }
    }

    static class Attempt {
        private final Student student;
        private final Examination examination;
        private final Map<Integer, String> answers = new LinkedHashMap<>();
        private boolean submitted = false;

        Attempt(Student student, Examination examination) {
            this.student = student;
            this.examination = examination;
        }

        void answerQuestion(int questionNumber, String answer) {
            if (submitted) {
                throw new IllegalStateException("Answers cannot be changed after submission.");
            }
            if (questionNumber < 1 || questionNumber > examination.getQuestions().size()) {
                throw new IllegalArgumentException("Invalid question number.");
            }
            answers.put(questionNumber, answer);
            System.out.println("Question " + questionNumber + " answered with '" + answer + "'.");
        }

        int submit() {
            if (submitted) {
                throw new IllegalStateException("Attempt has already been submitted.");
            }
            submitted = true;
            student.markSubmitted(examination.getTitle());
            System.out.println("Examination '" + examination.getTitle()
                    + "' submitted successfully.");

            int correct = 0;
            for (int i = 0; i < examination.getQuestions().size(); i++) {
                String answer = answers.get(i + 1);
                if (answer != null && examination.getQuestions().get(i).isCorrect(answer)) {
                    correct++;
                }
            }
            System.out.println("Result for '" + examination.getTitle()
                    + "' attempt: " + correct + "/" + examination.getQuestions().size() + " correct.");
            return correct;
        }
    }

    public static void main(String[] args) {
        Examination exam = new Examination("Math Quiz");
        exam.addQuestion(new MultipleChoiceQuestion("What is 2 + 2?", "A"));
        exam.addQuestion(new MultipleChoiceQuestion("What is 3 + 4?", "B"));

        Student student = new Student("Student");
        Attempt attempt = student.startExamination(exam);
        attempt.answerQuestion(1, "A");
        attempt.answerQuestion(2, "C");
        attempt.submit();
    }
}
