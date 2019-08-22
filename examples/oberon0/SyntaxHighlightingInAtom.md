Syntax Highlighting in Atom
----------------------------
Atom utilizes [Treesitter](https://tree-sitter.github.io/tree-sitter/)
parsers to keep a concrete syntax tree of your code that it
incrementally updates as you type. A Treesitter grammar can be
generated from a Silver grammar by running Silver with the
`--treesitter-spec` flag followed by the name of your language.
The treesitter spec is output to a text file using the standard
 Treesitter name of `grammar.js`. The exact format of this command is

 `silver [-I grammar directories] --treesitter-spec [language name] [grammar with parser specification]`

 To consolidate the rest of the boiler plate code needed for the process of creating a
 Treesitter parser the script `gen-treesitter-parser` is provided in the scripts directory
 in this repository. This script assumes the `grammar.js` file is in the same directory
 the script is run from. The documentation for this script can be found in the [README](../scripts) of the scripts directory.

 The `gen-treesitter-parser` will add the code for the Treesitter parser to the `generated/ide` directory under your Silver directory. Many of the examples already built use Makefiles to run the commands. Looking at these may be useful to
