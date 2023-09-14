package il.ac.hit.quizzy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IQuizFilesDAOTest {
    private IQuizFilesDAO quizFilesDAO;
    private String testQuizFileName;

    @BeforeEach
    void setUp() {
        quizFilesDAO = SimpleCSVQuizFilesDAO.getInstance();
        /** Create a test file quiz */
        testQuizFileName = "test_quiz_file.csv";
    }

    @AfterEach
    void tearDown() {
        /** Delete the temporary file if it exists */
        File testFile = new File(testQuizFileName);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void saveQuizToFile() {
        /** Create test quiz */
        IQuiz quiz = new TerminalQuiz();
        try {
            quiz.setName("Test");
            List<IQuizQuestion> questions = new ArrayList<>();
            IQuizQuestion question1 = new QuizQuestion.Builder()
                    .setTitle("Question 1")
                    .setQuestion("What course is this?")
                    .addAnswer("Design Patterns", true)
                    .addAnswer("Javascript", false)
                    .addAnswer("C#", false)
                    .addAnswer("Calculus", false)
                    .addAnswer("Dog training", false)
                    .create();
            questions.add(question1);
            quiz.setQuestions(questions);

            /** Save the quiz to file */
            quizFilesDAO.saveQuizToFile(quiz, testQuizFileName);

            /** Verify that the file exists */
            File tempFile = new File(testQuizFileName);
            assertTrue(tempFile.exists());
        } catch (QuizException e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }

    @Test
    void loadQuizFromFile() {
        /** Create a sample quiz and add questions to it */
        IQuiz quiz = new TerminalQuiz();
        try {
            quiz.setName("Sample Quiz");
            List<IQuizQuestion> questions = new ArrayList<>();
            IQuizQuestion question1 = new QuizQuestion.Builder()
                    .setTitle("Question 1")
                    .setQuestion("What course is this?")
                    .addAnswer("Design Patterns", true)
                    .addAnswer("Javascript", false)
                    .addAnswer("C#", false)
                    .addAnswer("Calculus", false)
                    .addAnswer("Dog training", false)
                    .create();
            questions.add(question1);
            quiz.setQuestions(questions);

            /** Save the quiz to file */
            quizFilesDAO.saveQuizToFile(quiz, testQuizFileName);

            /** Load the quiz from file */
            IQuiz loadedQuiz = quizFilesDAO.loadQuizFromFile(testQuizFileName);

            /** Verify the file is valid */
            assertEquals(quiz.getName(), loadedQuiz.getName());
            assertEquals(quiz.getQuestions().size(), loadedQuiz.getQuestions().size());
            assertEquals(quiz.getQuestions().get(0).getTitle(), loadedQuiz.getQuestions().get(0).getTitle());
        } catch (QuizException e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }
}
