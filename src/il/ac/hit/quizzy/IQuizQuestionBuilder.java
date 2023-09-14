/**
 * The IQuizQuestionBuilder interface defines methods for building quiz questions,
 * including setting the question title and text, adding answers, and creating a quiz question.
 */

package il.ac.hit.quizzy;

import java.util.List;

/** Quiz question builder interface */
public interface IQuizQuestionBuilder {

    /** Setters */

    /** Set question title */
    IQuizQuestionBuilder setTitle(String text) throws QuizException;
    /** Set question */
    IQuizQuestionBuilder setQuestion(String text) throws QuizException;

    /** Additional Methods */

    /** Add answer to question */
    IQuizQuestionBuilder addAnswer(String text, boolean correct);
    /** Add answers and bool flag to question (true points to the correct answer) */
    IQuizQuestionBuilder addAnswers(List<String> answerList, List<Boolean> correctList) throws QuizException;
    /** Create quiz question. */
    IQuizQuestion create();
}
