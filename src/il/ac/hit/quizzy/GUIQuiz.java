/**
 * GUIQuiz is a class that implements the IQuiz interface and provides functionality
 * for creating and managing a graphical user interface (GUI) quiz with questions and answers.
 */

package il.ac.hit.quizzy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GUIQuiz implements IQuiz, Cloneable {
    private String name;
    private String quizType = "GUI";
    private List<IQuizQuestion> questions;
    private int[] selectedAnswerIndices;

    /** Constructor */
    public GUIQuiz() {
        questions = new ArrayList<>();
    }

    /** Start GUI quiz */
    @Override
    public void start() {
        /** Create the main JFrame for the quiz */
        JFrame frame = new JFrame("Quiz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /** Create a JPanel to hold the quiz components */
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(240, 240, 240));

        selectedAnswerIndices = new int[questions.size()];

        /** Create a label for the quiz title */
        JLabel titleLabel = new JLabel("Lets start the quiz!: " + name);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.BLUE);
        panel.add(titleLabel);
        /** Add a separator between the title and the questions */
        panel.add(new JSeparator());

        /** Write each title, question, and answer on the frame */
        for (int i = 0; i < questions.size(); i++) {
            IQuizQuestion question = questions.get(i);
            JLabel questionTitle = new JLabel(question.getTitle());
            questionTitle.setForeground(new Color(51, 255, 85));
            questionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(questionTitle);
            panel.add(new JSeparator());

            JLabel questionTextLabel = new JLabel(question.getQuestion());
            questionTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            questionTextLabel.setForeground(new Color(51, 209, 255));
            panel.add(questionTextLabel);
            panel.add(new JSeparator());

            List<String> answers = question.getAnswers();
            ButtonGroup buttonGroup = new ButtonGroup();
            for (int j = 0; j < answers.size(); j++) {
                JRadioButton radioButton = new JRadioButton(answers.get(j));
                radioButton.setAlignmentX(Component.CENTER_ALIGNMENT);

                /** Associate the radio button with the question and answer indices */
                radioButton.setActionCommand(i + ":" + j);

                /** Add an ActionListener to track the selected answer */
                radioButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String[] indices = e.getActionCommand().split(":");
                        int questionIndex = Integer.parseInt(indices[0]);
                        int answerIndex = Integer.parseInt(indices[1]);
                        selectedAnswerIndices[questionIndex] = answerIndex;
                    }
                });

                /** Add the radio button to the panel */
                buttonGroup.add(radioButton);
                panel.add(radioButton);
            }
            panel.add(new JSeparator());
        }

        /** Create submit button */
        JButton submitButton = new JButton("Submit");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setFocusPainted(false); // Disable the focus border
        submitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Set a black border
        panel.add(submitButton);

        /** Add action to submit button */
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int correctAnswersCount = 0;

                /** Iterate through the questions and their selected answers */
                for (int i = 0; i < questions.size(); i++) {
                    IQuizQuestion question = questions.get(i);
                    List<Boolean> correctAnswers = question.getCorrectAnswers();
                    int correctAnswerIndex = correctAnswers.indexOf(true);
                    int selectedAnswerIndex = selectedAnswerIndices[i]; // Get the selected answer index

                    /** Check if the selected answer is correct and update the score */
                    if (selectedAnswerIndex == correctAnswerIndex) {
                        correctAnswersCount++;
                    }
                }
                double score = calculateScore(correctAnswersCount, questions.size());
                /** Display a message box with the quiz completion message and the user's score */
                JOptionPane.showMessageDialog(frame, "The quiz is over! Your final score is: " + String.format("%.2f", score) + "%");
            }
        });

        /** Add the panel to the frame and display the GUI */
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
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

    /** Calculate the user's grade for the quiz */
    @Override
    public double calculateScore(int correctAnswersCount, int questionsCount) {
        return (double) correctAnswersCount / questionsCount * 100;
    }

    /** Additional Methods */

    /** Add a question to the quiz */
    @Override
    public void addQuestion(IQuizQuestion question) {
        questions.add(question);
    }

    /** Clone the GUIQuiz object */
    @Override
    public Object clone() {
        try {
            /** Create a new GUIQuiz instance */
            GUIQuiz clonedQuiz = (GUIQuiz) super.clone();

            /** Clone the list of questions deeply */
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
}
