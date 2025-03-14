Feature: Book Search

    Scenario: Search books by author
        Given a library with the following books
        | title             | author            | published  |
        | Can't hurt me     | David Goggins     | 2018-11-15 |
        | How to be a GOAT  | Cristiano Ronaldo | 1985-02-05 |
        | The Art of War    | Sun Tzu           | 2000-01-01 |
        When the client search books made by the author 'David Goggins'
        Then 1 books should have been found
        | title             | author            | published  |
        | Can't hurt me     | David Goggins     | 2018-11-15 |

    Scenario: Search books by publication year
        Given a library with the following books
        | title             | author            | published  |
        | Can't hurt me     | David Goggins     | 2018-11-15 |
        | How to be a GOAT  | Cristiano Ronaldo | 1985-02-05 |
        | The Art of War    | Sun Tzu           | 2000-01-01 |
        When the client search books published between the years 1980-01-01 and 2005-01-01
        Then 2 books should have been found
        | title             | author            | published  |
        | How to be a GOAT  | Cristiano Ronaldo | 1985-02-05 |
        | The Art of War    | Sun Tzu           | 2000-01-01 |

    Scenario: Search books by title
        Given a library with the following books
        | title             | author            | published  |
        | Can't hurt me     | David Goggins     | 2018-11-15 |
        | How to be a GOAT  | Cristiano Ronaldo | 1985-02-05 |
        | The Art of War    | Sun Tzu           | 2000-01-01 |
        When the client search a book by the title 'How to be a GOAT'
        Then 1 books should have been found
        | title             | author            | published  |
        | How to be a GOAT  | Cristiano Ronaldo | 1985-02-05 |