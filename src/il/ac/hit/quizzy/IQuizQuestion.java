/**
 * The IQuizQuestion interface represents a quiz question and defines methods
 * for retrieving question-related information such as title, question text, answers,
 * and correct answers.
 */

package il.ac.hit.quizzy;

import java.util.List;

/** Interface representing a quiz question */
public interface IQuizQuestion {

    /** Getters */

    /** Get the title of the question */
    String getTitle();
    /** Get question */
    String getQuestion();
    /** Get answers */
    List<String> getAnswers();
    /** Get list of booleans of answers (right answer is true) */
    List<Boolean> getCorrectAnswers();

    /** Clone the question */
    Object clone() ;
}
