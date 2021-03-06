# Slide
Slide stands for Specification Language for IDEs. Slide is written in Silver
and works with languages written in Silver. Slide provides a syntax for specifying
"properties" of how IDEs should handle your language.
###  Running Slide
Slide is included with this `silver-ide` directory. Running the `build-everything` script in the home directory will do everything that is necessary to initally build Slide. After this, the `slide` command should be part of your path. Calling slide works as follows.

`slide [-I paths to specs] [command line options] ideInterfaceFile specificationListFile`

where `ideInterfaceFile` is generated by calling Silver with the `--ide-interface` flag.

and `specificationListFile` is used to indicate which of the specifications that
are on the path should be used. This can be used to filter specifications that
are used for different purposes. For example, writing general specifications and
demo specifications in the same directory and then conditionally including them
by either including them in the `specificationListFile` or not.

#### Command Line Options
- `-I <path>`:
Instructs Slide to look for specification files in the specified directory. Can be included more than once to specify multiple directories.
- `--treesitter <grammar.js file>`: Running slide with this flag will make all
ignore terminals with a specification property of `highlightable` appear in
the concrete syntax tree used by Treesitter by default these are not kept for
efficiency reasons. If highlighting these terminals is desired the highlightable
property must be provided.

### Syntax
An IDE specification contains a name specified by the line

`specification <Name>;`

This name is required. The file then contains specifications for
zero or more grammars or a global language wide ide specification.
A specification is started using

`ide_specification for grammar_name {
 ...
}`

or

`global ide_specification {
  ...
}`

The difference between these is that `ide_specification for grammar_name` builds a specification for a specific grammar
 while `global ide_specification` is meant for global properties of the entire language not specifically tied to a particular grammar.

Inside the specification, you specify the IDE "properties" you want on terminals, nonterminals and lexer classes using the following syntax.
```
terminal tName {
  property1 = value1;
  property2 = value2;
};
nonterminal ntName {
  property = value;
};
lexer class lcName {
  property = value;
};
prefix "prefixValue" {
  property = value;
}
```
#### Properties
##### Global Properties
These properties are allowed within the `global ide_specification {...}` property specification.
  - `language_name = name`: The name of your language
  - `file_extensions = [ext1, ext2, ...]`: The file extensions
  - `first_line_regex = /regex/`: A regular expression that indicates that this
  language is being used. This is often useful to distinguish between different
  extended versions of a language that use the same file extension.
  - `treesitter_parser = @scope/parser_name`: The name of the Treesitter parser. This needs to start with @ have a / and not contain any capital letters.
  - `lsp_jar_name = file.for.lsp`: The name of the JAR of your language server protocol implementation. Note the .jar SHOULD NOT be included.

##### For Terminals, Nonterminals, Lexer Classes, and Prefixes
These properties are valid to be used within terminals, nonterminals and lexer classes.

 - `atomMarkupName = name`: The name can be any of the following names provided
  under section 12.4 in the naming conventions section of this
  [article](https://macromates.com/manual/en/language_grammars.html).
  These names are used by Atom theme packages to provide colors to the syntax
  nodes that have this name associated with them.


 ##### For Terminals Only
 - `highlightable` : This property is meant to be used for ignore terminals in
 Silver to indicate that they are highlightable in the IDE. A common use case of
 this is to specify that comments are highlightable. This is necessary for some
 IDEs like Atom where ignore terminals are not usually included in the concrete
 syntax tree for efficiency reasons. This property would then override that so
 that this terminal does occur in the parse tree.
