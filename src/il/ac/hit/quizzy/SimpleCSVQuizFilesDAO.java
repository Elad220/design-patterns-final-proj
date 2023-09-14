/**
 * The SimpleCSVQuizFilesDAO class implements the IQuizFilesDAO interface and provides
 * methods for saving and loading quiz data from CSV files. It uses the Singleton pattern
 * to ensure a single instance of the class is created and provides functionality for
 * extracting the first element from a CSV file, saving a quiz to a CSV file, and loading
 * a quiz from a CSV file.
 */

package il.ac.hit.quizzy;

import java.io.*;
import java.util.*;

public class SimpleCSVQuizFilesDAO implements IQuizFilesDAO {
    private static SimpleCSVQuizFilesDAO instance;

    private SimpleCSVQuizFilesDAO() {
        /** Private constructor for Singleton */
    }

    /** Singleton pattern: Get the single instance of SimpleCSVQuizFilesDAO */
    public static synchronized IQuizFilesDAO getInstance() {
        if (instance == null) {
            instance = new SimpleCSVQuizFilesDAO();
        }
        return instance;
    }

    /** Helper method to extract the first element from a file */
    public static String extractFirstElement(String fileName) throws QuizException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            return reader.readLine().split(",", 2)[0];
        } catch (IOException e) {
            /** Handle any IOException and wrap it in a QuizException */
            throw new QuizException("Error extracting the first element: ", e);
        }
    }

    /**
    * This method takes a quiz object and a file name,then writes the quiz data to a CSV file,
     * including the quiz type, name, questions, and answers
    */
    @Override
    public void saveQuizToFile(IQuiz quiz, String fileName) throws QuizException {
        try (FileWriter filePtr = new FileWriter(fileName)) {
            filePtr.write(quiz.getType() + ",");
            filePtr.write(quiz.getName() + ",");
            for (IQuizQuestion question : quiz.getQuestions()) {
                filePtr.write(question.getTitle() + ",");
                filePtr.write(question.getQuestion() + ",");
                List<String> answers = question.getAnswers();
                List<Boolean> correctAnswers = question.getCorrectAnswers();
                for (int i = 0; i < answers.size(); i++) {
                    filePtr.write(answers.get(i) + ",");
                    filePtr.write(correctAnswers.get(i).toString() + ",");
                }
                filePtr.write("\n");
            }
        } catch (IOException e) {
            /** Handle any IOException and wrap it in a QuizException */
            throw new QuizException("Error saving quiz to file: ", e);
        }
    }

    /**
    * This method reads quiz data from a CSV file,
    * creates a quiz object, and populates it with the retrieved information,
    * including the quiz type, name, questions, and answers
    */
    @Override
    public IQuiz loadQuizFromFile(String fileName) throws QuizException {
        /** Extract quiz type (TERMINAL or GUI) from the file */
        String quizType = extractFirstElement(fileName);
        IQuiz quiz = null;
        if (quizType.equals("TERMINAL")){
            quiz = new TerminalQuiz();
        }else if (quizType.equals("GUI")){
            quiz = new GUIQuiz();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean flag = true;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if (flag == true){
                    /** Set the quiz name from the data */
                    quiz.setName(data[1]);
                    String[] tempArray = new String[data.length - 2];

                    /** Adjust the parts array to skip quiz name and type */
                    for (int i = 2; i < data.length; i++) {
                        tempArray[i - 2] = data[i];
                    }
                    data = tempArray;
                    flag = false;
                }

                if (data.length < 5) {
                    /** Check for invalid data format in the CSV file */
                    throw new QuizException("Invalid data format in the CSV file.");
                }

                /** retrieving the information from the file */
                String title = data[0];
                String questionText = data[1];
                List<String> answers = new ArrayList<>();
                List<Boolean> correctAnswers = new ArrayList<>();
                for (int i = 2; i < data.length; i += 2) {
                    answers.add(data[i]);
                    correctAnswers.add(Boolean.parseBoolean(data[i + 1]));
                }
                /** Enter the information to the quiz instance */
                IQuizQuestion question = new QuizQuestion.Builder()
                        .setTitle(title)
                        .setQuestion(questionText)
                        .addAnswers(answers, correctAnswers)
                        .create();
                quiz.addQuestion(question);
            }
        } catch (IOException e) {
            /** Handle any IOException and wrap it in a QuizException */
            throw new QuizException("Error loading quiz from file: ", e);
        }
        return quiz;
    }

}