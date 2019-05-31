grammar concretesyntax;

import concretesyntax:regex;
-- for first line regex property

synthesized attribute langProperties :: LanguageProperties;
synthesized attribute langProperty :: LanguageProperty;

nonterminal LanguageProperties_c with 
  langProperties, unparse;
nonterminal LanguagePropertyDcl with 
  langProperty, unparse;

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

concrete production firstLineRegexProperty
top::LanguagePropertyDcl ::= 'first_line_regex' '=' r::RegularExpression
{
  top.langProperty = firstLineRegexProp(r);
  top.unparse = "first_line_regex = " ++ r.regexString;
}

concrete production globalGrammarSpecDcl
top::LanguagePropertyDcl ::= dcl::GrammarWideSpecDcl
{
  top.langProperty = grammarWideGlobalSpec(dcl.specification);
  top.unparse = dcl.unparse;
}
