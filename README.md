# Code Smell Dataset

## Overview

This dataset provides a comprehensive collection of Java code examples demonstrating various code smells. The dataset centers around a `BankAccount` class implementation, presenting it in multiple variations: a clean version without code smells, and multiple versions containing intentionally injected code smells.

## Dataset Purpose

The primary purpose of this Dataset is to evaluate the performance of LLMs regarding the detection of code smells.

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

#### 2. `src/smellyannotated/`
Contains `BankAccountSmelly.java` with **all 14 code smells** injected simultaneously. Each code smell is annotated with comments (e.g., `//Long Method`, `//Data Clumps`) to identify where specific smells occur.

#### 3. `src/smellyunannotated/`
Contains `BankAccountSmelly.java` with **all 14 code smells** injected but **without any annotations**. This version is identical to `smellyannotated` except for the removal of code smell annotations.

#### 4. `src/smellybytype/`
Contains isolated examples of each code smell, organized into subdirectories. Each subdirectory focuses on a single code smell type.

##### `src/smellybytype/annotated/`
Contains 14 subdirectories (one per code smell type), each with a `BankAccountSmelly.java` that demonstrates **only that specific code smell** with annotations marking the smell.

**Subdirectories:**
- `Comments/`
- `DataClumps/`
- `DeadCode/`
- `DuplicateCode/`
- `FeatureEnvy/`
- `LargeClass/`
- `LongMethod/`
- `LongParameterList/`
- `MessageChains/`
- `MiddleMan/`
- `PrimitiveObsession/`
- `RefusedBequest/`
- `SwitchStatements/`
- `TemporaryField/`

##### `src/smellybytype/unannotated/`
Mirrors the structure of `annotated/` but **without annotations**. Each subdirectory contains a `BankAccountSmelly.java` demonstrating only that specific code smell.

#### 5. `src/utility/`
Contains supporting classes that are used by the BankAccount implementations throughout the dataset.

**Contents:**
- `AccountHolder.java` - Immutable class representing an account holder's name
- `AccountID.java` - Immutable class representing a unique account identifier
- `Money.java` - Immutable class for monetary values
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
14. **Middle Man** - Classes or Methods that delegate most of their work to others.

## Dataset Statistics

- **Total Java source files**: 35
- **Total compiled class files**: 41
- **Code smell types**: 14
- **Implementations per smell type**: 2 (annotated and unannotated)
- **Combined implementations**: 2 (smellyannotated and smellyunannotated)
- **Clean implementations**: 1
- **Utility classes**: 4

## Usage Examples

### Compiling the Code

The dataset has different compilation requirements depending on which parts you want to use:

```bash
# Compile the clean implementation (compiles successfully)
javac -d bin src/utility/*.java src/clean/BankAccount.java

# Run the demo
java -cp bin utility.BankAccountDemo

# Compile individual smell examples (most compile successfully)
javac -d bin src/utility/*.java src/smellybytype/annotated/LongMethod/BankAccountSmelly.java

# Note: Combined smell implementations (smellyannotated, smellyunannotated) 
# contain intentional references to non-existent classes and will not compile.
# These are intended for code smell detection, not execution.
```

## Notes

- The `bin/` directory contains compiled `.class` files that mirror the structure of `src/`
- All smelly implementations are based on the same core functionality as the clean version
- **Compilation Compatibility**: 
  - The **clean** implementation (`src/clean/`) compiles without errors
  - Individual smell examples in `src/smellybytype/*/` generally compile successfully
  - The combined implementations (`src/smellyannotated/` and `src/smellyunannotated/`) contain intentional references to non-existent classes (e.g., `BankBranch`, `TransactionLogger`, `NotificationService`, `AccountSecurityManager`) to demonstrate certain code smells, particularly **Large Class** and **Primitive Obsession**. These files are intended for code smell detection and educational purposes rather than compilation.
- Utility classes demonstrate proper object-oriented design principles and should be used as positive examples