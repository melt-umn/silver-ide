grammar concretesyntax;

terminal LanguageProperties_kwd 'language_properties';
terminal LanguageName_kwd 'language_name';
terminal FileExtensions_kwd 'file_extensions';
terminal TreesitterParser_kwd 'treesitter_parser';

synthesized attribute langProperties :: LanguageProperties;
synthesized attribute langProperty :: LanguageProperty;

nonterminal LanguageProperties_c with langProperties, unparse;
nonterminal LanguagePropertyDcl with langProperty, unparse;

concrete production nilLangProperties_c
top::LanguageProperties_c ::= 
{
  top.langProperties = nilLanguageProp();
  top.unparse = "";
}

concrete production consLangProperties_c
top::LanguageProperties_c ::= dcl::LanguagePropertyDcl ';' rest::LanguageProperties_c
{
  top.langProperties = consLanguageProp(dcl.langProperty, rest.langProperties);
  top.unparse = dcl.unparse ++ ";" ++ rest.unparse;
}

concrete production langNameProperty
top::LanguagePropertyDcl ::= 'language_name' '=' n::Name
{
  top.langProperty = langNameProp(n.name);
  top.unparse = "language_name = " ++ n.unparse;
}

concrete production fileExtProperty
top::LanguagePropertyDcl ::= 'file_extensions' '=' '[' names::NameList ']'
{
  top.langProperty = fileExtensionsProp(names.names);
  top.unparse = "file_extensions = [" ++ names.unparse ++ "]";
}

concrete production treesitterParserProperty
top::LanguagePropertyDcl ::= 'treesitter_parser' '=' n::NPMScopedPackageName_t
{
  top.langProperty = treesitterParserNameProp(n.lexeme);
  top.unparse = "treesitter_parser = " ++ n.lexeme;
}