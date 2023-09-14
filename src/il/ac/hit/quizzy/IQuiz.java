/**
 * The IQuiz interface defines methods for managing a quiz, including setting and retrieving
 * quiz information, calculating the final score, starting the quiz, adding questions,
 * and cloning the quiz.
 */

package il.ac.hit.quizzy;
import java.util.List;

public interface IQuiz {

    /** Setters */

    /** Set the name of the quiz */
    public abstract void setName(String text) throws QuizException;
    /** Set the list of questions in the quiz */
    void setQuestions(List<IQuizQuestion> questions) throws QuizException;

    /** Getters */

    /** Get the name of the quiz */
    public abstract String getName();
    /** Get the list of questions in the quiz */
    List<IQuizQuestion> getQuestions();
    /** Get the type of the quiz */
    public abstract String getType();

    /** Additional Methods */

    /** Calculate final score */
    double calculateScore(int correctAnswersCount, int questionsCount);

    /** Start the quiz */
    public abstract void start() throws QuizException;
    /** Add a question to the quiz */
    public abstract void addQuestion(IQuizQuestion question);
    /** Clone the quiz */
    Object clone();
}
