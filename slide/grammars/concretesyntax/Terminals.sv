
{-- Ignore Terminals --}
ignore terminal WhiteSpace /[\t\r\n\ ]+/;
ignore terminal LineComment /([\-][\-].*)/;
{-- Language Properties Keywords --}
terminal LanguageProperties_kwd 'language_properties';
terminal LanguageName_kwd 'language_name';
terminal FileExtensions_kwd 'file_extensions';
terminal TreesitterParser_kwd 'treesitter_parser';
terminal FirstLineRegex_kwd 'first_line_regex';
terminal LSPJarName_kwd 'lsp_jar_name';

{-- Quick Spec Terminals --}
terminal Color_kwd 'color';
terminal Nonterminals_kwd 'nonterminals';
terminal Terminals_kwd 'terminals';
terminal Grammar_kwd 'grammar';
terminal Grammars_kwd 'grammars';
terminal Under_kwd 'under';

{-- Name Terminals --}
terminal IdLower_t /[a-z][A-Za-z0-9\_]*/;
terminal IdUpper_t /[A-Z][A-Za-z0-9\_]*/;
terminal Id_t /[A-Za-z][A-Za-z0-9\_]*/;
terminal NPMScopedPackageName_t /@[a-z0-9][a-z0-9_\-]*\/[a-z0-9_\-]+/;

{-- Spec File Terminals --}
terminal Terminal_kwd 'terminal';
terminal Nonterminal_kwd 'nonterminal';
terminal Lexer_kwd 'lexer';
terminal Class_kwd 'class';
terminal IdeSpec_kwd 'ide_specification';
terminal Spec_kwd 'specification';
terminal For_kwd 'for';
{-- Global Spec Terminals --}
terminal Global_kwd 'global';

{-- Prefix Spec Terminals --}
terminal Prefix_kwd 'prefix';

{-- Other Terminals --}
terminal LCurly_t '{';
terminal RCurly_t '}';
terminal LBracket_t '[';
terminal RBracket_t ']';
terminal Semicolon_t ';';
terminal Equal_t '=';
terminal Comma_t ',';
terminal Colon_t ':';
terminal DoubleQuote_t '"';

{-- Terminal Property Terminals --}
terminal AtomMarkupName_kwd 'atomMarkupName';
terminal Highligtable_kwd 'highlightable';

{-- Wildcard Terminals --}
terminal WildcardMatchAll_t '*';

{-- Exclude Rule Terminals --}
terminal Excluding_kwd 'excluding';

{-- Color Terminals --}
terminal Black_kwd 'black';
terminal White_kwd 'white';
terminal Green_kwd 'green';
terminal Blue_kwd 'blue';
terminal Red_kwd 'red';
terminal Purple_kwd 'purple';
terminal DarkGreen_kwd 'dark-green';
