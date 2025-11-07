# Code Smell Dataset

## Overview

This dataset provides a comprehensive collection of Java code examples demonstrating various code smells. It is designed for educational purposes, machine learning research, code smell detection tools, and understanding refactoring principles. The dataset centers around a `BankAccount` class implementation, presenting it in multiple variations: a clean version without code smells, and multiple versions containing intentionally injected code smells.

## Dataset Purpose

The primary purposes of this dataset are:
- **Education**: Teaching developers about code smells and their impact on code quality
- **Machine Learning**: Training and evaluating LLMs and ML models for code smell detection
- **Tool Development**: Benchmarking and testing code analysis tools
- **Research**: Studying the characteristics and patterns of different code smells

## Directory Structure

The repository is organized into two main directories:

```
Code-Smell-Dataset/
├── src/                    # Java source files
│   ├── clean/              # Clean implementation without code smells
│   ├── smellyannotated/    # All code smells combined (with annotations)
│   ├── smellyunannotated/  # All code smells combined (without annotations)
│   ├── smellybytype/       # Individual code smells separated by type
│   │   ├── annotated/      # Each smell isolated and annotated
│   │   └── unannotated/    # Each smell isolated without annotations
│   └── utility/            # Supporting utility classes
└── bin/                    # Compiled Java class files (mirrors src structure)
```

### Source Directories Explained

#### 1. `src/clean/`
Contains the baseline `BankAccount.java` implementation that is completely free of code smells. This represents best practices and serves as the reference implementation for comparison.

**Contents:**
- `BankAccount.java` - Clean, well-structured bank account implementation

#### 2. `src/smellyannotated/`
Contains `BankAccountSmelly.java` with **all 14 code smells** injected simultaneously. Each code smell is annotated with comments (e.g., `//Long Method`, `//Data Clumps`) to identify where specific smells occur.

**Use case:** Educational purposes where learners need to identify multiple smells in a single codebase with guidance.

#### 3. `src/smellyunannotated/`
Contains `BankAccountSmelly.java` with **all 14 code smells** injected but **without any annotations**. This version is identical to `smellyannotated` except for the removal of code smell annotations.

**Use case:** Testing LLMs, ML models, or code analysis tools that need to detect code smells without hints.

#### 4. `src/smellybytype/`
Contains isolated examples of each code smell, organized into subdirectories. Each subdirectory focuses on a single code smell type.

##### `src/smellybytype/annotated/`
Contains 14 subdirectories (one per code smell type), each with a `BankAccountSmelly.java` that demonstrates **only that specific code smell** with annotations marking the smell.

**Subdirectories:**
- `Comments/` - Excessive or misleading comments
- `DataClumps/` - Groups of data items that appear together repeatedly
- `DeadCode/` - Unused code that serves no purpose
- `DuplicateCode/` - Code that is repeated in multiple places
- `FeatureEnvy/` - Method that accesses data of another object more than its own
- `LargeClass/` - Class trying to do too much
- `LongMethod/` - Method that is too long and does too many things
- `LongParameterList/` - Method with too many parameters
- `MessageChains/` - Chain of method calls (e.g., a.getB().getC().getD())
- `MiddleMan/` - Class that delegates most of its work to other classes
- `PrimitiveObsession/` - Overuse of primitive types instead of objects
- `RefusedBequest/` - Subclass that doesn't use inherited methods/fields
- `SwitchStatements/` - Complex switch statements that should be polymorphic
- `TemporaryField/` - Fields that are only set in certain circumstances

**Use case:** Focused learning or testing on specific code smell types with clear annotations.

##### `src/smellybytype/unannotated/`
Mirrors the structure of `annotated/` but **without annotations**. Each subdirectory contains a `BankAccountSmelly.java` demonstrating only that specific code smell.

**Use case:** Testing smell detection systems on isolated, single-smell examples without hints.

#### 5. `src/utility/`
Contains supporting classes that are used by the BankAccount implementations throughout the dataset.

