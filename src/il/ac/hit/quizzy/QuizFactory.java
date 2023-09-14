/**
 * The QuizFactory class provides a way to create quiz instances based on the specified type,
 * including terminal-based and GUI-based quizzes.
 */

package il.ac.hit.quizzy;

public class QuizFactory {
        private final TerminalQuiz terminalQuiz;
        private final GUIQuiz guiQuiz;

        /** Constructor */
        public QuizFactory() {
            terminalQuiz = new TerminalQuiz();
            guiQuiz = new GUIQuiz();
        }

        /** Create a quiz based on type */
        public IQuiz createQuiz(QuizType type) {
            if (type == QuizType.TERMINAL) {
                /** Create a new IQuiz instance by cloning the TerminalQuiz prototype */
                return (IQuiz) terminalQuiz.clone();
            } else if (type == QuizType.GUI) {
                /** Create a new IQuiz instance by cloning the GUIQuiz prototype */
                return (IQuiz) guiQuiz.clone();
                /** None of the types were selected */
            } else {
                throw new IllegalArgumentException("Invalid QuizType");
            }
        }
}
