# CPU-Scheduling-Simulation

Welcome to the CPU-Scheduling-Simulation project! This repository contains a simulation of various CPU scheduling algorithms implemented in Java. The project aims to demonstrate how different scheduling techniques affect the performance and efficiency of CPU task management. It is an excellent resource for students and developers looking to understand CPU scheduling concepts and their practical applications.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Acknowledgements](#acknowledgements)
- [Contributing](#contributing)
- [License](#license)

## Introduction

The CPU-Scheduling-Simulation project showcases the implementation of several CPU scheduling algorithms, providing a visual and analytical comparison of their performance. It serves as an educational tool for understanding how operating systems manage CPU resources and schedule tasks.

## Features

- **First-Come, First-Served (FCFS)**: Simplest scheduling algorithm where the first task to arrive is the first to be executed.
- **Shortest Job Next (SJN)**: Selects the task with the shortest execution time.
- **Priority Scheduling**: Tasks are scheduled based on priority.
- **Round Robin (RR)**: Each task is assigned a fixed time slot in a cyclic order.
- **Multilevel Queue Scheduling**: Segregates tasks into different queues based on their priority and type.

## Installation

To set up the project on your local machine, follow these steps:

1. **Clone the repository**:
    ```sh
    git clone https://github.com/JLany/CPU-Scheduling-Simulation.git
    ```
2. **Navigate to the project directory**:
    ```sh
    cd CPU-Scheduling-Simulation
    ```
3. **Compile the project**:
    Ensure you have JDK installed. Compile the project using:
    ```sh
    javac -d bin src/*.java
    ```
4. **Run the project**:
    ```sh
    java -cp bin Main
    ```

## Usage

After running the project, you can interact with the simulation through the command-line interface. Input various tasks and observe how different scheduling algorithms handle them. Compare metrics such as waiting time, turnaround time, and CPU utilization to understand the efficiency of each algorithm.

## Project Structure

The repository is organized as follows:

- `src/`: Contains the source code files.
- `bin/`: Compiled Java class files.
- `README.md`: Project documentation.

## Acknowledgements

This project is built on fundamental concepts of operating systems and CPU scheduling. Special thanks to:

- Educational resources and textbooks on operating systems for providing theoretical foundations.
- Java documentation and community for support in implementing the project.

## Contributing

Contributions are welcome! If you have ideas for improvements or find any issues, please submit a pull request or open an issue. Your feedback and contributions are highly valued.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

We hope this CPU scheduling simulation helps you understand the intricacies of CPU scheduling algorithms. Happy learning!

---

*This README was crafted to highlight the hard work and dedication put into developing the CPU-Scheduling-Simulation project. Your feedback and contributions are highly appreciated.*
