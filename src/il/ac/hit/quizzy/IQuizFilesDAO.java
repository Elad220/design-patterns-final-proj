/**
 * The IQuizFilesDAO interface defines methods for data access objects (DAOs)
 * responsible for managing quiz files, including saving and loading quizzes.
 */

package il.ac.hit.quizzy;

public interface IQuizFilesDAO {
    /** Save quiz */
    public abstract void saveQuizToFile(IQuiz quiz, String fileName) throws QuizException;
    /** Load quiz */
    public abstract IQuiz loadQuizFromFile(String fileName) throws QuizException;
}
