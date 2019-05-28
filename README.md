# silver-ide
This repository contains a collection of tools built to provide IDE support for languages built using Silver.

This repository along with accompanying extensions to Silver as of now support
  - [Syntax highlighting in Atom](#syntax-highlighting-in-atom)

Future support is hoping to be provided to provide a Language Server Protocol Server that can be used by any of the IDEs that support this protocol.

# Tasks

### Syntax Highlighting in Atom
There are two scripts that do most of the work for you
1. `gen-treesitter-parser`: generates a Treesitter parser. This is the parser used by Atom which keeps the entire parse tree in memory as you edit your code. The complete documentation for this script is in the README.md of scripts.
2. `gen-atom-language-package`: generates a language package that is used by Atom to provide the highlighting for the language. The complete documentation for this script is in the README.md of scripts.

After running these scripts with the appropriate command line options, an Atom package which provides syntax highlighting will exist for your language.

A few quick notes
1. An NPM account is required to publish your Treesitter parser so Atom can use it. If you do not have an account sign up [here](https://www.npmjs.com/signup).
2. Generating the Atom language package requires familiarity with the Specification Language for IDEs (SLIDE). Read the documentation [there](slide) to get familiar.
