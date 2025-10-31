# Kivi-Game

Kivi Game — Java Board Game with Design Patterns and Iterative Prototyping
Kivi Game is a Java-based board game developed as part of a software engineering course project. It showcases object-oriented design principles, iterative development, and the practical application of software design patterns for maintainable and extensible gameplay architecture.

Core Features:
Interactive Gameplay — Turn-based system where players (human or AI) roll dice and place cubes on the board.
Customizable Settings — Users can adjust player configurations, display preferences, and potentially sound options through a unified settings menu.
Dynamic UI — Built with Swing; includes multiple GUIs such as startup, setup, settings, load game, and help menus.
State Management — Enum-based game states managed centrally by a controller (e.g., KiviGame or GameEngine) for smooth screen transitions and gameplay flow.

Software Design Patterns Used:
Singleton: Ensures a single instance of the main game controller (KiviGame) managing shared resources like settings, themes, and the main window.
Facade: GameEngine acts as a façade to encapsulate core logic and simplify UI interactions.
Creator & Information Expert: GameEngine creates and manages Player, GameBoard, and other entities; Player maintains its own data (score, position, state).
Polymorphism: Unified handling of HumanPlayer and ComputerPlayer via a common Player interface.
Low Coupling & High Cohesion: Components are modular, interacting primarily through method calls and return values.
Pure Fabrication: Utility classes like CombinationMatcher, Stone, and ColorTheme encapsulate non-domain logic for reusability.

Architecture Overview:
Main Classes:
KiviGame — Singleton entry point and main window manager
GameEngine — Core logic controller (facade + creator)
Player, HumanPlayer, ComputerPlayer — Encapsulate gameplay entities
SettingsManager, ColorScheme, SoundManager — Manage visual and audio configurations
GUI modules — Startup, setup, settings, load game, help
Iteration 2–4 Deliverables:
Functional prototype with use cases: Select Player Settings, Select Display Settings
Updated use cases: Making a Roll, Placing a Player Cube
Iteration plans including sequence diagrams, architecture diagram, and JUnit tests (FactorsUtility.java)

Tech Stack:
Language: Java
UI: Java Swing
Testing: JUnit
Versioning: Git/GitHub

Learning Outcomes:
The application of software engineering best practices (iteration, modularity, maintainability)
Integration of design patterns into a real-world Java project
Team collaboration through iterative prototypes, testing, and final releases
