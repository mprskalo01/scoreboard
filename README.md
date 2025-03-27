
---

# Football World Cup Scoreboard Library

A simple Java library for managing live football match scores. This library provides an in-memory solution for tracking ongoing matches, updating scores, and generating match summaries.

### Features

- Start new matches
- Update match scores
- Finish ongoing matches
- Generate match summary with custom sorting

## Usage

### Installation

#### Option 1: Install via Maven
To use `scoreboard-library`, add the following dependency to your `pom.xml`:

```xml
<dependency>
  <groupId>com.worldcup</groupId>
  <artifactId>scoreboard-library</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

Then, install the package using Maven:

```sh
mvn install
```

#### Option 2: Manual Installation
1. Download the latest JAR file from the [GitHub Releases](https://github.com/mprskalo01/scoreboard/releases).
2. Place the JAR file in your project’s `libs` folder.
3. Add the JAR to your classpath:
   
   **For Java Projects:**
   ```sh
   javac -cp libs/scoreboard-library-1.0-SNAPSHOT.jar YourMainClass.java
   java -cp libs/scoreboard-library-1.0-SNAPSHOT.jar:. YourMainClass
   ```
   
   **For Maven Projects:**
   Add the JAR manually to your local repository:
   ```sh
   mvn install:install-file -Dfile=libs/scoreboard-library-1.0-SNAPSHOT.jar -DgroupId=com.worldcup -DartifactId=scoreboard-library -Dversion=1.0-SNAPSHOT -Dpackaging=jar
   ```

---

### Import
```java
import com.worldcup.scoreboard.Scoreboard;
import com.worldcup.scoreboard.model.Match;
```

### Initialize Scoreboard
```java
ScoreboardService scoreboard = new ScoreboardService();
```  

### Start Matches
```java
Match match1 = scoreboard.startMatch("Mexico", "Canada");
Match match2 = scoreboard.startMatch("Spain", "Brazil");
```  

### Update Scores
```java
scoreboard.updateScore(match1, 2, 1);
scoreboard.updateScore(match2, 3, 2);
```  

### Finish a Match
```java
scoreboard.finishMatch(match1);
```  

### Get Summary
```java
String summary = scoreboard.getSummary();
System.out.println(summary);
```  

### Example Output
```
Matches summary:
1. Spain 4 - Brazil 2
2. Germany 2 - France 2
3. Mexico 0 - Canada 0
```

## Class Documentation

### Team Class
- **Immutable record class** ensuring thread safety  

#### Validation:
- Minimum 3 character names  
- Null/empty checks  
- Automatic name formatting (proper capitalization)  

#### Methods:
- `name()` - Returns formatted team name  
- `toString()` - String representation  

---

### Match Class  

#### State Management:
- Tracks home/away teams (immutable)  
- Scores (mutable during match)  
- Start time (immutable)  
- In-progress status  

#### Validation:
- Non-negative scores  
- Only active matches can be updated  

#### Methods:
- `updateScore()` - Validates and updates scores  
- `finishMatch()` - Marks match as completed  
- `getTotalScore()` - Calculates combined score  

---

### MatchComparator Class  

#### Sorting Logic:
- **Primary:** Total score (descending)  
- **Secondary:** Most recent match (if scores are equal)  

#### Design:
- Receives reference list for position comparison  
- Implements standard Java `Comparator` interface  

---

### ScoreboardService Class  

#### Core Operations:
- Match lifecycle management (start/update/finish)  
- Summary generation  

#### Validation:
- Unique team pairs  
- Match existence checks  
- Progress state verification  

#### Thread Safety:
- Defensive copies for returned collections  
- State changes through controlled methods  

---

### Scoreboard Class (Public API)  

#### Facade Pattern:
- Simplified interface for core operations  
- Team creation abstraction  

#### Error Handling:
- Translated service exceptions  
- Consistent validation messages  
---
