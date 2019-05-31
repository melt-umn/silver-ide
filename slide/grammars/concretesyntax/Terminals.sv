
{-- Language Properties Keywords --}
terminal LanguageProperties_kwd 'language_properties';
terminal LanguageName_kwd 'language_name';
terminal FileExtensions_kwd 'file_extensions';
terminal TreesitterParser_kwd 'treesitter_parser';
terminal FirstLineRegex_kwd 'first_line_regex';

{-- Quick Spec Terminals --}
terminal Color_kwd 'color';
terminal Nonterminals_kwd 'nonterminals';
terminal Grammar_kwd 'grammar';

{-- Name Terminals --}
terminal IdLower_t /[a-z][A-Za-z0-9\_]*/;
terminal IdUpper_t /[A-Z][A-Za-z0-9\_]*/;
terminal NPMScopedPackageName_t /@[a-z0-9][a-z0-9_\-]*\/[a-z0-9_\-]+/;

{-- Spec File Terminals --}
ignore terminal WhiteSpace /[\t\r\n\ ]+/;
terminal Terminal_kwd 'terminal';
terminal Nonterminal_kwd 'nonterminal';
terminal Lexer_kwd 'lexer';
terminal Class_kwd 'class';
terminal IdeSpec_kwd 'ide_specification';
terminal For_kwd 'for';
{-- Global Spec Terminals --}
terminal Global_kwd 'global';

{-- Other Terminals --}
terminal LCurly_t '{';
terminal RCurly_t '}';
terminal LBracket_t '[';
terminal RBracket_t ']';
terminal Semicolon_t ';';
terminal Equal_t '=';
terminal Comma_t ',';
terminal Colon_t ':';

{-- Terminal Property Terminals --}
terminal AtomMarkupName_kwd 'atomMarkupName';
terminal Highligtable_kwd 'highlightable';
