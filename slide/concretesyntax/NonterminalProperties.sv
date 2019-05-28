grammar slide:concretesyntax;

synthesized attribute nonterminalProperties :: SpecNonterminalProperties;
synthesized attribute nonterminalProperty :: SpecNonterminalProperty;

nonterminal NonterminalProperties_c with unparse, nonterminalProperties;
nonterminal NonterminalProperty_c with unparse, nonterminalProperty;

concrete production nilNonterminalProperties_c
top::NonterminalProperties_c ::=
{
  top.unparse = "";
  top.nonterminalProperties = nilNonterminalProp();
}

concrete production consNonterminalProperties_c
top::NonterminalProperties_c ::= prop::NonterminalProperty_c ';' rest::NonterminalProperties_c
{
  top.unparse = prop.unparse ++ ";" ++ rest.unparse;
  top.nonterminalProperties = consNonterminalProp(prop.nonterminalProperty, rest.nonterminalProperties);
}

concrete production atomMarkupNamePropNonterm
top::NonterminalProperty_c ::= 'atomMarkupName' '=' name::AtomName_c
{
  top.unparse = "atomMarkupName = " ++ name.unparse;
  top.nonterminalProperty = atomMarkupNamePropNonterminal(name.unparse);
}
