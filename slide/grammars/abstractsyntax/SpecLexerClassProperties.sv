grammar abstractsyntax;

nonterminal SpecLexerClassProperties with atomMarkupName;

abstract production consLexerClassProp
top::SpecLexerClassProperties ::= h::SpecLexerClassProperty  t::SpecLexerClassProperties
{
  -- pick first declaration seen
  top.atomMarkupName = orElse(t.atomMarkupName, h.atomMarkupName);
}

abstract production nilLexerClassProp
top::SpecLexerClassProperties ::=
{
  top.atomMarkupName = nothing();
}

{--
 - Modifiers for terminals.
 -}
nonterminal SpecLexerClassProperty with atomMarkupName;

{- We default ALL attributes, so we can focus only on those that are interesting in each case... -}
aspect default production
top::SpecLexerClassProperty ::=
{
  top.atomMarkupName = nothing();
}

abstract production atomMarkupNamePropLexerClass
top::SpecLexerClassProperty::= name::String
{
  top.atomMarkupName = just(name);
}
