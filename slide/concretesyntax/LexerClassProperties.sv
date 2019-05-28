grammar slide:concretesyntax;

synthesized attribute lexerClassProperties :: SpecLexerClassProperties;
synthesized attribute lexerClassProperty :: SpecLexerClassProperty;
nonterminal LexerClassProperties_c with unparse, lexerClassProperties;
nonterminal LexerClassProperty_c with unparse, lexerClassProperty;

concrete production nilLexerClassProperties_c
top::LexerClassProperties_c ::=
{
  top.unparse = "";
  top.lexerClassProperties = nilLexerClassProp();
}

concrete production consLexerClassProperties_c
top::LexerClassProperties_c ::= prop::LexerClassProperty_c ';' rest::LexerClassProperties_c
{
  top.unparse = prop.unparse ++ ";" ++ rest.unparse;
  top.lexerClassProperties = 
    consLexerClassProp(prop.lexerClassProperty, rest.lexerClassProperties);
}

concrete production atomMarkupNamePropLexClass
top::LexerClassProperty_c ::= 'atomMarkupName' '=' name::AtomName_c 
{
  top.unparse = name.unparse;
  top.lexerClassProperty = atomMarkupNamePropLexerClass(name.unparse);
}
