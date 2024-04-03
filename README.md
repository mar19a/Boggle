# Boggle

## Overview

This game challenges players to form words from a grid of letters. Points are scored based on the letters used, with special bonuses for certain consonants. The game checks words against a comprehensive English dictionary. 

## Scoring Rules

- **Consonants**: Each consonant in a word is worth 1 point.
- **Vowels**: Each vowel (`A`, `E`, `I`, `O`, `U`) is worth 5 points.
- **Special Consonants Bonus**: Using any of `S`, `Z`, `P`, `X`, `Q` doubles the word's value.
- **Incorrect Responses**: Submitting a non-dictionary word deducts 10 points.

## Word List

- Utilize an extensive dictionary for word verification. For example: [English Words List](https://raw.githubusercontent.com/dwyl/english-words/master/words.txt)

## Gameplay

1. **Select Word**: Players form words by selecting contiguous letters in the grid.
2. **Submit or Clear**: Players can submit their word or clear the current selection.
3. **Scoring**: Submitted words are checked against the dictionary:
   - Correct words add points based on the scoring rules.
   - Incorrect submissions reduce the score by 10 points.

## Game Features

- **No Time Limit**: Take your time to think of the best possible words.
- **Dynamic Grid**: The letter grid changes every game, offering endless variety.
- **Simple UI**: The game uses toasts for feedback, e.g., score changes and rule reminders.
- **Fragment-based Design**: The game's layout is managed with fragments, ensuring a modular and adaptable design.

## Implementation Requirements

- **Fragments**: Utilize fragments for different sections of the game interface.
- **Communication**: Fragments communicate through interfaces to the main activity, maintaining separation of concerns.
- **UI Polish**: Enhance user experience with visual feedback, such as changing the color of selected letters and preventing reselection.
- **Accessibility**: Ensure the game is playable on devices of all sizes in portrait mode without requiring separate layouts.

## Getting Started

To start developing or playing the game, clone this repository and open it in your favorite Android development environment. Ensure you have the latest Android SDK installed and configure the project to use it.

## Contributions

Contributions are welcome! Please fork the repository, make your changes, and submit a pull request. For major changes or new features, open an issue first to discuss what you would like to add.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
