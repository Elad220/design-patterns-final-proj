/**
 * The TerminalQuiz class implements the IQuiz interface and provides functionality
 * for conducting a quiz in a terminal-based environment. It includes methods for
 * starting the quiz, setting quiz properties, calculating the user's score, and cloning
 * a TerminalQuiz object for reuse.
 */

package il.ac.hit.quizzy;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class TerminalQuiz implements IQuiz, Cloneable {
    private String name;
    private String quizType = "TERMINAL";
    private List<IQuizQuestion> questions;

    /** Constructor */
    public TerminalQuiz() {
        questions = new ArrayList<>();
    }

    /** Quiz start method implementation */
    @Override
    public void start() throws QuizException {
        Scanner scanner = new Scanner(System.in);
        int correctAnswersCount = 0;

        /** Game start message print to screen */
        System.out.println("Let's start the quiz!: " + name);

        /** Iterate over quiz questions */
        for (IQuizQuestion question : questions) {
            /** Print question title */
            System.out.println(question.getTitle());
            /** Print question text */
            System.out.println(question.getQuestion());

            /** Print answer options */
            List<String> answers = question.getAnswers();
            for (int i = 0; i < answers.size(); i++) {
                System.out.println((i + 1) + ". " + answers.get(i));
            }

            /** Prompt for user answer input */
            System.out.print("Choose your answer: ");
            int userAnswer = scanner.nextInt();

            /** Check if the user's answer is correct and update the score */
            /** Note: The user's answer is 1-based, while the answers list is 0-based, hence the subtraction */
            if ((userAnswer - 1) > question.getAnswers().size()){
                throw new QuizException("Answer number is out of range");
            }
            if (question.getCorrectAnswers().get(userAnswer - 1)) {
                correctAnswersCount++;
            }
        }
        /** Print the user's score */
        printScore(correctAnswersCount, questions.size());
    }

    /** Setters */

    /** Set the name of the quiz */
    @Override
    public void setName(String text) throws QuizException {
        if (text == null || text.isEmpty()) {
            throw new QuizException("Quiz name cannot be null or empty");
        }
        this.name = text;
    }

    /** Set the questions for the quiz */
    @Override
    public void setQuestions(List<IQuizQuestion> questions) throws QuizException {
        if (questions == null) {
            throw new QuizException("Quiz questions cannot be null");
        }
        this.questions = new ArrayList<>(questions);
    }

    /** Getters */

    /** Get the name of the quiz */
    @Override
    public String getName() {
        return name;
    }
    /** Get a list of questions in the quiz */
    @Override
    public List<IQuizQuestion> getQuestions() {
        return new ArrayList<>(questions);
    }
    /** Get the type of the quiz */
    @Override
    public String getType() {
        return this.quizType;
    }

    /** Additional Methods */

    /** Calculate the user's grade for the quiz */
    @Override
    public double calculateScore(int correctAnswersCount, int questionsCount) {
        return (double) correctAnswersCount / questionsCount * 100;
    }

    /** Add a question to the quiz */
    @Override
    public void addQuestion(IQuizQuestion question) {
        questions.add(question);
    }

    /** Clone the TerminalQuiz object */
    @Override
    public Object clone() {
        try {
            /** Create a new TerminalQuiz instance */
            TerminalQuiz clonedQuiz = (TerminalQuiz) super.clone();

            /** Deep copy list of questions */
            List<IQuizQuestion> clonedQuestions = new ArrayList<>();
            for (IQuizQuestion question : questions) {
                clonedQuestions.add((IQuizQuestion) question.clone());
            }

            /** Adding the cloned questions to the cloned quiz */
            try {
                clonedQuiz.setQuestions(clonedQuestions);
            } catch (QuizException e) {
                throw new RuntimeException(e);
            }

            /** Copy the name and quizType fields */
            clonedQuiz.name = this.name;
            clonedQuiz.quizType = this.quizType;

            return clonedQuiz;

        } catch (CloneNotSupportedException e) {
            try {
                /** Handle the CloneNotSupportedException by wrapping it in a custom QuizException */
                throw new QuizException("CloneNotSupportedException was raised", e);
            } catch (QuizException ex) {
                /** If an error occurs while handling the CloneNotSupportedException, wrap it in a RuntimeException for further processing */
                throw new RuntimeException(ex);
            }
        }
    }
    public void printScore(int correctAnswersCount, int questionsCount){
        double score = calculateScore(correctAnswersCount, questionsCount);
        /** Game Over print to screen */
        System.out.println("The quiz is over! Your final score is: " + String.format("%.2f", score) + "%");
    }
}



