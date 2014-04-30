# engine-2048

This is a simple library which gives 2048 backend functionality. e.g the generation of a grid and moving of tiles.

## Usage

The grid datastructure is keyed by the rows by letter and name and empty slots are set as nil. e.g

{:a1 nil :a2 2 :a3 nil :a4 4
 :b1 nil :b2 2 :b3 8 :b4 4
 :c1 2 :c2 8 :c3 4 :c4 nil
 :d1 4 :d2 8 :d3 nil :d4 nil}

The grid has a map as metadata containing boolean values for keys:

- :over? - True whether the game is over (unable to move in any direction)
- :left?/:right?/:up?/:down? - True when it is possible to move in the said direction

######There are two public functions:

(generate-grid) - returns a grid as the data structure described above

(grid-move :direction grid) - moves the grid in the following direction, generates a new random tile.
  - :direction can be any of the following :left/:right/:up/:down to move in the following direction
  - grid needs to be the previous grid

## License

Copyright © 2014 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
