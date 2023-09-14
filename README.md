# Quizzy Project Readme

## Introduction

Quizzy is a Java-based quiz application that leverages several design patterns to provide a flexible and extensible quiz management system. It incorporates the following design patterns:

1. **Singleton Pattern**: Ensures that there is a single instance of the `SimpleCSVQuizFilesDAO` class responsible for saving and loading quiz data from CSV files.

2. **Factory Pattern**: Provides a `QuizFactory` class to create quiz instances based on the user's choice, allowing you to select either a GUI or Terminal-based quiz.

3. **Prototype Pattern**: Utilizes the `clone` method for deep cloning quiz objects to enable efficient creation of similar quiz instances with different questions.

4. **Builder Pattern**: Offers a flexible and easy-to-use `IQuizQuestionBuilder` interface for constructing quiz questions with different attributes.

5. **Data Access Object (DAO) Pattern**: Implements the `IQuizFilesDAO` interface to manage the persistence of quiz data, allowing you to save and load quizzes from CSV files.

## Features

- Creation of both Terminal and GUI-based quizzes.
- Efficient deep cloning of quizzes for easy customization.
- Flexible creation of quiz questions with the Builder pattern.
- Saving and loading quizzes from CSV files with the DAO pattern.
- User-friendly interface for selecting quiz type and answering questions.
- Calculation of the user's score at the end of the quiz.

## Prerequisites

To run the Quizzy project, you'll need:

- Java Development Kit (JDK) installed on your system.
- An integrated development environment (IDE) like IntelliJ IDEA.

## Usage

1. Run the Quizzy application.

2. Choose the quiz type:
  - Terminal Quiz: Answer questions through the command line.
  - GUI Quiz: Answer questions through a graphical user interface.

3. Start the quiz and answer the questions presented to you.

4. At the end of the quiz, you will receive your final score.

## Project Structure

The Quizzy project is organized as follows:

- `src/main/java/il/ac/hit/quizzy/`: Contains the Java source code for the project.
  - `QuizFactory.java`: Factory class for creating quiz instances.
  - `SimpleCSVQuizFilesDAO.java`: Implementation of the DAO pattern for saving and loading quizzes from CSV files.
  - `TerminalQuiz.java`: Implementation of the Terminal-based quiz.
  - `GUIQuiz.java`: Implementation of the GUI-based quiz.
  - `QuizQuestion.java`: Implementation of quiz questions and the Builder pattern.
  - `IQuizQuestionBuilder.java`: Interface for the Builder pattern.
  - `QuizException.java`: Custom exception class for quiz-related exceptions.
  - `IQuiz.java`: Interface for quiz management.
  - `IQuizFilesDAO.java`: Interface for data access objects (DAOs) for quiz file management.