**Contents:**
- `AccountHolder.java` - Immutable class representing an account holder's name
- `AccountID.java` - Immutable class representing a unique account identifier
- `Money.java` - Immutable class for monetary values (avoids primitive obsession)
- `BankAccountDemo.java` - Demonstration program showing BankAccount usage

These utility classes follow best practices and represent the proper use of value objects to avoid code smells like Primitive Obsession.

## Code Smells Included

This dataset includes examples of **14 different code smells**:

1. **Refused Bequest** - Subclass inherits methods/fields from parent but doesn't use them
2. **Primitive Obsession** - Using primitive types instead of small objects for simple tasks
3. **Long Method** - Methods that are too long and try to do too much
4. **Large Class** - Classes that try to do too much and have too many responsibilities
5. **Long Parameter List** - Methods with too many parameters
6. **Data Clumps** - Groups of variables that are passed together in multiple places
7. **Switch Statements** - Complex switch/case statements that should use polymorphism
8. **Temporary Field** - Instance variables that are only used in certain circumstances
9. **Comments** - Excessive, redundant, or misleading comments
10. **Duplicate Code** - The same code structure repeated in multiple places
11. **Dead Code** - Unused methods, variables, or code blocks
12. **Feature Envy** - Methods that are more interested in other classes' data than their own
13. **Message Chains** - Long chains of method calls (Law of Demeter violation)
14. **Middle Man** - Classes that delegate most of their work to other classes

## Dataset Statistics

- **Total Java source files**: 35
- **Total compiled class files**: 41
- **Code smell types**: 14
- **Implementations per smell type**: 2 (annotated and unannotated)
- **Combined implementations**: 2 (smellyannotated and smellyunannotated)
- **Clean implementations**: 1
- **Utility classes**: 4

## File Organization Summary

| Location | Files | Description |
|----------|-------|-------------|
| `src/clean/` | 1 | Clean BankAccount without any code smells |
| `src/smellyannotated/` | 1 | All 14 smells combined with annotations |
| `src/smellyunannotated/` | 1 | All 14 smells combined without annotations |
| `src/smellybytype/annotated/` | 14 | One file per smell type (annotated) |
| `src/smellybytype/unannotated/` | 14 | One file per smell type (unannotated) |
| `src/utility/` | 4 | Supporting classes used across implementations |

## Usage Examples

### Compiling the Code

```bash
# Compile all source files
javac -d bin src/**/*.java

# Compile and run the demo
javac -d bin src/utility/*.java src/clean/BankAccount.java
java -cp bin utility.BankAccountDemo
```

### Comparing Implementations

To understand a specific code smell:
1. Review the clean implementation: `src/clean/BankAccount.java`
2. Examine the annotated version: `src/smellybytype/annotated/<SmellType>/BankAccountSmelly.java`
3. Compare with the unannotated version: `src/smellybytype/unannotated/<SmellType>/BankAccountSmelly.java`

### Using for Machine Learning

- **Training data**: Use `src/smellybytype/annotated/` with labels derived from directory names
- **Testing data**: Use `src/smellybytype/unannotated/` or `src/smellyunannotated/`
- **Baseline**: Compare against `src/clean/` for quality metrics

## Notes

- The `bin/` directory contains compiled `.class` files that mirror the structure of `src/`
- All smelly implementations are based on the same core functionality as the clean version
- Some smelly versions may have compilation errors due to references to non-existent classes created specifically to demonstrate certain smells (e.g., in Primitive Obsession examples)
- Utility classes demonstrate proper object-oriented design principles and should be used as positive examples

## License

Please refer to the repository license for usage terms and conditions.

## Contributing

Contributions to expand the dataset with additional code smell examples or improvements to existing examples are welcome. Please ensure that:
- New smells are documented in this README
- Both annotated and unannotated versions are provided
- The clean version remains free of any code smells
- Utility classes continue to demonstrate best practices