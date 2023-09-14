/**
 * The QuizQuestion class represents a quiz question and implements the IQuizQuestion interface.
 * It provides methods for retrieving question-related information and supports cloning for deep copying.
 * Additionally, it includes a builder pattern for creating instances of QuizQuestion.
 */

package il.ac.hit.quizzy;

import java.util.ArrayList;
import java.util.List;
import java.lang.Object;

public class QuizQuestion implements IQuizQuestion, Cloneable {
    private String title;
    private String question;
    private List<String> answers;
    private List<Boolean> correctAnswers;

    /** Constructor to initialize answers and correctAnswers */
    protected QuizQuestion() {
        answers = new ArrayList<>();
        correctAnswers = new ArrayList<>();
    }

    /** Getters */

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public List<String> getAnswers() {
        /** Return a new copy of the answers list to prevent modification of the original list */
        return new ArrayList<>(answers);
    }

    @Override
    public List<Boolean> getCorrectAnswers() {
        /** Return a new copy of the correctAnswers list to prevent modification of the original list */
        return new ArrayList<>(correctAnswers);
    }


    /** Additional Methods */
    @Override
    public Object clone() {
       try{
            /** Create a new QuizQuestion instance by cloning the object */
            QuizQuestion clonedQuestion = (QuizQuestion) super.clone();

            /** Copy the title and question */
            clonedQuestion.title = this.title;
            clonedQuestion.question = this.question;

            /** Clone the lists deeply to avoid sharing references */
            clonedQuestion.answers = new ArrayList<>(answers);
            clonedQuestion.correctAnswers = new ArrayList<>(correctAnswers);

            return clonedQuestion;

           } catch (CloneNotSupportedException e) {
               try {
                   /** Handle CloneNotSupportedException by wrapping it in a custom QuizException */
                   throw new QuizException("CloneNotSupportedException while cloning", e);
               } catch (QuizException ex) {
                   /** Re-throw as a runtime exception */
                   throw new RuntimeException(ex);
               }
           }
    }


    /** Builder pattern for creating QuizQuestion instances */
    public static class Builder implements IQuizQuestionBuilder {
        private String title;
        private String question;
        private List<String> answers;
        private List<Boolean> correctAnswers;

        /** Constructor */
        public Builder() {
            answers = new ArrayList<>();
            correctAnswers = new ArrayList<>();
        }

        /** Setters */
        @Override
        public IQuizQuestionBuilder setTitle(String text) throws QuizException {
            if (text == null || text.isEmpty()) {
                throw new QuizException("Title cannot be null or empty");
            }
            this.title = text;
            return this;
        }

        @Override
        public IQuizQuestionBuilder setQuestion(String text) throws QuizException {
            if (text == null || text.isEmpty()) {
                throw new QuizException("Question cannot be null or empty");
            }
            this.question = text;
            return this;
        }

        /** Additional Methods */
        @Override
        public IQuizQuestionBuilder addAnswer(String text, boolean correct) {
            answers.add(text);
            correctAnswers.add(correct);
            return this;
        }

        @Override
        public IQuizQuestionBuilder addAnswers(List<String> answerList, List<Boolean> correctList) throws QuizException {
            if (answerList.size() != correctList.size() || answerList.size() < 2) {
                throw new QuizException("Invalid answer data");
            }
            answers.addAll(answerList);
            correctAnswers.addAll(correctList);
            return this;
        }

        @Override
        public IQuizQuestion create() {
            /** Create a QuizQuestion instance with provided builder data */
            QuizQuestion question = new QuizQuestion();
            question.title = this.title;
            question.question = this.question;
            question.answers = this.answers;
            question.correctAnswers = this.correctAnswers;
            return question;
        }
    }
}
